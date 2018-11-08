package com.njstyle.gettyimagesgallery.di.binder

import com.njstyle.gettyimagesgallery.di.ui.MainModule
import com.njstyle.gettyimagesgallery.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinderModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity
}
