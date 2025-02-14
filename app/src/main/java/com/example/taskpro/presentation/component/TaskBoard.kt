package com.example.taskpro.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskpro.domain.Model.TaskDataModel
import com.example.taskpro.ui.theme.TaskProTheme

@Composable
fun TaskBoard(){

}

@Composable
fun TaskBoardContent(){

    val startedTaskList = remember { mutableStateListOf(
        TaskDataModel("1442837", "Buy groceries"),
        TaskDataModel("23223r32", "Walk the dog"),
        TaskDataModel("32424244", "Read a book"),
        TaskDataModel("42424242", "Workout"),
        TaskDataModel("52298899", "Call mom")
    )}

    val ongoingTaskList = remember { mutableStateListOf(
        TaskDataModel("038744", "Listen to music"),
        TaskDataModel("535353", "Go shopping")
    )}

    val completedTaskList = remember { mutableStateListOf(
        TaskDataModel("3231442", "Yoga")
    )}

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Started", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Ongoing", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Completed", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }

        Row (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TaskBoardStarted(modifier = Modifier.weight(1f), startedTaskList)
            TaskBoardOngoing(modifier = Modifier.weight(1f), ongoingTaskList)
            TaskBoardCompleted(modifier = Modifier.weight(1f), completedTaskList)
        }

    }

}

@Composable
fun TaskBoardStarted(
    modifier: Modifier = Modifier,
    tasks: List<TaskDataModel>
){
    LazyColumn(modifier = modifier) {
        items(tasks.size) { index ->
            Spacer(Modifier.height(10.dp))
            TaskBoardCard(task = tasks[index])
        }
    }
}

@Composable
fun TaskBoardOngoing(
    modifier: Modifier = Modifier,
    tasks: List<TaskDataModel>
){
    LazyColumn(modifier = modifier) {
        items(tasks.size) { index ->
            Spacer(Modifier.height(10.dp))
            TaskBoardCard(task = tasks[index])
        }
    }
}

@Composable
fun TaskBoardCompleted(
    modifier: Modifier = Modifier,
    tasks: List<TaskDataModel>
){
    LazyColumn(modifier = modifier) {
        items(tasks.size) { index ->
            Spacer(Modifier.height(10.dp))
            TaskBoardCard(task = tasks[index])
        }
    }
}

@Preview(
    name = "Landscape Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)@Composable
fun TaskBoardPreview(){
    TaskProTheme {
        TaskBoardContent()
    }
}