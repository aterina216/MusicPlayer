package com.example.musicplayer.ui.artistList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.remote.dto.Artist
import com.example.musicplayer.ui.components.ArtistCard

@Composable
fun Artistscreen(artist: List<Artist>,
                 viewmodel: ArtistViewmodel
) {

    Log.d("Artistscreen", "Получено артистов: ${artist.size}")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        LazyVerticalGrid(
            modifier = Modifier.padding(8.dp),
            columns = GridCells.Adaptive(100.dp)
        ) {
            items(artist) { artistItem ->
                ArtistCard(artistItem, viewmodel, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}