package ooh.app.weatherapp.domain.model

data class ForecastDay(
    val date: String = "",
    val day: Day = Day(),
    val hour: List<Hour> = emptyList<Hour>()
)
