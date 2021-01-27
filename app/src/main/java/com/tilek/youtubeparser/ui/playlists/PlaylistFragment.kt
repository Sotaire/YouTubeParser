package com.tilek.youtubeparser.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.tilek.youtubeparser.R
import com.tilek.youtubeparser.core.BaseFragment
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.models.PlaylistItem
import com.tilek.youtubeparser.data.network.Resource
import com.tilek.youtubeparser.data.network.Status
import com.tilek.youtubeparser.extensions.*
import com.tilek.youtubeparser.ui.noInternet.NoInternetFragment
import com.tilek.youtubeparser.ui.noInternet.NoInternetViewModel
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.OnPlaylistClickListener
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.PlaylistAdapter
import kotlinx.android.synthetic.main.playlist_fragment.*
import org.koin.android.ext.android.inject

class PlaylistFragment : BaseFragment<PlaylistViewModel>(R.layout.playlist_fragment), OnPlaylistClickListener {

    private lateinit var adapter: PlaylistAdapter
    private var nextPageToken: String? = null

    companion object {
        const val PLAYLIST_ITEM = "playlistItem"
        var isOffline = false
    }

    override fun setUpView() {
        initRecycler()
        logger("data", "start")
        if (PlaylistFragment.isOffline) {
            fetchDataFromLD()
        } else {
            fetchData()
            pagging()
        }
    }

    override fun getViewModule(): PlaylistViewModel = inject<PlaylistViewModel>().value

    override fun setUpViewModelObs() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOffline = arguments?.getBoolean(NoInternetFragment.OFFLINE_STATE, false) ?: false
    }

    private fun initRecycler() {
        adapter = PlaylistAdapter(this)
        recycler_playlist.adapter = adapter
    }

    private fun fetchDataFromLD() {
        mViewModule!!.getPlaylistFromLD()
        mViewModule!!.localData.observe(viewLifecycleOwner, {
            logger("data", it[0].id)
            adapter.add(it)
        })
    }

    private fun pagging() {
        nested_scroll.setOnScrollChangeListener { nested: NestedScrollView, _, scrollY, _, _ ->
            if (scrollY == nested.getChildAt(0).measuredHeight - nested.measuredHeight) {
                if (nextPageToken != null) {
                    fetchNextData(nextPageToken!!)
                }
            }
        }
    }

    private fun fetchNextData(nextPageToken: String) {
        mViewModule!!.getNextPlaylist(nextPageToken).observe(viewLifecycleOwner, {
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
        mViewModule!!.getPlaylists().observe(viewLifecycleOwner, {
            statusCheck(it)
        })
    }

    private fun setData(resource: Resource<PlaylistInfo>) {
        resource.data?.items?.let { it1 ->
            adapter.add(it1)
            mViewModule!!.addPlaylistsToLD(it1)
        }
        nextPageToken = resource.data?.nextPageToken
        playlist_progress.gone()
    }

    override fun onClick(item: PlaylistItem) {
        if (isInternetConnected(getConnectivityManager(requireContext()))) {
            val bundle = Bundle()
            bundle.putSerializable(PLAYLIST_ITEM, item)
            findNavController().navigate(R.id.action_playlistFragment_to_detailsFragment, bundle)
        } else {
            findNavController().navigate(R.id.action_playlistFragment_to_noInternetFragment)
        }
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {

    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        errorReason: YouTubeInitializationResult?
    ) {
    }
}