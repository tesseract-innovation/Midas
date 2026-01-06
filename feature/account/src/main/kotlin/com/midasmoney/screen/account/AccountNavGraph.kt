package com.midasmoney.screen.account

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.util.serializer.serializableNavType
import com.midasmoney.screen.account.accountform.AccountFormScreen
import com.midasmoney.screen.account.accountdetail.AccountDetails
import com.midasmoney.screen.account.transactionform.TransactionFormScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Suppress("unused")
@Serializable
sealed class AccountRoute(val route: String) {
    @Serializable
    data object Main : AccountRoute("Account")

    @Serializable
    data class AccountDetails(val account: Account) : AccountRoute("Account_details")

    @Serializable
    data class AccountForm(val account: Account? = null) : AccountRoute("Account_form")

    @Serializable
    data class TransactionForm(val account: Account? = null, val transaction: Transaction? = null) : AccountRoute("Transaction_form")
}

@Composable
fun AccountNavGraph(
    navController: NavHostController,
    shouldShowBottomBar: MutableState<Boolean>,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = AccountRoute.Main
    ) {
        composable<AccountRoute.Main> {
            shouldShowBottomBar.value = true
            AccountContentImp(
                navController = navController,
                paddingValues = paddingValues
            )
        }

        composable<AccountRoute.AccountDetails>(
            typeMap = mapOf(typeOf<Account>() to serializableNavType<Account>())
        ) {
            val args = it.toRoute<AccountRoute.AccountDetails>()
            // shouldShowBottomBar.value = false
            AccountDetails(
                args = args,
                navController = navController,
                paddingValues = paddingValues
            )
        }

        composable<AccountRoute.AccountForm>(
            typeMap = mapOf(typeOf<Account?>() to serializableNavType<Account?>(isNullableAllowed = true))
        ) {
            val args = it.toRoute<AccountRoute.AccountForm>()
            // shouldShowBottomBar.value = false
            AccountFormScreen(
                args = args,
                navController = navController,
                paddingValues = paddingValues,
            )
        }

        composable<AccountRoute.TransactionForm>(
            typeMap = mapOf(typeOf<Account?>() to serializableNavType<Account?>(isNullableAllowed = true),
                typeOf<Transaction?>() to serializableNavType<Transaction?>(isNullableAllowed = true))
        ) {
            val args = it.toRoute<AccountRoute.TransactionForm>()
            // shouldShowBottomBar.value = false
            TransactionFormScreen(
                args = args,
                navController = navController,
                paddingValues = paddingValues,
            )
        }
    }
}
