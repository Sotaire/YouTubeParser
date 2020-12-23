package com.tilek.youtubeparser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tilek.youtubeparser.R
import com.tilek.youtubeparser.ui.playlistAdapter.PlaylistAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter : PlaylistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
    }

    private fun initRecycler() {
        adapter = PlaylistAdapter()
        recycler_playlist.adapter = adapter
        setData()
    }

    private fun setData() {
        for (i in 1..10){
            adapter.add("data: $i")
        }
        adapter.notifyDataSetChanged()
    }
}