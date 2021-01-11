package com.tilek.youtubeparser.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.network.Resource
import com.tilek.youtubeparser.data.repository.YoutubeRepository

class DetailsViewModel : ViewModel() {

    private val repository = YoutubeRepository()

    fun getVideoListFromPlaylist(videoListId : String) : LiveData<Resource<PlaylistInfo>>{
        return repository.getVideoListFromPlaylist(videoListId)
    }

    fun getNextVideoListFromPlaylist(videoListId : String,nextPageToken : String){
        repository.getNextVideoListFromPlaylist(nextPageToken,videoListId)
    }

}