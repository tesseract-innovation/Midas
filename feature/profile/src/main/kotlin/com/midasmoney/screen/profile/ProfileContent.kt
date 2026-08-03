package com.midasmoney.screen.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.midasmoney.core.ui.component.MidasOutlinedButton
import com.midasmoney.core.ui.component.MidasSettingsDivider
import com.midasmoney.core.ui.component.MidasSettingsGroup
import com.midasmoney.core.ui.component.MidasSettingsTile
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

// ─── Data ───────────────────────────────────────────────────────────────────

data class ProfileUiState(
    val name: String = "Dayvson Silva",
    val email: String = "dayvson@midasmoney.com",
    val plan: String = "Midas Pro",
    val avatarInitials: String = "DS",
    val totalBalance: String = "R$ 48.320,00",
    val totalInvested: String = "R$ 31.500,00",
    val totalSaved: String = "R$ 16.820,00",
    val memberSince: String = "Membro desde Jan 2023",
)

data class SettingsItem(
    val icon: ImageVector,
    val label: String,
    val subtitle: String? = null,
    val trailing: @Composable (() -> Unit)? = null,
    val onClick: () -> Unit = {},
    val tint: Color = MidasColors.Green.primary,
)

// ─── Screen ─────────────────────────────────────────────────────────────────

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    uiState: ProfileUiState = ProfileUiState(),
    onBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onPersonalDataClick: () -> Unit = {},
    onSecurityClick: () -> Unit = {},
    onPlanClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
    onCurrencyClick: () -> Unit = {},
    onHelpFaqClick: () -> Unit = {},
    onSupportChatClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onTermsOfUseClick: () -> Unit = {},
) {
    var animationTriggered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animationTriggered = true }

    val contentAlpha by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha",
    )

    val contentTranslationY by animateDpAsState(
        targetValue = if (animationTriggered) 0.dp else 20.dp,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "translationY",
    )

    MidasTheme {
        Surface(
            modifier = Modifier.fillMaxSize().padding(bottom = paddingValues.calculateBottomPadding()),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .graphicsLayer {
                            alpha = contentAlpha
                            translationY = contentTranslationY.toPx()
                        },
            ) {
                ProfileHeader(
                    uiState = uiState,
                    onBackClick = onBackClick,
                )
                Spacer(modifier = Modifier.height(24.dp))
                StatsRow(uiState = uiState)
                Spacer(modifier = Modifier.height(28.dp))

                MidasSettingsGroup(title = "Conta") {
                    accountItems(
                        onPersonalDataClick = onPersonalDataClick,
                        onSecurityClick = onSecurityClick,
                        onPlanClick = onPlanClick,
                        onNotificationsClick = onNotificationsClick,
                    ).forEachIndexed { index, item ->
                        MidasSettingsTile(
                            icon = item.icon,
                            title = item.label,
                            subtitle = item.subtitle,
                            iconTint = item.tint,
                            onClick = item.onClick,
                            trailing = item.trailing,
                        )
                        if (index < 3) MidasSettingsDivider()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                MidasSettingsGroup(title = "Preferências") {
                    preferencesItems(
                        onLanguageClick = onLanguageClick,
                        onCurrencyClick = onCurrencyClick,
                    ).forEachIndexed { index, item ->
                        MidasSettingsTile(
                            icon = item.icon,
                            title = item.label,
                            subtitle = item.subtitle,
                            iconTint = item.tint,
                            onClick = item.onClick,
                            trailing = item.trailing,
                        )
                        if (index < 2) MidasSettingsDivider()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                MidasSettingsGroup(title = "Suporte") {
                    supportItems(
                        onHelpFaqClick = onHelpFaqClick,
                        onSupportChatClick = onSupportChatClick,
                        onPrivacyPolicyClick = onPrivacyPolicyClick,
                        onTermsOfUseClick = onTermsOfUseClick,
                    ).forEachIndexed { index, item ->
                        MidasSettingsTile(
                            icon = item.icon,
                            title = item.label,
                            subtitle = item.subtitle,
                            iconTint = item.tint,
                            onClick = item.onClick,
                            trailing = item.trailing,
                        )
                        if (index < 3) MidasSettingsDivider()
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                LogoutButton(onClick = onLogoutClick)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Midas Money v2.4.1",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 32.dp),
                )
            }
        }
    }
}

// ─── Header ─────────────────────────────────────────────────────────────────

@Composable
private fun ProfileHeader(
    uiState: ProfileUiState,
    onBackClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(280.dp),
    ) {
        // Gradient background
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        brush =
                            Brush.linearGradient(
                                colors =
                                    listOf(
                                        MidasColors.Green.extraDark,
                                        MidasColors.Green.dark,
                                        MidasColors.Green.kindaDark,
                                    ),
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                            ),
                    ),
        )

        // Decorative circles
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = MidasColors.Green.primary.copy(alpha = 0.15f),
                radius = 180.dp.toPx(),
                center = Offset(size.width * 0.85f, size.height * 0.1f),
            )
            drawCircle(
                color = MidasColors.Green.light.copy(alpha = 0.08f),
                radius = 120.dp.toPx(),
                center = Offset(size.width * 0.1f, size.height * 0.8f),
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp),
        ) {
            // Top bar
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MidasColors.White,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Editar perfil",
                        tint = MidasColors.White,
                    )
                }
            }

            // Avatar + name
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AvatarCircle(initials = uiState.avatarInitials)

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = uiState.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MidasColors.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = uiState.email,
                        style = MaterialTheme.typography.bodySmall,
                        color = MidasColors.White.copy(alpha = 0.75f),
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    PlanBadge(plan = uiState.plan)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = uiState.memberSince,
                style = MaterialTheme.typography.labelSmall,
                color = MidasColors.White.copy(alpha = 0.55f),
            )
        }
    }
}

@Composable
private fun AvatarCircle(initials: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    brush =
                        Brush.linearGradient(
                            colors =
                                listOf(
                                    MidasColors.Purple.primary,
                                    MidasColors.Purple.dark,
                                ),
                        ),
                )
                .border(2.dp, MidasColors.White.copy(alpha = 0.3f), CircleShape),
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MidasColors.White,
        )
    }
}

@Composable
private fun PlanBadge(plan: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier =
            Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MidasColors.White.copy(alpha = 0.15f))
                .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = MidasColors.Yellow.primary,
            modifier = Modifier.size(12.dp),
        )
        Text(
            text = plan,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = MidasColors.White,
        )
    }
}

// ─── Stats Row ───────────────────────────────────────────────────────────────

@Composable
private fun StatsRow(uiState: ProfileUiState) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            label = "Saldo Total",
            value = uiState.totalBalance,
            icon = Icons.Outlined.AccountBalanceWallet,
            accentColor = MidasColors.Green.primary,
        )
        StatCard(
            modifier = Modifier.weight(1f),
            label = "Investido",
            value = uiState.totalInvested,
            icon = Icons.Outlined.TrendingUp,
            accentColor = MidasColors.Blue.primary,
        )
        StatCard(
            modifier = Modifier.weight(1f),
            label = "Reservado",
            value = uiState.totalSaved,
            icon = Icons.Outlined.Savings,
            accentColor = MidasColors.Purple.primary,
        )
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    accentColor: Color,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)),
        tonalElevation = 2.dp,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(16.dp),
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun LogoutButton(onClick: () -> Unit) {
    MidasOutlinedButton(
        text = "Sair da conta",
        onClick = onClick,
        icon = Icons.Outlined.Logout,
        borderColor = MidasColors.Red.primary,
        contentColor = MidasColors.Red.primary,
        modifier = Modifier.padding(horizontal = 20.dp),
    )
}

// ─── Items helpers ───────────────────────────────────────────────────────────

// ─── Items helpers ───────────────────────────────────────────────────────────

@Composable
private fun accountItems(
    onPersonalDataClick: () -> Unit,
    onSecurityClick: () -> Unit,
    onPlanClick: () -> Unit,
    onNotificationsClick: () -> Unit,
): List<SettingsItem> {
    return listOf(
        SettingsItem(
            icon = Icons.Outlined.Person,
            label = "Dados pessoais",
            subtitle = "Nome, CPF, data de nascimento",
            tint = MidasColors.Green.primary,
            onClick = onPersonalDataClick,
        ),
        SettingsItem(
            icon = Icons.Outlined.Lock,
            label = "Segurança",
            subtitle = "Senha, biometria, PIN",
            tint = MidasColors.Blue.primary,
            onClick = onSecurityClick,
        ),
        SettingsItem(
            icon = Icons.Outlined.CreditCard,
            label = "Plano e assinatura",
            subtitle = "Midas Pro • Ativo",
            tint = MidasColors.Purple.primary,
            onClick = onPlanClick,
        ),
        SettingsItem(
            icon = Icons.Outlined.Notifications,
            label = "Notificações",
            subtitle = "Alertas, relatórios e avisos",
            tint = MidasColors.Yellow.kindaDark,
            onClick = onNotificationsClick,
        ),
    )
}

@Composable
private fun preferencesItems(
    onLanguageClick: () -> Unit,
    onCurrencyClick: () -> Unit,
): List<SettingsItem> {
    var darkModeEnabled by remember { mutableStateOf(true) }
    return listOf(
        SettingsItem(
            icon = Icons.Outlined.DarkMode,
            label = "Modo escuro",
            tint = MidasColors.Purple.primary,
            trailing = {
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it },
                    colors =
                        SwitchDefaults.colors(
                            checkedThumbColor = MidasColors.White,
                            checkedTrackColor = MidasColors.Green.primary,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                )
            },
        ),
        SettingsItem(
            icon = Icons.Outlined.Language,
            label = "Idioma",
            subtitle = "Português (Brasil)",
            tint = MidasColors.Blue.primary,
            onClick = onLanguageClick,
        ),
        SettingsItem(
            icon = Icons.Outlined.AttachMoney,
            label = "Moeda padrão",
            subtitle = "Real Brasileiro (BRL)",
            tint = MidasColors.Green.primary,
            onClick = onCurrencyClick,
        ),
    )
}

@Composable
private fun supportItems(
    onHelpFaqClick: () -> Unit,
    onSupportChatClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfUseClick: () -> Unit,
): List<SettingsItem> =
    listOf(
        SettingsItem(
            icon = Icons.Outlined.HelpOutline,
            label = "Ajuda & FAQ",
            tint = MidasColors.Blue.primary,
            onClick = onHelpFaqClick,
        ),
        SettingsItem(
            icon = Icons.Outlined.Chat,
            label = "Falar com suporte",
            subtitle = "Disponível 24h",
            tint = MidasColors.Green.primary,
            onClick = onSupportChatClick,
        ),
        SettingsItem(
            icon = Icons.Outlined.PrivacyTip,
            label = "Política de privacidade",
            tint = MidasColors.Gray,
            onClick = onPrivacyPolicyClick,
        ),
        SettingsItem(
            icon = Icons.Outlined.Info,
            label = "Termos de uso",
            tint = MidasColors.Gray,
            onClick = onTermsOfUseClick,
        ),
    )

// ─── Preview ─────────────────────────────────────────────────────────────────

@CustomPreview
@Composable
private fun ProfileScreenLightPreview() {
    MidasTheme(dark = false) {
        val paddingValues = PaddingValues()
        ProfileScreen(paddingValues = paddingValues)
    }
}
