package ooh.app.weatherapp.data.repository

import ooh.app.weatherapp.BuildConfig
import ooh.app.weatherapp.domain.repository.ApiKeyRepository
import javax.inject.Inject

class ApiKeyRepositoryImpl @Inject constructor(): ApiKeyRepository {

    override fun getApiKey(): String{
        return BuildConfig.API_KEY
    }
}