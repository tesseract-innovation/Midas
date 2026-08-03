package com.midasmoney.screen.account
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme

// ─── Models ──────────────────────────────────────────────────────────────────

data class TransactionItem(
    val id: String,
    val title: String,
    val type: String,
    val date: String,
    val amount: Double,
    val status: String,
    val color: Color,
    val isExpense: Boolean,
)

data class Account(
    val id: String,
    val name: String,
    val balance: Double,
    val income: Double,
    val expense: Double,
    val iconColor: Color,
    val transactions: List<TransactionItem> = emptyList(),
)

private val sampleAccounts = listOf(
    Account(
        id = "1",
        name = "Account 1",
        balance = 163.00,
        income = 0.00,
        expense = 63.00,
        iconColor = MidasColors.Red.dark,
        transactions = listOf(
            TransactionItem("t1", "Transaction 1", "Transfer", "Today", 63.00, "Approved", MidasColors.Red.dark, true),
        ),
    ),
    Account(
        id = "2",
        name = "Savings",
        balance = 8_420.00,
        income = 3_200.00,
        expense = 0.00,
        iconColor = MidasColors.Green.dark,
        transactions = listOf(
            TransactionItem("t2", "Salary", "Income", "July 5", 3_200.00, "Approved", MidasColors.Green.dark, false),
        ),
    ),
)

// ─────────────────────────────────────────────────────────────────────────────
// ACCOUNTS LIST SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun AccountsScreen(
    paddingValues: PaddingValues,
    accounts: List<Account> = sampleAccounts,
    onAccountClick: (String) -> Unit = {},
    onEditAccount: (String) -> Unit = {},
    onDeleteAccount: (String) -> Unit = {},
    onAddAccount: () -> Unit = {},
) {
    val totalBalance = accounts.sumOf { it.balance }

    MidasTheme {
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    // Title
                    Text(
                        "Accounts",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    // Total balance
                    Text(
                        "Total: $ ${"%.2f".format(totalBalance)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp, bottom = 20.dp),
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        accounts.forEach { account ->
                            AccountCard(
                                account = account,
                                onClick = { onAccountClick(account.id) },
                                onEdit = { onEditAccount(account.id) },
                                onDelete = { onDeleteAccount(account.id) },
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(96.dp))
                }
            }

            // FAB
            FloatingActionButton(
                onClick = onAddAccount,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 28.dp),
                shape = RoundedCornerShape(16.dp),
                containerColor = MidasColors.Purple.primary,
                contentColor = MidasColors.White,
            ) {
                Icon(Icons.Default.Add, "Add account")
            }
        }
    }
}

@Composable
private fun AccountCard(
    account: Account,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
        ) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(account.iconColor),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Outlined.AccountBalanceWallet, null, tint = MidasColors.White, modifier = Modifier.size(22.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(account.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text(
                        "$ ${"%.2f".format(account.balance)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                // Action buttons
                Row {
                    IconButton(onClick = onEdit, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Outlined.Edit, "Edit", tint = MidasColors.Blue.primary, modifier = Modifier.size(18.dp))
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Outlined.Delete, "Delete", tint = MidasColors.Red.primary, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(12.dp))

            // Income/Expense
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                AccountStat(label = "Income", value = account.income, color = MidasColors.Green.primary)
                AccountStat(label = "Expense", value = account.expense, color = MidasColors.Red.primary)
            }
        }
    }
}

@Composable
private fun AccountStat(label: String, value: Double, color: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            "$ ${"%.2f".format(value)}",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = color,
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ACCOUNT DETAIL SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailScreen(
    paddingValues: PaddingValues,
    account: Account = sampleAccounts[0],
    onBackClick: () -> Unit = {},
    onTransactionClick: (String) -> Unit = {},
    onAddTransaction: () -> Unit = {},
) {
    MidasTheme {
        Box(modifier = Modifier.fillMaxSize().padding(bottom = paddingValues.calculateBottomPadding())) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(account.iconColor),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(Icons.Outlined.AccountBalanceWallet, null, tint = MidasColors.White, modifier = Modifier.size(16.dp))
                                }
                                Column {
                                    Text(account.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                                    Text("Balance: $ ${"%.2f".format(account.balance)}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        },
                        navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, "Back") } },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    )
                },
                containerColor = MaterialTheme.colorScheme.background,
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Stats
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        MiniStatCard(modifier = Modifier.weight(1f), label = "Income", value = account.income, color = MidasColors.Green.primary)
                        MiniStatCard(modifier = Modifier.weight(1f), label = "Expense", value = account.expense, color = MidasColors.Red.primary)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Transactions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)

                    Spacer(modifier = Modifier.height(12.dp))

                    if (account.transactions.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Outlined.ReceiptLong, null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f), modifier = Modifier.size(48.dp))
                                Text("No transactions yet", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            account.transactions.forEach { tx ->
                                TransactionRow(item = tx, onClick = { onTransactionClick(tx.id) })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(96.dp))
                }
            }

            // FAB
            FloatingActionButton(
                onClick = onAddTransaction,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 28.dp),
                shape = RoundedCornerShape(16.dp),
                containerColor = MidasColors.Purple.primary,
                contentColor = MidasColors.White,
            ) {
                Icon(Icons.Default.Add, "Add transaction")
            }
        }
    }
}

@Composable
private fun MiniStatCard(modifier: Modifier = Modifier, label: String, value: Double, color: Color) {
    Surface(modifier = modifier, shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                "$ ${"%.2f".format(value)}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = color,
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// EDIT TRANSACTION SCREEN
// ─────────────────────────────────────────────────────────────────────────────

data class TransactionFormState(
    val title: String = "Transaction 1",
    val value: String = "63.0",
    val description: String = "Description",
    val icon: String = "airplane",
    val color: Color = MidasColors.Orange.light,
    val type: String = "Transfer",
    val status: String = "Approved",
    val date: String = "2026-05-30",
    val time: String = "00:00",
)

private val transactionTypes = listOf("Income", "Expense", "Transfer")
private val transactionStatuses = listOf("Pending", "Approved", "Rejected")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTransactionScreen(
    initial: TransactionFormState = TransactionFormState(),
    isNew: Boolean = false,
    onBackClick: () -> Unit = {},
    onSaveClick: (TransactionFormState) -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    var form by remember { mutableStateOf(initial) }
    var showTypeSheet by remember { mutableStateOf(false) }
    var showStatusSheet by remember { mutableStateOf(false) }

    MidasTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            if (isNew) "New Transaction" else "Edit Transaction",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, "Back") } },
                    actions = {
                        if (!isNew) {
                            IconButton(onClick = onDeleteClick) {
                                Icon(Icons.Outlined.Delete, "Delete", tint = MidasColors.Red.primary)
                            }
                        }
                        IconButton(onClick = { onSaveClick(form) }) {
                            Icon(Icons.Default.Check, "Save", tint = MidasColors.Green.primary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                // Text fields
                TransactionTextField(value = form.title, onValueChange = { form = form.copy(title = it) }, label = "Title", icon = Icons.Outlined.Label)
                TransactionTextField(
                    value = form.value,
                    onValueChange = { form = form.copy(value = it) },
                    label = "Value",
                    icon = Icons.Outlined.AttachMoney,
                    keyboardType = KeyboardType.Decimal,
                )
                TransactionTextField(value = form.description, onValueChange = { form = form.copy(description = it) }, label = "Description", icon = Icons.Outlined.Notes)

                // Icon picker
                SelectorRow(
                    label = "Icon",
                    trailing = {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Outlined.AirplanemodeActive, null, tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(18.dp))
                        }
                    },
                    onClick = {},
                )

                // Color picker
                SelectorRow(
                    label = "Color",
                    trailing = {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(form.color),
                        )
                    },
                    onClick = {},
                )

                // Type
                SelectorRow(
                    label = "Type",
                    trailing = {
                        Text(form.type, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    },
                    onClick = { showTypeSheet = true },
                )

                // Status
                SelectorRow(
                    label = "Status",
                    trailing = {
                        StatusChip(status = form.status)
                    },
                    onClick = { showStatusSheet = true },
                )

                // Date
                SelectorRow(
                    label = "Date",
                    trailing = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Outlined.CalendarToday, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                            Text(form.date, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    },
                    onClick = {},
                )

                // Time
                SelectorRow(
                    label = "Time",
                    trailing = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Outlined.AccessTime, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                            Text(form.time, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    },
                    onClick = {},
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Type bottom sheet
        if (showTypeSheet) {
            ModalBottomSheet(
                onDismissRequest = { showTypeSheet = false },
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 32.dp)) {
                    Text("Transaction type", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                    transactionTypes.forEach { type ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { form = form.copy(type = type); showTypeSheet = false }
                                .padding(vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(type, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                            if (form.type == type) {
                                Icon(Icons.Default.Check, null, tint = MidasColors.Green.primary, modifier = Modifier.size(18.dp))
                            }
                        }
                        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    }
                }
            }
        }

        // Status bottom sheet
        if (showStatusSheet) {
            ModalBottomSheet(
                onDismissRequest = { showStatusSheet = false },
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 32.dp)) {
                    Text("Status", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                    transactionStatuses.forEach { status ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { form = form.copy(status = status); showStatusSheet = false }
                                .padding(vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            StatusChip(status = status)
                            if (form.status == status) {
                                Icon(Icons.Default.Check, null, tint = MidasColors.Green.primary, modifier = Modifier.size(18.dp))
                            }
                        }
                        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, null, modifier = Modifier.size(18.dp)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MidasColors.Green.primary,
            focusedLabelColor = MidasColors.Green.primary,
            focusedLeadingIconColor = MidasColors.Green.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    )
}

@Composable
private fun SelectorRow(
    label: String,
    trailing: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            trailing()
        }
    }
}

@Composable
private fun StatusChip(
    status: String,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, textColor) = when (status.lowercase()) {
        "approved", "completed" -> Pair(MidasColors.Green.primary.copy(alpha = 0.15f), MidasColors.Green.primary)
        "pending", "scheduled" -> Pair(MidasColors.Orange.primary.copy(alpha = 0.15f), MidasColors.Orange.primary)
        "rejected", "canceled", "failed" -> Pair(MidasColors.Red.primary.copy(alpha = 0.15f), MidasColors.Red.primary)
        else -> Pair(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = status,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )
    }
}

@Composable
private fun TransactionRow(
    item: TransactionItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(item.color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (item.isExpense) Icons.Outlined.ArrowUpward else Icons.Outlined.ArrowDownward,
                contentDescription = null,
                tint = item.color,
                modifier = Modifier.size(18.dp),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = item.type,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)),
                )
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            val prefix = if (item.isExpense) "- " else "+ "
            Text(
                text = "$prefix R$ ${"%.2f".format(item.amount)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = if (item.isExpense) MidasColors.Red.primary else MidasColors.Green.primary,
            )
            Text(
                text = item.status,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AccountsPreview() = AccountsScreen(PaddingValues())

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AccountDetailPreview() = AccountDetailScreen(PaddingValues())

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EditTransactionPreview() = EditTransactionScreen()
