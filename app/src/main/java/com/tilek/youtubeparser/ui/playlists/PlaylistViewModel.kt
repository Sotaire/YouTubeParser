package com.tilek.youtubeparser.ui.playlists

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.models.PlaylistItem
import com.tilek.youtubeparser.data.network.Resource
import com.tilek.youtubeparser.data.repository.YoutubeRepository

class PlaylistViewModel (private var repository : YoutubeRepository) : ViewModel () {

    var localData = MutableLiveData<MutableList<PlaylistItem>>()

    fun getPlaylists(): LiveData<Resource<PlaylistInfo>> {
        return repository.getPlaylists()
    }

    fun getNextPlaylist(nextPageToken : String): LiveData<Resource<PlaylistInfo>> {
        return repository.getNextPlaylists(nextPageToken)
    }

    fun getPlaylistFromLD(){
        localData.value = repository.getPlaylist()
    }

    fun addPlaylistsToLD(list : MutableList<PlaylistItem>){
        repository.addPlaylists(list)
    }

}