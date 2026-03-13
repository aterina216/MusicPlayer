package com.example.musicplayer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.ui.components.BottomItem
import com.example.musicplayer.ui.components.BottomMusicBar
import com.example.musicplayer.ui.screens.FavoritesScreen
import com.example.musicplayer.ui.screens.HistoryScreen
import com.example.musicplayer.ui.screens.Home
import com.example.musicplayer.ui.viewmodels.ArtistViewmodel

@Composable
fun InitNavigation(viewmodel: ArtistViewmodel) {

    val navController = rememberNavController()

    Scaffold(bottomBar = { BottomMusicBar(navController) }) {
        innerPadding ->
        NavHost(navController = navController, startDestination = "home",
            modifier = Modifier.padding(innerPadding)) {
            composable(BottomItem.Home.route) {
                Home(viewmodel)
            }
            composable(BottomItem.Favorites.route){
                FavoritesScreen()
            }
            composable(BottomItem.History.route){
                HistoryScreen()
            }
            composable(BottomItem.Settings.route){
                BottomItem.Settings
            }
        }
    }
}