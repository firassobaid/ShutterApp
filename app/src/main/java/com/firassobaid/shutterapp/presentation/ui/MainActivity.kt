package com.firassobaid.shutterapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.firassobaid.shutterapp.presentation.viewmodel.MovieViewModel
import com.firassobaid.shutterapp.core.theme.ShutterAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel = hiltViewModel<MovieViewModel>()
            val genreState = viewModel.genresState.collectAsStateWithLifecycle()
            val movieState = viewModel.moviesState.collectAsStateWithLifecycle()

            ShutterAppTheme {
                MoviesScreen(
                    genreState = genreState.value,
                    movieState = movieState.value,
                    onGenreClick = { genreId ->
                        viewModel.onGenreSelected(genreId)
                    },
                    onGenreRetry = {
                        viewModel.loadData()
                    },
                    onMovieRetry = { genreId ->
                        viewModel.onGenreSelected(genreId)
                    }
                )
            }
        }
    }
}
