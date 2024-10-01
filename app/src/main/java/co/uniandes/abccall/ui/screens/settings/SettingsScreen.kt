package co.uniandes.abccall.ui.screens.settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.uniandes.abccall.R
import co.uniandes.abccall.ui.navigation.BottomBar
import co.uniandes.abccall.ui.navigation.goAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {

    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val phoneState = remember { mutableStateOf("") }
    val channelState = remember { mutableStateOf("") }

    val expanded = remember { mutableStateOf(false) }
    val typeOptions = listOf("Correo", "Llamada")

    Scaffold(
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

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(top = 55.dp, bottom = 83.dp),
                        text = stringResource(id = R.string.profile).uppercase(),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    OutlinedTextField(
                        value = nameState.value,
                        onValueChange = { nameState.value = it },
                        label = { Text(
                            text = stringResource(id = R.string.name),
                            style = MaterialTheme.typography.bodyMedium
                        ) },
                        placeholder = { Text(
                            text = stringResource(id = R.string.name),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        ) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = emailState.value,
                        onValueChange = { emailState.value = it },
                        label = { Text(
                            text = stringResource(id = R.string.email),
                            style = MaterialTheme.typography.bodyMedium
                        ) },
                        placeholder = { Text(
                            text = stringResource(id = R.string.email),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        ) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = phoneState.value,
                        onValueChange = { phoneState.value = it },
                        label = { Text(
                            text = stringResource(id = R.string.phone),
                            style = MaterialTheme.typography.bodyMedium
                        ) },
                        placeholder = { Text(
                            text = stringResource(id = R.string.phone),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        ) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded.value,
                        onExpandedChange = { expanded.value = !expanded.value }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = channelState.value,
                            onValueChange = { },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded.value
                                )
                            },
                            label = { Text(text = stringResource(id = R.string.channel)) },
                            placeholder = { Text(text = stringResource(id = R.string.channel), color = Color.Gray) },
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
                                        channelState.value = option
                                        expanded.value = false
                                    }
                                )
                            }
                        }
                    }

                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { navController.goAuth() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.sign_out).uppercase(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Button(
                        onClick = {  }
                    ) {
                        Text(
                            text = stringResource(id = R.string.update).uppercase(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

            }

        }
    }
}
