package com.tilek.youtubeparser.ui.playlists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.repository.YoutubeRepository

class PlaylistViewModel : ViewModel() {

    var repository = YoutubeRepository()

    fun getPlaylists():MutableLiveData<PlaylistInfo?>{
        return repository.getPlaylists()
    }
}