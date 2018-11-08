package com.njstyle.gettyimagesgallery.di.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import com.njstyle.gettyimagesgallery.ui.image.ImageThumbnailFragment
import com.njstyle.gettyimagesgallery.ui.image.ImageThumbnailViewModel
import com.njstyle.gettyimagesgallery.ui.image.list.ImageThumbnailListAdapter
import com.njstyle.gettyimagesgallery.ui.image.list.ImageThumbnailViewHolderFactory
import com.njstyle.gettyimagesgallery.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ImageThumbnailModule {

    @Provides
    fun provideImageThumbnailViewHolderFactory() = ImageThumbnailViewHolderFactory()

    @Provides
    fun provideImageThumbnailListAdapter(imageThumbnailViewHolderFactory: ImageThumbnailViewHolderFactory)
            = ImageThumbnailListAdapter(imageThumbnailViewHolderFactory)

    @Provides
    fun provideProfileViewModel(fragment: ImageThumbnailFragment, viewModelFactory: ViewModelProvider.Factory): ImageThumbnailViewModel
            = ViewModelProviders.of(fragment, viewModelFactory)[ImageThumbnailViewModel::class.java]

    @Provides
    fun provideMainViewModel(fragment: ImageThumbnailFragment, viewModelFactory: ViewModelProvider.Factory): MainViewModel
            = ViewModelProviders.of(fragment, viewModelFactory)[MainViewModel::class.java]
}