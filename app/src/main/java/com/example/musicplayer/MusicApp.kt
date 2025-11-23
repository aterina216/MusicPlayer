package com.example.musicplayer

import android.app.Application
import android.util.Log
import com.example.musicplayer.di.AppComponent
import com.example.musicplayer.di.AppModule
import com.example.musicplayer.di.DaggerAppComponent

class MusicApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        Log.d("MyApp", "Dagger component initialized")
    }
}