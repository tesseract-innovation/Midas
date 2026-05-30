package com.midasmoney.screen.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.GoalsSummary
import com.midasmoney.core.domain.usercase.GetGoalsSummaryUseCase
import com.midasmoney.domain.repository.IGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class GoalsUiState {
    object Loading : GoalsUiState()

    data class Success(
        val goals: List<Goal>,
        val summary: GoalsSummary,
    ) : GoalsUiState()

    data class Error(val message: String) : GoalsUiState()
}

@HiltViewModel
class GoalsViewModel
    @Inject
    constructor(
        private val repository: IGoalRepository,
        private val getGoalsSummary: GetGoalsSummaryUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<GoalsUiState>(GoalsUiState.Loading)
        val uiState: StateFlow<GoalsUiState> = _uiState.asStateFlow()

        init {
            loadGoals()
        }

        fun loadGoals() {
            viewModelScope.launch(Dispatchers.IO) {
                repository.allGoals
                    .catch { e ->
                        _uiState.value = GoalsUiState.Error(e.message ?: "Failed to load goals")
                    }
                    .collect { goals ->
                        _uiState.value =
                            GoalsUiState.Success(
                                goals = goals,
                                summary = getGoalsSummary(goals),
                            )
                    }
            }
        }

        fun deleteGoal(goal: Goal) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.delete(goal)
                    .onFailure { e ->
                        _uiState.value = GoalsUiState.Error(e.message ?: "Failed to delete goal")
                    }
            }
        }
    }
