package com.example.taskpro.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskpro.domain.model.ProjectModel
import com.example.taskpro.ui.theme.darkGrayBackground
import com.example.taskpro.ui.theme.darkText
import com.example.taskpro.ui.theme.darkestGrayBackground
import com.example.taskpro.ui.theme.lightText
import com.example.taskpro.ui.theme.lightestGrayBackground
import com.example.taskpro.ui.theme.white

@Composable
fun ProjectListItemCard(
    project: ProjectModel,
    onProjectClick: (String) -> Unit,
){
    val priorityColor = when (project.priority) {
        1 -> Color(0xFF4CAF50) // Green
        2 -> Color(0xFF8BC34A)
        3 -> Color(0xFFFFC107) // Amber
        4 -> Color(0xFFFF9800)
        else -> Color(0xFFF44336) // Red
    }

    val isDark = isSystemInDarkTheme()
    val cardBackgroundColor = if (isDark) darkestGrayBackground else white
    val createdTimeTextColor = if (isDark) lightestGrayBackground else darkText

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onProjectClick(project.id) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .background(color = cardBackgroundColor)
        ) {
            // Priority colored stripe
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .background(priorityColor, shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            )

            // Content
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = project.name ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${project.createdAt}",
                    style = MaterialTheme.typography.bodySmall,
                    color = createdTimeTextColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Box(
                        modifier = Modifier
                            .background(
                                color = priorityColor,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = project.taskCount.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    CompletionProgressCircle(percentage = project.completionPercentage ?: 0)

                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProjectListItemCard(){
    val sampleProject = ProjectModel(
        id = "1",
        name = "Sample Project",
        taskCount = 5,
        dueDate = null,
        priority = null
    )
    ProjectListItemCard(onProjectClick = {}, project = sampleProject)
}