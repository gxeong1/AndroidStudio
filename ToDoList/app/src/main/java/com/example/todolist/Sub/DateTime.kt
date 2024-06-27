package com.example.todolist.Sub

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerField(taskDateTime: MutableState<String>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val timePickerDialog = TimePickerDialog(
                context,
                { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                    taskDateTime.value = "$selectedYear-${selectedMonth + 1}-$selectedDay $selectedHour:$selectedMinute"
                }, hour, minute, true
            )
            timePickerDialog.show()
        }, year, month, day
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .height(60.dp)
            .clickable { datePickerDialog.show() } // 클릭 이벤트 추가
    ) {
        OutlinedTextField(
            value = taskDateTime.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxSize(), // 전체 크기로 설정
            label = {
                Text(
                    text = "날짜를 입력해주세요...",
                    color = Color.Gray,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            readOnly = true, // 텍스트 필드가 직접 편집되지 않도록 설정
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White, // 배경색을 투명하게 설정
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            ),
            shape = RoundedCornerShape(10.dp) // 모서리 둥글게
        )
    }
}
