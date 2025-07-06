package com.dgmltn.worldclock.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dgmltn.worldclock.domain.ALL_CITIES
import com.dgmltn.worldclock.domain.FAVORITE_CITIES
import com.dgmltn.worldclock.ui.theme.DayColorPalette
import com.dgmltn.worldclock.ui.theme.LocalColorPalette
import com.dgmltn.worldclock.ui.theme.NightColorPalette
import com.dgmltn.worldclock.ui.theme.WcPreview
import com.dgmltn.worldclock.util.currentLocalTime
import dev.jamesyox.kastro.common.HorizonState
import dev.jamesyox.kastro.sol.calculateSolarState
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI
import kotlin.time.Duration.Companion.milliseconds

private val CLOCK_DIAMETER = 72.dp

@Composable
fun AnalogClockMini(
    timeZone: TimeZone,
    isDay: Boolean,
    modifier: Modifier = Modifier
) {
    var time by remember { mutableStateOf(timeZone.currentLocalTime()) }

    // Update every second (TODO: maybe update every minute instead?)
    LaunchedEffect(timeZone) {
        while (true) {
            time = timeZone.currentLocalTime()
            val nextDelay = 1000 - (time.toMillisecondOfDay() % 1000)
            delay(nextDelay.milliseconds)
        }
    }

    AnalogClockMini(
        localTime = time,
        isDay = isDay,
        modifier = modifier
    )
}

@Composable
fun AnalogClockMini(
    localTime: LocalTime,
    isDay: Boolean,
    modifier: Modifier = Modifier,
) {
    AnalogClockMini(
        hour = localTime.hour,
        minute = localTime.minute,
        isDay = isDay,
        modifier = modifier,
    )
}

@Composable
fun AnalogClockMini(
    hour: Int,
    minute: Int,
    isDay: Boolean,
    modifier: Modifier = Modifier
) {

    CompositionLocalProvider(LocalColorPalette provides if (isDay) DayColorPalette else NightColorPalette) {
        val backgroundColor = LocalColorPalette.current.miniClockBackground
        val hourHandColor = LocalColorPalette.current.miniClockHourHand
        val minuteHandColor = LocalColorPalette.current.miniClockMinuteHand

        Box(
            modifier = modifier.size(CLOCK_DIAMETER)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2

                // Draw background
                drawCircle(
                    color = backgroundColor,
                    radius = radius,
                    center = center
                )

                // Angles in degrees -> radians (subtract 90 to rotate to 12 o'clock)
                val hourAngle = ((hour % 12) + minute / 60f) * 30f - 90f
                val minuteAngle = (minute % 60) * 6f - 90f

                // Hand properties
                val hourLength = radius * 0.5f
                val minuteLength = radius * 0.8f
                val tailLength = radius * 0.1f
                val strokeWidth = 6f
                val capStyle = StrokeCap.Round

                // Draw hour hand
                drawLine(
                    color = hourHandColor,
                    start = Offset(
                        x = center.x - cos(hourAngle.toRadians()) * tailLength,
                        y = center.y - sin(hourAngle.toRadians()) * tailLength
                    ),
                    end = Offset(
                        x = center.x + cos(hourAngle.toRadians()) * hourLength,
                        y = center.y + sin(hourAngle.toRadians()) * hourLength
                    ),
                    strokeWidth = strokeWidth,
                    cap = capStyle
                )

                // Draw minute hand
                drawLine(
                    color = minuteHandColor,
                    start = Offset(
                        x = center.x - cos(minuteAngle.toRadians()) * tailLength,
                        y = center.y - sin(minuteAngle.toRadians()) * tailLength
                    ),
                    end = Offset(
                        x = center.x + cos(minuteAngle.toRadians()) * minuteLength,
                        y = center.y + sin(minuteAngle.toRadians()) * minuteLength
                    ),
                    strokeWidth = strokeWidth,
                    cap = capStyle
                )

                // Draw center dot
                drawCircle(
                    color = minuteHandColor,
                    radius = 6f,
                    center = center
                )
            }
        }
    }
}

// Helper extension
private fun Float.toRadians(): Float = this * PI.toFloat() / 180f

@Preview(showBackground = true)
@Composable
fun Preview_AnalogClockMini() {
    WcPreview(darkTheme = false) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = spacedBy(16.dp)) {
            PreviewClockTimes.forEach {
                AnalogClockMini(
                    localTime = it,
                    isDay = it.hour in 6..17,
                )
            }
        }
    }
}

val PreviewClockTimes = listOf(
    LocalTime(3, 9),
    LocalTime(5, 31),
    LocalTime(10, 49),
    LocalTime(15, 3),
    LocalTime(21, 31),
    LocalTime(23, 40)
)