package com.midasmoney.screen.analytics

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
import com.midasmoney.core.data.model.TransactionType
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.ui.color.MidasColors
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview
import com.midasmoney.core.ui.component.MidasTitleItem

@Composable
fun AnalyticsContentImp(navController: NavController) {
    AnalyticsContent(navController)
}

@Composable
fun AnalyticsContent(
    navController: NavController,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    var selectedChip by remember { mutableStateOf<String?>("7D") }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 33.dp, bottom = 100.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 19.dp)
                ) {
                    val chips = listOf("7D", "1M", "3M", "6M", "1Y")
                    chips.forEach { chipText ->
                        FilterChip(
                            selected = (selectedChip == chipText),
                            onClick = { selectedChip = chipText },
                            label = { Text(text = chipText) }
                        )
                    }
                }
                TotalBalanceCard(isDarkTheme)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 16.dp, start = 19.dp, end = 19.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        TransactionCard(
                            transactionReport = Database.transactionReportIncome
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        TransactionCard(
                            transactionReport = Database.transactionReportExpense
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    MidasTitleItem("Incomes by category")
                }
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    val slices = mutableListOf<PieChartData.Slice>()
                    slices.add(PieChartData.Slice("Salary", 100f, MidasColors.Purple.primary))
                    slices.add(PieChartData.Slice("Freelance", 654f, MidasColors.DarkGray))
                    slices.add(PieChartData.Slice("Investments", 123f, MidasColors.Red.light))
                    val pieChartData = PieChartData(slices, PlotType.Pie)
                    SimplePieChart(LocalContext.current, pieChartData, isDarkTheme)
                }
                Row {
                    MidasTitleItem("Expenses by category")
                }
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    val slices = mutableListOf<PieChartData.Slice>()
                    slices.add(
                        PieChartData.Slice(
                            "Entertainment",
                            234f,
                            MidasColors.Orange.primary
                        )
                    )
                    slices.add(PieChartData.Slice("Dining Out", 543f, MidasColors.Green.primary))
                    slices.add(PieChartData.Slice("Investments", 123f, MidasColors.Red.light))
                    slices.add(PieChartData.Slice("Gifts", 345f, MidasColors.Green.light))
                    slices.add(PieChartData.Slice("Transportation", 687f, MidasColors.Gray))
                    val pieChartData = PieChartData(slices, PlotType.Pie)
                    SimplePieChart(LocalContext.current, pieChartData, isDarkTheme)
                }

                Row {
                    MidasTitleItem("Expenses")
                }
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
                    SingleLineChartWithGridLines(points, TransactionType.EXPENSE, isDarkTheme)
                }
                Row {
                    MidasTitleItem("Incomes")
                }
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    val points = mutableListOf<Point>()
                    points.add(Point(x = 1f, y = 9348f))
                    points.add(Point(x = 2f, y = 1292f))
                    points.add(Point(x = 3f, y = 3243f))
                    points.add(Point(x = 4f, y = 5433f))
                    points.add(Point(x = 5f, y = 2893f))
                    points.add(Point(x = 6f, y = 3284f))
                    points.add(Point(x = 7f, y = 1293f))
                    SingleLineChartWithGridLines(points, TransactionType.INCOME, isDarkTheme)
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
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val pieChartConfig =
        PieChartConfig(
            labelVisible = true,
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
            isAnimationEnable = true,
            chartPadding = 30,
            backgroundColor = if (isDarkTheme) Color.Black else Color.White,
            showSliceLabels = false,
            animationDuration = 1500
        )
    Column(modifier = Modifier.height(500.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Legends(legendsConfig = DataUtils.getLegendsConfigFromPieChartData(pieChartData, 3))
        PieChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            pieChartData,
            pieChartConfig
        ) { slice ->
            Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * Single line chart with grid lines
 *
 * @param pointsData
 */
@Composable
private fun SingleLineChartWithGridLines(
    pointsData: List<Point>,
    type: TransactionType,
    isDarkTheme: Boolean
) {
    val steps = 10
    val xAxisData = AxisData.Builder()
        .axisLabelColor(if(isDarkTheme) Color.White else Color.Black)
        .axisStepSize(50.dp)
        .topPadding(105.dp)
        .steps(pointsData.size - 1)
        .labelData { i -> pointsData[i].x.toInt().toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()
    val yAxisData = AxisData.Builder()
        .axisLabelColor(if(isDarkTheme) Color.White else Color.Black)
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
        backgroundColor = if (isDarkTheme) Color.Black else Color.White
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = data
    )
}


@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun AnalyticsScreenLightPreview() {
    val navController = rememberNavController()
    MidasLightPreview {
        AnalyticsContent(
            navController
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun AnalyticsScreenDarkPreview() {
    val navController = rememberNavController()
    MidasDarkPreview {
        AnalyticsContent(
            navController = navController,
            isDarkTheme = true
        )
    }
}