package com.mobiquity.miniapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mobiquity.miniapp.model.entities.Product

@BindingAdapter("item", "width", "height")
fun ImageView.setProductImage(item: Product?, width: Float, height: Float) {
    item?.let {
        loadAndSetImage(item.url, width.toInt(), height.toInt(), this)
    }
}