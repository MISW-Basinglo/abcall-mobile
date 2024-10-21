package co.uniandes.abcall.data.repositories.auth

import co.uniandes.abcall.networking.LoginResponse
import co.uniandes.abcall.networking.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse>
    suspend fun logout()
}
