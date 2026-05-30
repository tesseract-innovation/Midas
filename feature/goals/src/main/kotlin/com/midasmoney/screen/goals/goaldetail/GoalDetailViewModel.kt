package com.midasmoney.screen.goals.goaldetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.GoalContribution
import com.midasmoney.core.util.UUID
import com.midasmoney.domain.repository.IGoalContributionRepository
import com.midasmoney.domain.repository.IGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

sealed class GoalDetailState {
    object Idle : GoalDetailState()

    object Loading : GoalDetailState()

    object ContributeSuccess : GoalDetailState()

    data class Error(val message: String) : GoalDetailState()
}

@HiltViewModel
@OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
class GoalDetailViewModel
    @Inject
    constructor(
        private val repository: IGoalRepository,
        private val contributionRepository: IGoalContributionRepository,
    ) : ViewModel() {
        private val _state = MutableStateFlow<GoalDetailState>(GoalDetailState.Idle)
        val state: StateFlow<GoalDetailState> = _state.asStateFlow()

        private val activeGoalId = MutableStateFlow<String?>(null)

        val goal: StateFlow<Goal?> =
            activeGoalId
                .flatMapLatest { id ->
                    if (id == null) flowOf(null) else repository.observeById(id)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = null,
                )

        val contributions: StateFlow<List<GoalContribution>> =
            activeGoalId
                .flatMapLatest { id ->
                    if (id == null) flowOf(emptyList()) else contributionRepository.getByGoalId(id)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = emptyList(),
                )

        fun loadContributions(goalId: String) {
            activeGoalId.value = goalId
        }

        fun contribute(
            goal: Goal,
            amount: Double,
        ) {
            if (amount <= 0) {
                _state.value = GoalDetailState.Error("Amount must be greater than zero")
                return
            }
            viewModelScope.launch(Dispatchers.IO) {
                _state.value = GoalDetailState.Loading
                val updated = goal.copy(progress = (goal.progress + amount).coerceAtMost(goal.amount))
                repository.update(updated)
                    .onSuccess {
                        val contribution =
                            GoalContribution(
                                goalId = UUID(goal.id.toString()),
                                amount = amount,
                                date = Clock.System.now(),
                            )
                        contributionRepository.insert(contribution)
                        _state.value = GoalDetailState.ContributeSuccess
                    }
                    .onFailure { e ->
                        _state.value = GoalDetailState.Error(e.message ?: "Failed to update goal")
                    }
            }
        }

        fun resetState() {
            _state.value = GoalDetailState.Idle
        }
    }
