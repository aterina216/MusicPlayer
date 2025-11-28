package com.example.musicplayer.ui.screens


import android.content.Intent
import android.net.Uri
import androidx.collection.intIntMapOf
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.musicplayer.data.remote.dto.ArtistDetails
import com.example.musicplayer.ui.viewmodel.ArtistViewmodel

@Composable
fun ArtistDetailScreen(
    artistId: String,
    viewModel: ArtistViewmodel,
    navController: NavController
) {
    val artistDetails by viewModel.selectedArtist.collectAsState()
    val isLoading by viewModel.isLoadingDetails.collectAsState()

    LaunchedEffect(artistId) {
        viewModel.loadArtistDetails(artistId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Кастомный топбар
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.Blue)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.White
                )
            }
            Text(
                text = artistDetails?.artist?.name ?: "Артист",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Контент
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (artistDetails == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Артист не найден")
            }
        } else {
            ArtistDetailContent(artistDetails!!, viewModel)
        }
    }
}

@Composable
fun ArtistDetailContent(artistDetails: ArtistDetails,
                        artistViewmodel: ArtistViewmodel) {
    val artist = artistDetails.artist
    val context = LocalContext.current

    LazyColumn {
        item {
            // Фото и имя
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val imageUrl = artistDetails.highResImageUrl ?: artist.image.firstOrNull()?.text

                AsyncImage(
                    model = imageUrl,
                    contentDescription = artist.name,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = artist.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            // Статистика
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(artist.listeners, fontWeight = FontWeight.Bold)
                    Text("слушателей", fontSize = 12.sp, color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(artist.playcount, fontWeight = FontWeight.Bold)
                    Text("прослушиваний", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        // Биография если есть
        artistDetails.bio?.let { bio ->
            if (bio.isNotBlank()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("ОБ АРТИСТЕ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = bio,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        item {
            // Ссылка
            if (artist.url.isNotBlank()) {

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text("ССЫЛКИ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Last.fm профиль",
                            color = Color.Blue,
                            modifier = Modifier.clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(artist.url))
                                context.startActivity(intent)
                            }
                        )
                    }

                    IconButton(
                        onClick = {
                            var favorite = !artist.isFavorite
                            artistViewmodel.toggleFavorite(artist.id)
                            artistViewmodel.updateFavoriteStatus(artist.id, favorite)
                        },
                        modifier = Modifier.size(100.dp)
                    ) {
                        Icon(
                            imageVector = if (artist.isFavorite) {
                                Icons.Filled.Favorite
                            }
                            else {
                                Icons.Outlined.Favorite
                            },
                            contentDescription = "Избранное",
                            tint = if (artist.isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
            }
        }
    }
}