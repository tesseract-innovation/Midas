package com.midasmoney.shared.ui.core.component

import androidx.compose.runtime.Composable
import com.midasmoney.shared.ui.core.color.MidasTheme

enum class Theme {
    DARK,
    LIGHT
}

@Composable
fun MidasPreview(theme: Theme = Theme.LIGHT, content: @Composable () -> Unit) {
    MidasTheme(
        isTrueBlack = true,
        dark = theme == Theme.DARK
    ) {
        content()
    }
}

@Suppress("unused")
@Composable
fun MidasDarkPreview(content: @Composable () -> Unit) {
    MidasPreview(
        theme = Theme.DARK
    ) {
        content()
    }
}

@Suppress("unused")
@Composable
fun MidasLightPreview(content: @Composable () -> Unit) {
    MidasPreview(
        theme = Theme.LIGHT
    ) {
        content()
    }
}