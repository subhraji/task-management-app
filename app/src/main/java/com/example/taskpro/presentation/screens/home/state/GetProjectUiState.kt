package com.example.taskpro.presentation.screens.home.state

import com.example.taskpro.domain.model.project.ProjectModel

sealed class GetProjectUiState {

    object Idl: GetProjectUiState()

    object Loading: GetProjectUiState()

    data class SUCCESS(
        val projectList: List<ProjectModel>,
        val message: String
    ): GetProjectUiState()

    data class ERROR(
        val message: String
    ): GetProjectUiState()
}