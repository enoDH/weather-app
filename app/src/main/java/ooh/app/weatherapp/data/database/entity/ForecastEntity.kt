package ooh.app.weatherapp.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "forecast",
    foreignKeys = [ForeignKey(
        entity = CityEntity::class,
        parentColumns = ["city_id"],
        childColumns = ["city_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["city_id"])]
)
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city_id: Int = 0,
    val date: String = "",
    val maxTempC: Double = 0.0,
    val minTempC: Double = 0.0,
    val conditionText: String = "",
    val conditionIconUrl: String = ""
)
