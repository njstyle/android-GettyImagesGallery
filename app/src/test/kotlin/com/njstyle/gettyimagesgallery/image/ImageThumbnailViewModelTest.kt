package com.njstyle.gettyimagesgallery.image

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.njstyle.gettyimagesgallery.constant.Constants.Companion.BASE_URL
import com.njstyle.gettyimagesgallery.ui.image.ImageThumbnailViewModel
import com.njstyle.gettyimagesgallery.ui.image.constant.ImageThumbnailItem
import com.njstyle.gettyimagesgallery.ui.image.constant.ImageThumbnailViewEvent
import com.njstyle.gettyimagesgallery.util.JsoupManager
import com.njstyle.gettyimagesgallery.util.TestSchedulersRule
import com.njstyle.gettyimagesgallery.util.test
import io.reactivex.subjects.PublishSubject
import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doThrow
import org.mockito.MockitoAnnotations
import java.io.File
import java.lang.RuntimeException

class ImageThumbnailViewModelTest {
    @Mock
    private lateinit var jsoupManager: JsoupManager

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = TestSchedulersRule()

    private lateinit var imageThumbnailViewModel: ImageThumbnailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        imageThumbnailViewModel = ImageThumbnailViewModel(jsoupManager)
        imageThumbnailViewModel.testSubject = PublishSubject.create()
    }

    @Test
    fun givenHtml_whenRequestImageThumbnailList_thenSuccess() {
        val test = ImageThumbnailViewModelTest::class.java.getResource("/test.html").toURI()

        `when`(jsoupManager.getDocumentByUrl(ArgumentMatchers.anyString())).thenReturn(Jsoup.parse(File(test), "UTF-8"))

        val testObserver = imageThumbnailViewModel.testSubject?.test()

        imageThumbnailViewModel.requestImageThumbnailList()

        testObserver?.awaitCount(1)

        assertEquals(false, imageThumbnailViewModel.isWaiting.value)

        val items = imageThumbnailViewModel.items.value
        assertEquals(521, items!!.size)

        val valueVerifyList = arrayListOf(
            ImageThumbnailItem("$BASE_URL/Images/Thumbnails/1565/156562.jpg", "A Helping Hand"),
            ImageThumbnailItem("$BASE_URL/Images/Thumbnails/1527/152701.jpg", "Black Tie Evening"),
            ImageThumbnailItem("$BASE_URL/Images/Thumbnails/1565/156597.jpg", "Yachting In Lyford Cay"),
            ImageThumbnailItem("$BASE_URL/Images/Thumbnails/1525/152506.jpg", "Kaufmann Desert House"),
            ImageThumbnailItem("$BASE_URL/Images/Thumbnails/1340/134066.jpg", "Zeffirelli At Home")
        )

        valueVerifyList.forEach { verifyItem ->
            val findItem = items.find { it == verifyItem }

            assertNotNull(findItem)
        }

        assertEquals(true, items.first() == valueVerifyList.first())
        assertEquals(true, items.last() == valueVerifyList.last())
    }

    @Test
    fun givenDocument_whenRequestImageThumbnailList_thenFail() {
        doThrow(RuntimeException()).`when`(jsoupManager).getDocumentByUrl(ArgumentMatchers.anyString())

        val waitObserver = imageThumbnailViewModel.testSubject?.test()
        val viewEventObserver = imageThumbnailViewModel.viewEventSender.test()

        imageThumbnailViewModel.requestImageThumbnailList()

        waitObserver?.awaitCount(1)

        viewEventObserver.assertValue(ImageThumbnailViewEvent.API_ERROR to 0)

        assertEquals(true, imageThumbnailViewModel.isWaiting.value)

        val items = imageThumbnailViewModel.items.value
        assertEquals(0, items!!.size)
    }
}
