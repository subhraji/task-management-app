package com.example.taskpro.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun DragAndDropLists() {


    // State for the first list
    var list1 by remember { mutableStateOf(listOf("Item 1", "Item 2")) }
    // State for the second list
    var list2 by remember { mutableStateOf(listOf("Item 3", "Item 4")) }

    // State to track the item being dragged
    var draggedItem by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // First List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            items(list1) { item ->
                DraggableItem(
                    item = item,
                    onDragStart = { draggedItem = item },
                    onDragStopped = { draggedItem = null },
                    orientation = Orientation.Vertical
                ) {
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                            .padding(16.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }

        // Second List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            items(list2) { item ->
                DraggableItem(
                    item = item,
                    onDragStart = { draggedItem = item },
                    onDragStopped = { draggedItem = null },
                    orientation = Orientation.Vertical
                ) {
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                            .padding(16.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }

    // Handle dropping the item into the other list
    LaunchedEffect(draggedItem) {
        if (draggedItem != null) {
            // Remove the item from the original list
            list1 = list1.filter { it != draggedItem }
            list2 = list2.filter { it != draggedItem }

            // Add the item to the other list
            if (draggedItem in list1) {
                list2 = list2 + draggedItem!!
            } else {
                list1 = list1 + draggedItem!!
            }

            // Reset the dragged item
            draggedItem = null
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableItem(
    item: String,
    onDragStart: () -> Unit,
    onDragStopped: () -> Unit,
    orientation: Orientation,
    content: @Composable () -> Unit
) {

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta -> if (isDragging) offsetX += delta },
                onDragStopped = {  })
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta -> if (isDragging) offsetY += delta },
                onDragStopped = {  })
    ) {
        content()
    }
}