package ooh.app.weatherapp.domain.model

data class CurrentWeather(
    val location: Location = Location(),
    val current: Current = Current()
)
