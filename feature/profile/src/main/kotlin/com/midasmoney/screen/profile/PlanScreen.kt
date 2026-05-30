package com.midasmoney.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.core.ui.component.*
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

data class PlanUiState(
    val currentPlan: String = "Midas Pro",
    val renewalDate: String = "15 de julho de 2025",
    val price: String = "R$ 19,90/mês",
    val paymentMethod: String = "Visa •••• 4231",
)

@Composable
fun PlanScreen(
    uiState: PlanUiState = PlanUiState(),
    onBackClick: () -> Unit = {},
    onCancelPlan: () -> Unit = {},
    onUpgrade: () -> Unit = {},
    onChangePayment: () -> Unit = {},
) {
    MidasTheme {
        MidasScaffold(
            title = "Plano e assinatura",
            onBackClick = onBackClick,
        ) { padding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                // Current plan card
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        MidasColors.Purple.extraDark,
                                        MidasColors.Purple.dark,
                                        MidasColors.Purple.kindaDark,
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                                ),
                            ),
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column {
                                Text(
                                    "Plano atual",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MidasColors.White.copy(alpha = 0.6f),
                                    letterSpacing = 1.sp,
                                )
                                Text(
                                    uiState.currentPlan,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MidasColors.White,
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = MidasColors.Green.primary.copy(alpha = 0.2f),
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                ) {
                                    Box(
                                        modifier =
                                            Modifier
                                                .size(6.dp)
                                                .clip(RoundedCornerShape(50))
                                                .background(MidasColors.Green.primary),
                                    )
                                    Text(
                                        "Ativo",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MidasColors.Green.primary,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(thickness = 0.5.dp, color = MidasColors.White.copy(alpha = 0.15f))
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            PlanInfoItem(label = "Preço", value = uiState.price)
                            PlanInfoItem(label = "Renovação", value = uiState.renewalDate)
                        }
                    }
                }

                // Payment method
                MidasSectionLabel("Método de pagamento")
                MidasSettingsGroup {
                    MidasSettingsTile(
                        icon = Icons.Outlined.CreditCard,
                        title = "Cartão de crédito",
                        subtitle = uiState.paymentMethod,
                        iconTint = MidasColors.Blue.primary,
                        trailing = {
                            TextButton(onClick = onChangePayment) {
                                Text(
                                    "Alterar",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MidasColors.Green.primary,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        },
                    )
                }

                // Benefits
                MidasSectionLabel("Benefícios inclusos")
                MidasCard {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        val benefits =
                            listOf(
                                "Carteiras ilimitadas",
                                "Relatórios avançados com IA",
                                "Suporte prioritário 24h",
                                "Exportação de dados (PDF/Excel)",
                                "Metas e planejamento financeiro",
                                "Integração com Open Banking",
                            )
                        benefits.forEach { benefit ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Box(
                                    modifier =
                                        Modifier
                                            .size(20.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(MidasColors.Green.primary.copy(alpha = 0.12f)),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        Icons.Default.Check,
                                        null,
                                        tint = MidasColors.Green.primary,
                                        modifier = Modifier.size(12.dp),
                                    )
                                }
                                Text(
                                    benefit,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }
                    }
                }

                // Upgrade card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MidasColors.Yellow.kindaDark.copy(alpha = 0.08f),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .border(
                                0.5.dp,
                                MidasColors.Yellow.kindaDark.copy(alpha = 0.25f),
                                RoundedCornerShape(16.dp),
                            ),
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Icon(
                            Icons.Outlined.Star,
                            null,
                            tint = MidasColors.Yellow.primary,
                            modifier = Modifier.size(28.dp),
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Midas Ultra disponível",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                "Análises preditivas e assessoria IA",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Button(
                            onClick = onUpgrade,
                            shape = RoundedCornerShape(10.dp),
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = MidasColors.Yellow.kindaDark,
                                    contentColor = MidasColors.White,
                                ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        ) {
                            Text("Ver", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                // Cancel
                TextButton(
                    onClick = onCancelPlan,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        "Cancelar assinatura",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        textDecoration = TextDecoration.Underline,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun PlanInfoItem(
    label: String,
    value: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MidasColors.White.copy(alpha = 0.6f))
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MidasColors.White,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PlanPreview() = PlanScreen()
