package com.example.musicplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.ui.screens.Home
import com.example.musicplayer.ui.viewmodels.ArtistViewmodel

@Composable
fun InitNavigation(viewmodel: ArtistViewmodel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home"){
        composable("home") {
            Home(viewmodel)
        }
    }
}