package com.example.musicplayer.di

import androidx.core.content.pm.PermissionInfoCompat
import com.example.musicplayer.data.api.lastfm.DeezerInterface
import com.example.musicplayer.data.api.lastfm.LastFMApi
import com.example.musicplayer.data.db.database.ArtistDataBase
import com.example.musicplayer.data.repo.ArtistsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(lastFMApi: LastFMApi, deezerInterface: DeezerInterface, dataBase: ArtistDataBase): ArtistsRepository {
        return ArtistsRepository(lastFMApi, deezerInterface, dataBase)
    }
}