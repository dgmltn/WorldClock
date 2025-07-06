package com.dgmltn.worldclock.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dgmltn.worldclock.ui.theme.WcPreview
import kotlin.math.absoluteValue

@Composable
fun WormPagerIndicator(
    count: Int,
    currentPage: Int,
    pageOffset: Float,
    modifier: Modifier = Modifier,
    radius: Dp = 6.dp,
    selectedScale: Float = 1.2f,
    spacing: Dp = 12.dp,
    color: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
    selectedColor: Color = MaterialTheme.colorScheme.primary,
) {
    require(selectedScale >= 1f)

    val widgetWidth: Dp = (radius * 2f * count) + (spacing * (count - 1)) + (radius * (selectedScale - 1f) * 2)
    val widgetHeight: Dp = radius * 2f * selectedScale

    Canvas(
        modifier = modifier
            .width(widgetWidth)
            .height(widgetHeight)
    ) {
        val radiusPx = radius.toPx()
        val spacingPx = spacing.toPx()
        val selectedRadiusPx = radiusPx * selectedScale

        // Debug: draw the full canvas
        //drawRect(Color.Red, topLeft = Offset.Zero, size = size, style = Fill)

        // Draw dots
        val centerOf = { index: Int ->
            val centerx = selectedRadiusPx + index * (radiusPx * 2 + spacingPx)
            val centery = this.size.height / 2f
            Offset(centerx, centery)
        }
        repeat(count) { iteration ->
            drawCircle(
                color = if (currentPage == iteration) selectedColor else color,
                radius = radiusPx * if (currentPage == iteration) selectedScale else 1f,
                center = centerOf(iteration)
            )
        }

        // Draw worm
        if (pageOffset != 0f) {
            val center = centerOf(currentPage)
            val wormLength = (2 * pageOffset.absoluteValue) * (spacingPx + 2 * radiusPx)
            val wormx: Pair<Float, Float> = if (pageOffset >= 0) {
                center.x.let { it to it + wormLength }
            } else {
                center.x.let { it - wormLength to it }
            }

            val r = radiusPx * selectedScale
            val rect = Rect(Offset(wormx.first, center.y), Offset(wormx.second, center.y))
//                .also {
//                    // Test the worm by drawing just a line
//                    drawLine(Color.Green, it.topLeft, it.bottomRight, 10f)
//                }
                .inflate(r)

            drawRoundRect(
                color = selectedColor,
                topLeft = rect.topLeft,
                size = rect.size,
                cornerRadius = CornerRadius(r)
            )
        }

    }
}

@Preview
@Composable
fun Preview_WaterDropPagerIndicator() {
    val pagerState = rememberPagerState(pageCount = { 5 })

    WcPreview {
        Column {
            WormPagerIndicator(
                count = pagerState.pageCount,
                currentPage = pagerState.currentPage,
                pageOffset = pagerState.currentPageOffsetFraction,
                modifier = Modifier.align(CenterHorizontally).height(24.dp)
            )
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top,
                pageSpacing = 8.dp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .animateContentSize()
            ) { page ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(100.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Page $page")
                }
            }

        }
    }
}