package com.example.musicplayer.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.musicplayer.data.remote.dto.Artist
import com.example.musicplayer.ui.viewmodel.ArtistViewmodel

@Composable
fun ArtistCard(artist: Artist,
               viewmodel: ArtistViewmodel,
               modifier: Modifier) {

    Log.d("ArtistCard", "Rendering: ${artist.name}, images: ${artist.image.size}")

    val imageCache by viewmodel.imageCache.collectAsState()
    val imageUrl = imageCache[artist.name]

    Log.d("ArtistCard", "Rendering: ${artist.name}, imageUrl: $imageUrl")

    Card(modifier = Modifier.height(225.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp))
    {
        Column(modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){

            AsyncImage(
                model = imageUrl,
                contentDescription = artist.name,
                modifier = Modifier.size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = artist.name,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${artist.listeners} слушателей",
                color = Color(0xFF666666),
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "${artist.playcount} прослушиваний",
                color = Color(0xFF666666),
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}