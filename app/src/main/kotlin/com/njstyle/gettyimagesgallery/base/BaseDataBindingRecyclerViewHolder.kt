package com.njstyle.gettyimagesgallery.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseDataBindingRecyclerViewHolder<BINDING: ViewDataBinding, ITEM>
        (parent: ViewGroup, layoutId: Int): BaseRecyclerViewHolder<ITEM>(parent, layoutId) {

    var binding: BINDING = DataBindingUtil.bind(itemView)
            ?: DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
}