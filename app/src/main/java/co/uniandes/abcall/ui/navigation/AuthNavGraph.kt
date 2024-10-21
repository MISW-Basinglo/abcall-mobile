package co.uniandes.abcall.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import co.uniandes.abcall.ui.navigation.Screen.Auth
import co.uniandes.abcall.ui.screens.auth.LoginScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Auth.route,
        startDestination = Auth.Login.route,
    ) {

        composable(route = Auth.Login.route) {
            LoginScreen(navController)
        }

    }
}