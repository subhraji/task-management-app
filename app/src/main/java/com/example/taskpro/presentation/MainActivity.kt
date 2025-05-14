package com.example.taskpro.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskpro.presentation.component.DragAndDropBoxes
import com.example.taskpro.presentation.component.DragAndDropLists
import com.example.taskpro.ui.theme.TaskProTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskProTheme {
                DragAndDropBoxes(modifier = Modifier)
            }
        }
    }
}