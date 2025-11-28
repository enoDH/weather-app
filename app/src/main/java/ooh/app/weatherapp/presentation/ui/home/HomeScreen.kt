package ooh.app.weatherapp.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ooh.app.weatherapp.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            uiState.city, style = TextStyle(
                color = Color.Black, fontSize = 32.sp
            )
        )

        Text(
            text = "${uiState.currentWeather.tempC}°C",
            modifier = Modifier.padding(top = 32.dp),
            style = TextStyle(
                color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 72.sp
            )
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https:" + uiState.currentWeather.condition.icon).build(),
            contentDescription = uiState.currentWeather.condition.text,
            modifier = Modifier.size(100.dp)
        )

        Text(uiState.currentWeather.condition.text)


        Row {
            Text(uiState.currentWeather.windDir)

            WindDirectionIcon(
                windDirection = uiState.currentWeather.windDir,
                windDirectionText = "Wind ${uiState.currentWeather.windDir}"
            )

            Text(uiState.currentWeather.windMph.toString() + "mph")
        }

        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Green, shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(uiState.currentWeather.airQuality.co.toString())
                Text("CO")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(uiState.currentWeather.airQuality.no2.toString())
                Text("NO2")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(uiState.currentWeather.airQuality.o3.toString())
                Text("O3")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(uiState.currentWeather.airQuality.so2.toString())
                Text("SO2")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(uiState.currentWeather.airQuality.pm25.toString())
                Text("PM2.5")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(uiState.currentWeather.airQuality.pm10.toString())
                Text("PM10")
            }
        }

        LazyRow {
            items(uiState.forecastWeather.forecastDay) { item ->
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(4.dp)
                        .background(color = Color.Magenta, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(item.date)

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https:" + item.day.condition.icon).build(),
                        contentDescription = item.day.condition.text,
                        modifier = Modifier.size(50.dp)
                    )

                    Text(item.day.condition.text)

                    Text("Max: ${item.day.maxTempC}")
                    Text("Min: ${item.day.minTempC}")
                }
            }
        }

    }

}


@Composable
fun WindDirectionIcon(
    windDirection: String,
    windDirectionText: String,
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray
) {
    val rotationAngle = directionStringToDegrees(windDirection)

    Icon(
        imageVector = Icons.Filled.ArrowUpward,
        contentDescription = windDirectionText,
        tint = tint,
        modifier = modifier.rotate(rotationAngle.toFloat())
    )
}


fun directionStringToDegrees(direction: String): Double {
    val normalizedDirection = direction.uppercase().trim()

    return when (normalizedDirection) {
        "N" -> 0.0
        "NNE" -> 22.5
        "NE" -> 45.0
        "ENE" -> 67.5
        "E" -> 90.0
        "ESE" -> 112.5
        "SE" -> 135.0
        "SSE" -> 157.5
        "S" -> 180.0
        "SSW" -> 202.5
        "SW" -> 225.0
        "WSW" -> 247.5
        "W" -> 270.0
        "WNW" -> 292.5
        "NW" -> 315.0
        "NNW" -> 337.5
        else -> 0.0
    }
}