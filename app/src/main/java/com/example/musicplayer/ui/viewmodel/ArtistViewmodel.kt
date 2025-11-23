package com.example.musicplayer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.remote.api.RetrofitClient
import com.example.musicplayer.data.remote.dto.Artist
import com.example.musicplayer.data.repository.ArtistRepositiry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArtistViewmodel(val repositiry: ArtistRepositiry): ViewModel() {

    private var artists = MutableStateFlow<List<Artist>>(emptyList())
    val _artists: StateFlow<List<Artist>> = artists.asStateFlow()

    private val _imageCache = mutableStateMapOf<String, String>()
    val imageCache: Map<String, String> get() = _imageCache

    init {
        loadTopArtists()
    }

    fun loadTopArtists() {
        viewModelScope.launch {
            val artistsList = repositiry.getTopArtists()
            artists.value = artistsList
        }
    }

    fun loadArtistImageFromDeezer(artistName: String){

        viewModelScope.launch {
            try {
                val response = RetrofitClient.deezerApi.searchArtist(artistName)
                if(response.isSuccessful){
                    val deezerArtist = response.body()?.data?.firstOrNull()
                    val imageUrl = deezerArtist?.picture_big ?: deezerArtist?.picture_medium

                    if(!imageUrl.isNullOrEmpty()) {
                        _imageCache[artistName] = imageUrl
                        Log.d("Deezer", "Loaded image for $artistName")
                    }
                }
            }
            catch (e: Exception) {
                Log.e("Deezer", "Error loading image for $artistName: ${e.message}")
            }
        }
    }
}