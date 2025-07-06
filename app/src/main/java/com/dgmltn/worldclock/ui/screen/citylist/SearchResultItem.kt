package com.dgmltn.worldclock.ui.screen.citylist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dgmltn.worldclock.R
import com.dgmltn.worldclock.domain.City
import com.dgmltn.worldclock.ui.component.AnalogClockMini
import com.dgmltn.worldclock.ui.theme.LocalColorPalette
import com.dgmltn.worldclock.ui.theme.WcPreview
import com.dgmltn.worldclock.util.createMapboxUrl
import com.dgmltn.worldclock.util.createMarkerOverlay
import com.dgmltn.worldclock.util.isDay


@Composable
fun SearchResultItem(
    city: City,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(
                color = LocalColorPalette.current.searchResultContainer,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        // Result row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clickable { isExpanded = !isExpanded }
        ) {
            AnalogClockMini(
                timeZone = city.timeZone,
                isDay = city.isDay,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(48.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                CompositionLocalProvider(LocalContentColor provides LocalColorPalette.current.searchResultText) {
                    Text(city.name)
                    if (city.detail.isNotEmpty()) Text(city.detail)
                    else Text(city.country)
                    Text(city.timeZone.id)
                }
            }
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    painter = painterResource(if (isExpanded) R.drawable.ic_minus_circle else R.drawable.ic_plus_circle),
                    contentDescription = null,
                    tint = LocalColorPalette.current.searchResultExpandIcon
                )
            }
        }
        AnimatedVisibility(isExpanded) {
            var imageSize by remember { mutableStateOf(IntSize(0, 0)) }
            Column {
                // Map
                AsyncImage(
                    model = createMapboxUrl(
                        lat = city.latitude,
                        lon = city.longitude,
                        size = imageSize,
                        overlays = listOf(createMarkerOverlay(lat = city.latitude, lon = city.longitude)),
                    ),
                    contentDescription = "Map of ${city.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .aspectRatio(2f)
                        .onSizeChanged { imageSize = it }
                        .clip(RoundedCornerShape(16.dp))
                )
                // Button to add to clock
                Button(
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalColorPalette.current.button,
                        contentColor = LocalColorPalette.current.buttonText,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.Add_Clock).uppercase(),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = Bold),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview_SearchResultItem() {
    WcPreview {
        SearchResultItem(
            city = City(
                name = "New York",
                country = "USA",
                timeZone = kotlinx.datetime.TimeZone.of("America/New_York"),
                detail = "New York County, NY, USA",
                latitude = 40.7128,
                longitude = -74.0060
            ),
            onClick = {}
        )
    }
}