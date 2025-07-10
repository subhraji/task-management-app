package com.example.taskpro.presentation.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.taskpro.domain.model.ProjectModel

class SharedProjectViewModel : ViewModel() {
    var selectedProject by mutableStateOf<ProjectModel?>(null)
}