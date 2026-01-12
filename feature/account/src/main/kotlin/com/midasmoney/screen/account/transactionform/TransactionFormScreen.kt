package com.midasmoney.screen.account.transactionform

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.TransactionStatus
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.domain.model.extension.formatTime
import com.midasmoney.core.domain.model.extension.toKtLocalTime
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
import com.midasmoney.screen.account.AccountRoute
import com.midasmoney.screen.account.component.DeleteDialog
import kotlinx.datetime.LocalDate
import kotlin.time.ExperimentalTime

@Composable
fun TransactionFormScreen(
    args: AccountRoute.TransactionForm,
    navController: NavHostController,
    viewModel: TransactionFormViewModel = hiltViewModel()
) {
    viewModel.initArgs(account = args.account, transaction = args.transaction)

    val uiState by viewModel.formState.collectAsStateWithLifecycle()
    val formData by viewModel.formData.collectAsStateWithLifecycle()
    val transaction by viewModel.transaction.collectAsStateWithLifecycle()
    val isEditMode by viewModel.isEditMode.collectAsStateWithLifecycle()

    TransactionFormScreenImp(
        uiState = uiState,
        formData = formData,
        navController = navController,
        isEditMode = isEditMode,
        transaction = transaction,
        onSaveTransaction = {
            viewModel.saveTransaction()
            navController.popBackStack()
        },
        onDeleteTransaction = { transaction ->
            viewModel.deleteTransaction(transaction)
        },
        onEditMode = { transaction ->
            viewModel.updateFormData()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun TransactionFormScreenImp(
    uiState: TransactionFormState,
    formData: TransactionFormData,
    navController: NavHostController,
    isEditMode: Boolean,
    transaction: Transaction?,
    onSaveTransaction: () -> Unit,
    onDeleteTransaction: (transaction: Transaction?) -> Unit,
    onEditMode: (transition: Transaction?) -> Unit
) {

    LaunchedEffect(isEditMode) {
        onEditMode(transaction)
    }

    LaunchedEffect(uiState) {
        if (uiState is TransactionFormState.Success) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TransactionFormTopBar(
                uiState = uiState,
                formData = formData,
                navController = navController,
                isEditMode = isEditMode,
                transaction = transaction,
                onSaveTransaction = onSaveTransaction,
                onDeleteTransaction = onDeleteTransaction
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
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
                value = formData.title.value,
                onValueChange = { formData.title.value = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            // Value
            OutlinedTextField(
                value = formData.amount.value.toString(),
                onValueChange = { formData.amount.value = it.toDouble() },
                label = { Text("Value") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // Description
            OutlinedTextField(
                value = formData.description.value,
                onValueChange = { formData.description.value = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            // Icon
            PickerCard(
                label = "Icon",
                onClick = { formData.showIconPicker.value = !formData.showIconPicker.value },
                content = {
                    formData.icon.value.let {
                        MidasIcon(
                            iconType = it,
                            color = formData.color.value
                        )
                    }
                }
            )
            if (formData.showIconPicker.value) {
                IconPickerGrid(
                    selectedIcon = formData.icon.value,
                    selectedColor = formData.color.value,
                    onIconSelected = {
                        formData.icon.value = it!!
                        formData.showIconPicker.value = false
                    }
                )
            }

            // Color
            PickerCard(
                label = "Color",
                onClick = { formData.showColorPicker.value = !formData.showColorPicker.value },
                content = { formData.color.value.let { SelectedColorCircle(it) } }
            )
            if (formData.showColorPicker.value) {
                ColorPickerGrid(
                    selectedColor = formData.color.value,
                    onColorSelected = {
                        formData.color.value = it
                        formData.showColorPicker.value = false
                    }
                )
            }

            // Type
            PickerCard(
                label = "Type",
                onClick = { formData.showTypePicker.value = !formData.showTypePicker.value },
                content = {
                    val selectedText = formData.type.value?.displayName ?: "Select a type"
                    Text(selectedText)
                }
            )
            if (formData.showTypePicker.value) {
                TransactionTypePicker(
                    selected = formData.type.value,
                    onSelected = { type ->
                        formData.type.value = type
                        formData.showTypePicker.value = false
                    }
                )
            }

            // Status
            PickerCard(
                label = "Status",
                onClick = { formData.showStatusPicker.value = !formData.showStatusPicker.value },
                content = {
                    val selectedText = formData.status.value?.displayName ?: "Select a status"
                    Text(selectedText)
                }
            )
            if (formData.showStatusPicker.value) {
                TransactionStatusPicker(
                    selected = formData.status.value,
                    onSelected = {
                        formData.status.value = it
                        formData.showStatusPicker.value = false
                    }
                )
            }

            // Data
            PickerCard(
                label = "Date",
                onClick = { formData.showDatePicker.value = !formData.showDatePicker.value },
                content = {
                    val selectedText = formData.date.value.toString()
                    Text(selectedText)
                }
            )
            if (formData.showDatePicker.value) {
                val currentDate = midasDatePicker()
                if (currentDate.isNotEmpty()) {
                    formData.date.value = LocalDate.parse(currentDate)
                    formData.showDatePicker.value = false
                }
            }

            // Hora
            PickerCard(
                label = "Time",
                onClick = { formData.showTimePicker.value = !formData.showTimePicker.value },
                content = { Text(formData.time.value.formatTime()) }
            )
            if (formData.showTimePicker.value) {
                val currentTime = midasTimePicker()
                if (currentTime.isNotEmpty()) {
                    formData.time.value = currentTime.toKtLocalTime()
                    formData.showTimePicker.value = false
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun TransactionFormTopBar(
    uiState: TransactionFormState,
    formData: TransactionFormData,
    navController: NavHostController,
    isEditMode: Boolean,
    transaction: Transaction?,
    onSaveTransaction: () -> Unit,
    onDeleteTransaction: (transaction: Transaction?) -> Unit,
) {
    TopAppBar(
        title = {
            Text(stringResource(if (isEditMode) R.string.edit_transaction else R.string.new_transaction))
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
            }
        },
        actions = {
            if (formData.showDeleteDialog.value) {
                DeleteDialog(
                    titleItem = formData.title.value,
                    onConfirm = {
                        onDeleteTransaction(transaction)
                        formData.showDeleteDialog.value = false
                    },
                    onDismiss = {
                        formData.showDeleteDialog.value = false
                    })
            }
            if (isEditMode) {
                IconButton(
                    enabled = uiState !is TransactionFormState.Loading,
                    onClick = {
                        formData.showDeleteDialog.value = true
                    }
                ) {
                    Icon(Icons.Default.Delete, stringResource(R.string.delete))
                }
            }
            IconButton(
                enabled = uiState !is TransactionFormState.Loading,
                onClick = {
                    onSaveTransaction()
                }
            ) {
                Icon(Icons.Default.Check, stringResource(R.string.save))
            }
        }
    )
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
                        .clickable {
                            Log.d("TransactionTypePicker", "Selected: ${types[index].name}")
                            onSelected(types[index]) 
                        }
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
            formData = TransactionFormData(),
            navController = rememberNavController(),
            isEditMode = true,
            transaction = Database.transactions.first(),
            onSaveTransaction = {},
            onDeleteTransaction = {},
            onEditMode = {}
        )
    }
}
