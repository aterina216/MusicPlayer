package com.example.musicplayer.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.musicplayer.data.api.models.Artist
import com.example.musicplayer.data.api.models.Artists
import com.example.musicplayer.data.db.entity.ArtistEntity

@Dao
interface ArtistsDao {

    @Query("SELECT * FROM artists")
    suspend fun getAllArtists(): List<ArtistEntity>

    @Insert
    suspend fun insertAllArtists(artists: List<ArtistEntity>)

    @Insert
    suspend fun insertArtist(artist: ArtistEntity)

    @Query("SELECT name FROM artists")
    suspend fun getArtistNames(): List<String>
}