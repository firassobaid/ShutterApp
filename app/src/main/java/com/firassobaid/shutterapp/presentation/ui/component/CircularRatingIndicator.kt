package com.firassobaid.shutterapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firassobaid.shutterapp.R

@Composable
fun CircularIndicator(rating: Float, maxRating: Float = 10.0f, modifier: Modifier) {
    val progress = rating / maxRating
    //set color based on progress
    val ratingColor = when {
        progress < 0.2f -> Color.Red
        progress < 0.4f -> colorResource(R.color.shutter_orange_red)
        progress < 0.6f -> Color.Yellow
        progress < 0.8f -> colorResource(R.color.shutter_light_green)
        else -> Color.Green
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(colorResource(R.color.shutter_dark_gray))
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = { progress },
            color = ratingColor,
            trackColor = Color.Gray,
            strokeWidth = 4.dp
        )
        Text(
            text = "${rating.toInt()}/${maxRating.toInt()}",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}