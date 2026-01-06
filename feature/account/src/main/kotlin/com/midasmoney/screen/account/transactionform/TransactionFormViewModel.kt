package com.midasmoney.screen.account.transactionform

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.domain.repository.ITransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TransactionFormState {
    object Idle : TransactionFormState()
    object Loading : TransactionFormState()
    object Success : TransactionFormState()
    data class Error(val message: String) : TransactionFormState()
}

@Suppress("unused")
@HiltViewModel
class TransactionFormViewModel @Inject constructor(
    private val repository: ITransactionRepository
) : ViewModel() {
    private val _formState = MutableStateFlow<TransactionFormState>(TransactionFormState.Idle)
    val formState: StateFlow<TransactionFormState> = _formState.asStateFlow()

    fun createTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            _formState.value = TransactionFormState.Loading
            repository.insert(transaction)
                .onSuccess {
                    Log.d(TAG, "Transaction created successfully")
                    _formState.value = TransactionFormState.Success
                }
                .onFailure {
                    Log.d(TAG, "Failed to create transaction")
                    _formState.value =
                        TransactionFormState.Error(it.message ?: "Failed to create transaction")
                }
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            _formState.value = TransactionFormState.Loading
            repository.update(transaction)
                .onSuccess {
                    Log.d(TAG, "Transaction updated successfully")
                    _formState.value = TransactionFormState.Success
                }
                .onFailure {
                    Log.d(TAG, "Failed to update transaction")
                    _formState.value =
                        TransactionFormState.Error(it.message ?: "Failed to update transaction")
                }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            _formState.value = TransactionFormState.Loading
            repository.delete(transaction)
                .onSuccess {
                    Log.d(TAG, "Transaction deleted successfully")
                    _formState.value = TransactionFormState.Success
                }
                .onFailure {
                    Log.d(TAG, "Failed to delete transaction")
                    _formState.value =
                        TransactionFormState.Error(it.message ?: "Failed to delete transaction")
                }
        }
    }

    companion object {
        private val TAG = TransactionFormViewModel::class.simpleName
    }
}
