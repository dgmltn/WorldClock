package com.dgmltn.worldclock.data

import co.touchlab.kermit.Logger
import com.dgmltn.worldclock.domain.City
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

// curl "https://api.mapbox.com/search/geocode/v6/forward?q=Los%20Angeles&autocomplete=true&types=place&access_token=${ACCESS_TOKEN}"
// curl "https://api.mapbox.com/v4/mapbox.mapbox-streets-v8/tilequery/-122.4194,37.7749.json?layers=timezone&access_token=${ACCESS_TOKEN}"
/**
 * Implementation of [GeocoderRepository] using Mapbox Geocoding API.
 * See more here: https://docs.mapbox.com/api/search/geocoding/
 *
 * TODO: revisit this someday ... maybe it's valuable, but it doesn't provide timezone info.
 * So for now, we use Open Meteo for geocoding.
 */
class GeocoderRepositoryImplMapbox(private val client: HttpClient): GeocoderRepository {
    override suspend fun searchCityByName(
        name: String,
        limit: Int
    ): List<City> {
        if (name.isBlank()) return emptyList()
        try {
            val response = client.get("https://geocoding-api.open-meteo.com/v1/search") {
                parameter("name", name)
                parameter("count", limit)
                parameter("language", "en")
                parameter("format", "json")
            }.body<MapboxFeatureCollection>()
            return response.toCityList()
        } catch (e: Exception) {
            // Log the error or handle it as needed
            Logger.e(e) { e.toString() }
            return emptyList()
        }
    }
}

private fun MapboxFeatureCollection.toCityList(): List<City> {
    return features?.mapNotNull { it.toDomainCity() } ?: emptyList()
}

private fun MapboxFeature.toDomainCity(): City? {
    return City(
        id = "mapbox_$id",
        name = properties.name,
        country = properties.placeFormatted,
        detail = properties.placeFormatted,
        latitude = properties.coordinates.latitude,
        longitude = properties.coordinates.longitude,
        timeZone = TimeZone.of("UTC") // Mapbox does not provide timezone info, defaulting to UTC
        //TODO: this is a big problem. We want to know the timezone of the place, but Mapbox does not provide it directly.
    )
}

@Serializable
private data class MapboxFeatureCollection(
    val features: List<MapboxFeature>? = null
)

@Serializable
private data class MapboxFeature(
    val id: String,
    val properties: MapboxProperties,
)

@Serializable
private data class MapboxProperties(
    val name: String,
    val coordinates: MapboxCoordinates,
    val placeFormatted: String,
)

@Serializable
private data class MapboxCoordinates(
    val latitude: Double,
    val longitude: Double,
)