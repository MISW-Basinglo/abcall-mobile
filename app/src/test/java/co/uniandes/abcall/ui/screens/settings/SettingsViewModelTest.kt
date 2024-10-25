package co.uniandes.abcall.ui.screens.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.data.repositories.auth.AuthRepository
import co.uniandes.abcall.data.repositories.user.UserRepository
import co.uniandes.abcall.networking.Result
import co.uniandes.abcall.networking.UserChannel
import co.uniandes.abcall.networking.UserResponse
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SettingsViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var updateStateObserver: Observer<UpdateState>
    private lateinit var userObserver: Observer<UserResponse>

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        authRepository = mockk()
        userRepository = mockk()
        viewModel = SettingsViewModel(authRepository, userRepository)
        updateStateObserver = mockk(relaxed = true)
        userObserver = mockk(relaxed = true)

        Dispatchers.setMain(testDispatcher)
        viewModel.updateState.observeForever(updateStateObserver)
        viewModel.user.observeForever(userObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.updateState.removeObserver(updateStateObserver)
        viewModel.user.removeObserver(userObserver)
    }

    @Test
    fun `getUser success updates user LiveData`() = runTest {
        // Given
        val userResponse = UserResponse(
            id = 1,
            authId = 123,
            name = "John Doe",
            phone = "1234567890",
            channel = UserChannel.EMAIL,
            companyId = 1001,
            dni = "ABC123",
            email = "john.doe@example.com",
            importance = 5,
            createdAt = Date(),
            updatedAt = null
        )
        coEvery { userRepository.getUser() } returns Result.Success(userResponse)

        // When
        viewModel.getUser()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { userObserver.onChanged(userResponse) }
    }

    @Test
    fun `getUser error updates state to Error`() = runTest {
        // Given
        val errorMessage = "Error fetching user"
        coEvery { userRepository.getUser() } returns Result.Error(errorMessage)

        // When
        viewModel.getUser()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Error(errorMessage)) }
    }

    @Test
    fun `getUser exception updates state to Error`() = runTest {
        // Given
        val exceptionMessage = "Unexpected error"
        coEvery { userRepository.getUser() } throws Exception(exceptionMessage)

        // When
        viewModel.getUser()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Error(exceptionMessage)) }
    }

    @Test
    fun `setUser success updates user LiveData and sets UpdateState to Success`() = runTest {
        // Given
        val userResponse = UserResponse(
            id = 1,
            authId = 123,
            name = "John Doe",
            phone = "1234567890",
            channel = UserChannel.EMAIL,
            companyId = 1001,
            dni = "ABC123",
            email = "john.doe@example.com",
            importance = 5,
            createdAt = Date(),
            updatedAt = null
        )
        coEvery { userRepository.setUser(any()) } returns Result.Success(userResponse)

        // When
        viewModel.setUser("EMAIL")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { userObserver.onChanged(userResponse) }
        verify { updateStateObserver.onChanged(UpdateState.Success) }
    }

    @Test
    fun `setUser error updates state to Error`() = runTest {
        // Given
        val errorMessage = "Error setting user"
        coEvery { userRepository.setUser(any()) } returns Result.Error(errorMessage)

        // When
        viewModel.setUser("EMAIL")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Error(errorMessage)) }
    }

    @Test
    fun `setUser exception updates state to Error`() = runTest {
        // Given
        val exceptionMessage = "Unexpected error"
        coEvery { userRepository.setUser(any()) } throws Exception(exceptionMessage)

        // When
        viewModel.setUser("EMAIL")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Error(exceptionMessage)) }
    }

    @Test
    fun `logout calls authRepository logout`() = runTest {
        // Mock behavior
        coEvery { authRepository.logout() } just Runs

        // When
        viewModel.logout()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { authRepository.logout() }
    }

    @Test
    fun `resetState updates state to Idle`() {
        // When
        viewModel.resetState()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Idle) }
    }
}
