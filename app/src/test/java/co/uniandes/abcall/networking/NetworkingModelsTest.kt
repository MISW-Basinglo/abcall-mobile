package co.uniandes.abcall.networking

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.Date

class NetworkingModelsTest {

    @Test
    fun `LoginRequest should be created correctly`() {
        val email = "test@example.com"
        val password = "password123"
        val request = LoginRequest(email, password)

        assertEquals(email, request.email)
        assertEquals(password, request.password)
    }

    @Test
    fun `ChannelUpdateRequest should be created correctly`() {
        val channel = "general"
        val request = ChannelUpdateRequest(channel)

        assertEquals(channel, request.channel)
    }

    @Test
    fun `MessageRequest should be created correctly`() {
        val message = "Hello, World!"
        val request = MessageRequest(message)

        assertEquals(message, request.message)
    }

    @Test
    fun `SuggestRequest should be created correctly`() {
        val description = "This is a suggestion."
        val request = SuggestRequest(description)

        assertEquals(description, request.description)
    }

    @Test
    fun `IssueRequest should be created correctly`() {
        val type = "COMPLAINT"
        val description = "This is an issue."
        val source = "EMAIL"
        val request = IssueRequest(type, description, source)

        assertEquals(type, request.type)
        assertEquals(description, request.description)
        assertEquals(source, request.source)
    }

    @Test
    fun `IssueResponse should be created correctly`() {
        val id = 1
        val type = IssueType.COMPLAINT
        val description = "This is an issue."
        val solution = "This is a solution."
        val status = IssueStatus.OPEN
        val source = IssueSource.EMAIL
        val userId = 1
        val companyId = 1
        val createdAt = Date()
        val updatedAt = null

        val response = IssueResponse(
            id, type, description, solution, status, source, userId, companyId, createdAt, updatedAt
        )

        assertEquals(id, response.id)
        assertEquals(type, response.type)
        assertEquals(description, response.description)
        assertEquals(solution, response.solution)
        assertEquals(status, response.status)
        assertEquals(source, response.source)
        assertEquals(userId, response.userId)
        assertEquals(companyId, response.companyId)
        assertEquals(createdAt, response.createdAt)
        assertEquals(updatedAt, response.updatedAt)
    }

    @Test
    fun `IssueSource fromValue should return correct enum value`() {
        assertEquals(IssueSource.CALL, IssueSource.fromValue("CALL"))
        assertEquals(IssueSource.APP_MOBILE, IssueSource.fromValue("NON_EXISTENT"))
    }

    @Test
    fun `IssueType fromValue should return correct enum value`() {
        assertEquals(IssueType.REQUEST, IssueType.fromValue("REQUEST"))
        assertEquals(IssueType.REQUEST, IssueType.fromValue("NON_EXISTENT"))
    }

    @Test
    fun `IssueStatus fromValue should return correct enum value`() {
        assertEquals(IssueStatus.OPEN, IssueStatus.fromValue("OPEN"))
        assertEquals(IssueStatus.OPEN, IssueStatus.fromValue("NON_EXISTENT"))
    }

    @Test
    fun `LoginResponse should be created correctly`() {
        val accessToken = "abc123"
        val refreshToken = "def456"
        val response = LoginResponse(accessToken, refreshToken)

        assertEquals(accessToken, response.accessToken)
        assertEquals(refreshToken, response.refreshToken)
    }

    @Test
    fun `TokenResponse should be created correctly`() {
        val accessToken = "abc123"
        val response = TokenResponse(accessToken)

        assertEquals(accessToken, response.accessToken)
    }

    @Test
    fun `SuggestResponse should be created correctly`() {
        val description = "This is a suggestion."
        val response = SuggestResponse(description)

        assertEquals(description, response.solution)
    }

    @Test
    fun `IssuesListResponse should be created correctly`() {
        val count = 2
        val data = listOf(
            IssueResponse(1, IssueType.COMPLAINT, "Issue 1", null, IssueStatus.OPEN, IssueSource.EMAIL, 1, 1, Date(), null),
            IssueResponse(2, IssueType.REQUEST, "Issue 2", null, IssueStatus.SCALED, IssueSource.APP_MOBILE, 1, 1, Date(), null)
        )
        val response = IssuesListResponse(count, data)

        assertEquals(count, response.count)
        assertNotNull(response.data)
        assertEquals(data.size, response.data.size)
    }

    @Test
    fun `IssueDataResponse should be created correctly`() {
        val issueResponse = IssueResponse(1, IssueType.COMPLAINT, "Issue description", null, IssueStatus.OPEN, IssueSource.EMAIL, 1, 1, Date(), null)
        val response = IssueDataResponse(issueResponse)

        assertEquals(issueResponse, response.data)
    }
}
