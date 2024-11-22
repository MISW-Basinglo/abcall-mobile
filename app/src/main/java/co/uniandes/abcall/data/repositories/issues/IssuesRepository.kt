package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.networking.IssueResponse
import co.uniandes.abcall.networking.Result
import co.uniandes.abcall.networking.SuggestResponse

interface IssuesRepository {
    suspend fun getIssues(userId: Int): Result<List<IssueResponse>>
    suspend fun createIssue(type: String, description: String): Result<IssueResponse>
    suspend fun suggestIssue(description: String): Result<SuggestResponse>
}
