package com.midasmoney.screen.home

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.resource.R.string.financial_goals
import com.midasmoney.core.resource.R.string.manage
import com.midasmoney.core.resource.R.string.recent_transactions
import com.midasmoney.core.resource.R.string.view_all
import com.midasmoney.core.ui.component.GoalCard
import com.midasmoney.core.ui.component.MidasTitleItem
import com.midasmoney.core.ui.component.TransactionCard
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

@Composable
fun HomeContentImp(navController: NavHostController, paddingValues: PaddingValues) {
    HomeContent(navController, paddingValues)
}

@Composable
fun HomeContent(
    navController: NavController,
    paddingValues: PaddingValues,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    BalanceStatus(
                        balance = Database.balance,
                        isDarkTheme = isDarkTheme
                    )
                }
                item {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 19.dp, end = 19.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            TransactionOverviewCard(
                                transactionReport = Database.transactionReportIncome
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.1f))
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            TransactionOverviewCard(
                                transactionReport = Database.transactionReportExpense
                            )
                        }
                    }

                }
                item {
                    MidasTitleItem(
                        textTitle = stringResource(recent_transactions),
                        textButton = stringResource(view_all),
                        actionButton = { navController.navigate(HomeRoute.History.route) }
                    )
                }
                item {
                    TransactionCard(
                        transaction = Database.transactions[0],
                    )
                }
                item {
                    TransactionCard(
                        transaction = Database.transactions[1],
                    )
                }
                item {
                    MidasTitleItem(
                        textTitle = stringResource(financial_goals),
                        textButton = stringResource(manage),
                        actionButton = { navController.navigate(HomeRoute.Goals.route) }
                    )
                }
                item {
                    GoalCard(
                        goal = Database.goalList[0]
                    )
                }
                item {
                    GoalCard(
                        goal = Database.goalList[3]
                    )
                }
                item {
                    GoalCard(
                        goal = Database.goalList[4]
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .padding(top = 13.dp)
                    ) {
                        MidasTitleItem("Expenses")
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    ) {


                        SimplePieChart(LocalContext.current, getPieChartData())
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        LegendCircleChart(getPieChartData()
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        val points = mutableListOf<Point>()
                        points.add(Point(x = 1f, y = 534f))
                        points.add(Point(x = 2f, y = 432f))
                        points.add(Point(x = 3f, y = 323f))
                        points.add(Point(x = 4f, y = 654f))
                        points.add(Point(x = 5f, y = 898f))
                        points.add(Point(x = 6f, y = 932f))
                        points.add(Point(x = 7f, y = 123f))
                        SingleLineChartWithGridLines(
                            pointsData = points,
                            isDarkTheme = isDarkTheme
                        )
                    }
                }
            }
        }
    }
}

/**
 * Simple pie chart
 *
 * @param context
 */
@Composable
private fun SimplePieChart(
    context: Context,
    pieChartData: PieChartData,
) {
    val pieChartConfig =
        PieChartConfig(
            labelVisible = true,
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
            isAnimationEnable = true,
            chartPadding = 30,
            backgroundColor = MaterialTheme.colorScheme.surface,
            showSliceLabels = true,
            animationDuration = 1500
        )
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(Color.Red)
    ) {
        PieChart(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            pieChartData,
            pieChartConfig
        ) { slice ->
            Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun LegendCircleChart(
    pieChartData: PieChartData,
    gridSize: Int = 3
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp),
        contentAlignment = Alignment.Center
    ) {
//        Spacer(modifier = Modifier.height(20.dp))
        Legends(legendsConfig = DataUtils.getLegendsConfigFromPieChartData(pieChartData, gridSize))
    }
}

/**
 * Single line chart with grid lines
 *
 * @param pointsData
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun SingleLineChartWithGridLines(
    pointsData: List<Point>,
    type: TransactionType = TransactionType.EXPENSE,
    isDarkTheme: Boolean
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val axisStepSize = if (screenWidth < 600) {
        50.dp // Celulares
    } else if (screenWidth < 840) {
        100.dp // Tablets pequenos
    } else {
        150.dp // Telas grandes
    }
    val steps = 10
    val xAxisData = AxisData.Builder()
        .axisLabelColor(if (isDarkTheme) Color.White else Color.Black)
        .axisStepSize(axisStepSize)
        .topPadding(105.dp)
        .steps(pointsData.size - 1)
        .labelData { i -> pointsData[i].x.toInt().toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()
    val yAxisData = AxisData.Builder()
        .axisLabelColor(if (isDarkTheme) Color.White else Color.Black)
        .steps(steps)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            // Add yMin to get the negative axis values to the scale
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
            ((i * yScale) + yMin).formatToSinglePrecision()
        }.build()
    val data = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = if (type == TransactionType.INCOME) MidasColors.Green.primary else MidasColors.Red.primary,
                    ),
                    IntersectionPoint(
                        color = if (type == TransactionType.INCOME) MidasColors.Green.extraDark else MidasColors.Red.extraDark
                    ),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(
                        color = if (type == TransactionType.INCOME) MidasColors.Green.light else MidasColors.Red.light
                    ),
                    SelectionHighlightPopUp()
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = MaterialTheme.colorScheme.surface
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = data
    )
}

private fun getPieChartData(): PieChartData {
    val slices = mutableListOf<PieChartData.Slice>()
    slices.add(
        PieChartData.Slice(
            "Entertainment",
            234f,
            MidasColors.Orange.primary
        )
    )
    slices.add(
        PieChartData.Slice(
            "Dining Out",
            543f,
            MidasColors.Green.primary
        )
    )
    slices.add(PieChartData.Slice("Investments", 123f, MidasColors.Red.light))
    slices.add(PieChartData.Slice("Gifts", 345f, MidasColors.Green.light))
    slices.add(PieChartData.Slice("Transportation", 687f, MidasColors.Gray))
    return PieChartData(slices, PlotType.Pie)
}

@CustomPreview
@Composable
fun HomeContentImpLightPreview() {
    MidasTheme {
        HomeContent(
            navController = rememberNavController(),
            paddingValues = PaddingValues()
        )
    }
}
