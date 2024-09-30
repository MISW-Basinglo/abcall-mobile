package co.uniandes.abccall.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import co.uniandes.abccall.ui.navigation.Screen.Auth
import co.uniandes.abccall.ui.navigation.Screen.Main

@Composable
fun RootNavGraph(isLoggedIn: Boolean, navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        startDestination = if(isLoggedIn) Main.route else Auth.route
    ) {
        authNavGraph(navHostController)
        mainNavGraph(navHostController)
    }
}