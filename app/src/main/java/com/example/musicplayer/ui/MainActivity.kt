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
import com.example.musicplayer.data.repository.ArtistRepositiry
import com.example.musicplayer.ui.viewmodel.ArtistViewmodel
import com.example.musicplayer.ui.artistList.Artistscreen
import com.example.musicplayer.ui.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {

                val viewmodel: ArtistViewmodel by viewModels { ViewModelFactory() }

                val artists by viewmodel._artists.collectAsState()

                Artistscreen(artists, viewmodel)
            }
        }
    }
}