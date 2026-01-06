package com.midasmoney.screen.account

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.resource.R.string.accounts
import com.midasmoney.core.resource.R.string.description_add_account
import com.midasmoney.core.resource.R.string.description_delete_account
import com.midasmoney.core.resource.R.string.description_edit_account
import com.midasmoney.core.resource.R.string.error_load_accounts
import com.midasmoney.core.resource.R.string.expense
import com.midasmoney.core.resource.R.string.income
import com.midasmoney.core.resource.R.string.no_accounts
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.ui.component.MidasTitleItem
import com.midasmoney.core.ui.preview.MidasDarkPreview
import com.midasmoney.core.ui.preview.MidasLightPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.screen.account.component.DeleteDialog

private const val TAG = "AccountContentImp"

@Composable
fun AccountContentImp(
    navController: NavController,
    paddingValues: PaddingValues
) {
    val viewModel: AccountViewModel = hiltViewModel<AccountViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AccountContent(
        navController = navController,
        paddingValues = paddingValues,
        uiState = uiState,
        viewModel = viewModel
    )
}

@Suppress("unused")
@Composable
fun AccountContent(
    navController: NavController,
    paddingValues: PaddingValues,
    uiState: AccountUiState,
    viewModel: AccountViewModel,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (uiState) {
                is AccountUiState.Loading -> {
                    Log.d(TAG, "UIState - Loading")
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AccountUiState.Success -> {
                    Log.d(TAG, "UIState - Success")
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        MidasTitleItem(stringResource(accounts))

                        if (uiState.accounts.isEmpty()) {
                            Log.d(TAG, "UIState accounts is empty")
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Log.d(TAG, "UIState render main box")
                                Text(
                                    text = stringResource(no_accounts),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MidasColors.Gray
                                )
                            }
                        } else {
                            Log.d(TAG, "UISate is not empty")
                            uiState.accounts.forEach { account ->
                                Log.d(TAG, "Account: $account")
                                AccountCard(account, viewModel, navController)
                            }
                        }
                    }
                }

                is AccountUiState.Error -> {
                    Log.d(TAG, "UIState - Error")
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stringResource(error_load_accounts),
                                style = MaterialTheme.typography.titleMedium,
                                color = MidasColors.Red.primary
                            )
                            Text(
                                text = uiState.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MidasColors.Gray
                            )
                        }
                    }
                }
            }

            // Floating Action Button
            FloatingActionButton(
                onClick = {
                    navController.navigate(AccountRoute.AccountForm())
                    Log.d(TAG, "Floating Action Button clicked!")
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MidasColors.Blue.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(description_add_account),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun AccountCard(
    account: Account,
    viewModel: AccountViewModel,
    navController: NavController,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val icon = account.icon.let {
        IconConverter.getImageVector(it)
    }
    val color = account.color.let {
        ColorConverter.aRgbToColor(it)
    }

    if (showDeleteDialog) {
        DeleteDialog(
            titleItem = account.name,
            onConfirm = {
                viewModel.deleteAccount(account)
                showDeleteDialog = false
            },
            onDismiss = {
                showDeleteDialog = false
            })
    }

    Column(
        modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable {
                    Log.d(TAG, "Account ${account.name} was clicked!")
                    navController.navigate(AccountRoute.AccountDetails(account))
                }) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 15.dp, end = 15.dp),
                ) {
                    AccountGeneralInfo(account, icon, color, isDarkTheme)
                    AccountTransactionInfo(account, isDarkTheme)
                }

                // Action buttons
                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Edit button
                    IconButton(
                        onClick = {
                            navController.navigate(
                                AccountRoute.AccountForm(account)
                            )
                        }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(description_edit_account),
                            tint = MidasColors.Blue.primary
                        )
                    }

                    // Delete button
                    IconButton(
                        onClick = {
                            showDeleteDialog = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(description_delete_account),
                            tint = MidasColors.Red.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AccountGeneralInfo(
    account: Account,
    icon: ImageVector,
    color: Color,
    isDarkTheme: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = icon.name,
                tint = color,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.2f))
                    .padding(10.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.Start, modifier = Modifier.padding(12.dp)
        ) {
            Row {
                Text(
                    text = account.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(
                    text = account.balance.totalValue.toCurrency(),
                    fontSize = 15.sp,
                    color = if (isDarkTheme) MidasColors.Gray else MidasColors.Gray,
                    fontWeight = FontWeight.W400
                )
            }
        }
    }
}

@Composable
private fun AccountTransactionInfo(
    account: Account,
    isDarkTheme: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = stringResource(income),
                fontSize = 15.sp,
                color = if (isDarkTheme) MidasColors.Gray else MidasColors.Gray,
                fontWeight = FontWeight.W400
            )
            Text(
                text = account.balance.income.toCurrency(),
                fontWeight = FontWeight.Bold,
                color = MidasColors.Green.primary
            )
        }

        Column {
            Text(
                text = stringResource(expense),
                fontSize = 15.sp,
                color = if (isDarkTheme) MidasColors.Gray else MidasColors.Gray,
                fontWeight = FontWeight.W400
            )
            Text(
                text = account.balance.expense.toCurrency(),
                fontWeight = FontWeight.Bold,
                color = MidasColors.Red.primary
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun AccountContentLightPreview() {
    MidasLightPreview {
        val paddingValues = PaddingValues()
        val navController = rememberNavController()
        val viewModel: AccountViewModel = hiltViewModel<AccountViewModel>()
        AccountContent(
            navController = navController,
            paddingValues = paddingValues,
            uiState = AccountUiState.Success(Database.accounts),
            viewModel = viewModel,
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun AccountContentDarkPreview() {
    MidasDarkPreview {
        val paddingValues = PaddingValues()
        val navController = rememberNavController()
        val viewModel: AccountViewModel = hiltViewModel<AccountViewModel>()
        AccountContent(
            navController = navController,
            paddingValues = paddingValues,
            uiState = AccountUiState.Success(Database.accounts),
            viewModel = viewModel,
            isDarkTheme = true
        )
    }
}
