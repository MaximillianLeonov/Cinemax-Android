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

package com.maximillianleonov.cinemax.feature.list

import androidx.paging.PagingData
import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.common.State
import com.maximillianleonov.cinemax.core.ui.model.ErrorMessage
import com.maximillianleonov.cinemax.core.ui.model.Movie
import com.maximillianleonov.cinemax.core.ui.model.TvShow
import kotlinx.coroutines.flow.Flow

data class ListUiState(
    val contentType: ContentType.List,
    val movies: Flow<PagingData<Movie>>,
    val tvShows: Flow<PagingData<TvShow>>,
    val isRetry: Boolean = false,
    val error: ErrorMessage? = null
) : State {
    val isError: Boolean get() = error != null

    fun requireError() = checkNotNull(error)
}
