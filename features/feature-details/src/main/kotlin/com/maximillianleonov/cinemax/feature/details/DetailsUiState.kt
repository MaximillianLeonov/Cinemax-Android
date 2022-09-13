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

package com.maximillianleonov.cinemax.feature.details

import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.model.ErrorMessage
import com.maximillianleonov.cinemax.core.ui.model.MovieDetails
import com.maximillianleonov.cinemax.core.ui.model.TvShowDetails
import com.maximillianleonov.cinemax.core.ui.model.UserMessage

data class DetailsUiState(
    val contentType: ContentType.Details,
    val movie: MovieDetails? = null,
    val tvShow: TvShowDetails? = null,
    val isLoading: Boolean = false,
    val error: ErrorMessage? = null,
    val userMessage: UserMessage? = null
) {
    val isError: Boolean get() = error != null
    val isOfflineModeAvailable: Boolean
        get() = movie != null || tvShow != null

    fun requireError() = checkNotNull(error)
}
