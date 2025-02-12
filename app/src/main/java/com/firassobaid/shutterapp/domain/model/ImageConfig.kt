package com.firassobaid.shutterapp.domain.model

import com.google.gson.annotations.SerializedName

data class ImageConfig(
    @SerializedName("base_url")
    val baseUrl: String,
    @SerializedName("secure_base_url")
    val secureBaseUrl: String,
    @SerializedName("poster_sizes")
    val posterSizes: List<String>
)
