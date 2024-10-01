package co.uniandes.abcall.ui.screens.issues

import co.uniandes.abcall.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.uniandes.abcall.data.models.Issue
import co.uniandes.abcall.ui.components.IssueItem
import co.uniandes.abcall.ui.navigation.BottomBar
import co.uniandes.abcall.ui.navigation.Screen.Main.CreateIssue

@Composable
fun IssuesScreen(navController: NavController) {

    val items = listOf(
        Issue(
            "Título 1",
            "Lorem ipsum dolor sit amet consectetur adipiscing elit turpis, porta litora risus penatibus curabitur et enim egestas...",
            "29/02/2024 18:50:33",
            "Cerrado"
        ),
        Issue(
            "Título 2",
            "Lorem ipsum dolor sit amet consectetur adipiscing elit turpis, porta litora risus penatibus curabitur et enim egestas...",
            "29/02/2024 18:50:33",
            "Escalado"
        ),
        Issue(
            "Título 3",
            "Lorem ipsum dolor sit amet consectetur adipiscing elit turpis, porta litora risus penatibus curabitur et enim egestas...",
            "29/02/2024 18:50:33",
            "Abierto"
        ),
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(CreateIssue.route)
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Issue",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 55.dp, bottom = 83.dp ),
                    text = stringResource(id = R.string.issues).uppercase(),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items.size) { index ->
                        val item = items[index]
                        IssueItem(item)
                        if (index < items.size - 1) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = colorResource(id = R.color.grey_light))
                        }
                    }
                }
            }
        }
    }
}
