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
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Aqua = Color(0xff34d9b8)
private val DarkAqua = Color(0xff00a183)
private val VeryLightAqua = Color(0xffedfdf8)
private val Orange = Color(0xffe47633)
private val LightOrange = Color(0xfff2a363)
private val DarkGrey = Color(0xff121212)

private val DaySkyTop = Color(0xffE0F3FA)
private val DaySkyBottom = Color(0xffc2f9ff)
private val NightSkyTop = Color(0xFFFF00FF)
private val NightSkyBottom = Color(0xFFFF00FF)

val LightColors = lightColors(
    primary = Aqua,
    primaryVariant = DarkAqua,
    onPrimary = Color.Black,
    secondary = Orange,
    secondaryVariant = LightOrange,
    onSecondary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black
)

val DarkColors = darkColors(
    primary = Aqua,
    primaryVariant = DarkAqua,
    onPrimary = Color.Black,
    secondary = LightOrange,
    onSecondary = Color.Black,
    surface = DarkGrey,
    onSurface = Color.White,
)

@Composable
fun Colors.skyTop(): Color = if (isLight) DaySkyTop else NightSkyTop

@Composable
fun Colors.skyBottom(): Color = if (isLight) DaySkyBottom else NightSkyBottom
