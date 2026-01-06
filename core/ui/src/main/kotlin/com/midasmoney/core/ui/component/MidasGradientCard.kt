package com.midasmoney.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.midasmoney.core.ui.preview.MidasDarkPreview
import com.midasmoney.core.ui.preview.MidasLightPreview

@Composable
fun MidasGradientCard(
    primaryColor: Color,
    secondaryColor: Color,
    height: Dp,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    compose: @Composable () -> Unit
) {
    val gradientColors = if (isDarkTheme) {
        listOf(
            primaryColor.copy(alpha = 0.7f),
            secondaryColor.copy(alpha = 0.5f)
        )
    } else {
        listOf(
            primaryColor,
            secondaryColor
        )
    }
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 5.dp)
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
    ) {
        Column {
            compose()
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun MidasGradientCardLightPreview() {
    MidasLightPreview {
        MidasGradientCard(
            primaryColor = Color.Blue,
            secondaryColor = Color.Red,
            height = 140.dp
        ) {}
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun MidasGradientCardDarkPreview() {
    MidasDarkPreview {
        MidasGradientCard(
            primaryColor = Color.Blue,
            secondaryColor = Color.Red,
            isDarkTheme = true,
            height = 140.dp
        ) {}
    }
}
