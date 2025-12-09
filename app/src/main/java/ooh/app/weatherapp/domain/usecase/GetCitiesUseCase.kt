package ooh.app.weatherapp.domain.usecase

import jakarta.inject.Inject
import ooh.app.weatherapp.domain.repository.WeatherRepository

class GetCitiesUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun getCities(): List<String> {
        return weatherRepository.getCities()
    }
}