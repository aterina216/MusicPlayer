package com.example.musicplayer.data.remote.dto

import com.example.musicplayer.data.remote.dto.Attr

data class Artists(
    val attr: Attr,
    val artist: List<Artist>
)