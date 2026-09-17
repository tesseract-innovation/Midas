package com.midasmoney.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.midasmoney.core.ui.R

@OptIn(ExperimentalTextApi::class)
private fun interWeight(weight: FontWeight) =
    Font(
        resId = R.font.inter_variable,
        weight = weight,
        variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight)),
    )

val InterFontFamily =
    FontFamily(
        interWeight(FontWeight.Normal),
        interWeight(FontWeight.Medium),
        interWeight(FontWeight.SemiBold),
        interWeight(FontWeight.Bold),
        interWeight(FontWeight.ExtraBold),
    )

/**
 * Same type scale as Material 3's default [Typography] — only the font family changes to Inter.
 * Sizes/weights per element stay whatever each screen already sets (e.g. the account cards'
 * explicit fontSize overrides), so this doesn't change any spec'd size.
 */
private val defaultTypography = Typography()

val MidasTypography =
    Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = InterFontFamily),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = InterFontFamily),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = InterFontFamily),
        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = InterFontFamily),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = InterFontFamily),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = InterFontFamily),
        titleLarge = defaultTypography.titleLarge.copy(fontFamily = InterFontFamily),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = InterFontFamily),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = InterFontFamily),
        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = InterFontFamily),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = InterFontFamily),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = InterFontFamily),
        labelLarge = defaultTypography.labelLarge.copy(fontFamily = InterFontFamily),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = InterFontFamily),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = InterFontFamily),
    )
