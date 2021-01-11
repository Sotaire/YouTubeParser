package com.tilek.youtubeparser.data.models

import java.io.Serializable

data class PlaylistInfo(
    var nextPageToken:String? = null,
    var items : MutableList<PlaylistItem>?=null
)

data class PlaylistItem(
    var id : String? = null,
    var snippet : Snippet?= null,
    var contentDetails : ContentDetails? = null
) : Serializable

data class ContentDetails(
    var itemCount : Int ? = null
)

data class Snippet(
    var title:String?= null,
    var thumbnails : ImageInfo? = null,
    var channelTitle: String ? = null
)

data class ImageInfo(
    var medium : Medium? = null
)

data class Medium(
    var url : String? = null
)