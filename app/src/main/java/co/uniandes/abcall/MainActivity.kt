package co.uniandes.abcall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import co.uniandes.abcall.ui.navigation.RootNavGraph
import co.uniandes.abcall.ui.theme.ABCallTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isAuthenticated = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ABCallTheme {
                val navController = rememberNavController()
                RootNavGraph(
                    isLoggedIn = isAuthenticated,
                    navHostController = navController
                )
            }
        }
    }

}
