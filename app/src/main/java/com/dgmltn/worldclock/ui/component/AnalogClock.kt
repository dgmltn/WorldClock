package com.dgmltn.worldclock.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.dgmltn.worldclock.ui.theme.LocalColorPalette
import com.dgmltn.worldclock.ui.theme.WcPreview
import com.dgmltn.worldclock.util.currentLocalTime
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AnalogClock(
    timeZone: TimeZone,
    modifier: Modifier = Modifier
) {
    var time by remember { mutableStateOf(timeZone.currentLocalTime()) }

    // Update every second
    LaunchedEffect(timeZone) {
        while (true) {
            time = timeZone.currentLocalTime()
            val nextDelay = 1000 - (time.toMillisecondOfDay() % 1000)
            delay(nextDelay.milliseconds)
        }
    }

    AnalogClock(
        localTime = time,
        modifier = modifier
    )
}

@Composable
fun AnalogClock(
    localTime: LocalTime,
    modifier: Modifier = Modifier
) {
    AnalogClock(
        hour = localTime.hour,
        minute = localTime.minute,
        second = localTime.second,
        modifier = modifier
    )
}

@Composable
fun AnalogClock(
    hour: Int,
    minute: Int,
    second: Int,
    modifier: Modifier = Modifier,
) {
    val outerBackground = LocalColorPalette.current.outerBackground
    val innerBackground = LocalColorPalette.current.innerBackground
    val tickColor = LocalColorPalette.current.tickColor
    val hourHandColor = LocalColorPalette.current.hourHandColor
    val minuteHandColor = LocalColorPalette.current.minuteHandColor
    val innerFaceShadowColor = LocalColorPalette.current.innerFaceShadowColor
    val secondHandColor = LocalColorPalette.current.secondHandColor
    val centerDotColor = LocalColorPalette.current.centerDotColor
    val outerFaceShadow1Color = LocalColorPalette.current.outerFaceShadow1Color
    val outerFaceShadow2Color = LocalColorPalette.current.outerFaceShadow2Color
    val outerFaceShadow3Color = LocalColorPalette.current.outerFaceShadow3Color
    val outerFaceShadow4Color = LocalColorPalette.current.outerFaceShadow4Color

    // Calculate angles for each hand (0 degrees = 12 o'clock)
    val secondAngle = second * 6f // 6 degrees per second
    val minuteAngle = minute * 6f + secondAngle / 60f // 6 degrees per minute + second adjustment
    val hourAngle = hour * 30f + minuteAngle / 12f // 30 degrees per hour + minute adjustment

    Canvas(
        modifier = modifier
            .size(300.dp)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2

        // Hand lengths
        val hourHandLength = radius * 0.35f
        val minuteHandLength = radius * 0.6f
        val secondHandLength = radius * 0.80f
        val innerFaceRadius = radius * 0.76f

        // Extension past center for polished look
        val handExtension = 40f


        // Clock outer face shadows
        drawCircleShadow(
            center = center,
            radius = radius,
            shadowColor = outerFaceShadow4Color,
            offset = Offset(0.05934718f, 0f),
            blurRadius = 0.2611276f,
        )
        drawCircleShadow(
            center = center,
            radius = radius,
            shadowColor = outerFaceShadow3Color,
            offset = Offset(-0.0652819f, 0f),
            blurRadius = 0.2611276f,
        )
        drawCircleShadow(
            center = center,
            radius = radius,
            shadowColor = outerFaceShadow2Color,
            offset = Offset(0.29673591f, 0f),
            blurRadius = 0.32047478f,
        )
        drawCircleShadow(
            center = center,
            radius = radius,
            shadowColor = outerFaceShadow1Color,
            offset = Offset(-0.22551929f, 0f),
            blurRadius = 0.32047478f,
        )

        // clock outer face
        drawCircle(
            brush = SolidColor(outerBackground),
            radius = radius,
            center = center
        )

        // Clock inner face shadow
        drawCircleShadow(
            center = center,
            radius = innerFaceRadius,
            shadowColor = innerFaceShadowColor,
            offset = Offset(0.1f, 0f),
            blurRadius = 0.1f,
        )

        // Clock inner face
        drawCircle(
            brush = SolidColor(innerBackground),
            radius = innerFaceRadius,
            center = center
        )

        // Draw tick marks
        val numberOfTicks = 30
        for (i in 0 until numberOfTicks) {
            val angle = i * 360f / numberOfTicks - 90f // Start from 12 o'clock
            val tickLength = radius * 0.03f
            val tickWidth = radius * 0.025f

            val startRadius = (radius + innerFaceRadius) / 2f - tickLength / 2f
            val endRadius = (radius + innerFaceRadius) / 2f + tickLength / 2f

            val startX = center.x + cos(Math.toRadians(angle.toDouble())).toFloat() * startRadius
            val startY = center.y + sin(Math.toRadians(angle.toDouble())).toFloat() * startRadius
            val endX = center.x + cos(Math.toRadians(angle.toDouble())).toFloat() * endRadius
            val endY = center.y + sin(Math.toRadians(angle.toDouble())).toFloat() * endRadius

            drawLine(
                color = tickColor,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = tickWidth,
                cap = StrokeCap.Square
            )
        }

        // Helper function to draw hands
        fun drawHand(angle: Float, length: Float, color: Color, strokeWidth: Float) {
            val radian = Math.toRadians((angle - 90).toDouble())
            val startX = center.x - cos(radian).toFloat() * handExtension
            val startY = center.y - sin(radian).toFloat() * handExtension
            val endX = center.x + cos(radian).toFloat() * length
            val endY = center.y + sin(radian).toFloat() * length

            drawLine(
                color = color,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }

        val secondHandWidth = radius * 0.025f
        val minuteHandWidth = radius * 0.025f
        val hourHandWidth = radius * 0.035f

        // Draw hour hand
        drawHand(hourAngle, hourHandLength, hourHandColor, hourHandWidth)

        // Draw minute hand
        drawHand(minuteAngle, minuteHandLength, minuteHandColor, minuteHandWidth)

        // Draw second hand
        drawHand(secondAngle, secondHandLength, secondHandColor, secondHandWidth)

        // Draw center dot
        drawCircle(
            color = centerDotColor,
            radius = 12f,
            center = center
        )
    }
}

@PreviewLightDark
@Composable
fun AnalogClockPreview() {
    WcPreview {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = spacedBy(48.dp)
        ) {
            PreviewClockTimes.subList(0, 2).forEach {
                AnalogClock(it)
            }
        }
    }
}