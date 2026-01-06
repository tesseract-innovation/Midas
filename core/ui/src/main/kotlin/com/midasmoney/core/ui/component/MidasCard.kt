package com.midasmoney.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasTheme

@Composable
fun MidasCard(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, getBorderColor()),
        modifier = Modifier

    ) {
        Column(
            modifier = modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            content()
        }
    }
}

@Composable
private fun getBorderColor(): Color {
    return if (isSystemInDarkTheme()){
        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    }
}

@CustomPreview
@Composable
fun MidasCardPreview() {
    MidasTheme {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            {}
        )
    }
}
