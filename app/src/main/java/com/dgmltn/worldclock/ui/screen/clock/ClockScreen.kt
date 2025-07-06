package com.dgmltn.worldclock.ui.screen.clock

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dgmltn.worldclock.domain.City
import com.dgmltn.worldclock.ui.MainViewModel
import com.dgmltn.worldclock.ui.component.AnalogClock
import com.dgmltn.worldclock.ui.component.DigitalClock
import com.dgmltn.worldclock.ui.component.WormPagerIndicator
import com.dgmltn.worldclock.ui.screen.citylist.koinActivityViewModel

@Composable
fun ClockScreen(
    modifier: Modifier = Modifier,
    vm: MainViewModel = koinActivityViewModel(),
) {
    val cities by vm.cities.collectAsState()
    ClockScreen(cities, modifier)
}


@Composable
fun ClockScreen(
    cities: List<City>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { cities.size })

    Box(modifier = modifier.fillMaxSize()) {
        Column(verticalArrangement = spacedBy(24.dp)) {
            WormPagerIndicator(
                count = pagerState.pageCount,
                currentPage = pagerState.currentPage,
                pageOffset = pagerState.currentPageOffsetFraction,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25f),
                selectedColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .height(40.dp)
            )
            HorizontalPager(state = pagerState) { page ->
                val city = cities[page]
                CityPage(city = city)
            }
        }
    }
}

@Composable
fun CityPage(
    city: City,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 64.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        AnalogClock(
            timeZone = city.timeZone,
            modifier = Modifier.size(300.dp),
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = city.name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        DigitalClock(
            timeZone = city.timeZone,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Text(
            text = city.country,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray,
        )
    }
}