package com.tilek.youtubeparser.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tilek.youtubeparser.data.models.Medium

class MediumConverter {

    @TypeConverter
    fun fromRaw(raw: String?): Medium? {
        if (raw == null) return null
        val gson = Gson()
        val type = object : TypeToken<Medium?>() {}.type
        return gson.fromJson<Medium>(raw, type)
    }

    @TypeConverter
    fun toRaw(medium: Medium?): String? {
        if (medium == null) return null
        val gson = Gson()
        val type = object : TypeToken<Medium?>() {}.type
        return gson.toJson(medium, type)
    }

}