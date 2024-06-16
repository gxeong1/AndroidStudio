package com.example.todolist.Main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.R

@Composable
fun Main_Header(){
    val notoFontFamily = FontFamily(
        Font(R.font.noto)
    )
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "TODOLIST.",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            fontFamily = notoFontFamily,
            style = TextStyle(
                letterSpacing = (-1.0).sp
            )
        )
        
        Row (modifier = Modifier
            .padding(end = 15.dp)){
            IconButton(
                onClick = { /* Your click action here */ },
                modifier = Modifier.size(30.dp),
                content = {
                    Image(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(id = R.drawable.noti),
                        contentDescription = null
                    )
                }  )
            
            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = { /* Your click action here */ },
                modifier = Modifier.size(30.dp),
                content = {
                    Image(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(id = R.drawable.more),
                        contentDescription = null
                    )
                }  )
        }

    }
}