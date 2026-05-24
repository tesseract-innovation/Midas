package com.midasmoney.core.data.room.repository.goalcontribution

import com.midasmoney.core.data.room.entity.GoalContributionEntity
import com.midasmoney.core.domain.model.GoalContribution
import com.midasmoney.core.util.UUID
import com.midasmoney.domain.repository.mapper.IGoalContributionEntityMapper
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
object GoalContributionEntityMapper : IGoalContributionEntityMapper<GoalContributionEntity> {
    override fun toDomain(entity: GoalContributionEntity): GoalContribution {
        return entity.run {
            GoalContribution(
                id = UUID(id),
                goalId = UUID(goalId),
                amount = amount,
                date = Instant.fromEpochMilliseconds(date),
            )
        }
    }

    override fun toEntity(domain: GoalContribution): GoalContributionEntity {
        return domain.run {
            GoalContributionEntity(
                id = id.toString(),
                goalId = goalId.toString(),
                amount = amount,
                date = date.toEpochMilliseconds(),
            )
        }
    }
}
