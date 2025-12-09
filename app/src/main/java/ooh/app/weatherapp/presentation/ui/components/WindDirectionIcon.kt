package ooh.app.weatherapp.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color

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