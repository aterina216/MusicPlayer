package com.example.musicplayer.di

import android.content.Context
import androidx.annotation.UiContext
import com.example.musicplayer.MainActivity
import com.example.musicplayer.MusicPlayerApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataBaseModule::class, NetworkModule::class, RepositoryModule::class, ViewModelFactoryModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder
        fun build(): AppComponent
    }
}