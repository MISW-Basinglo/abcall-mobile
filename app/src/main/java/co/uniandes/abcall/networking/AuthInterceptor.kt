package co.uniandes.abcall.networking

import co.uniandes.abcall.storage.LocalStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val localStorage: LocalStorage,
    private val api: AuthApi
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val accessToken = localStorage.getAccessToken()
        request = accessToken?.let {
            addAuthorizationHeader(request, it)
        } ?: request

        val response = chain.proceed(request)

        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            response.close()

            val newAccessToken = refreshAccessToken()
            if (newAccessToken != null) {
                localStorage.saveAccessToken(newAccessToken)
                val newRequest = addAuthorizationHeader(request, newAccessToken)
                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private fun addAuthorizationHeader(request: Request, token: String): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
    }

    private fun refreshAccessToken(): String? {
        val refreshToken = localStorage.getRefreshToken() ?: return null

        val refreshResponse = api.refreshAccessToken(refreshToken)

        return if (refreshResponse.isSuccessful) {
            refreshResponse.body()?.accessToken
        } else {
            null
        }
    }
}
