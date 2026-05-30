package com.midasmoney.screen.goals.goalform

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.resource.R
import com.midasmoney.core.ui.component.ColorPickerGrid
import com.midasmoney.core.ui.component.IconPickerGrid
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.util.UUID
import com.midasmoney.screen.goals.GoalsRoute
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import java.time.Instant
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalFormScreen(
    args: GoalsRoute.GoalForm,
    navController: NavController,
) {
    val viewModel: GoalFormViewModel = hiltViewModel()
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    val goal = args.goal
    val isEditMode = goal != null

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var monthlyValue by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf<IconType?>(null) }
    var selectedColor by remember { mutableStateOf<Color?>(null) }
    var targetDate by remember { mutableStateOf<LocalDate?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showIconPicker by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var hasHandledSuccess by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    LaunchedEffect(Unit) {
        viewModel.resetFormState()
        hasHandledSuccess = false
    }

    LaunchedEffect(isEditMode) {
        goal?.let {
            title = it.title
            description = it.description
            amount = it.amount.toString()
            monthlyValue = it.monthlyValue.toString()
            selectedIcon = it.icon.iconType
            selectedColor = ColorConverter.aRgbToColor(it.color)
            targetDate = it.targetDate
        }
    }

    LaunchedEffect(formState) {
        when (formState) {
            is GoalFormState.Success -> {
                if (!hasHandledSuccess) {
                    hasHandledSuccess = true
                    navController.popBackStack()
                }
            }
            is GoalFormState.Error -> {
                errorMessage = (formState as GoalFormState.Error).message
            }
            else -> {}
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val javaDate =
                            Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                        targetDate = LocalDate(javaDate.year, javaDate.monthValue, javaDate.dayOfMonth)
                    }
                    showDatePicker = false
                }) { Text(stringResource(R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
        ) {
            DatePicker(state = datePickerState, showModeToggle = false)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text =
                            if (isEditMode) {
                                stringResource(R.string.title_edit_goal)
                            } else {
                                stringResource(R.string.title_new_goal)
                            },
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val formData =
                                GoalFormData(
                                    title = title,
                                    description = description,
                                    amount = amount.toDoubleOrNull() ?: 0.0,
                                    monthlyValue = monthlyValue.toDoubleOrNull() ?: 0.0,
                                    icon = selectedIcon?.let { IconModel(it) },
                                    color = selectedColor?.toArgb(),
                                    targetDate = targetDate,
                                )
                            val validationError = viewModel.validateForm(formData)
                            if (validationError != null) {
                                errorMessage = validationError
                                return@IconButton
                            }
                            val savedGoal =
                                Goal(
                                    id = if (isEditMode) UUID(goal.id.toString()) else UUID.randomUUID(),
                                    title = title,
                                    description = description,
                                    amount = formData.amount,
                                    progress = if (isEditMode) goal.progress else 0.0,
                                    icon = IconModel(selectedIcon!!),
                                    color = selectedColor!!.toArgb(),
                                    targetDate = targetDate!!,
                                    monthlyValue = formData.monthlyValue,
                                )
                            if (isEditMode) {
                                viewModel.updateGoal(savedGoal)
                            } else {
                                viewModel.createGoal(savedGoal)
                            }
                        },
                        enabled = formState !is GoalFormState.Loading,
                    ) {
                        Icon(Icons.Default.Check, contentDescription = stringResource(R.string.save))
                    }
                },
            )
        },
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                errorMessage?.let { error ->
                    Card(
                        colors =
                            CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                            ),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        errorMessage = null
                    },
                    label = { Text(stringResource(R.string.label_goal_title)) },
                    placeholder = { Text(stringResource(R.string.placeholder_goal_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = formState !is GoalFormState.Loading,
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.label_goal_description)) },
                    placeholder = { Text(stringResource(R.string.placeholder_goal_description)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    enabled = formState !is GoalFormState.Loading,
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                        errorMessage = null
                    },
                    label = { Text(stringResource(R.string.label_goal_amount)) },
                    placeholder = { Text(stringResource(R.string.placeholder_goal_amount)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = formState !is GoalFormState.Loading,
                )

                OutlinedTextField(
                    value = monthlyValue,
                    onValueChange = { monthlyValue = it },
                    label = { Text(stringResource(R.string.label_goal_monthly_value)) },
                    placeholder = { Text(stringResource(R.string.placeholder_goal_monthly_value)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = formState !is GoalFormState.Loading,
                )

                // Target Date
                Text(
                    text = stringResource(R.string.label_goal_target_date),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                MidasCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker = true },
                ) {
                    Text(
                        text =
                            targetDate?.format(
                                LocalDate.Format {
                                    monthName(MonthNames.ENGLISH_ABBREVIATED)
                                    char(' ')
                                    dayOfMonth()
                                    chars(", ")
                                    year()
                                },
                            ) ?: stringResource(R.string.no_selected_date),
                        modifier = Modifier.padding(16.dp),
                        color =
                            if (targetDate != null) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            },
                    )
                }

                // Icon Selection
                Text(
                    text = stringResource(R.string.label_select_icon),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                MidasCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { showIconPicker = !showIconPicker },
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text =
                                if (selectedIcon != null) {
                                    stringResource(R.string.label_selected_icon)
                                } else {
                                    stringResource(R.string.label_tap_to_select_icon)
                                },
                            color =
                                if (selectedIcon != null) {
                                    MaterialTheme.colorScheme.onSurface
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                },
                        )
                        if (selectedIcon != null) {
                            Icon(
                                imageVector =
                                    com.midasmoney.core.domain.model.converter.IconConverter
                                        .getImageVector(IconModel(selectedIcon!!)),
                                contentDescription = null,
                                modifier =
                                    Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            (selectedColor ?: MaterialTheme.colorScheme.secondaryContainer)
                                                .copy(alpha = 0.2f),
                                        )
                                        .padding(6.dp),
                                tint = selectedColor ?: MaterialTheme.colorScheme.secondaryContainer,
                            )
                        }
                    }
                }
                if (showIconPicker) {
                    IconPickerGrid(
                        selectedIcon = selectedIcon,
                        selectedColor = selectedColor,
                        onIconSelected = {
                            selectedIcon = it
                            showIconPicker = false
                            errorMessage = null
                        },
                    )
                }

                // Color Selection
                Text(
                    text = stringResource(R.string.label_select_color),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                MidasCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { showColorPicker = !showColorPicker },
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text =
                                if (selectedColor != null) {
                                    stringResource(R.string.label_selected_color)
                                } else {
                                    stringResource(R.string.label_tap_to_select_color)
                                },
                            color =
                                if (selectedColor != null) {
                                    MaterialTheme.colorScheme.onSurface
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                },
                        )
                        if (selectedColor != null) {
                            Box(
                                modifier =
                                    Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(selectedColor!!),
                            )
                        }
                    }
                }
                if (showColorPicker) {
                    ColorPickerGrid(
                        selectedColor = selectedColor,
                        onColorSelected = {
                            selectedColor = it
                            showColorPicker = false
                            errorMessage = null
                        },
                    )
                }

                if (formState is GoalFormState.Loading) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
