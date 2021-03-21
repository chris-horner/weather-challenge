package codes.chrishorner.weatherchallenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codes.chrishorner.weatherchallenge.style.LargeTempTextStyle
import codes.chrishorner.weatherchallenge.style.MediumTempTextStyle
import codes.chrishorner.weatherchallenge.style.WeatherChallengeTheme

data class FormattedConditions(
    val currentTemperature: String,
    val highTemperature: String,
    val lowTemperature: String,
    val feelsLikeTemperature: String,
    val description: String,
)

private val DummyConditions = FormattedConditions(
    currentTemperature = "17°",
    highTemperature = "22°",
    lowTemperature = "14°",
    feelsLikeTemperature = "15.8°",
    description = "Partly cloudy. Areas of haze. Winds southerly 20 to 30 km/h decreasing to 15 to 20 km/h in the evening."
)

@Composable
fun Conditions(
    modifier: Modifier = Modifier,
    conditions: FormattedConditions = DummyConditions,
) {
    Box(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_weather_partly_cloudy),
                contentDescription = stringResource(R.string.currentIconDesc),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                modifier = Modifier.size(72.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = conditions.currentTemperature,
                style = LargeTempTextStyle,
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = conditions.highTemperature,
                style = MediumTempTextStyle,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(28.dp)
                    .offset(x = (-16).dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colors.onBackground.copy(alpha = 0.2f))
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(R.string.feelsLike),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = conditions.feelsLikeTemperature,
                    style = MediumTempTextStyle,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = conditions.lowTemperature,
                    style = MediumTempTextStyle,
                    modifier = Modifier.alignByBaseline()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = conditions.description,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview
@Composable
private fun ConditionsPreview() {
    WeatherChallengeTheme {
        Conditions()
    }
}
