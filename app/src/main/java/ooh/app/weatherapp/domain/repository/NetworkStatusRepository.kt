package ooh.app.weatherapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface NetworkStatusRepository {
    val isOnline: Flow<Boolean>
}
