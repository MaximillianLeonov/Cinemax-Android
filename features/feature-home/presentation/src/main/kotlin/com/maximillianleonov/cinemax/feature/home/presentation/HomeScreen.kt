/*
 * Copyright 2022 Maximillian Leonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maximillianleonov.cinemax.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.common.ContentType
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxSwipeRefresh
import com.maximillianleonov.cinemax.core.presentation.components.MoviesAndTvShowsContainer
import com.maximillianleonov.cinemax.core.presentation.components.SnackbarErrorHandler
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.home.presentation.components.UpcomingMoviesContainer

@Composable
internal fun HomeRoute(
    onSeeAllClick: (ContentType.List) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState,
        modifier = modifier,
        onSeeAllClick = onSeeAllClick,
        onRefresh = { viewModel.onEvent(HomeEvent.Refresh) },
        onDismiss = { viewModel.onEvent(HomeEvent.ClearError) }
    )
}

@Suppress("ReusedModifierInstance")
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onSeeAllClick: (ContentType.List) -> Unit,
    onRefresh: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isLoading
    )
) {
    SnackbarErrorHandler(
        errorMessage = uiState.error,
        onRetry = onRefresh,
        onDismiss = onDismiss
    )
    CinemaxSwipeRefresh(
        modifier = modifier.windowInsetsPadding(
            WindowInsets.safeDrawing.only(
                WindowInsetsSides.Horizontal + WindowInsetsSides.Top
            )
        ),
        swipeRefreshState = swipeRefreshState,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag(tag = ContentTestTag),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium),
            contentPadding = PaddingValues(vertical = CinemaxTheme.spacing.extraMedium)
        ) {
            item {
                UpcomingMoviesContainer(
                    modifier = Modifier.testTag(tag = UpcomingTestTag),
                    movies = uiState.movies[ContentType.Main.UpcomingMovies].orEmpty(),
                    onSeeAllClick = { onSeeAllClick(ContentType.List.Upcoming) }
                )
            }
            item {
                MoviesAndTvShowsContainer(
                    titleResourceId = R.string.top_rated,
                    onSeeAllClick = { onSeeAllClick(ContentType.List.TopRated) },
                    movies = uiState.movies[ContentType.Main.TopRatedMovies].orEmpty(),
                    tvShows = uiState.tvShows[ContentType.Main.TopRatedTvShows].orEmpty()
                )
            }
            item {
                MoviesAndTvShowsContainer(
                    titleResourceId = R.string.most_popular,
                    onSeeAllClick = { onSeeAllClick(ContentType.List.Popular) },
                    movies = uiState.movies[ContentType.Main.PopularMovies].orEmpty(),
                    tvShows = uiState.tvShows[ContentType.Main.PopularTvShows].orEmpty()
                )
            }
            item {
                MoviesAndTvShowsContainer(
                    modifier = Modifier.testTag(tag = NowPlayingTestTag),
                    titleResourceId = R.string.now_playing,
                    onSeeAllClick = { onSeeAllClick(ContentType.List.NowPlaying) },
                    movies = uiState.movies[ContentType.Main.NowPlayingMovies].orEmpty(),
                    tvShows = uiState.tvShows[ContentType.Main.NowPlayingTvShows].orEmpty()
                )
            }
        }
    }
}

private const val TestTag = "home"
private const val ContentTestTag = "$TestTag:content"
private const val UpcomingTestTag = "$TestTag:upcoming"
private const val NowPlayingTestTag = "$TestTag:nowplaying"
