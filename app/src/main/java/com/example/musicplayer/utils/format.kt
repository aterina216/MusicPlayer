package com.example.musicplayer.utils


import java.text.NumberFormat
import java.util.Locale

object format {

    fun formatNumber(number: String): String {
        return try {
            val num = number.toLong()
            NumberFormat.getInstance(Locale.US).format(num)
        }
        catch (e: NumberFormatException) {
            number
        }
    }
}