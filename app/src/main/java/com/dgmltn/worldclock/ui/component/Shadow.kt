package com.dgmltn.worldclock.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dgmltn.worldclock.ui.theme.WcPreview
import kotlin.math.sqrt


// blurRadius is in terms of radius ... blurRadius = 0.5f -> radius * 0.5f
// Same for offset
fun DrawScope.drawCircleShadow(
    center: Offset,
    radius: Float,
    shadowColor: Color = Color.Red,
    blurRadius: Float = 0.1f,
    offset: Offset = Offset(0.1f, 0.1f),
) {
    // Fudge factors I used to make the shadow look better subjectively on the phone
    // Original = 1.0f
    val prettyFactor = 1.2f
    val prettyAlphaFactor = 0.6f

    val shadowCenter = center + offset * radius
    val shadowRadius = radius + blurRadius * radius * prettyFactor
    val innerCircleRadius = radius - offset.magnitude() * radius
    val stop = innerCircleRadius / shadowRadius / prettyFactor

    val brush = Brush.radialGradient(
        0.0f to Color.Transparent,
        stop - 0.0000001f to Color.Transparent,
        stop to shadowColor.copy(alpha = shadowColor.alpha * prettyAlphaFactor),
        1.0f to Color.Transparent,
        center = shadowCenter,
        radius = shadowRadius,
    )
    
    drawCircle(
        brush = brush,
        radius = shadowRadius,
        center = shadowCenter
    )
}

private fun Offset.magnitude() = sqrt(x * x + y * y)

@Preview(showBackground = true)
@Composable
fun BoxShadowPreview() {
    WcPreview {
        Column(verticalArrangement = spacedBy(24.dp)) {
            Canvas(modifier = Modifier.size(100.dp)) {
                drawCircleShadow(
                    center = center,
                    radius = size.minDimension / 2f * .9f,
                    shadowColor = Color.Green,
                    blurRadius = 0.2f,
                    offset = Offset(0.1f, 0.1f),
                )
                drawCircle(
                    color = Color.Black,
                    radius = size.minDimension / 2f * .9f,
                    center = center,
                    style = Stroke(1f)
                )
            }
            Canvas(modifier = Modifier.size(100.dp)) {
                drawCircleShadow(
                    center = center,
                    radius = size.minDimension / 2f * .9f,
                    shadowColor = Color.Red,
                    blurRadius = 0.2f,
                    offset = Offset(0f, 0.1f),
                )
                drawCircle(
                    color = Color.Black,
                    radius = size.minDimension / 2f * .9f,
                    center = center,
                    style = Stroke(1f)
                )
            }

            Canvas(modifier = Modifier.size(100.dp)) {
                drawCircleShadow(
                    center = center,
                    radius = size.minDimension / 2f * .9f,
                    shadowColor = Color.Cyan.copy(alpha = 0.5f),
                    blurRadius = 0.2f,
                    offset = Offset(0.2f, 0f),
                )
                drawCircle(
                    color = Color.Black,
                    radius = size.minDimension / 2f * .9f,
                    center = center,
                    style = Stroke(1f)
                )
            }

        }
    }
}
