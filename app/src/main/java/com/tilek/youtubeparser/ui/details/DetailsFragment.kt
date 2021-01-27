package com.tilek.youtubeparser.ui.details

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
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
import com.tilek.youtubeparser.ui.details.adapter.DetailAdapter
import com.tilek.youtubeparser.ui.playlists.PlaylistFragment
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.OnPlaylistClickListener
import com.tilek.youtubeparser.ui.video.VideoActivity
import com.tilek.youtubeparser.ui.video.VideoDetailFragment
import kotlinx.android.synthetic.main.details_fragment.*
import org.koin.android.ext.android.inject

class DetailsFragment : BaseFragment<DetailsViewModel>(R.layout.details_fragment), OnPlaylistClickListener{

    private lateinit var videoList : PlaylistItem
    private lateinit var adapter : DetailAdapter
    private var nextPageToken: String? = null

    override fun getViewModule(): DetailsViewModel =
            inject<DetailsViewModel>().value

    override fun setUpView() {
        initView()
        initRecycler()
        fetchData()
        pagging()
    }

    override fun setUpViewModelObs() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoList = it.getSerializable(PlaylistFragment.PLAYLIST_ITEM) as PlaylistItem
        }
    }

    private fun initView() {
        image_view.loadImage(videoList.snippet?.thumbnails?.medium?.url,1)
    }

    private fun initRecycler() {
        adapter = DetailAdapter(this)
        video_list_recycler.adapter = adapter
    }

    private fun fetchData() {
        mViewModule!!.getVideoListFromPlaylist(videoList.id.toString()).observe(viewLifecycleOwner, Observer {
            statusCheck(it)
        })
    }

    private fun pagging() {
        nested_scroll_video.setOnScrollChangeListener { nested: NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == nested.getChildAt(0).measuredHeight - nested.measuredHeight) {
                if (nextPageToken != null) {
                    fetchNextData(nextPageToken!!)
                }
            }
        }
    }

    private fun fetchNextData(nextPageToken: String) {
        mViewModule!!.getNextVideoListFromPlaylist(videoList.id.toString(),nextPageToken).observe(viewLifecycleOwner, Observer {
            if (it?.data?.nextPageToken == null) {
                this.nextPageToken = null
            }
            statusCheck(it)
        })
    }

    private fun setData(resource : Resource<PlaylistInfo>){
        resource.data?.items?.let { it1 -> adapter.add(it1) }
        nextPageToken = resource.data?.nextPageToken
        progress_bar.gone()
    }

    private fun statusCheck(resource : Resource<PlaylistInfo>){
        when (resource.status) {
            Status.SUCCESS -> setData(resource)
            Status.LOADING -> progress_bar.visible()
            Status.ERROR -> logger("error",resource.message.toString())
        }
    }

    override fun onClick(item: PlaylistItem) {
        if (isInternetConnected(getConnectivityManager(requireContext()))) {
            val intent : Intent = Intent(requireContext(),VideoActivity :: class.java)
            intent.putExtra(VideoActivity.VIDEO_ITEM, item)
            requireActivity().startActivity(intent)
//            findNavController().navigate(R.id.action_detailsFragment_to_videoDetailFragment, bundle)
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