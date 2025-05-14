package com.example.taskpro.domain.Model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TaskDataModel(
    val id: String,
    val task: String,
    status: TaskStatus
) {
    var status by mutableStateOf(status)
}

enum class TaskStatus(var status: String){
    Started("started"),
    Ongoing("ongoing"),
    Completed("completed")
}