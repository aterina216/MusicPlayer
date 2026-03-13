package com.example.musicplayer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.musicplayer.ui.viewmodels.ArtistViewmodel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicplayer.ui.components.BottomMusicBar
import com.example.musicplayer.ui.components.TopMusicBar
import com.example.musicplayer.ui.functions.PopularArtists

@Composable
fun Home(viewmodel: ArtistViewmodel) {

    val artists by viewmodel.popularArtists.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
        TopMusicBar()

        if (artists.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            PopularArtists(artists)
        }

    }
}