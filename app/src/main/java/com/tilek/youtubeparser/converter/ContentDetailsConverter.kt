package com.tilek.youtubeparser.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tilek.youtubeparser.data.models.ContentDetails

class ContentDetailsConverter {

    @TypeConverter
    fun fromRaw(raw: String?): ContentDetails? {
        if (raw == null) return null
        val gson = Gson()
        val type = object : TypeToken<ContentDetails?>() {}.type
        return gson.fromJson<ContentDetails>(raw, type)
    }

    @TypeConverter
    fun toRaw(contentDetails: ContentDetails?): String? {
        if (contentDetails == null) return null
        val gson = Gson()
        val type = object : TypeToken<ContentDetails?>() {}.type
        return gson.toJson(contentDetails, type)
    }

}