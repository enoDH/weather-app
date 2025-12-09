package ooh.app.weatherapp.domain.usecase

import jakarta.inject.Inject
import ooh.app.weatherapp.domain.repository.WeatherRepository

class SaveCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
){
    suspend fun saveCity(city: String){
        weatherRepository.saveCity(city)
    }
}