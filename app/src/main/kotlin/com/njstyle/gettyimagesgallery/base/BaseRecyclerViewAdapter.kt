package com.njstyle.gettyimagesgallery.base

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<ITEM, VIEW_HOLDER: BaseRecyclerViewHolder<ITEM>>: RecyclerView.Adapter<VIEW_HOLDER>() {

    private var items = ObservableArrayList<ITEM>()

    private val listItemChangedCallback = object : ObservableList.OnListChangedCallback<ObservableArrayList<ITEM>>() {
        override fun onChanged(sender: ObservableArrayList<ITEM>?) {
        }

        override fun onItemRangeRemoved(sender: ObservableArrayList<ITEM>?, positionStart: Int, itemCount: Int) {
            notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(
            sender: ObservableArrayList<ITEM>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
        }

        override fun onItemRangeInserted(sender: ObservableArrayList<ITEM>?, positionStart: Int, itemCount: Int) {
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(sender: ObservableArrayList<ITEM>?, positionStart: Int, itemCount: Int) {
            notifyItemRangeChanged(positionStart, itemCount)
        }
    }

    fun setItems(items: List<ITEM>) {
        this.items = items as ObservableArrayList<ITEM>

        addItemChangedCallback()
    }

    private fun addItemChangedCallback() {
        this.items.addOnListChangedCallback(listItemChangedCallback)
    }

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int): ITEM = items[position]

    fun onDestroy() {
        items.removeOnListChangedCallback(listItemChangedCallback)
    }
}
