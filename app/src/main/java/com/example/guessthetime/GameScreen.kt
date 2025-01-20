package com.example.guessthetime
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.xr.runtime.math.toRadians
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ClockCanvas(hours: Int, minutes: Int, seconds: Int) {
    val size = 260.dp
    val secondHandColor: Color = MaterialTheme.colorScheme.primary

    Canvas(modifier = Modifier.size(size)) {
        val centerX = size.toPx() / 2f
        val centerY = size.toPx() / 2f
        val radius = size.toPx() / 2f

        drawCircle(
            color = Color.Black,
            radius = radius,
            center = androidx.compose.ui.geometry.Offset(centerX, centerY),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8f)
        )
        drawHand(centerX, centerY, (hours % 12) * 30 + minutes / 2f, radius * 0.5f, 16f) // Hour hand
        drawHand(centerX, centerY, minutes * 6 + seconds / 10f, radius * 0.7f, 8f)   // Minute hand
        drawHand(centerX, centerY, seconds * 6f, radius * 0.9f, 4f, secondHandColor)       // Second hand
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawHand(
    centerX: Float,
    centerY: Float,
    angle: Float,
    length: Float,
    strokeWidth: Float,
    color: Color = Color.Black
) {
    val radian = toRadians(angle - 90)
    val endX = centerX + length * cos(radian)
    val endY = centerY + length * sin(radian)
    drawLine(
        start = androidx.compose.ui.geometry.Offset(centerX, centerY),
        end = androidx.compose.ui.geometry.Offset(endX, endY),
        color = color,
        strokeWidth = strokeWidth
    )
}


@Composable
fun GameScreen() {
    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    val hours = viewModel.hours.collectAsState()
    val minutes = viewModel.minutes.collectAsState()
    val seconds = viewModel.seconds.collectAsState()
    val hour = remember { mutableStateOf("") }
    val minute = remember { mutableStateOf("") }
    val second = remember { mutableStateOf("") }
    var answer = remember { mutableStateOf("")}

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier=Modifier.fillMaxHeight()) {

        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .height(435.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                //verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(top = 60.dp)
            ) {
                Text(
                    text = "Guess The Time",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .padding(bottom = 20.dp),
                    style = TextStyle(lineHeight = 50.sp)
                )
                ClockCanvas(hours.value, minutes.value, seconds.value)
            }
        }
        OutlinedTextField(
            value = hour.value,
            onValueChange = { newHour -> hour.value = newHour },
            label = { Text("Godzina", fontSize = 25.sp) },
            modifier = Modifier
                .width(400.dp)
                .height(60.dp)
                .padding(start = 20.dp, end = 20.dp),
            textStyle = TextStyle(fontSize = 29.sp)
        )
        OutlinedTextField(
            value = minute.value,
            onValueChange = { newMinute -> minute.value = newMinute },
            label = { Text("Minuta", fontSize = 25.sp) },
            modifier = Modifier
                .width(400.dp)
                .height(60.dp)
                .padding(start = 20.dp, end = 20.dp),
            textStyle = TextStyle(fontSize = 29.sp)
        )
        OutlinedTextField(
            value = second.value,
            onValueChange = { newSecond -> second.value = newSecond },
            label = { Text("Sekunda", fontSize = 25.sp) },
            modifier = Modifier
                .width(400.dp)
                .height(60.dp)
                .padding(start = 20.dp, end = 20.dp),
            textStyle = TextStyle(fontSize = 29.sp)
        )
        Button(
            onClick = {
                answer.value = String.format("%02d:%02d:%02d", hours.value, minutes.value, seconds.value)
                viewModel.updateTime()
            },
            modifier = Modifier
                .width(400.dp)
                .height(70.dp)
                .padding(start = 20.dp, end = 20.dp, top=15.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Zatwierdź", fontSize = 27.sp)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Text(text = buildAnnotatedString {
                append("Odpowiedź: ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                    append(answer.value ?: "")
                }
            }, fontSize = 27.sp, modifier = Modifier.padding(top=20.dp, start=25.dp))

            Text("Dokładność: ", fontSize = 27.sp, modifier = Modifier.padding(start=25.dp))
            Text("Punkty: ", fontSize = 27.sp, modifier = Modifier.padding(start=25.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen()
}
