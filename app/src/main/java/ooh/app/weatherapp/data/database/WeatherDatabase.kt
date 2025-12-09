package ooh.app.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ooh.app.weatherapp.data.database.dao.CityDao
import ooh.app.weatherapp.data.database.dao.ForecastDao
import ooh.app.weatherapp.data.database.dao.WeatherDao
import ooh.app.weatherapp.data.database.entity.CityEntity
import ooh.app.weatherapp.data.database.entity.ForecastEntity
import ooh.app.weatherapp.data.database.entity.WeatherEntity


@Database(
    entities = [CityEntity::class, WeatherEntity::class, ForecastEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
    abstract fun forecastDao(): ForecastDao
}