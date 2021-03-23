/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package codes.chrishorner.weatherchallenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codes.chrishorner.weatherchallenge.style.MediumTempTextStyle
import codes.chrishorner.weatherchallenge.style.SmallTempTextStyle
import codes.chrishorner.weatherchallenge.style.WeatherChallengeTheme

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
    description = "A fine autumn morning with a chance of showers in the afternoon. Winds southerly 20 to 30 km/h decreasing to 15 to 20 km/h in the evening.",
)

@Composable
fun Conditions(
    modifier: Modifier = Modifier,
    conditions: FormattedConditions = DummyConditions,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.feelsLike),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.alignByBaseline()
            )
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
            val iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_water),
                    tint = iconTint,
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
                    tint = iconTint,
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
                    tint = iconTint,
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ConditionsPreview() {
    WeatherChallengeTheme {
        UpcomingForecasts()
    }
}
