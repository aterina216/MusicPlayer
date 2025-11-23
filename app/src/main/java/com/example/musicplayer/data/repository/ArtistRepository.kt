package com.example.musicplayer.data.repository

import android.util.Log
import com.example.musicplayer.data.db.ArtistDao
import com.example.musicplayer.data.remote.api.DeezerApi
import com.example.musicplayer.data.remote.api.LastFmApi
import com.example.musicplayer.data.remote.dto.Artist
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ArtistRepository @Inject constructor (
    private val api: LastFmApi, private val dao: ArtistDao,
    private val deezerApi: DeezerApi
) {

    private val _imageCache = mutableMapOf<String, String>()
    val imageCache: Map<String, String> get() = _imageCache

    suspend fun getTopArtists(): List<Artist> {

        Log.d("Repository", "🎯 ENTERING getTopArtists()")

        val cachedArtists = dao.getAllArtists().first()

        Log.d("Repository", "📊 cachedArtists from DB: ${cachedArtists?.size ?: 0}")

        if (cachedArtists!=null && cachedArtists.isNotEmpty()){

            Log.d("Repository", "🔄 USING CACHED ARTISTS from DB")

            cachedArtists.forEach { artist ->
                Log.d("Repository", "🔍 Processing cached artist: ${artist.name}")
                artist.image?.forEach { img ->
                    Log.d("Repository", "   Image: size=${img.size}, url=${img.text?.take(30)}...")
                }

                artist.image?.find { it.size == "extralarge" }?.text?.let { url ->
                    if(url.isNotBlank()) {
                        _imageCache[artist.name] = url
                        Log.d("Repository", "✅ Restored image from cache for ${artist.name}: ${url.take(30)}...")
                    }
                }
            }

            Log.d("Repository", "🎯 RETURNING CACHED: ${cachedArtists.size} artists, imageCache: ${_imageCache.size}")

            return cachedArtists
        }

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

            try {
                dao.insertArtists(artists)
                Log.d("Repository", "✅ DATABASE INSERT SUCCESS")
            } catch (e: Exception) {
                Log.e("Repository", "❌ DATABASE INSERT FAILED: ${e.message}")
                // Если падает здесь - просто возвращаем artists без сохранения
                return artists
            }

            Log.d("Repository", "AFTER DATABASE INSERT - returning artists")
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