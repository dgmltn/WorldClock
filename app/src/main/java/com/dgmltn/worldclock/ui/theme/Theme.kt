package com.dgmltn.worldclock.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier

private val DarkM3ColorScheme = darkColorScheme(
    primary = Red,
    secondary = Red,
    tertiary = Red,
    background = NightBackground,
    onBackground = OnNightBackground,
)

private val LightM3ColorScheme = lightColorScheme(
    primary = Red,
    secondary = Red,
    tertiary = Red,
    background = DayBackground,
    onBackground = OnDayBackground,
)

@Composable
fun WorldClockTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorSchemeM3 = when {
        darkTheme -> DarkM3ColorScheme
        else -> LightM3ColorScheme
    }
    val colorPalette = when {
        darkTheme -> NightColorPalette
        else -> DayColorPalette
    }

    CompositionLocalProvider(LocalColorPalette provides colorPalette) {
        MaterialTheme(
            colorScheme = colorSchemeM3,
            typography = Typography,
            content = content
        )
    }
}

@Composable
fun WcPreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    WorldClockTheme(darkTheme = darkTheme) {
        Box (modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            content()
        }
    }
}