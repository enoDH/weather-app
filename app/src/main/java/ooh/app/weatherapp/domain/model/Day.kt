package ooh.app.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_c") val maxTempC: Double = 0.0,
    @SerializedName("mintemp_c") val minTempC: Double = 0.0,
    @SerializedName("maxwind_mph") val maxWindMph: Double = 0.0,
    @SerializedName("avgvis_km") val avgVisKm: Double = 0.0,
    @SerializedName("avghumidity") val avgHumidity: Int = 0,
    val condition: Condition = Condition(),
    @SerializedName("air_quality") val airQuality: AirQuality = AirQuality()
)
