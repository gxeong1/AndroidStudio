package com.example.todolist.Sub

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun EditTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    onDelete: () -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var dateTime by remember { mutableStateOf(task.dateTime) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "수정 또는 삭제") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("할 일") }
                )
                OutlinedTextField(
                    value = dateTime,
                    onValueChange = { dateTime = it },
                    label = { Text("날짜 및 시간") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(title, dateTime) }) {
                Text("저장")
            }
        },
        dismissButton = {
            Button(onClick = onDelete) {
                Text("삭제")
            }
        }
    )
}
