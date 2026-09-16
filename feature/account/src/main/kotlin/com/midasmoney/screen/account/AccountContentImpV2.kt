package com.midasmoney.screen.account

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import com.midasmoney.core.resource.R.string.delete
import com.midasmoney.core.resource.R.string.description_add_account
import com.midasmoney.core.resource.R.string.edit
import com.midasmoney.core.resource.R.string.error_load_accounts
import com.midasmoney.core.resource.R.string.expense
import com.midasmoney.core.resource.R.string.income
import com.midasmoney.core.resource.R.string.label_statement
import com.midasmoney.core.resource.R.string.no_accounts
import com.midasmoney.core.resource.R.string.total_balance
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
                        val totalBalance = accounts.sumOf { it.balance.currentBalance }
                        val totalIncome = accounts.sumOf { it.balance.income }
                        val totalExpense = accounts.sumOf { it.balance.expense }

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
                                accountCount = accounts.size,
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
                                            .padding(top = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    AccountsListHeader(count = accounts.size)
                                    accounts.forEach { account ->
                                        if (account.type == AccountType.CREDIT_CARD) {
                                            AccountCardCreditCard(
                                                account = account,
                                                onClick = {
                                                    navController.navigate(AccountRoute.AccountDetails(account))
                                                },
                                                onEdit = {
                                                    navController.navigate(AccountRoute.AccountForm(account))
                                                },
                                                onDelete = { viewModel.deleteAccount(account) },
                                                onStatement = {
                                                    navController.navigate(AccountRoute.AccountDetails(account))
                                                },
                                            )
                                        } else {
                                            AccountCard(
                                                account = account,
                                                onClick = {
                                                    navController.navigate(AccountRoute.AccountDetails(account))
                                                },
                                                onEdit = {
                                                    navController.navigate(AccountRoute.AccountForm(account))
                                                },
                                                onDelete = { viewModel.deleteAccount(account) },
                                                onStatement = {
                                                    navController.navigate(AccountRoute.AccountDetails(account))
                                                },
                                            )
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
    val netFlow = totalIncome - totalExpense

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
                    value = totalIncome.toCurrency(),
                    icon = Icons.Outlined.ArrowDownward,
                    iconColor = MidasColors.Green.primary,
                )
                AccountsHeroStatDivider()
                AccountsHeroStat(
                    modifier = Modifier.weight(1f),
                    label = stringResource(expense),
                    value = totalExpense.toCurrency(),
                    icon = Icons.Outlined.ArrowUpward,
                    iconColor = MidasColors.Red.primary,
                )
                AccountsHeroStatDivider()
                AccountsHeroStat(
                    modifier = Modifier.weight(1f),
                    label = "Net",
                    value = netFlow.toCurrency(),
                    icon = Icons.AutoMirrored.Outlined.TrendingUp,
                    iconColor = if (netFlow >= 0) MidasColors.Green.primary else MidasColors.Red.primary,
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
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            "$count active",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun AccountCard(
    account: Account,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onStatement: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

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
    val accountIncome = account.balance.income
    val accountExpense = account.balance.expense
    val transactionCount = account.transactions.size
    val maxFlow = maxOf(accountIncome, accountExpense).coerceAtLeast(1.0)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier =
            Modifier
                .fillMaxWidth()
                // Colored accent bar indicating the account's own color.
                .drawBehind {
                    drawRect(
                        color = accentColor,
                        topLeft = Offset(0f, 12.dp.toPx()),
                        size = Size(3.dp.toPx(), size.height - 24.dp.toPx()),
                    )
                },
    ) {
        Column {
            Column(
                modifier =
                    Modifier
                        .clickable(onClick = onClick)
                        .padding(14.dp),
            ) {
                Row(
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
                        Text(
                            balance.toCurrency(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (balance >= 0) MaterialTheme.colorScheme.onSurface else MidasColors.Red.primary,
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color =
                            if (balance >= 0) {
                                MidasColors.Green.primary.copy(alpha = 0.12f)
                            } else {
                                MidasColors.Red.primary.copy(alpha = 0.12f)
                            },
                    ) {
                        Text(
                            if (balance >= 0) "+ ${accountIncome.toCurrency()}" else "- ${accountExpense.toCurrency()}",
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (balance >= 0) MidasColors.Green.primary else MidasColors.Red.primary,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    StatWithProgress(
                        modifier = Modifier.weight(1f),
                        label = stringResource(income),
                        value = accountIncome.toCurrency(),
                        valueColor = MidasColors.Green.primary,
                        progress = (accountIncome / maxFlow).toFloat(),
                        progressColor = MidasColors.Green.primary,
                        icon = Icons.Outlined.ArrowDownward,
                    )
                    VerticalDivider(
                        modifier = Modifier.padding(horizontal = 6.dp).height(36.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                    )
                    StatWithProgress(
                        modifier = Modifier.weight(1f),
                        label = stringResource(expense),
                        value = accountExpense.toCurrency(),
                        valueColor = MidasColors.Red.primary,
                        progress = (accountExpense / maxFlow).toFloat(),
                        progressColor = MidasColors.Red.primary,
                        icon = Icons.Outlined.ArrowUpward,
                    )
                    VerticalDivider(
                        modifier = Modifier.padding(horizontal = 6.dp).height(36.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                    )
                    StatWithProgress(
                        modifier = Modifier.weight(1f),
                        label = "Transactions",
                        value = "$transactionCount",
                        valueColor = MaterialTheme.colorScheme.onSurface,
                        progress = (transactionCount / 20f).coerceIn(0f, 1f),
                        progressColor = accentColor,
                        icon = Icons.Outlined.Receipt,
                    )
                }
            }

            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(42.dp),
            ) {
                ActionFooterButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Edit,
                    label = stringResource(edit),
                    color = MidasColors.Blue.primary,
                    onClick = onEdit,
                )
                VerticalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
                ActionFooterButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Receipt,
                    label = stringResource(label_statement),
                    color = MidasColors.Green.primary,
                    onClick = onStatement,
                )
                VerticalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
                ActionFooterButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Delete,
                    label = stringResource(delete),
                    color = MidasColors.Red.primary,
                    onClick = { showDeleteDialog = true },
                )
            }
        }
    }
}

@Composable
private fun StatWithProgress(
    modifier: Modifier,
    label: String,
    value: String,
    valueColor: Color,
    progress: Float,
    progressColor: Color,
    icon: ImageVector,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            Icon(icon, null, tint = progressColor, modifier = Modifier.size(9.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 9.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(value, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = valueColor)
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(2.dp))
                        .background(progressColor),
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// CREDIT CARD ACCOUNT CARD
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
private fun AccountCardCreditCard(
    account: Account,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onStatement: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

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
    val expense = account.balance.expense
    val transactionCount = account.transactions.size
    val dueDay = account.dueDay
    val daysUntilDue = dueDay?.let { daysUntilDue(it) }
    val hasAlert = daysUntilDue != null && daysUntilDue <= 3

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Column(
                modifier =
                    Modifier
                        .clickable(onClick = onClick)
                        .padding(14.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box {
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
                        if (hasAlert) {
                            Box(
                                modifier =
                                    Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(MidasColors.Red.primary)
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.surfaceContainer,
                                            shape = CircleShape,
                                        )
                                        .align(Alignment.BottomEnd),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(Icons.Outlined.Warning, null, tint = MidasColors.White, modifier = Modifier.size(8.dp))
                            }
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            account.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            account.type.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 9.sp,
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            balance.toCurrency(),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (balance < 0) MidasColors.Red.primary else MidasColors.Green.primary,
                        )
                        Text(
                            "Open bill",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 9.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    CreditCardStat(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.ArrowUpward,
                        iconBg = MidasColors.Red.primary.copy(alpha = 0.1f),
                        iconTint = MidasColors.Red.primary,
                        label = "Bill",
                        value = expense.toCurrency(),
                        valueColor = MidasColors.Red.primary,
                    )
                    VerticalDivider(
                        modifier = Modifier.padding(horizontal = 6.dp).height(36.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                    )
                    CreditCardStat(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.CalendarToday,
                        iconBg = MidasColors.Blue.primary.copy(alpha = 0.1f),
                        iconTint = MidasColors.Blue.primary,
                        label = "Due in",
                        value = if (daysUntilDue != null) "$daysUntilDue days" else "—",
                        valueColor = MidasColors.Blue.primary,
                    )
                    VerticalDivider(
                        modifier = Modifier.padding(horizontal = 6.dp).height(36.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                    )
                    CreditCardStat(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.PieChart,
                        iconBg = MaterialTheme.colorScheme.surfaceVariant,
                        iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
                        label = "Limit",
                        value = account.creditLimit?.toCurrency() ?: "—",
                        valueColor = MaterialTheme.colorScheme.onSurface,
                    )
                }

                if (transactionCount > 0) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "$transactionCount transactions",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(42.dp),
            ) {
                ActionFooterButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Edit,
                    label = stringResource(edit),
                    color = MidasColors.Blue.primary,
                    onClick = onEdit,
                )
                VerticalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
                ActionFooterButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Receipt,
                    label = stringResource(label_statement),
                    color = MidasColors.Green.primary,
                    onClick = onStatement,
                )
                VerticalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
                ActionFooterButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Delete,
                    label = stringResource(delete),
                    color = MidasColors.Red.primary,
                    onClick = { showDeleteDialog = true },
                )
            }
        }
    }
}

@Composable
private fun CreditCardStat(
    modifier: Modifier,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    label: String,
    value: String,
    valueColor: Color,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
        Box(
            modifier =
                Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconBg),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, null, tint = iconTint, modifier = Modifier.size(13.dp))
        }
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 9.sp)
            Text(value, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = valueColor)
        }
    }
}

@Composable
private fun ActionFooterButton(
    modifier: Modifier,
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxHeight()
                .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, null, tint = color, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = color)
    }
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
