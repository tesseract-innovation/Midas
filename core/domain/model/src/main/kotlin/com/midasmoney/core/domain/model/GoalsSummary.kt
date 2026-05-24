package com.midasmoney.core.domain.model

import kotlinx.serialization.Serializable

/**
 * Aggregated view of the user's goals, shown in the balance status card.
 *
 * @param totalSaved sum of [Goal.progress] across all goals.
 * @param completionPercent overall progress (0..100); 0 when there is nothing to complete.
 * @param activeGoalsCount number of goals whose progress has not yet reached their target.
 */
@Serializable
data class GoalsSummary(
    val totalSaved: Double,
    val completionPercent: Int,
    val activeGoalsCount: Int,
) {
    companion object {
        val EMPTY = GoalsSummary(totalSaved = 0.0, completionPercent = 0, activeGoalsCount = 0)
    }
}
