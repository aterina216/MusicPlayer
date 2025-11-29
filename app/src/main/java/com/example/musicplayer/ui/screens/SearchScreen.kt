package com.example.musicplayer.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicplayer.ui.viewmodel.ArtistViewmodel

@Composable
fun SearchScreen(
    viewmodel: ArtistViewmodel,
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by viewmodel.searchResults.collectAsState()
    val isSearching by viewmodel.isSearching.collectAsState()

    Log.d("SEARCH_SCREEN", "🎨 Composable перерисовка. searchResults: ${searchResults.size}, isSearching: $isSearching")

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    Log.d("SEARCH_SCREEN", "⌨️ Пользователь вводит: '$it'")
                    searchQuery = it
                    if (it.length >= 2) {
                        Log.d("SEARCH_SCREEN", "🚀 Запускаем поиск...")
                        viewmodel.searchArtists(it)
                    } else {
                        Log.d("SEARCH_SCREEN", "🧹 Очищаем результаты")
                        viewmodel.searchResults.value = emptyList()
                    }
                },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Искать исполнителей...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Поиск")
                },
                singleLine = true
            )
        }
        when {
            isSearching -> {
                Log.d("SEARCH_SCREEN", "⏳ Показываем индикатор загрузки")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            searchResults.isEmpty() && searchQuery.length >= 2 -> {
                Log.d("SEARCH_SCREEN", "📭 Показываем 'ничего не найдено'")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ничего не найдено для \"$searchQuery\"")
                }
            }
            else -> {
                Log.d("SEARCH_SCREEN", "🎯 Показываем результаты: ${searchResults.size}")
                Artistscreen(
                    artist = searchResults,
                    viewmodel = viewmodel,
                    navController = navController
                )
            }
        }
    }
}