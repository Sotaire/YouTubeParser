package com.tilek.youtubeparser.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<V : ViewModel>(private val layoutId: Int) :
        Fragment() {

    var mViewModule: V? = null

    private var hasInitializedRootView = false
    var rootView: View? = null

    abstract fun getViewModule(): V

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container)
    }

    private fun getPersistentView(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(layoutId, container, false)
        } else {
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            mViewModule = getViewModule()
            setUpViewModelObs()
            setUpView()
        }
    }

    abstract fun setUpView()

    abstract fun setUpViewModelObs()
}