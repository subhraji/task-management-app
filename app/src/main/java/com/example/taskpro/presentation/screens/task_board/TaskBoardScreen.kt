@file:OptIn(ExperimentalFoundationApi::class)

package com.example.taskpro.presentation.screens.task_board

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.taskpro.domain.model.TaskDataModel
import com.example.taskpro.domain.model.TaskStatus
import com.example.taskpro.presentation.screens.task_board.components.TaskBoardBox
import com.example.taskpro.utils.setScreenOrientation
import kotlin.random.Random

@Composable
fun TaskBoardScreen(
    modifier: Modifier = Modifier,
    projectId: String,
    onBackClick: () -> Unit
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

    val boxCount = 3
    val colors = remember {
        (1..boxCount).map {
            Color(Random.nextLong()).copy(alpha = 1f)
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

    Row(modifier = modifier.fillMaxSize()) {
        // STARED
        TaskBoardBox(
            taskLists = taskLists,
            boxStatus = TaskStatus.Pending,
            backgroundColor = colors[0],
            onDropTask = { taskId ->
                Log.d("onDrop","ggg started box")
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
            backgroundColor = colors[1],
            onDropTask = { taskId ->
                Log.d("onDrop","ggg ongoing box")
                val task = taskLists.find { it.id == taskId }
                task?.status = TaskStatus.InProgress
            },
            modifier = Modifier.weight(1f)
        )

        // COMPLETED
        TaskBoardBox(
            taskLists = taskLists,
            boxStatus = TaskStatus.Completed,
            backgroundColor = colors[2],
            onDropTask = { taskId ->
                Log.d("onDrop","ggg completed box")
                val task = taskLists.find { it.id == taskId }
                task?.status = TaskStatus.Completed
            },
            modifier = Modifier.weight(1f)
        )
    }
}
