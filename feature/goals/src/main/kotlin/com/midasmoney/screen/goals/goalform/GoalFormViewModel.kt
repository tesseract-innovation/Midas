package com.midasmoney.screen.goals.goalform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.domain.repository.IGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

sealed class GoalFormState {
    object Idle : GoalFormState()

    object Loading : GoalFormState()

    object Success : GoalFormState()

    data class Error(val message: String) : GoalFormState()
}

data class GoalFormData(
    val title: String = "",
    val description: String = "",
    val amount: Double = 0.0,
    val monthlyValue: Double = 0.0,
    val icon: IconModel? = null,
    val color: Int? = null,
    val targetDate: LocalDate? = null,
)

@HiltViewModel
class GoalFormViewModel
    @Inject
    constructor(
        private val repository: IGoalRepository,
    ) : ViewModel() {
        private val _formState = MutableStateFlow<GoalFormState>(GoalFormState.Idle)
        val formState: StateFlow<GoalFormState> = _formState.asStateFlow()

        private val _formData = MutableStateFlow(GoalFormData())
        val formData: StateFlow<GoalFormData> = _formData.asStateFlow()

        fun updateFormData(formData: GoalFormData) {
            _formData.value = formData
        }

        fun createGoal(goal: Goal) {
            viewModelScope.launch(Dispatchers.IO) {
                _formState.value = GoalFormState.Loading
                repository.insert(goal)
                    .onSuccess { _formState.value = GoalFormState.Success }
                    .onFailure { e ->
                        _formState.value = GoalFormState.Error(e.message ?: "Failed to create goal")
                    }
            }
        }

        fun updateGoal(goal: Goal) {
            viewModelScope.launch(Dispatchers.IO) {
                _formState.value = GoalFormState.Loading
                repository.update(goal)
                    .onSuccess {
                        _formState.value = GoalFormState.Success
                        resetForm()
                    }
                    .onFailure { e ->
                        _formState.value = GoalFormState.Error(e.message ?: "Failed to update goal")
                    }
            }
        }

        fun resetFormState() {
            _formState.value = GoalFormState.Idle
        }

        fun resetForm() {
            _formData.value = GoalFormData()
            _formState.value = GoalFormState.Idle
        }

        fun validateForm(formData: GoalFormData): String? {
            return when {
                formData.title.isBlank() -> "Goal title is required"
                formData.amount <= 0 -> "Target amount must be greater than zero"
                formData.icon == null -> "Please select an icon"
                formData.color == null -> "Please select a color"
                formData.targetDate == null -> "Please select a target date"
                else -> null
            }
        }
    }
