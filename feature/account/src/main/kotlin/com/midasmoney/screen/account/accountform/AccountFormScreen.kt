package com.midasmoney.screen.account.accountform

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.navigation.compose.rememberNavController
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.Balance
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.resource.R
import com.midasmoney.core.ui.component.ColorPickerGrid
import com.midasmoney.core.ui.component.IconPickerGrid
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import com.midasmoney.core.util.UUID
import com.midasmoney.screen.account.AccountRoute

private const val TAG = "AccountFormScreen"

@Suppress("unused")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountFormScreen(
    args: AccountRoute.AccountForm,
    navController: NavController,
    paddingValues: PaddingValues,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val viewModel: AccountFormViewModel = hiltViewModel<AccountFormViewModel>()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer

    var name by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf<IconType?>(IconType.CREDIT_CARD) }
    var selectedColor by remember { mutableStateOf<Color?>(defaultColor) }
    var initialBalance by remember { mutableStateOf("0.00") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showIconPicker by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var hasHandledSuccess by remember { mutableStateOf(false) }

    val account = args.account
    val isEditMode = account != null

    // Reset form state on screen load
    LaunchedEffect(Unit) {
        Log.d(TAG, "Reset FormState")
        viewModel.resetFormState()
        hasHandledSuccess = false
    }

    // Load account data if editing
    LaunchedEffect(isEditMode) {
        Log.d(TAG, "Edit mode - Load account data")
        account?.let {
            name = it.name
            selectedIcon = it.icon.iconType
            selectedColor = ColorConverter.aRgbToColor(it.color)
            initialBalance = it.balance.totalValue.toString()
        }
    }

    // Handle form state changes
    LaunchedEffect(formState) {
        Log.d(TAG, "FormState - $formState")
        when (formState) {
            is AccountFormState.Success -> {
                Log.d(TAG, "State - Success")
                if (!hasHandledSuccess) {
                    hasHandledSuccess = true
                    Log.d(TAG, "State - Success - Handled")
                    navController.popBackStack()
                }
            }

            is AccountFormState.Error -> {
                Log.d(TAG, "State - Error")
                errorMessage = (formState as AccountFormState.Error).message
            }

            else -> {
                Log.d(TAG, "State None")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val titleResource =
                        if (isEditMode) R.string.title_edit_account else R.string.title_new_account
                    Text(text = stringResource(titleResource))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Validate form
                            val formData = AccountFormData(
                                name = name,
                                icon = selectedIcon?.let { IconModel(it) },
                                color = selectedColor?.toArgb(),
                                initialBalance = initialBalance.toDoubleOrNull() ?: 0.0
                            )

                            val validationError = viewModel.validateForm(formData)
                            if (validationError != null) {
                                errorMessage = validationError
                                Log.d(TAG, "Validation error return at error")
                                return@IconButton
                            }

                            // Create or update account
                            val account = Account(
                                id = if (isEditMode) UUID(account.id.toString()) else UUID.randomUUID(),
                                name = name,
                                icon = IconModel(selectedIcon!!),
                                color = selectedColor!!.toArgb(),
                                balance = Balance(
                                    totalValue = initialBalance.toDoubleOrNull() ?: 0.0,
                                    income = 0.0,
                                    expense = 0.0
                                ),
                                transactions = emptyList()
                            )

                            if (isEditMode) {
                                Log.d(TAG, "Update account")
                                viewModel.updateAccount(account)
                                navController.popBackStack()
                            } else {
                                Log.d(TAG, "Create account")
                                viewModel.createAccount(account)
                                navController.popBackStack()
                            }
                        },
                        enabled = formState !is AccountFormState.Loading

                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Error message
                errorMessage?.let { error ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MidasColors.Red.primary.copy(alpha = 0.1f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = error,
                            color = MidasColors.Red.primary,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                // Account Name
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        errorMessage = null
                    },
                    label = { Text(stringResource(R.string.label_account_name)) },
                    placeholder = { Text(stringResource(R.string.placeholder_account_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = formState !is AccountFormState.Loading
                )

                // Initial Balance
                OutlinedTextField(
                    value = initialBalance,
                    onValueChange = {
                        initialBalance = it
                        errorMessage = null
                    },
                    label = { Text(stringResource(R.string.label_initial_balance)) },
                    placeholder = { Text(stringResource(R.string.placeholder_initial_balance)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = formState !is AccountFormState.Loading
                )

                // Icon Selection
                Text(
                    text = stringResource(R.string.label_select_icon),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                MidasCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showIconPicker = !showIconPicker }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (selectedIcon != null)
                                stringResource(R.string.label_selected_icon)
                            else
                                stringResource(R.string.label_tap_to_select_icon),
                            color = if (selectedIcon != null)
                                MaterialTheme.colorScheme.onSurface
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )

                        if (selectedIcon != null) {
                            Icon(
                                imageVector = IconConverter.getImageVector(
                                    IconModel(
                                        selectedIcon!!
                                    )
                                ),
                                contentDescription = stringResource(R.string.description_selected_icon),
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        (selectedColor
                                            ?: MaterialTheme.colorScheme.secondaryContainer)
                                            .copy(alpha = 0.2f)
                                    )
                                    .padding(6.dp),
                                tint = selectedColor ?: MaterialTheme.colorScheme.secondaryContainer
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
                        }
                    )
                }

                // Color Selection
                Text(
                    text = stringResource(R.string.label_select_color),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                MidasCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showColorPicker = !showColorPicker }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (selectedColor != null)
                                stringResource(R.string.label_selected_color)
                            else
                                stringResource(R.string.label_tap_to_select_color),
                            color = if (selectedColor != null)
                                MaterialTheme.colorScheme.onSurface
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )

                        if (selectedColor != null) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(selectedColor!!)
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
                        }
                    )
                }

                // Loading indicator
                if (formState is AccountFormState.Loading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@CustomPreview
@Composable
fun AccountFormScreenPreview() {
    MidasTheme {
        val paddingValues = PaddingValues()
        val navController = rememberNavController()
        AccountFormScreen(
            args = AccountRoute.AccountForm(null),
            navController = navController,
            paddingValues = paddingValues,
            isDarkTheme = false
        )
    }
}
