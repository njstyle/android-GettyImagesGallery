package com.njstyle.gettyimagesgallery.ui.image

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.support.annotation.VisibleForTesting
import com.njstyle.gettyimagesgallery.base.BaseDisposableViewModel
import com.njstyle.gettyimagesgallery.constant.Constants.Companion.BASE_URL
import com.njstyle.gettyimagesgallery.ui.image.constant.ImageThumbnailItem
import com.njstyle.gettyimagesgallery.ui.image.constant.ImageThumbnailViewEvent
import com.njstyle.gettyimagesgallery.util.JsoupManager
import com.njstyle.gettyimagesgallery.util.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.jsoup.select.Elements

class ImageThumbnailViewModel(private val jsoupManager: JsoupManager) : BaseDisposableViewModel<ImageThumbnailViewEvent>() {

    private val _isWaiting = MutableLiveData<Boolean>().apply { value = true }
    val isWaiting: LiveData<Boolean> = _isWaiting

    private val itemList = ObservableArrayList<ImageThumbnailItem>()
    private val _items = MutableLiveData<ObservableArrayList<ImageThumbnailItem>>().apply { value = itemList }
    val items: LiveData<ObservableArrayList<ImageThumbnailItem>> = _items

    fun requestImageThumbnailList() {
        Logger.d()

        addDisposable(Observable.just(jsoupManager)
            .map { it.getDocumentByUrl("$BASE_URL/collections/archive/slim-aarons.aspx") }
            .map { it.select(".gallery-wrap") }
            .map { it.select(".gallery-item-group") }
            .flatMap { elements ->
                Observable.zip(
                    getThumbnailImageUrl(elements),
                    getImageCaption(elements),
                    BiFunction<String, String, Pair<String, String>> { imageThumbnailUrl, imageCaption -> imageThumbnailUrl to imageCaption })
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setWaiting(false)
                if (itemList.size == 0) Logger.d("url: $BASE_URL${it.first} caption: ${it.second}")
                itemList.add(ImageThumbnailItem(BASE_URL + it.first, it.second))
            }, {
                Logger.d()
                sendViewEvent(ImageThumbnailViewEvent.API_ERROR, 0)
            }, {
                testSubject?.onNext(0)
            })
        )
    }

    private fun getThumbnailImageUrl(elements: Elements) = Observable.just(elements)
        .map { it.select(".picture") }
        .concatMapIterable { it.tagName("img") }
        .map { it.attr("src") }
        .subscribeOn(Schedulers.io())

    private fun getImageCaption(elements: Elements) = Observable.just(elements)
        .map { it.select(".gallery-item-caption") }
        .concatMapIterable { it.select("a") }
        .map { it.text() }
        .subscribeOn(Schedulers.io())

    fun setWaiting(isWaiting: Boolean) {
        if (_isWaiting.value != isWaiting) _isWaiting.value = isWaiting
    }

    fun deleteAllItems() = itemList.clear()

    @VisibleForTesting
    var testSubject: PublishSubject<Any>? = null
}