package com.midasmoney.screen.account

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.resource.R.string.description_add_account
import com.midasmoney.core.resource.R.string.description_delete_account
import com.midasmoney.core.resource.R.string.description_edit_account
import com.midasmoney.core.resource.R.string.error_load_accounts
import com.midasmoney.core.resource.R.string.expense
import com.midasmoney.core.resource.R.string.income
import com.midasmoney.core.resource.R.string.no_accounts
import com.midasmoney.core.resource.R.string.total_balance
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import com.midasmoney.screen.account.component.DeleteDialog

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
                                            .padding(horizontal = 20.dp)
                                            .padding(top = 20.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    accounts.forEach { account ->
                                        AccountCard(
                                            account = account,
                                            onClick = {
                                                navController.navigate(AccountRoute.AccountDetails(account))
                                            },
                                            onEdit = {
                                                navController.navigate(AccountRoute.AccountForm(account))
                                            },
                                            onDelete = { viewModel.deleteAccount(account) },
                                        )
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
                                MidasColors.Blue.extraDark,
                                MidasColors.Blue.dark,
                                MidasColors.Blue.kindaDark,
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
            Text(
                "$accountCount ${if (accountCount == 1) "Account" else "Accounts"}",
                style = MaterialTheme.typography.bodySmall,
                color = MidasColors.White.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp),
            )

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
                Box(
                    modifier =
                        Modifier
                            .width(0.5.dp)
                            .height(40.dp)
                            .background(MidasColors.White.copy(alpha = 0.12f))
                            .align(Alignment.CenterVertically),
                )
                AccountsHeroStat(
                    modifier = Modifier.weight(1f),
                    label = stringResource(expense),
                    value = totalExpense.toCurrency(),
                    icon = Icons.Outlined.ArrowUpward,
                    iconColor = MidasColors.Red.primary,
                )
            }
        }
    }
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
private fun AccountCard(
    account: Account,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
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
    val color = ColorConverter.aRgbToColor(account.color)

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier =
                Modifier
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
                    modifier =
                        Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(color),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(icon, null, tint = MidasColors.White, modifier = Modifier.size(22.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        account.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        account.balance.currentBalance.toCurrency(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                // Action buttons
                Row {
                    IconButton(onClick = onEdit, modifier = Modifier.size(36.dp)) {
                        Icon(
                            Icons.Outlined.Edit,
                            stringResource(description_edit_account),
                            tint = MidasColors.Blue.primary,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }, modifier = Modifier.size(36.dp)) {
                        Icon(
                            Icons.Outlined.Delete,
                            stringResource(description_delete_account),
                            tint = MidasColors.Red.primary,
                            modifier = Modifier.size(18.dp),
                        )
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
                AccountStat(label = stringResource(income), value = account.balance.income, color = MidasColors.Green.primary)
                AccountStat(label = stringResource(expense), value = account.balance.expense, color = MidasColors.Red.primary)
            }
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
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            value.toCurrency(),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = color,
        )
    }
}
