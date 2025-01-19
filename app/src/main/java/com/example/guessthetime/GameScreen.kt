package com.example.guessthetime
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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

@Composable
fun ClockCanvas(hours: Int, minutes: Int, seconds: Int) {
    val size = 350.dp
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
    val hours = remember { mutableStateOf(Random.nextInt(0, 25)) }
    val minutes = remember { mutableStateOf(Random.nextInt(0, 60)) }
    val seconds = remember { mutableStateOf(Random.nextInt(0, 60)) }

    Box(contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(top=100.dp)
        ) {
            Text(
                text = "Guess The Time",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .padding(bottom = 40.dp),
                style = TextStyle(lineHeight = 50.sp)
            )
            ClockCanvas(hours.value, minutes.value, seconds.value)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {

    GameScreen()
}
