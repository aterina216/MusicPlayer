package com.example.musicplayer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.musicplayer.data.remote.dto.Artist
import com.example.musicplayer.data.remote.dto.ArtistImage
import com.example.musicplayer.data.db.ArtistImageDao
import kotlin.coroutines.coroutineContext

@Database(entities = [Artist::class, ArtistImage::class], version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArtistDatabase: RoomDatabase() {

    abstract fun artistDao(): ArtistDao
    abstract fun artistImageDao(): ArtistImageDao

    companion object {
        fun getDatabase(context: Context): ArtistDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ArtistDatabase::class.java,
                "artists_db"
            ).build()
        }
    }
}