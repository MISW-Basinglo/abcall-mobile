package co.uniandes.abcall.ui.navigation

import androidx.navigation.NavController

fun NavController.goMain() {
    this.navigate(Screen.Main.route){
        popUpTo(this@goMain.graph.startDestinationId)
        launchSingleTop = true
    }
}

fun NavController.goAuth() {
    this.navigate(Screen.Auth.route){
        popUpTo(this@goAuth.graph.startDestinationId)
        launchSingleTop = true
    }
}

fun NavController.bottomNavigate(route: String) {
    this.navigate(route) {
        this@bottomNavigate.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.goCreateIssue() {
    this.navigate(Screen.Main.CreateIssue.route)
}

fun NavController.goBack() {
    this.popBackStack()
}
