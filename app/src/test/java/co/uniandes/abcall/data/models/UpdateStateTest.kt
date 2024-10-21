import co.uniandes.abcall.data.models.UpdateState
import org.junit.Assert.*
import org.junit.Test

class UpdateStateTest {

    @Test
    fun `test UpdateState_Idle is instance of Idle`() {
        // Given
        val state: UpdateState = UpdateState.Idle

        // Then
        assertTrue(state is UpdateState.Idle)
    }

    @Test
    fun `test UpdateState_Loading is instance of Loading`() {
        // Given
        val state: UpdateState = UpdateState.Loading

        // Then
        assertTrue(state is UpdateState.Loading)
    }

    @Test
    fun `test UpdateState_Success is instance of Success`() {
        // Given
        val state: UpdateState = UpdateState.Success

        // Then
        assertTrue(state is UpdateState.Success)
    }

    @Test
    fun `test UpdateState_Error is instance of Error with correct message`() {
        // Given
        val errorMessage = "An error occurred"
        val state: UpdateState = UpdateState.Error(errorMessage)

        // Then
        assertTrue(state is UpdateState.Error)
        assertEquals(errorMessage, (state as UpdateState.Error).message)
    }

}
