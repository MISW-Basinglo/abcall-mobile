package co.uniandes.abcall.di

import co.uniandes.abcall.networking.AuthApi
import co.uniandes.abcall.storage.LocalStorage
import com.google.gson.Gson
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Test

class NetworkModuleTest {

     class SharedStorage : LocalStorage {
        override fun getAccessToken(): String? = null
        override fun getRefreshToken(): String? = null
        override fun saveAccessToken(accessToken: String) { }
        override fun saveRefreshToken(refreshToken: String) { }
        override fun clearTokens() { }
    }

    private val authApi: AuthApi = mockk()


    @Test
    fun `test Gson instance`() {
        val gson = NetworkModule.provideGson()
        assertNotNull(gson)
    }

    @Test
    fun `test AbcallApi instance`() {
        val okHttpClient = NetworkModule.provideAbcallOkHttpClient(SharedStorage(), authApi)
        val abcallApi = NetworkModule.provideAbcallApi(Gson(), okHttpClient)
        assertNotNull(abcallApi)
    }

    @Test
    fun `test AuthApi instance`() {
        val okHttpClient = NetworkModule.provideAuthOkHttpClient()
        val authApi = NetworkModule.provideAuthApi(Gson(), okHttpClient)
        assertNotNull(authApi)
    }

    @Test
    fun `test LocalStorage instance`() {
        val localStorage = SharedStorage()
        assertNotNull(localStorage)
    }
}
