package com.example.musicplayer.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicplayer.data.remote.dto.Image

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val image: List<Image>,
    val listeners: String,
    val name: String,
    val mbid: String? = null,
    val playcount: String,
    val streamable: String,
    val url: String
)