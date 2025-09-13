package com.midasmoney.screen.analytics.chart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.models.BarPlotData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.combinedchart.CombinedChart
import co.yml.charts.ui.combinedchart.model.CombinedChartData
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview

@Composable
fun AnalyticsCombinedChart() {
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
                items(2) { item ->
                    when (item) {
                        0 -> {
                            Text(
                                modifier = Modifier.padding(12.dp),
                                text = "Combined bar line chart",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            BarWithLineChart()
                        }

                        1 -> {
                            Text(
                                modifier = Modifier.padding(12.dp),
                                text = "Combined bar line chart with background",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            BarWithLineChartAndBackground()
                        }
                    }
                }
            })
        }
    }
}

/**
 * Bar with line chart
 *
 */
@Composable
fun BarWithLineChart() {
    val maxRange = 100
    val groupBarData = DataUtils.getGroupBarChartData(50, 100, 3)
    val yStepSize = 10
    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .bottomPadding(5.dp)
        .labelData { index -> index.toString() }
        .build()
    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (maxRange / yStepSize)).toString() }
        .build()
    val linePlotData = LinePlotData(
        lines = listOf(
            Line(
                DataUtils.getLineChartData(50, maxRange = 100),
                lineStyle = LineStyle(color = Color.Blue),
                intersectionPoint = IntersectionPoint(),
                selectionHighlightPoint = SelectionHighlightPoint(),
                selectionHighlightPopUp = SelectionHighlightPopUp()
            ),
            Line(
                DataUtils.getLineChartData(50, maxRange = 100),
                lineStyle = LineStyle(color = Color.Black),
                intersectionPoint = IntersectionPoint(),
                selectionHighlightPoint = SelectionHighlightPoint(),
                selectionHighlightPopUp = SelectionHighlightPopUp()
            )
        )
    )
    val colorPaletteList = DataUtils.getColorPaletteList(3)
    val legendsConfig = LegendsConfig(
        legendLabelList = DataUtils.getLegendsLabelData(colorPaletteList),
        gridColumnCount = 3
    )
    val barPlotData = BarPlotData(
        groupBarList = groupBarData,
        barStyle = BarStyle(barWidth = 35.dp),
        barColorPaletteList = colorPaletteList
    )
    val combinedChartData = CombinedChartData(
        combinedPlotDataList = listOf(barPlotData, linePlotData),
        xAxisData = xAxisData,
        yAxisData = yAxisData
    )
    Column(
        Modifier
            .height(500.dp)
    ) {
        CombinedChart(
            modifier = Modifier
                .height(400.dp),
            combinedChartData = combinedChartData
        )
        Legends(
            legendsConfig = legendsConfig
        )
    }
}


/**
 * Bar with line chart and background
 *
 */
@Composable
fun BarWithLineChartAndBackground() {
    val maxRange = 100
    val groupBarData = DataUtils.getGroupBarChartData(50, 100, 3)
    val yStepSize = 10
    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .bottomPadding(5.dp)
//        .backgroundColor(Color.Yellow)
        .labelData { index -> index.toString() }
        .build()
    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
//        .backgroundColor(Color.Yellow)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (maxRange / yStepSize)).toString() }
        .build()
    val linePlotData = LinePlotData(
        lines = listOf(
            Line(
                DataUtils.getLineChartData(50, maxRange = 100),
                lineStyle = LineStyle(color = Color.Blue),
                intersectionPoint = IntersectionPoint(),
                selectionHighlightPoint = SelectionHighlightPoint(),
                selectionHighlightPopUp = SelectionHighlightPopUp()
            ),
            Line(
                DataUtils.getLineChartData(50, maxRange = 100),
                lineStyle = LineStyle(color = Color.Black),
                intersectionPoint = IntersectionPoint(),
                selectionHighlightPoint = SelectionHighlightPoint(),
                selectionHighlightPopUp = SelectionHighlightPopUp()
            )
        )
    )
    val colorPaletteList = DataUtils.getColorPaletteList(3)
    val legendsConfig = LegendsConfig(
        legendLabelList = DataUtils.getLegendsLabelData(colorPaletteList),
        gridColumnCount = 3
    )
    val barPlotData = BarPlotData(
        groupBarList = groupBarData,
        barStyle = BarStyle(barWidth = 35.dp),
        barColorPaletteList = colorPaletteList
    )
    val combinedChartData = CombinedChartData(
        combinedPlotDataList = listOf(barPlotData, linePlotData),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
//        backgroundColor = Color.Yellow
    )
    Column(
        Modifier
            .height(500.dp)
    ) {
        CombinedChart(
            modifier = Modifier
                .height(400.dp),
            combinedChartData = combinedChartData
        )
        Legends(
            legendsConfig = legendsConfig
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun AnalyticsCombinedChartScreenLightPreview() {
    val navController = rememberNavController()
    MidasLightPreview {
        AnalyticsCombinedChart(
            //navController
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun AnalyticsCombinedChartScreenDarkPreview() {
    val navController = rememberNavController()
    MidasDarkPreview {
        AnalyticsCombinedChart(
            //navController = navController,
            //isDarkTheme = true
        )
    }
}