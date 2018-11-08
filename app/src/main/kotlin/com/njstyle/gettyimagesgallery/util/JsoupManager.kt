package com.njstyle.gettyimagesgallery.util

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

open class JsoupManager {
    open fun getDocumentByUrl(url: String): Document
            = Jsoup.connect(url).get()
}
