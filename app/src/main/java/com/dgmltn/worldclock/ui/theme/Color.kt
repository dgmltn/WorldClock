package com.dgmltn.worldclock.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalColorPalette = compositionLocalOf<ColorPalette> { NightColorPalette }

val Red = Color(0xFFD81F72)
val Fab = Color(0xFFD92D7B)

// Night / Dark
val NightBackground = Color(0xFF142550)
val OnNightBackground = Color(0xFFECF1FD)


// Day / Light
val DayBackground = Color(0xFFF7F9FC)
val OnDayBackground = Color(0xFF060709)

// Mini clock
val MiniClockNightBackground = Color(0xFF22335D)
val MiniClockNightHourHand = Color(0xFFFFFFFF)
val MiniClockNightMinuteHand = Red
val MiniClockDayBackground = Color(0xFFE3ECF9)
val MiniClockDayHourHand = Color(0xFF0C0F1F)
val MiniClockDayMinuteHand = Red

interface ColorPalette {
    val background: Color
    val onBackground: Color
    val primaryText: Color
    val fab1: Color
    val onFab1: Color
    val fab2: Color
    val onFab2: Color

    // Mini clock
    val miniClockBackground: Color
    val miniClockHourHand: Color
    val miniClockMinuteHand: Color

    // Analog Clock
    val outerBackground: Color
    val innerBackground: Color
    val tickColor: Color
    val hourHandColor: Color
    val minuteHandColor: Color
    val innerFaceShadowColor: Color
    val secondHandColor: Color
    val centerDotColor: Color
    val outerFaceShadow1Color: Color
    val outerFaceShadow2Color: Color
    val outerFaceShadow3Color: Color
    val outerFaceShadow4Color: Color

    // Search screen
    val searchBoxContainer: Color
    val searchBoxText: Color

    val searchResultContainer: Color
    val searchResultText: Color
    val searchResultExpandIcon: Color
    val searchResultMap: Color

    val button: Color
    val buttonText: Color
}

object NightColorPalette : ColorPalette {
    override val background = NightBackground
    override val onBackground = OnNightBackground
    override val primaryText = Red
    override val fab1 = Fab
    override val onFab1 = Color.White
    override val fab2 = DayBackground
    override val onFab2 = Fab
    override val miniClockBackground = MiniClockNightBackground
    override val miniClockHourHand = MiniClockNightHourHand
    override val miniClockMinuteHand = MiniClockNightMinuteHand

    override val outerBackground = Color(0xFF142550)
    override val innerBackground = Color(0xFF22335D)
    override val tickColor = Color(0x38FFFFFF)
    override val hourHandColor = Color(0xFFFFFFFF)
    override val minuteHandColor = Color(0xFF9FA7BC)
    override val innerFaceShadowColor = Color(0x04417505)
    override val secondHandColor = Red
    override val centerDotColor = Red
    override val outerFaceShadow1Color = Color(0xFF101C3B)
    override val outerFaceShadow2Color = Color(0x641A3781)
    override val outerFaceShadow3Color = Color(0xFF101C3B)
    override val outerFaceShadow4Color = Color(0xFF1A3781)

    override val searchBoxContainer = Color.White
    override val searchBoxText = Color(0xFF000000)
    override val searchResultContainer = Color(0xFFE7EEFB)
    override val searchResultText = Color(0xFF000000)
    override val searchResultExpandIcon = Color(0xFFCFD4DB)
    override val searchResultMap = Color(0xFFCFD6E3)

    override val button = Red
    override val buttonText = Color.White
}

object DayColorPalette : ColorPalette {
    override val background = DayBackground
    override val onBackground = OnDayBackground
    override val primaryText = Red
    override val fab1 = Fab
    override val onFab1 = Color.White
    override val fab2 = DayBackground
    override val onFab2 = Fab
    override val miniClockBackground = MiniClockDayBackground
    override val miniClockHourHand = MiniClockDayHourHand
    override val miniClockMinuteHand = MiniClockDayMinuteHand

    override val outerBackground = Color(0xFFE7EEFB)
    override val innerBackground = Color(0xFFEDF1FB)
    override val tickColor = Color(0xFFFFFFFF)
    override val hourHandColor = Color(0xFF0C0F1F)
    override val minuteHandColor = Color(0xFF9FA7BC)
    override val innerFaceShadowColor = Color(0x59C4D4E7)
    override val secondHandColor = Red
    override val centerDotColor = Red
    override val outerFaceShadow1Color = Color(0x69FFFFFF)
    override val outerFaceShadow2Color = Color(0x8DC7D6EA)
    override val outerFaceShadow3Color = Color(0xFFFFFFFF)
    override val outerFaceShadow4Color = Color(0xFFC4D4E7)

    override val searchBoxContainer = Color(0xFFFFFFFF)
    override val searchBoxText = Color(0xFF000000)
    override val searchResultContainer = Color(0xFFE7EEFB)
    override val searchResultText = Color(0xFF000000)
    override val searchResultExpandIcon = Color(0xFFCFD4DB)
    override val searchResultMap = Color(0xFFCFD6E3)

    override val button = Red
    override val buttonText = Color.White
}