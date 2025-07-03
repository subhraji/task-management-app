@file:OptIn(ExperimentalFoundationApi::class)

package com.example.taskpro.presentation.screens.task_board

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.taskpro.domain.model.TaskDataModel
import com.example.taskpro.domain.model.TaskStatus
import com.example.taskpro.presentation.screens.task_board.components.TaskBoardBox
import com.example.taskpro.ui.theme.black
import com.example.taskpro.ui.theme.completedGreen
import com.example.taskpro.ui.theme.ongoingAmber
import com.example.taskpro.ui.theme.pendingRed
import com.example.taskpro.ui.theme.yellowPrimary
import com.example.taskpro.utils.setScreenOrientation

@Composable
fun TaskBoardScreen(
    modifier: Modifier = Modifier,
    projectId: String,
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
            TaskDataModel("1442837", "Buy groceries", TaskStatus.Pending),
            TaskDataModel("23223r32", "Walk the dog", TaskStatus.Pending),
            TaskDataModel("32424244", "Read a book", TaskStatus.InProgress),
            TaskDataModel("32424245", "Drink water", TaskStatus.Completed)
        )
    }

    Box {

        Row(modifier = modifier.fillMaxSize()) {
            // PENDING
            TaskBoardBox(
                taskLists = taskLists,
                boxStatus = TaskStatus.Pending,
                backgroundColor = pendingRed,
                onDropTask = { taskId ->
                    val task = taskLists.find { it.id == taskId }
                    task?.status = TaskStatus.Pending
                },
                modifier = Modifier.weight(1f),
                onBackClick = onBackClick
            )

            // ONGOING
            TaskBoardBox(
                taskLists = taskLists,
                boxStatus = TaskStatus.InProgress,
                backgroundColor = ongoingAmber,
                onDropTask = { taskId ->
                    val task = taskLists.find { it.id == taskId }
                    task?.status = TaskStatus.InProgress
                },
                modifier = Modifier.weight(1f)
            )

            // COMPLETED
            TaskBoardBox(
                taskLists = taskLists,
                boxStatus = TaskStatus.Completed,
                backgroundColor = completedGreen,
                onDropTask = { taskId ->
                    val task = taskLists.find { it.id == taskId }
                    task?.status = TaskStatus.Completed
                },
                modifier = Modifier.weight(1f)
            )
        }

        FloatingActionButton(
            onClick = { },
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
