package com.dgmltn.worldclock.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.dgmltn.worldclock.util.currentLocalTime
import com.dgmltn.worldclock.util.formatWithSystemDefault
import kotlinx.coroutines.delay
import kotlinx.datetime.TimeZone

@Composable
fun DigitalClock(
    timeZone: TimeZone,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    var time by remember { mutableStateOf(timeZone.currentLocalTime()) }

    // Update every second
    LaunchedEffect(timeZone) {
        while (true) {
            time = timeZone.currentLocalTime()
            delay(1000)
        }
    }

    Text(
        text = time.formatWithSystemDefault(),
        style = style,
        modifier = modifier
    )
}
