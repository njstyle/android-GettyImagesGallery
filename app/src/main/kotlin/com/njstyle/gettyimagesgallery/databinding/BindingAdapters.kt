package com.njstyle.gettyimagesgallery.databinding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.njstyle.gettyimagesgallery.base.BaseRecyclerViewAdapter
import com.njstyle.gettyimagesgallery.extension.createWaitProgressDrawable
import com.njstyle.gettyimagesgallery.util.GlideApp

fun getDrawablePlaceHolder(imageView: ImageView, placeholder: Drawable?) = placeholder ?: imageView.context.createWaitProgressDrawable()

@BindingAdapter(value = ["imageCenterCropUrl", "placeholder"], requireAll = false)
fun ImageView.loadImageCenterCropUrl(imageUrl: String?, placeholder: Drawable?) {
    GlideApp.with(this)
            .load(imageUrl)
            .centerCrop()
            .placeholder(getDrawablePlaceHolder(this, placeholder))
            .into(this)
}

@BindingAdapter("items")
fun RecyclerView.setItems(items: List<Nothing>) {
    (this.adapter as BaseRecyclerViewAdapter<*, *>).setItems(items)
}