package co.uniandes.abccall.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import co.uniandes.abccall.ui.navigation.Screen.Main

@Composable
fun BottomBar(
    navController: NavController
) {
    val navigationScreen = listOf(Main.Issues, Main.Chat, Main.Settings)

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationScreen.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                label = {
                    Text(text = stringResource(id = item.title!!))
                },
                icon = {
                    Icon(
                        imageVector = (
                                if (item.route == currentRoute)
                                    item.selectedIcon
                                else
                                    item.unselectedIcon
                                )!!,
                        contentDescription = stringResource(id = item.title!!)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.tertiary
                ),
                onClick = {
                    navController.bottomNavigate(item.route)
                },
            )
        }
    }
}