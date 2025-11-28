package ooh.app.weatherapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ooh.app.weatherapp.domain.usecase.CurrentWeatherUseCase
import ooh.app.weatherapp.domain.usecase.ForecastWeatherUseCase
import ooh.app.weatherapp.presentation.ui.home.HomeUiState

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val forecastWeatherUseCase: ForecastWeatherUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getCurrentWeather()
        getForecastWeather()
    }

    fun getCurrentWeather() {
        viewModelScope.launch {

            runCatching {
                currentWeatherUseCase.getCurrentWeather(_uiState.value.city, _uiState.value.aqi)
            }.onSuccess { currentWeather ->
                _uiState.update {
                    it.copy(
                        currentWeather = currentWeather.current
                    )
                }
            }.onFailure { e ->
                Log.e("TEST_Weather", e.message.toString())
            }


        }
    }

    fun getForecastWeather() {
        viewModelScope.launch {

            runCatching {
                forecastWeatherUseCase.getForecastWeather(
                    _uiState.value.city,
                    _uiState.value.aqi,
                    _uiState.value.days,
                    _uiState.value.hour
                )
            }.onSuccess { forecastWeather ->
                _uiState.update {
                    it.copy(
                        forecastWeather = forecastWeather.forecast
                    )
                }
            }.onFailure { e ->
                Log.e("TEST_Weather", e.message.toString())
            }


        }
    }
}