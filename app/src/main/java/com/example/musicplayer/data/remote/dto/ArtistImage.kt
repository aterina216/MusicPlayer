package com.example.musicplayer.data.remote.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "artist_images",
    primaryKeys = ["artist_name"],  // ✅ используй имя колонки
    indices = [Index(value = ["artist_name"], unique = true)]  // ✅ используй имя колонки
)
data class ArtistImage(
    @ColumnInfo(name = "artist_name")
    val artistName: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String
)