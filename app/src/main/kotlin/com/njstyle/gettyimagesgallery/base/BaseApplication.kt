package com.njstyle.gettyimagesgallery.base

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.njstyle.gettyimagesgallery.di.DaggerAppComponent

class BaseApplication: DaggerApplication() {

    companion object {
        var isDebug = false
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        checkDebug()
    }

    private fun checkDebug() {
        val pm = applicationContext.packageManager
        try {
            val appInfo = pm.getApplicationInfo(applicationContext.packageName, 0)
            isDebug = (0 != appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)
        } catch (e: PackageManager.NameNotFoundException) {
            /* debuggable variable will remain false */
        }
    }
}
