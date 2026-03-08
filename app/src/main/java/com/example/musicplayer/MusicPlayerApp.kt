package com.example.musicplayer

import android.app.Application
import com.example.musicplayer.di.AppComponent
import com.example.musicplayer.di.DaggerAppComponent

class MusicPlayerApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appContext(this).build()
    }
}