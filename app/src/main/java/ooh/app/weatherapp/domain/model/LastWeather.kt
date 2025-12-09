package ooh.app.weatherapp.domain.model

data class LastWeather(
    val city: String = "",
    val lastUpdated: String = "",
    val tempC: Double = 0.0,
    val windMph: Double = 0.0,
    val windDir: String = "",
    val conditionText: String = "",
    val conditionIconUrl: String = "",
    val timestamp: Long = 0
)
