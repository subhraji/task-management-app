package com.example.taskpro.domain.model.project

import com.example.taskpro.data.local.entity.ProjectEntity

data class ProjectModel(
    val id: Int? = null,
    val name: String?,
    val taskCount: Int? = 0,
    val dueDate: String?,
    val priority: Int?,
    val completionPercentage: Int? = 0,
    val createdAt: Long? = System.currentTimeMillis()
){
    fun convertToProjectEntity(): ProjectEntity {
        return ProjectEntity(
            id = id,
            name = name,
            taskCount = taskCount,
            dueDate = dueDate,
            priority = priority,
            completionPercentage = completionPercentage,
            createdAt = createdAt
        )
    }
}
