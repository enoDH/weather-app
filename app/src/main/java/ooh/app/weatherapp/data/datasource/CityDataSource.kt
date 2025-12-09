package ooh.app.weatherapp.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CityDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val CITY_NAME = stringPreferencesKey("city")
    }

    val cityFlow: Flow<String?> =
        dataStore.data.map { prefs ->
            prefs[Keys.CITY_NAME]
        }

    suspend fun saveCity(city: String) {
        dataStore.edit { prefs ->
            prefs[Keys.CITY_NAME] = city
        }
    }
}
