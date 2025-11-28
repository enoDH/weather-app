package ooh.app.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class CurrentForecast(
    @SerializedName("last_updated") val lastUpdated: String = "",
    @SerializedName("temp_c") val tempC: Double = 0.0,
    val condition: Condition = Condition(),
    @SerializedName("wind_mph") val windMph: Double = 0.0,
    @SerializedName("wind_dir") val windDir: String = "",
    @SerializedName("precip_in") val precipIn: Double = 0.0,
    val humidity: Int = 0,
    val cloud: Int = 0,
    @SerializedName("air_quality") val airQuality: AirQuality = AirQuality()
)
