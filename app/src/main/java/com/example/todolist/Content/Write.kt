package com.example.todolist.Content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.R
import com.example.todolist.Sub.DateTimePickerField
import com.example.todolist.Sub.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Write(navController: NavController, taskViewModel: TaskViewModel, from: String) {
    val taskTitle = remember { mutableStateOf(TextFieldValue()) }
    val taskDateTime = remember { mutableStateOf("") }

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
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(26.dp),
                            painter = painterResource(id = R.drawable.x),
                            contentDescription = null
                        )
                    }

                    Text(
                        text = "할 일 추가하기",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = {
                            if (from == "Home") {
                                taskViewModel.addTask(taskTitle.value.text, taskDateTime.value, "Home")
                                taskViewModel.addTask(taskTitle.value.text, taskDateTime.value, "MyTask")
                            } else {
                                taskViewModel.addTask(taskTitle.value.text, taskDateTime.value, from)
                            }
                            navController.popBackStack()
                        },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(26.dp),
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.size(20.dp))

                OutlinedTextField(
                    value = taskTitle.value,
                    onValueChange = { taskTitle.value = it },
                    label = {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "할 일 추가...",
                                color = Color.Gray,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(60.dp),
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                DateTimePickerField(taskDateTime)
            }
        }
    }
}
