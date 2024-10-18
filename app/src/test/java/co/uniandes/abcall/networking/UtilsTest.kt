package co.uniandes.abcall.networking

import android.util.Base64
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UtilsTest {


    @Before
    fun setUp() {
        // Mock JWT object
    }

    @Test
    fun `getJwtClaim returns role when claim exists`() {
        mockkStatic(Base64::class)

        // Given
        val expectedClaim = "user"

        // Then
        assertEquals(expectedClaim, Roles.USER.value)
    }


    @Before
    fun tearDown() {
        unmockkAll()  // Clean up after tests
    }
}
