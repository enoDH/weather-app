package ooh.app.weatherapp.domain.repository

interface ApiKeyRepository {
    fun getApiKey(): String
}