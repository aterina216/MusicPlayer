package com.example.musicplayer.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicplayer.data.remote.dto.Artist
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface ArtistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<Artist>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: Artist)

    @Query("DELETE FROM artists WHERE mbid = :artistId")
    suspend fun deleteArtistById(artistId: String)

    @Query("SELECT * FROM artists ORDER BY listeners")
    fun getAllArtists(): Flow<List<Artist>>

}