package ooh.app.weatherapp.domain.usecase

import ooh.app.weatherapp.domain.model.LastWeather
import ooh.app.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLastWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getLastWeather(city: Int): LastWeather {
        return weatherRepository.getLastWeather(city)
    }
}