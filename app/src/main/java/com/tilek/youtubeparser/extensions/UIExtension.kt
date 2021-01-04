package com.tilek.youtubeparser.extensions

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tilek.youtubeparser.R

fun ImageView.loadImage (imageURL : String?, radius : Int){
    Glide.with(this.context)
        .load(imageURL)
        .placeholder(R.drawable.video_back)
        .transform(CenterCrop(),RoundedCorners(radius))
        .into(this)
}
fun Context.showToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}