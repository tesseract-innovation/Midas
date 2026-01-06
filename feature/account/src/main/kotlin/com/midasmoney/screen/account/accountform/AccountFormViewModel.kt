package com.midasmoney.screen.account.accountform

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.domain.repository.IAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
    val initialBalance: Double = 0.0
)

@Suppress("unused")
@HiltViewModel
class AccountFormViewModel @Inject constructor(
    private val repository: IAccountRepository
) : ViewModel() {
    private val _formState = MutableStateFlow<AccountFormState>(AccountFormState.Idle)
    val formState: StateFlow<AccountFormState> = _formState.asStateFlow()

    private val _formData = MutableStateFlow(AccountFormData())
    val formData: StateFlow<AccountFormData> = _formData.asStateFlow()

    fun updateFormData(formData: AccountFormData) {
        _formData.value = formData
    }

    fun createAccount(account: Account) {
        viewModelScope.launch((Dispatchers.IO)) {
            _formState.value = AccountFormState.Loading
            repository.insert(account)
                .onSuccess {
                    Log.d(TAG, "Account created successfully")
                    _formState.value = AccountFormState.Success
                }
                .onFailure { e ->
                    Log.e(TAG, "Failed to create account", e)
                    _formState.value = AccountFormState.Error(
                        e.message ?: "Failed to create account"
                    )
                }
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch {
            _formState.value = AccountFormState.Loading
            repository.update(account)
                .onSuccess {
                    Log.d(TAG, "Account updated successfully")
                    _formState.value = AccountFormState.Success
                    resetForm()
                }
                .onFailure { e ->
                    Log.e(TAG, "Failed to update account", e)
                    _formState.value = AccountFormState.Error(
                        e.message ?: "Failed to update account"
                    )
                }
        }
    }

    fun resetForm() {
        _formData.value = AccountFormData()
        _formState.value = AccountFormState.Idle
    }

    fun resetFormState() {
        _formState.value = AccountFormState.Idle
    }

    suspend fun getAccountById(accountId: String): Account? {
        return repository.getById(accountId)
    }

    fun validateForm(formData: AccountFormData): String? {
        return when {
            formData.name.isBlank() -> "Account name is required"
            formData.icon == null -> "Please select an icon"
            formData.color == null -> "Please select a color"
            else -> null
        }
    }

    companion object {
        private val TAG = AccountFormViewModel::class.simpleName
    }
}
