package com.midasmoney.screen.account.transactionform

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.TransactionStatus
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.extension.getCurrentLocalDate
import com.midasmoney.core.domain.model.extension.getCurrentTime
import com.midasmoney.core.domain.model.extension.toKtLocalTime
import com.midasmoney.core.domain.model.extension.toLocalDate
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.util.UUID
import com.midasmoney.domain.repository.IAccountRepository
import com.midasmoney.domain.repository.ITransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import javax.inject.Inject
import kotlin.math.abs
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

sealed class TransactionFormState {
    object Idle : TransactionFormState()
    object Loading : TransactionFormState()
    object Success : TransactionFormState()
    data class Error(val message: String) : TransactionFormState()
}

@OptIn(ExperimentalTime::class)
data class TransactionFormData(
    var title: MutableState<String> = mutableStateOf(""),
    var description: MutableState<String> = mutableStateOf(""),
    var icon: MutableState<IconType> = mutableStateOf(IconType.CREDIT_CARD),
    var color: MutableState<Color> = mutableStateOf(MidasColors.Green.dark),
    var amount: MutableState<Double> = mutableDoubleStateOf(0.0),
    var type: MutableState<TransactionType?> = mutableStateOf(null),
    var status: MutableState<TransactionStatus?> = mutableStateOf(null),
    var date: MutableState<LocalDate> = mutableStateOf(Clock.System.getCurrentLocalDate()),
    var time: MutableState<LocalTime> = mutableStateOf(Clock.System.getCurrentTime()),
    var showTypePicker: MutableState<Boolean> = mutableStateOf(false),
    var showStatusPicker: MutableState<Boolean> = mutableStateOf(false),
    var showDatePicker: MutableState<Boolean> = mutableStateOf(false),
    var showTimePicker: MutableState<Boolean> = mutableStateOf(false),
    var showIconPicker: MutableState<Boolean> = mutableStateOf(false),
    var showColorPicker: MutableState<Boolean> = mutableStateOf(false),
    var showDeleteDialog: MutableState<Boolean> = mutableStateOf(false)
)

@Suppress("unused")
@HiltViewModel
class TransactionFormViewModel @Inject constructor(
    private val transactionRepository: ITransactionRepository,
    private val accountRepository: IAccountRepository
) : ViewModel() {
    private val _formState = MutableStateFlow<TransactionFormState>(TransactionFormState.Idle)
    val formState: StateFlow<TransactionFormState> = _formState.asStateFlow()

    private val _formData = MutableStateFlow(TransactionFormData())
    val formData: StateFlow<TransactionFormData> = _formData.asStateFlow()

    private val _account = MutableStateFlow<Account?>(null)
    val account: StateFlow<Account?> = _account.asStateFlow()

    private val _transaction = MutableStateFlow<Transaction?>(null)
    val transaction: StateFlow<Transaction?> = _transaction.asStateFlow()

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode: StateFlow<Boolean> = _isEditMode.asStateFlow()

    fun initArgs(account: Account?, transaction: Transaction?) {
        _account.value = account
        _transaction.value = transaction
        _isEditMode.value = _transaction.value != null
    }

    fun createTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            _formState.value = TransactionFormState.Loading
            transactionRepository.insert(transaction)
                .onSuccess {
                    Log.d(TAG, "Transaction created successfully")
                    _formState.value = TransactionFormState.Success
                    updateTotalBalance(transaction.accountId.toString())
                }
                .onFailure {
                    Log.d(TAG, "Failed to create transaction")
                    it.printStackTrace()
                    _formState.value =
                        TransactionFormState.Error(it.message ?: "Failed to create transaction")
                }
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            _formState.value = TransactionFormState.Loading
            transactionRepository.update(transaction)
                .onSuccess {
                    Log.d(TAG, "Transaction updated successfully")
                    _formState.value = TransactionFormState.Success
                    updateTotalBalance(transaction.accountId.toString())
                }
                .onFailure {
                    Log.d(TAG, "Failed to update transaction")
                    it.printStackTrace()
                    _formState.value =
                        TransactionFormState.Error(it.message ?: "Failed to update transaction")
                }
        }
    }

    fun deleteTransaction(transaction: Transaction?) {
        viewModelScope.launch(Dispatchers.IO) {
            _formState.value = TransactionFormState.Loading
            transactionRepository.delete(transaction!!)
                .onSuccess {
                    Log.d(TAG, "Transaction deleted successfully")
                    _formState.value = TransactionFormState.Success
                    updateTotalBalance(transaction.accountId.toString())
                }
                .onFailure {
                    Log.d(TAG, "Failed to delete transaction")
                    it.printStackTrace()
                    _formState.value =
                        TransactionFormState.Error(it.message ?: "Failed to delete transaction")
                }
        }
    }

    fun updateTotalBalance(accountId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val transactionBalance = transactionRepository.getTotalAmountForAccount(accountId)
            val income = transactionRepository.getTotalIncome(accountId)
            val expense = transactionRepository.getTotalExpense(accountId)
            val accountFlowFirst = accountRepository.getAccountByIdFlow(accountId).first()
            accountFlowFirst?.let { account ->
                val newBalance = account.balance.initialBalance + transactionBalance
                if (abs(account.balance.currentBalance - newBalance) > 0.000001) {
                    account.balance = account.balance.copy(
                        currentBalance = newBalance,
                        income = income,
                        expense = expense
                    )
                    accountRepository.update(account)
                    Log.d(
                        TAG, "Balance updated. initial=${account.balance.initialBalance} +" +
                                "income=$income expense=$expense current=$newBalance"
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun saveTransaction() {
        var newTransaction = Transaction(
            id = UUID.randomUUID(),
            accountId = UUID.randomUUID(),
            icon = IconModel(formData.value.icon.value),
            color = ColorConverter.colorToArgb(formData.value.color.value),
            title = formData.value.title.value,
            description = formData.value.description.value,
            amount = formData.value.amount.value,
            type = formData.value.type.value ?: TransactionType.TRANSFER,
            status = formData.value.status.value ?: TransactionStatus.PENDING,
            date = formData.value.date.value.toString(),
            time = formData.value.time.value.toString(),
            createAt = Clock.System.now(),
        )
        if (isEditMode.value) {
            transaction.value?.let {
                newTransaction = newTransaction.copy(
                    id = it.id,
                    accountId = it.accountId,
                    createAt = it.createAt
                )
            }
            updateTransaction(newTransaction)
        } else {
            account.value?.let {
                newTransaction = newTransaction.copy(
                    accountId = it.id
                )
            }
            createTransaction(newTransaction)
        }
    }

    fun updateFormData() {
        transaction.value?.run {
            TransactionFormData(
                title = mutableStateOf(this.title),
                description = mutableStateOf(this.description),
                amount = mutableDoubleStateOf(this.amount),
                icon = mutableStateOf(this.icon.iconType),
                type = mutableStateOf(this.type),
                status = mutableStateOf(this.status),
                color = mutableStateOf(ColorConverter.aRgbToColor(this.color)),
                date = mutableStateOf(this.date.toLocalDate()),
                time = mutableStateOf(this.time.toKtLocalTime())
            ).also {
                _formData.value = it
            }
        }
    }

    companion object {
        private val TAG = TransactionFormViewModel::class.simpleName
    }
}
