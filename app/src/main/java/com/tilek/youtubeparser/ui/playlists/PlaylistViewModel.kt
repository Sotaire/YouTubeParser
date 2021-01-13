package com.tilek.youtubeparser.ui.playlists

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilek.youtubeparser.App
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.models.PlaylistItem
import com.tilek.youtubeparser.data.network.Resource
import com.tilek.youtubeparser.data.repository.YoutubeRepository
import com.tilek.youtubeparser.data.repository.YoutubeRepositoryLocal

class PlaylistViewModel : ViewModel() {

    var repository = YoutubeRepository()
    var localData = MutableLiveData<MutableList<PlaylistItem>>()
    lateinit var localRepositoryLocal : YoutubeRepositoryLocal

    fun initRepository(context: Context){
        localRepositoryLocal = YoutubeRepositoryLocal(context)
    }

    fun getPlaylists(): LiveData<Resource<PlaylistInfo>> {
        return repository.getPlaylists()
    }

    fun getNextPlaylist(nextPageToken : String): LiveData<Resource<PlaylistInfo>> {
        return repository.getNextPlaylists(nextPageToken)
    }

    fun getPlaylistFromLD(){
        localData.value = localRepositoryLocal.getPlaylist()
    }

    fun addPlaylistsToLD(list : MutableList<PlaylistItem>){
        localRepositoryLocal.addPlaylists(list)
    }

}