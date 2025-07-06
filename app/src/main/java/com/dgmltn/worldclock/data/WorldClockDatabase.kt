package com.dgmltn.worldclock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.dgmltn.worldclock.domain.City
import kotlinx.datetime.TimeZone

@Database(
    entities = [City::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(TimeZoneTypeConverter::class)
abstract class WorldClockDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        private const val DATABASE_NAME = "config"

        @Volatile
        private var INSTANCE: WorldClockDatabase? = null

        fun getInstance(context: Context): WorldClockDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorldClockDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(true) // Only true during development
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


class TimeZoneTypeConverter {
    @TypeConverter
    fun fromTimeZone(timeZone: TimeZone): String = timeZone.id

    @TypeConverter
    fun toTimeZone(id: String): TimeZone = TimeZone.of(id)
}
