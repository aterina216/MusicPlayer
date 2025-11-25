package com.example.musicplayer.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicplayer.data.remote.dto.ArtistImage

@Dao
interface ArtistImageDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertImage(image: ArtistImage)

    @Query("SELECT * FROM artist_images")
    suspend fun getAllImages(): List<ArtistImage>

    @Query("SELECT image_url FROM artist_images WHERE artist_name = :artistName")
    suspend fun getImageForArtist(artistName: String): String?
}