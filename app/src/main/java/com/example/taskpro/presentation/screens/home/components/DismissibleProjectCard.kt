package com.example.taskpro.presentation.screens.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.taskpro.domain.model.ProjectModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ProjectSwipeItem(
    project: ProjectModel,
    onProjectClick: (String) -> Unit,
    onDelete: (ProjectModel) -> Unit
) {
    val swipeOffsetX = remember { Animatable(0f) }
    val maxSwipe = with(LocalDensity.current) { 70.dp.toPx() }
    val fullWidth = with(LocalDensity.current) { 400.dp.toPx() }

    var showDialog by remember { mutableStateOf(false) }
    var pendingDelete by remember { mutableStateOf(false) }

    /*LaunchedEffect(showDialog) {
        if (!showDialog) {
            swipeOffsetX.animateTo(0f)
        }
    }*/
    LaunchedEffect(showDialog, pendingDelete) {
        if (!showDialog && pendingDelete) {
            swipeOffsetX.animateTo(-fullWidth)
            onDelete(project)
            pendingDelete = false
        } else if (!showDialog && !pendingDelete) {
            swipeOffsetX.animateTo(0f)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Project") },
            text = { Text("Are you sure you want to delete this project?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    pendingDelete = true
                }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Background delete icon
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = { showDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }

        // Foreground swipe able card
        Box(
            modifier = Modifier
                .offset { IntOffset(swipeOffsetX.value.roundToInt(), 0) }
                .pointerInput(Unit) {
                    coroutineScope {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                launch {
                                    if (swipeOffsetX.value < -maxSwipe / 2) {
                                        swipeOffsetX.animateTo(-maxSwipe)
                                    } else {
                                        swipeOffsetX.animateTo(0f)
                                    }
                                }

                            },
                            onHorizontalDrag = { change, dragAmount ->
                                launch {
                                    val newOffset = (swipeOffsetX.value + dragAmount)
                                        .coerceIn(-maxSwipe, 0f)
                                    swipeOffsetX.snapTo(newOffset)
                                }

                            }
                        )
                    }

                }
        ) {
            ProjectListItemCard(
                project = project,
                onProjectClick = onProjectClick,
            )
        }
    }
}

