package com.tilek.youtubeparser.data.network

import com.tilek.youtubeparser.data.models.PlaylistInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApi {

    @GET("youtube/v3/playlists")
    suspend fun getPlaylists(
        @Query("part")part : String,
        @Query("channelId") channelId:String,
        @Query("key") key : String,
        @Query("maxResults") maxResults : Int
    ):PlaylistInfo

    @GET("youtube/v3/playlists")
    suspend fun getNextPlaylists(
        @Query("part")part : String,
        @Query("channelId") channelId:String,
        @Query("key") key : String,
        @Query("maxResults") maxResults : Int,
        @Query("pageToken") nextPageToken : String? = null
    ):PlaylistInfo

    @GET("youtube/v3/playlistItems")
    suspend fun getVideoListFromPlaylist(
        @Query("part")part : String,
        @Query("playlistId") playlistId:String,
        @Query("key") key : String,
        @Query("maxResults") maxResults : Int
    ) : PlaylistInfo

    @GET("youtube/v3/playlistItems")
    suspend fun getNextVideoListFromPlaylist(
        @Query("part")part : String,
        @Query("playlistId") playlistId:String,
        @Query("key") key : String,
        @Query("maxResults") maxResults : Int,
        @Query("pageToken") nextPageToken : String? = null
    ) : PlaylistInfo


    companion object{
        fun provideYoutubeApi(retrofit: Retrofit) : YouTubeApi{
                return retrofit.create(YouTubeApi::class.java)
        }
    }
}