package com.example.todolist.Content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolist.R
import com.example.todolist.Routes
import com.example.todolist.Sub.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Today_Tasks(navController: NavController, taskViewModel: TaskViewModel) {
    val iconToggleViewModel: IconToggleViewModel = viewModel()
    val todayDate = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }

    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F5F5)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 40.dp, start = 20.dp, end = 20.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { navController.navigate(Routes.MainScreen) },
                        modifier = Modifier.size(30.dp),
                        content = {
                            Image(
                                modifier = Modifier.size(26.dp),
                                painter = painterResource(id = R.drawable.arrow),
                                contentDescription = null
                            )
                        }
                    )

                    Text(
                        text = "오늘 할 일",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.size(30.dp))
                }

                Spacer(modifier = Modifier.height(30.dp))

                Column {
                    // 오늘 날짜의 작업들 필터링 및 정렬
                    taskViewModel.tasks.filter { task ->
                        val taskDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(task.dateTime)!!
                        )
                        taskDate == todayDate
                    }.sortedBy { task ->
                        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(task.dateTime)
                    }.forEach { task ->
                        TaskCard(
                            taskTitle = task.title,
                            taskTime = task.dateTime.split(" ")[1],
                            taskDay = "오늘",
                            iconToggleViewModel = iconToggleViewModel,
                            onDoubleClick = {
                                selectedTask = task
                                showEditDialog = true
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                // 편집 다이얼로그 표시
                if (showEditDialog && selectedTask != null) {
                    EditTaskDialog(
                        task = selectedTask!!,
                        onDismiss = { showEditDialog = false },
                        onSave = { title, dateTime ->
                            taskViewModel.updateTask(selectedTask!!, title, dateTime)
                            showEditDialog = false
                        },
                        onDelete = {
                            taskViewModel.deleteTask(selectedTask!!)
                            showEditDialog = false
                        }
                    )
                }
            }
        }
    }
}
