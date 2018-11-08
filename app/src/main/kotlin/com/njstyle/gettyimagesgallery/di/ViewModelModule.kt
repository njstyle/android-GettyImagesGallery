package com.njstyle.gettyimagesgallery.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.njstyle.gettyimagesgallery.ui.image.ImageThumbnailViewModel
import com.njstyle.gettyimagesgallery.ui.main.MainViewModel
import com.njstyle.gettyimagesgallery.util.JsoupManager
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(
            providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return requireNotNull(providers[modelClass as Class<out ViewModel>]).get() as T
        }
    }

    @Provides
    @Singleton
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun provideMainViewModel(): ViewModel = MainViewModel()

    @Provides
    @IntoMap
    @ViewModelKey(ImageThumbnailViewModel::class)
    fun provideProfileViewModel(jsoupManager: JsoupManager): ViewModel = ImageThumbnailViewModel(jsoupManager)
}
