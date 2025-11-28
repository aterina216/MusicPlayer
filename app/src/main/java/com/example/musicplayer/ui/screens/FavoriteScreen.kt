package com.example.musicplayer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.NavController
import com.example.musicplayer.data.remote.dto.Artist
import com.example.musicplayer.ui.components.ArtistCard
import com.example.musicplayer.ui.viewmodel.ArtistViewmodel


@Composable
fun FavoriteScreen(
    artist: List<Artist>,
    viewmodel: ArtistViewmodel,
    navController: NavController
) {
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
                ArtistCard(
                    artist = artistItem,
                    viewmodel = viewmodel,
                    navController = navController,  // Передаем навигатор
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}