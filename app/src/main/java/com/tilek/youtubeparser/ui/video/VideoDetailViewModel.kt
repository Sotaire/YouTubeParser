package com.tilek.youtubeparser.ui.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.models.PlaylistItem

class VideoDetailViewModel : ViewModel() {

    val currentVideo = MutableLiveData<PlaylistItem>()
    var firsVideoId: String? = null
    var detailsPlaylist = MutableLiveData<PlaylistInfo>()

    fun setUpPlaylistData(detailsPlaylist: PlaylistInfo) {
        if (this.detailsPlaylist.value == null) {
            if (detailsPlaylist.items!!.isNotEmpty()) currentVideo.value = detailsPlaylist.items!![0]
            this.detailsPlaylist.value = detailsPlaylist
        }
    }

    fun setUpFirstVideoId(videoId: String?) {
        if (firsVideoId == null)
            firsVideoId = videoId
    }

    fun changeVideo(detailItem: PlaylistItem) {
        currentVideo.value = detailItem
    }

    fun changeLastSecond(time: Float) {
        currentVideo.value?.let {
            it.startTime = time
        }
    }

}