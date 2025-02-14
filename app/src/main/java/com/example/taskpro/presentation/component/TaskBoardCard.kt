package com.example.taskpro.presentation.component

import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskpro.domain.Model.TaskDataModel
import com.example.taskpro.ui.theme.TaskProTheme

@Composable
fun TaskBoardCard(task: TaskDataModel){
    Card {
        Column(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.Start)
        ){
            Text("Task Id: ${task.id}")
            Text("Task: ${task.task}")
        }
    }
}

@Preview
@Composable
fun TaskBoardCardPreview(){
    TaskProTheme {
        //TaskBoardCard()
    }
}