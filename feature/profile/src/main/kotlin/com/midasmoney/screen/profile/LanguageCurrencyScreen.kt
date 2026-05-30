package com.midasmoney.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

// ─────────────────────────────────────────────────────────────────────────────
// LANGUAGE SCREEN
// ─────────────────────────────────────────────────────────────────────────────

private data class Language(
    val code: String,
    val name: String,
    val nativeName: String,
    val flag: String,
)

private val languages =
    listOf(
        Language("pt-BR", "Português", "Português (Brasil)", "🇧🇷"),
        Language("en-US", "English", "English (US)", "🇺🇸"),
        Language("es", "Español", "Español", "🇪🇸"),
        Language("fr", "Français", "Français", "🇫🇷"),
        Language("de", "Deutsch", "Deutsch", "🇩🇪"),
        Language("it", "Italiano", "Italiano", "🇮🇹"),
        Language("ja", "Japanese", "日本語", "🇯🇵"),
        Language("zh", "Chinese", "中文", "🇨🇳"),
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    currentLanguageCode: String = "pt-BR",
    onBackClick: () -> Unit = {},
    onLanguageSelected: (String) -> Unit = {},
) {
    var selected by remember { mutableStateOf(currentLanguageCode) }

    MidasTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Idioma",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, "Voltar") } },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
        ) { padding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Selecione o idioma de preferência para usar no aplicativo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 20.dp),
                )

                Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
                    Column {
                        languages.forEachIndexed { index, lang ->
                            LanguageRow(
                                language = lang,
                                isSelected = lang.code == selected,
                                onClick = {
                                    selected = lang.code
                                    onLanguageSelected(lang.code)
                                },
                            )
                            if (index < languages.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(start = 60.dp),
                                    thickness = 0.5.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun LanguageRow(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .background(if (isSelected) MidasColors.Green.primary.copy(alpha = 0.05f) else Color.Transparent)
                .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(language.flag, style = MaterialTheme.typography.titleLarge)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                language.nativeName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) MidasColors.Green.primary else MaterialTheme.colorScheme.onSurface,
            )
            Text(
                language.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (isSelected) {
            Box(
                modifier =
                    Modifier
                        .size(22.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MidasColors.Green.primary),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Default.Check, null, tint = MidasColors.White, modifier = Modifier.size(14.dp))
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// CURRENCY SCREEN
// ─────────────────────────────────────────────────────────────────────────────

private data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
    val country: String,
)

private val currencies =
    listOf(
        Currency("BRL", "Real Brasileiro", "R$", "Brasil"),
        Currency("USD", "Dólar Americano", "US$", "Estados Unidos"),
        Currency("EUR", "Euro", "€", "União Europeia"),
        Currency("GBP", "Libra Esterlina", "£", "Reino Unido"),
        Currency("JPY", "Iene Japonês", "¥", "Japão"),
        Currency("ARS", "Peso Argentino", "$", "Argentina"),
        Currency("CLP", "Peso Chileno", "$", "Chile"),
        Currency("MXN", "Peso Mexicano", "$", "México"),
        Currency("CAD", "Dólar Canadense", "CA$", "Canadá"),
        Currency("CHF", "Franco Suíço", "CHF", "Suíça"),
        Currency("AUD", "Dólar Australiano", "A$", "Austrália"),
        Currency("CNY", "Yuan Chinês", "¥", "China"),
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    currentCurrencyCode: String = "BRL",
    onBackClick: () -> Unit = {},
    onCurrencySelected: (String) -> Unit = {},
) {
    var selected by remember { mutableStateOf(currentCurrencyCode) }

    MidasTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Moeda padrão",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, "Voltar") } },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
        ) { padding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "A moeda padrão é usada para exibir valores e gerar relatórios.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 20.dp),
                )

                Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
                    Column {
                        currencies.forEachIndexed { index, currency ->
                            CurrencyRow(
                                currency = currency,
                                isSelected = currency.code == selected,
                                onClick = {
                                    selected = currency.code
                                    onCurrencySelected(currency.code)
                                },
                            )
                            if (index < currencies.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(start = 60.dp),
                                    thickness = 0.5.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun CurrencyRow(
    currency: Currency,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .background(if (isSelected) MidasColors.Green.primary.copy(alpha = 0.05f) else Color.Transparent)
                .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (isSelected) {
                            MidasColors.Green.primary.copy(alpha = 0.12f)
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        },
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                currency.symbol,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MidasColors.Green.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                currency.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) MidasColors.Green.primary else MaterialTheme.colorScheme.onSurface,
            )
            Text(
                "${currency.code} · ${currency.country}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (isSelected) {
            Box(
                modifier =
                    Modifier
                        .size(22.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MidasColors.Green.primary),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Default.Check, null, tint = MidasColors.White, modifier = Modifier.size(14.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LanguagePreview() = LanguageScreen()

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CurrencyPreview() = CurrencyScreen()
