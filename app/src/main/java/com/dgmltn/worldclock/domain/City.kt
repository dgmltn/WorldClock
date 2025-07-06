package com.dgmltn.worldclock.domain

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.TimeZone
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "city")
@Keep
@OptIn(ExperimentalUuidApi::class)
data class City(
    val name: String,
    val country: String,
    val detail: String,
    val latitude: Double,
    val longitude: Double,
    val timeZone: TimeZone,
    @PrimaryKey val id: String = Uuid.random().toString(),
)

val FAVORITE_CITIES = listOf(
    City(
        id = "los_angeles",
        name = "Los Angeles",
        country = "United States",
        detail = "California",
        latitude = 34.0522,
        longitude = -118.2437,
        timeZone = TimeZone.of("America/Los_Angeles")
    ),
    City(
        id = "new_york",
        name = "New York",
        country = "United States",
        detail = "New York",
        latitude = 40.7128,
        longitude = -74.0060,
        timeZone = TimeZone.of("America/New_York")
    ),
    City(
        id = "london",
        name = "London",
        country = "United Kingdom",
        detail = "England",
        latitude = 51.5074,
        longitude = -0.1278,
        timeZone = TimeZone.of("Europe/London")
    ),
    City(
        id = "tokyo",
        name = "Tokyo",
        country = "Japan",
        detail = "Tokyo",
        latitude = 35.6762,
        longitude = 139.6503,
        timeZone = TimeZone.of("Asia/Tokyo")
    ),
    City(
        id = "sydney",
        name = "Sydney",
        country = "Australia",
        detail = "New South Wales",
        latitude = -33.8688,
        longitude = 151.2093,
        timeZone = TimeZone.of("Australia/Sydney")
    )
)

val ALL_CITIES = listOf(
    City("Baker Island", "United States", "", 0.1936, -176.4769, TimeZone.of("Etc/GMT+12")), // UTC-12
    City("Pago Pago", "American Samoa", "", -14.2756, -170.7020, TimeZone.of("Pacific/Pago_Pago")), // UTC-11
    City("Honolulu", "United States", "", 21.3069, -157.8583, TimeZone.of("Pacific/Honolulu")), // UTC-10
    City("Anchorage", "United States", "", 61.2181, -149.9003, TimeZone.of("America/Anchorage")), // UTC-9
    City("Los Angeles", "United States", "", 34.0522, -118.2437, TimeZone.of("America/Los_Angeles")), // UTC-8
    City("Denver", "United States", "", 39.7392, -104.9903, TimeZone.of("America/Denver")), // UTC-7
    City("Mexico City", "Mexico", "", 19.4326, -99.1332, TimeZone.of("America/Mexico_City")), // UTC-6
    City("New York", "United States", "", 40.7128, -74.0060, TimeZone.of("America/New_York")), // UTC-5
    City("Caracas", "Venezuela", "", 10.4806, -66.9036, TimeZone.of("America/Caracas")), // UTC-4
    City("Buenos Aires", "Argentina", "", -34.6037, -58.3816, TimeZone.of("America/Argentina/Buenos_Aires")), // UTC-3
    City("South Georgia", "South Georgia and the South Sandwich Islands", "", -54.2811, -36.5092, TimeZone.of("Atlantic/South_Georgia")), // UTC-2
    City("Praia", "Cape Verde", "", 14.9331, -23.5133, TimeZone.of("Atlantic/Cape_Verde")), // UTC-1
    City("London", "United Kingdom", "", 51.5074, -0.1278, TimeZone.of("Europe/London")), // UTC+0
    City("Paris", "France", "", 48.8566, 2.3522, TimeZone.of("Europe/Paris")), // UTC+1
    City("Cairo", "Egypt", "", 30.0444, 31.2357, TimeZone.of("Africa/Cairo")), // UTC+2
    City("Moscow", "Russia", "", 55.7558, 37.6173, TimeZone.of("Europe/Moscow")), // UTC+3
    City("Dubai", "United Arab Emirates", "", 25.2048, 55.2708, TimeZone.of("Asia/Dubai")), // UTC+4
    City("Karachi", "Pakistan", "", 24.8607, 67.0011, TimeZone.of("Asia/Karachi")), // UTC+5
    City("Dhaka", "Bangladesh", "", 23.8103, 90.4125, TimeZone.of("Asia/Dhaka")), // UTC+6
    City("Bangkok", "Thailand", "", 13.7563, 100.5018, TimeZone.of("Asia/Bangkok")), // UTC+7
    City("Beijing", "China", "", 39.9042, 116.4074, TimeZone.of("Asia/Shanghai")), // UTC+8
    City("Tokyo", "Japan", "", 35.6762, 139.6503, TimeZone.of("Asia/Tokyo")), // UTC+9
    City("Sydney", "Australia", "", -33.8688, 151.2093, TimeZone.of("Australia/Sydney")), // UTC+10
    City("Noumea", "New Caledonia", "", -22.2558, 166.4505, TimeZone.of("Pacific/Noumea")), // UTC+11
    City("Auckland", "New Zealand", "", -36.8485, 174.7633, TimeZone.of("Pacific/Auckland")), // UTC+12
    City("Apia", "Samoa", "", -13.8333, -171.7667, TimeZone.of("Pacific/Apia")), // UTC+13
    City("Kiritimati", "Kiribati", "", 1.8721, -157.4278, TimeZone.of("Pacific/Kiritimati")) // UTC+14
)