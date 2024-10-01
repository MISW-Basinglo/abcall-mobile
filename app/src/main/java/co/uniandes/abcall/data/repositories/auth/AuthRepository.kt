package co.uniandes.abcall.data.repositories.auth

interface AuthRepository {
    fun login(email: String, password: String)
    fun updateChannel(channel: String)
}
