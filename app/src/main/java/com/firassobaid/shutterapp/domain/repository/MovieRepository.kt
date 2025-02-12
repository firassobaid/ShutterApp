package com.firassobaid.shutterapp.domain.repository

import com.firassobaid.shutterapp.domain.model.Genre
import com.firassobaid.shutterapp.domain.model.ImageConfig
import com.firassobaid.shutterapp.domain.model.Movie

interface MovieRepository {
    suspend fun getGenres(apiKey: String): Result<List<Genre>>
    suspend fun getMoviesByGenre(apiKey: String, genreId: Int): Result<List<Movie>>
    suspend fun getConfiguration(apiKey: String): Result<ImageConfig>
}