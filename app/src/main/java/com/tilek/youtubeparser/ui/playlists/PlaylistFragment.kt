package com.tilek.youtubeparser.ui.playlists

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tilek.youtubeparser.R
import com.tilek.youtubeparser.data.models.PlaylistItem
import com.tilek.youtubeparser.data.network.Status
import com.tilek.youtubeparser.extensions.getConnectivityManager
import com.tilek.youtubeparser.extensions.isInternetConnected
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.OnPlaylistClickListener
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.PlaylistAdapter
import kotlinx.android.synthetic.main.playlist_fragment.*

class PlaylistFragment : Fragment(), OnPlaylistClickListener {

    companion object {
        const val PLAYLIST_ITEM = "playlistItem"
    }

    private lateinit var viewModel: PlaylistViewModel
    private lateinit var adapter: PlaylistAdapter
    private var nextPageToken: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.playlist_fragment, container, false)
    }

    private fun initRecycler() {
        adapter = PlaylistAdapter(this)
        recycler_playlist.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        initRecycler()
        fetchData()
        pagging()
    }

    private fun pagging() {
        nested_scroll.setOnScrollChangeListener { nested: NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == nested.getChildAt(0).measuredHeight - nested.measuredHeight) {
                if (nextPageToken != null) {
                    fetchNextData(nextPageToken!!)
                }
            }
        }
    }

    private fun fetchNextData(nextPageToken: String) {
        viewModel.getNextPlaylist(nextPageToken).observe(viewLifecycleOwner, Observer {
            if (it?.data?.nextPageToken == null) {
                this.nextPageToken = null
            }
            when (it.status) {
                Status.SUCCESS -> {
                    it?.data?.items?.let { it1 -> adapter.add(it1) }
                    this.nextPageToken = it?.data?.nextPageToken
                }
            }
        })
    }

    private fun fetchData() {
        viewModel.getPlaylists().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it?.data?.items?.let { it1 -> adapter.add(it1) }
                    nextPageToken = it?.data?.nextPageToken
                }
            }
        })
    }

    override fun onClick(item: PlaylistItem) {
        if (isInternetConnected(getConnectivityManager(requireContext()))) {
            var bundle = Bundle()
            bundle.putSerializable(PLAYLIST_ITEM,item)
            findNavController().navigate(R.id.action_playlistFragment_to_detailsFragment,bundle)
        }else{
            findNavController().navigate(R.id.action_playlistFragment_to_noInternetFragment)
        }
    }

}