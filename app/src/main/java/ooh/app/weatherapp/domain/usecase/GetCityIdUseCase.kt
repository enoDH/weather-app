package ooh.app.weatherapp.domain.usecase

import ooh.app.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCityIdUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun getCityIdByCityName(cityName: String): Int {
        return weatherRepository.getCityIdByCityName(cityName)
    }
}