package com.example.musicplayer

import android.hardware.biometrics.BiometricPrompt
import android.util.StatsLog

data class ArtistInfo (
    val name: String,
    val image: List<Image>,
    val stats: Stats,
    val bio: ArtistBio?
)