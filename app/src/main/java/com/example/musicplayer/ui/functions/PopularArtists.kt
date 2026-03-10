package com.example.musicplayer.ui.functions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.api.models.Artist
import com.example.musicplayer.data.db.entity.ArtistEntity
import com.example.musicplayer.ui.components.ArtistItem

@Composable
fun PopularArtists(artists: List<ArtistEntity>) {

    LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(artists) {
            artist ->
                ArtistItem(artist)
        }
    }
}