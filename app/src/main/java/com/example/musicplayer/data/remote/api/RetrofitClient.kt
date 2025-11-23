package com.example.musicplayer.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val BASE_URL = "https://ws.audioscrobbler.com"

    const val BASE_URL_FOR_DEEZER = "https://api.deezer.com/"

    val lastFmApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LastFmApi::class.java)

    val deezerApi = Retrofit.Builder()
        .baseUrl("https://api.deezer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DeezerApi::class.java)
}