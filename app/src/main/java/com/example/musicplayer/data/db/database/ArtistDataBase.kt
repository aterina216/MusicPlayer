package com.example.musicplayer.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicplayer.data.db.dao.ArtistsDao
import com.example.musicplayer.data.db.entity.ArtistEntity

@Database(entities = [ArtistEntity::class], version = 2)
abstract class ArtistDataBase: RoomDatabase() {

    abstract fun artistDao(): ArtistsDao
}