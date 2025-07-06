package com.dgmltn.worldclock.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dgmltn.worldclock.R

val Aileron = FontFamily(
    Font(R.font.aileron_light, FontWeight.Light),
    Font(R.font.aileron_regular, FontWeight.Normal),
    Font(R.font.aileron_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.aileron_bold, FontWeight.Bold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Aileron,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Aileron,
        fontWeight = FontWeight.Bold,
        fontSize = 33.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = Aileron,
        fontSize = 52.sp,
        fontWeight = FontWeight.Normal,
    ),
    labelLarge = TextStyle(
        fontFamily = Aileron,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
    ),
    // Other default text styles to override
)
