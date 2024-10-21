package co.uniandes.abcall.data.repositories.auth

import co.uniandes.abcall.networking.AuthApi
import co.uniandes.abcall.networking.ErrorResponse
import co.uniandes.abcall.networking.LoginRequest
import co.uniandes.abcall.networking.LoginResponse
import co.uniandes.abcall.networking.Result
import co.uniandes.abcall.networking.Roles
import co.uniandes.abcall.networking.ServerErrorMessage
import co.uniandes.abcall.networking.Utils
import co.uniandes.abcall.storage.LocalStorage
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor(
    @Named("AuthApi") private val api: AuthApi,
    private val localStorage: LocalStorage
): AuthRepository {

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        val response = api.auth(LoginRequest(email, password))
        return if (response.isSuccessful) {
            response.body()?.let { loginResponse ->
                val role = Utils.getJwtClaim(loginResponse.accessToken, Utils.ROLE_CLAIM)
                if (role == Roles.USER.value) {
                    localStorage.saveAccessToken(loginResponse.accessToken)
                    localStorage.saveRefreshToken(loginResponse.refreshToken)
                    return Result.Success(loginResponse)
                } else {
                    return Result.Error(ServerErrorMessage.USER_NOT_AUTHORIZED)
                }
            } ?: Result.Error(ServerErrorMessage.GENERIC_ERROR)
        } else {
            val gson = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.Error(gson.message)
        }
    }

    override suspend fun logout() {
        localStorage.clearTokens()
    }

}