package ooh.app.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class Location(
    val name: String = "",
    val region: String = "",
    val country: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    @SerializedName("tz_id")
    val tzId: String = "",
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long = 0,
    val localtime: String = ""
)
