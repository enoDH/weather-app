package ooh.app.weatherapp.data.repository

import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import ooh.app.weatherapp.data.network.NetworkMonitor
import ooh.app.weatherapp.domain.repository.NetworkStatusRepository

@Singleton
class NetworkStatusRepositoryImpl @Inject constructor(
    private val monitor: NetworkMonitor
) : NetworkStatusRepository {
    override val isOnline: Flow<Boolean> = monitor.isOnline
}
