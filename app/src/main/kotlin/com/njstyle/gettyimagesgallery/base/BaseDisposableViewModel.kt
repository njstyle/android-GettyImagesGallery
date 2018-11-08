package com.njstyle.gettyimagesgallery.base

import com.njstyle.gettyimagesgallery.util.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseDisposableViewModel<VIEW_EVENT>: BaseViewModel<VIEW_EVENT>() {

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    override fun onCleared() {
        Logger.d()

        compositeDisposable.clear()
        super.onCleared()
    }
}