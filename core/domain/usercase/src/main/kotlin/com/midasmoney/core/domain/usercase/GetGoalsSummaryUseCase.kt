package com.midasmoney.core.domain.usercase

import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.GoalsSummary
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * Derives a [GoalsSummary] from the user's goals for the balance status card.
 *
 * A goal is considered active while its [Goal.progress] is below its [Goal.amount].
 * The completion percentage is the weighted ratio of saved-to-target across all goals,
 * guarding against division by zero when there is nothing targeted.
 */
class GetGoalsSummaryUseCase @Inject constructor() {

    operator fun invoke(goals: List<Goal>): GoalsSummary {
        if (goals.isEmpty()) return GoalsSummary.EMPTY

        val totalSaved = goals.sumOf { it.progress }
        val totalTarget = goals.sumOf { it.amount }
        val completionPercent = if (totalTarget > 0.0) {
            ((totalSaved / totalTarget) * 100).roundToInt().coerceIn(0, 100)
        } else {
            0
        }
        val activeGoalsCount = goals.count { it.progress < it.amount }

        return GoalsSummary(
            totalSaved = totalSaved,
            completionPercent = completionPercent,
            activeGoalsCount = activeGoalsCount,
        )
    }
}
