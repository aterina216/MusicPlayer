package com.example.musicplayer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.remote.dto.Artist
import com.example.musicplayer.data.remote.dto.ArtistDetails
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

    private val _selectedArtist = MutableStateFlow<ArtistDetails?>(null)
    val selectedArtist: StateFlow<ArtistDetails?> = _selectedArtist.asStateFlow()

    private val _isLoadingDetails = MutableStateFlow(false)
    val isLoadingDetails: StateFlow<Boolean> = _isLoadingDetails.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

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

    fun loadArtistDetails(artistId: String) {
        viewModelScope.launch {
            _isLoadingDetails.value = true
            _errorMessage.value = null

            Log.d("ViewModel", "🎯 Loading details for artist: $artistId")

            try {
                val artistDetails = repository.getArtistDetails(artistId)
                if(artistDetails != null) {
                    _selectedArtist.value = artistDetails
                    Log.d("ViewModel", "✅ Successfully loaded details for: ${artistDetails.artist.name}")
                }
                else {
                    _errorMessage.value = "Артист не найден"
                    Log.d("ViewModel", "❌ Artist details not found")
                }
            }
            catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки: ${e.message}"
                Log.e("ViewModel", "❌ Error loading artist details: ${e.message}", e)
            } finally {
                _isLoadingDetails.value = false
            }
        }
    }

    fun clearSelectedArtist() {
        _selectedArtist.value = null
        _errorMessage.value = null
    }

    fun searchArtists(query: String) {
        viewModelScope.launch {
            try {
                val results = repository.searchArtists(query)
                Log.d("ViewModel", "🔍 Search completed: ${results.size} results")
            }
            catch (e: Exception) {
                Log.e("ViewModel", "❌ Search error: ${e.message}")
            }
        }
    }
}