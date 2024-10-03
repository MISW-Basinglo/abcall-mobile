package co.uniandes.abcall.data.repositories.auth

import co.uniandes.abcall.networking.AbcallApi
import co.uniandes.abcall.networking.LoginRequest
import co.uniandes.abcall.storage.LocalStorage
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AbcallApi,
    private val localStorage: LocalStorage
): AuthRepository {

    override suspend fun login(email: String, password: String) {
        /*
        val response = api.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let { loginResponse ->
                localStorage.saveToken(loginResponse.token)
            }
        }
        */
    }

    override suspend fun logout() {
        localStorage.clearToken()
    }

    override suspend fun updateChannel(channel: String) {
        //api.updateChannel(ChannelUpdateRequest(channel))
    }

}