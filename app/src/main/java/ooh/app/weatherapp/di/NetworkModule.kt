package ooh.app.weatherapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import ooh.app.weatherapp.data.network.NetworkMonitor
import ooh.app.weatherapp.data.repository.NetworkStatusRepositoryImpl
import ooh.app.weatherapp.domain.repository.NetworkStatusRepository
import ooh.app.weatherapp.domain.usecase.GetNetworkStatusUseCase

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkStatusRepository(
        monitor: NetworkMonitor
    ): NetworkStatusRepository {
        return NetworkStatusRepositoryImpl(monitor)
    }

    @Provides
    fun provideGetNetworkStatusUseCase(repo: NetworkStatusRepository): GetNetworkStatusUseCase{
        return GetNetworkStatusUseCase(repo)
    }
}

