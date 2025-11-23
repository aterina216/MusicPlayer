package com.example.musicplayer.data.repository

import android.util.Log
import com.example.musicplayer.data.db.ArtistDao
import com.example.musicplayer.data.remote.api.DeezerApi
import com.example.musicplayer.data.remote.api.LastFmApi
import com.example.musicplayer.data.remote.dto.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtistRepositiry @Inject constructor (
    private val api: LastFmApi, private val dao: ArtistDao,
    private val deezerApi: DeezerApi
) {

    private val _imageCache = mutableMapOf<String, String>()
    val imageCache: Map<String, String> get() = _imageCache

    suspend fun getTopArtists(): List<Artist> {
        try {
            Log.d("Repository", "=== STARTING ARTISTS LOAD ===")
            val lastFmResponse = api.getTopArtists()
            if (!lastFmResponse.isSuccessful) {

                Log.e("Repository", "LastFM API error: ${lastFmResponse.code()}")
                return emptyList()
            }
            val artists = lastFmResponse.body()?.artists?.artist ?: emptyList()
            Log.d("Repository", "Loaded ${artists.size} artists from LastFM")

            coroutineScope {
                artists.map { artist ->
                    async {
                        Log.d("Repository", "Loading image for: ${artist.name}")
                        val imageUrl = getImages(artist.name)
                        if (!imageUrl.isNullOrEmpty()) {
                            _imageCache[artist.name] = imageUrl
                            Log.d("Repository", "✅ Cached image for ${artist.name}: ${imageUrl.take(50)}...")
                        }
                        else {
                            Log.w("Repository", "❌ No image found for ${artist.name}")
                        }
                    }
                }.awaitAll() // Ждем завершения всех асинхронных задач
            }

            Log.d("Repository", "=== FINISHED LOADING ===")
            Log.d("Repository", "Cache contains ${_imageCache.size} images")
            _imageCache.forEach { (name, url) ->
                Log.d("Repository", "Cache entry: $name -> ${url.take(30)}...")
            }

            return artists
        } catch (e: Exception) {
            print("${e.message}")
            return emptyList()
        }
    }

    suspend fun getImages(artistName: String): String? {
        return try {
            Log.d("Deezer", "Searching Deezer for: $artistName")
            val response = deezerApi.searchArtist(artistName)

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("Deezer", "Deezer response successful, data count: ${body?.data?.size ?: 0}")

                val deezerArtist = body?.data?.firstOrNull()
                val imageUrl = deezerArtist?.picture_big ?: deezerArtist?.picture_medium

                Log.d("Deezer", "Image URL for $artistName: $imageUrl")
                imageUrl
            } else {
                Log.e("Deezer", "Deezer API error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("Deezer", "❌ ERROR loading image for $artistName: ${e.message}", e)
            null
        }
    }
}