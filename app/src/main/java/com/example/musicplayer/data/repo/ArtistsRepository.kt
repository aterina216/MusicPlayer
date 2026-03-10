package com.example.musicplayer.data.repo

import android.util.Log
import com.example.musicplayer.data.Mapper.toEntity
import com.example.musicplayer.data.api.lastfm.API_KEY
import com.example.musicplayer.data.api.lastfm.LastFMApi
import com.example.musicplayer.data.api.models.Artist
import com.example.musicplayer.data.api.models.Artists
import com.example.musicplayer.data.db.database.ArtistDataBase
import com.example.musicplayer.data.db.entity.ArtistEntity

class ArtistsRepository(val lastFMApi: LastFMApi, val dataBase: ArtistDataBase) {

    suspend fun getPopularArtists(): List<ArtistEntity> {
        return try {
            val response =lastFMApi.getPopularArtists()
            Log.d("API", "Response: $response")
            val names = dataBase.artistDao().getArtistNames()
            val artists = response.artists.artist.map {
                it.toEntity()
            }
            artists.forEach { if (!names.contains(it.name)) dataBase.artistDao().insertArtist(it) }
            dataBase.artistDao().getAllArtists()

        } catch (e: Exception) {
            Log.e("API", "Error: ${e.message}", e)
            emptyList()
        }
    }
}