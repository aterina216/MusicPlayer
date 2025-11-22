package com.example.musicplayer.data.remote.dto

import com.example.musicplayer.data.remote.dto.Image

data class Artist(
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val streamable: String,
    val url: String
)