package ooh.app.weatherapp.data.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val apiKey: String
) : Interceptor {

    private val QUERY_API_KEY_NAME = "key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val originalHttpUrl = originalRequest.url

        val newHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(QUERY_API_KEY_NAME, apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()

        return chain.proceed(newRequest)
    }
}