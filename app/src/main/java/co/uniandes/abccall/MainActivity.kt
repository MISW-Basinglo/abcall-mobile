package co.uniandes.abccall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import co.uniandes.abccall.ui.navigation.RootNavGraph
import co.uniandes.abccall.ui.theme.ABCcallTheme

class MainActivity : ComponentActivity() {

    private var isAuthenticated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ABCcallTheme {
                val navController = rememberNavController()
                RootNavGraph(
                    isLoggedIn = isAuthenticated,
                    navHostController = navController
                )
            }
        }
    }

}
