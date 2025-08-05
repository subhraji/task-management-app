package com.example.taskpro.domain.use_case.project

import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.domain.repository.project.ProjectRepository
import javax.inject.Inject

class UpdateProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(project: ProjectModel) {
        projectRepository.updateProject(project)
    }
}