package ooh.app.weatherapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ooh.app.weatherapp.domain.model.Condition
import ooh.app.weatherapp.domain.model.Current
import ooh.app.weatherapp.domain.model.CurrentWeather
import ooh.app.weatherapp.domain.model.Day
import ooh.app.weatherapp.domain.model.Forecast
import ooh.app.weatherapp.domain.model.ForecastDay
import ooh.app.weatherapp.domain.model.LastForecast
import ooh.app.weatherapp.domain.model.LastWeather
import ooh.app.weatherapp.domain.usecase.CurrentWeatherUseCase
import ooh.app.weatherapp.domain.usecase.ForecastWeatherUseCase
import ooh.app.weatherapp.domain.usecase.GetCitiesUseCase
import ooh.app.weatherapp.domain.usecase.GetCityIdUseCase
import ooh.app.weatherapp.domain.usecase.GetCityUseCase
import ooh.app.weatherapp.domain.usecase.GetLastForecastUseCase
import ooh.app.weatherapp.domain.usecase.GetLastWeatherUseCase
import ooh.app.weatherapp.domain.usecase.GetNetworkStatusUseCase
import ooh.app.weatherapp.domain.usecase.InsertCityUseCase
import ooh.app.weatherapp.domain.usecase.InsertLastForecastUseCase
import ooh.app.weatherapp.domain.usecase.InsertLastWeatherUseCase
import ooh.app.weatherapp.domain.usecase.SaveCityUseCase
import ooh.app.weatherapp.presentation.ui.home.HomeUiState

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val forecastWeatherUseCase: ForecastWeatherUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val insertCityUseCase: InsertCityUseCase,
    private val saveCityUseCase: SaveCityUseCase,
    private val getCityUseCase: GetCityUseCase,
    private val getLastForecastUseCase: GetLastForecastUseCase,
    private val getLastWeatherUseCase: GetLastWeatherUseCase,
    private val insertLastWeatherUseCase: InsertLastWeatherUseCase,
    private val insertLastForecastUseCase: InsertLastForecastUseCase,
    private val getNetworkStatusUseCase: GetNetworkStatusUseCase,
    private val getCityIdUseCase: GetCityIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    networkStatus = getNetworkStatusUseCase.getNetworkStatus().firstOrNull() ?: false
                )
            }

            getCurrentWeather()
            getForecastWeather()
            getCities()
        }
    }

    fun getCurrentWeather() {
        viewModelScope.launch {
            val city = getCityUseCase.getCity().firstOrNull()


            if (_uiState.value.networkStatus) {
                if (!city.isNullOrEmpty())
                {
                    runCatching {
                        currentWeatherUseCase.getCurrentWeather(city, _uiState.value.aqi)
                    }.onSuccess { currentWeather ->
                        _uiState.update {
                            it.copy(
                                currentWeather = currentWeather.current,
                                city = city
                            )
                        }

                        insertLastWeatherUseCase.insertLastWeather(
                            LastWeather(
                                city = city,
                                lastUpdated = currentWeather.current.lastUpdated,
                                tempC = currentWeather.current.tempC,
                                windDir = currentWeather.current.windDir,
                                windMph = currentWeather.current.windMph,
                                conditionText = currentWeather.current.condition.text,
                                conditionIconUrl = currentWeather.current.condition.icon
                            )
                        )
                    }.onFailure { e ->
                        Log.e("TEST_Weather", e.message.toString())
                    }
                }
            }
            else{
                Log.i("TEST_Weather",_uiState.value.networkStatus.toString())
                if (!city.isNullOrEmpty()){
                    val lastWeather = getLastWeatherUseCase.getLastWeather(
                        getCityIdUseCase.getCityIdByCityName(city)
                    )

                    _uiState.update {
                        it.copy(
                            currentWeather = Current(
                                lastUpdated = lastWeather.lastUpdated,
                                tempC = lastWeather.tempC,
                                condition = Condition(
                                    text = lastWeather.conditionText,
                                    icon = lastWeather.conditionIconUrl
                                ),
                                windMph = lastWeather.windMph,
                                windDir = lastWeather.windDir
                            ),
                            city = city
                        )
                    }
                }
            }

        }
    }

    fun getForecastWeather() {
        viewModelScope.launch {
            val city = getCityUseCase.getCity().firstOrNull()

            if (_uiState.value.networkStatus) {
                if (!city.isNullOrEmpty()){
                    runCatching {
                        forecastWeatherUseCase.getForecastWeather(
                            city = city,
                            aqi = _uiState.value.aqi,
                            days = _uiState.value.days,
                            hour = _uiState.value.hour
                        )
                    }.onSuccess { forecastWeather ->
                        _uiState.update {
                            it.copy(
                                forecastWeather = forecastWeather.forecast
                            )
                        }

                        insertLastForecastUseCase.insertLastForecast(
                            forecastWeather.forecast.forecastDay.map {
                                LastForecast(
                                    city = city,
                                    date = it.date,
                                    maxTempC = it.day.maxTempC,
                                    minTempC = it.day.minTempC,
                                    conditionText = it.day.condition.text,
                                    conditionIconUrl = it.day.condition.icon
                                )
                            }
                        )
                    }.onFailure { e ->
                        Log.e("TEST_Weather", e.message.toString())
                    }
                }
            }
            else {
                if(!city.isNullOrEmpty()){
                    val forecast = getLastForecastUseCase.getLastForecast(
                        getCityIdUseCase.getCityIdByCityName(city)
                    )

                    _uiState.update {
                        it.copy(
                            forecastWeather = Forecast(
                                forecastDay = forecast.map {
                                    ForecastDay(
                                        date = it.date,
                                        day = Day(
                                            minTempC = it.minTempC,
                                            maxTempC = it.maxTempC,
                                            condition = Condition(
                                                text = it.conditionText,
                                                icon = it.conditionIconUrl
                                            )
                                        )
                                    )
                                }
                            )
                        )
                    }
                }
            }
        }
    }

    fun search() {
        viewModelScope.launch {

            runCatching {
                currentWeatherUseCase.getCurrentWeather(_uiState.value.query, "no")
            }.onSuccess { currentWeather ->
                _uiState.update {
                    it.copy(
                        search = currentWeather, hasSearchResult = true
                    )
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        hasSearchResult = false
                    )
                }
                Log.e("TEST_Weather", e.message.toString())
            }


        }

    }

    fun displayDialog() {
        if (_uiState.value.networkStatus){
            getCities()
            getCitiesWeather()

            _uiState.update {
                it.copy(
                    displayDialog = true
                )
            }
        }
    }

    fun hideDialog() {
        _uiState.update {
            it.copy(
                displayDialog = false
            )
        }
    }

    fun changeQuery(query: String) {
        _uiState.update {
            it.copy(
                query = query
            )
        }

        if (query.isNotBlank()) {
            search()
        } else {
            _uiState.update { it.copy(search = CurrentWeather()) }
        }
    }






    fun saveCity() {
        viewModelScope.launch {
            val cities = getCitiesUseCase.getCities()

            val isFound = cities.find {
                it == _uiState.value.search.location.name
            }

            if (isFound.isNullOrEmpty() && _uiState.value.search.location.name != "") {
                insertCityUseCase.insertCity(_uiState.value.search.location.name)
                saveCityUseCase.saveCity(_uiState.value.search.location.name)
            }

            getCurrentWeather()
            getForecastWeather()
            getCities()
            hideDialog()
        }
    }

    fun getCityWeather(city: String) {
        viewModelScope.launch {

            runCatching {
                currentWeatherUseCase.getCurrentWeather(city, _uiState.value.aqi)
            }.onSuccess { currentWeather ->
                _uiState.update {
                    it.copy(
                        currentWeather = currentWeather.current, city = currentWeather.location.name
                    )
                }
            }.onFailure { e ->
                Log.e("TEST_Weather", e.message.toString())
            }

            saveCityUseCase.saveCity(city)
            getForecastWeather()
            hideDialog()
        }
    }

    fun getCities() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    cities = getCitiesUseCase.getCities()
                )
            }
        }
    }

    fun getCitiesWeather() {
        viewModelScope.launch {
            if (_uiState.value.cities.isNotEmpty()) {
                val citiesWeather = mutableListOf<CurrentWeather>()

                _uiState.value.cities.forEach { it ->
                    if (it != "") {
                        citiesWeather.add(
                            currentWeatherUseCase.getCurrentWeather(
                                city = it, aqi = "no"
                            )
                        )
                    }
                }


                _uiState.update {
                    it.copy(
                        citiesWeather = citiesWeather
                    )
                }
            }
        }
    }

}