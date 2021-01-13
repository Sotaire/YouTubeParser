package com.tilek.youtubeparser.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tilek.youtubeparser.R
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.models.PlaylistItem
import com.tilek.youtubeparser.data.network.Resource
import com.tilek.youtubeparser.data.network.Status
import com.tilek.youtubeparser.extensions.*
import com.tilek.youtubeparser.ui.noInternet.NoInternetFragment
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.OnPlaylistClickListener
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.PlaylistAdapter
import kotlinx.android.synthetic.main.playlist_fragment.*

class PlaylistFragment : Fragment(), OnPlaylistClickListener {

    companion object {
        const val PLAYLIST_ITEM = "playlistItem"
        var isOffline = false
    }

    private lateinit var viewModel: PlaylistViewModel
    private lateinit var adapter: PlaylistAdapter
    private var nextPageToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOffline = arguments?.getBoolean(NoInternetFragment.OFFLINE_STATE, false) ?: false
    }

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
        viewModel.initRepository(requireContext())
        initRecycler()
        logger("data","start")
        if (isOffline) {
            fetchDataFromLD()
        } else {
            fetchData()
            pagging()
        }
    }

    private fun fetchDataFromLD() {
        viewModel.getPlaylistFromLD()
        viewModel.localData.observe(viewLifecycleOwner, Observer {
            logger("data",it[0].id)
            adapter.add(it)
        })
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
            statusCheck(it)
        })
    }

    private fun statusCheck(resource: Resource<PlaylistInfo>) {
        when (resource.status) {
            Status.SUCCESS -> setData(resource)
            Status.LOADING -> playlist_progress.visible()
            Status.ERROR -> logger("error", resource.message.toString())
        }
    }

    private fun fetchData() {
        viewModel.getPlaylists().observe(viewLifecycleOwner, Observer {
            statusCheck(it)
        })
    }

    fun setData(resource: Resource<PlaylistInfo>) {
        resource.data?.items?.let { it1 ->
            adapter.add(it1)
            viewModel.addPlaylistsToLD(it1)
        }
        nextPageToken = resource.data?.nextPageToken
        playlist_progress.gone()
    }

    override fun onClick(item: PlaylistItem) {
        if (isInternetConnected(getConnectivityManager(requireContext()))) {
            var bundle = Bundle()
            bundle.putSerializable(PLAYLIST_ITEM, item)
            findNavController().navigate(R.id.action_playlistFragment_to_detailsFragment, bundle)
        } else {
            findNavController().navigate(R.id.action_playlistFragment_to_noInternetFragment)
        }
    }

}