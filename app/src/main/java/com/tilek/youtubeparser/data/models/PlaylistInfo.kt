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
)

@Entity
data class PlaylistItem(
    @PrimaryKey
    var id: String,
    @TypeConverters(SnippetConverter::class)
    var snippet: Snippet? = null,
    var playlistId : String? = null,
    @TypeConverters(ContentDetailsConverter::class)
    var contentDetails: ContentDetails? = null
) : Serializable

data class ContentDetails(
    var itemCount: Int? = null
)

data class Snippet(
    var title: String? = null,
    @TypeConverters(ImageInfoConverter::class)
    var thumbnails: ImageInfo? = null,
    var channelTitle: String? = null
)

data class ImageInfo(
    @TypeConverters(MediumConverter::class)
    var medium: Medium? = null
)

data class Medium(
    var url: String? = null
)