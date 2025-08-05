package com.example.taskpro.domain.use_case.project

import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.domain.repository.project.ProjectRepository
import javax.inject.Inject

class AddProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(projectModel: ProjectModel): Result<Unit>{
        return try {
            projectRepository.addProject(projectModel)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}