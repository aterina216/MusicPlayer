package com.example.musicplayer.data.api.lastfm

import com.example.musicplayer.data.api.models.DeezerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerInterface {

    @GET("search/artist")
    suspend fun getArtist(
        @Query("q") query: String)
    : DeezerResponse
}