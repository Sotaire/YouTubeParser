package com.tilek.youtubeparser.ui.noInternet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.tilek.youtubeparser.R
import com.tilek.youtubeparser.extensions.getConnectivityManager
import com.tilek.youtubeparser.extensions.isInternetConnected
import com.tilek.youtubeparser.extensions.showToast
import kotlinx.android.synthetic.main.fragment_no_internet.*


class NoInternetFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_no_internet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isOnline()
    }

    private fun isOnline() {
        btn_try_again.setOnClickListener {
            if (isInternetConnected(getConnectivityManager(requireContext()))){
                findNavController().navigate(R.id.action_noInternetFragment_to_playlistFragment)
            }else{
                context?.showToast("No internet")
            }
        }
    }
}