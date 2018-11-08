package com.njstyle.gettyimagesgallery.ui.image

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.njstyle.gettyimagesgallery.R
import com.njstyle.gettyimagesgallery.constant.Constants.Companion.IMAGE_THUMBNAIL_ANIMATION_DURATION_MSEC
import com.njstyle.gettyimagesgallery.constant.Constants.Companion.IMAGE_THUMBNAIL_AUTO_HIDE_DELAY_SEC
import com.njstyle.gettyimagesgallery.ui.image.list.ImageThumbnailViewHolder
import com.njstyle.gettyimagesgallery.ui.main.MainActivity
import com.njstyle.gettyimagesgallery.util.RecyclerViewMatcher
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.hamcrest.Matchers.not
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class ImageThumbnailFragmentTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private lateinit var imageThumbnailFragment: ImageThumbnailFragment
    private lateinit var imageThumbnailViewModel: ImageThumbnailViewModel
    private var testObserver: TestObserver<Any>? = null

    @Before
    fun setup() {
        imageThumbnailFragment = activityTestRule.activity.imageThumbnailFragment
        imageThumbnailViewModel = imageThumbnailFragment.imageThumbnailViewModel
        imageThumbnailViewModel.testSubject = PublishSubject.create()
        testObserver = imageThumbnailViewModel.testSubject?.test()
    }

    private fun checkHtmlRequestAndParsingSuccess() {
        testObserver?.awaitCount(1)

        Assert.assertEquals(false, imageThumbnailViewModel.isWaiting.value)
    }

    private fun getItemLastIdx(): Int {
        val lastIdx = (imageThumbnailViewModel.items.value?.size ?: 0) - 1

        Assert.assertNotEquals(-1, lastIdx)

        return lastIdx
    }

    private fun scrollToIdx(idx: Int) {
        onView(withId(R.id.rvImageThumbnailList))
            .perform(RecyclerViewActions.scrollToPosition<ImageThumbnailViewHolder>(idx))
    }

    private fun getRecyclerViewHolderItemMatcher(idx: Int, layoutId: Int)
            = RecyclerViewMatcher(R.id.rvImageThumbnailList).atPositionOnView(idx, layoutId)

    @Test
    fun scrollDownToLast_checkImageCaptionNotDisplayed() {
        checkHtmlRequestAndParsingSuccess()

        val lastIdx = getItemLastIdx()

        for (i in 0 until lastIdx step 30) {
            scrollToIdx(i)

            onView(getRecyclerViewHolderItemMatcher(i, R.id.tvImageCaption))
                .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))

            sleep(100)
        }

        onView(withId(R.id.rvImageThumbnailList))
            .perform(RecyclerViewActions.scrollToPosition<ImageThumbnailViewHolder>(lastIdx))
    }

    @Test
    fun scrollDownToLast_clickViewHolder_checkImageCaption() {
        checkHtmlRequestAndParsingSuccess()

        val lastIdx = getItemLastIdx()
        val sleepTimeMsec = IMAGE_THUMBNAIL_AUTO_HIDE_DELAY_SEC * 1000 + IMAGE_THUMBNAIL_ANIMATION_DURATION_MSEC + 100L

        listOf(0, lastIdx / 2, lastIdx).forEach { idx ->
            scrollToIdx(idx)

            onView(getRecyclerViewHolderItemMatcher(idx, R.id.iv)).perform(click())

            onView(getRecyclerViewHolderItemMatcher(idx, R.id.tvImageCaption))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

            onView(getRecyclerViewHolderItemMatcher(idx, R.id.tvImageCaption))
                .check(ViewAssertions.matches(withText(imageThumbnailViewModel.items.value?.get(idx)?.imageCaption)))

            sleep(sleepTimeMsec)

            onView(getRecyclerViewHolderItemMatcher(idx, R.id.tvImageCaption))
                .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
        }
    }
}