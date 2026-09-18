package com.midasmoney.screen.account

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.AccountType
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.domain.model.extension.toExpenseCurrency
import com.midasmoney.core.domain.model.extension.toIncomeCurrency
import com.midasmoney.core.resource.R.string.activate_account
import com.midasmoney.core.resource.R.string.bill
import com.midasmoney.core.resource.R.string.contributions
import com.midasmoney.core.resource.R.string.current_balance
import com.midasmoney.core.resource.R.string.current_bill
import com.midasmoney.core.resource.R.string.deactivate_account
import com.midasmoney.core.resource.R.string.deactivated_accounts
import com.midasmoney.core.resource.R.string.description_account_options
import com.midasmoney.core.resource.R.string.description_add_account
import com.midasmoney.core.resource.R.string.description_delete_account
import com.midasmoney.core.resource.R.string.description_edit_account
import com.midasmoney.core.resource.R.string.due_in
import com.midasmoney.core.resource.R.string.error_load_accounts
import com.midasmoney.core.resource.R.string.expense
import com.midasmoney.core.resource.R.string.income
import com.midasmoney.core.resource.R.string.limit
import com.midasmoney.core.resource.R.string.no_accounts
import com.midasmoney.core.resource.R.string.saved
import com.midasmoney.core.resource.R.string.title_transactions
import com.midasmoney.core.resource.R.string.total_balance
import com.midasmoney.core.resource.R.string.withdrawals
import com.midasmoney.core.resource.R.string.withdrawn
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import com.midasmoney.domain.repository.IAccountRepository
import com.midasmoney.screen.account.component.DeleteDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

// ─────────────────────────────────────────────────────────────────────────────
// ACCOUNTS LIST SCREEN
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun AccountsScreenImp(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    val viewModel: AccountViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AccountsScreen(
        navController = navController,
        paddingValues = paddingValues,
        uiState = uiState,
        viewModel = viewModel,
    )
}

@Composable
fun AccountsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    uiState: AccountUiState,
    viewModel: AccountViewModel,
) {
    MidasTheme {
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding()),
            color = MaterialTheme.colorScheme.background,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (uiState) {
                    is AccountUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is AccountUiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(
                                    text = stringResource(error_load_accounts),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MidasColors.Red.primary,
                                )
                                Text(
                                    text = uiState.message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MidasColors.Gray,
                                )
                            }
                        }
                    }

                    is AccountUiState.Success -> {
                        val accounts = uiState.accounts
                        val activeAccounts = accounts.filter { it.isActive }
                        val deactivatedAccounts = accounts.filter { !it.isActive }
                        val totalBalance = activeAccounts.sumOf { it.balance.currentBalance }
                        val totalIncome = activeAccounts.sumOf { it.balance.income }
                        val totalExpense = activeAccounts.sumOf { it.balance.expense }

                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                        ) {
                            AccountsHeroCard(
                                totalBalance = totalBalance,
                                totalIncome = totalIncome,
                                totalExpense = totalExpense,
                                accountCount = activeAccounts.size,
                            )

                            if (accounts.isEmpty()) {
                                Box(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(32.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = stringResource(no_accounts),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MidasColors.Gray,
                                    )
                                }
                            } else {
                                Column(
                                    modifier =
                                        Modifier
                                            .padding(horizontal = 16.dp)
                                            .padding(top = 20.dp),
                                ) {
                                    AccountsListHeader(count = activeAccounts.size)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    activeAccounts.forEach { account ->
                                        AccountCard(
                                            account = account,
                                            onClick = {
                                                navController.navigate(AccountRoute.AccountDetails(account))
                                            },
                                            onEdit = {
                                                navController.navigate(AccountRoute.AccountForm(account))
                                            },
                                            onDelete = { viewModel.deleteAccount(account) },
                                            onSetActive = { isActive -> viewModel.setAccountActive(account, isActive) },
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    if (deactivatedAccounts.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(20.dp))
                                        DeactivatedAccountsHeader(count = deactivatedAccounts.size)
                                        Spacer(modifier = Modifier.height(12.dp))
                                        deactivatedAccounts.forEach { account ->
                                            AccountCard(
                                                account = account,
                                                onClick = {
                                                    navController.navigate(AccountRoute.AccountDetails(account))
                                                },
                                                onEdit = {
                                                    navController.navigate(AccountRoute.AccountForm(account))
                                                },
                                                onDelete = { viewModel.deleteAccount(account) },
                                                onSetActive = { isActive -> viewModel.setAccountActive(account, isActive) },
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(96.dp))
                        }
                    }
                }

                FloatingActionButton(
                    onClick = { navController.navigate(AccountRoute.AccountForm()) },
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 20.dp, bottom = 28.dp),
                    shape = RoundedCornerShape(16.dp),
                    containerColor = MidasColors.Purple.primary,
                    contentColor = MidasColors.White,
                ) {
                    Icon(Icons.Default.Add, stringResource(description_add_account))
                }
            }
        }
    }
}

@Composable
private fun AccountsHeroCard(
    totalBalance: Double,
    totalIncome: Double,
    totalExpense: Double,
    accountCount: Int,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors =
                            listOf(
                                MidasColors.Purple.extraDark,
                                MidasColors.Blue.dark,
                                MidasColors.Green.extraDark,
                            ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                    ),
                ),
    ) {
        // Decorative circle
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = MidasColors.Blue.light.copy(alpha = 0.12f),
                radius = 150.dp.toPx(),
                center = Offset(size.width * 0.9f, size.height * 0.1f),
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            Text(
                stringResource(total_balance),
                style = MaterialTheme.typography.labelSmall,
                color = MidasColors.White.copy(alpha = 0.65f),
            )
            Text(
                totalBalance.toCurrency(),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MidasColors.White,
                modifier = Modifier.padding(top = 4.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 4.dp),
            ) {
                Icon(
                    Icons.Outlined.AccountBalance,
                    null,
                    tint = MidasColors.White.copy(alpha = 0.5f),
                    modifier = Modifier.size(11.dp),
                )
                Text(
                    "$accountCount ${if (accountCount == 1) "active account" else "active accounts"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MidasColors.White.copy(alpha = 0.6f),
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(thickness = 0.5.dp, color = MidasColors.White.copy(alpha = 0.12f))
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                AccountsHeroStat(
                    modifier = Modifier.weight(1f),
                    label = stringResource(income),
                    value = totalIncome.toIncomeCurrency(),
                    icon = Icons.Outlined.ArrowDownward,
                    iconColor = MidasColors.Green.primary,
                )
                AccountsHeroStatDivider()
                AccountsHeroStat(
                    modifier = Modifier.weight(1f),
                    label = stringResource(expense),
                    value = totalExpense.toExpenseCurrency(),
                    icon = Icons.Outlined.ArrowUpward,
                    iconColor = MidasColors.Red.primary,
                )
            }
        }
    }
}

@Composable
private fun RowScope.AccountsHeroStatDivider() {
    Box(
        modifier =
            Modifier
                .width(0.5.dp)
                .height(36.dp)
                .background(MidasColors.White.copy(alpha = 0.12f))
                .align(Alignment.CenterVertically),
    )
}

@Composable
private fun AccountsHeroStat(
    modifier: Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
) {
    Column(modifier = modifier.padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            Icon(icon, null, tint = iconColor, modifier = Modifier.size(11.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = MidasColors.White.copy(alpha = 0.55f),
                fontSize = 9.sp,
            )
        }
        Text(
            value,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MidasColors.White,
            modifier = Modifier.padding(top = 3.dp),
        )
    }
}

@Composable
private fun AccountsListHeader(count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "Accounts",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            "$count active",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = MidasColors.Green.primary,
        )
    }
}

@Composable
private fun DeactivatedAccountsHeader(count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            stringResource(deactivated_accounts),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            "$count inactive",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = MidasColors.Green.primary,
        )
    }
}

@Composable
private fun AccountCard(
    account: Account,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onSetActive: (Boolean) -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteDialog(
            titleItem = account.name,
            onConfirm = {
                onDelete()
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false },
        )
    }

    val icon = IconConverter.getImageVector(account.icon)
    val accentColor = ColorConverter.aRgbToColor(account.color)
    val balance = account.balance.currentBalance
    val isCreditCard = account.type == AccountType.CREDIT_CARD
    val balanceColor =
        when {
            isCreditCard -> if (balance < 0) MidasColors.Red.primary else MidasColors.Green.primary
            balance >= 0 -> MaterialTheme.colorScheme.onSurface
            else -> MidasColors.Red.primary
        }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier =
            Modifier
                .fillMaxWidth()
                .alpha(if (account.isActive) 1f else 0.5f),
    ) {
        Column {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp, end = 10.dp, top = 14.dp, bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(icon, null, tint = accentColor, modifier = Modifier.size(20.dp))
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        account.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        modifier = Modifier.padding(top = 2.dp),
                    ) {
                        Icon(
                            Icons.Outlined.AccountBalance,
                            null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(10.dp),
                        )
                        Text(
                            account.type.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box {
                        Box(
                            modifier =
                                Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (menuExpanded) {
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
                                        } else {
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.07f)
                                        },
                                    )
                                    .clickable { menuExpanded = true },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = stringResource(description_account_options),
                                tint =
                                    if (menuExpanded) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                modifier = Modifier.size(16.dp),
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            modifier =
                                Modifier
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                    .width(175.dp),
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(description_edit_account), style = MaterialTheme.typography.bodyMedium) },
                                leadingIcon = {
                                    Icon(Icons.Outlined.Edit, null, tint = MidasColors.Blue.primary, modifier = Modifier.size(18.dp))
                                },
                                onClick = {
                                    menuExpanded = false
                                    onEdit()
                                },
                            )
                            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        stringResource(if (account.isActive) deactivate_account else activate_account),
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        if (account.isActive) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                        null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp),
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    onSetActive(!account.isActive)
                                },
                            )
                            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        stringResource(description_delete_account),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MidasColors.Red.primary,
                                    )
                                },
                                leadingIcon = {
                                    Icon(Icons.Outlined.Delete, null, tint = MidasColors.Red.primary, modifier = Modifier.size(18.dp))
                                },
                                onClick = {
                                    menuExpanded = false
                                    showDeleteDialog = true
                                },
                            )
                        }
                    }

                    Text(
                        balance.toCurrency(),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = balanceColor,
                    )
                    Text(
                        stringResource(if (isCreditCard) current_bill else current_balance),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                        fontSize = 12.sp,
                    )
                }
            }

            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
            if (isCreditCard) {
                CreditCardFooter(account)
            } else {
                FlowFooter(account)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// FOOTER VARIANTS
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Days from [today] until the next occurrence of [dueDay] (1-31) of the month.
 * If [dueDay] doesn't exist in a given month (e.g. 31 in February), it's clamped
 * to that month's last day.
 */
@OptIn(ExperimentalTime::class)
private fun daysUntilDue(
    dueDay: Int,
    today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
): Int {
    fun lastDayOf(monthStart: LocalDate): LocalDate =
        monthStart.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)

    fun dueDateIn(monthStart: LocalDate): LocalDate {
        val clampedDay = dueDay.coerceIn(1, lastDayOf(monthStart).day)
        return LocalDate(monthStart.year, monthStart.month, clampedDay)
    }

    val thisMonthStart = LocalDate(today.year, today.month, 1)
    var dueDate = dueDateIn(thisMonthStart)
    if (dueDate < today) {
        dueDate = dueDateIn(thisMonthStart.plus(1, DateTimeUnit.MONTH))
    }
    return today.daysUntil(dueDate)
}

@Composable
private fun FlowFooter(account: Account) {
    val incomeValue = account.balance.income
    val expenseValue = account.balance.expense
    val transactionCount = account.transactionCount

    val incomeLabel =
        when (account.type) {
            AccountType.SAVINGS -> stringResource(saved)
            AccountType.INVESTMENT -> stringResource(contributions)
            else -> stringResource(income)
        }
    val expenseLabel =
        when (account.type) {
            AccountType.SAVINGS -> stringResource(withdrawn)
            AccountType.INVESTMENT -> stringResource(withdrawals)
            else -> stringResource(expense)
        }

    Row(modifier = Modifier.fillMaxWidth()) {
        FooterStat(
            modifier = Modifier.weight(1f),
            iconBg = MidasColors.Green.primary.copy(alpha = 0.1f),
            icon = Icons.Outlined.ArrowDownward,
            iconTint = MidasColors.Green.primary,
            label = incomeLabel,
            value = incomeValue.toIncomeCurrency(),
            valueColor = MidasColors.Green.primary,
        )
        FooterDivider()
        FooterStat(
            modifier = Modifier.weight(1f),
            iconBg = MidasColors.Red.primary.copy(alpha = 0.1f),
            icon = Icons.Outlined.ArrowUpward,
            iconTint = MidasColors.Red.primary,
            label = expenseLabel,
            value = expenseValue.toExpenseCurrency(),
            valueColor = MidasColors.Red.primary,
        )
        FooterDivider()
        FooterStat(
            modifier = Modifier.weight(1f),
            iconBg = MaterialTheme.colorScheme.surfaceVariant,
            icon = Icons.Outlined.Receipt,
            iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
            label = stringResource(title_transactions),
            value = "$transactionCount",
            valueColor = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun CreditCardFooter(account: Account) {
    val billValue = account.balance.expense
    val daysUntilDueValue = account.dueDay?.let { daysUntilDue(it) }
    val dueColor =
        when {
            daysUntilDueValue == null -> MaterialTheme.colorScheme.onSurfaceVariant
            daysUntilDueValue <= 3 -> MidasColors.Red.primary
            else -> MidasColors.Yellow.kindaDark
        }

    Row(modifier = Modifier.fillMaxWidth()) {
        FooterStat(
            modifier = Modifier.weight(1f),
            iconBg = MidasColors.Red.primary.copy(alpha = 0.1f),
            icon = Icons.Outlined.RequestPage,
            iconTint = MidasColors.Red.primary,
            label = stringResource(bill),
            value = billValue.toCurrency(),
            valueColor = MidasColors.Red.primary,
        )
        FooterDivider()
        FooterStat(
            modifier = Modifier.weight(1f),
            iconBg = dueColor.copy(alpha = 0.1f),
            icon = Icons.Outlined.CalendarMonth,
            iconTint = dueColor,
            label = stringResource(due_in),
            value = if (daysUntilDueValue != null) "$daysUntilDueValue days" else "—",
            valueColor = dueColor,
        )
        FooterDivider()
        FooterStat(
            modifier = Modifier.weight(1f),
            iconBg = MaterialTheme.colorScheme.surfaceVariant,
            icon = Icons.Outlined.PieChart,
            iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
            label = stringResource(limit),
            value = account.creditLimit?.toCurrency() ?: "—",
            valueColor = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun FooterStat(
    modifier: Modifier,
    iconBg: Color,
    icon: ImageVector,
    iconTint: Color,
    label: String,
    value: String,
    valueColor: Color,
) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
            fontSize = 12.sp,
            maxLines = 2,
        )
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
            Box(
                modifier =
                    Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, null, tint = iconTint, modifier = Modifier.size(12.dp))
            }
            Text(
                value,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = valueColor,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                softWrap = false,
            )
        }
    }
}

@Composable
private fun FooterDivider() {
    VerticalDivider(
        modifier =
            Modifier
                .padding(vertical = 8.dp)
                .width(0.5.dp),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
    )
}

@CustomPreview
@Composable
fun AccountsScreenPreview() {
    AccountsScreen(
        navController = rememberNavController(),
        paddingValues = PaddingValues(),
        uiState = AccountUiState.Success(accounts = Database.accounts),
        viewModel = remember { AccountViewModel(FakeAccountRepository) },
    )
}

/**
 * No-op [IAccountRepository] used only to construct [AccountViewModel] for previews.
 *
 * hiltViewModel() can't resolve a real Hilt graph inside the static Compose preview
 * renderer, so previews build the ViewModel directly instead.
 */
private object FakeAccountRepository : IAccountRepository {
    override val allAccounts: Flow<List<Account>> = flowOf(Database.accounts)

    override fun getAccountByIdFlow(accountId: String): Flow<Account?> = flowOf(null)

    override suspend fun deleteAccountById(accountId: String): Result<Unit> = Result.success(Unit)

    override suspend fun getAccountCount(): Int = Database.accounts.size

    override suspend fun getTotalBalance(): Double = 0.0

    override suspend fun insert(item: Account): Result<Unit> = Result.success(Unit)

    override suspend fun insert(items: List<Account>): Result<Unit> = Result.success(Unit)

    override suspend fun update(item: Account): Result<Unit> = Result.success(Unit)

    override suspend fun delete(item: Account): Result<Unit> = Result.success(Unit)

    override suspend fun getById(id: String): Account? = null

    override suspend fun getAll(): Flow<List<Account>> = flowOf(Database.accounts)
}
