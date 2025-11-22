package com.example.musicplayer



import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApi {

    @GET("search/artist")
    suspend fun searchArtist(
        @Query("q") artistName: String
    ) : Response<DeezerResponse>
}