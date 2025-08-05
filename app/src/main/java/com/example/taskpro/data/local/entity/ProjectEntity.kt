package com.example.taskpro.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskpro.domain.model.project.ProjectModel

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String?,
    val taskCount: Int?,
    val dueDate: String?,
    val priority: Int?,
    val completionPercentage: Int?,
    val createdAt: Long?
){
    fun convertToProjectModel(): ProjectModel {
        return ProjectModel(
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
