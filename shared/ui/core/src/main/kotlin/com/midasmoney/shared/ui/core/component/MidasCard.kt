package com.midasmoney.shared.ui.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MidasCard(
    modifier: Modifier,
    compose: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)),
        modifier = Modifier

    ) {
        Column(
            modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
        ) {
            compose()
        }
    }
}

@Preview
@Composable
fun MidasCardLightPreview() {
    MidasLightPreview {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            {}
        )
    }
}

@Preview
@Composable
fun MidasCardDarkPreview() {
    MidasDarkPreview {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            {}
        )
    }
}