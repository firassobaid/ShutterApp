package com.firassobaid.shutterapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firassobaid.shutterapp.BuildConfig
import com.firassobaid.shutterapp.domain.model.Genre
import com.firassobaid.shutterapp.domain.model.Movie
import com.firassobaid.shutterapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _genres = MutableStateFlow(GenreState())
    val genresState: StateFlow<GenreState> = _genres

    private val _movies = MutableStateFlow(MovieState())
    val moviesState: StateFlow<MovieState> = _movies

    init {
        loadData()
    }

    fun loadData() {
        _genres.update { it.copy(loading = true, error = false) }
        viewModelScope.launch {
            val deferreds = listOf(     // fetch two calls at the same time
                async { loadImageConfig() },
                async { loadGenres() }
            )
            deferreds.awaitAll()        // use awaitAll to wait for both network requests
        }
    }

    private fun loadImageConfig() {
        viewModelScope.launch {
            movieRepository.getConfiguration(apiKey = BuildConfig.API_KEY)
                .onSuccess { result ->
                    val baseUrl =
                        // building the url, using one of the available poster sizes
                        "${result.secureBaseUrl}${result.posterSizes.find { it == "w500" }}"
                    _movies.update {
                        it.copy(imageBase = baseUrl)
                    }
                }
                .onFailure {
                    Log.e("MovieViewModel", "Failed to fetch configuration")
                }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            movieRepository.getGenres(apiKey = BuildConfig.API_KEY)
                .onSuccess { result ->
                    _genres.update {
                        it.copy(genres = result, loading = false)
                    }
                    onGenreSelected(result.firstOrNull()?.id ?: 0)
                }
                .onFailure {
                    _genres.update {
                        it.copy(error = true, loading = false)
                    }
                }
        }
    }

    fun onGenreSelected(genreId: Int) {
        _genres.update { it.copy(selectedGenreId = genreId) }
        loadMoviesByGenre(genreId = genreId)
    }

    private fun loadMoviesByGenre(genreId: Int) {
        _movies.update { it.copy(loading = true, movies = emptyList(), error = false) }
        viewModelScope.launch {
            movieRepository.getMoviesByGenre(
                apiKey = BuildConfig.API_KEY,
                genreId = genreId
            ).onSuccess { result ->
                _movies.update {
                    it.copy(movies = result, loading = false)
                }
            }
                .onFailure {
                    _movies.update {
                        it.copy(error = true, loading = false)
                    }
                }
        }
    }

}

data class GenreState(
    val genres: List<Genre> = emptyList(),
    val selectedGenreId: Int? = null,
    val loading: Boolean = false,
    val error: Boolean = false
)

data class MovieState(
    val movies: List<Movie> = emptyList(),
    val imageBase: String = "",
    val loading: Boolean = false,
    val error: Boolean = false
)
