package com.example.musicplayer.ui.navigation


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.ui.screens.Artistscreen
import com.example.musicplayer.ui.viewmodel.ArtistViewmodel
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.musicplayer.ui.screens.ArtistDetailScreen
import com.example.musicplayer.ui.screens.FavoriteScreen
import com.example.musicplayer.ui.screens.SearchScreen

@Composable
fun InitNavigation(viewmodel: ArtistViewmodel) {

    val navController = rememberNavController()
    val artists by viewmodel._artists.collectAsState()
    val favoriteArtists by viewmodel._favoriteArtists.collectAsState()
    val searchResults by viewmodel.searchResults.collectAsState()

    Scaffold(bottomBar = {
        MyBottomNavigation(navController)
    })

    { paddingValues: PaddingValues ->

        NavHost(navController, "artists", modifier = Modifier.padding(paddingValues)) {
            composable("artists") {
                Artistscreen(artists, viewmodel, navController)
            }

            composable("favorite") {
                FavoriteScreen(favoriteArtists, viewmodel, navController)
            }

            composable("search") {
                SearchScreen(viewmodel, navController)
            }

            composable(
                "artist_detail/{artistId}",
                arguments = listOf(navArgument("artistId") { type = NavType.StringType })
            ) { backStackEntry ->
                val artistId = backStackEntry.arguments?.getString("artistId") ?: ""
                ArtistDetailScreen(artistId, viewmodel, navController)
            }
        }

    }
}


