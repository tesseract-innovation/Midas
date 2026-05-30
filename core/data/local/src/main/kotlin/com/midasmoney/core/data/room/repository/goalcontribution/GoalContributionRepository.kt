package com.midasmoney.core.data.room.repository.goalcontribution

import com.midasmoney.core.data.room.dao.GoalContributionDao
import com.midasmoney.core.data.room.dao.IDao
import com.midasmoney.core.data.room.entity.GoalContributionEntity
import com.midasmoney.core.data.room.repository.BaseRepository
import com.midasmoney.core.domain.model.GoalContribution
import com.midasmoney.domain.repository.IGoalContributionRepository
import com.midasmoney.domain.repository.mapper.IEntityMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GoalContributionRepository
    @Inject
    constructor(
        private val contributionDao: GoalContributionDao,
    ) : BaseRepository<GoalContribution, GoalContributionEntity>(), IGoalContributionRepository {
        override val dao: IDao<GoalContributionEntity>
            get() = contributionDao

        override val entityMapper: IEntityMapper<GoalContributionEntity, GoalContribution>
            get() = GoalContributionEntityMapper

        override fun getByGoalId(goalId: String): Flow<List<GoalContribution>> =
            contributionDao.getByGoalId(goalId)
                .map { entities -> entities.map { GoalContributionEntityMapper.toDomain(it) } }

        override suspend fun getById(id: String): GoalContribution? = null

        override suspend fun getAll(): Flow<List<GoalContribution>> {
            throw UnsupportedOperationException("Use getByGoalId(goalId) instead")
        }

        override suspend fun deleteByGoalId(goalId: String): Result<Unit> {
            return withContext(Dispatchers.IO) {
                try {
                    contributionDao.deleteByGoalId(goalId)
                    Result.success(Unit)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        }
    }
