package ooh.app.weatherapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CityCurrentWeather(
    onClickCallback: () -> Unit,
    btnText: String,
    cityName: String,
    icon: String,
    iconDescription: String,
    tempC: Double,
    windDir: String,
    windMph: Double
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(color = Color.Cyan, RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                text = cityName,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 36.sp
                )
            )

            Button(onClick = {
                onClickCallback()
            }) {
                Text(btnText)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        )
        {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:$icon")
                    .build(),
                contentDescription = iconDescription,
                modifier = Modifier.size(50.dp)
            )

            Text(
                text = "${tempC}°C",
                fontSize = 32.sp
            )
        }

        Row {
            Text(windDir)

            WindDirectionIcon(
                windDirection = windDir,
                windDirectionText = "Wind $windDir"
            )

            Text(windMph.toString() + "mph")
        }
    }
}