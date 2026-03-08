package com.example.musicplayer.data.repo

import android.util.Log
import com.example.musicplayer.data.api.lastfm.API_KEY
import com.example.musicplayer.data.api.lastfm.LastFMApi
import com.example.musicplayer.data.api.models.Artist
import com.example.musicplayer.data.api.models.Artists
import com.example.musicplayer.data.db.database.ArtistDataBase

class ArtistsRepository(val lastFMApi: LastFMApi, val dataBase: ArtistDataBase) {

    suspend fun getPopularArtists(): List<Artist> {
        return try {
            val response =lastFMApi.getPopularArtists()
            Log.d("API", "Response: $response")
            response.artists.artist
        } catch (e: Exception) {
            Log.e("API", "Error: ${e.message}", e)
            emptyList()
        }
    }
}