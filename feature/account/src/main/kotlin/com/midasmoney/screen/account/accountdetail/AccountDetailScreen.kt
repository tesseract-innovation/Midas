package com.midasmoney.screen.account.accountdetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.resource.R
import com.midasmoney.core.resource.R.string.description_add_account
import com.midasmoney.core.ui.component.TransactionCard
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import com.midasmoney.screen.account.AccountRoute

private const val TAG = "AccountDetailScreen"

@Composable
fun AccountDetails(
    args: AccountRoute.AccountDetails,
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: AccountDetailViewModel = hiltViewModel()
) {
    val account = args.account
    val transactions = viewModel.transactions.collectAsStateWithLifecycle()
    val totalBalance = viewModel.totalBalance.collectAsStateWithLifecycle()
    viewModel.loadTransactions(account.id.toString())
    AccountDetailsImp(
        account = account,
        totalBalance = totalBalance,
        transactions = transactions,
        navController = navController,
        paddingValues = paddingValues
    )
}

@Composable
private fun AccountDetailsImp(
    account: Account,
    totalBalance: State<Double?>,
    transactions: State<List<Transaction>>,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                ) {
                    AccountDetailHeader(
                        account = account,
                        totalBalance = totalBalance
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = stringResource(R.string.title_transactions),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                )

                // No transactions
                if (transactions.value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_transactions),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MidasColors.Gray,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                    )
                } else {
                    // List all transactions
                    ShowAllTransactions(transactions, navController, account)
                }
            }

            // Floating Action Button
            FloatingActionButton(
                onClick = {
                    navController.navigate(AccountRoute.TransactionForm(account))
                    Log.d(TAG, "Floating Action Button clicked!")
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MidasColors.Purple.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(description_add_account),
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }
        }
    }
}

@Composable
private fun ShowAllTransactions(
    transactions: State<List<Transaction>>,
    navController: NavHostController,
    account: Account
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(transactions.value.size) { index ->
            TransactionCard(
                transaction = transactions.value[index],
                onClick = {
                    navController.navigate(
                        AccountRoute.TransactionForm(
                            account,
                            transactions.value[index]
                        )
                    )
                },
                paddingStart = 12.dp,
                paddingEnd = 12.dp,
                paddingTop = 0.dp
            )
        }
    }
}

@Composable
fun AccountDetailHeader(
    account: Account,
    totalBalance: State<Double?>
) {
    val icon = IconConverter.getImageVector(account.icon)
    val color = ColorConverter.aRgbToColor(account.color)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = account.name,
            tint = color,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f))
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = account.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.balance) + ": ${totalBalance.value?.toCurrency()}",
                style = MaterialTheme.typography.titleMedium,
                color = MidasColors.Gray // Or adapt based on theme
            )
        }
    }
}

@CustomPreview
@Composable
fun AccountDetailsDarkPreview() {
    MidasTheme {
        val paddingValues = PaddingValues()
        val navController = rememberNavController()
        val mockAccount = Database.accounts.first()
        val args = AccountRoute.AccountDetails(
            account = Account(
                name = mockAccount.name,
                icon = mockAccount.icon,
                color = mockAccount.color,
                balance = mockAccount.balance,
                transactions = mockAccount.transactions,
                id = mockAccount.id
            )
        )
        val transactions = remember { mutableStateOf(mockAccount.transactions) }
        val totalBalance = remember { mutableStateOf<Double?>(mockAccount.balance.currentBalance) }
        AccountDetailsImp(
            account = args.account,
            totalBalance = totalBalance,
            transactions = transactions,
            navController,
            paddingValues
        )
    }
}
