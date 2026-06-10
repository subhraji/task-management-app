package com.example.taskpro.presentation.screens.home

import android.R.id.bold
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskpro.R
import com.example.taskpro.domain.model.project.ProjectModel
import com.example.taskpro.presentation.screens.home.components.ProjectSwipeItem
import com.example.taskpro.presentation.screens.home.state.GetProjectUiState
import com.example.taskpro.presentation.screens.home.viewmodel.HomeScreenViewModel
import com.example.taskpro.ui.theme.darkText
import com.example.taskpro.ui.theme.yellowPrimary

@Composable
fun HomeScreen(
    onProjectClick: (ProjectModel) -> Unit,
    onAddProjectClick: () -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.restoreSearchState()
    }

    HomeScreenContent(
        uiState = uiState,
        searchQuery = searchQuery,
        isSearching = isSearching,
        onSearchQueryChange = viewModel::updateSearchQuery,
        onToggleSearch = viewModel::toggleSearch,
        onProjectClick = onProjectClick,
        onAddProjectClick = onAddProjectClick,
        onDeleteProject = viewModel::deleteProject
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: GetProjectUiState,
    searchQuery: String,
    isSearching: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    onProjectClick: (ProjectModel) -> Unit,
    onAddProjectClick: () -> Unit,
    onDeleteProject: (ProjectModel) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching){
                        customSearchField(
                            searchQuery = searchQuery,
                            onSearchQueryChange = onSearchQueryChange
                        )
                    } else {
                        Text("Your Projects")
                    }
                },
                actions = {
                    IconButton( onClick = {
                        if (isSearching){
                            onSearchQueryChange("")
                        }
                        onToggleSearch()
                    }) {
                        Icon(
                            imageVector = if (isSearching) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
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
                    val message = uiState.message
                    Text("Error: $message")
                }
                is GetProjectUiState.Idl -> {
                    CircularProgressIndicator()
                }
                is GetProjectUiState.SUCCESS -> {
                    val projects = uiState.projectList

                    if (projects.isEmpty()){
                        NoDataFoundComponent(onAddProjectClick = onAddProjectClick)
                    } else {
                        LazyColumn{
                            items(projects.size) { index ->
                                val project = projects[index]
                                ProjectSwipeItem(
                                    project = project,
                                    onProjectClick = onProjectClick,
                                    onDelete = onDeleteProject
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun customSearchField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp) // exact height you want
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp), // inner padding
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            singleLine = true,
            textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
            ,   decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search projects...",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun NoDataFoundComponent(onAddProjectClick: () -> Unit){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {

            Image(
                painter = painterResource(R.drawable.empty_error_img),
                contentDescription = "No projects",
                modifier = Modifier.size(150.dp)
            )

            Text(
                text = "No projects added.",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = "You don't have any project added yet, Please add a project",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Gray
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Button(
                onClick = onAddProjectClick,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = yellowPrimary),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Text("Add Project", color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreenContent(
        uiState = GetProjectUiState.SUCCESS(emptyList(), "Success"),
        searchQuery = "",
        isSearching = false,
        onSearchQueryChange = {},
        onToggleSearch = {},
        onProjectClick = {},
        onAddProjectClick = {},
        onDeleteProject = {}
    )
}