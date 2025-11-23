package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.data.repository.ArtistRepositiry
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
   private val repository: ArtistRepositiry
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtistViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}