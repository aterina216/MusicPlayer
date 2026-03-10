package com.example.musicplayer.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.api.models.Artist
import com.example.musicplayer.data.api.models.Artists
import com.example.musicplayer.data.db.entity.ArtistEntity
import com.example.musicplayer.data.repo.ArtistsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistViewmodel(val repository: ArtistsRepository): ViewModel() {

    private var _popularArtists = MutableStateFlow<List<ArtistEntity>>(emptyList())
    val popularArtists: StateFlow<List<ArtistEntity>> = _popularArtists

    init {
        Log.d("TAG", "ArtistViewmodel: init")
        getPopularArtists()
    }

    fun getPopularArtists() {
        viewModelScope.launch {
            try {
                _popularArtists.value = repository.getPopularArtists()
                Log.e("TAG", "getPopularArtists: ${_popularArtists.value}")
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}