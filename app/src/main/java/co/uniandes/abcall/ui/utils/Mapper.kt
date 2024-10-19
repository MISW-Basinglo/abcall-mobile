package co.uniandes.abcall.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import co.uniandes.abcall.R
import co.uniandes.abcall.networking.IssueStatus
import co.uniandes.abcall.networking.IssueType

@Composable
fun getIssueStatusColor(status: IssueStatus): Color {
    return when (status) {
        IssueStatus.CLOSED -> colorResource(id = R.color.green)
        IssueStatus.SCALED -> colorResource(id = R.color.orange)
        IssueStatus.OPEN -> colorResource(id = R.color.red)
    }
}

@Composable
fun getIssueStatusText(status: IssueStatus): String {
    return when (status) {
        IssueStatus.CLOSED -> stringResource(id = R.string.issue_closed)
        IssueStatus.SCALED -> stringResource(id = R.string.issue_scaled)
        IssueStatus.OPEN -> stringResource(id = R.string.issue_opened)
    }
}

@Composable
fun getIssueTypeText(status: IssueType?): String {
    return when (status) {
        IssueType.REQUEST -> stringResource(id = R.string.issue_request)
        IssueType.COMPLAINT -> stringResource(id = R.string.issue_complaint)
        IssueType.CLAIM -> stringResource(id = R.string.issue_claim)
        IssueType.SUGGESTION -> stringResource(id = R.string.issue_suggestion)
        IssueType.PRAISE -> stringResource(id = R.string.issue_praise)
        else -> ""
    }
}


