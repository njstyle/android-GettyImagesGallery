package com.njstyle.gettyimagesgallery.base

import android.view.View
import dagger.android.support.DaggerFragment

abstract class BaseFragment: DaggerFragment() {
    protected abstract val layoutId: Int
    protected lateinit var rootView: View
}