package ooh.app.weatherapp.domain.model

data class ForecastWeather(
    val location: LocationForecast = LocationForecast(),
    val current: CurrentForecast = CurrentForecast(),
    val forecast: Forecast = Forecast()
)
