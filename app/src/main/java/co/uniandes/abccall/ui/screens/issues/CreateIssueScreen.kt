package co.uniandes.abccall.ui.screens.issues

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.uniandes.abccall.R
import co.uniandes.abccall.ui.navigation.TopBar
import co.uniandes.abccall.ui.navigation.goBack

@Composable
fun CreateIssueScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.create_issue).uppercase(),
                navigationAction = { navController.goBack() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(text = "Create Issues Screen")
        }
    }
}
