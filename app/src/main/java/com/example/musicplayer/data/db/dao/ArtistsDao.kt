package com.example.musicplayer.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.musicplayer.data.api.models.Artist
import com.example.musicplayer.data.api.models.Artists
import com.example.musicplayer.data.db.entity.ArtistEntity

@Dao
interface ArtistsDao {

    @Query("SELECT * FROM artists")
    fun getAllArtists(): List<ArtistEntity>
}