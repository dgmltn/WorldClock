package com.dgmltn.worldclock.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import com.dgmltn.worldclock.BuildConfig
import com.dgmltn.worldclock.domain.ALL_CITIES

// https://docs.mapbox.com/api/maps/static-images/
// https://console.mapbox.com/studio/styles/jesterwrestler/cmcu29qe2006001r47g1k3n4d/edit/#11/40.73/-74

// https://docs.mapbox.com/api/maps/static-images/#marker
fun createMarkerOverlay(
    lat: Double,
    lon: Double,
    color: Color = Color(0xFFD81F72),
): String {
    val name = "pin-l" // "pin-s" or "pin-l"
    val colorRgb = color.toHexRgb()
    return "${name}+${colorRgb}(${lon},${lat})"
}

fun createMapboxUrl(
    username: String = "jesterwrestler",
    styleId: String = "cmcu29qe2006001r47g1k3n4d",
    lat: Double = ALL_CITIES[7].latitude,
    lon: Double = ALL_CITIES[7].longitude,
    zoom: Int = 2,
    size: IntSize = IntSize(480, 240),
    accessToken: String = BuildConfig.MAPBOX_ACCESS_TOKEN,
    attribution: Boolean = false,
    logo: Boolean = false,
    overlays: List<String> = emptyList(),
): String {
    if (size.width == 0 || size.height == 0) { return "" }
    val overlaysString = overlays.joinToString(",")
    val overlayPathItem = if (overlaysString.isNotEmpty()) "${overlaysString}/" else ""
    return "https://api.mapbox.com/styles/v1/${username}/${styleId}/static/${overlayPathItem}${lon},${lat},${zoom}/${size.width/2}x${size.height/2}@2x?access_token=${accessToken}&attribution=${attribution}&logo=${logo}"
}
