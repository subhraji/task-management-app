package com.example.taskpro

import app.cash.turbine.test
import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.domain.use_case.project.GetProjectUseCase
import com.example.taskpro.domain.use_case.project.SearchProjectUseCase
import com.example.taskpro.presentation.screens.home.state.GetProjectUiState
import com.example.taskpro.presentation.screens.home.viewmodel.HomeScreenViewModel
import com.example.taskpro.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

    private var getProjectUseCase : GetProjectUseCase = mockk()
    private var searchProjectUseCase : SearchProjectUseCase = mockk()

    private lateinit var viewModel : HomeScreenViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `init should call getProject and emit loading then success`() = runTest(testDispatcher){

        //Arrange
        val projects = listOf(ProjectModel(1, "Test project",
            3, "11", 1))

        coEvery{ getProjectUseCase() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(projects))
        }

        coEvery { searchProjectUseCase( any()) } returns flow {
            emit(Resource.Success(emptyList()))
        }

        //Act
        viewModel = HomeScreenViewModel(getProjectUseCase, searchProjectUseCase)

        //Assert
        viewModel.uiState.test {

            assertEquals(GetProjectUiState.Idl, awaitItem())

            val loading = awaitItem()
            assertTrue(loading is GetProjectUiState.Loading)

            val success = awaitItem()
            assertTrue(success is GetProjectUiState.SUCCESS)

            val successState = success as GetProjectUiState.SUCCESS
            assertEquals(projects, successState.projectList)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search should call searchProject and emit loading then success`() = runTest(testDispatcher) {

        //Arrange
        val projects = listOf(ProjectModel(1, "Test project",
            3, "11", 1))

        coEvery { searchProjectUseCase("abc") } returns flow {
            emit(Resource.Success(projects))
        }

        coEvery { getProjectUseCase() } returns flow {
            emit(Resource.Success(emptyList()))
        }

        //Act
        viewModel = HomeScreenViewModel(getProjectUseCase, searchProjectUseCase)
        runCurrent()

        viewModel.uiState.test {

            val initSuccess = awaitItem()
            assertTrue(initSuccess is GetProjectUiState.SUCCESS)

            viewModel.updateSearchQuery("abc")

            advanceTimeBy(300)
            runCurrent()

            val loading = awaitItem()
            assertTrue(loading is GetProjectUiState.Loading)

            val success = awaitItem()
            assertTrue(success is GetProjectUiState.SUCCESS)

            val successState = success as GetProjectUiState.SUCCESS
            assertEquals(projects, successState.projectList)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `clearing search should call getProjects`() = runTest(testDispatcher) {

        //Arrange
        coEvery { getProjectUseCase() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(emptyList()))
        }

        coEvery { searchProjectUseCase(any()) } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(emptyList()))
        }

        //Act
        viewModel = HomeScreenViewModel(getProjectUseCase, searchProjectUseCase)
        runCurrent()

        //Assert
        viewModel.uiState.test {

            // The current state is already Success from init
            val initSuccess = awaitItem()
            assertTrue(initSuccess is GetProjectUiState.SUCCESS)

            viewModel.updateSearchQuery("abc")
            advanceTimeBy(301)
            runCurrent()

            assertTrue(awaitItem() is GetProjectUiState.Loading)
            val searchSuccess = awaitItem()
            assertTrue(searchSuccess is GetProjectUiState.SUCCESS)

            viewModel.updateSearchQuery("")
            advanceTimeBy(301)
            runCurrent()

            assertTrue(awaitItem() is GetProjectUiState.Loading)
            val finalSuccess = awaitItem()
            assertTrue(finalSuccess is GetProjectUiState.SUCCESS)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `search should call searchProjectUseCase with correct query`() = runTest {
        //Arrange
        coEvery { searchProjectUseCase(any()) } returns flow {
            emit(Resource.Success(emptyList()))
        }

        coEvery { getProjectUseCase() } returns flow {
            emit(Resource.Success(emptyList()))
        }

        //Act
        viewModel = HomeScreenViewModel(getProjectUseCase, searchProjectUseCase)
        runCurrent()

        viewModel.updateSearchQuery("abc")
        advanceTimeBy(300)
        runCurrent()

        coVerify(exactly = 1) { searchProjectUseCase("abc") }
        coVerify(exactly = 1) { getProjectUseCase() }
    }

    @Test
    fun `search should throw error when use case fails`() = runTest {

        //Arrange
        coEvery { searchProjectUseCase("abc") } returns flow {
            emit(Resource.Error("Something went wrong"))
        }

        coEvery { getProjectUseCase() } returns flow {
            emit(Resource.Success(emptyList()))
        }

        //Act
        viewModel = HomeScreenViewModel(getProjectUseCase, searchProjectUseCase)
        runCurrent()

        viewModel.uiState.test {

            skipItems(1)

            viewModel.updateSearchQuery("abc")
            advanceTimeBy(300)
            runCurrent()

            val loading = awaitItem()
            assertTrue(loading is GetProjectUiState.Loading)

            val error = awaitItem()
            assertTrue(error is GetProjectUiState.ERROR)

            val errorState = error as GetProjectUiState.ERROR
            assertEquals("Something went wrong", errorState.message)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `previous search should be cancelled when new query comes`() = runTest(testDispatcher) {

        //Arrange
        coEvery { searchProjectUseCase(any()) } answers {
            val query = firstArg<String>()
            val projects = listOf(ProjectModel(1, query,
                3, "11", 1))

            flow {
                emit(Resource.Loading())
                delay(1000)
                emit(Resource.Success(projects))
            }
        }

        coEvery { getProjectUseCase() } returns flow {
            emit(Resource.Success(emptyList()))
        }

        //Act
        viewModel = HomeScreenViewModel(getProjectUseCase, searchProjectUseCase)
        runCurrent()

        viewModel.uiState.test {

            skipItems(1)

            viewModel.updateSearchQuery("a")
            advanceTimeBy(100)

            viewModel.updateSearchQuery("abc")
            advanceTimeBy(300)
            advanceTimeBy(1000)

            runCurrent()

            assertTrue( awaitItem() is GetProjectUiState.Loading)

            val success = awaitItem()
            assertTrue(success is GetProjectUiState.SUCCESS)
            val successState = success as GetProjectUiState.SUCCESS

            assertEquals("abc", successState.projectList.first().name)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should emit error when exception is thrown`() = runTest(testDispatcher) {
        //Arrange
        coEvery { searchProjectUseCase("abc") } returns flow {
            emit(Resource.Loading())
            throw RuntimeException("crash happened")
        }

        coEvery { getProjectUseCase() } returns flow {
            emit(Resource.Success(emptyList()))
        }

        //Act
        viewModel = HomeScreenViewModel(getProjectUseCase, searchProjectUseCase)
        runCurrent()

        //Assert
        viewModel.uiState.test {

            skipItems(1)

            viewModel.updateSearchQuery("abc")
            advanceTimeBy(300)
            runCurrent()

            val loading = awaitItem()
            assertTrue(loading is GetProjectUiState.Loading)

            val error = awaitItem()
            assertTrue(error is GetProjectUiState.ERROR)

            val errorState = error as GetProjectUiState.ERROR

            errorState.message?.let { msg ->
                assertTrue(msg.contains("crash"))
            }

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should handle null error message`() = runTest {
        //Arrange
        coEvery { searchProjectUseCase("abc") } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(null))
        }

        coEvery { getProjectUseCase() } returns flow {
            emit(Resource.Success(emptyList()))
        }

        //Act
        viewModel = HomeScreenViewModel(getProjectUseCase, searchProjectUseCase)
        runCurrent()

        //Assert
        viewModel.uiState.test {

            skipItems(1)

            viewModel.updateSearchQuery("abc")
            advanceTimeBy(300)
            runCurrent()

            val loading = awaitItem()
            assertTrue(loading is GetProjectUiState.Loading)

            val error = awaitItem()
            assertTrue(error is GetProjectUiState.ERROR)

            val errorState = error as GetProjectUiState.ERROR
            assertEquals(null, errorState.message)

            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `should recover after retry`() = runTest {

        val projects = listOf(ProjectModel(1, "test",
            3, "11", 1))

        var callCount = 0

        coEvery { getProjectUseCase() } answers {
            callCount++

            flow {
                if (callCount == 1) {
                    emit(Resource.Error("failed to load."))
                } else {
                    emit(Resource.Success(projects))
                }
            }
        }

        viewModel = HomeScreenViewModel(getProjectUseCase, searchProjectUseCase)
        runCurrent()

        viewModel.uiState.test {

            assertTrue(awaitItem() is GetProjectUiState.ERROR)

            // retry
            viewModel.getProjects()
            runCurrent()

            assertTrue(awaitItem() is GetProjectUiState.Loading)

            val success = awaitItem() as GetProjectUiState.SUCCESS
            assertEquals("test", success.projectList.first().name)
        }
    }
}