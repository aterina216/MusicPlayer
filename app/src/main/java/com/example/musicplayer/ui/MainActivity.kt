package com.example.musicplayer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicplayer.MusicApp
import com.example.musicplayer.data.repository.ArtistRepositiry
import com.example.musicplayer.ui.viewmodel.ArtistViewmodel
import com.example.musicplayer.ui.artistList.Artistscreen
import com.example.musicplayer.ui.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewmodel: ArtistViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        (application as MusicApp).appComponent.inject(this)

        enableEdgeToEdge()
        setContent {
            MaterialTheme {

                val viewmodel: ArtistViewmodel = viewmodel

                val artists by viewmodel._artists.collectAsState()

                Artistscreen(artists, viewmodel)
            }
        }
    }
}