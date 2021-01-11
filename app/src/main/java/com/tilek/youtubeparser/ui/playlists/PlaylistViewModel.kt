package com.tilek.youtubeparser.ui.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.network.Resource
import com.tilek.youtubeparser.data.repository.YoutubeRepository

class PlaylistViewModel : ViewModel() {

    var repository = YoutubeRepository()

    fun getPlaylists(): LiveData<Resource<PlaylistInfo>> {
        return repository.getPlaylists()
    }

    fun getNextPlaylist(nextPageToken : String): LiveData<Resource<PlaylistInfo>> {
        return repository.getNextPlaylists(nextPageToken)
    }
}