package com.example.taskpro.presentation.screens.task_board.components

import android.content.ClipDescription
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskpro.domain.model.TaskDataModel
import com.example.taskpro.domain.model.TaskStatus


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskBoardBox(
    taskLists: List<TaskDataModel>,
    boxStatus: TaskStatus,
    backgroundColor: Color,
    onDropTask: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val tasksForStatus by remember(taskLists) {
        derivedStateOf {
            taskLists.filter { it.status == boxStatus }
        }
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(backgroundColor)
            .dragAndDropTarget(
                shouldStartDragAndDrop = { event ->
                    event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                },
                target = remember {
                    object : DragAndDropTarget {
                        override fun onDrop(event: DragAndDropEvent): Boolean {
                            val taskId = event
                                .toAndroidDragEvent()
                                .clipData
                                ?.getItemAt(0)
                                ?.text
                                ?.toString()

                            if (!taskId.isNullOrBlank()) {
                                onDropTask(taskId)

                                val task = taskLists.find { it.id == taskId }
                                //task?.status = boxStatus

                                Toast.makeText(context, "Dropped task ID: $taskId current status: ${task?.status}", Toast.LENGTH_SHORT).show()
                            }
                            return true
                        }
                    }
                }
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = boxStatus.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }

            items(tasksForStatus, key = { it.id }) { task ->
                TaskBoardCard(task = task)
            }
        }
    }
}

