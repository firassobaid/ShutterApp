package com.firassobaid.shutterapp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.firassobaid.shutterapp.R
import com.firassobaid.shutterapp.domain.model.Genre
import com.firassobaid.shutterapp.presentation.ui.component.ErrorComponent
import com.firassobaid.shutterapp.presentation.ui.component.LoadingComponent
import com.firassobaid.shutterapp.presentation.ui.component.MovieItem
import com.firassobaid.shutterapp.presentation.ui.component.ShutterTopBar
import com.firassobaid.shutterapp.presentation.viewmodel.GenreState
import com.firassobaid.shutterapp.presentation.viewmodel.MovieState

@Composable
fun MoviesScreen(
    genreState: GenreState,
    movieState: MovieState,
    onGenreClick: (Int) -> Unit,
    onGenreRetry: () -> Unit = {},
    onMovieRetry: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ShutterTopBar(
                title = stringResource(R.string.genres_top_bar_title)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.shutter_dark_gray))
                .padding(innerPadding),
        ) {
            when {
                genreState.loading -> {
                    LoadingComponent()
                }

                genreState.genres.isNotEmpty() -> {
                    Column {
                        GenreContent(
                            genres = genreState.genres,
                            selectedGenreId = genreState.selectedGenreId ?: 0,
                            onGenreClick = onGenreClick
                        )
                        MoviesContent(
                            movieState = movieState,
                            onMovieRetry = { onMovieRetry(genreState.selectedGenreId ?: 0) }
                        )
                    }
                }

                genreState.error -> {
                    ErrorComponent(
                        text = stringResource(R.string.genres_error),
                        onRetry = onGenreRetry
                    )
                }
            }
        }
    }
}

@Composable
fun GenreContent(
    genres: List<Genre>,
    selectedGenreId: Int,
    onGenreClick: (Int) -> Unit
) {
    val selectedTabIndex = genres.indexOfFirst { it.id == selectedGenreId }

    ScrollableTabRow(
        modifier = Modifier
            .background(colorResource(R.color.shutter_dark_gray)),
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = colorResource(R.color.shutter_light_gray)
            )
        }
    ) {
        genres.forEach { genre ->
            Tab(
                modifier = Modifier.background(
                    colorResource(R.color.shutter_dark_gray)
                ),
                selected = selectedGenreId == genre.id,
                onClick = {
                    onGenreClick(genre.id)
                }
            ) {
                Text(
                    color = colorResource(R.color.shutter_light_gray),
                    text = genre.name,
                    modifier = Modifier.padding(16.dp),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun MoviesContent(
    movieState: MovieState,
    onMovieRetry: () -> Unit
) {
    when {
        movieState.movies.isNotEmpty() -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(movieState.movies) { movie ->
                    MovieItem(movie = movie, imageBaseUrl = movieState.imageBase)
                }
            }
        }

        movieState.error -> {
            ErrorComponent(
                text = stringResource(R.string.movies_error),
                onRetry = onMovieRetry
            )
        }

        movieState.loading -> {
            LoadingComponent()
        }
    }
}
