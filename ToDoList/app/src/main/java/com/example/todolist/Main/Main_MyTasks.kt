package com.example.todolist.Main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolist.R
import com.example.todolist.Routes
import com.example.todolist.Sub.IconToggleViewModel
import com.example.todolist.Sub.TaskViewModel

@Composable
fun Main_My(navController: NavController) {
    val taskViewModel: TaskViewModel = viewModel()
    val iconToggleViewModel: IconToggleViewModel = viewModel()
    val context = LocalContext.current

    val importantTasksCount by remember {
        derivedStateOf {
            taskViewModel.tasks.count { task ->
                iconToggleViewModel.isStarred(context, task.title)
            }
        }
    }

    val homeTasksCount by remember {
        derivedStateOf {
            taskViewModel.tasks.count { task ->
                task.category == "Home"
            }
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "내 할 일",
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
                    .clickable { navController.navigate(Routes.MyTask) }
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        TaskCategory(
            iconRes = R.drawable.star,
            categoryName = "중요한 일",
            taskCount = importantTasksCount.toString(),
            modifier = Modifier.clickable { navController.navigate(Routes.ImportantTasks) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TaskCategory(
            iconRes = R.drawable.home,
            categoryName = "집에서 할 일",
            taskCount = homeTasksCount.toString(),
            modifier = Modifier.clickable { navController.navigate(Routes.HomeTasks) }
        )
    }
}

@Composable
fun TaskCategory(iconRes: Int, categoryName: String, taskCount: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .width(355.dp)
            .height(72.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = categoryName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "$taskCount 개 남음 >",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = modifier
            )
        }
    }
}
