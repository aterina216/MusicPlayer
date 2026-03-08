package com.example.musicplayer.data.api.models

import com.google.gson.annotations.SerializedName

class Artists(
    @SerializedName("@attr") val attr: Attr,
    val artist: List<Artist>
)