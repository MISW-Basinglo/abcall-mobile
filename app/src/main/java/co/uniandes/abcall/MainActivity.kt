package co.uniandes.abcall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import co.uniandes.abcall.storage.LocalStorage
import co.uniandes.abcall.ui.navigation.RootNavGraph
import co.uniandes.abcall.ui.theme.ABCallTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var localStorage: LocalStorage

    private var isAuthenticated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isAuthenticated = localStorage.getAccessToken() != null

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
