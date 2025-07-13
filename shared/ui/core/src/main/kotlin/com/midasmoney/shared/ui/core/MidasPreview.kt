package com.midasmoney.shared.ui.core

import androidx.compose.runtime.Composable

enum class Theme{
    DARK,
    LIGHT
}

@Composable
fun MidasPreview(theme: Theme = Theme.LIGHT, content: @Composable () -> Unit){
    MidasTheme(
        isTrueBlack = true,
        dark = theme == Theme.DARK
    ) {
        content()
    }
}