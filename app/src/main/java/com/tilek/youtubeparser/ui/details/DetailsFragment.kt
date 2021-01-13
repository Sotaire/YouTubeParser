package com.tilek.youtubeparser.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.tilek.youtubeparser.R
import com.tilek.youtubeparser.data.models.PlaylistInfo
import com.tilek.youtubeparser.data.models.PlaylistItem
import com.tilek.youtubeparser.data.network.Resource
import com.tilek.youtubeparser.data.network.Status
import com.tilek.youtubeparser.extensions.gone
import com.tilek.youtubeparser.extensions.loadImage
import com.tilek.youtubeparser.extensions.logger
import com.tilek.youtubeparser.extensions.visible
import com.tilek.youtubeparser.ui.details.adapter.DetailAdapter
import com.tilek.youtubeparser.ui.playlists.PlaylistFragment
import com.tilek.youtubeparser.ui.playlists.playlistAdapter.OnPlaylistClickListener
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment(), OnPlaylistClickListener{

    private lateinit var viewModel: DetailsViewModel
    private lateinit var videoList : PlaylistItem
    private lateinit var adapter : DetailAdapter
    private var nextPageToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoList = it.getSerializable(PlaylistFragment.PLAYLIST_ITEM) as PlaylistItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        initView()
        initRecycler()
        fetchData()
        pagging()
    }

    private fun initView() {
        image_view.loadImage(videoList.snippet?.thumbnails?.medium?.url,1)
    }

    private fun initRecycler() {
        adapter = DetailAdapter(this)
        video_list_recycler.adapter = adapter
    }

    private fun fetchData() {
        viewModel.getVideoListFromPlaylist(videoList.id.toString()).observe(viewLifecycleOwner, Observer {
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
        viewModel.getNextVideoListFromPlaylist(videoList.id.toString(),nextPageToken).observe(viewLifecycleOwner, Observer {
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

    }

}