package com.njstyle.gettyimagesgallery.di.binder

import com.njstyle.gettyimagesgallery.di.ui.ImageThumbnailModule
import com.njstyle.gettyimagesgallery.ui.image.ImageThumbnailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBinderModule {

    @ContributesAndroidInjector(modules = [ImageThumbnailModule::class])
    abstract fun bindImageThumbnailFragment(): ImageThumbnailFragment
}