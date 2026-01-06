package com.midasmoney.core.ui.preview

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Preview(
    name = "Dark Mode",
    showBackground = true,
    backgroundColor = Color.BLACK.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Light Mode",
    showBackground = true,
    backgroundColor = Color.WHITE.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
annotation class CustomPreview
