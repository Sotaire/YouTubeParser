package com.tilek.youtubeparser.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.network.Resource
import com.tilek.youtubeparser.data.network.RetrofitClient
import com.tilek.youtubeparser.data.network.YouTubeApi
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeRepository() {

    val channelId = "UC8M5YVWQan_3Elm-URehz9w"
    val key = "AIzaSyDr7WJvkOUCrqvY6dsVmV9hjCpxG-EYbiI"
    val part = "snippet,contentDetails"
    val maxResults = 8

    private val api: YouTubeApi = RetrofitClient.instanceRetrofit()

    fun getPlaylists() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = api.getPlaylists(part, channelId, key,maxResults)))
        }catch (e : Exception){
            emit(Resource.error(data = null,message = e.message.toString()))
        }
    }

    fun getNextPlaylists(nextPageToken : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = api.getNextPlaylists(part, channelId, key,maxResults,nextPageToken)))
        }catch (e : Exception){
            emit(Resource.error(data = null,message = e.message.toString()))
        }
    }

    fun getVideoListFromPlaylist(videoListId : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = api.getVideoListFromPlaylist(part, videoListId, key,maxResults)))
        }catch (e : Exception){
            emit(Resource.error(data = null,message = e.message.toString()))
        }
    }

    fun getNextVideoListFromPlaylist(nextPageToken : String,videoListId : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = api.getNextVideoListFromPlaylist(part, videoListId, key,maxResults,nextPageToken)))
        }catch (e : Exception){
            emit(Resource.error(data = null,message = e.message.toString()))
        }
    }

}