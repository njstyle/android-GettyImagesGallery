package com.njstyle.gettyimagesgallery.ui.image.list

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.njstyle.gettyimagesgallery.R
import com.njstyle.gettyimagesgallery.base.BaseDataBindingRecyclerViewHolder
import com.njstyle.gettyimagesgallery.constant.Constants.Companion.IMAGE_THUMBNAIL_ANIMATION_DURATION_MSEC
import com.njstyle.gettyimagesgallery.constant.Constants.Companion.IMAGE_THUMBNAIL_AUTO_HIDE_DELAY_SEC
import com.njstyle.gettyimagesgallery.databinding.ItemImageThumbnailBinding
import com.njstyle.gettyimagesgallery.ui.image.constant.ImageThumbnailItem
import com.njstyle.gettyimagesgallery.util.Logger
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class ImageThumbnailViewHolder(parent: ViewGroup): BaseDataBindingRecyclerViewHolder<ItemImageThumbnailBinding, ImageThumbnailItem>
    (parent, R.layout.item_image_thumbnail) {

    private var timerDisposable: Disposable? = null


    override fun onBindViewHolder(item: ImageThumbnailItem, position: Int) {
        binding.imageThumbnailItem = item
        binding.iv.setOnClickListener { showImageCaptionWithAnimation() }
    }

    private fun showImageCaptionWithAnimation() {
        if (binding.tvImageCaption.visibility == View.VISIBLE) return
        binding.tvImageCaption.visibility = View.VISIBLE

        Logger.d()

        val translateAnimation = TranslateAnimation(0F, 0F, 100F, 0F)
        translateAnimation.duration = IMAGE_THUMBNAIL_ANIMATION_DURATION_MSEC
        translateAnimation.fillAfter = true
        translateAnimation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                timerDisposable = Completable.timer(IMAGE_THUMBNAIL_AUTO_HIDE_DELAY_SEC, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { hideImageCaptionWithAnimation() }
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        binding.tvImageCaption.startAnimation(translateAnimation)
    }

    private fun hideImageCaptionWithAnimation() {
        if (binding.tvImageCaption.visibility == View.GONE) return
        binding.tvImageCaption.visibility = View.GONE

        Logger.d()

        val translateAnimation = TranslateAnimation(0F, 0F, 0F, 100F)
        translateAnimation.duration = IMAGE_THUMBNAIL_ANIMATION_DURATION_MSEC
        translateAnimation.fillAfter = true

        binding.tvImageCaption.startAnimation(translateAnimation)
    }

    fun onRecycled() {
        Logger.d()

        timerDisposable?.dispose()
        binding.tvImageCaption.clearAnimation()
        binding.tvImageCaption.visibility = View.GONE
    }
}
