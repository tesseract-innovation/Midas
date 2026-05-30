package com.midasmoney.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.core.ui.component.MidasSectionLabel
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

data class NotificationsUiState(
    val pushEnabled: Boolean = true,
    val emailEnabled: Boolean = true,
    val transactionsEnabled: Boolean = true,
    val goalsEnabled: Boolean = true,
    val tipsEnabled: Boolean = false,
    val securityEnabled: Boolean = true,
    val monthlyReportEnabled: Boolean = true,
    val weeklyReportEnabled: Boolean = false,
    val promotionsEnabled: Boolean = false,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    uiState: NotificationsUiState = NotificationsUiState(),
    onBackClick: () -> Unit = {},
    onStateChange: (NotificationsUiState) -> Unit = {},
) {
    var state by remember { mutableStateOf(uiState) }

    fun update(new: NotificationsUiState) {
        state = new
        onStateChange(new)
    }

    MidasTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Notificações",
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
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                // Master toggles
                MidasSectionLabel("Canais")
                NotifGroup {
                    NotifToggleRow(
                        icon = Icons.Outlined.Notifications,
                        label = "Notificações push",
                        subtitle = "Alertas no seu dispositivo",
                        iconBg = MidasColors.Green.primary,
                        checked = state.pushEnabled,
                        onCheckedChange = { update(state.copy(pushEnabled = it)) },
                    )
                    NotifDivider()
                    NotifToggleRow(
                        icon = Icons.Outlined.Email,
                        label = "E-mail",
                        subtitle = "Receber notificações por e-mail",
                        iconBg = MidasColors.Blue.primary,
                        checked = state.emailEnabled,
                        onCheckedChange = { update(state.copy(emailEnabled = it)) },
                    )
                }

                // Transactions
                MidasSectionLabel("Atividade financeira")
                NotifGroup {
                    NotifToggleRow(
                        icon = Icons.Outlined.Sync,
                        label = "Transações",
                        subtitle = "Entradas, saídas e transferências",
                        iconBg = MidasColors.Green.primary,
                        checked = state.transactionsEnabled,
                        onCheckedChange = { update(state.copy(transactionsEnabled = it)) },
                    )
                    NotifDivider()
                    NotifToggleRow(
                        icon = Icons.Outlined.Flag,
                        label = "Metas",
                        subtitle = "Progresso e conquistas de metas",
                        iconBg = MidasColors.Purple.primary,
                        checked = state.goalsEnabled,
                        onCheckedChange = { update(state.copy(goalsEnabled = it)) },
                    )
                    NotifDivider()
                    NotifToggleRow(
                        icon = Icons.Outlined.Lightbulb,
                        label = "Dicas financeiras",
                        subtitle = "Sugestões personalizadas de IA",
                        iconBg = MidasColors.Yellow.kindaDark,
                        checked = state.tipsEnabled,
                        onCheckedChange = { update(state.copy(tipsEnabled = it)) },
                    )
                }

                // Security
                MidasSectionLabel("Segurança")
                NotifGroup {
                    NotifToggleRow(
                        icon = Icons.Outlined.Security,
                        label = "Alertas de segurança",
                        subtitle = "Novos acessos e atividades suspeitas",
                        iconBg = MidasColors.Red.primary,
                        checked = state.securityEnabled,
                        onCheckedChange = { update(state.copy(securityEnabled = it)) },
                    )
                }

                // Reports
                MidasSectionLabel("Relatórios")
                NotifGroup {
                    NotifToggleRow(
                        icon = Icons.Outlined.CalendarMonth,
                        label = "Resumo mensal",
                        subtitle = "Relatório completo todo mês",
                        iconBg = MidasColors.Blue.primary,
                        checked = state.monthlyReportEnabled,
                        onCheckedChange = { update(state.copy(monthlyReportEnabled = it)) },
                    )
                    NotifDivider()
                    NotifToggleRow(
                        icon = Icons.Outlined.DateRange,
                        label = "Resumo semanal",
                        subtitle = "Balanço rápido toda semana",
                        iconBg = MidasColors.Blue.light,
                        checked = state.weeklyReportEnabled,
                        onCheckedChange = { update(state.copy(weeklyReportEnabled = it)) },
                    )
                }

                // Marketing
                MidasSectionLabel("Promoções")
                NotifGroup {
                    NotifToggleRow(
                        icon = Icons.Outlined.LocalOffer,
                        label = "Ofertas e novidades",
                        subtitle = "Promoções e atualizações do app",
                        iconBg = MidasColors.Orange.primary,
                        checked = state.promotionsEnabled,
                        onCheckedChange = { update(state.copy(promotionsEnabled = it)) },
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun NotifGroup(content: @Composable ColumnScope.() -> Unit) {
    Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
        Column(content = content)
    }
}

@Composable
private fun NotifDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(start = 56.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
    )
}

@Composable
private fun NotifToggleRow(
    icon: ImageVector,
    label: String,
    subtitle: String,
    iconBg: Color,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconBg.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, null, tint = iconBg, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors =
                SwitchDefaults.colors(
                    checkedThumbColor = MidasColors.White,
                    checkedTrackColor = MidasColors.Green.primary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotificationsPreview() = NotificationsScreen()
