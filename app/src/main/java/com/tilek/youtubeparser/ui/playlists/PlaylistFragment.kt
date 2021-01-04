package com.tilek.youtubeparser.ui.playlists

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.tilek.youtubeparser.R
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.PlaylistAdapter
import kotlinx.android.synthetic.main.playlist_fragment.*

class PlaylistFragment : Fragment() {

    private lateinit var viewModel: PlaylistViewModel
    private lateinit var adapter: PlaylistAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.playlist_fragment, container, false)
    }

    private fun initRecycler() {
        adapter = PlaylistAdapter()
        recycler_playlist.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        initRecycler()
        fetchData()
    }

    private fun fetchData() {
        viewModel.getPlaylists().observe(viewLifecycleOwner, Observer {
            it?.items?.let { it1 -> adapter.add(it1) }
            Log.d("data",it?.items?.size.toString())
        })
    }

}