package com.midasmoney.screen.account.accountdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.domain.repository.IAccountRepository
import com.midasmoney.domain.repository.ITransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AccountDetailState {
    object Idle : AccountDetailState()
    object Loading : AccountDetailState()
    object Success : AccountDetailState()
    data class Error(val message: String) : AccountDetailState()
}

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val accountRepository: IAccountRepository,
    private val transactionRepository: ITransactionRepository
) : ViewModel() {
    private val _accountDetailState = MutableStateFlow<AccountDetailState>(AccountDetailState.Idle)
    val accountDetailState: MutableStateFlow<AccountDetailState> = _accountDetailState

    private val _transactions = MutableStateFlow<List<Transaction>>(mutableListOf())
    val transactions: MutableStateFlow<List<Transaction>> = _transactions

    private val _totalBalance = MutableStateFlow<Double>(0.0)
    val totalBalance: MutableStateFlow<Double> = _totalBalance

    private val _totalIncome = MutableStateFlow<Double>(0.0)
    val totalIncome: MutableStateFlow<Double> = _totalIncome

    private val _totalExpense = MutableStateFlow<Double>(0.0)
    val totalExpense: MutableStateFlow<Double> = _totalExpense

    fun loadTransactions(accountId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _accountDetailState.value = AccountDetailState.Loading
            transactionRepository.getTransactionForAccount(accountId).collect {
                _transactions.value = it
                _accountDetailState.value = AccountDetailState.Success
                Log.d(TAG, "Transactions loaded successfully")
            }
        }
    }

    fun updateTotalBalance(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            _totalBalance.value =
                transactionRepository.getTotalAmountForAccount(account.id.toString())
            _totalIncome.value = transactionRepository.getTotalIncome(account.id.toString())
            _totalExpense.value = transactionRepository.getTotalExpense(account.id.toString())
            account.balance = account.balance.copy(
                totalValue = _totalBalance.value,
                expense = _totalIncome.value,
                income = _totalExpense.value
            )
            accountRepository.update(account)
        }
    }

    companion object {
        private val TAG = AccountDetailViewModel::class.simpleName
    }
}
