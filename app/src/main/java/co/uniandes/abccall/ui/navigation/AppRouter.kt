package co.uniandes.abccall.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import co.uniandes.abccall.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings

private object Route {
    const val AUTH = "auth"
    const val LOGIN = "login"

    const val MAIN = "main"
    const val ISSUES = "issues"
    const val CREATE_ISSUE = "issues/create"
    const val CHAT = "chat"
    const val SETTINGS = "settings"
}

sealed class Screen(val route: String) {

    data object Auth: Screen(Route.AUTH) {
        data object Login: Screen(Route.LOGIN)
    }

    data object Main: TopLevelDestination(Route.MAIN) {
        data object Issues: TopLevelDestination(
            route = Route.ISSUES,
            title = R.string.issues,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
        )
        data object CreateIssue: TopLevelDestination(Route.CREATE_ISSUE)
        data object Chat: TopLevelDestination(
            route = Route.CHAT,
            title = R.string.chat,
            selectedIcon = Icons.Filled.Send,
            unselectedIcon = Icons.Outlined.Send,
        )
        data object Settings: TopLevelDestination(
            route = Route.SETTINGS,
            title = R.string.settings,
            selectedIcon = Icons.Outlined.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        )
    }

}

sealed class TopLevelDestination(
    val route: String,
    val title: Int? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val navArguments: List<NamedNavArgument> = emptyList()
)
