package com.tilek.youtubeparser.data.repository

import androidx.lifecycle.MutableLiveData
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.network.RetrofitClient
import com.tilek.youtubeparser.data.network.YouTubeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeRepository() {

    val channelId = "UC8M5YVWQan_3Elm-URehz9w"
    val key = "AIzaSyDr7WJvkOUCrqvY6dsVmV9hjCpxG-EYbiI"
    val part = "snippet,contentDetails"

    private val api: YouTubeApi = RetrofitClient.instanceRetrofit()

    fun getPlaylists(): MutableLiveData<PlaylistInfo?>{
        var data = MutableLiveData<PlaylistInfo?>()
        api.getPlaylists(part, channelId, key).enqueue(object : Callback<PlaylistInfo?>{
            override fun onResponse(call: Call<PlaylistInfo?>, response: Response<PlaylistInfo?>) {
                data?.value = response.body()
            }

            override fun onFailure(call: Call<PlaylistInfo?>, t: Throwable) {
                data.value = null
            }

        })
        return data
    }
}