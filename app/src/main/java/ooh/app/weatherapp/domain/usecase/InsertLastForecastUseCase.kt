package ooh.app.weatherapp.domain.usecase

import ooh.app.weatherapp.domain.model.LastForecast
import ooh.app.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class InsertLastForecastUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun insertLastForecast(forecast: List<LastForecast>){
        weatherRepository.insertForecast(forecast)
    }
}