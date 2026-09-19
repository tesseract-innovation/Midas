package com.midasmoney.screen.account.accountform

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.AccountType
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.TransactionStatus
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.domain.model.extension.getCurrentLocalDate
import com.midasmoney.core.domain.model.extension.getCurrentTime
import com.midasmoney.core.util.UUID
import com.midasmoney.domain.repository.IAccountRepository
import com.midasmoney.domain.repository.ITransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

sealed class AccountFormState {
    object Idle : AccountFormState()

    object Loading : AccountFormState()

    object Success : AccountFormState()

    data class Error(val message: String) : AccountFormState()
}

data class AccountFormData(
    val name: String = "",
    val icon: IconModel? = null,
    val color: Int? = null,
    val initialBalance: Double = 0.0,
    val type: AccountType = AccountType.CHECKING,
    val creditLimit: Double? = null,
    val dueDay: Int? = null,
)

@Suppress("unused")
@HiltViewModel
class AccountFormViewModel
    @Inject
    constructor(
        private val accountRepository: IAccountRepository,
        private val transactionRepository: ITransactionRepository,
    ) : ViewModel() {
        private val _formState = MutableStateFlow<AccountFormState>(AccountFormState.Idle)
        val formState: StateFlow<AccountFormState> = _formState.asStateFlow()

        private val _formData = MutableStateFlow(AccountFormData())
        val formData: StateFlow<AccountFormData> = _formData.asStateFlow()

        fun updateFormData(formData: AccountFormData) {
            _formData.value = formData
        }

        // Guards against duplicate inserts/updates from a fast double-tap: the Room
        // write can complete (Loading -> Success) before Compose disables the button
        // or navigates away, so this flag - unlike formState - is never reset on success.
        private var hasSubmitted = false

        fun createAccount(account: Account) {
            if (hasSubmitted) return
            hasSubmitted = true
            _formState.value = AccountFormState.Loading
            viewModelScope.launch((Dispatchers.IO)) {
                val initialAmount = account.balance.initialBalance
                // The opening amount is recorded as its own transaction below, so the
                // account's initialBalance offset must be zero to avoid double-counting
                // it when balances are recalculated from the transaction history.
                val accountToPersist =
                    if (initialAmount != 0.0) {
                        account.copy(balance = account.balance.copy(initialBalance = 0.0))
                    } else {
                        account
                    }
                accountRepository.insert(accountToPersist)
                    .onSuccess {
                        if (initialAmount != 0.0) {
                            createBalanceTransaction(
                                account = accountToPersist,
                                amount = initialAmount,
                                title = "Initial Balance",
                                description = "Account opening balance",
                                type = TransactionType.INITIAL_BALANCE,
                            )
                        }
                        Log.d(TAG, "Account created successfully")
                        _formState.value = AccountFormState.Success
                    }
                    .onFailure { e ->
                        Log.e(TAG, "Failed to create account", e)
                        hasSubmitted = false
                        _formState.value =
                            AccountFormState.Error(
                                e.message ?: "Failed to create account",
                            )
                    }
            }
        }

        fun updateAccount(
            account: Account,
            newBalance: Double,
        ) {
            if (hasSubmitted) return
            hasSubmitted = true
            _formState.value = AccountFormState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                accountRepository.update(account)
                    .onSuccess {
                        val delta = newBalance - account.balance.currentBalance
                        if (delta != 0.0) {
                            createBalanceTransaction(
                                account = account,
                                amount = delta,
                                title = "Balance Adjustment",
                                description = "Manual balance correction",
                                type = TransactionType.BALANCE_ADJUSTMENT,
                            )
                        }
                        Log.d(TAG, "Account updated successfully")
                        _formState.value = AccountFormState.Success
                    }
                    .onFailure { e ->
                        Log.e(TAG, "Failed to update account", e)
                        hasSubmitted = false
                        _formState.value =
                            AccountFormState.Error(
                                e.message ?: "Failed to update account",
                            )
                    }
            }
        }

        // Records an initial-balance or balance-adjustment amount as its own
        // transaction, then recomputes and persists the account's balance/income/
        // expense from the full transaction ledger so they stay consistent with it.
        @OptIn(ExperimentalTime::class)
        private suspend fun createBalanceTransaction(
            account: Account,
            amount: Double,
            title: String,
            description: String,
            type: TransactionType,
        ) {
            val transaction =
                Transaction(
                    id = UUID.randomUUID(),
                    accountId = account.id,
                    icon = account.icon,
                    color = account.color,
                    title = title,
                    description = description,
                    type = type,
                    status = TransactionStatus.COMPLETED,
                    amount = amount,
                    date = Clock.System.getCurrentLocalDate().toString(),
                    time = Clock.System.getCurrentTime().toString(),
                    createAt = Clock.System.now(),
                )
            transactionRepository.insert(transaction)
                .onFailure { e -> Log.e(TAG, "Failed to create $title transaction", e) }

            val accountId = account.id.toString()
            val currentBalance = transactionRepository.getTotalAmountForAccount(accountId)
            val income = transactionRepository.getTotalIncome(accountId)
            val expense = transactionRepository.getTotalExpense(accountId)
            accountRepository.update(
                account.copy(
                    balance =
                        account.balance.copy(
                            currentBalance = currentBalance,
                            income = income,
                            expense = expense,
                        ),
                ),
            )
        }

        fun resetFormState() {
            _formState.value = AccountFormState.Idle
        }

        suspend fun getAccountById(accountId: String): Account? {
            return accountRepository.getById(accountId)
        }

        fun validateForm(formData: AccountFormData): String? {
            return when {
                formData.name.isBlank() -> "Account name is required"
                formData.icon == null -> "Please select an icon"
                formData.color == null -> "Please select a color"
                formData.type == AccountType.CREDIT_CARD && (formData.creditLimit == null || formData.creditLimit <= 0.0) ->
                    "Please enter a valid credit limit"
                formData.type == AccountType.CREDIT_CARD && (formData.dueDay == null || formData.dueDay !in 1..31) ->
                    "Please enter a valid due day (1-31)"
                else -> null
            }
        }

        companion object {
            private val TAG = AccountFormViewModel::class.simpleName
        }
    }
