package com.midasmoney.screen.analytics.chart

import android.content.Context
import android.graphics.Typeface
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.components.Legends
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import com.midasmoney.shared.ui.core.component.MidasDarkPreview
import com.midasmoney.shared.ui.core.component.MidasLightPreview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnalyticsDonutChart() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
        })
    {
        val context = LocalContext.current
        LazyColumn(content = {
            items(2) { item ->
                when (item) {
                    0 -> {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = "Simple donut chart",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            SimpleDonutChart(context)
                        }
                    }

                    1 -> {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = "Multiple donuts",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        MultipleSmallDonutCharts(context)
                    }
                }
            }
        })
    }
}

/**
 * Simple donut chart
 *
 * @param context
 */
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterialApi
@Composable
private fun SimpleDonutChart(context: Context) {
//    val accessibilitySheetState =
//        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
//    val scope = rememberCoroutineScope()
    val data = DataUtils.getDonutChartData()
    // Sum of all the values
//    val sumOfValues = data.totalLength

    // Calculate each proportion value
//    val proportions = data.slices.proportion(sumOfValues)
    val pieChartConfig =
        PieChartConfig(
            labelVisible = true,
            strokeWidth = 120f,
            labelColor = Color.Black,
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
            isAnimationEnable = true,
            chartPadding = 25,
            labelFontSize = 42.sp,
            isSumVisible = true,
        )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        Legends(legendsConfig = DataUtils.getLegendsConfigFromPieChartData(pieChartData = data, 3))
        DonutPieChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            data,
            pieChartConfig
        ) { slice ->
            Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * Multiple small donut charts
 *
 * @param context
 */
@ExperimentalMaterialApi
@Composable
private fun MultipleSmallDonutCharts(context: Context) {
//    val accessibilitySheetState =
//        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
//    val scope = rememberCoroutineScope()
    val data = DataUtils.getDonutChartData()
    // Sum of all the values
//    val sumOfValues = data.totalLength

    // Calculate each proportion value
//    val proportions = data.slices.proportion(sumOfValues)
    val firstPieChartConfig =
        PieChartConfig(
            labelVisible = true,
            strokeWidth = 50f,
            labelColor = Color.Black,
            backgroundColor = Color.Yellow,
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
            isAnimationEnable = true,
            chartPadding = 25,
            labelFontSize = 16.sp
        )
    val secondPieChartConfig =
        PieChartConfig(
            labelVisible = true,
            strokeWidth = 50f,
            labelColor = Color.Black,
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            backgroundColor = Color.Black,
            labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
            isAnimationEnable = true,
            chartPadding = 25,
            labelFontSize = 16.sp,
            isSumVisible = true,
            sumUnit = "unit",
            labelColorType = PieChartConfig.LabelColorType.SLICE_COLOR,
            labelType = PieChartConfig.LabelType.VALUE
        )
    val thirdPieChartConfig =
        PieChartConfig(
            labelVisible = true,
            strokeWidth = 50f,
            labelColor = Color.Black,
            activeSliceAlpha = .9f,
            backgroundColor = Color.LightGray,
            isEllipsizeEnabled = true,
            labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
            isAnimationEnable = true,
            chartPadding = 25,
            labelFontSize = 16.sp
        )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Legends(legendsConfig = DataUtils.getLegendsConfigFromPieChartData(pieChartData = data, 3))
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                DonutPieChart(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    data,
                    firstPieChartConfig
                ) { slice ->
                    Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
                }
                Spacer(modifier = Modifier.width(30.dp))
            }
            item {
                DonutPieChart(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    data,
                    secondPieChartConfig
                ) { slice ->
                    Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
                }
                Spacer(modifier = Modifier.width(30.dp))
            }
            item {
                DonutPieChart(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    data,
                    thirdPieChartConfig
                ) { slice ->
                    Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
                }
                Spacer(modifier = Modifier.width(30.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun AnalyticsDonutChartScreenLightPreview() {
//    val navController = rememberNavController()
    MidasLightPreview {
        AnalyticsDonutChart(
            //navController
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun AnalyticsDonutChartScreenDarkPreview() {
//    val navController = rememberNavController()
    MidasDarkPreview {
        AnalyticsDonutChart(
            //navController = navController,
            //tisDarkTheme = true
        )
    }
}