package ooh.app.weatherapp.presentation.ui.home

import ooh.app.weatherapp.domain.model.Current
import ooh.app.weatherapp.domain.model.CurrentWeather
import ooh.app.weatherapp.domain.model.Forecast

data class HomeUiState(
    val city: String = "",
    val currentWeather: Current = Current(),
    val forecastWeather: Forecast = Forecast(),
    val isLoading: Boolean = false,
    val aqi: String = "yes",
    val days: Int = 3,
    val hour: Int = 12,
    val cities: List<String> = emptyList(),
    val citiesWeather: List<CurrentWeather> = emptyList(),
    val displayDialog: Boolean = false,
    val search: CurrentWeather = CurrentWeather(),
    val query: String = "",
    val hasSearchResult: Boolean = false,
    val networkStatus: Boolean = false
)
