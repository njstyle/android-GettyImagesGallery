package com.njstyle.gettyimagesgallery.di

import android.app.Application
import com.njstyle.gettyimagesgallery.base.BaseApplication
import com.njstyle.gettyimagesgallery.di.binder.ActivityBinderModule
import com.njstyle.gettyimagesgallery.di.binder.FragmentBinderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ActivityBinderModule::class,
            FragmentBinderModule::class,
            ViewModelModule::class,
            NetworkModule::class
        ])
interface AppComponent: AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}