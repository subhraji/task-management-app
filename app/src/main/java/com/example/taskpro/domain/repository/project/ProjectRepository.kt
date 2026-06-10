package com.example.taskpro.domain.repository.project

import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getProjects(): Flow<Resource<List<ProjectModel>>>
    suspend fun addProject(project: ProjectModel): Result<Unit>
    suspend fun updateProject(project: ProjectModel): Result<Unit>
    suspend fun deleteProject(project: ProjectModel): Result<Unit>
    fun searchProjects(query: String): Flow<Resource<List<ProjectModel>>>
}