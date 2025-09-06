package com.example.taskpro

import app.cash.turbine.test
import com.example.taskpro.data.local.dao.ProjectDao
import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.domain.repository.project.ProjectRepository
import com.example.taskpro.domain.use_case.project.GetProjectUseCase
import com.example.taskpro.presentation.screens.home.state.GetProjectUiState
import com.example.taskpro.presentation.screens.home.viewmodel.HomeScreenViewModel
import com.example.taskpro.utils.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class GetProjectUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: ProjectRepository = mockk()
    private val useCase = GetProjectUseCase(repository)

    @Test
    fun emits_Loading_then_Success_when_repository_succeeds(): Unit = runTest {

        val projects = listOf(
            ProjectModel(id = 1, name = "A", taskCount = 0, dueDate = null, priority = 1, completionPercentage = 0),
            ProjectModel(id = 2, name = "B", taskCount = 2, dueDate = null, priority = 2, completionPercentage = 30)
        )

        every { repository.getProjects() } returns flowOf(
            Resource.Loading(),
            Resource.Success(projects)
        )

        // Act + Assert
        useCase().test {
            // First item should be Loading
            assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)

            // Second item should be Success with our projects
            val success = awaitItem() as Resource.Success
            assertThat(success.data).hasSize(2)
            assertThat(success.data[0].name).isEqualTo("A")

            awaitComplete()
        }
    }

    @Test
    fun emit_loading_and_error_when_repository_fails(): Unit = runTest {
        every { repository.getProjects() } returns flowOf(
            Resource.Loading(),
            Resource.Error("Failed to fetch projects")
        )

        useCase().test {
            assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            val error = awaitItem() as Resource.Error
            assertThat(error.message).isEqualTo("Failed to fetch projects")
            awaitComplete()
        }
    }

    @Test
    fun emit_idle_loading_success_when_viewModel_succeed(): Unit = runTest {
        val projects = listOf(mockk<ProjectModel>(relaxed = true))

        every { useCase() } returns flowOf(
            Resource.Loading(),
            Resource.Success(projects)
        )

        val vm = HomeScreenViewModel(useCase)

        vm.uiState.test {
            assertEquals(GetProjectUiState.Idl, awaitItem())
            assertEquals(GetProjectUiState.Loading, awaitItem())
            assertTrue(awaitItem() is GetProjectUiState.SUCCESS)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun emit_Idle_Loading_Error_when_viewModel_fails(): Unit = runTest {

        every { useCase() } returns flowOf(
            Resource.Loading(),
            Resource.Error("Failed to fetch projects")
        )

        val vm = HomeScreenViewModel(useCase)

        vm.uiState.test {
            assertEquals(GetProjectUiState.Idl, awaitItem())

            advanceUntilIdle()

            assertEquals(GetProjectUiState.Loading, awaitItem())

            val errorState = awaitItem()
            assertTrue(errorState is GetProjectUiState.ERROR)

            //below two lines are same assertThat is from Google truth and assertEquals is from jUnit.
            assertThat((errorState as GetProjectUiState.ERROR).message).isEqualTo("Failed to fetch projects")
            assertEquals("Failed to fetch projects", (errorState as GetProjectUiState.ERROR).message)

            cancelAndIgnoreRemainingEvents()

        }
    }
}