package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.networking.AbcallApi
import co.uniandes.abcall.networking.ErrorResponse
import co.uniandes.abcall.networking.IssueRequest
import co.uniandes.abcall.networking.IssueResponse
import co.uniandes.abcall.networking.IssueSource
import co.uniandes.abcall.networking.Result
import co.uniandes.abcall.networking.ServerErrorMessage
import co.uniandes.abcall.networking.SuggestRequest
import co.uniandes.abcall.networking.SuggestResponse
import com.google.gson.Gson
import javax.inject.Inject

class IssuesRepositoryImpl @Inject constructor(
    private val api: AbcallApi
): IssuesRepository {

    override suspend fun getIssues(userId: Int): Result<List<IssueResponse>> {
        val response = api.getIssues(userId)
        return if (response.isSuccessful) {
            response.body()?.let { issuesResponse ->
                return Result.Success(issuesResponse.data)
            } ?: Result.Error(ServerErrorMessage.GENERIC_ERROR)
        } else {
            val gson = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.Error(gson.message)
        }
    }

    override suspend fun createIssue(type: String, description: String): Result<IssueResponse> {
        val issue = IssueRequest(type, description, IssueSource.APP_MOBILE.name)
        val response = api.createIssue(issue)
        return if (response.isSuccessful) {
            response.body()?.let { issuesResponse ->
                return Result.Success(issuesResponse.data)
            } ?: Result.Error(ServerErrorMessage.GENERIC_ERROR)
        } else {
            val gson = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.Error(gson.message)
        }
    }

    override suspend fun suggestIssue(description: String): Result<SuggestResponse> {
        val issue = SuggestRequest(description)
        val response = api.suggestIssue(issue)
        return if (response.isSuccessful) {
            response.body()?.let { issuesResponse ->
                return Result.Success(issuesResponse)
            } ?: Result.Error(ServerErrorMessage.GENERIC_ERROR)
        } else {
            val gson = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.Error(gson.message)
        }
    }

}
