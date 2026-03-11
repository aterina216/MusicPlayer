package com.example.musicplayer.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicplayer.data.api.models.Image

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var imageUrl: String? = null,
    val listeners: String,
    val mbid: String?,
    val name: String,
    val playcount: String,
    val streamable: String,
    val url: String
)