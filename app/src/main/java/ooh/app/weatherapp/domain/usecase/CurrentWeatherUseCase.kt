package ooh.app.weatherapp.domain.usecase

import ooh.app.weatherapp.domain.model.CurrentWeather
import ooh.app.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class CurrentWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun getCurrentWeather(city: String, aqi: String): CurrentWeather{
        return weatherRepository.getCurrentWeather(city, aqi)
    }
}