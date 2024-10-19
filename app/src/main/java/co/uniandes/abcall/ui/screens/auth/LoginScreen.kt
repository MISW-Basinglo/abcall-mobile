package co.uniandes.abcall.ui.screens.auth

import android.annotation.SuppressLint
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.uniandes.abcall.R
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.networking.ServerErrorMessage.GENERIC_ERROR
import co.uniandes.abcall.networking.ServerErrorMessage.USER_INVALID_PASSWORD
import co.uniandes.abcall.networking.ServerErrorMessage.USER_NOT_AUTHORIZED
import co.uniandes.abcall.networking.ServerErrorMessage.USER_NOT_FOUND
import co.uniandes.abcall.networking.ServerErrorMessage.USER_NOT_REGISTERED
import co.uniandes.abcall.ui.components.FullLoading
import co.uniandes.abcall.ui.navigation.goMain

@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val emailState = remember { mutableStateOf("user@example.com") }
    val passwordState = remember { mutableStateOf("userpass") }
    val isButtonEnabled by derivedStateOf {
        Patterns.EMAIL_ADDRESS.matcher(emailState.value).matches() && passwordState.value.isNotBlank()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val updateState by viewModel.updateState.observeAsState(UpdateState.Idle)

    Box {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 55.dp, bottom = 83.dp ),
                text = stringResource(id = R.string.app_name).uppercase(),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge
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
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text(
                    text = stringResource(id = R.string.password),
                    style = MaterialTheme.typography.bodyMedium
                ) },
                placeholder = { Text(
                    text = stringResource(id = R.string.password),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                ) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    viewModel.login(emailState.value, passwordState.value)
                },
                enabled = isButtonEnabled
            ) {
                Text(
                    text = stringResource(id = R.string.access).uppercase(),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Row(
                modifier = Modifier.padding(vertical = 42.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.no_account),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.sign_up),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        Toast.makeText(
                            navController.context,
                            navController.context.getString(R.string.not_implemented),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

            Text(
                text = stringResource(id = R.string.forgot_password),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    Toast.makeText(
                        navController.context,
                        navController.context.getString(R.string.not_implemented),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

        }

        when (updateState) {
            is UpdateState.Loading -> {
                FullLoading()
            }
            is UpdateState.Success -> {
                viewModel.resetState()
                navController.goMain()
            }
            is UpdateState.Error -> {
                val exception = (updateState as UpdateState.Error).message
                val errorMessage = when (exception) {
                    USER_NOT_REGISTERED -> stringResource(id = R.string.user_not_registered)
                    USER_INVALID_PASSWORD -> stringResource(id = R.string.invalid_password)
                    USER_NOT_FOUND -> stringResource(id = R.string.user_not_found)
                    USER_NOT_AUTHORIZED -> stringResource(id = R.string.user_not_authorized)
                    GENERIC_ERROR -> stringResource(id = R.string.generic_error)
                    else -> exception
                }
                Toast.makeText(navController.context, errorMessage, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            is UpdateState.Idle -> { }
        }
    }

}
