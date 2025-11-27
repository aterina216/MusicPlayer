package com.example.musicplayer.data.repository

import android.util.Log
import com.example.musicplayer.data.db.ArtistDao
import com.example.musicplayer.data.remote.api.DeezerApi
import com.example.musicplayer.data.remote.api.LastFmApi
import com.example.musicplayer.data.remote.dto.Artist
import com.example.musicplayer.data.remote.dto.ArtistImage
import com.example.musicplayer.data.db.ArtistImageDao
import com.example.musicplayer.data.remote.dto.ArtistDetails
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val api: LastFmApi,
    private val dao: ArtistDao,
    private val imageDao: ArtistImageDao,
    private val deezerApi: DeezerApi
) {
    private val _imageCache = mutableMapOf<String, String>()
    val imageCache: Map<String, String> get() = _imageCache

    suspend fun getTopArtists(): List<Artist> {
        Log.d("Repository", "🎯 ENTERING getTopArtists()")

        // Сначала восстанавливаем кэш изображений из БД
        restoreImageCacheFromDatabase()

        // Потом проверяем артистов
        val cachedArtists = dao.getAllArtists().first()
        Log.d("Repository", "📊 cachedArtists from DB: ${cachedArtists.size}")

        if (cachedArtists.isNotEmpty()) {
            Log.d("Repository", "🔄 USING CACHED ARTISTS from DB")
            Log.d("Repository", "🎯 RETURNING CACHED: ${cachedArtists.size} artists, imageCache: ${_imageCache.size}")
            return cachedArtists
        }

        return loadArtistsFromNetwork()
    }

    private suspend fun restoreImageCacheFromDatabase() {
        try {
            val savedImages = imageDao.getAllImages()
            savedImages.forEach { image ->
                _imageCache[image.artistName] = image.imageUrl
            }
            Log.d("Repository", "🔄 Restored ${savedImages.size} images from database")
        } catch (e: Exception) {
            Log.e("Repository", "❌ ERROR restoring images: ${e.message}")
        }
    }

    private suspend fun loadArtistsFromNetwork(): List<Artist> {
        return try {
            Log.d("Repository", "=== STARTING NETWORK LOAD ===")

            val lastFmResponse = api.getTopArtists()
            if (!lastFmResponse.isSuccessful) {
                Log.e("Repository", "LastFM error: ${lastFmResponse.code()}")
                return emptyList()
            }

            val artists = lastFmResponse.body()?.artists?.artist ?: emptyList()
            Log.d("Repository", "Loaded ${artists.size} artists from LastFM")

            // Загружаем и сохраняем изображения
            loadAndSaveImages(artists)

            // Сохраняем артистов
            dao.insertArtists(artists)
            Log.d("Repository", "✅ Saved ${artists.size} artists to DB")

            artists
        } catch (e: Exception) {
            Log.e("Repository", "❌ NETWORK ERROR: ${e.message}")
            emptyList()
        }
    }

    private suspend fun loadAndSaveImages(artists: List<Artist>) {
        artists.forEach { artist ->
            try {
                val imageUrl = getImages(artist.name)
                if (!imageUrl.isNullOrEmpty()) {
                    _imageCache[artist.name] = imageUrl

                    // Сохраняем в отдельную таблицу
                    val artistImage = ArtistImage(artist.name, imageUrl)
                    imageDao.insertImage(artistImage)

                    Log.d("Repository", "✅ Saved image for ${artist.name}")
                }
            } catch (e: Exception) {
                Log.e("Repository", "❌ Error saving image for ${artist.name}: ${e.message}")
            }
        }
    }

    suspend fun getImages(artistName: String): String? {
        return try {
            val response = deezerApi.searchArtist(artistName)
            if (response.isSuccessful) {
                val deezerArtist = response.body()?.data?.firstOrNull()
                deezerArtist?.picture_big ?: deezerArtist?.picture_medium
            } else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getArtistById(artistId: String): Artist? {
        return try {
            val artist = dao.getArtistById(artistId)
            Log.d("Repository", "🔍 Found artist by ID '$artistId': ${artist?.name}")
            artist
        }
        catch (e: Exception){
            print("${e.message}")
            return null
        }
    }

    suspend fun getArtistDetails(artistId: String): ArtistDetails? {
        return try {
            val artist = dao.getArtistById(artistId)
            if (artist == null) {
                Log.d("Repository", "❌ Artist not found in DB: $artistId")
                return null
            }
            val bio = getArtistBio(artist.name)
            val highResImage = getHighResImage(artist.name)

            Log.d("Repository", "✅ Loaded full details for: ${artist.name}")

            ArtistDetails(
                artist = artist,
                bio = bio,
                highResImageUrl = highResImage
            )
        }
        catch (e: Exception) {
            print("${e.message}")
            null
        }
    }

    suspend fun getArtistBio(artistName: String): String?{
        return try {
            val response = api.getArtistInfo(artist = artistName)
            if (response.isSuccessful) {
                val bio = response.body()?.artist?.bio?.summary
                Log.d("Repository", "📖 Loaded bio for $artistName: ${bio?.take(50)}...")
                bio?.replace(Regex("<[^>]*>"), "")?.trim()
            }
            else {
                Log.d("Repository", "❌ Bio API error: ${response.code()}")
                null
            }
        }
        catch (e: Exception) {
            Log.e("Repository", "❌ Error getting artist bio: ${e.message}")
            null
        }
    }

    private suspend fun getHighResImage(artistName: String): String? {
        return try {
            val response = deezerApi.searchArtist(artistName)
            if(response.isSuccessful){
                val deezerArtist = response.body()?.data?.firstOrNull()
                deezerArtist?.picture_big ?: deezerArtist?.picture_medium
            }
            else null
        }
        catch (e: Exception) {
            null
        }
    }

    suspend fun searchArtists(query: String): List<Artist> {
        return try {
            val results = dao.searchArtists(query)
            Log.d("Repository", "🔍 Search for '$query' found ${results.size} results")
            results
        }
        catch (e: Exception) {
            Log.e("Repository", "❌ Error searching artists: ${e.message}")
            emptyList()
        }
    }
}