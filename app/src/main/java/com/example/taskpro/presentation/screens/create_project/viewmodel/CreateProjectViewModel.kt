package com.example.taskpro.presentation.screens.create_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.domain.use_case.project.AddProjectUseCase
import com.example.taskpro.presentation.screens.create_project.state.CreateProjectUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProjectViewModel @Inject constructor(
    private val addProjectUseCase: AddProjectUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<CreateProjectUiState>(CreateProjectUiState.Idle)
    val uiState: StateFlow<CreateProjectUiState> = _uiState.asStateFlow()

    fun createProject( project: ProjectModel){
        viewModelScope.launch {
            _uiState.value = CreateProjectUiState.Loading

            val result = addProjectUseCase(project)

            _uiState.value = when{
                result.isSuccess -> CreateProjectUiState.Success("Project created successfully")
                else -> CreateProjectUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}