package com.midasmoney.core.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.midasmoney.core.data.room.entity.GoalContributionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalContributionDao : IDao<GoalContributionEntity> {
    @Query("SELECT * FROM goal_contribution WHERE goalId = :goalId ORDER BY date DESC")
    fun getByGoalId(goalId: String): Flow<List<GoalContributionEntity>>

    @Query("DELETE FROM goal_contribution WHERE goalId = :goalId")
    suspend fun deleteByGoalId(goalId: String)
}
