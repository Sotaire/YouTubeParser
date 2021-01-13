package com.tilek.youtubeparser.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tilek.youtubeparser.R
import com.tilek.youtubeparser.data.models.PlaylistItem
import com.tilek.youtubeparser.extensions.loadImage
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.OnPlaylistClickListener
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.PlaylistAdapter
import kotlinx.android.synthetic.main.playlist_item.view.*

class DetailAdapter (var listener : OnPlaylistClickListener) : RecyclerView.Adapter<DetailAdapter.VideoListViewHolder>() {

    private var list = ArrayList<PlaylistItem>()

    fun add(data:MutableList<PlaylistItem>){
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder
            = VideoListViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist_item,parent,false))

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.itemView.setOnClickListener {
            listener.onClick(list[position])
        }
    }

    override fun getItemCount(): Int  = list.size

    class VideoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(playlistItem: PlaylistItem){
            itemView.apply {
                video_name.text = playlistItem.snippet?.title
                video_amount.text = (playlistItem.contentDetails?.itemCount.toString())
                playlist_image.loadImage(playlistItem.snippet?.thumbnails?.medium?.url, 10)
            }
        }

    }

}