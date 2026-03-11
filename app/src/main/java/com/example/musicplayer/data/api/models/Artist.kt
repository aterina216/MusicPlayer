package com.example.musicplayer.data.api.models

data class Artist(
    var image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val streamable: String,
    val url: String
)