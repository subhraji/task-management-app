package com.example.taskpro.domain.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TaskDataModel(
    val id: String,
    val title: String?,
    val description: String?,
    val dueDate: Long?,
    val priority: Int?,
    val createdAt: Long? = System.currentTimeMillis(),
    status: TaskStatus
) {
    var status by mutableStateOf(status)
}

enum class TaskStatus(var status: String){
    Pending("pending"),
    InProgress("inProgress"),
    Completed("completed")
}