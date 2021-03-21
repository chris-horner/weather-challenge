package codes.chrishorner.weatherchallenge

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import codes.chrishorner.weatherchallenge.style.MediumTempTextStyle
import codes.chrishorner.weatherchallenge.style.SmallTempTextStyle

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
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(R.string.feelsLike),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.alignByBaseline()
                )
            }
            Text(
                text = conditions.feelsLikeTemperature,
                style = MediumTempTextStyle,
                modifier = Modifier.alignByBaseline()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_water),
                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                    contentDescription = ""
                )
                Text(
                    text = conditions.humidity,
                    style = SmallTempTextStyle,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_weather_windy),
                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                    contentDescription = ""
                )
                Text(
                    text = conditions.windSpeed,
                    style = SmallTempTextStyle,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_weather_sunny_alert),
                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                    contentDescription = ""
                )
                Text(
                    text = conditions.uvWarningTimes,
                    style = SmallTempTextStyle,
                )
            }
        }

        Text(
            text = conditions.description,
            style = MaterialTheme.typography.body1
        )
    }
}
