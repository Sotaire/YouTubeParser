package com.tilek.youtubeparser.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tilek.youtubeparser.converter.ContentDetailsConverter
import com.tilek.youtubeparser.converter.ImageInfoConverter
import com.tilek.youtubeparser.converter.MediumConverter
import com.tilek.youtubeparser.converter.SnippetConverter
import java.io.Serializable

data class PlaylistInfo(
        var nextPageToken: String? = null,
        var items: MutableList<PlaylistItem>? = null
): Serializable

@Entity
data class PlaylistItem(
        @PrimaryKey
        var id: String,
        @TypeConverters(SnippetConverter::class)
        var snippet: Snippet? = null,
        var playlistId: String? = null,
        @TypeConverters(ContentDetailsConverter::class)
        var contentDetails: ContentDetails? = null,
        var startTime: Float = 0f
) : Serializable

data class ContentDetails(
        var videoId: String? = null,
        var itemCount: Int? = null
) : Serializable

data class Snippet(
        var title: String? = null,
        @TypeConverters(ImageInfoConverter::class)
        var thumbnails: ImageInfo? = null,
        var channelTitle: String? = null,
        var description: String? = null
): Serializable

data class ImageInfo(
        @TypeConverters(MediumConverter::class)
        var medium: Medium? = null
): Serializable

data class Medium(
        var url: String? = null
): Serializable