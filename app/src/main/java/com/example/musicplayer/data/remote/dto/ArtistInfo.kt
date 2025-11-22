package com.example.musicplayer.data.remote.dto

import com.example.musicplayer.data.remote.dto.Image
import com.example.musicplayer.data.remote.dto.Stats

data class ArtistInfo (
    val name: String,
    val image: List<Image>,
    val stats: Stats,
    val bio: ArtistBio?
)