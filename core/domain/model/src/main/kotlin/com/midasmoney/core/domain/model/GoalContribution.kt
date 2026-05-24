package com.midasmoney.core.domain.model

import com.midasmoney.core.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
@OptIn(ExperimentalTime::class)
data class GoalContribution(
    val goalId: UUID,
    val amount: Double,
    @Contextual val date: Instant,
    val id: UUID = UUID.randomUUID(),
)
