package com.example.musicplayer

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi {


    @GET("/2.0/")
    suspend fun getTopArtists(
        @Query("method") method: String = "chart.getTopArtists",
        @Query("api_key") apiKey: String = KEY.API_KEY,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 50
    ): Response<LastFmResponse>

    @GET("/2.0/")
    suspend fun getArtistInfo(
        @Query("method") method: String = "artist.getInfo",
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String = KEY.API_KEY,
        @Query("format") format: String = "json"
    ): Response<ArtistInfoResponse>
}