package com.example.musicplayer.data.api.lastfm

import com.example.musicplayer.data.api.lastfm.API_KEY.MYAPI_KEY
import com.example.musicplayer.data.api.models.Artist
import com.example.musicplayer.data.api.models.ArtistResponse
import com.example.musicplayer.data.api.models.Artists
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LastFMApi {
    @GET("?method=chart.gettopartists&format=json")
    suspend fun getPopularArtists(
        @Query("api_key") api_key: String = MYAPI_KEY
    ): ArtistResponse
}