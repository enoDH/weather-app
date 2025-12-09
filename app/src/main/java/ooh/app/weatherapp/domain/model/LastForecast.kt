package ooh.app.weatherapp.domain.model

data class LastForecast(
    val id: Int = 0,
    val city: String = "",
    val date: String = "",
    val maxTempC: Double = 0.0,
    val minTempC: Double = 0.0,
    val conditionText: String = "",
    val conditionIconUrl: String = ""
)
