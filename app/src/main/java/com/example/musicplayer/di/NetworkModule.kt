package com.example.musicplayer.di

import com.example.musicplayer.data.api.lastfm.API.BASE_URL
import com.example.musicplayer.data.api.lastfm.API.DEEZER_URL
import com.example.musicplayer.data.api.lastfm.DeezerInterface
import com.example.musicplayer.data.api.lastfm.LastFMApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlin.jvm.java

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Provides
    @Singleton
    @Named("lastfm")
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    @Named("deezer")
    fun provideDeezerRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(DEEZER_URL)
        .build()
    @Provides
    @Singleton
    fun provideLastFmApi(@Named("lastfm") retrofit: Retrofit): LastFMApi {
        return retrofit.create(LastFMApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDeezerApi(@Named("deezer") retrofit: Retrofit): DeezerInterface {
        return retrofit.create(DeezerInterface::class.java)
    }
}


