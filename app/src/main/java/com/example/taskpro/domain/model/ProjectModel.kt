package com.example.taskpro.domain.model

data class ProjectModel(
    val id: String,
    val name: String?,
    val taskCount: Int?,
    val dueDate: Long?,
    val priority: Int?,
    val completionPercentage: Int? = 0,
    val createdAt: Long? = System.currentTimeMillis()
)
