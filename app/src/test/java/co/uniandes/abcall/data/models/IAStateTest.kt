package co.uniandes.abcall.data.models

import org.junit.Assert.*
import org.junit.Test

class IAStateTest {

    @Test
    fun `test IAState_Idle is instance of Idle`() {
        // Given
        val state: IAState = IAState.Idle

        // Then
        assertTrue(state is IAState.Idle)
    }

    @Test
    fun `test IAState_Loading is instance of Loading`() {
        // Given
        val state: IAState = IAState.Loading

        // Then
        assertTrue(state is IAState.Loading)
    }

    @Test
    fun `test IAState_Success is instance of Success`() {
        // Given
        val state: IAState = IAState.Success

        // Then
        assertTrue(state is IAState.Success)
    }

    @Test
    fun `test IAState_Error is instance of Error with correct message`() {
        // Given
        val errorMessage = "An error occurred"
        val state: IAState = IAState.Error(errorMessage)

        // Then
        assertTrue(state is IAState.Error)
        assertEquals(errorMessage, (state as IAState.Error).message)
    }
}
