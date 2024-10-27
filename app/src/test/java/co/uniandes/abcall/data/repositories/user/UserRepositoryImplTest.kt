package co.uniandes.abcall.data.repositories.user

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
import java.util.*

class UserRepositoryImplTest {

    private lateinit var api: AbcallApi
    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setUp() {
        api = mockk()
        userRepository = UserRepositoryImpl(api = api)
    }

    @Test
    fun `getUser returns successful result`() = runBlocking {
        // Given
        val userResponse = UserResponse(
            id = 1,
            authId = 123,
            name = "John Doe",
            phone = "1234567890",
            channel = UserChannel.EMAIL,
            companyId = 1001,
            dni = "ABC123",
            email = "john.doe@example.com",
            importance = 5,
            createdAt = Date(),
            updatedAt = null
        )
        val response = Response.success(UserDataResponse(userResponse))

        // Mock behavior
        coEvery { api.getUser() } returns response

        // When
        val result = userRepository.getUser()

        // Then
        assert(result is Result.Success)
        assertEquals(userResponse, (result as Result.Success).data)
    }

    @Test
    fun `getUser returns error result when response body is null`() = runBlocking {
        // Given
        val response = Response.success<UserDataResponse>(null)

        // Mock behavior
        coEvery { api.getUser() } returns response

        // When
        val result = userRepository.getUser()

        // Then
        assert(result is Result.Error)
        assertEquals(ServerErrorMessage.GENERIC_ERROR, (result as Result.Error).message)
    }

    @Test
    fun `getUser returns error result when response is unsuccessful`() = runBlocking {
        // Given
        val errorMessage = "Error fetching user"
        val errorResponse = ErrorResponse(errorMessage, "Error")
        val errorBodyJson = Gson().toJson(errorResponse)
        val responseBody = ResponseBody.create(MediaType.get("application/json"), errorBodyJson)

        val response = Response.error<UserDataResponse>(400, responseBody)

        // Mock behavior
        coEvery { api.getUser() } returns response

        // When
        val result = userRepository.getUser()

        // Then
        assert(result is Result.Error)
        assertEquals(errorMessage, (result as Result.Error).message)
    }

    @Test
    fun `setUser returns successful result`() = runBlocking {
        // Given
        val userRequest = UserRequest(
            name = "John Doe",
            phone = "1234567890",
            channel = "EMAIL",
            email = "john.doe@example.com"
        )
        val userResponse = UserResponse(
            id = 1,
            authId = 123,
            name = "John Doe",
            phone = "1234567890",
            channel = UserChannel.EMAIL,
            companyId = 1001,
            dni = "ABC123",
            email = "john.doe@example.com",
            importance = 5,
            createdAt = Date(),
            updatedAt = null
        )
        val response = Response.success(UserDataResponse(userResponse))

        // Mock behavior
        coEvery { api.setUser(1, userRequest) } returns response

        // When
        val result = userRepository.setUser(1, userRequest)

        // Then
        assert(result is Result.Success)
        assertEquals(userResponse, (result as Result.Success).data)
    }

    @Test
    fun `setUser returns error result when response body is null`() = runBlocking {
        // Given
        val userRequest = UserRequest(
            name = "John Doe",
            phone = "1234567890",
            channel = "EMAIL",
            email = "john.doe@example.com"
        )
        val response = Response.success<UserDataResponse>(null)

        // Mock behavior
        coEvery { api.setUser(1, userRequest) } returns response

        // When
        val result = userRepository.setUser(1, userRequest)

        // Then
        assert(result is Result.Error)
        assertEquals(ServerErrorMessage.GENERIC_ERROR, (result as Result.Error).message)
    }

    @Test
    fun `setUser returns error result when response is unsuccessful`() = runBlocking {
        // Given
        val userRequest = UserRequest(
            name = "John Doe",
            phone = "1234567890",
            channel = "EMAIL",
            email = "john.doe@example.com"
        )
        val errorMessage = "Error setting user"
        val errorResponse = ErrorResponse(errorMessage, "Error")
        val errorBodyJson = Gson().toJson(errorResponse)
        val responseBody = ResponseBody.create(MediaType.get("application/json"), errorBodyJson)

        val response = Response.error<UserDataResponse>(400, responseBody)

        // Mock behavior
        coEvery { api.setUser(1, userRequest) } returns response

        // When
        val result = userRepository.setUser(1, userRequest)

        // Then
        assert(result is Result.Error)
        assertEquals(errorMessage, (result as Result.Error).message)
    }

}
