/*
 * Copyright 2022 Afig Aliyev
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

package com.maximillianleonov.cinemax.core.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maximillianleonov.cinemax.core.database.model.movie.MovieDetailsEntity
import com.maximillianleonov.cinemax.core.database.util.Constants.Tables.MOVIE_DETAILS
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {
    @Query("SELECT * FROM $MOVIE_DETAILS WHERE id = :id")
    fun getById(id: Int): Flow<MovieDetailsEntity?>

    @Query("SELECT * FROM $MOVIE_DETAILS WHERE id IN (:ids)")
    fun getByIds(ids: List<Int>): Flow<List<MovieDetailsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDetails: MovieDetailsEntity)

    @Query("DELETE FROM $MOVIE_DETAILS WHERE id = :id")
    suspend fun deleteById(id: Int)
}
