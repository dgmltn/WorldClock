package com.dgmltn.worldclock.ui.screen.citylist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dgmltn.worldclock.R
import com.dgmltn.worldclock.domain.City
import com.dgmltn.worldclock.domain.FAVORITE_CITIES
import com.dgmltn.worldclock.ui.component.AnalogClockMini
import com.dgmltn.worldclock.ui.component.DigitalClock
import com.dgmltn.worldclock.ui.theme.Aileron
import com.dgmltn.worldclock.ui.theme.LocalColorPalette
import com.dgmltn.worldclock.ui.theme.WcPreview
import com.dgmltn.worldclock.util.getTodayLabel
import com.dgmltn.worldclock.util.isDay
import kotlin.time.ExperimentalTime

@Composable
fun DismissableCityListItem(
    city: City,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    isDismissEnabled: Boolean = true,
) {
    if (isDismissEnabled) {
        DismissableCityListItem(
            city = city,
            onDismiss = onDismiss,
            modifier = modifier
        )
    } else {
        CityListItem(
            city = city,
            modifier = modifier
        )
    }
}

@Composable
fun DismissableCityListItem(
    city: City,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val positionThreshold = with(LocalDensity.current) { 96.dp.toPx() }
    var composableWidth by remember { mutableIntStateOf(0) }
    var hasReachedThreshold by remember { mutableStateOf(false) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it != SwipeToDismissBoxValue.Settled && hasReachedThreshold) {
                onDismiss()
                true
            } else {
                false
            }
        },
        positionalThreshold = { positionThreshold }
    )

    LaunchedEffect(dismissState.progress) {
        val distance = dismissState.progress * composableWidth
        val next = distance > positionThreshold && composableWidth != 0  && dismissState.targetValue != SwipeToDismissBoxValue.Settled
        if (next != hasReachedThreshold) {
            hasReachedThreshold = next
            if (next) {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
            }
        }
    }


    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier
            .onSizeChanged { composableWidth = it.width },
        backgroundContent = {
            val resId = if (hasReachedThreshold) R.drawable.ic_trash_outline_open else R.drawable.ic_trash_outline
            if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                ) {
                    Icon(
                        painter = painterResource(resId),
                        contentDescription = stringResource(R.string.Delete_Clock),
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .size(36.dp)
                            .align(Alignment.CenterStart),
                    )
                }
            } else if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
                    Icon(
                        painter = painterResource(resId),
                        contentDescription = stringResource(R.string.Delete_Clock),
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .size(36.dp)
                            .align(Alignment.CenterEnd),
                    )
                }
            }
        },
        content = {
            CityListItem(
                city = city,
                modifier = Modifier.background(LocalColorPalette.current.background)
            )
        },
    )
}

@OptIn(ExperimentalTime::class)
@Composable
fun CityListItem(
    city: City,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .padding(vertical = 16.dp),
        headlineContent = {
            Text(
                text = city.name,
                fontSize = 24.sp,
                fontFamily = Aileron,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        },
        supportingContent = { DigitalClock(city.timeZone) },
        trailingContent = {
            Text(
                text = stringResource(getTodayLabel(city.timeZone)),
                fontFamily = Aileron,
            )
        },
        leadingContent = {
            AnalogClockMini(
                timeZone = city.timeZone,
                isDay = city.isDay,
                modifier = Modifier.padding(horizontal = 16.dp).size(48.dp)
            )
        },
        colors = ListItemDefaults.colors(
            headlineColor = MaterialTheme.colorScheme.onBackground,
            supportingColor = MaterialTheme.colorScheme.onBackground,
            containerColor = Color.Transparent,
        )
    )
}

@PreviewLightDark
@Composable
private fun Preview_CityListItem() {
    var cities by remember { mutableStateOf(FAVORITE_CITIES) }
    WcPreview {
        LazyColumn(modifier = Modifier.size(width = 360.dp, height = 430.dp)) {
            item {
                Text(
                    cities.joinToString { it.name },
                    color = LocalColorPalette.current.onBackground,
                )
            }
            itemsIndexed(items = cities, key = { i, city -> city.id }) { i, city ->
                DismissableCityListItem(
                    onDismiss = {
                        cities = cities.filter { it.id != city.id }
                    },
                    city = city,
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}