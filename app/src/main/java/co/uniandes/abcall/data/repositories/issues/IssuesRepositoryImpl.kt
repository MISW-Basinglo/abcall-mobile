package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.networking.AbcallApi
import co.uniandes.abcall.networking.ErrorResponse
import co.uniandes.abcall.networking.IssueRequest
import co.uniandes.abcall.networking.IssueResponse
import co.uniandes.abcall.networking.IssueSource
import co.uniandes.abcall.networking.IssueStatus
import co.uniandes.abcall.networking.IssueType
import co.uniandes.abcall.networking.Result
import co.uniandes.abcall.networking.ServerErrorMessage
import com.google.gson.Gson
import javax.inject.Inject

class IssuesRepositoryImpl @Inject constructor(
    private val api: AbcallApi
): IssuesRepository {

    override suspend fun getIssues(): Result<List<IssueResponse>> {
        val response = api.getIssues()
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

    override suspend fun suggestIssue(description: String): String {
        //api.suggestIssue(SuggestRequest(description))
        return "Al completar el campo de descripción, asegúrate de incluir información clara y detallada sobre el incidente. Describe el problema específico que estás enfrentando, incluyendo pasos que llevaron al inconveniente, cualquier mensaje de error que hayas recibido y el impacto que tiene en tu actividad. Cuanta más información proporciones, más fácil será para nuestro equipo entender y resolver tu solicitud de manera eficiente."
    }

}
