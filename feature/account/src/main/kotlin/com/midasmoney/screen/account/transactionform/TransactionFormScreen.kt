package com.midasmoney.screen.account.transactionform

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.TransactionStatus
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.extension.formatDate
import com.midasmoney.core.domain.model.extension.formatTime
import com.midasmoney.core.domain.model.extension.toKtLocalTime
import com.midasmoney.core.domain.model.extension.toLocalDate
import com.midasmoney.core.resource.R
import com.midasmoney.core.ui.component.ColorPickerGrid
import com.midasmoney.core.ui.component.IconPickerGrid
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.ui.component.MidasIcon
import com.midasmoney.core.ui.component.SelectedColorCircle
import com.midasmoney.core.ui.component.midasDatePicker
import com.midasmoney.core.ui.component.midasTimePicker
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import com.midasmoney.core.util.UUID
import com.midasmoney.screen.account.AccountRoute
import com.midasmoney.screen.account.component.DeleteDialog
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun TransactionFormScreen(
    args: AccountRoute.TransactionForm,
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: TransactionFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.formState.collectAsStateWithLifecycle()
    val account = args.account
    val transaction = args.transaction
    val isEditMode = transaction != null
    TransactionFormScreenImp(
        uiState = uiState,
        paddingValues = paddingValues,
        navController = navController,
        isEditMode = isEditMode,
        account = account,
        transaction = transaction,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun TransactionFormScreenImp(
    uiState: TransactionFormState,
    paddingValues: PaddingValues,
    navController: NavHostController,
    isEditMode: Boolean,
    account: Account?,
    transaction: Transaction?,
    viewModel: TransactionFormViewModel
) {
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf<IconType?>(IconType.CREDIT_CARD) }
    var color by remember { mutableStateOf<Color?>(defaultColor) }
    var amount by remember { mutableDoubleStateOf(0.00) }

    var selectedType by remember { mutableStateOf<TransactionType?>(null) }
    var selectedStatus by remember { mutableStateOf<TransactionStatus?>(null) }
    var selectedDate by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        )
    }
    var selectedTime by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
        )
    }

    var showTypePicker by remember { mutableStateOf(false) }
    var showStatusPicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showIconPicker by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isEditMode) {
        transaction.let { tr ->
            title = tr?.title ?: ""
            description = tr?.description ?: ""
            icon = tr?.icon?.iconType ?: IconType.CREDIT_CARD
            color = Color(tr?.color ?: ColorConverter.colorToArgb(defaultColor))
            amount = tr?.amount ?: 0.0
            selectedType = tr?.type
            selectedStatus = tr?.status
            selectedDate = tr?.date?.toLocalDate() ?: Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).date
            selectedTime = tr?.time?.toKtLocalTime() ?: Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).time

        }
    }

    LaunchedEffect(uiState) {
        if (uiState is TransactionFormState.Success) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(if (isEditMode) R.string.edit_transaction else R.string.new_transaction)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                },
                actions = {
                    if (showDeleteDialog) {
                        DeleteDialog(
                            titleItem = transaction?.title ?: "",
                            onConfirm = {
                                transaction?.let { viewModel.deleteTransaction(it) }
                                showDeleteDialog = false
                            },
                            onDismiss = {
                                showDeleteDialog = false
                            })
                    }
                    if (isEditMode) {
                        IconButton(
                            enabled = uiState !is TransactionFormState.Loading,
                            onClick = {
                                showDeleteDialog = true
                            }
                        ) {
                            Icon(Icons.Default.Delete, stringResource(R.string.delete))
                        }
                    }
                    IconButton(
                        enabled = uiState !is TransactionFormState.Loading,
                        onClick = {
                            val newTransaction = Transaction(
                                id = transaction?.id ?: UUID.randomUUID(),
                                accountId = account?.id ?: UUID.randomUUID(),
                                icon = IconModel(icon ?: IconType.CREDIT_CARD),
                                color = ColorConverter.colorToArgb(color ?: defaultColor),
                                title = title,
                                description = description,
                                amount = amount,
                                type = selectedType ?: TransactionType.TRANSFER,
                                status = selectedStatus ?: TransactionStatus.PENDING,
                                date = selectedDate.toJavaLocalDate().formatDate(),
                                time = selectedTime.formatTime(),
                                createAt = Clock.System.now(),
                            )
                            if (isEditMode) {
                                viewModel.updateTransaction(newTransaction)
                                navController.popBackStack()
                            } else {
                                viewModel.createTransaction(newTransaction)
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Check, stringResource(R.string.save))
                    }
                }
            )
        }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Error
            if (uiState is TransactionFormState.Error) {
                MidasCard(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        uiState.message,
                        color = MidasColors.Red.primary,
                    )
                }
            }

            // Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            // Value
            OutlinedTextField(
                value = amount.toString(),
                onValueChange = { amount = it.toDouble() },
                label = { Text("Value") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            // Icon
            PickerCard(
                label = "Icon",
                onClick = { showIconPicker = !showIconPicker },
                content = { icon?.let { MidasIcon(iconType = it, color = color) } }
            )
            if (showIconPicker) {
                IconPickerGrid(
                    selectedIcon = icon,
                    selectedColor = color,
                    onIconSelected = {
                        icon = it
                        showIconPicker = false
                    }
                )
            }

            // Color
            PickerCard(
                label = "Color",
                onClick = { showColorPicker = !showColorPicker },
                content = { color?.let { SelectedColorCircle(it) } }
            )
            if (showColorPicker) {
                ColorPickerGrid(
                    selectedColor = color,
                    onColorSelected = {
                        color = it
                        showColorPicker = false
                    }
                )
            }

            // Type
            PickerCard(
                label = "Type",
                onClick = { showTypePicker = !showTypePicker },
                content = {
                    val selectedText = selectedType?.displayName ?: "Select a type"
                    Text(selectedText)
                }
            )
            if (showTypePicker) {
                TransactionTypePicker(
                    selected = selectedType,
                    onSelected = {
                        selectedType = it
                        showTypePicker = false
                    }
                )
            }

            // Status
            PickerCard(
                label = "Status",
                onClick = { showStatusPicker = !showStatusPicker },
                content = {
                    val selectedText = selectedStatus?.displayName ?: "Select a status"
                    Text(selectedText)
                }
            )
            if (showStatusPicker) {
                TransactionStatusPicker(
                    selected = selectedStatus,
                    onSelected = {
                        selectedStatus = it
                        showStatusPicker = false
                    }
                )
            }

            // Data
            PickerCard(
                label = "Date",
                onClick = { showDatePicker = !showDatePicker },
                content = {
                    val selectedText = selectedDate.toString()
                    Text(selectedText)
                }
            )
            if (showDatePicker) {
                val currentDate = midasDatePicker()
                if (currentDate.isNotEmpty()) {
                    selectedDate = LocalDate.parse(currentDate)
                    showDatePicker = false
                }
            }

            // Hora
            PickerCard(
                label = "Time",
                onClick = { showTimePicker = !showTimePicker },
                content = { Text(selectedTime.formatTime()) }
            )
            if (showTimePicker) {
                val currentTime = midasTimePicker()
                if (currentTime.isNotEmpty()) {
                    selectedTime = currentTime.toKtLocalTime()
                    showTimePicker = false
                }
            }

            // Loading
            if (uiState is TransactionFormState.Loading) {
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun PickerCard(
    label: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    MidasCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontWeight = FontWeight.Bold)
            content()
        }
    }
}

@Composable
fun TransactionTypePicker(
    selected: TransactionType?,
    onSelected: (TransactionType) -> Unit
) {
    val types = TransactionType.entries
    MidasCard(Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(types.size) { index ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = if (types[index] == selected) 2.dp else 1.dp,
                            color = if (types[index] == selected) MidasColors.Blue.primary
                            else MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelected(types[index]) }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(types[index].displayName)
                }
            }
        }
    }
}

@Composable
fun TransactionStatusPicker(
    selected: TransactionStatus?,
    onSelected: (TransactionStatus) -> Unit
) {
    val statuses = TransactionStatus.entries

    MidasCard(Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(statuses.size) { index ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = if (statuses[index] == selected) 2.dp else 1.dp,
                            color = if (statuses[index] == selected) MidasColors.Blue.primary
                            else MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelected(statuses[index]) }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(statuses[index].displayName)
                }
            }
        }
    }
}

@CustomPreview
@Composable
fun TransactionFormPreview() {
    MidasTheme {
        TransactionFormScreenImp(
            uiState = TransactionFormState.Idle,
            navController = rememberNavController(),
            paddingValues = PaddingValues(),
            isEditMode = false,
            account = Database.accounts.first(),
            transaction = Database.transactions.first(),
            viewModel = hiltViewModel()
        )
    }
}
