package com.midasmoney.core.domain.usercase

import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class GetGoalsSummaryUseCaseTest {
    private val useCase = GetGoalsSummaryUseCase()

    private fun goal(
        progress: Double,
        amount: Double,
    ) = Goal(
        title = "title",
        description = "description",
        amount = amount,
        progress = progress,
        icon = IconModel(IconType.MONEY),
        color = 0,
        targetDate = LocalDate(2026, 12, 31),
        monthlyValue = 0.0,
    )

    @Test
    fun `empty list returns EMPTY summary`() {
        val result = useCase(emptyList())

        assertEquals(0.0, result.totalSaved, 0.0)
        assertEquals(0, result.completionPercent)
        assertEquals(0, result.activeGoalsCount)
    }

    @Test
    fun `sums progress and counts active goals`() {
        val goals =
            listOf(
                goal(progress = 250.0, amount = 1000.0),
                goal(progress = 500.0, amount = 1000.0),
                goal(progress = 1000.0, amount = 1000.0), // completed
            )

        val result = useCase(goals)

        assertEquals(1750.0, result.totalSaved, 0.0)
        assertEquals(2, result.activeGoalsCount)
    }

    @Test
    fun `completion percent is weighted ratio rounded`() {
        val goals =
            listOf(
                goal(progress = 780.0, amount = 1000.0),
            )

        assertEquals(78, useCase(goals).completionPercent)
    }

    @Test
    fun `zero target does not divide by zero`() {
        val goals = listOf(goal(progress = 0.0, amount = 0.0))

        assertEquals(0, useCase(goals).completionPercent)
    }

    @Test
    fun `completion percent never exceeds one hundred`() {
        val goals = listOf(goal(progress = 1500.0, amount = 1000.0))

        assertEquals(100, useCase(goals).completionPercent)
    }
}
