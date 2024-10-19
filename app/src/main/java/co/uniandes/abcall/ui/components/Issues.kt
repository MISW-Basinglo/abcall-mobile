package co.uniandes.abcall.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import co.uniandes.abcall.R
import co.uniandes.abcall.networking.IssueResponse
import co.uniandes.abcall.ui.utils.getIssueStatusColor
import co.uniandes.abcall.ui.utils.getIssueStatusText

@Composable
fun IssueItem(item: IssueResponse, onClick: () -> Unit = {}) {
    Column(modifier = Modifier
        .padding(vertical = 8.dp)
        .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.type.name,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = getIssueStatusText(item.status),
                style = MaterialTheme.typography.headlineLarge,
                color = getIssueStatusColor(item.status)
            )
        }
        Text(
            text = item.createdAt.toLocaleString(),
            style = MaterialTheme.typography.bodySmall,
            color = colorResource(id = R.color.grey_medium)
        )
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
