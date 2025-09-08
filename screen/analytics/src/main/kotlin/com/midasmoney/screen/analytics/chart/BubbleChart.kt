package com.midasmoney.screen.analytics.chart

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.bubblechart.BubbleChart
import co.yml.charts.ui.bubblechart.model.BubbleChartData
import co.yml.charts.ui.linechart.model.GridLines
import com.midasmoney.shared.ui.core.component.MidasDarkPreview
import com.midasmoney.shared.ui.core.component.MidasLightPreview

@Composable
fun AnalyticsBubbleChart(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 100.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(content = {
                item {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Gradient bubble chart",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    BubbleChartWithGrid(
                        pointsData = DataUtils.getRandomPoints(
                            200,
                            start = 30,
                            maxRange = 100
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Solid bubble chart",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    SolidBubbleChart(
                        pointsData = DataUtils.getRandomPoints(
                            200,
                            start = 30,
                            maxRange = 900
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

            })
        }
    }
}

/**
 * Bubble chart with grid lines
 *
 * @param pointsData
 */
@Composable
private fun BubbleChartWithGrid(pointsData: List<Point>) {
    val steps = 5
    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(pointsData.size - 1)
        .labelData { i -> pointsData[i].x.toInt().toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelAndAxisLinePadding(15.dp)
        .labelData { i ->
            // Add yMin to get the negative axis values to the scale
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
            ((i * yScale) + yMin).formatToSinglePrecision()
        }.build()

    val data = BubbleChartData(
        DataUtils.getBubbleChartDataWithGradientStyle(pointsData),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines()
    )

    BubbleChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        bubbleChartData = data
    )

}


/**
 * Bubble chart with grid lines
 *
 * @param pointsData
 */
@Composable
private fun SolidBubbleChart(pointsData: List<Point>) {
    val steps = 5
    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(pointsData.size - 1)
        .labelData { i -> pointsData[i].x.toInt().toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelAndAxisLinePadding(15.dp)
        .labelData { i ->
            // Add yMin to get the negative axis values to the scale
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
            ((i * yScale) + yMin).formatToSinglePrecision()
        }.build()

    val data = BubbleChartData(
        DataUtils.getBubbleChartDataWithSolidStyle(pointsData),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines()
    )

    BubbleChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        bubbleChartData = data
    )

}


@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun AnalyticsBubbleChartScreenLightPreview() {
    val navController = rememberNavController()
    MidasLightPreview {
        AnalyticsBubbleChart(navController)
    }
}

@Preview(showBackground = true, backgroundColor = Color.BLACK.toLong())
@Composable
fun AnalyticsBubbleChartScreenDarkPreview() {
    val navController = rememberNavController()
    MidasDarkPreview {
        AnalyticsBubbleChart(navController)
    }
}