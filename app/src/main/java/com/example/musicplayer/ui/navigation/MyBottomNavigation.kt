package com.example.musicplayer.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MyBottomNavigation(navController: NavController) {

    val items = listOf(BottomNavItem.Artists,
        BottomNavItem.Search,
        BottomNavItem.Favorite)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRote = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 8.dp
    ) {
        items.forEach {
            item ->
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = item.icon,
                        contentDescription = item.title)
                },
                label = { Text(item.title) },
                selected = currentRote == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}