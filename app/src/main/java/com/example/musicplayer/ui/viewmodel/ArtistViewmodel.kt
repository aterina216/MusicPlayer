package com.example.musicplayer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.remote.dto.Artist
import com.example.musicplayer.data.repository.ArtistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtistViewmodel @Inject constructor (val repository: ArtistRepository): ViewModel() {

    private var artists = MutableStateFlow<List<Artist>>(emptyList())
    val _artists: StateFlow<List<Artist>> = artists.asStateFlow()

    private val _imageCache = MutableStateFlow<Map<String, String>>(emptyMap())
    val imageCache: StateFlow<Map<String, String>> = _imageCache.asStateFlow()

    init {
        Log.d("ViewModel", "🚀 ViewModel created")
        loadTopArtists()
    }

    fun loadTopArtists() {
        viewModelScope.launch {

            Log.d("ViewModel", "Starting to load artists...")

            try {
                val artistsList = repository.getTopArtists()
                Log.d("ViewModel", "📊 Repository returned: ${artistsList.size} artists")

                artists.value = artistsList

                _imageCache.value = repository.imageCache.toMap()

                Log.d("ViewModel", "Artists loaded: ${artistsList.size}")
                artistsList.take(3).forEach { artist ->
                    val cachedImage = repository.imageCache[artist.name]
                    Log.d("ViewModel", "Cache check for ${artist.name}: $cachedImage")
                }
            }
            catch (e: Exception) {
                Log.e("ViewModel", "Error loading artists: ${e.message}", e)
            }

        }
    }
}