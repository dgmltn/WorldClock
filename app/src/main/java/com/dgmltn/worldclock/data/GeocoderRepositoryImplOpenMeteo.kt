package com.dgmltn.worldclock.data

import co.touchlab.kermit.Logger
import com.dgmltn.worldclock.domain.City
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

class GeocoderRepositoryImplOpenMeteo(private val client: HttpClient) : GeocoderRepository {
    override suspend fun searchCityByName(name: String, limit: Int): List<City> {
        if (name.isBlank()) return emptyList()
        try {
            val response = client.get("https://geocoding-api.open-meteo.com/v1/search") {
                parameter("name", name)
                parameter("count", limit)
                parameter("language", "en")
                parameter("format", "json")
            }.body<GeocodingResponse>()
            return response.results?.mapNotNull { it.toDomainCity() } ?: emptyList()
        } catch (e: Exception) {
            // Log the error or handle it as needed
            Logger.e(e) { e.toString() }
            return emptyList()
        }
    }
}

@Serializable
private data class GeocodingResponse(
    val results: List<GeocodingCity>? = null
)

@Serializable
private data class GeocodingCity(
    val id: Long,
    val name: String,
    val country: String = "¯\\_(ツ)_/¯",
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val admin1: String = "",
    val admin2: String = "",
    val admin3: String = "",
    val admin4: String = "",
) {
    val detail by lazy {
        listOf(admin1, admin2, admin3, admin4)
            .mapNotNull { it.takeIf { it.isNotBlank() } }
            .joinToString(", ")
    }

    fun toDomainCity(): City? = try {
        City(
            name = name,
            country = country,
            detail = detail,
            latitude = latitude,
            longitude = longitude,
            timeZone = TimeZone.of(timezone),
            id = "OpenMeteo-$id"
        )
    } catch (_: Exception) {
        null
    }
}

