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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlin.random.Random

/**
 * A wrapper around [TopAppBar] which uses [Modifier.statusBarsPadding] to shift the app bar's
 * contents down, but still draws the background behind the status bar too.
 *
 * Thanks Chris: https://github.com/chrisbanes/accompanist/blob/main/sample/src/main/java/dev/chrisbanes/accompanist/sample/insets/EdgeToEdgeLazyColumn.kt
 */
@Composable
fun InsetAwareTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 4.dp
) {
    Surface(
        color = backgroundColor,
        elevation = elevation,
        modifier = modifier
    ) {
        TopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            backgroundColor = Color.Transparent,
            contentColor = contentColor,
            elevation = 0.dp,
            modifier = Modifier.statusBarsPadding()
        )
    }
}

/**
 * Uses [withFrameMillis] to update a state every frame.
 */
@Composable
fun frameTimeMillis(): State<Long> {
    val millisState = mutableStateOf(0L)

    LaunchedEffect(Unit) {
        val startTime = withFrameMillis { it }

        while (true) {
            withFrameMillis { frameTime ->
                millisState.value = frameTime - startTime
            }
        }
    }

    return millisState
}

@DrawableRes
fun weatherIconRes(descriptor: String, night: Boolean = false): Int = when (descriptor) {
    "sunny" -> R.drawable.ic_weather_sunny
    "clear" -> if (night) R.drawable.ic_weather_clear_night else R.drawable.ic_weather_sunny
    "mostly_sunny", "partly_cloudy" -> if (night) R.drawable.ic_weather_partly_cloudy_night else R.drawable.ic_weather_partly_cloudy
    "cloudy" -> R.drawable.ic_weather_cloudy
    "hazy" -> if (night) R.drawable.ic_weather_hazy_night else R.drawable.ic_weather_hazy
    "light_rain", "light_shower" -> R.drawable.ic_weather_light_rain
    "windy" -> R.drawable.ic_weather_windy
    "fog" -> R.drawable.ic_weather_fog
    "shower", "rain", "heavy_shower" -> R.drawable.ic_weather_rain
    "dusty" -> R.drawable.ic_weather_dusty
    "frost" -> R.drawable.ic_weather_frost
    "snow" -> R.drawable.ic_weather_snow
    "storm" -> R.drawable.ic_weather_storm
    "cyclone" -> R.drawable.ic_weather_cyclone
    else -> R.drawable.ic_weather_unknown
}


/**
 * A random number between start (inclusive) and end (exclusive).
 */
fun random(start: Float, end: Float): Float {
    return start + Random.nextFloat() * (end - start)
}
