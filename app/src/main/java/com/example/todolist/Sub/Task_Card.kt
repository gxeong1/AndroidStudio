package com.example.todolist.Sub

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.todolist.R

fun saveStarState(context: Context, key: String, value: Boolean) {
    val sharedPreferences = context.getSharedPreferences("star_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean(key, value).apply()
}

fun getStarState(context: Context, key: String): Boolean {
    val sharedPreferences = context.getSharedPreferences("star_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, false)
}

fun saveCheckState(context: Context, key: String, value: Boolean) {
    val sharedPreferences = context.getSharedPreferences("check_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean(key, value).apply()
}

fun getCheckState(context: Context, key: String): Boolean {
    val sharedPreferences = context.getSharedPreferences("check_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, false)}class IconToggleViewModel : ViewModel() {
    val starState = mutableStateMapOf<String, Boolean>()

    fun isStarred(context: Context, taskTitle: String): Boolean {
        return starState[taskTitle] ?: getStarState(context, taskTitle)
    }

    fun toggleStar(context: Context, taskTitle: String) {
        val currentState = starState[taskTitle] ?: getStarState(context, taskTitle)
        val newState = !currentState
        starState[taskTitle] = newState
        saveStarState(context, taskTitle, newState)
    }
}

@Composable
fun TaskCard(
    taskTitle: String,
    taskTime: String,
    taskDay: String,
    iconToggleViewModel: IconToggleViewModel,
    onDoubleClick: () -> Unit
) {
    val context = LocalContext.current
    val isStarred by remember { derivedStateOf { iconToggleViewModel.isStarred(context, taskTitle) } }

    Row(
        modifier = Modifier
            .width(355.dp)
            .height(72.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { onDoubleClick() }
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomCheckbox(
            taskId = taskTitle,
            checked = false,
            onCheckedChange = {}
        )

        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(
                text = taskTitle,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )

            Text(
                text = "$taskDay, $taskTime 까지",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { iconToggleViewModel.toggleStar(context, taskTitle) },
            modifier = Modifier.size(30.dp)
        ) {
            if (isStarred) {
                Image(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = R.drawable.fullstar),
                    contentDescription = null
                )
            } else {
                Image(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = R.drawable.emptystar),
                    contentDescription = null
                )
            }
        }
    }
}