package com.example.taskpro.presentation.component

import android.content.ClipData
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskpro.domain.Model.TaskDataModel
import com.example.taskpro.domain.Model.TaskStatus

/*@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskBoardCard(task: TaskDataModel, dragBoxIndex: Int){
    AnimatedVisibility(
        visible = true
    ){
        Card(
            modifier = Modifier
                .dragAndDropSource {
                    startTransfer(
                        transferData = DragAndDropTransferData(
                            clipData = ClipData.newPlainText(
                                "text",
                                "Drag me!"
                            )
                        )
                    )
                }
                .fillMaxWidth()
                .height(80.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = task.task ?: "No task",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
    }

}*/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskBoardCard(task: TaskDataModel) {
    Card(
        modifier = Modifier
            .dragAndDropSource {
                startTransfer(
                    transferData = DragAndDropTransferData(
                        clipData = ClipData.newPlainText("task_id", task.id ?: "")
                    )
                )
            }
            .fillMaxWidth()
            .height(80.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = task.task ?: "No task",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

private fun isVisible(task: TaskDataModel, boxIndex: Int): Boolean{
    return task.status == boxStatus(boxIndex)
}

private fun boxStatus(boxIndex: Int): TaskStatus {
    return when(boxIndex){
        0 -> TaskStatus.Started
        1 -> TaskStatus.Ongoing
        2 -> TaskStatus.Completed
        else -> {
            TaskStatus.Started}
    }
}