package com.example.taskpro.presentation.screens.create_task

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskpro.domain.model.ProjectModel
import com.example.taskpro.domain.model.local.ProjectPriorityModel
import com.example.taskpro.presentation.screens.create_project.CreateProjectScreen
import com.example.taskpro.ui.theme.amber
import com.example.taskpro.ui.theme.darkGrayBackground
import com.example.taskpro.ui.theme.darkText
import com.example.taskpro.ui.theme.green
import com.example.taskpro.ui.theme.orange
import com.example.taskpro.ui.theme.pink
import com.example.taskpro.ui.theme.red
import com.example.taskpro.ui.theme.white
import com.example.taskpro.ui.theme.yellowPrimary
import com.example.taskpro.ui.theme.yellowTertiary
import com.example.taskpro.utils.isDark
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    project: ProjectModel,
    onBack: () -> Unit = {}
){

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val priorityOptions = listOf(
        ProjectPriorityModel(green, 1),
        ProjectPriorityModel(amber, 2),
        ProjectPriorityModel(orange, 3),
        ProjectPriorityModel(pink, 4),
        ProjectPriorityModel(red, 5)
    )
    var selectedPriority by remember { mutableStateOf(priorityOptions.first()) }
    val selectedBorderColor = if (isDark()) white else darkGrayBackground

    var dueDate by remember { mutableStateOf("") }
    var showDatePicker = remember { mutableStateOf(false) }

    if(showDatePicker.value){
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                dueDate = "$day/${month + 1}/$year"
                showDatePicker.value = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = System.currentTimeMillis()
        datePicker.show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Task") },
                navigationIcon = { IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = darkGrayBackground,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(yellowTertiary)

            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = project.name.orEmpty(),
                        color = darkText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Project id: JJM143",
                        color = darkText,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text("Task Name")
            OutlinedTextField(
                value = name,
                onValueChange = { newName -> name = newName },
                label = { Text("Type task name here ...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text("Task description")
            OutlinedTextField(
                value = description,
                onValueChange = { newDescription -> description = newDescription },
                label = { Text("Type task description here ...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(120.dp)
            )

            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text("Due date")
            OutlinedTextField(
                value = dueDate,
                onValueChange = { },
                label = { Text("Select due date") },
                readOnly = true,
                enabled = false,
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker.value = true }
            )

            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text("Choose Task Priority")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(priorityOptions.size) { index ->
                    val priority = priorityOptions[index]
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = priority.color,
                                shape = CircleShape
                            )
                            .border(
                                width = if (priority.color == selectedPriority.color) 3.dp else 0.dp,
                                color = if (priority.color == selectedPriority.color) selectedBorderColor else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { selectedPriority = priority }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ){
                Text(
                    text = "Create Task",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun CreateTaskScreenPreview(){
    CreateTaskScreen(project = ProjectModel("g","j",2, 12, 1))
}