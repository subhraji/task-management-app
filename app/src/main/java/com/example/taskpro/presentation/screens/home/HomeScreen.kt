package com.example.taskpro.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.presentation.screens.home.components.ProjectSwipeItem
import com.example.taskpro.presentation.screens.home.state.GetProjectUiState
import com.example.taskpro.presentation.screens.home.viewmodel.HomeScreenViewModel
import com.example.taskpro.ui.theme.yellowPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProjectClick: (ProjectModel) -> Unit,
){
    val viewModel: HomeScreenViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Projects") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ){
            when(uiState){
                is GetProjectUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is GetProjectUiState.ERROR -> {
                    val message = (uiState as GetProjectUiState.ERROR).message
                    Text("Error: $message")
                }
                is GetProjectUiState.Idl -> {
                    CircularProgressIndicator()
                }
                is GetProjectUiState.SUCCESS -> {
                    val projects = (uiState as GetProjectUiState.SUCCESS).projectList
                    LazyColumn{
                        items(projects.size) { index ->
                            val project = projects[index]
                            ProjectSwipeItem(
                                project = project,
                                onProjectClick = onProjectClick,
                                onDelete = { projectToDelete ->
                                    //projects.remove(projectToDelete)
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(onProjectClick = {})
}