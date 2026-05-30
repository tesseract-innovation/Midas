package com.midasmoney.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.core.ui.component.*
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

// ─── Model ───────────────────────────────────────────────────────────────────

data class SecurityUiState(
    val isBiometricEnabled: Boolean = true,
    val isPinEnabled: Boolean = true,
    val is2FAEnabled: Boolean = false,
    val lastPasswordChange: String = "Há 32 dias",
    val activeSessions: Int = 2,
)

// ─── Screen ──────────────────────────────────────────────────────────────────

@Composable
fun SecurityScreen(
    uiState: SecurityUiState = SecurityUiState(),
    onBackClick: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onChangePin: () -> Unit = {},
    onManageSessions: () -> Unit = {},
    onBiometricToggle: (Boolean) -> Unit = {},
    on2FAToggle: (Boolean) -> Unit = {},
) {
    var biometricEnabled by remember { mutableStateOf(uiState.isBiometricEnabled) }
    var twoFAEnabled by remember { mutableStateOf(uiState.is2FAEnabled) }

    MidasTheme {
        MidasScaffold(
            title = "Segurança",
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

                // Security score banner
                SecurityScoreBanner(score = if (twoFAEnabled) 95 else 70)

                // Senha
                MidasSectionLabel("Senha")
                MidasSettingsGroup {
                    MidasSettingsTile(
                        icon = Icons.Outlined.Lock,
                        title = "Alterar senha",
                        subtitle = "Última alteração: ${uiState.lastPasswordChange}",
                        iconTint = MidasColors.Blue.primary,
                        onClick = onChangePasswordClick,
                    )
                }

                // Biometria & PIN
                MidasSectionLabel("Acesso rápido")
                MidasSettingsGroup {
                    MidasSettingsTile(
                        icon = Icons.Outlined.Fingerprint,
                        title = "Biometria",
                        subtitle = "Usar digital ou Face ID para entrar",
                        iconTint = MidasColors.Green.primary,
                        onClick = {
                            biometricEnabled = !biometricEnabled
                            onBiometricToggle(biometricEnabled)
                        },
                        trailing = {
                            Switch(
                                checked = biometricEnabled,
                                onCheckedChange = {
                                    biometricEnabled = it
                                    onBiometricToggle(it)
                                },
                                colors =
                                    SwitchDefaults.colors(
                                        checkedThumbColor = MidasColors.White,
                                        checkedTrackColor = MidasColors.Green.primary,
                                    ),
                            )
                        },
                    )
                    MidasSettingsDivider()
                    MidasSettingsTile(
                        icon = Icons.Outlined.Password,
                        title = "PIN de acesso",
                        subtitle = if (uiState.isPinEnabled) "PIN configurado" else "Não configurado",
                        iconTint = MidasColors.Purple.primary,
                        onClick = onChangePin,
                        trailing = {
                            val statusColor = if (uiState.isPinEnabled) MidasColors.Green.primary else MidasColors.Red.primary
                            val statusLabel = if (uiState.isPinEnabled) "Ativo" else "Inativo"
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = statusColor.copy(alpha = 0.12f),
                            ) {
                                Text(
                                    statusLabel,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = statusColor,
                                )
                            }
                        },
                    )
                }

                // 2FA
                MidasSectionLabel("Verificação em duas etapas")
                MidasSettingsGroup {
                    MidasSettingsTile(
                        icon = Icons.Outlined.Shield,
                        title = "Autenticação 2FA",
                        subtitle = if (twoFAEnabled) "Proteção extra ativada" else "Recomendado para mais segurança",
                        iconTint = if (twoFAEnabled) MidasColors.Green.primary else MidasColors.Gray,
                        onClick = {
                            twoFAEnabled = !twoFAEnabled
                            on2FAToggle(twoFAEnabled)
                        },
                        trailing = {
                            Switch(
                                checked = twoFAEnabled,
                                onCheckedChange = {
                                    twoFAEnabled = it
                                    on2FAToggle(it)
                                },
                                colors =
                                    SwitchDefaults.colors(
                                        checkedThumbColor = MidasColors.White,
                                        checkedTrackColor = MidasColors.Green.primary,
                                    ),
                            )
                        },
                    )
                }

                // Sessões
                MidasSectionLabel("Sessões ativas")
                MidasSettingsGroup {
                    MidasSettingsTile(
                        icon = Icons.Outlined.Devices,
                        title = "Gerenciar dispositivos",
                        subtitle = "${uiState.activeSessions} dispositivos conectados",
                        iconTint = MidasColors.Orange.primary,
                        onClick = onManageSessions,
                    )
                }

                // Danger zone
                MidasSectionLabel("Zona de risco")
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MidasColors.Red.primary.copy(alpha = 0.06f),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .border(
                                0.5.dp,
                                MidasColors.Red.primary.copy(alpha = 0.2f),
                                RoundedCornerShape(16.dp),
                            ),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .clickable { }
                                .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MidasColors.Red.primary.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                Icons.Outlined.DeleteForever,
                                contentDescription = null,
                                tint = MidasColors.Red.primary,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Encerrar todas as sessões",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MidasColors.Red.primary,
                            )
                            Text(
                                "Desconectar todos os dispositivos",
                                style = MaterialTheme.typography.bodySmall,
                                color = MidasColors.Red.primary.copy(alpha = 0.7f),
                            )
                        }
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = MidasColors.Red.primary.copy(alpha = 0.5f),
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ─── Sub-components ──────────────────────────────────────────────────────────

@Composable
private fun SecurityScoreBanner(score: Int) {
    val color =
        when {
            score >= 90 -> MidasColors.Green.primary
            score >= 60 -> MidasColors.Yellow.kindaDark
            else -> MidasColors.Red.primary
        }
    val label =
        when {
            score >= 90 -> "Excelente"
            score >= 60 -> "Moderado"
            else -> "Vulnerável"
        }
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.08f),
        modifier =
            Modifier
                .fillMaxWidth()
                .border(0.5.dp, color.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(48.dp)) {
                CircularProgressIndicator(
                    progress = { score / 100f },
                    modifier = Modifier.fillMaxSize(),
                    color = color,
                    trackColor = color.copy(alpha = 0.15f),
                    strokeWidth = 4.dp,
                )
                Text(
                    "$score",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = color,
                )
            }
            Column {
                Text(
                    "Nível de segurança: $label",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    if (score >= 90) "Sua conta está bem protegida" else "Ative o 2FA para melhorar",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SecurityPreview() = SecurityScreen()
