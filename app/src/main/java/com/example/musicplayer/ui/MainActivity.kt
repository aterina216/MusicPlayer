package com.example.musicplayer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicplayer.ui.artistList.ArtistViewmodel
import com.example.musicplayer.ui.artistList.Artistscreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {

                val viewmodel: ArtistViewmodel = viewModel()

                val artists by viewmodel._artists.collectAsState()

                Artistscreen(artists, viewmodel)
            }
        }
    }
}