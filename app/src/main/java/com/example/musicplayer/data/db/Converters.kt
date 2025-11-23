package com.example.musicplayer.data.db

import androidx.room.TypeConverter
import com.example.musicplayer.data.remote.dto.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromImageList(imageList: List<Image>): String {
        return Gson().toJson(imageList)
    }

    @TypeConverter
    fun toImageList(imageListString: String): List<Image> {
        val type = object : TypeToken<List<Image>>() {}.type
        return Gson().fromJson(imageListString, type)
    }
}