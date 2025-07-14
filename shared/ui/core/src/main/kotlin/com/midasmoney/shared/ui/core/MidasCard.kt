package com.midasmoney.shared.ui.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MidasCard(
    modifier: Modifier,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    compose: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = if (isSystemInDarkTheme()) BorderStroke(
            1.dp,
            MidasColors.DarkGray
        ) else BorderStroke(1.dp, MidasColors.ExtraLightGray),
        modifier = Modifier

    ) {
        Column(
            modifier = modifier
                .background(color = if (isDarkTheme) MidasColors.ExtraDarkGray else MidasColors.White)
        ) {
            compose()
        }
    }
}

@Preview
@Composable
fun MidasCardLightPreview() {
    MidasPreview(
        theme = Theme.LIGHT
    ) {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            isDarkTheme = false,
            {}
        )
    }
}

@Preview
@Composable
fun MidasCardDarkPreview() {
    MidasPreview(
        theme = Theme.DARK
    ) {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            isDarkTheme = true,
            {}
        )
    }
}