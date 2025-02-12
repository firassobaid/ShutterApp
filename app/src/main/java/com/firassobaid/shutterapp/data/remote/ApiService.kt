package com.firassobaid.shutterapp.data.remote

import com.firassobaid.shutterapp.data.model.ConfigurationResponse
import com.firassobaid.shutterapp.data.model.GenreResponse
import com.firassobaid.shutterapp.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String
    ): GenreResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int
    ): MovieResponse

    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String,): ConfigurationResponse
}
