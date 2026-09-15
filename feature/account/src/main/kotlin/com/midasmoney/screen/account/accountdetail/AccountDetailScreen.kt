package com.midasmoney.screen.account.accountdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.domain.model.extension.formatAmount
import com.midasmoney.core.domain.model.extension.formatAmountColor
import com.midasmoney.core.domain.model.extension.formatDate
import com.midasmoney.core.domain.model.extension.formatIconColorBackground
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.resource.R
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import com.midasmoney.screen.account.AccountRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetails(
    args: AccountRoute.AccountDetails,
    navController: NavHostController,
    viewModel: AccountDetailViewModel = hiltViewModel(),
) {
    val account = args.account
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val totalBalance by viewModel.totalBalance.collectAsStateWithLifecycle()

    LaunchedEffect(account.id) {
        viewModel.loadTransactions(account.id.toString())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AccountDetailTopBar(
                    title = account.name,
                    onBack = { navController.popBackStack() },
                    onEdit = { navController.navigate(AccountRoute.AccountForm(account)) },
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
        ) { padding ->
            AccountDetailContent(
                account = account,
                totalBalance = totalBalance,
                transactions = transactions,
                onTransactionClick = { transaction ->
                    navController.navigate(AccountRoute.TransactionForm(account, transaction))
                },
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
            )
        }

        AddTransactionButton(
            onClick = { navController.navigate(AccountRoute.TransactionForm(account)) },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountDetailTopBar(
    title: String,
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                )
            }
        },
        actions = {
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.description_edit_account),
                )
            }
        },
    )
}

@Composable
private fun AddTransactionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
                .height(54.dp)
                .navigationBarsPadding(),
        shape = RoundedCornerShape(16.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MidasColors.Blue.primary,
                contentColor = Color.White,
            ),
    ) {
        Icon(Icons.Outlined.AddCircleOutline, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            stringResource(R.string.description_add_transaction),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun AccountDetailContent(
    account: Account,
    totalBalance: Double,
    transactions: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val icon = IconConverter.getImageVector(account.icon)
    val color = ColorConverter.aRgbToColor(account.color)

    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AccountHeaderCard(account = account, totalBalance = totalBalance, accentColor = color, icon = icon)
        AccountStatsCard(account = account)
        TransactionsSection(transactions = transactions, onTransactionClick = onTransactionClick)
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun AccountHeaderCard(
    account: Account,
    totalBalance: Double,
    accentColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = account.name,
                tint = accentColor,
                modifier =
                    Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(accentColor.copy(alpha = 0.2f))
                        .padding(12.dp),
            )
            Column {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "${stringResource(R.string.balance)}: ${totalBalance.toCurrency()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MidasColors.Gray,
                )
            }
        }
    }
}

@Composable
private fun AccountStatsCard(account: Account) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            AccountStat(label = stringResource(R.string.income), value = account.balance.income, color = MidasColors.Green.primary)
            AccountStat(label = stringResource(R.string.expense), value = account.balance.expense, color = MidasColors.Red.primary)
        }
    }
}

@Composable
private fun AccountStat(
    label: String,
    value: Double,
    color: Color,
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MidasColors.Gray)
        Text(
            value.toCurrency(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color,
        )
    }
}

@Composable
private fun TransactionsSection(
    transactions: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.title_transactions),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            if (transactions.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_transactions),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MidasColors.Gray,
                    modifier = Modifier.padding(top = 12.dp),
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
                transactions.forEachIndexed { index, transaction ->
                    TransactionRow(transaction = transaction, onClick = { onTransactionClick(transaction) })
                    if (index < transactions.lastIndex) {
                        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionRow(
    transaction: Transaction,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = IconConverter.getImageVector(transaction.icon),
            contentDescription = transaction.title,
            tint = ColorConverter.aRgbToColor(transaction.color),
            modifier =
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(transaction.formatIconColorBackground())
                    .padding(10.dp),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(transaction.type.displayName, style = MaterialTheme.typography.bodySmall, color = MidasColors.Gray)
                Box(
                    modifier =
                        Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(MidasColors.Gray),
                )
                Text(transaction.formatDate(), style = MaterialTheme.typography.bodySmall, color = MidasColors.Gray)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = transaction.formatAmount(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = transaction.formatAmountColor(),
            )
            Text(
                text = transaction.status.displayName,
                style = MaterialTheme.typography.labelSmall,
                color = MidasColors.Gray,
            )
        }
    }
}

@CustomPreview
@Composable
private fun AccountDetailContentPreview() {
    val account = Database.accounts.first()
    MidasTheme {
        AccountDetailContent(
            account = account,
            totalBalance = account.balance.currentBalance,
            transactions = account.transactions,
            onTransactionClick = {},
        )
    }
}
