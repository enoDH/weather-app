package ooh.app.weatherapp.domain.usecase

import jakarta.inject.Inject
import ooh.app.weatherapp.domain.model.LastForecast
import ooh.app.weatherapp.domain.repository.WeatherRepository

class GetLastForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
){

    suspend fun getLastForecast(city: Int): List<LastForecast>{
        return weatherRepository.getLastForecast(city)
    }
}