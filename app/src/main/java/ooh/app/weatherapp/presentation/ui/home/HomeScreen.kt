package ooh.app.weatherapp.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = uiState.city,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.displayDialog() }) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add city"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            if (uiState.displayDialog) {
                CustomDialog(
                    onDismissRequest = { uiState.displayDialog }
                ) {
                    DialogContent(viewModel = viewModel, uiState = uiState)
                }
            }

            Text(
                text = "${uiState.currentWeather.tempC}°C",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:" + uiState.currentWeather.condition.icon)
                    .build(),
                contentDescription = uiState.currentWeather.condition.text,
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = uiState.currentWeather.condition.text,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiState.currentWeather.windDir,
                    style = MaterialTheme.typography.bodyLarge
                )

                WindDirectionIcon(
                    windDirection = uiState.currentWeather.windDir,
                    windDirectionText = "Wind ${uiState.currentWeather.windDir}"
                )

                Text(
                    text = uiState.currentWeather.windMph.toString() + " mph",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AirQualityItem("CO", uiState.currentWeather.airQuality.co)
                    AirQualityItem("NO2", uiState.currentWeather.airQuality.no2)
                    AirQualityItem("O3", uiState.currentWeather.airQuality.o3)
                    AirQualityItem("SO2", uiState.currentWeather.airQuality.so2)
                    AirQualityItem("PM2.5", uiState.currentWeather.airQuality.pm25)
                    AirQualityItem("PM10", uiState.currentWeather.airQuality.pm10)
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                items(uiState.forecastWeather.forecastDay) { item ->
                    Card(
                        modifier = Modifier
                            .width(130.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = item.date,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https:" + item.day.condition.icon)
                                    .build(),
                                contentDescription = item.day.condition.text,
                                modifier = Modifier.size(42.dp)
                            )

                            Text(
                                text = item.day.condition.text,
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text("Max: ${item.day.maxTempC}")
                            Text("Min: ${item.day.minTempC}")
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogContent(
    viewModel: HomeViewModel,
    uiState: HomeUiState
) {

    Surface(
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Add City",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.hideDialog() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Close dialog"
                        )
                    }
                }
            )

            Divider()


            var active by remember { mutableStateOf(false) }

            SearchBar(
                query = uiState.query,
                onQueryChange = viewModel::changeQuery,
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Search city") },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {

                    when {
                        uiState.hasSearchResult -> {
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
                        }

                        uiState.query.isNotEmpty() -> {
                            Text("Searching…")
                        }

                        else -> {
                            Text("Enter a city...")
                        }
                    }
                }
            }


            if (uiState.citiesWeather.isNotEmpty()) {
                Text(
                    "Saved Cities",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp, start = 16.dp)
                        .align(Alignment.Start)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                ) {
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


@Composable
private fun AirQualityItem(label: String, value: Double) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

