package com.example.musicplayer.ui.components

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.musicplayer.R

sealed class BottomItem(
    val title: String,
    val route: String,
    val image: Int
) {

    object Home: BottomItem("Home", "home", R.drawable.baseline_home_24)
    object Favorites: BottomItem("Favorites", "favorites", R.drawable.outline_music_note_2_24)
    object History: BottomItem("History", "history", R.drawable.outline_history_24)
    object Settings: BottomItem("Settings", "settings", R.drawable.baseline_settings_24)
}