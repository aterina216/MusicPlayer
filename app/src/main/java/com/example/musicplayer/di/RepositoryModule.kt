package com.example.musicplayer.di

import androidx.core.content.pm.PermissionInfoCompat
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
    fun provideRepository(lastFMApi: LastFMApi, dataBase: ArtistDataBase): ArtistsRepository {
        return ArtistsRepository(lastFMApi, dataBase)
    }
}