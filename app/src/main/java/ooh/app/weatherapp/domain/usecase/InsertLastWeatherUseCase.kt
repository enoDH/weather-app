package ooh.app.weatherapp.domain.usecase

import ooh.app.weatherapp.domain.model.LastWeather
import ooh.app.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class InsertLastWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun insertLastWeather(weather: LastWeather) {
        weatherRepository.insertWeather(weather)
    }
}