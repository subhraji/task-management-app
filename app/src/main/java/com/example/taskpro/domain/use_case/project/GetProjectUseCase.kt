package com.example.taskpro.domain.use_case.project

import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.domain.repository.project.ProjectRepository
import com.example.taskpro.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(): Flow<Resource<List<ProjectModel>>>{
        return projectRepository.getProjects()
    }
}