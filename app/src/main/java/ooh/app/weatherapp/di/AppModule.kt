package ooh.app.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ooh.app.weatherapp.data.api.ApiService
import ooh.app.weatherapp.data.api.interceptors.AuthInterceptor
import ooh.app.weatherapp.data.repository.ApiKeyRepositoryImpl
import ooh.app.weatherapp.data.repository.WeatherImpl
import ooh.app.weatherapp.domain.repository.ApiKeyRepository
import ooh.app.weatherapp.domain.repository.WeatherRepository
import ooh.app.weatherapp.domain.usecase.CurrentWeatherUseCase
import ooh.app.weatherapp.domain.usecase.ForecastWeatherUseCase
import ooh.app.weatherapp.domain.usecase.GetCitiesUseCase
import ooh.app.weatherapp.domain.usecase.GetCityIdUseCase
import ooh.app.weatherapp.domain.usecase.GetCityUseCase
import ooh.app.weatherapp.domain.usecase.GetLastForecastUseCase
import ooh.app.weatherapp.domain.usecase.GetLastWeatherUseCase
import ooh.app.weatherapp.domain.usecase.InsertCityUseCase
import ooh.app.weatherapp.domain.usecase.InsertLastForecastUseCase
import ooh.app.weatherapp.domain.usecase.InsertLastWeatherUseCase
import ooh.app.weatherapp.domain.usecase.SaveCityUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(apiKey: String): AuthInterceptor {
        return AuthInterceptor(apiKey)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiKey(repo: ApiKeyRepositoryImpl): String {
        return repo.getApiKey()
    }

    @Provides
    @Singleton
    fun provideApiKeyRepository(implementation: ApiKeyRepositoryImpl): ApiKeyRepository {
        return implementation
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(implementation: WeatherImpl): WeatherRepository {
        return implementation
    }

    @Provides
    fun provideGetCurrentWeatherUseCase(repo: WeatherRepository): CurrentWeatherUseCase {
        return CurrentWeatherUseCase(repo)
    }

    @Provides
    fun provideForecastWeatherUseCase(repo: WeatherRepository): ForecastWeatherUseCase {
        return ForecastWeatherUseCase(repo)
    }

    @Provides
    fun provideInsertCityUseCase(repo: WeatherRepository): InsertCityUseCase {
        return InsertCityUseCase(repo)
    }

    @Provides
    fun provideGetCitiesUseCase(repo: WeatherRepository): GetCitiesUseCase {
        return GetCitiesUseCase(repo)
    }

    @Provides
    fun provideSaveCityUseCase(repo: WeatherRepository): SaveCityUseCase {
        return SaveCityUseCase(repo)
    }

    @Provides
    fun provideGetCityUseCase(repo: WeatherRepository): GetCityUseCase {
        return GetCityUseCase(repo)
    }

    @Provides
    fun provideGetLustForecastUseCase(repo: WeatherRepository): GetLastForecastUseCase{
        return GetLastForecastUseCase(repo)
    }

    @Provides
    fun provideGetLastWeatherUseCase(repo: WeatherRepository): GetLastWeatherUseCase{
        return GetLastWeatherUseCase(repo)
    }

    @Provides
    fun provideInsertLastWeatherUseCase(repo: WeatherRepository): InsertLastWeatherUseCase{
        return InsertLastWeatherUseCase(repo)
    }

    @Provides
    fun provideInsertLastForecastUseCase(repo: WeatherRepository): InsertLastForecastUseCase{
        return InsertLastForecastUseCase(repo)
    }

    @Provides
    fun provideGetCityIdUseCase(repo: WeatherRepository): GetCityIdUseCase{
        return GetCityIdUseCase(repo)
    }
}