package co.uniandes.abcall.data.repositories.auth

interface AuthRepository {
    suspend fun login(email: String, password: String)
    suspend fun updateChannel(channel: String)
}
