package com.firassobaid.shutterapp.data.repository

import com.firassobaid.shutterapp.data.remote.ApiService
import com.firassobaid.shutterapp.domain.model.Genre
import com.firassobaid.shutterapp.domain.model.ImageConfig
import com.firassobaid.shutterapp.domain.model.Movie
import com.firassobaid.shutterapp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultMovieRepository @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {
    override suspend fun getGenres(apiKey: String): Result<List<Genre>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGenres(apiKey)
                Result.success(response.genres)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getMoviesByGenre(apiKey: String, genreId: Int): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMoviesByGenre(apiKey, genreId)
                Result.success(response.results)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getConfiguration(apiKey: String): Result<ImageConfig> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getConfiguration(apiKey)
                Result.success(response.images)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}