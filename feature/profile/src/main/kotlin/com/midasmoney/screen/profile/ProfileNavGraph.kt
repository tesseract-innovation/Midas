package com.midasmoney.screen.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class ProfileRoute(val route: String) {
    data object Main : ProfileRoute("profile_main")

    data object PersonalData : ProfileRoute("personal_data")

    data object Security : ProfileRoute("security")

    data object Plan : ProfileRoute("plan")

    data object Notifications : ProfileRoute("notifications")

    data object Language : ProfileRoute("language")

    data object Currency : ProfileRoute("currency")

    data object HelpFaq : ProfileRoute("help_faq")

    data object SupportChat : ProfileRoute("support_chat")

    data object PrivacyPolicy : ProfileRoute("privacy_policy")

    data object TermsOfUse : ProfileRoute("terms_of_use")
}

@Composable
fun ProfileNavGraph(
    navController: NavHostController,
    shouldShowBottomBar: MutableState<Boolean>,
    paddingValues: PaddingValues,
    onLogoutClick: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = ProfileRoute.Main.route,
    ) {
        composable(route = ProfileRoute.Main.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            shouldShowBottomBar.value = true
            ProfileScreen(
                paddingValues = paddingValues,
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    viewModel.logout()
                    onLogoutClick()
                },
                onPersonalDataClick = { navController.navigate(ProfileRoute.PersonalData.route) },
                onSecurityClick = { navController.navigate(ProfileRoute.Security.route) },
                onPlanClick = { navController.navigate(ProfileRoute.Plan.route) },
                onNotificationsClick = { navController.navigate(ProfileRoute.Notifications.route) },
                onLanguageClick = { navController.navigate(ProfileRoute.Language.route) },
                onCurrencyClick = { navController.navigate(ProfileRoute.Currency.route) },
                onHelpFaqClick = { navController.navigate(ProfileRoute.HelpFaq.route) },
                onSupportChatClick = { navController.navigate(ProfileRoute.SupportChat.route) },
                onPrivacyPolicyClick = { navController.navigate(ProfileRoute.PrivacyPolicy.route) },
                onTermsOfUseClick = { navController.navigate(ProfileRoute.TermsOfUse.route) },
            )
        }

        composable(route = ProfileRoute.PersonalData.route) {
            shouldShowBottomBar.value = false
            PersonalDataScreen(
                uiState = PersonalDataUiState(),
                onBackClick = { navController.popBackStack() },
                onSaveClick = { /* Handle save */ },
            )
        }

        composable(route = ProfileRoute.Security.route) {
            shouldShowBottomBar.value = false
            SecurityScreen(
                uiState = SecurityUiState(),
                onBackClick = { navController.popBackStack() },
                onChangePasswordClick = {},
                onChangePin = {},
                onManageSessions = {},
                onBiometricToggle = {},
                on2FAToggle = {},
            )
        }

        composable(route = ProfileRoute.Plan.route) {
            shouldShowBottomBar.value = false
            PlanScreen(
                uiState = PlanUiState(),
                onBackClick = { navController.popBackStack() },
                onCancelPlan = {},
                onUpgrade = {},
                onChangePayment = {},
            )
        }

        composable(route = ProfileRoute.Notifications.route) {
            shouldShowBottomBar.value = false
            NotificationsScreen(
                uiState = NotificationsUiState(),
                onBackClick = { navController.popBackStack() },
                onStateChange = { /* Handle change */ },
            )
        }

        composable(route = ProfileRoute.Language.route) {
            shouldShowBottomBar.value = false
            LanguageScreen(
                currentLanguageCode = "pt-BR",
                onBackClick = { navController.popBackStack() },
                onLanguageSelected = { /* Handle selection */ },
            )
        }

        composable(route = ProfileRoute.Currency.route) {
            shouldShowBottomBar.value = false
            CurrencyScreen(
                currentCurrencyCode = "BRL",
                onBackClick = { navController.popBackStack() },
                onCurrencySelected = { /* Handle selection */ },
            )
        }

        composable(route = ProfileRoute.HelpFaq.route) {
            shouldShowBottomBar.value = false
            HelpFaqScreen(
                onBackClick = { navController.popBackStack() },
                onContactSupport = { navController.navigate(ProfileRoute.SupportChat.route) },
            )
        }

        composable(route = ProfileRoute.SupportChat.route) {
            shouldShowBottomBar.value = false
            SupportChatScreen(
                onBackClick = { navController.popBackStack() },
            )
        }

        composable(route = ProfileRoute.PrivacyPolicy.route) {
            shouldShowBottomBar.value = false
            PrivacyPolicyScreen(
                onBackClick = { navController.popBackStack() },
            )
        }

        composable(route = ProfileRoute.TermsOfUse.route) {
            shouldShowBottomBar.value = false
            TermsOfUseScreen(
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
