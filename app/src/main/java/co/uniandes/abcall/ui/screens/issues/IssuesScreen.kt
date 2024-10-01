package co.uniandes.abcall.ui.screens.issues

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import co.uniandes.abcall.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.ui.components.IssueItem
import co.uniandes.abcall.ui.navigation.BottomBar
import co.uniandes.abcall.ui.navigation.Screen.Main.CreateIssue
import co.uniandes.abcall.ui.navigation.goCreateIssue
import co.uniandes.abcall.ui.navigation.goMain

@Composable
fun IssuesScreen(navController: NavController, viewModel: IssuesViewModel = hiltViewModel()) {
    val items by viewModel.issues.observeAsState(emptyList())

    val updateState by viewModel.updateState.observeAsState(UpdateState.Idle)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.goCreateIssue()
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.issue_create_content_description),
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

            when (updateState) {
                is UpdateState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.5f))
                            .clickable(enabled = false) {}
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                is UpdateState.Success -> {
                    viewModel.resetState()
                }
                is UpdateState.Error -> {
                    viewModel.resetState()
                    val errorMessage = (updateState as UpdateState.Error).message
                    Toast.makeText(navController.context, errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> { }
            }
        }
    }
}
