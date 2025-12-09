package ooh.app.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ooh.app.weatherapp.data.database.entity.CityEntity

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)

    @Query("SELECT * FROM cities")
    suspend fun getCities(): List<CityEntity>

    @Query("SELECT city_id FROM cities WHERE city = :cityName")
    suspend fun getCityIdByName(cityName: String): Int

    @Query("SELECT city FROM cities WHERE city_id = :cityId")
    suspend fun getCityNameById(cityId: Int): String
}