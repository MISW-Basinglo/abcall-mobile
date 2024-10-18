package co.uniandes.abcall.data.repositories.auth

import co.uniandes.abcall.networking.*
import co.uniandes.abcall.storage.LocalStorage
import com.google.gson.Gson
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthRepositoryImplTest {

    private lateinit var authApi: AuthApi
    private lateinit var localStorage: LocalStorage
    private lateinit var authRepository: AuthRepositoryImpl

    @Before
    fun setUp() {
        authApi = mockk()
        localStorage = mockk(relaxed = true)  // Relaxed para evitar tener que definir cada método que no usamos
        authRepository = AuthRepositoryImpl(api = authApi, localStorage = localStorage)
    }

    @Test
    fun `login successful for valid user`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val accessToken = "fakeAccessToken"
        val refreshToken = "fakeRefreshToken"
        val loginResponse = LoginResponse(accessToken, refreshToken)
        val response = Response.success(loginResponse)

        // Mock behavior
        coEvery { authApi.auth(LoginRequest(email, password)) } returns response
        mockkObject(Utils) // Simular métodos estáticos
        every { Utils.getJwtClaim(accessToken, Utils.ROLE_CLAIM) } returns Roles.USER.value

        // When
        val result = authRepository.login(email, password)

        // Then
        assert(result is Result.Success)
        assertEquals(loginResponse, (result as Result.Success).data)

        // Verify tokens are saved
        coVerify { localStorage.saveAccessToken(accessToken) }
        coVerify { localStorage.saveRefreshToken(refreshToken) }
    }

    @Test
    fun `login fails for unauthorized role`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val accessToken = "fakeAccessToken"
        val refreshToken = "fakeRefreshToken"
        val loginResponse = LoginResponse(accessToken, refreshToken)
        val response = Response.success(loginResponse)

        // Mock behavior
        coEvery { authApi.auth(LoginRequest(email, password)) } returns response
        mockkObject(Utils)
        every { Utils.getJwtClaim(accessToken, Utils.ROLE_CLAIM) } returns "unauthorized_role"

        // When
        val result = authRepository.login(email, password)

        // Then
        assert(result is Result.Error)
        assertEquals(ServerErrorMessage.USER_NOT_AUTHORIZED, (result as Result.Error).message)

        // Verify tokens are NOT saved
        coVerify(exactly = 0) { localStorage.saveAccessToken(any()) }
        coVerify(exactly = 0) { localStorage.saveRefreshToken(any()) }
    }

    @Test
    fun `login fails with generic error when response body is null`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val response = Response.success<LoginResponse>(null)

        // Mock behavior
        coEvery { authApi.auth(LoginRequest(email, password)) } returns response

        // When
        val result = authRepository.login(email, password)

        // Then
        assert(result is Result.Error)
        assertEquals(ServerErrorMessage.GENERIC_ERROR, (result as Result.Error).message)
    }

    @Test
    fun `login fails with generic error when error body is not null`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val errorMessage = "Invalid credentials"
        val status = "Error"
        val errorResponse = ErrorResponse(errorMessage, status)
        val errorBodyJson = Gson().toJson(errorResponse)
        val responseBody = ResponseBody.create(MediaType.get("application/json"), errorBodyJson)

        val response = Response.error<LoginResponse>(400, responseBody)

        // Mock behavior
        coEvery { authApi.auth(LoginRequest(email, password)) } returns response

        // When
        val result = authRepository.login(email, password)

        // Then
        assert(result is Result.Error)
    }

    @Test
    fun `logout clears tokens`() = runBlocking {
        // When
        authRepository.logout()

        // Then
        coVerify { localStorage.clearTokens() }
    }

}
