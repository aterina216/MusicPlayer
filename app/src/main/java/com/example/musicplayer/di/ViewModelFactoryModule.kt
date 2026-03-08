package com.example.musicplayer.di

import com.example.musicplayer.data.repo.ArtistsRepository
import com.example.musicplayer.ui.viewmodels.ArtistViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: ArtistsRepository): ArtistViewModelFactory {
        return ArtistViewModelFactory(repository)
    }
}