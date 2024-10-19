package co.uniandes.abcall.ui.screens.issues

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.uniandes.abcall.R
import co.uniandes.abcall.data.models.IAState
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.networking.IssueType
import co.uniandes.abcall.ui.components.FullLoading
import co.uniandes.abcall.ui.navigation.TopBar
import co.uniandes.abcall.ui.navigation.goBack
import co.uniandes.abcall.ui.utils.getIssueTypeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIssueScreen(navController: NavController, viewModel: CreateIssueViewModel = hiltViewModel()) {
    val typeState = remember { mutableStateOf<IssueType?>(null) }
    val descriptionState = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }

    val updateState by viewModel.updateState.observeAsState(UpdateState.Idle)
    val iaState by viewModel.iaState.observeAsState(IAState.Idle)
    val suggestState by viewModel.suggestState.observeAsState("")

    val keyboardController = LocalSoftwareKeyboardController.current

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
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded.value,
                        onExpandedChange = { expanded.value = !expanded.value }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = getIssueTypeText(typeState.value),
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
                            IssueType.entries.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(text = getIssueTypeText(option)) },
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

                    when (iaState) {
                        is IAState.Loading -> {
                            FullLoading(color = Color.White)
                        }
                        is IAState.Success -> {
                            viewModel.resetState()
                        }
                        is IAState.Error -> {
                            viewModel.resetState()
                            val errorMessage = (iaState as IAState.Error).message
                            Toast.makeText(navController.context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Button(
                                enabled = typeState.value != null && descriptionState.value.isNotEmpty(),
                                onClick = {
                                    keyboardController?.hide()
                                    viewModel.suggestIssue(descriptionState.value)
                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.suggest).uppercase(),
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_gemini),
                                    contentDescription = stringResource(id = R.string.suggest_content_description),
                                    modifier = Modifier.size(30.dp).padding(start = 5.dp)
                                )
                            }
                        }
                    }

                    if(suggestState.isNotEmpty()){
                        Text(
                            modifier = Modifier.padding(top = 43.dp, bottom = 8.dp),
                            text = stringResource(id = R.string.recommendations),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                        )

                        Text(
                            text = suggestState,
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.grey_medium),
                        )
                    }

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
                        enabled = typeState.value != null && descriptionState.value.isNotEmpty(),
                        onClick = {
                            viewModel.createIssue(
                                type = typeState.value?.name.orEmpty(),
                                description = descriptionState.value
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.create).uppercase(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

            }

            when (updateState) {
                is UpdateState.Loading -> {
                    FullLoading()
                }
                is UpdateState.Success -> {
                    viewModel.resetState()
                    Toast.makeText(navController.context, stringResource(id = R.string.successful), Toast.LENGTH_SHORT).show()
                    navController.goBack()
                }
                is UpdateState.Error -> {
                    val errorMessage = (updateState as UpdateState.Error).message
                    Toast.makeText(navController.context, errorMessage, Toast.LENGTH_SHORT).show()
                    viewModel.resetState()
                }
                else -> {}
            }

        }
    }
}
