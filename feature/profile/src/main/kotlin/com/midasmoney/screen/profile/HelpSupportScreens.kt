package com.midasmoney.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.core.ui.component.MidasSectionLabel
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

// ─────────────────────────────────────────────────────────────────────────────
// HELP & FAQ SCREEN
// ─────────────────────────────────────────────────────────────────────────────

private data class FaqItem(val question: String, val answer: String, val category: String)

private data class FaqCategory(val label: String, val icon: ImageVector, val color: Color)

private val faqCategories =
    listOf(
        FaqCategory("Conta", Icons.Outlined.Person, MidasColors.Green.primary),
        FaqCategory("Pagamentos", Icons.Outlined.CreditCard, MidasColors.Blue.primary),
        FaqCategory("Segurança", Icons.Outlined.Lock, MidasColors.Purple.primary),
        FaqCategory("Planos", Icons.Outlined.Star, MidasColors.Yellow.kindaDark),
    )

private val faqItems =
    listOf(
        FaqItem(
            "Como alterar minha senha?",
            "Vá em Perfil → Segurança → Alterar senha. Você precisará confirmar sua identidade pelo e-mail cadastrado.",
            "Conta",
        ),
        FaqItem(
            "Como cancelar minha assinatura?",
            "Acesse Perfil → Plano e assinatura e role até o final. O cancelamento é efetivo no próximo ciclo de cobrança.",
            "Planos",
        ),
        FaqItem(
            "Meus dados são seguros?",
            "Sim. Usamos criptografia AES-256 e não compartilhamos seus dados com terceiros. Confira nossa política de privacidade.",
            "Segurança",
        ),
        FaqItem(
            "Como adicionar um cartão de crédito?",
            "Na tela principal, toque em '+' e selecione 'Cartão de crédito'. Insira os dados manualmente ou escaneie o cartão.",
            "Pagamentos",
        ),
        FaqItem(
            "Como exportar meus dados?",
            "Em Relatórios, toque no ícone de exportar no canto superior direito. Disponível em PDF e Excel para planos Pro e Ultra.",
            "Conta",
        ),
        FaqItem(
            "O que é Open Banking?",
            "É uma integração com seu banco para importar transações automaticamente, com sua autorização. Disponível para bancos parceiros.",
            "Pagamentos",
        ),
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpFaqScreen(
    onBackClick: () -> Unit = {},
    onContactSupport: () -> Unit = {},
) {
    var search by remember { mutableStateOf("") }
    var expandedIndex by remember { mutableStateOf<Int?>(null) }
    val filtered =
        if (search.isBlank()) {
            faqItems
        } else {
            faqItems.filter { it.question.contains(search, ignoreCase = true) || it.answer.contains(search, ignoreCase = true) }
        }

    MidasTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Ajuda & FAQ",
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
                        .verticalScroll(rememberScrollState()),
            ) {
                // Search bar
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = { Text("Buscar dúvidas...") },
                    leadingIcon = { Icon(Icons.Outlined.Search, null, modifier = Modifier.size(18.dp)) },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MidasColors.Green.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            focusedLeadingIconColor = MidasColors.Green.primary,
                        ),
                )

                // Categories
                if (search.isBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        faqCategories.forEach { cat ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                Box(
                                    modifier =
                                        Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(cat.color.copy(alpha = 0.1f))
                                            .clickable { },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(cat.icon, null, tint = cat.color, modifier = Modifier.size(20.dp))
                                }
                                Text(
                                    cat.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    MidasSectionLabel("Perguntas frequentes")
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
                        Column {
                            filtered.forEachIndexed { index, item ->
                                FaqRow(
                                    item = item,
                                    isExpanded = expandedIndex == index,
                                    onClick = { expandedIndex = if (expandedIndex == index) null else index },
                                )
                                if (index < filtered.lastIndex) {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        thickness = 0.5.dp,
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                    )
                                }
                            }
                            if (filtered.isEmpty()) {
                                Box(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(32.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        "Nenhum resultado encontrado",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Contact support CTA
                Surface(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clickable(onClick = onContactSupport),
                    shape = RoundedCornerShape(16.dp),
                    color = MidasColors.Green.primary.copy(alpha = 0.08f),
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Icon(
                            Icons.Outlined.HeadsetMic,
                            null,
                            tint = MidasColors.Green.primary,
                            modifier = Modifier.size(24.dp),
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Não encontrou o que procura?",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                "Fale com nosso suporte agora",
                                style = MaterialTheme.typography.bodySmall,
                                color = MidasColors.Green.primary,
                            )
                        }
                        Icon(
                            Icons.Default.ChevronRight,
                            null,
                            tint = MidasColors.Green.primary,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun FaqRow(
    item: FaqItem,
    isExpanded: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.question,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Icon(
                if (isExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
            )
        }
        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                item.answer,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// SUPPORT CHAT SCREEN
// ─────────────────────────────────────────────────────────────────────────────

private data class ChatMessage(
    val id: Int,
    val text: String,
    val isFromUser: Boolean,
    val time: String,
)

private val initialMessages =
    listOf(
        ChatMessage(
            0,
            "Olá! 👋 Bem-vindo ao suporte Midas. Sou a Mia, sua assistente virtual. Como posso te ajudar hoje?",
            false,
            "09:00",
        ),
        ChatMessage(1, "Opções rápidas:", false, "09:00"),
    )

private val quickReplies = listOf("Problema no pagamento", "Alterar plano", "Conta e acesso", "Outra dúvida")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportChatScreen(onBackClick: () -> Unit = {}) {
    var messages by remember { mutableStateOf(initialMessages) }
    var input by remember { mutableStateOf("") }
    var nextId by remember { mutableIntStateOf(2) }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        messages = messages + ChatMessage(nextId++, text, true, "agora")
        // Simulate bot reply
        messages = messages +
            ChatMessage(
                nextId++,
                "Entendido! Um agente humano irá te atender em breve. Tempo estimado: menos de 2 minutos. 🕐",
                false,
                "agora",
            )
        input = ""
    }

    MidasTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Box(
                                modifier =
                                    Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(MidasColors.Green.primary),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    Icons.Outlined.SupportAgent,
                                    null,
                                    tint = MidasColors.White,
                                    modifier = Modifier.size(18.dp),
                                )
                            }
                            Column {
                                Text(
                                    "Suporte Midas",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                ) {
                                    Box(
                                        modifier =
                                            Modifier.size(
                                                6.dp,
                                            ).clip(CircleShape).background(MidasColors.Green.primary),
                                    )
                                    Text(
                                        "Online agora",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MidasColors.Green.primary,
                                    )
                                }
                            }
                        }
                    },
                    navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, "Voltar") } },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                Column(
                    modifier =
                        Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .navigationBarsPadding(),
                ) {
                    // Quick replies
                    if (messages.size <= 3) {
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            quickReplies.take(2).forEach { reply ->
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    modifier = Modifier.clickable { sendMessage(reply) },
                                ) {
                                    Text(
                                        reply,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        OutlinedTextField(
                            value = input,
                            onValueChange = { input = it },
                            placeholder = { Text("Escreva sua mensagem...") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(24.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(onSend = { sendMessage(input) }),
                            colors =
                                OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MidasColors.Green.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                ),
                        )
                        IconButton(
                            onClick = { sendMessage(input) },
                            modifier =
                                Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (input.isNotBlank()) MidasColors.Green.primary else MaterialTheme.colorScheme.surfaceVariant,
                                    ),
                        ) {
                            Icon(
                                Icons.Default.Send,
                                "Enviar",
                                tint = if (input.isNotBlank()) MidasColors.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                }
            },
        ) { padding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                messages.forEach { msg ->
                    ChatBubble(message = msg)
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start,
    ) {
        if (!message.isFromUser) {
            Box(
                modifier =
                    Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(MidasColors.Green.primary)
                        .align(Alignment.Bottom),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.SupportAgent, null, tint = MidasColors.White, modifier = Modifier.size(14.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(
            modifier = Modifier.widthIn(max = 260.dp),
            horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start,
        ) {
            Surface(
                shape =
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isFromUser) 16.dp else 4.dp,
                        bottomEnd = if (message.isFromUser) 4.dp else 16.dp,
                    ),
                color = if (message.isFromUser) MidasColors.Green.primary else MaterialTheme.colorScheme.surfaceContainer,
            ) {
                Text(
                    message.text,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.isFromUser) MidasColors.White else MaterialTheme.colorScheme.onSurface,
                )
            }
            Text(
                message.time,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HelpFaqPreview() = HelpFaqScreen()

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SupportChatPreview() = SupportChatScreen()
