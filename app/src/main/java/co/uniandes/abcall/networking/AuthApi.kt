package co.uniandes.abcall.networking

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun auth(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/refresh-token")
    fun refreshAccessToken(@Body refreshToken: String): Response<TokenResponse>

}