package com.tilek.youtubeparser.data.repository

import android.content.Context
import com.tilek.youtubeparser.data.local.YoutubeDataBase
import com.tilek.youtubeparser.data.models.PlaylistItem
import com.tilek.youtubeparser.data.models.VideoListItem

class YoutubeRepositoryLocal(context: Context) {

    private val youtubeDao = YoutubeDataBase.getDatabase(context).wordDao()

    fun getPlaylist() : MutableList<PlaylistItem>{
        return youtubeDao.getPlaylist()
    }

    fun getAllVideos(id : String) : VideoListItem?{
        return youtubeDao.getAllVideos(id)
    }

    fun addPlaylists(list : MutableList<PlaylistItem>){
        youtubeDao.addPlaylist(list)
    }

    fun addVideos(list : MutableList<PlaylistItem>){
        youtubeDao.addVideos(list)
    }

}