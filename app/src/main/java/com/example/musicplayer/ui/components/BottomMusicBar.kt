package com.example.musicplayer.ui.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.musicplayer.R

@Composable
fun BottomMusicBar(navController: NavController) {

   val items = listOf(
       BottomItem.Home,
       BottomItem.Favorites,
       BottomItem.History,
       BottomItem.Settings
   )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        
        items.forEach { 
            item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {navController.navigate(item.route) {
                    popUpTo(navController.graph.startDestinationId){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                } },
                icon = {
                    Icon(
                        painter = painterResource(item.image),
                        contentDescription = item.title,
                        tint = if(currentRoute == item.route)
                        MaterialTheme.colorScheme.primary
                        else
                        MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if(currentRoute == item.route)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}