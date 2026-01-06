package com.midasmoney.screen.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.midasmoney.screen.analytics.chart.AnalyticsBarchart
import com.midasmoney.screen.analytics.chart.AnalyticsBubbleChart
import com.midasmoney.screen.analytics.chart.AnalyticsCombinedChart
import com.midasmoney.screen.analytics.chart.AnalyticsDonutChart
import com.midasmoney.screen.analytics.chart.AnalyticsLineChart
import com.midasmoney.screen.analytics.chart.AnalyticsPieChart
import com.midasmoney.screen.analytics.chart.AnalyticsWaveChart

sealed class AnalyticsRoute(val route: String) {
    data object Main : AnalyticsRoute("Analytics")
    data object BarChart : AnalyticsRoute("Bar_chart")
    data object BubbleChart : AnalyticsRoute("Bubble_chart")
    data object CombinedChart : AnalyticsRoute("Combined_chart")
    data object DonutChart : AnalyticsRoute("Donut_chart")
    data object LineChart : AnalyticsRoute("Line_chart")
    data object PieChart : AnalyticsRoute("Pie_chart")
    data object WaveChart : AnalyticsRoute("Wave_chart")
}

@Composable
fun AnalyticsNavGraph(
    navController: NavHostController,
    shouldShowBottomBar: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = AnalyticsRoute.Main.route
    ) {
        composable(AnalyticsRoute.Main.route) {
            shouldShowBottomBar.value = true
            AnalyticsContentImp()
        }
        composable(AnalyticsRoute.BarChart.route) {
            shouldShowBottomBar.value = false
            AnalyticsBarchart(navController)
        }
        composable(AnalyticsRoute.BubbleChart.route) {
            shouldShowBottomBar.value = false
            AnalyticsBubbleChart()
        }
        composable(AnalyticsRoute.CombinedChart.route) {
            shouldShowBottomBar.value = false
            AnalyticsCombinedChart()
        }
        composable(AnalyticsRoute.DonutChart.route) {
            shouldShowBottomBar.value = false
            AnalyticsDonutChart()
        }
        composable(AnalyticsRoute.LineChart.route) {
            shouldShowBottomBar.value = false
            AnalyticsLineChart()
        }
        composable(AnalyticsRoute.PieChart.route) {
            shouldShowBottomBar.value = false
            AnalyticsPieChart()
        }
        composable(AnalyticsRoute.WaveChart.route) {
            shouldShowBottomBar.value = false
            AnalyticsWaveChart()
        }
    }
}
