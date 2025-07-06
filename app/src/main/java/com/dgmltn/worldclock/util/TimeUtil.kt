package com.dgmltn.worldclock.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.dgmltn.worldclock.R
import com.dgmltn.worldclock.domain.City
import dev.jamesyox.kastro.common.HorizonState
import dev.jamesyox.kastro.sol.calculateSolarState
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toDeprecatedInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
fun TimeZone.currentLocalTime(): LocalTime {
    return Clock.System.now()
        .toLocalDateTime()
        .time
}

/**
 * Formats [LocalTime] as localized time. For example, "8:00 AM" or "13:45"
 */
@Composable
fun LocalTime.formatWithSystemDefault(): String {
    val context = LocalContext.current
    val is24HourFormat = android.text.format.DateFormat.is24HourFormat(context)
    return if (is24HourFormat) {
        formatHourMinute24Hour()
    } else {
        val am = stringResource(R.string.AM)
        val pm = stringResource(R.string.PM)
        formatHourMinuteAmPm(am, pm)
    }
}

private fun LocalTime.formatHourMinuteAmPm(am: String, pm: String): String {
    val customFormat = LocalTime.Format {
        amPmHour(); char(':'); minute(); char(' '); amPmMarker(am, pm)
    }
    return format(customFormat)
}

private fun LocalTime.formatHourMinute24Hour(): String {
    val customFormat = LocalTime.Format {
        hour(); char(':'); minute()
    }
    return format(customFormat)
}

/**
 * Compares the date and timezone to determine if the date in the timezone is today. It could be
 * yesterday or tomorrow depending on the timezone.
 */
@OptIn(ExperimentalTime::class)
fun getTodayLabel(
    otherTimeZone: TimeZone,
    thisTimeZone: TimeZone = TimeZone.currentSystemDefault(),
    atTime: Instant = Clock.System.now(),
): Int {
    val dayOfTodayInThisTimeZone = atTime.toLocalDateTime(thisTimeZone).date
    val dayOfTodayInOtherTimeZone = atTime.toLocalDateTime(otherTimeZone).date
    return when (dayOfTodayInThisTimeZone.compareTo(dayOfTodayInOtherTimeZone)) {
        1 -> return R.string.Yesterday
        -1 -> return R.string.Tomorrow
        else -> return R.string.Today
    }
}

@OptIn(ExperimentalTime::class)
val City.isDay
    get() = Clock.System.now().toDeprecatedInstant().calculateSolarState(
        latitude = latitude,
        longitude = longitude,
    ).horizonState == HorizonState.Up

