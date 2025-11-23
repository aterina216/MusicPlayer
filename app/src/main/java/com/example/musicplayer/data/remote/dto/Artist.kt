package com.example.musicplayer.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicplayer.data.remote.dto.Image

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey
    val mbid: String,
    val image: List<Image>,
    val listeners: String,
    val name: String,
    val playcount: String,
    val streamable: String,
    val url: String
)