package com.njstyle.gettyimagesgallery.util

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.njstyle.gettyimagesgallery.R

@GlideModule
class GlideAppModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        builder.setDefaultRequestOptions(RequestOptions().apply {
            timeout(5000)
            error(R.drawable.ic_error)
        })
    }
}