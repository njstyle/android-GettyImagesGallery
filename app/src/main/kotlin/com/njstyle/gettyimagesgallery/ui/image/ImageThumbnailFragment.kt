package com.njstyle.gettyimagesgallery.ui.image

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njstyle.gettyimagesgallery.R
import com.njstyle.gettyimagesgallery.base.BaseDataBindingFragment
import com.njstyle.gettyimagesgallery.base.ViewEventType
import com.njstyle.gettyimagesgallery.databinding.FragmentImageThumbnailBinding
import com.njstyle.gettyimagesgallery.extension.isNetworkConnected
import com.njstyle.gettyimagesgallery.ui.image.constant.ImageThumbnailViewEvent
import com.njstyle.gettyimagesgallery.ui.image.list.ImageThumbnailListAdapter
import com.njstyle.gettyimagesgallery.ui.main.MainViewEvent
import com.njstyle.gettyimagesgallery.ui.main.MainViewModel
import com.njstyle.gettyimagesgallery.ui.util.GridDividerDecoration
import kotlinx.android.synthetic.main.fragment_image_thumbnail.view.*
import javax.inject.Inject

class ImageThumbnailFragment: BaseDataBindingFragment<FragmentImageThumbnailBinding>() {

    @Inject lateinit var imageThumbnailViewModel: ImageThumbnailViewModel
    @Inject lateinit var imageThumbnailListAdapter: ImageThumbnailListAdapter
    @Inject lateinit var mainViewModel: MainViewModel

    override val layoutId = R.layout.fragment_image_thumbnail

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        initDataBinding()
        initRecyclerView()

        imageThumbnailViewModel.viewEventSender.observe(this, Observer(::receiveImageThumbnailViewEvent))
        imageThumbnailViewModel.requestImageThumbnailList()

        return rootView
    }

    override fun onStart() {
        super.onStart()

        imageThumbnailViewModel.isWaiting.value?.let { isWaiting ->
            imageThumbnailViewModel.items.value?.let { items ->
                if (isWaiting && items.size > 0) {
                    imageThumbnailViewModel.setWaiting(false)
                }
            }
        }
    }

    private fun initDataBinding() {
        binding.imageThumbnailViewModel = imageThumbnailViewModel
        binding.setLifecycleOwner(this)
    }

    private fun initRecyclerView() {
        val spanCount = calculateGridSpanCount()

        rootView.rvImageThumbnailList.layoutManager = GridLayoutManager(this.activity, spanCount)
        context?.let { ctx ->
            rootView.rvImageThumbnailList.addItemDecoration(
                GridDividerDecoration(
                    resources.getDimensionPixelSize(R.dimen.image_thumbnail_grid_divider_size),
                    ContextCompat.getColor(ctx, R.color.image_thumbnail_grid_divider_color),
                    spanCount
                )
            )
        }
        rootView.rvImageThumbnailList.adapter = imageThumbnailListAdapter
    }

    private fun refresh() {
        imageThumbnailViewModel.setWaiting(true)
        imageThumbnailViewModel.deleteAllItems()
        imageThumbnailViewModel.requestImageThumbnailList()
    }

    private fun receiveImageThumbnailViewEvent(event: ViewEventType<ImageThumbnailViewEvent>?) {
        if (event == null) return

        when (event.first) {
            ImageThumbnailViewEvent.API_ERROR -> {
                if (context?.isNetworkConnected() == false) mainViewModel.sendViewEvent(MainViewEvent.NETWORK_ERROR, 0)
                else refresh()
            }
        }
    }

    private fun calculateGridSpanCount(): Int {
        val displayWidth = resources.displayMetrics.widthPixels
        val itemSize = resources.getDimensionPixelSize(R.dimen.image_thumbnail_item_size)
        val gridSpanCount = displayWidth / itemSize

        return if (gridSpanCount < 1) 1 else gridSpanCount
    }

    override fun onDestroyView() {
        imageThumbnailListAdapter.onDestroy()

        super.onDestroyView()
    }
}
