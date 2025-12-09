package ooh.app.weatherapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ooh.app.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    fun getCity(): Flow<String?> {
        return weatherRepository.getCity()
    }
}