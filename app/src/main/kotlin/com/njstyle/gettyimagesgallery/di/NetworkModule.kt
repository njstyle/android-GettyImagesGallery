package com.njstyle.gettyimagesgallery.di

import com.njstyle.gettyimagesgallery.util.JsoupManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideJsoupManager() = JsoupManager()
}