package co.uniandes.abcall.networking

import co.uniandes.abcall.storage.LocalStorage
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Timeout

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Callback
import java.net.HttpURLConnection

class AuthInterceptorTest {

    private lateinit var localStorage: LocalStorage
    private lateinit var authApi: AuthApi
    private lateinit var authInterceptor: AuthInterceptor
    private lateinit var chain: Interceptor.Chain

    @Before
    fun setUp() {
        localStorage = mockk(relaxed = true)
        authApi = mockk()
        authInterceptor = AuthInterceptor(localStorage, authApi)
        chain = mockk(relaxed = true)
    }

    @Test
    fun `intercept adds authorization header when access token exists`() {
        // Given
        val accessToken = "validAccessToken"
        val originalRequest = Request.Builder()
            .url("http://example.com")
            .build()

        val expectedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        val mockResponse = Response.Builder()
            .request(expectedRequest)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(null, "OK"))
            .build()

        // Mock behavior
        every { localStorage.getAccessToken() } returns accessToken
        every { chain.request() } returns originalRequest
        every { chain.proceed(any()) } returns mockResponse

        // When
        val response = authInterceptor.intercept(chain)

        // Then
        assertEquals(200, response.code())
        verify { chain.proceed(any()) }
    }


    @Test
    fun `intercept does not add authorization header when no access token`() {
        // Given
        val originalRequest = Request.Builder().url("http://example.com").build()

        val expectedRequest = originalRequest.newBuilder().build()

        val mockResponse = Response.Builder()
            .request(expectedRequest)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(null, "OK"))
            .build()

        // Mock behavior
        every { localStorage.getAccessToken() } returns null
        every { chain.request() } returns originalRequest
        every { chain.proceed(any()) } returns mockResponse

        // When
        val response = authInterceptor.intercept(chain)

        // Then
        assertEquals(200, response.code())
        verify { chain.proceed(originalRequest) }
    }

    @Test
    fun `intercept refreshes access token on unauthorized response`() = runBlocking {
        // Given
        val accessToken = "validAccessToken"
        val refreshToken = "validRefreshToken"
        val newAccessToken = "newAccessToken"
        val originalRequest = Request.Builder().url("http://example.com").build()

        val oldRequest = originalRequest.newBuilder()
            .build()
        val unauthorizedResponse = Response.Builder()
            .request(oldRequest)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(HttpURLConnection.HTTP_UNAUTHORIZED)
            .message("BAD")
            .body(ResponseBody.create(null, ""))
            .build()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()

        val mockResponse = Response.Builder()
            .request(newRequest)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(null, "OK"))
            .build()

        // Mock behavior
        every { localStorage.getAccessToken() } returns accessToken
        every { localStorage.getRefreshToken() } returns refreshToken
        every { chain.request() } returns originalRequest
        every { chain.proceed(originalRequest) } returns unauthorizedResponse
        every { chain.proceed(newRequest) } returns mockResponse

        // When
        authInterceptor.intercept(chain)

        // Then
        verify { chain.proceed(any()) }
    }

    @Test
    fun `intercept does not refresh access token if refresh fails`() = runBlocking {
        // Given
        val accessToken = "validAccessToken"
        val refreshToken = "validRefreshToken"
        val originalRequest = Request.Builder().url("http://example.com").build()
        val unauthorizedResponse = Response.Builder()
            .request(originalRequest)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .message("BAD")
            .code(HttpURLConnection.HTTP_UNAUTHORIZED)
            .body(ResponseBody.create(null, ""))
            .build()

        val mockCall = object : retrofit2.Call<TokenResponse> {
            override fun clone() = this
            override fun execute(): retrofit2.Response<TokenResponse> {
                return retrofit2.Response.error(400, ResponseBody.create(null, ""))
            }
            override fun isExecuted() = false
            override fun cancel() { }
            override fun isCanceled() = false
            override fun request() = Request.Builder().build()
            override fun timeout() = Timeout.NONE
            override fun enqueue(callback: Callback<TokenResponse>) { }
        }

        // Mock behavior
        every { localStorage.getAccessToken() } returns accessToken
        every { localStorage.getRefreshToken() } returns refreshToken
        every { chain.request() } returns originalRequest
        every { chain.proceed(any()) } returns unauthorizedResponse
        every { authApi.refreshAccessToken("Bearer $refreshToken") } returns mockCall // Fix here

        // When
        val response = authInterceptor.intercept(chain)

        // Then
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, response.code())
        verify { chain.proceed(any()) }
    }

}
