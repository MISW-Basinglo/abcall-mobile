package co.uniandes.abcall.ui.screens.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.data.repositories.auth.AuthRepository
import co.uniandes.abcall.networking.LoginResponse
import co.uniandes.abcall.networking.Result
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: AuthRepository
    private lateinit var observer: Observer<UpdateState>

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = LoginViewModel(repository)
        observer = mockk(relaxed = true)

        Dispatchers.setMain(testDispatcher)
        viewModel.updateState.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.updateState.removeObserver(observer)
    }

    @Test
    fun `login success updates state to Success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        coEvery { repository.login(email, password) } returns Result.Success(LoginResponse("", ""))

        // When
        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle() // Advance coroutines

        // Then
        verify { observer.onChanged(UpdateState.Loading) }
        verify { observer.onChanged(UpdateState.Success) }
    }

    @Test
    fun `login error updates state to Error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val errorMessage = "Login failed"
        coEvery { repository.login(email, password) } returns Result.Error(errorMessage)

        // When
        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { observer.onChanged(UpdateState.Loading) }
        verify { observer.onChanged(UpdateState.Error(errorMessage)) }
    }

    @Test
    fun `login exception updates state to Error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val exceptionMessage = "Unexpected error"
        coEvery { repository.login(email, password) } throws Exception(exceptionMessage)

        // When
        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { observer.onChanged(UpdateState.Loading) }
        verify { observer.onChanged(UpdateState.Error(exceptionMessage)) }
    }

    @Test
    fun `login empty exception updates state to Error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val exception = Exception(null as String?)

        coEvery { repository.login(email, password) } throws exception

        // When
        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { observer.onChanged(UpdateState.Loading) }
        verify { observer.onChanged(UpdateState.Error("")) }
    }

    @Test
    fun `resetState updates state to Idle`() {
        // When
        viewModel.resetState()

        // Then
        verify { observer.onChanged(UpdateState.Idle) }
    }
}
