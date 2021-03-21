package codes.chrishorner.weatherchallenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

data class FormattedConditions(
    val feelsLikeTemperature: String,
    val humidity: String,
    val windSpeed: String,
    val uvWarningTimes: String,
    val description: String,
)

private val DummyConditions = FormattedConditions(
    feelsLikeTemperature = "15.8°",
    humidity = "60%",
    windSpeed = "21 km/h",
    uvWarningTimes = "11:00 - 14:00",
    description = "Partly cloudy. Areas of haze. Winds southerly 20 to 30 km/h decreasing to 15 to 20 km/h in the evening.",
)

@Composable
fun Conditions(
    modifier: Modifier = Modifier,
    conditions: FormattedConditions = DummyConditions,
) {
    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Row {
                Icon(painter = painterResource(R.drawable.ic_water), contentDescription = "")
                Text(text = conditions.humidity)
            }
            Row {
                Icon(painter = painterResource(R.drawable.ic_weather_windy), contentDescription = "")
                Text(text = conditions.windSpeed)
            }
            Row {
                Icon(painter = painterResource(R.drawable.ic_weather_sunny_alert), contentDescription = "")
                Text(text = conditions.uvWarningTimes)
            }
        }
    }
}
