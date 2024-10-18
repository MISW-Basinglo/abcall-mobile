package co.uniandes.abcall.networking

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.Protocol
import android.util.Log
import io.mockk.*
import org.junit.Before
import org.junit.Test

class LoggerInterceptorTest {

    private lateinit var loggerInterceptor: LoggerInterceptor
    private lateinit var chain: Interceptor.Chain

    @Before
    fun setUp() {
        loggerInterceptor = LoggerInterceptor()
        chain = mockk(relaxed = true)
    }

    @Test
    fun `intercept logs request URL and response code`() {
        // Given
        val request = Request.Builder().url("http://example.com").build()

        val responseBody = ResponseBody.create(null, "Response body content")
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(responseBody)
            .build()

        every { chain.request() } returns request
        every { chain.proceed(any()) } returns response

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        // When
        loggerInterceptor.intercept(chain)

        // Then
        verify { Log.d(any(), any()) }

        // Clean up static mock
        unmockkStatic(Log::class)
    }

    @Test
    fun `intercept logs empty response body when body is null`() {
        // Given
        val request = Request.Builder()
            .url("http://example.com")
            .build()

        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(204)
            .message("No Content")
            .body(null)
            .build()

        every { chain.request() } returns request
        every { chain.proceed(request) } returns response

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        // When
        loggerInterceptor.intercept(chain)

        // Then
        verify { Log.d(any(), any()) }

        // Clean up static mock
        unmockkStatic(Log::class)
    }
}
