package com.tilek.youtubeparser.ui.playlistAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tilek.youtubeparser.R

class PlaylistAdapter : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    var list = ArrayList<String>()

    fun add(data:String){
        list.add(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder
            = PlaylistViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist_item,parent,false))

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        //holder.onBind()
    }

    override fun getItemCount(): Int  = list.size

    class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}