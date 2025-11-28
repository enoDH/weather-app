package ooh.app.weatherapp.presentation.ui.home

import ooh.app.weatherapp.domain.model.Current
import ooh.app.weatherapp.domain.model.Forecast

data class HomeUiState(
    val city: String = "Kyiv",
    val currentWeather: Current = Current(),
    val forecastWeather: Forecast = Forecast(),
    val isLoading: Boolean = false,
    val aqi: String = "yes",
    val days: Int = 3,
    val hour: Int = 12
)
