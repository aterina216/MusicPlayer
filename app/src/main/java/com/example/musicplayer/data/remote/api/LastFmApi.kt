package com.example.musicplayer.data.remote.api

import com.example.musicplayer.data.remote.response.ArtistInfoResponse
import com.example.musicplayer.KEY
import com.example.musicplayer.data.remote.dto.LastFmSearchResponse
import com.example.musicplayer.data.remote.response.LastFmResponse
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

    @GET("/2.0/")
    suspend fun searchArtists(
        @Query("method") method: String = "artist.search",
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String = KEY.API_KEY,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20
    ): Response<LastFmSearchResponse>
}