package ooh.app.weatherapp.data.repository

import kotlinx.coroutines.flow.Flow
import ooh.app.weatherapp.data.api.ApiService
import ooh.app.weatherapp.data.database.dao.CityDao
import ooh.app.weatherapp.data.database.dao.ForecastDao
import ooh.app.weatherapp.data.database.dao.WeatherDao
import ooh.app.weatherapp.data.database.entity.CityEntity
import ooh.app.weatherapp.data.database.entity.ForecastEntity
import ooh.app.weatherapp.data.database.entity.WeatherEntity
import ooh.app.weatherapp.data.datasource.CityDataSource
import ooh.app.weatherapp.domain.model.CurrentWeather
import ooh.app.weatherapp.domain.model.ForecastWeather
import ooh.app.weatherapp.domain.model.LastForecast
import ooh.app.weatherapp.domain.model.LastWeather
import ooh.app.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherImpl @Inject constructor(
    private val api: ApiService,
    private val weatherDao: WeatherDao,
    private val forecastDao: ForecastDao,
    private val cityDao: CityDao,
    private val prefs: CityDataSource
) : WeatherRepository {
    override suspend fun getCurrentWeather(
        city: String,
        aqi: String
    ): CurrentWeather {
        return api.currentWeather(city, aqi)
    }

    override suspend fun getForecastWeather(
        city: String,
        aqi: String,
        days: Int,
        hour: Int
    ): ForecastWeather {
        return api.forecastWeather(city, aqi, days, hour)
    }

    override suspend fun getLastWeather(cityId: Int): LastWeather {
        val entity = weatherDao.getWeather(cityId)
        val city = cityDao.getCityNameById(cityId)

        val weather = LastWeather(
            city = city,
            lastUpdated = entity.lastUpdated,
            tempC = entity.tempC,
            windDir = entity.windDir,
            windMph = entity.windMph, timestamp = entity.timestamp
        )

        return weather
    }

    override suspend fun getLastForecast(cityId: Int): List<LastForecast> {
        val entity = forecastDao.getForecast(cityId)
        val city = cityDao.getCityNameById(cityId)

        val forecast = entity.map {
            LastForecast(
                id = it.id,
                city = city,
                date = it.date,
                maxTempC = it.maxTempC,
                minTempC = it.minTempC
            )
        }

        return forecast
    }

    override suspend fun insertWeather(weather: LastWeather) {
        val cityId = cityDao.getCityIdByName(weather.city)
        weatherDao.deleteWeatherByCityId(cityId)

        val entity = WeatherEntity(
            city_id = cityId,
            lastUpdated = weather.lastUpdated,
            tempC = weather.tempC,
            windMph = weather.windMph,
            windDir = weather.windDir,
            conditionText = weather.conditionText,
            conditionIconUrl = weather.conditionIconUrl,
            timestamp = weather.timestamp
        )
        weatherDao.insertWeather(entity)
    }

    override suspend fun insertForecast(forecast: List<LastForecast>) {
        val cityId = cityDao.getCityIdByName(forecast[0].city)
        forecastDao.deleteForecastByCityId(cityId)

        val entity = forecast.map {
            ForecastEntity(
                city_id = cityId,
                date = it.date,
                maxTempC = it.maxTempC,
                minTempC = it.minTempC,
                conditionText = it.conditionText,
                conditionIconUrl = it.conditionIconUrl
            )
        }

        forecastDao.insertForecast(entity)
    }

    override suspend fun insertCity(city: String) {
        cityDao.insertCity(CityEntity(city = city))
    }

    override suspend fun getCities(): List<String> {
        return cityDao.getCities().map { it.city }
    }

    override suspend fun saveCity(city: String) {
        prefs.saveCity(city)
    }

    override fun getCity(): Flow<String?> {
        return prefs.cityFlow
    }

    override suspend fun getCityIdByCityName(cityName: String): Int {
        return cityDao.getCityIdByName(cityName)
    }

}