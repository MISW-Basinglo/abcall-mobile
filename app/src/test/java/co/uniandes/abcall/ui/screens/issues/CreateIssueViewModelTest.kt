package co.uniandes.abcall.ui.screens.issues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.uniandes.abcall.data.models.IAState
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.data.repositories.issues.IssuesRepository
import co.uniandes.abcall.networking.IssueResponse
import co.uniandes.abcall.networking.IssueSource
import co.uniandes.abcall.networking.IssueStatus
import co.uniandes.abcall.networking.IssueType
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
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class CreateIssueViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CreateIssueViewModel
    private lateinit var repository: IssuesRepository
    private lateinit var updateStateObserver: Observer<UpdateState>
    private lateinit var iaStateObserver: Observer<IAState>
    private lateinit var suggestStateObserver: Observer<String>

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = CreateIssueViewModel(repository)
        updateStateObserver = mockk(relaxed = true)
        iaStateObserver = mockk(relaxed = true)
        suggestStateObserver = mockk(relaxed = true)

        Dispatchers.setMain(testDispatcher)
        viewModel.updateState.observeForever(updateStateObserver)
        viewModel.iaState.observeForever(iaStateObserver)
        viewModel.suggestState.observeForever(suggestStateObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.updateState.removeObserver(updateStateObserver)
        viewModel.iaState.removeObserver(iaStateObserver)
        viewModel.suggestState.removeObserver(suggestStateObserver)
    }

    @Test
    fun `createIssue success updates state to Success`() = runTest {
        // Given
        val type = "Bug"
        val description = "Issue description"
        val issue = IssueResponse(1, IssueType.REQUEST,
            "Description 1",
            "Solution 1",
            IssueStatus.OPEN,
            IssueSource.APP_MOBILE,
            1,
            1,
            Date(1633046400000),
            null
        )
        coEvery { repository.createIssue(type, description) } returns Result.Success(issue)

        // When
        viewModel.createIssue(type, description)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Loading) }
        verify { updateStateObserver.onChanged(UpdateState.Success) }
    }

    @Test
    fun `createIssue error updates state to Error`() = runTest {
        // Given
        val type = "Bug"
        val description = "Issue description"
        val errorMessage = "Failed to create issue"
        coEvery { repository.createIssue(type, description) } returns Result.Error(errorMessage)

        // When
        viewModel.createIssue(type, description)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Loading) }
        verify { updateStateObserver.onChanged(UpdateState.Error(errorMessage)) }
    }

    @Test
    fun `suggestIssue success updates suggestState and iaState to Success`() = runTest {
        // Given
        val description = "Suggested issue description"
        val suggestion = "Suggested solution"
        coEvery { repository.suggestIssue(description) } returns suggestion

        // When
        viewModel.suggestIssue(description)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { iaStateObserver.onChanged(IAState.Loading) }
        verify { suggestStateObserver.onChanged(suggestion) }
        verify { iaStateObserver.onChanged(IAState.Success) }
    }

    @Test
    fun `suggestIssue error updates updateState to Error`() = runTest {
        // Given
        val description = "Suggested issue description"
        val errorMessage = "Error suggesting issue"
        coEvery { repository.suggestIssue(description) } throws Exception(errorMessage)

        // When
        viewModel.suggestIssue(description)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { iaStateObserver.onChanged(IAState.Loading) }
        verify { updateStateObserver.onChanged(UpdateState.Error(errorMessage)) }
    }

    @Test
    fun `resetState updates state to Idle`() {
        // When
        viewModel.resetState()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Idle) }
        verify { iaStateObserver.onChanged(IAState.Idle) }
    }
}
