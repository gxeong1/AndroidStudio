package com.example.todolist.Main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.Routes
import com.example.todolist.Sub.CustomCheckbox
import com.example.todolist.Sub.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Main_Today(navController: NavHostController) {
    val taskViewModel: TaskViewModel = viewModel()
    val todayDate = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "오늘 할 일",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "전체보기 >",
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .clickable {
                        navController.navigate(Routes.TodayTasks)
                    }
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            taskViewModel.tasks.filter { task ->
                val taskDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(task.dateTime)
                )
                taskDate == todayDate
            }.sortedBy { task ->
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(task.dateTime)
            }.take(2).forEach { task ->
                Today_Task(
                    taskTitle = task.title,
                    taskTime = task.dateTime.split(" ")[1],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun Today_Task(taskTitle: String, taskTime: String, modifier: Modifier = Modifier) {
    var isChecked by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .width(170.dp)
            .height(130.dp)
            .padding(end = 2.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = taskTitle,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "오늘, $taskTime 까지",
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                CustomCheckbox(
                    taskId = taskTitle,
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
            }
        }
    }
}

