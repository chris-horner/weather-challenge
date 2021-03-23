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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
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

private data class HomeUiState(
    val locationName: String,
    val lastUpdated: String,
)

private val DummyUiState = HomeUiState(
    locationName = "Melbourne",
    lastUpdated = "Updated 1 minute ago",
)

@Composable
private fun HomeUi(state: HomeUiState = DummyUiState) {

    val scrollState = rememberScrollState()
    val toolbarElevation = animateDpAsState(targetValue = if (scrollState.value > 0) 4.dp else 0.dp)

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AppBar(
                    title = state.locationName,
                    subtitle = state.lastUpdated,
                    elevation = toolbarElevation.value
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp),
            ) {
                Box(modifier = Modifier.height(272.dp)) {

                    // Render a gradient for the sky behind the tree image.
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    0f to MaterialTheme.colors.skyTop,
                                    1f to MaterialTheme.colors.skyBottom
                                )
                            )
                            .fillMaxWidth()
                            .height(256.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.tree_foreground),
                        contentDescription = stringResource(R.string.treeDesc),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.BottomStart,
                        modifier = Modifier.fillMaxHeight()
                    )

                    FloatingLeaves(
                        modifier = Modifier
                            .fillMaxSize()
                            .clipToBounds()
                    )

                    CurrentTemperature(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(horizontal = 16.dp)
                    )
                }

                Conditions(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(R.drawable.graph_top),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 2.dp)
                )

                TemperatureGraph(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primaryVariant)
                )

                Image(
                    painter = painterResource(R.drawable.graph_bottom),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-2).dp),
                )

                UpcomingForecasts(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun AppBar(title: String, subtitle: String, elevation: Dp) {
    InsetAwareTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h5,
                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
                Icon(Icons.Rounded.ArrowDropDown, contentDescription = null)
            }
        },
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
        DropdownMenuItem(
            onClick = {
                expanded = false
            }
        ) {
            Text(stringResource(R.string.refresh))
        }
        DropdownMenuItem(
            onClick = {
                expanded = false
            }
        ) {
            Text(stringResource(R.string.about))
        }
    }
}
