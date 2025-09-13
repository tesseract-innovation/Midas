package com.midasmoney.screen.analytics.chart

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.common.components.Legends
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview

@Composable
fun AnalyticsPieChart() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 100.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(content = {
                items(3) { item ->
                    when (item) {
                        0 -> {
                            Text(
                                modifier = Modifier.padding(12.dp),
                                text = "Simple piechart",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            SimplePiechart(LocalContext.current)
                        }

                        1 -> {
                            Text(
                                modifier = Modifier.padding(12.dp),
                                text = "Piechart with slice lables",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            PiechartWithSliceLables(LocalContext.current)
                        }
                    }
                }
            })
        }
    }
}

/**
 * Simple piechart
 *
 * @param context
 */
@Composable
private fun SimplePiechart(context: Context) {
    val pieChartData = DataUtils.getPieChartData()
    val pieChartConfig =
        PieChartConfig(
            labelVisible = true,
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
            isAnimationEnable = true,
            chartPadding = 30,
            backgroundColor = Color.Black,
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
 * Piechart with slice lables
 *
 * @param context
 */
@Composable
private fun PiechartWithSliceLables(context: Context) {
    val pieChartData = DataUtils.getPieChartData2()

    val pieChartConfig =
        PieChartConfig(
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
            sliceLabelTypeface = Typeface.defaultFromStyle(Typeface.ITALIC),
            isAnimationEnable = true,
            chartPadding = 20,
            showSliceLabels = true,
            labelVisible = true
        )
    Column(modifier = Modifier.height(500.dp)) {
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

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun AnalyticsPieChartScreenLightPreview() {
//    val navController = rememberNavController()
    MidasLightPreview {
        AnalyticsPieChart(
            //navController
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun AnalyticsPieChartScreenDarkPreview() {
//    val navController = rememberNavController()
    MidasDarkPreview {
        AnalyticsPieChart(
            //navController = navController,
            //tisDarkTheme = true
        )
    }
}