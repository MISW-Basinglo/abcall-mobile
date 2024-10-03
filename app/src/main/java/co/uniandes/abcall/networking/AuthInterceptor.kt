package co.uniandes.abcall.networking

import co.uniandes.abcall.storage.LocalStorage
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val localStorage: LocalStorage) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()

        localStorage.getToken()?.let { token ->
            builder.header("Authorization", "Bearer $token")
        }

        val request = builder.build()
        return chain.proceed(request)
    }
}
