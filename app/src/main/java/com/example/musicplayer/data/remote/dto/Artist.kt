package com.example.musicplayer.data.remote.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.musicplayer.data.remote.dto.Image

@Entity(tableName = "artists",
    indices = [Index(value = ["name"], unique = true)])
data class Artist(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val image: List<Image>,
    val listeners: String,
    val name: String,
    val mbid: String? = null,
    val playcount: String,
    val streamable: String,
    val url: String,

    @ColumnInfo(name = "deezer_image_url")
    val deezerImageUrl: String? = null,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)