package co.uniandes.abccall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.uniandes.abccall.ui.navigation.BottomBar
import co.uniandes.abccall.ui.navigation.RootNavGraph
import co.uniandes.abccall.ui.navigation.Screen.Main.Chat
import co.uniandes.abccall.ui.navigation.Screen.Main.Issues
import co.uniandes.abccall.ui.navigation.Screen.Main.CreateIssue
import co.uniandes.abccall.ui.navigation.Screen.Main.Settings
import co.uniandes.abccall.ui.navigation.TopBar
import co.uniandes.abccall.ui.theme.ABCcallTheme

class MainActivity : ComponentActivity() {

    private var isAuthenticated = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ABCcallTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                val topBarState = rememberSaveable { (mutableStateOf(false)) }
                val topBarTitle = rememberSaveable { (mutableStateOf("")) }

                // Control TopBar and BottomBar
                when (navBackStackEntry?.destination?.route) {
                    Issues.route -> {
                        bottomBarState.value = true
                        topBarState.value = false
                    }
                    Chat.route ->  {
                        bottomBarState.value = true
                        topBarState.value = false
                    }
                    Settings.route -> {
                        bottomBarState.value = true
                        topBarState.value = false
                    }
                    CreateIssue.route -> {
                        bottomBarState.value = false
                        topBarState.value = true
                        topBarTitle.value = stringResource(id = R.string.create_issue)
                    }
                    else -> bottomBarState.value = false
                }
                Scaffold(
                    topBar = {
                        if (topBarState.value) TopBar(
                            title = topBarTitle.value.uppercase(),
                            navController = navController
                        )
                    },
                    bottomBar = {
                        if (bottomBarState.value) BottomBar(navController = navController)
                    }
                ) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        RootNavGraph(
                            isLoggedIn = isAuthenticated,
                            navHostController = navController
                        )
                    }
                }
            }
        }
    }

}
