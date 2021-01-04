package com.tilek.youtubeparser.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun isInternetConnected(cm:ConnectivityManager) : Boolean{
    val networkInfo : NetworkInfo? = cm.activeNetworkInfo
    return networkInfo!= null && networkInfo.isConnectedOrConnecting
}

fun getConnectivityManager(context: Context) : ConnectivityManager{
    return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}