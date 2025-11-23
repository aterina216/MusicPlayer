package com.example.musicplayer.data.repository

import com.example.musicplayer.data.db.ArtistDao
import com.example.musicplayer.data.remote.api.DeezerApi
import com.example.musicplayer.data.remote.api.LastFmApi
import com.example.musicplayer.data.remote.dto.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow

class ArtistRepositiry(private val api: LastFmApi, private val dao: ArtistDao,
    private val deezerApi: DeezerApi) {

    private val _imageCache = mutableMapOf<String, String>()
    val imageCache: Map<String, String> get() = _imageCache

    suspend fun getTopArtists(): List<Artist> {
        try {
            val lastFmResponse = api.getTopArtists()
            if(!lastFmResponse.isSuccessful){
                return emptyList()
            }
            val artists = lastFmResponse.body()?.artists?.artist ?: emptyList()
            val imageJobs = artists.map { artist ->
                CoroutineScope(Dispatchers.IO).async {
                    val imageUrl = getImages(artist.name)
                    if (!imageUrl.isNullOrEmpty()) {
                        _imageCache[artist.name] = imageUrl
                    }
                }
            }

            // Ждем завершения всех загрузок
            imageJobs.awaitAll()

            return artists
        }
        catch (e: Exception){
            print("${e.message}")
            return emptyList()
        }
    }

    suspend fun getImages(artistName: String): String?{

      return  try {
            val response = deezerApi.searchArtist(artistName)
            if (response.isSuccessful) {
                val deezerArtist = response.body()?.data?.firstOrNull()
                deezerArtist?.picture_big ?: deezerArtist?.picture_medium
            }
            else {
                null
            }
        }
        catch (e: Exception) {
            print("${e.message}")
            null
        }
    }
}