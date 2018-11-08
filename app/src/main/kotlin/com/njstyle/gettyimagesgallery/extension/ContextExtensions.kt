package com.njstyle.gettyimagesgallery.extension

import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat
import android.support.v4.widget.CircularProgressDrawable
import com.njstyle.gettyimagesgallery.R

fun Context.createWaitProgressDrawable()
        = CircularProgressDrawable(this).also {
            it.setColorSchemeColors(ContextCompat.getColor(this, R.color.defaultColor))
            it.strokeWidth = 5f
            it.centerRadius = 30f
            it.start()
        }

fun Context.isNetworkConnected(): Boolean {
    (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.let { connectivityManager ->
        connectivityManager.activeNetworkInfo?.let { activeNetworkInfo ->
            if (!activeNetworkInfo.isConnected) {
                return false
            }
        } ?: return false
    }

    return true
}
