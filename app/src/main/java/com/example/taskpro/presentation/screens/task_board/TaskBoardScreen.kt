@file:OptIn(ExperimentalFoundationApi::class)

package com.example.taskpro.presentation.screens.task_board

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.taskpro.domain.model.task.TaskDataModel
import com.example.taskpro.domain.model.task.TaskStatus
import com.example.taskpro.presentation.screens.task_board.components.TaskBoardBox
import com.example.taskpro.ui.theme.black
import com.example.taskpro.ui.theme.darkGrayBackground
import com.example.taskpro.ui.theme.lightestGrayText
import com.example.taskpro.utils.setScreenOrientation

@Composable
fun TaskBoardScreen(
    modifier: Modifier = Modifier,
    projectId: Int,
    onBackClick: () -> Unit,
    onFabClick: () -> Unit
) {

    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(Unit) {
        activity?.let {
            setScreenOrientation(it, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            activity?.let {
                setScreenOrientation(it, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
        }
    }

    val taskLists = remember {
        mutableStateListOf(
            TaskDataModel(
                id = "1442837",
                title = "Buy groceries",
                description = "test description xyz and bla bla bla, nothing else for this task",
                dueDate = 22,
                priority = 2,
                status = TaskStatus.Pending),
            TaskDataModel(
                id = "123",
                title = "Buy groceries",
                description = "test description xyz and bla bla bla, nothing else for this task",
                dueDate = 22,
                priority = 2,
                status = TaskStatus.Pending),
            TaskDataModel(
                id = "122",
                title = "Buy groceries",
                description = "test description xyz and bla bla bla, nothing else for this task",
                dueDate = 22,
                priority = 2,
                status = TaskStatus.Pending),
            TaskDataModel(
                id = "244",
                title = "Buy groceries",
                description = "test description xyz and bla bla bla, nothing else for this task",
                dueDate = 22,
                priority = 2,
                status = TaskStatus.Pending),
            TaskDataModel(
                id = "657",
                title = "Buy groceries",
                description = "test description xyz and bla bla bla, nothing else for this task",
                dueDate = 22,
                priority = 2,
                status = TaskStatus.Pending),
            TaskDataModel(
                id = "23223r32",
                title = "Walk the dog",
                description = "test description",
                dueDate = 22,
                priority = 3,
                status = TaskStatus.Pending),
            TaskDataModel(
                id = "32424244",
                title = "Read a book",
                description = "test description",
                dueDate = 22,
                priority = 1,
                status = TaskStatus.InProgress),
            TaskDataModel(
                id = "32424245",
                title = "Drink water",
                description = "test description",
                dueDate = 22,
                priority = 4,
                status = TaskStatus.Completed)
        )
    }

    Box {

        Row(modifier = modifier.fillMaxSize()) {
            // PENDING
            TaskBoardBox(
                taskLists = taskLists,
                boxStatus = TaskStatus.Pending,
                backgroundColor = lightestGrayText,
                onDropTask = { taskId ->
                    val task = taskLists.find { it.id == taskId }
                    task?.status = TaskStatus.Pending
                },
                modifier = Modifier.weight(1f),
                onBackClick = onBackClick
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = darkGrayBackground)
            ) { }

            // ONGOING
            TaskBoardBox(
                taskLists = taskLists,
                boxStatus = TaskStatus.InProgress,
                backgroundColor = lightestGrayText,
                onDropTask = { taskId ->
                    val task = taskLists.find { it.id == taskId }
                    task?.status = TaskStatus.InProgress
                },
                modifier = Modifier.weight(1f),
                //onBackClick = onBackClick
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = darkGrayBackground)
            ) { }
            // COMPLETED
            TaskBoardBox(
                taskLists = taskLists,
                boxStatus = TaskStatus.Completed,
                backgroundColor = lightestGrayText,
                onDropTask = { taskId ->
                    val task = taskLists.find { it.id == taskId }
                    task?.status = TaskStatus.Completed
                },
                modifier = Modifier.weight(1f),
                //onBackClick = onBackClick
            )
        }

        FloatingActionButton(
            onClick = { onFabClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Task",
                tint = black
            )
        }
    }
}
