package com.example.taskpro.presentation.screens.create_project.state

sealed class CreateProjectUiState {
    object Idle : CreateProjectUiState()
    object Loading : CreateProjectUiState()
    data class Success(val message: String) : CreateProjectUiState()
    data class Error(val message: String) : CreateProjectUiState()
}