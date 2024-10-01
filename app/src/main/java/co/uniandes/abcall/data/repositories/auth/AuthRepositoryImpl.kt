package co.uniandes.abcall.data.repositories.auth

import co.uniandes.abcall.networking.AbcallApi
import co.uniandes.abcall.networking.ChannelUpdateRequest
import co.uniandes.abcall.networking.LoginRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AbcallApi
): AuthRepository {

    override suspend fun login(email: String, password: String) {
        //api.login(LoginRequest(email, password))
    }

    override suspend fun updateChannel(channel: String) {
        //api.updateChannel(ChannelUpdateRequest(channel))
    }

}