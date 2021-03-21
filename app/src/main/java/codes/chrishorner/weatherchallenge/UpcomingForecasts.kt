/*
 * Copyright 2020 The Android Open Source Project
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codes.chrishorner.weatherchallenge.style.SmallTempTextStyle
import codes.chrishorner.weatherchallenge.style.WeatherChallengeTheme

data class UpcomingForecast(
    val day: String,
    val percentChanceOfRain: Int,
    val formattedChanceOfRain: String,
    val iconDescriptor: String,
    val lowTemperature: String,
    val highTemperature: String,
)

private val DummyUpcomingForecasts = listOf(
    UpcomingForecast("Tomorrow", 0, "", "partly_cloudy", "13°", "22°"),
    UpcomingForecast("Friday", 0, "", "windy", "11°", "20°"),
    UpcomingForecast("Saturday", 50, "50%", "shower", "10°", "18°"),
    UpcomingForecast("Sunday", 70, "70%", "storm", "9°", "18°"),
    UpcomingForecast("Monday", 20, "20%", "shower", "11°", "22°"),
    UpcomingForecast("Tuesday", 0, "", "partly_cloudy", "10°", "19°"),
    UpcomingForecast("Wednesday", 0, "", "sunny", "12°", "24°"),
)

@Composable
fun UpcomingForecasts(
    modifier: Modifier = Modifier,
    forecasts: List<UpcomingForecast> = DummyUpcomingForecasts
) {
    Column(modifier = modifier) {
        for (forecast in forecasts) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(40.dp)
            ) {

                Text(
                    text = forecast.day,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                if (forecast.percentChanceOfRain > 0) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = forecast.formattedChanceOfRain,
                            style = MaterialTheme.typography.subtitle2,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }

                Icon(
                    painter = painterResource(weatherIconRes(forecast.iconDescriptor)),
                    contentDescription = "", // Not important for accessibility.
                    modifier = Modifier.padding(end = 8.dp)
                )

                Text(
                    text = forecast.lowTemperature,
                    style = SmallTempTextStyle,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .width(32.dp)
                        .padding(end = 4.dp)
                )

                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colors.onBackground.copy(alpha = 0.2f))
                )

                Text(
                    text = forecast.highTemperature,
                    style = SmallTempTextStyle,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(36.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun UpcomingForecastsPreview() {
    WeatherChallengeTheme {
        UpcomingForecasts()
    }
}
