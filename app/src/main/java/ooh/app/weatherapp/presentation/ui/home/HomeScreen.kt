package ooh.app.weatherapp.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ooh.app.weatherapp.presentation.ui.components.CityCurrentWeather
import ooh.app.weatherapp.presentation.ui.components.CustomDialog
import ooh.app.weatherapp.presentation.ui.components.WindDirectionIcon
import ooh.app.weatherapp.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.displayDialog) {
            CustomDialog(onDismissRequest = { uiState.displayDialog }) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var active by remember { mutableStateOf(false) }


                    SearchBar(
                        query = uiState.query,
                        onQueryChange = { newQuery ->
                            viewModel.changeQuery(newQuery)
                        },
                        onSearch = { newQuery ->
                            active = false
                        },
                        active = active,
                        onActiveChange = { active = it },
                        placeholder = { Text("Search") },
                    ) {
                        if (uiState.hasSearchResult) {
                            CityCurrentWeather(
                                onClickCallback = viewModel::saveCity,
                                btnText = "Add",
                                cityName = uiState.search.location.name,
                                icon = uiState.search.current.condition.icon,
                                iconDescription = uiState.search.current.condition.text,
                                tempC = uiState.search.current.tempC,
                                windDir = uiState.search.current.windDir,
                                windMph = uiState.search.current.windMph
                            )


                        } else if (uiState.query.isNotEmpty()) {
                            Text("Searching...", modifier = Modifier.padding(16.dp))
                        } else {
                            Text("#_#", modifier = Modifier.padding(16.dp))
                        }
                    }

                    if (uiState.citiesWeather.isNotEmpty()) {
                        LazyColumn {
                            items(uiState.citiesWeather) { item ->


                                CityCurrentWeather(
                                    onClickCallback = { viewModel.getCityWeather(item.location.name) },
                                    btnText = "Show",
                                    cityName = item.location.name,
                                    icon = item.current.condition.icon,
                                    iconDescription = item.current.condition.text,
                                    tempC = item.current.tempC,
                                    windDir = item.current.windDir,
                                    windMph = item.current.windMph
                                )
                            }
                        }
                    }
                }
            }
        }



        Box(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = uiState.city, style = TextStyle(
                    color = Color.Black, fontSize = 36.sp
                ), modifier = Modifier.align(Alignment.Center)
            )

            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add city",
                modifier = Modifier
                    .size(
                        64.dp
                    )
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 16.dp)
                    .clickable(
                        onClick = {
                            viewModel.displayDialog()
                        })
            )
        }

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
        )
        {
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
                        .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
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
