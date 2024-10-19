package co.uniandes.abcall.storage

interface LocalStorage {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveAccessToken(accessToken: String)
    fun saveRefreshToken(refreshToken: String)
    fun clearTokens()
}