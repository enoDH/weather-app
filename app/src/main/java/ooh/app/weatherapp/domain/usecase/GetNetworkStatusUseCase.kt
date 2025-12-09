package ooh.app.weatherapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ooh.app.weatherapp.domain.repository.NetworkStatusRepository
import javax.inject.Inject

class GetNetworkStatusUseCase @Inject constructor(
    private val networkStatusRepository: NetworkStatusRepository
) {
    suspend fun getNetworkStatus(): Flow<Boolean> {
        return networkStatusRepository.isOnline
    }
}