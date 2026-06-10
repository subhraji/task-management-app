package com.example.taskpro.data.repository.project

import com.example.taskpro.data.local.dao.ProjectDao
import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.domain.repository.project.ProjectRepository
import com.example.taskpro.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
   private val projectDao: ProjectDao
) : ProjectRepository {
    override fun getProjects(): Flow<Resource<List<ProjectModel>>> = flow {
        emit(Resource.Loading())
        try {
            projectDao.getAllProjects().map { entities ->
                entities.map { projectEntity -> projectEntity.convertToProjectModel() }
            }.catch { e ->
                emit(Resource.Error(e.localizedMessage ?: "Unknown error occurred"))
            }.collect{ projects ->
                emit(Resource.Success(projects))
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override suspend fun addProject(project: ProjectModel): Result<Unit> {
        return withContext(Dispatchers.IO){
            try {
                val id = projectDao.insertProject(project.convertToProjectEntity())
                Result.success(id)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun updateProject(project: ProjectModel): Result<Unit> {
        return withContext(Dispatchers.IO){
            try {
                projectDao.updateProject(project.convertToProjectEntity())
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun deleteProject(project: ProjectModel): Result<Unit> {
        return withContext(Dispatchers.IO){
            try {
                projectDao.deleteProject(project.convertToProjectEntity())
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override fun searchProjects(query: String): Flow<Resource<List<ProjectModel>>> = flow{
         emit(Resource.Loading())
         try {
             projectDao.searchProjects(query = query).map { entities ->
                 entities.map { projectEntities -> projectEntities.convertToProjectModel() }
             }.catch { e ->
                 emit(Resource.Error(e.localizedMessage ?: "Unknown error occurred"))
             }.collect{ projects ->
                 emit(Resource.Success(projects))
             }
         } catch (e: Exception){
             e.printStackTrace()
         }
    }
}