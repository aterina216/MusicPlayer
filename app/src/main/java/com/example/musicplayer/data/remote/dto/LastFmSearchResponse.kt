package com.example.musicplayer.data.remote.dto

import com.google.gson.annotations.SerializedName

// Убедись что эти модели точно соответствуют JSON от Last.fm
data class LastFmSearchResponse(
    val results: LastFmSearchResults
)

data class LastFmSearchResults(
    @SerializedName("artistmatches")
    val artistMatches: ArtistMatches
)

data class ArtistMatches(
    val artist: List<ArtistSearchItem>
)

data class ArtistSearchItem(
    val name: String,
    val mbid: String?,
    val url: String,
    val image: List<Image>?
)