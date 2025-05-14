@file:OptIn(ExperimentalFoundationApi::class)

package com.example.taskpro.presentation.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.taskpro.domain.Model.TaskDataModel
import com.example.taskpro.domain.Model.TaskStatus
import kotlin.random.Random

/*
@Composable
fun DragAndDropBoxes(
    modifier: Modifier = Modifier
) {
    val boxCount = 3
    var dragBoxIndex by remember { mutableIntStateOf(0) }

    var draggedTaskId by remember { mutableStateOf<String?>(null) }


    val colors = remember {
        (1..boxCount).map {
            Color(Random.nextLong()).copy(alpha = 1f)
        }
    }

    val taskLists = remember { mutableStateListOf(
        TaskDataModel("1442837", "Buy groceries", TaskStatus.Stared),
        TaskDataModel("23223r32", "Walk the dog", TaskStatus.Stared),
        TaskDataModel("32424244", "Read a book",TaskStatus.Stared))
    }

    Column(modifier = modifier.fillMaxSize()) {
        val modifier1 = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(colors[0])
            .dragAndDropTarget(
                shouldStartDragAndDrop = { event ->
                    event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                },
                target = remember {
                    object : DragAndDropTarget {
                        override fun onDrop(event: DragAndDropEvent): Boolean {
//                            val text = event.toAndroidDragEvent().clipData
//                                ?.getItemAt(0)?.text
                            dragBoxIndex = 0
                            taskLists[dragBoxIndex].status = TaskStatus.Stared
                            return true
                        }
                    }
                }
            )

        val modifier2 = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(colors[1])
            .dragAndDropTarget(
                shouldStartDragAndDrop = { event ->
                    event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                },
                target = remember {
                    object : DragAndDropTarget {
                        override fun onDrop(event: DragAndDropEvent): Boolean {
                            dragBoxIndex = 1
                            taskLists[dragBoxIndex].status = TaskStatus.Ongoing
                            return true
                        }
                    }
                }
            )

        val modifier3 = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(colors[2])
            .dragAndDropTarget(
                shouldStartDragAndDrop = { event ->
                    event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                },
                target = remember {
                    object : DragAndDropTarget {
                        override fun onDrop(event: DragAndDropEvent): Boolean {
                            dragBoxIndex = 2
                            taskLists[dragBoxIndex].status = TaskStatus.Completed
                            return true
                        }
                    }
                }
            )



        TaskBoardBox(
            dragBoxIndex = dragBoxIndex,
            index = 0,
            taskLists = taskLists,
            boxStatus = TaskStatus.Stared,
            modifier = modifier1
        )
        TaskBoardBox(
            dragBoxIndex = dragBoxIndex,
            index = 1,
            taskLists = taskLists,
            boxStatus = TaskStatus.Ongoing,
            modifier = modifier2
        )
        TaskBoardBox(
            dragBoxIndex = dragBoxIndex,
            index = 2,
            taskLists = taskLists,
            boxStatus = TaskStatus.Completed,
            modifier = modifier3
        )
    }
}*/

@Composable
fun DragAndDropBoxes(modifier: Modifier = Modifier) {


    val boxCount = 3
    val colors = remember {
        (1..boxCount).map {
            Color(Random.nextLong()).copy(alpha = 1f)
        }
    }

    val taskLists = remember {
        mutableStateListOf(
            TaskDataModel("1442837", "Buy groceries", TaskStatus.Started),
            TaskDataModel("23223r32", "Walk the dog", TaskStatus.Started),
            TaskDataModel("32424244", "Read a book", TaskStatus.Ongoing),
            TaskDataModel("32424245", "Drink water", TaskStatus.Completed)

        )
    }


    Row(modifier = modifier.fillMaxSize()) {
        // STARED
        TaskBoardBox(
            taskLists = taskLists,
            boxStatus = TaskStatus.Started,
            backgroundColor = colors[0],
            onDropTask = { taskId ->
                Log.d("onDrop","ggg started box")
                val task = taskLists.find { it.id == taskId }
                task?.status = TaskStatus.Started
            },
            modifier = Modifier.weight(1f)
        )

        // ONGOING
        TaskBoardBox(
            taskLists = taskLists,
            boxStatus = TaskStatus.Ongoing,
            backgroundColor = colors[1],
            onDropTask = { taskId ->
                Log.d("onDrop","ggg ongoing box")
                val task = taskLists.find { it.id == taskId }
                task?.status = TaskStatus.Ongoing
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
