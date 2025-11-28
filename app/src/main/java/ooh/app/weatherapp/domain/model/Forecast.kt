package ooh.app.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday") val forecastDay: List<ForecastDay> = emptyList<ForecastDay>()
)
