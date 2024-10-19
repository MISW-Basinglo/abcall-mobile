package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.networking.*
import com.google.gson.Gson
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.Date

class IssuesRepositoryImplTest {

    private lateinit var api: AbcallApi
    private lateinit var issuesRepository: IssuesRepositoryImpl

    @Before
    fun setUp() {
        api = mockk()
        issuesRepository = IssuesRepositoryImpl(api = api)
    }

    @Test
    fun `getIssues returns successful result`() = runBlocking {
        // Given
        val issueResponseList = listOf(
            IssueResponse(
                id = 1,
                type = IssueType.REQUEST,
                description = "Bug description",
                solution = null,
                status = IssueStatus.OPEN,
                source = IssueSource.APP_MOBILE,
                userId = 1,
                companyId = 1,
                createdAt = Date(),
                updatedAt = Date()
            )
        )
        val response = Response.success(IssuesListResponse(1, issueResponseList))

        // Mock behavior
        coEvery { api.getIssues() } returns response

        // When
        val result = issuesRepository.getIssues()

        // Then
        assert(result is Result.Success)
        assertEquals(issueResponseList, (result as Result.Success).data)
    }

    @Test
    fun `getIssues returns error result when response body is null`() = runBlocking {
        // Given
        val response = Response.success<IssuesListResponse>(null)

        // Mock behavior
        coEvery { api.getIssues() } returns response

        // When
        val result = issuesRepository.getIssues()

        // Then
        assert(result is Result.Error)
        assertEquals(ServerErrorMessage.GENERIC_ERROR, (result as Result.Error).message)
    }

    @Test
    fun `getIssues returns error result when response is unsuccessful`() = runBlocking {
        // Given
        val errorMessage = "Error fetching issues"
        val errorResponse = ErrorResponse(errorMessage, "Error")
        val errorBodyJson = Gson().toJson(errorResponse)
        val responseBody = ResponseBody.create(MediaType.get("application/json"), errorBodyJson)

        val response = Response.error<IssuesListResponse>(400, responseBody)

        // Mock behavior
        coEvery { api.getIssues() } returns response

        // When
        val result = issuesRepository.getIssues()

        // Then
        assert(result is Result.Error)
        assertEquals(errorMessage, (result as Result.Error).message)
    }

    @Test
    fun `createIssue returns successful result`() = runBlocking {
        // Given
        val issueRequest = IssueRequest("Bug", "Issue description", IssueSource.APP_MOBILE.name)
        val issueResponse = IssueResponse(1, IssueType.REQUEST, "Bug description", null, IssueStatus.OPEN, IssueSource.APP_MOBILE, 1, 1, Date(), Date())
        val response = Response.success(IssueDataResponse(issueResponse))

        // Mock behavior
        coEvery { api.createIssue(issueRequest) } returns response

        // When
        val result = issuesRepository.createIssue("Bug", "Issue description")

        // Then
        assert(result is Result.Success)
        assertEquals(issueResponse, (result as Result.Success).data)
    }

    @Test
    fun `createIssue returns error result when response body is null`() = runBlocking {
        // Given
        val issueRequest = IssueRequest("Bug", "Issue description", IssueSource.APP_MOBILE.name)
        val response = Response.success<IssueDataResponse>(null)

        // Mock behavior
        coEvery { api.createIssue(issueRequest) } returns response

        // When
        val result = issuesRepository.createIssue("Bug", "Issue description")

        // Then
        assert(result is Result.Error)
        assertEquals(ServerErrorMessage.GENERIC_ERROR, (result as Result.Error).message)
    }

    @Test
    fun `createIssue returns error result when response is unsuccessful`() = runBlocking {
        // Given
        val issueRequest = IssueRequest("Bug", "Issue description", IssueSource.APP_MOBILE.name)
        val errorMessage = "Error creating issue"
        val errorResponse = ErrorResponse(errorMessage, "Error")
        val errorBodyJson = Gson().toJson(errorResponse)
        val responseBody = ResponseBody.create(MediaType.get("application/json"), errorBodyJson)

        val response = Response.error<IssueDataResponse>(400, responseBody)

        // Mock behavior
        coEvery { api.createIssue(issueRequest) } returns response

        // When
        val result = issuesRepository.createIssue("Bug", "Issue description")

        // Then
        assert(result is Result.Error)
        assertEquals(errorMessage, (result as Result.Error).message)
    }

    @Test
    fun `suggestIssue returns correct suggestion`() = runBlocking {
        // Given
        val description = "Issue description"

        // When
        val suggestion = issuesRepository.suggestIssue(description)

        // Then
        assertEquals("Al completar el campo de descripción, asegúrate de incluir información clara y detallada sobre el incidente. Describe el problema específico que estás enfrentando, incluyendo pasos que llevaron al inconveniente, cualquier mensaje de error que hayas recibido y el impacto que tiene en tu actividad. Cuanta más información proporciones, más fácil será para nuestro equipo entender y resolver tu solicitud de manera eficiente.", suggestion)
    }
}