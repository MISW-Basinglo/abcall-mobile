package co.uniandes.abcall.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import co.uniandes.abcall.ui.navigation.Screen.Main
import co.uniandes.abcall.ui.screens.chat.ChatScreen
import co.uniandes.abcall.ui.screens.issues.CreateIssueScreen
import co.uniandes.abcall.ui.screens.issues.IssuesScreen
import co.uniandes.abcall.ui.screens.settings.SettingsScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Main.route,
        startDestination = Main.Issues.route,
    ) {

        composable(route = Main.Issues.route) {
            IssuesScreen(navController)
        }

        composable(route = Main.CreateIssue.route) {
            CreateIssueScreen(navController)
        }

        composable(route = Main.Chat.route) {
            ChatScreen(navController)
        }

        composable(route = Main.Settings.route) {
            SettingsScreen(navController)
        }

    }
}