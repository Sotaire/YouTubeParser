package com.tilek.youtubeparser.data.network

import com.tilek.youtubeparser.data.models.PlaylistInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApi {

    @GET("youtube/v3/playlists")
    fun getPlaylists(
        @Query("part")part : String,
        @Query("channelId") channelId:String,
        @Query("key") key : String
    ):Call<PlaylistInfo>

}