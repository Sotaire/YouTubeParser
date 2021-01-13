package com.tilek.youtubeparser.data.local

import androidx.room.*
import com.tilek.youtubeparser.data.models.PlaylistItem
import com.tilek.youtubeparser.data.models.VideoListItem

@Dao
interface YoutubeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlaylist(list: MutableList<PlaylistItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVideos(list: MutableList<PlaylistItem>)

    @Query("SELECT * FROM PlaylistItem")
    fun getPlaylist() : MutableList<PlaylistItem>

    @Transaction
    @Query("SELECT * FROM PlaylistItem WHERE PlaylistItem.id =:id")
    fun getAllVideos(id : String): VideoListItem?
}