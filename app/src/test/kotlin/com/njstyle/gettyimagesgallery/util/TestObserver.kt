package com.njstyle.gettyimagesgallery.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import org.junit.Assert
import org.junit.Assert.fail
import java.lang.Thread.sleep

class TestObserver<T> : Observer<T> {
    private var isTimeOut = false
    private var atLeast = -1

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }

    fun awaitCount(atLeast: Int, timeoutMillis: Long = 5000L) {
        val start = System.currentTimeMillis()
        this.atLeast = atLeast

        while (true) {
            if (observedValues.size >= atLeast) break

            if (timeoutMillis > 0L && System.currentTimeMillis() - start >= timeoutMillis) {
                isTimeOut = true
                break
            }

            sleep(10)
        }
    }

    fun assertNoTimeout() {
        if (isTimeOut) Assert.fail("Timeout -> observedValues.size: ${observedValues.size}, atLeast: $atLeast")
    }

    fun assertValues(actual: List<T>) {
        checkItemCount()

        if (observedValues.size != actual.size) {
            fail("size is difference -> observedValues.size: ${observedValues.size}, actual.size: ${actual.size}, atLeast: $atLeast, result: ${observedValues.size != actual.size}")
            return
        }

        observedValues.forEachIndexed { index, value ->
            Assert.assertEquals(value, actual[index])
        }
    }



    fun assertValue(actual: T) {
        checkItemCount()

        Assert.assertEquals(observedValues[0], actual)
    }

    private fun checkItemCount() {
        if (observedValues.count() == 0) Assert.fail("observedValues count is 0")
    }
}

fun <T> LiveData<T>.test() = TestObserver<T>().also {
    observeForever(it)
}