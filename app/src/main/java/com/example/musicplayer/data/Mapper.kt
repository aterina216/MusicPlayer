package com.example.musicplayer.data

import com.example.musicplayer.data.api.models.Artist
import com.example.musicplayer.data.api.models.Image
import com.example.musicplayer.data.db.entity.ArtistEntity

object Mapper {

    fun Artist.toEntity(): ArtistEntity {
        return ArtistEntity(
            id = 0,
            listeners = this.listeners,
            mbid = this.mbid,
            name = this.name,
            playcount = this.playcount,
            streamable = this.streamable,
            url = this.url
        )
    }

    fun getImageUrl(images: List<Image>): String {
        val image = images.find { it.size == "large"} ?: images.firstOrNull()
        if (image != null) return image.text else return ""
    }
}