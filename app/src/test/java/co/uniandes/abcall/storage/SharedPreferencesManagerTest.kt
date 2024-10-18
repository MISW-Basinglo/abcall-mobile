package co.uniandes.abcall.storage

import android.content.Context
import android.content.SharedPreferences
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

class SharedPreferencesManagerTest {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var context: Context

    @Before
    fun setUp() {
        // Mock SharedPreferences and its Editor
        context = mockk()
        sharedPreferences = mockk()
        editor = mockk()

        // Set up SharedPreferences behavior
        every { context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE) } returns sharedPreferences
        every { sharedPreferences.edit() } returns editor
        every { editor.putString(any(), any()) } returns editor
        every { editor.remove(any()) } returns editor
        every { editor.apply() } just Runs

        // Initialize SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(context)
    }

    @Test
    fun `save and retrieve access token`() {
        // Given
        val accessToken = "testAccessToken"

        // When saving the access token
        sharedPreferencesManager.saveAccessToken(accessToken)

        // Then
        verify { editor.putString("jwt_access_token", accessToken) }
        verify { editor.apply() }

        // Mocking getAccessToken response
        every { sharedPreferences.getString("jwt_access_token", null) } returns accessToken

        // When retrieving the access token
        val retrievedToken = sharedPreferencesManager.getAccessToken()

        // Then
        assertEquals(accessToken, retrievedToken)
    }

    @Test
    fun `save and retrieve refresh token`() {
        // Given
        val refreshToken = "testRefreshToken"

        // When saving the refresh token
        sharedPreferencesManager.saveRefreshToken(refreshToken)

        // Then
        verify { editor.putString("jwt_refresh_token", refreshToken) }
        verify { editor.apply() }

        // Mocking getRefreshToken response
        every { sharedPreferences.getString("jwt_refresh_token", null) } returns refreshToken

        // When retrieving the refresh token
        val retrievedToken = sharedPreferencesManager.getRefreshToken()

        // Then
        assertEquals(refreshToken, retrievedToken)
    }

    @Test
    fun `clear tokens`() {
        // When clearing the tokens
        sharedPreferencesManager.clearTokens()

        // Then
        verify { editor.remove("jwt_access_token") }
        verify { editor.remove("jwt_refresh_token") }
        verify { editor.apply() }
    }

    @Test
    fun `get access token returns null when not saved`() {
        // Given no token is saved
        every { sharedPreferences.getString("jwt_access_token", null) } returns null

        // When retrieving the access token
        val token = sharedPreferencesManager.getAccessToken()

        // Then
        assertNull(token)
    }

    @Test
    fun `get refresh token returns null when not saved`() {
        // Given no token is saved
        every { sharedPreferences.getString("jwt_refresh_token", null) } returns null

        // When retrieving the refresh token
        val token = sharedPreferencesManager.getRefreshToken()

        // Then
        assertNull(token)
    }
}
