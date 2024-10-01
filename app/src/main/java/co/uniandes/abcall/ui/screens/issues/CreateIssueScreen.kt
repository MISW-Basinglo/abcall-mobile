package co.uniandes.abcall.ui.screens.issues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.uniandes.abcall.R
import co.uniandes.abcall.ui.navigation.TopBar
import co.uniandes.abcall.ui.navigation.goBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIssueScreen(navController: NavController) {
    val typeState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val typeOptions = listOf("Salud", "Ciencia", "Tecnología")

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.create_issue).uppercase(),
                navigationAction = { navController.goBack() },
                actionContentDescription = stringResource(id = R.string.issue_back_content_description)
            )
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

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded.value,
                        onExpandedChange = { expanded.value = !expanded.value }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = typeState.value,
                            onValueChange = { },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded.value,
                                )
                            },
                            label = { Text(text = stringResource(id = R.string.type_issue)) },
                            placeholder = { Text(text = stringResource(id = R.string.type_issue), color = Color.Gray) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .padding(vertical = 8.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            typeOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(text = option) },
                                    onClick = {
                                        typeState.value = option
                                        expanded.value = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = descriptionState.value,
                        onValueChange = {
                            if (it.length <= 300) {
                                descriptionState.value = it
                            }
                        },
                        maxLines = 5,
                        label = { Text(
                            text = stringResource(id = R.string.description),
                            style = MaterialTheme.typography.bodyMedium
                        ) },
                        placeholder = { Text(
                            text = stringResource(id = R.string.description),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        ) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 8.dp)
                    )

                    Text(
                        text = "${descriptionState.value.length}/300",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Text(
                        modifier = Modifier.padding(top = 43.dp, bottom = 8.dp),
                        text = stringResource(id = R.string.recommendations),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                    )

                    Text(
                        text = stringResource(id = R.string.recommendations_body),
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.grey_medium),
                    )

                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { navController.goBack() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel).uppercase(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Button(
                        onClick = { navController.goBack() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.create).uppercase(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

            }

        }
    }
}
