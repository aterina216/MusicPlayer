package com.example.musicplayer.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicplayer.data.db.database.ArtistDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context): ArtistDataBase {
        return Room.databaseBuilder(
            context,
            ArtistDataBase::class.java,
            "artist_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(dataBase: ArtistDataBase) = dataBase.artistDao()
}