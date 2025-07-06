package com.dgmltn.worldclock.util

import androidx.compose.ui.graphics.Color

fun Color.toHexArgb(): String {
    val a = (alpha * 255).toInt()
    val r = (red * 255).toInt()
    val g = (green * 255).toInt()
    val b = (blue * 255).toInt()
    return String.format("%02X%02X%02X%02X", a, r, g, b)
}

fun Color.toHexRgb(): String {
    val r = (red * 255).toInt()
    val g = (green * 255).toInt()
    val b = (blue * 255).toInt()
    return String.format("%02X%02X%02X", r, g, b)
}