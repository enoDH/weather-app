package ooh.app.weatherapp.domain.usecase

import jakarta.inject.Inject
import ooh.app.weatherapp.domain.repository.WeatherRepository

class InsertCityUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend fun insertCity(city: String) {
        weatherRepository.insertCity(city)
    }
}