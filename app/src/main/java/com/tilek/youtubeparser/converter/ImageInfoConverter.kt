package com.tilek.youtubeparser.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tilek.youtubeparser.data.models.ImageInfo

class ImageInfoConverter {

    @TypeConverter
    fun fromRaw(raw: String?): ImageInfo? {
        if (raw == null) return null
        val gson = Gson()
        val type = object : TypeToken<ImageInfo>() {}.type
        return gson.fromJson<ImageInfo>(raw, type)
    }

    @TypeConverter
    fun toRaw(imageInfo: ImageInfo?): String? {
        if (imageInfo == null) return null
        val gson = Gson()
        val type = object : TypeToken<ImageInfo?>() {}.type
        return gson.toJson(imageInfo, type)
    }

}