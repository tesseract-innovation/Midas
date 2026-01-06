package com.midasmoney.screen.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Account
import com.midasmoney.domain.repository.IAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AccountUiState {
    object Loading : AccountUiState()
    data class Success(val accounts: List<Account>) : AccountUiState()
    data class Error(val message: String) : AccountUiState()
}

@Suppress("unused")
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: IAccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    init {
        loadAccounts()
    }

    fun loadAccounts() {
        Log.d(TAG, "loadAccounts() called")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "viewModelScope.launch() called")
            repository.allAccounts
                .catch { e ->
                    Log.d(TAG, "catch() called, Error account state")
                    _uiState.value = AccountUiState.Error(
                        e.message ?: "Failed to load accounts"
                    )
                }
                .collect { accounts ->
                    Log.d(TAG, "collect() called, Success account state")
                    _uiState.value = AccountUiState.Success(accounts)
                }
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(account)
                .onSuccess {
                    // Account deleted successfully
                    Log.d(TAG, "Account deleted successfully")
                }
                .onFailure { e ->
                    _uiState.value = AccountUiState.Error(
                        e.message ?: "Failed to delete account"
                    )
                }
        }
    }

    fun deleteAccountById(accountId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAccountById(accountId)
                .onSuccess {
                    // Account deleted successfully
                    Log.d(TAG, "Account deleted successfully")
                }
                .onFailure { e ->
                    _uiState.value = AccountUiState.Error(
                        e.message ?: "Failed to delete account"
                    )
                }
        }
    }

    companion object {
        private val TAG = AccountViewModel::class.simpleName
    }
}
