package ooh.app.weatherapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import ooh.app.weatherapp.data.database.WeatherDatabase
import ooh.app.weatherapp.data.database.dao.CityDao
import ooh.app.weatherapp.data.database.dao.ForecastDao
import ooh.app.weatherapp.data.database.dao.WeatherDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            "weather.db"
        ).build()
    }

    @Provides
    fun provideCityDao(database: WeatherDatabase): CityDao{
        return database.cityDao()
    }

    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    fun provideForecastDao(database: WeatherDatabase): ForecastDao {
        return database.forecastDao()
    }
}