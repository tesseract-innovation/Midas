package com.midasmoney.screen.account.accountdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.domain.repository.IAccountRepository
import com.midasmoney.domain.repository.ITransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AccountDetailState {
    object Idle : AccountDetailState()

    object Loading : AccountDetailState()

    object Success : AccountDetailState()

    data class Error(val message: String) : AccountDetailState()
}

@HiltViewModel
class AccountDetailViewModel
    @Inject
    constructor(
        private val accountRepository: IAccountRepository,
        private val transactionRepository: ITransactionRepository,
    ) : ViewModel() {
        private val _accountDetailState = MutableStateFlow<AccountDetailState>(AccountDetailState.Idle)
        val accountDetailState: StateFlow<AccountDetailState> = _accountDetailState.asStateFlow()

        private val _transactions = MutableStateFlow<List<Transaction>>(mutableListOf())
        val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

        private val _totalBalance = MutableStateFlow(0.0)
        val totalBalance: StateFlow<Double> = _totalBalance.asStateFlow()

        private val _income = MutableStateFlow(0.0)
        val income: StateFlow<Double> = _income.asStateFlow()

        private val _expense = MutableStateFlow(0.0)
        val expense: StateFlow<Double> = _expense.asStateFlow()

        fun loadTransactions(accountId: String) {
            viewModelScope.launch(Dispatchers.IO) {
                _transactions.value = transactionRepository.getTransactionForAccount(accountId).first()
                val account = accountRepository.getById(accountId)
                _totalBalance.value = account?.balance?.currentBalance ?: 0.0
                _income.value = account?.balance?.income ?: 0.0
                _expense.value = account?.balance?.expense ?: 0.0
            }
        }

        fun deleteAccount(accountId: String) {
            if (_accountDetailState.value is AccountDetailState.Loading || _accountDetailState.value is AccountDetailState.Success) return
            _accountDetailState.value = AccountDetailState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                accountRepository.deleteAccountById(accountId)
                    .onSuccess {
                        _accountDetailState.value = AccountDetailState.Success
                    }
                    .onFailure { e ->
                        _accountDetailState.value =
                            AccountDetailState.Error(e.message ?: "Failed to delete account")
                    }
            }
        }

        companion object {
            private val TAG = AccountDetailViewModel::class.simpleName
        }
    }
