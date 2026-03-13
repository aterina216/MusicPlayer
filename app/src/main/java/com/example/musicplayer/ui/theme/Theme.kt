package com.example.musicplayer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import com.example.musicplayer.ui.theme.Typography

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1DB954),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1E3A2A),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFB3B3B3),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFB3B3B3)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1DB954),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB9F6CA),
    onPrimaryContainer = Color(0xFF00210A),
    secondary = Color(0xFF666666),
    onSecondary = Color.White,
    background = Color(0xFFF0F0FF),
    onBackground = Color(0xFF121212),
    surface = Color(0xFFF5F5F5),
    onSurface = Color(0xFF121212),
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF666666),
)

@Composable
fun MusicPlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}