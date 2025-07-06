package com.dgmltn.worldclock.ui.screen.addcity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dgmltn.worldclock.R
import com.dgmltn.worldclock.domain.ALL_CITIES
import com.dgmltn.worldclock.domain.City
import com.dgmltn.worldclock.ui.component.DigitalClock
import com.dgmltn.worldclock.ui.component.MapPreview

@Composable
fun AddCityScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    cityResults: List<City>,
    onCitySelected: (City) -> Unit,
    selectedCity: City?
) {
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text(stringResource(R.string.Search_for_a_city)) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.Search)) }
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cityResults) { city: City ->
                ListItem(
                    headlineContent = { Text("${city.name}, ${city.country}") },
                    supportingContent = { DigitalClock(city.timeZone) },
                    modifier = Modifier
                        .clickable { onCitySelected(city) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                HorizontalDivider()
            }
        }

        selectedCity?.let {
            MapPreview(city = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCityScreenPreview() {
    val mockCities = ALL_CITIES
    var search by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<City?>(null) }

    AddCityScreen(
        searchQuery = search,
        onSearchQueryChange = { search = it },
        cityResults = mockCities,
        onCitySelected = { selected = it },
        selectedCity = selected
    )
}