package ooh.app.weatherapp.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "weather",
    foreignKeys = [ForeignKey(
        entity = CityEntity::class,
        parentColumns = ["city_id"],
        childColumns = ["city_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["city_id"])]
)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city_id: Int = 0,
    val lastUpdated: String = "",
    val tempC: Double = 0.0,
    val windMph: Double = 0.0,
    val windDir: String = "",
    val conditionText: String = "",
    val conditionIconUrl: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
