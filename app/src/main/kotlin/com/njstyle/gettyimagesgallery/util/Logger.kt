package com.njstyle.gettyimagesgallery.util

import android.util.Log
import com.njstyle.gettyimagesgallery.base.BaseApplication

object Logger {
    private fun getTag(customTag: String = "", isShowThreadName: Boolean = false): String {
        val stringBuilder = StringBuilder()
        var stackTrace: StackTraceElement? = null
        var simpleClassName = ""

        for (i in 5..7) {
            stackTrace = Thread.currentThread().stackTrace[i]
            simpleClassName = stackTrace.className.substringAfterLast(".").substringBefore("$")
            if (simpleClassName != this::class.java.simpleName) break
        }

        if (customTag.isNotEmpty()) {
            stringBuilder.append("[$customTag] ")
        }

        if (isShowThreadName) {
            stringBuilder.append("${Thread.currentThread().name}: ")
        }

        stringBuilder.append("$simpleClassName > " +
                "${stackTrace?.methodName}:${stackTrace?.lineNumber}")

        return stringBuilder.toString()
    }

    private fun isDebug() = BaseApplication.isDebug

    fun d(tag: String, msg: String) {
        if (isDebug()) Log.d(getTag(tag), msg)
    }

    fun d(msg: String = "called") {
        if (isDebug()) Log.d(getTag(), msg)
    }

    fun dt(msg: String = "called") {
        if (isDebug()) Log.d(getTag(isShowThreadName = true), msg)
    }

    fun e(tag: String, msg: String) {
        if (isDebug()) Log.e(getTag(tag), msg)
    }

    fun e(msg: String = "called") {
        if (isDebug()) Log.e(getTag(), msg)
    }

    fun et(msg: String = "called") {
        if (isDebug()) Log.e(getTag(isShowThreadName = true), msg)
    }
}