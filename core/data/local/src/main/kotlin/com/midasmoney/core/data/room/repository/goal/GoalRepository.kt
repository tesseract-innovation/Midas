package com.midasmoney.core.data.room.repository.goal

import com.midasmoney.core.data.room.dao.GoalDao
import com.midasmoney.core.data.room.dao.IDao
import com.midasmoney.core.data.room.entity.GoalEntity
import com.midasmoney.core.data.room.repository.BaseRepository
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.domain.repository.IGoalRepository
import com.midasmoney.domain.repository.mapper.IEntityMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GoalRepository
    @Inject
    constructor(
        private val goalDao: GoalDao,
    ) : BaseRepository<Goal, GoalEntity>(), IGoalRepository {
        override val dao: IDao<GoalEntity>
            get() = goalDao

        override val entityMapper: IEntityMapper<GoalEntity, Goal>
            get() = GoalEntityMapper

        override val allGoals: Flow<List<Goal>> =
            goalDao.getAllGoals()
                .map { entities -> entities.map { GoalEntityMapper.toDomain(it) } }

        override suspend fun getById(id: String): Goal? {
            return goalDao.getGoalById(id)?.let { GoalEntityMapper.toDomain(it) }
        }

        override fun observeById(goalId: String): Flow<Goal?> =
            goalDao.observeGoalById(goalId)
                .map { entity -> entity?.let { GoalEntityMapper.toDomain(it) } }

        override suspend fun getAll(): Flow<List<Goal>> = allGoals

        override suspend fun deleteGoalById(goalId: String): Result<Unit> {
            return withContext(Dispatchers.IO) {
                try {
                    goalDao.deleteGoalById(goalId)
                    Result.success(Unit)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        }
    }
