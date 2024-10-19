package co.uniandes.abcall.ui.screens.issues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class IssuesViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: IssuesViewModel
    private lateinit var repository: IssuesRepository
    private lateinit var updateStateObserver: Observer<UpdateState>
    private lateinit var issuesObserver: Observer<List<IssueResponse>>

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = IssuesViewModel(repository)
        updateStateObserver = mockk(relaxed = true)
        issuesObserver = mockk(relaxed = true)

        Dispatchers.setMain(testDispatcher)
        viewModel.updateState.observeForever(updateStateObserver)
        viewModel.issues.observeForever(issuesObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.updateState.removeObserver(updateStateObserver)
        viewModel.issues.removeObserver(issuesObserver)
    }

    @Test
    fun `loadIssues success updates state to Success and issues sorted`() = runTest {
        // Given
        val issuesList = listOf(
            IssueResponse(1, IssueType.REQUEST,
                "Description 1",
                "Solution 1",
                IssueStatus.OPEN,
                IssueSource.APP_MOBILE,
                1,
                1,
                Date(1633046400000),
                null
            ),
            IssueResponse(
                2,
                IssueType.COMPLAINT,
                "Description 2",
                "Solution 2",
                IssueStatus.CLOSED,
                IssueSource.APP_MOBILE,
                2,
                2,
                Date(1633132800000),
                null
            )
        )
        coEvery { repository.getIssues() } returns Result.Success(issuesList)

        // When
        viewModel.loadIssues()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Loading) }
        verify { updateStateObserver.onChanged(UpdateState.Success) }
        verify { issuesObserver.onChanged(issuesList.sortedByDescending { it.createdAt }) }
    }

    @Test
    fun `loadIssues error updates state to Error`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { repository.getIssues() } returns Result.Error(errorMessage)

        // When
        viewModel.loadIssues()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Loading) }
        verify { updateStateObserver.onChanged(UpdateState.Error(errorMessage)) }
    }

    @Test
    fun `loadIssues exception updates state to Error`() = runTest {
        // Given
        val exceptionMessage = "Unexpected error"
        coEvery { repository.getIssues() } throws Exception(exceptionMessage)

        // When
        viewModel.loadIssues()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Loading) }
        verify { updateStateObserver.onChanged(UpdateState.Error(exceptionMessage)) }
    }

    @Test
    fun `resetState updates state to Idle`() {
        // When
        viewModel.resetState()

        // Then
        verify { updateStateObserver.onChanged(UpdateState.Idle) }
    }
}
