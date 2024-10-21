package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.networking.IssueResponse
import co.uniandes.abcall.networking.Result

interface IssuesRepository {
    suspend fun getIssues(): Result<List<IssueResponse>>
    suspend fun createIssue(type: String, description: String): Result<IssueResponse>
    suspend fun suggestIssue(description: String): String
}
