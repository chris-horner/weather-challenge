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
package codes.chrishorner.weatherchallenge.style

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val AutumnRed = Color(0xFFD13D3D)
private val DeepRed = Color(0xFF470000)
private val DarkBrown = Color(0xFF250909)
private val Orange = Color(0xFFFFDAA8)
private val LightOrange = Color(0xFFFFF4E1)
private val DarkGrey = Color(0xFF121212)
private val Grey = Color(0xFF212121)

val LightColors = lightColors(
    primary = Orange,
    primaryVariant = LightOrange,
    onPrimary = DarkBrown,
    secondary = AutumnRed,
    secondaryVariant = DeepRed,
    onSecondary = DarkBrown,
    surface = Color.White,
    onSurface = DarkBrown,
    onBackground = DarkBrown
)

val DarkColors = darkColors(
    primary = AutumnRed,
    primaryVariant = Grey,
    onPrimary = Color.Black,
    secondary = Orange,
    secondaryVariant = LightOrange,
    onSecondary = Color.Black,
    surface = DarkGrey,
    onSurface = Color.White,
)

val Colors.skyTop: Color get() = if (isLight) primary else surface

val Colors.skyBottom: Color get() = if (isLight) background else Grey
