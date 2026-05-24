package com.midasmoney.screen.goals

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.util.serializer.serializableNavType
import com.midasmoney.screen.goals.goaldetail.GoalDetailScreen
import com.midasmoney.screen.goals.goalform.GoalFormScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
sealed class GoalsRoute(val route: String) {
    @Serializable
    data object Main : GoalsRoute("Goals")

    @Serializable
    data class GoalForm(val goal: Goal? = null) : GoalsRoute("Goal_form")

    @Serializable
    data class GoalDetail(val goal: Goal) : GoalsRoute("Goal_detail")
}

@Composable
fun GoalsNavGraph(
    navController: NavHostController,
    shouldShowBottomBar: MutableState<Boolean>,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = GoalsRoute.Main
    ) {
        composable<GoalsRoute.Main> {
            shouldShowBottomBar.value = true
            GoalsContentImp(
                navController = navController,
                paddingValues = paddingValues
            )
        }

        composable<GoalsRoute.GoalForm>(
            typeMap = mapOf(typeOf<Goal?>() to serializableNavType<Goal?>(isNullableAllowed = true))
        ) {
            val args = it.toRoute<GoalsRoute.GoalForm>()
            shouldShowBottomBar.value = false
            GoalFormScreen(
                args = args,
                navController = navController
            )
        }

        composable<GoalsRoute.GoalDetail>(
            typeMap = mapOf(typeOf<Goal>() to serializableNavType<Goal>())
        ) {
            val args = it.toRoute<GoalsRoute.GoalDetail>()
            shouldShowBottomBar.value = false
            GoalDetailScreen(
                args = args,
                navController = navController
            )
        }
    }
}
