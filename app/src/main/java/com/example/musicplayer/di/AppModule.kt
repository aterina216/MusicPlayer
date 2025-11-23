package com.example.musicplayer.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.musicplayer.data.db.ArtistDao
import com.example.musicplayer.data.db.ArtistDatabase
import com.example.musicplayer.data.remote.api.DeezerApi
import com.example.musicplayer.data.remote.api.LastFmApi
import com.example.musicplayer.data.remote.api.RetrofitClient
import com.example.musicplayer.data.repository.ArtistRepository
import com.example.musicplayer.ui.viewmodel.ArtistViewmodel
import com.example.musicplayer.ui.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesDataBase(context: Context): ArtistDatabase {
        return Room.databaseBuilder(
            context,
            ArtistDatabase::class.java,
            "artists_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesDao(dataBase: ArtistDatabase): ArtistDao {
        return dataBase.artistDao()
    }

    @Singleton
    @Provides
    fun provideLastFmApi(): LastFmApi {
        return Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LastFmApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDeezerApi(): DeezerApi {
        return Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL_FOR_DEEZER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeezerApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        lastFmApi: LastFmApi,
        dao: ArtistDao,
        deezerApi: DeezerApi
    ): ArtistRepository {
        return ArtistRepository(lastFmApi, dao, deezerApi)
    }

    @Singleton
    @Provides
    fun provideViewModelFactory(repo: ArtistRepository): ViewModelFactory {
        return ViewModelFactory(repo)
    }

    @Singleton
    @Provides
    fun provideViewModel(repositiry: ArtistRepository): ArtistViewmodel {
        return ArtistViewmodel(repositiry)
    }
}