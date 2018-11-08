package com.njstyle.gettyimagesgallery.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

open class BaseViewModel<VIEW_EVENT>: ViewModel() {

    private val _viewEventSender by lazy { MutableLiveData<ViewEventType<VIEW_EVENT>>() }

    val viewEventSender: LiveData<ViewEventType<VIEW_EVENT>>
        get() = _viewEventSender

    fun sendViewEvent(viewEvent: VIEW_EVENT, data: Any) {
        _viewEventSender.value = viewEvent to data
    }

    fun initEvent() {
        _viewEventSender.value = null
    }
}

typealias ViewEventType<VIEW_EVENT> = Pair<VIEW_EVENT, Any>
