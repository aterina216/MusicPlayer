package com.example.musicplayer.data.api.models

data class DeezerArtist(
    val `data`: List<Data>,
    val next: String,
    val total: Int
)