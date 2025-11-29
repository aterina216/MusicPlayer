package com.example.musicplayer.data.repository

import com.example.musicplayer.data.remote.dto.Artist
import com.example.musicplayer.data.remote.dto.LastFmSearchResponse
import com.example.musicplayer.data.remote.response.LastFmResponse

// Добавь этот класс в repository или в отдельный файл
class LastFmResponseMapper {

    fun mapSearchResponseToArtists(
        lastFmResponse: LastFmSearchResponse,
        deezerImages: Map<String, String> = emptyMap()
    ): List<Artist> {
        return lastFmResponse.results.artistMatches.artist.map { searchItem ->
            Artist(
                id = 0, // autoGenerate
                image = searchItem.image ?: emptyList(),
                listeners = "", // нет в поисковом ответе
                name = searchItem.name,
                mbid = searchItem.mbid,
                playcount = "", // нет в поисковом ответе
                streamable = "",
                url = searchItem.url,
                deezerImageUrl = deezerImages[searchItem.name],
                isFavorite = false
            )
        }
    }

    // Можно добавить и для других методов Last.fm
    fun mapTopArtistsResponse(lastFmResponse: LastFmResponse): List<Artist> {
        return lastFmResponse.artists.artist.map { artist ->
            Artist(
                id = 0,
                image = artist.image,
                listeners = artist.listeners,
                name = artist.name,
                mbid = artist.mbid,
                playcount = artist.playcount,
                streamable = artist.streamable,
                url = artist.url,
                deezerImageUrl = null, // будет заполнено позже
                isFavorite = false
            )
        }
    }
}