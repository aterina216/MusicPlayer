package com.example.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.ui.navigation.InitNavigation
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.ui.viewmodels.ArtistViewModelFactory
import com.example.musicplayer.ui.viewmodels.ArtistViewmodel
import javax.inject.Inject
import kotlin.getValue

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ArtistViewModelFactory
    private val viewModel: ArtistViewmodel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        (application as MusicPlayerApp).appComponent.inject(this)
        setContent {
            MusicPlayerTheme {
                InitNavigation(viewModel)
            }
        }
    }
}

