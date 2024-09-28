package co.uniandes.abccall.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import co.uniandes.abccall.ui.navigation.Screen.Auth
import co.uniandes.abccall.ui.navigation.Screen.Main

@Composable
fun RootNavGraph(isLoggedIn: Boolean, navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = if(isLoggedIn) Main.route else Auth.route
    ) {
        authNavGraph(navHostController)
        mainNavGraph(navHostController)
    }
}