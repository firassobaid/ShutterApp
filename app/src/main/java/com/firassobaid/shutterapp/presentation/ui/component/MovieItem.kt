package com.firassobaid.shutterapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.firassobaid.shutterapp.R
import com.firassobaid.shutterapp.domain.model.Movie

@Composable
fun MovieItem(movie: Movie, imageBaseUrl: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Transparent)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$imageBaseUrl${movie.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image)
            )

            CircularIndicator(
                rating = movie.voteAverage.toFloat(),
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
        Text(
            text = movie.title,
            color = Color.White
        )
        Text(
            text = "(${movie.releaseYear})",
            color = colorResource(R.color.shutter_light_gray)
        )
    }
}
