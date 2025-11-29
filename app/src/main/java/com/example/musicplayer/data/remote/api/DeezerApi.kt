package com.example.musicplayer.data.remote.api

import com.example.musicplayer.data.remote.response.DeezerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApi {

    @GET("search/artist")
    suspend fun searchArtist(
        @Query("q") artistName: String
    ) : Response<DeezerResponse>

    @GET("search/artist")
    suspend fun searchArtist(
        @Query("q") artistName: String,
        @Query("limit") limit: Int = 20
    ): Response<DeezerResponse>
}