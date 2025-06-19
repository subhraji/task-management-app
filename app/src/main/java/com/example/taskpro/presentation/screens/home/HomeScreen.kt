package com.example.taskpro.presentation.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskpro.domain.model.ProjectModel
import com.example.taskpro.presentation.screens.home.components.ProjectListItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onProjectClick: (String) -> Unit){

    val projectList = remember {
        mutableStateListOf(
            ProjectModel(id = "1442837", name = "Water log Project", taskCount = 3, dueDate = null, priority = 2, completionPercentage = 40),
            ProjectModel(id = "1228393", name = "Example Project", taskCount = 8, dueDate = null, priority = 4, completionPercentage = 10),
            ProjectModel(id = "7878637", name = "Test Project", taskCount = 2, dueDate = null, priority = 1, completionPercentage = 70),
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Projects") }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(projectList.size) { index ->
                val project = projectList[index]
                ProjectListItemCard(onProjectClick = onProjectClick, project = project)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(onProjectClick = {})
}