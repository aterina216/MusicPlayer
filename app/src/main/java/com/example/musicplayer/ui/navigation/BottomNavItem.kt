package com.example.musicplayer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Artists: BottomNavItem(
        "artists",
        title = "Артисты",
        icon = Icons.Default.Home
    )

    object Favorite: BottomNavItem(
        "favorite",
        title = "Избранное",
        icon = Icons.Default.Favorite
    )

    object Search: BottomNavItem(
        "search",
        "Поиск",
        Icons.Default.Search
    )
}