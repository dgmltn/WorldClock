package com.dgmltn.worldclock.data

import com.dgmltn.worldclock.domain.City

/**
 * Repository interface for geocoding services.
 */
interface GeocoderRepository {
    suspend fun searchCityByName(name: String, limit: Int = 5): List<City>
//    suspend fun getBestMatchForTimezone(timezoneId: String): CityInfo
}