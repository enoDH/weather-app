package ooh.app.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ooh.app.weatherapp.data.database.entity.ForecastEntity

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: List<ForecastEntity>)

    @Query("SELECT * FROM forecast WHERE city_id = :cityId")
    suspend fun getForecast(cityId: Int): List<ForecastEntity>

    @Query("DELETE FROM forecast WHERE city_id = :cityId")
    suspend fun deleteForecastByCityId(cityId: Int)
}