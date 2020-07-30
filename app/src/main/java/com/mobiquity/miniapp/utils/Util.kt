package com.mobiquity.miniapp.utils

import android.content.res.Resources.getSystem
import android.widget.ImageView
import com.mobiquity.miniapp.R
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

const val BASE_URL = "http://mobcategories.s3-website-eu-west-1.amazonaws.com"

fun loadAndSetImage(
    url: String,
    width: Int,
    height: Int,
    imageView: ImageView
) {
    Picasso.get()
        .load(BASE_URL + url)
        .networkPolicy(NetworkPolicy.NO_CACHE)
        .placeholder(R.drawable.ic_pic_placeholder)
        .resize(width.px, height.px)
        .into(imageView)
}

val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()
