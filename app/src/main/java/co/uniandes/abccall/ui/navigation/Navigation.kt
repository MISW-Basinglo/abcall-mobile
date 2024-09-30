package co.uniandes.abccall.ui.navigation

import androidx.navigation.NavController

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

fun NavController.goBack() {
    this.popBackStack()
}
