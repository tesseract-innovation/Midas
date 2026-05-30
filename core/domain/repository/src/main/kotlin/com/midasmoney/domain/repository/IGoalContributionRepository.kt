package com.midasmoney.domain.repository

import com.midasmoney.core.domain.model.GoalContribution
import kotlinx.coroutines.flow.Flow

interface IGoalContributionRepository : IRepository<GoalContribution> {
    fun getByGoalId(goalId: String): Flow<List<GoalContribution>>

    suspend fun deleteByGoalId(goalId: String): Result<Unit>
}
