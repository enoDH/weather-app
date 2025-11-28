package ooh.app.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("air_quality") val airQuality: AirQuality = AirQuality()
)
