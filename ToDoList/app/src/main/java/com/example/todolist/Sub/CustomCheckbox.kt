package com.example.todolist.Sub

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckbox(taskId: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val context = LocalContext.current
    var isChecked by remember { mutableStateOf(getCheckState(context, taskId)) }

    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(color = if (isChecked) Color(0xFF3CABFF) else Color.Transparent)
            .border(1.dp, Color.Gray, CircleShape)
            .clickable(enabled = !isChecked) {
                isChecked = true
                saveCheckState(context, taskId, true)
                Toast.makeText(context, "할 일을 끝냈습니다", Toast.LENGTH_SHORT).show()
                onCheckedChange(true)
            },
        contentAlignment = Alignment.Center
    ) {
        if (isChecked) {
            Canvas(modifier = Modifier.size(10.dp)) {
                drawCircle(color = Color(0xFFC6E6FF))
            }
        }
    }
}
