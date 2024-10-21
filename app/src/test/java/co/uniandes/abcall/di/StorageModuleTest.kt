package co.uniandes.abcall.di

import android.content.Context
import co.uniandes.abcall.storage.LocalStorage
import co.uniandes.abcall.storage.SharedPreferencesManager
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Test

class StorageModuleTest {

    @Test
    fun `providesSharedPreferencesManager returns an instance of SharedPreferencesManager`() {
        // Arrange
        val mockContext = mockk<Context>(relaxed = true)

        // Act
        val localStorage: LocalStorage = StorageModule.providesSharedPreferencesManager(mockContext)

        // Assert
        assertNotNull(localStorage)
        assert(localStorage is SharedPreferencesManager)
    }

}
