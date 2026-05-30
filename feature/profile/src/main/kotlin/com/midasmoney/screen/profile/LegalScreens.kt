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
import androidx.compose.ui.unit.sp
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

// ─── Shared model ────────────────────────────────────────────────────────────

private data class LegalSection(
    val title: String,
    val icon: ImageVector,
    val content: String,
)

// ─────────────────────────────────────────────────────────────────────────────
// PRIVACY POLICY SCREEN
// ─────────────────────────────────────────────────────────────────────────────

private val privacySections =
    listOf(
        LegalSection(
            "Coleta de dados",
            Icons.Outlined.Storage,
            "Coletamos apenas as informações necessárias para o funcionamento do aplicativo: nome, e-mail, CPF (para verificação de identidade) e dados financeiros que você insere manualmente ou autoriza via Open Banking. Não coletamos dados de localização em segundo plano.",
        ),
        LegalSection(
            "Uso das informações",
            Icons.Outlined.Analytics,
            "Seus dados são usados exclusivamente para personalizar sua experiência financeira, gerar relatórios e insights, e garantir a segurança da sua conta. Nunca compartilhamos suas informações com terceiros sem sua autorização explícita.",
        ),
        LegalSection(
            "Armazenamento e segurança",
            Icons.Outlined.Lock,
            "Todos os dados são criptografados com AES-256 em repouso e TLS 1.3 em trânsito. Nossos servidores ficam no Brasil, em conformidade com a LGPD. Realizamos auditorias de segurança regulares.",
        ),
        LegalSection(
            "Seus direitos (LGPD)",
            Icons.Outlined.VerifiedUser,
            "Você tem o direito de acessar, corrigir, excluir e exportar todos os seus dados a qualquer momento. Para exercer esses direitos, acesse Configurações → Privacidade ou entre em contato com nosso DPO em privacidade@midasmoney.com.",
        ),
        LegalSection(
            "Cookies e rastreamento",
            Icons.Outlined.Cookie,
            "Utilizamos apenas cookies essenciais para manter sua sessão ativa. Não utilizamos cookies de rastreamento para publicidade. Você pode gerenciar suas preferências nas configurações do dispositivo.",
        ),
        LegalSection(
            "Contato",
            Icons.Outlined.Email,
            "Para dúvidas sobre privacidade, entre em contato com nosso Encarregado de Proteção de Dados (DPO) em privacidade@midasmoney.com ou pelo endereço: Midas Money Tecnologia Ltda., Av. Paulista 1374, São Paulo - SP.",
        ),
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(onBackClick: () -> Unit = {}) {
    LegalDocumentScreen(
        title = "Política de Privacidade",
        lastUpdated = "Última atualização: 10 de janeiro de 2025",
        icon = Icons.Outlined.PrivacyTip,
        iconColor = MidasColors.Purple.primary,
        summary = "Respeitamos sua privacidade e estamos em conformidade com a LGPD (Lei Geral de Proteção de Dados). Este documento explica como coletamos, usamos e protegemos seus dados.",
        sections = privacySections,
        onBackClick = onBackClick,
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// TERMS OF USE SCREEN
// ─────────────────────────────────────────────────────────────────────────────

private val termsSections =
    listOf(
        LegalSection(
            "Aceitação dos termos",
            Icons.Outlined.Gavel,
            "Ao utilizar o Midas Money, você concorda com estes Termos de Uso. Se não concordar com qualquer parte, deve encerrar o uso do aplicativo. Estes termos se aplicam a todos os usuários.",
        ),
        LegalSection(
            "Elegibilidade",
            Icons.Outlined.HowToReg,
            "O Midas Money é destinado a pessoas físicas com 18 anos ou mais, residentes no Brasil. Ao criar uma conta, você declara ter capacidade legal para celebrar este acordo.",
        ),
        LegalSection(
            "Responsabilidades do usuário",
            Icons.Outlined.Person,
            "Você é responsável por manter a confidencialidade de suas credenciais de acesso, por todas as atividades realizadas em sua conta e por fornecer informações verdadeiras e atualizadas.",
        ),
        LegalSection(
            "Limitação de responsabilidade",
            Icons.Outlined.Info,
            "O Midas Money é uma ferramenta de gestão financeira pessoal e não presta serviços de consultoria de investimentos. As informações e análises fornecidas não constituem recomendações financeiras.",
        ),
        LegalSection(
            "Propriedade intelectual",
            Icons.Outlined.Copyright,
            "Todo o conteúdo do Midas Money — incluindo design, código, logos e textos — é protegido por direitos autorais e pertence à Midas Money Tecnologia Ltda. É proibida a reprodução sem autorização.",
        ),
        LegalSection(
            "Rescisão",
            Icons.Outlined.Cancel,
            "Você pode encerrar sua conta a qualquer momento em Configurações → Conta → Excluir conta. Reservamo-nos o direito de suspender contas que violem estes termos, com aviso prévio sempre que possível.",
        ),
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsOfUseScreen(onBackClick: () -> Unit = {}) {
    LegalDocumentScreen(
        title = "Termos de Uso",
        lastUpdated = "Última atualização: 10 de janeiro de 2025",
        icon = Icons.Outlined.Description,
        iconColor = MidasColors.Blue.primary,
        summary = "Leia com atenção antes de usar o aplicativo. Estes termos estabelecem as regras e condições para o uso do Midas Money.",
        sections = termsSections,
        onBackClick = onBackClick,
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// SHARED LEGAL DOCUMENT SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LegalDocumentScreen(
    title: String,
    lastUpdated: String,
    icon: ImageVector,
    iconColor: Color,
    summary: String,
    sections: List<LegalSection>,
    onBackClick: () -> Unit,
) {
    MidasTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            title,
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

                // Header card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceContainer,
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(iconColor.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(icon, null, tint = iconColor, modifier = Modifier.size(24.dp))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                summary,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 18.sp,
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                lastUpdated,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            )
                        }
                    }
                }

                // Sections
                sections.forEach { section ->
                    LegalSectionCard(section = section)
                }

                // Bottom note
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MidasColors.Green.primary.copy(alpha = 0.06f),
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Icon(
                            Icons.Outlined.CheckCircle,
                            null,
                            tint = MidasColors.Green.primary,
                            modifier = Modifier.size(18.dp),
                        )
                        Text(
                            "Ao usar o app, você confirma que leu e concorda com este documento.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun LegalSectionCard(section: LegalSection) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(section.icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
            Text(
                section.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Text(
            section.content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 22.sp,
        )
        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrivacyPreview() = PrivacyPolicyScreen()

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TermsPreview() = TermsOfUseScreen()
