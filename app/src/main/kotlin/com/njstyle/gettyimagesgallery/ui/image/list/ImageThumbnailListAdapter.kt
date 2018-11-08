package com.njstyle.gettyimagesgallery.ui.image.list

import android.view.ViewGroup
import com.njstyle.gettyimagesgallery.base.BaseRecyclerViewAdapter
import com.njstyle.gettyimagesgallery.ui.image.constant.ImageThumbnailItem

class ImageThumbnailListAdapter(private val imageThumbnailViewHolderFactory: ImageThumbnailViewHolderFactory)
    : BaseRecyclerViewAdapter<ImageThumbnailItem, ImageThumbnailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = imageThumbnailViewHolderFactory.createViewHolder(parent)

    override fun onBindViewHolder(viewHolder: ImageThumbnailViewHolder, position: Int) {
        viewHolder.onBindViewHolder(getItem(position), position)
    }

    override fun onViewRecycled(holder: ImageThumbnailViewHolder) {
        holder.onRecycled()

        super.onViewRecycled(holder)
    }
}