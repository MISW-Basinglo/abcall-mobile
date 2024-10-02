package co.uniandes.abcall.storage

interface LocalStorage {
    fun getToken(): String?
    fun saveToken(newToken: String)
    fun clearToken()
}