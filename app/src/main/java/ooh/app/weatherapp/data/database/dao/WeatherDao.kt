package ooh.app.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ooh.app.weatherapp.data.database.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(city: WeatherEntity)

    @Query("SELECT * FROM weather WHERE city_id = :cityId")
    suspend fun getWeather(cityId: Int): WeatherEntity

    @Query("DELETE FROM weather WHERE city_id = :cityId")
    suspend fun deleteWeatherByCityId(cityId: Int)
}