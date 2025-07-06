package com.dgmltn.worldclock.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgmltn.worldclock.data.CityRepository
import com.dgmltn.worldclock.data.GeocoderRepository
import com.dgmltn.worldclock.domain.ALL_CITIES
import com.dgmltn.worldclock.domain.City
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class MainViewModel(
    private val geocoder: GeocoderRepository,
    private val cityRepository: CityRepository,
) : ViewModel() {
    val cities: StateFlow<List<City>> = cityRepository
        .listAll()
        .map { cities ->
            // Sort cities by tz offset, and then by longitude
            cities.sortedWith(
                compareBy<City>(
                    { it.timeZone.offsetAt(Clock.System.now()).totalSeconds },
                    { it.longitude }
                )
            )
        }
        .onEach {
            if (it.isEmpty()) {
                // If no cities are present, add the user's timezone city
                cityRepository.addCity(ALL_CITIES.first { it.timeZone == TimeZone.currentSystemDefault() })
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val cityQueryText = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val suggestions: StateFlow<List<City>> = cityQueryText
        .map { it.trim() }
        .debounce(300.milliseconds)
        .map {
            if (it.isBlank()) {
                ALL_CITIES
            } else {
                geocoder.searchCityByName(it, 5)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ALL_CITIES)

    fun updateQuery(query: String) {
        cityQueryText.update { query.trim() }
    }

    fun addCity(city: City) {
        viewModelScope.launch {
            cityRepository.addCity(city)
        }
    }

    fun deleteCity(city: City) {
        viewModelScope.launch {
            cityRepository.removeCity(city.id)
        }
    }
}

