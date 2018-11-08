package com.njstyle.gettyimagesgallery.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseRecyclerViewHolder<ITEM>(parent: ViewGroup,
                                            layoutId: Int
                                            ): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)) {

    val context: Context = parent.context

    abstract fun onBindViewHolder(item: ITEM, position: Int)
}
