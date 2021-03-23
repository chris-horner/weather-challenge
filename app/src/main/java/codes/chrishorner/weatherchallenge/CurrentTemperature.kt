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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

data class FormattedTemperatures(
    val current: String,
    val low: String,
    val high: String,
)

private val DummyTemperatures = FormattedTemperatures(
    current = "17°",
    low = "14°",
    high = "22°",
)

@Composable
fun CurrentTemperature(
    modifier: Modifier = Modifier,
    temperatures: FormattedTemperatures = DummyTemperatures,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_weather_partly_cloudy),
                contentDescription = stringResource(R.string.currentIconDesc),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                modifier = Modifier.size(48.dp),
            )
            Text(
                text = temperatures.current,
                style = LargeTempTextStyle,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = temperatures.low,
                style = MediumTempTextStyle,
            )
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colors.secondaryVariant.copy(alpha = 0.2f))
            )
            Text(
                text = temperatures.high,
                style = MediumTempTextStyle,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CurrentTemperaturePreview() {
    WeatherChallengeTheme {
        CurrentTemperature()
    }
}
