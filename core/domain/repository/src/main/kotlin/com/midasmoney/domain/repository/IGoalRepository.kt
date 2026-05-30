package com.midasmoney.domain.repository

import com.midasmoney.core.domain.model.Goal
import kotlinx.coroutines.flow.Flow

interface IGoalRepository : IRepository<Goal> {
    val allGoals: Flow<List<Goal>>

    fun observeById(goalId: String): Flow<Goal?>

    suspend fun deleteGoalById(goalId: String): Result<Unit>
}
