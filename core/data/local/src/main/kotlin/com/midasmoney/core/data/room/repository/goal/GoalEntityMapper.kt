package com.midasmoney.core.data.room.repository.goal

import com.midasmoney.core.data.room.entity.GoalEntity
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import com.midasmoney.core.util.UUID
import com.midasmoney.domain.repository.mapper.IGoalEntityMapper
import kotlinx.datetime.LocalDate

object GoalEntityMapper : IGoalEntityMapper<GoalEntity> {
    override fun toDomain(entity: GoalEntity): Goal {
        return entity.run {
            Goal(
                id = UUID(id),
                title = title,
                description = description,
                amount = amount,
                progress = progress,
                icon = IconModel(IconType.valueOf(icon)),
                color = color,
                targetDate = LocalDate.parse(targetDate),
                monthlyValue = monthlyValue,
            )
        }
    }

    override fun toEntity(domain: Goal): GoalEntity {
        return domain.run {
            GoalEntity(
                id = id.toString(),
                title = title,
                description = description,
                amount = amount,
                progress = progress,
                icon = icon.iconType.name,
                color = color,
                targetDate = targetDate.toString(),
                monthlyValue = monthlyValue,
            )
        }
    }
}
