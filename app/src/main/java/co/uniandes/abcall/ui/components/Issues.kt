package co.uniandes.abcall.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.uniandes.abcall.R
import co.uniandes.abcall.data.models.Issue

@Composable
fun IssueItem(item: Issue) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.type,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = getText(item.status),
                style = MaterialTheme.typography.headlineLarge,
                color = getStatusColor(item.status)
            )
        }
        Text(
            text = item.date,
            style = MaterialTheme.typography.bodySmall,
            color = colorResource(id = R.color.grey_medium)
        )
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun getStatusColor(status: String): Color {
    return when (status) {
        "Cerrado" -> colorResource(id = R.color.green)
        "Escalado" -> colorResource(id = R.color.red)
        "Abierto" -> colorResource(id = R.color.orange)
        else -> Color.Black
    }
}

@Composable
fun getText(status: String): String {
    return when (status) {
        "Cerrado" -> stringResource(id = R.string.issue_closed)
        "Escalado" -> stringResource(id = R.string.issue_scaled)
        "Abierto" -> stringResource(id = R.string.issue_opened)
        else -> ""
    }
}

