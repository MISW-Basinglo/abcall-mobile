package co.uniandes.abcall.networking

import okhttp3.Interceptor
import okhttp3.Response
import android.util.Log

class LoggerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        Log.d("LoggerInterceptor", "Request URL: ${request.url()}")
        Log.d("LoggerInterceptor", "Response Code: ${response.code()}")
        Log.d("LoggerInterceptor", "Response Body: ${response.body()}")

        return response
    }
}