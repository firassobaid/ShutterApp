package com.firassobaid.shutterapp.data.model

import com.firassobaid.shutterapp.domain.model.Movie

data class MovieResponse(
    val results: List<Movie>
)