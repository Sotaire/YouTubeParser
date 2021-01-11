package com.tilek.youtubeparser.ui.playlists.playlistAdapter

import com.tilek.youtubeparser.data.models.PlaylistItem

interface OnPlaylistClickListener {

    fun onClick(item : PlaylistItem)

}