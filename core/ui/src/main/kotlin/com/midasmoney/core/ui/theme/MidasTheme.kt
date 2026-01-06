package com.midasmoney.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun MidasTheme(
    isTrueBlack: Boolean = true,
    dark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val colorScheme =
                if (!dark) midasDarkColorScheme(isTrueBlack) else midasLightColorScheme()
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = !dark
        }
    }
    MaterialTheme(
        colorScheme = if (dark) midasDarkColorScheme(isTrueBlack) else midasLightColorScheme(),
        content = content,
    )
}

private fun midasLightColorScheme(): ColorScheme = ColorScheme(
    primary = MidasColors.Green.primary,
    onPrimary = MidasColors.White,
    primaryContainer = MidasColors.Purple.dark,
    onPrimaryContainer = MidasColors.White,
    inversePrimary = MidasColors.Purple.dark,
    secondary = MidasColors.Green.dark,
    onSecondary = MidasColors.White,
    secondaryContainer = MidasColors.Green.light,
    onSecondaryContainer = MidasColors.White,
    tertiary = MidasColors.Green.primary,
    onTertiary = MidasColors.White,
    tertiaryContainer = MidasColors.Green.light,
    onTertiaryContainer = MidasColors.White,
    background = MidasColors.White,
    onBackground = MidasColors.Black,
    surface = MidasColors.White,
    onSurface = MidasColors.Black,
    surfaceVariant = MidasColors.ExtraLightGray,
    onSurfaceVariant = MidasColors.Black,
    surfaceTint = MidasColors.Black,
    inverseSurface = MidasColors.DarkGray,
    inverseOnSurface = MidasColors.White,
    error = MidasColors.Red.primary,
    onError = MidasColors.White,
    errorContainer = MidasColors.Red.light,
    onErrorContainer = MidasColors.White,
    outline = MidasColors.Gray,
    outlineVariant = MidasColors.DarkGray,
    scrim = MidasColors.ExtraDarkGray.copy(alpha = 0.8f),
    surfaceBright = MidasColors.White,              // 0xFFFAFAFC - mais brilhante
    surfaceDim = MidasColors.ExtraLightGray,        // 0xFFEBEBF0 - mais escuro
    surfaceContainer = MidasColors.ExtraLightGray,  // 0xFFEBEBF0 - container padrão
    surfaceContainerHigh = MidasColors.LightGray,   // 0xFFCBCBD6 - elevação alta
    surfaceContainerHighest = MidasColors.Gray,     // 0xFF74747A - elevação máxima
    surfaceContainerLow = MidasColors.White,        // 0xFFFAFAFC - elevação baixa
    surfaceContainerLowest = MidasColors.White,     // 0xFFFAFAFC - elevação mínima
)

private fun midasDarkColorScheme(isTrueBlack: Boolean): ColorScheme = ColorScheme(
    primary = MidasColors.Green.primary,
    onPrimary = MidasColors.White,
    primaryContainer = MidasColors.Purple.dark,
    onPrimaryContainer = MidasColors.White,
    inversePrimary = MidasColors.Purple.dark,
    secondary = MidasColors.Green.extraLight,
    onSecondary = MidasColors.White,
    secondaryContainer = MidasColors.Green.dark,
    onSecondaryContainer = MidasColors.White,
    tertiary = MidasColors.Green.primary,
    onTertiary = MidasColors.White,
    tertiaryContainer = MidasColors.Green.light,
    onTertiaryContainer = MidasColors.White,
    background = if (isTrueBlack) MidasColors.TrueBlack else MidasColors.Black,
    onBackground = MidasColors.White,
    surface = if (isTrueBlack) MidasColors.TrueBlack else MidasColors.Black,
    onSurface = MidasColors.White,
    surfaceVariant = MidasColors.ExtraDarkGray,
    onSurfaceVariant = MidasColors.White,
    surfaceTint = MidasColors.White,
    inverseSurface = MidasColors.LightGray,
    inverseOnSurface = if (isTrueBlack) MidasColors.TrueBlack else MidasColors.Black,
    error = MidasColors.Red.primary,
    onError = MidasColors.White,
    errorContainer = MidasColors.Red.light,
    onErrorContainer = MidasColors.White,
    outline = MidasColors.Gray,
    outlineVariant = MidasColors.LightGray,
    scrim = MidasColors.ExtraLightGray.copy(alpha = 0.8f),
    surfaceBright = MidasColors.DarkGray,           // 0xFF303033 - mais brilhante
    surfaceDim = MidasColors.Black,                 // 0xFF09090A - mais escuro
    surfaceContainer = MidasColors.ExtraDarkGray,   // 0xFF1C1C1F - container padrão
    surfaceContainerHigh = MidasColors.DarkGray,    // 0xFF303033 - elevação alta
    surfaceContainerHighest = MidasColors.Gray,     // 0xFF74747A - elevação máxima
    surfaceContainerLow = MidasColors.ExtraDarkGray,// 0xFF1C1C1F - elevação baixa
    surfaceContainerLowest = MidasColors.Black,     // 0xFF09090A - elevação mínima
)
