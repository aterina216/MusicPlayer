package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.data.repo.ArtistsRepository

class ArtistViewModelFactory(val repository: ArtistsRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArtistViewmodel(repository) as T
    }
}