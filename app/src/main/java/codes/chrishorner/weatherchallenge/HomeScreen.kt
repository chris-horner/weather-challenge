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

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import codes.chrishorner.weatherchallenge.style.skyBottom
import codes.chrishorner.weatherchallenge.style.skyTop
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun HomeScreen() {
    HomeUi()
}

@Composable
private fun HomeUi() {

    val scrollState = rememberScrollState()
    val toolbarElevation = animateDpAsState(targetValue = if (scrollState.value > 0) 4.dp else 0.dp)

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { AppBar(toolbarElevation.value) },
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp),
            ) {
                Box(
                    modifier = Modifier.background(
                        Brush.verticalGradient(
                            0f to MaterialTheme.colors.skyTop(),
                            1f to MaterialTheme.colors.skyBottom()
                        )
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.tree),
                        contentDescription = stringResource(R.string.treeDesc),
                        alignment = Alignment.CenterStart,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(256.dp)
                    )

                    FloatingLeaves(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(256.dp)
                    )

                    CurrentTemperature(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(horizontal = 16.dp)
                    )
                }
                TemperatureGraph(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun AppBar(elevation: Dp) {
    InsetAwareTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(
                        text = "Melbourne",
                        style = MaterialTheme.typography.h6,
                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = "Updated 1 minute ago",
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
                Icon(Icons.Rounded.ArrowDropDown, contentDescription = null)
            }
        },
        backgroundColor = MaterialTheme.colors.skyTop(),
        actions = { Menu() },
        elevation = elevation
    )
}

@Composable
private fun Menu() {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = null)
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        offset = DpOffset(0.dp, (-56).dp),
    ) {
        DropdownMenuItem(onClick = {
            expanded = false
        }) {
            Text(stringResource(R.string.refresh))
        }
        DropdownMenuItem(onClick = {
            expanded = false
        }) {
            Text(stringResource(R.string.about))
        }
    }
}
