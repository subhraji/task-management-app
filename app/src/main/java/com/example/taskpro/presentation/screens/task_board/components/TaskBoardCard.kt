package com.example.taskpro.presentation.screens.task_board.components

import android.content.ClipData
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskpro.domain.model.task.TaskDataModel
import com.example.taskpro.domain.model.task.TaskStatus
import com.example.taskpro.ui.theme.darkGrayBackground
import com.example.taskpro.ui.theme.darkOrange

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskBoardCard(
    task: TaskDataModel
) {

    var isLocked by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
    Card(
        modifier = Modifier
            .then(
                if (!isLocked) {
                    Modifier
                        .dragAndDropSource {
                            startTransfer(
                                transferData = DragAndDropTransferData(
                                    clipData = ClipData.newPlainText("task_id", task.id)
                                )
                            )
                        }

                } else Modifier.clickable {}
            )
            .fillMaxWidth()
            .shadow(elevation = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = task.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${task.description}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                if (isLocked){
                    Text(
                        "View Task",
                        modifier = Modifier
                            .clickable {  },
                        fontSize = 12.sp,
                        color = darkOrange,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(1f, 1f),
                                blurRadius = 1f),
                        )
                    )
                }

            }

        }

    }

    Box(
        modifier = Modifier
            .offset(x = 4.dp, y = (-4).dp)
            .size(22.dp)
            .align(Alignment.TopEnd)
            .clip(CircleShape)
            .background(darkGrayBackground)
            .clickable {
                isLocked = !isLocked
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isLocked) Icons.Filled.Create else Icons.Filled.Close,
            contentDescription = "Toggle Lock",
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
    }
}
}

@Composable
@Preview(showBackground = false)
fun TaskBoardCardPreview(){
    TaskBoardCard(
        TaskDataModel(
            id = "123",
            title = "Task title",
            description = "test description test description test description test description test description",
            dueDate = 22,
            priority = 1,
            status = TaskStatus.Pending
        )
    )
}