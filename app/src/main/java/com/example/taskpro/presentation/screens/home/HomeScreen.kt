package com.example.taskpro.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskpro.domain.model.ProjectModel
import com.example.taskpro.presentation.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onProjectClick: (String) -> Unit){

    val projectList = remember {
        mutableStateListOf(
            ProjectModel("1442837", "Abcd Project", 3),
            ProjectModel("1228393", "Example Project", 8),
            ProjectModel("7878637", "Test Project", 2),
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
                Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { onProjectClick(project.id) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(project.name, fontWeight = FontWeight.Bold)
                            Text("${project.taskCount} tasks", color = Color.Gray)
                        }
                    }
            }
        }
    }
}