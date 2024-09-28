package co.uniandes.abccall.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import co.uniandes.abccall.ui.navigation.Screen.Main.Issues

@Composable
fun LoginScreen(navController: NavController) {
    Column {
        Text(text = "Login Screen")
        Button(
            onClick = { 
                navController.navigate(Issues.route)
            }
        ) {
            Text(text = "INGRESAR")
        }
    }
}
