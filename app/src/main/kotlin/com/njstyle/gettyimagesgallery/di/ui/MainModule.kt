package com.njstyle.gettyimagesgallery.di.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import com.njstyle.gettyimagesgallery.ui.image.ImageThumbnailFragment
import com.njstyle.gettyimagesgallery.ui.main.MainActivity
import com.njstyle.gettyimagesgallery.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideImageThumbnailFragment() = ImageThumbnailFragment()

    @Provides
    fun provideMainViewModel(activity: MainActivity, viewModelFactory: ViewModelProvider.Factory): MainViewModel
            = ViewModelProviders.of(activity, viewModelFactory)[MainViewModel::class.java]
}