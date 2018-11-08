package com.njstyle.gettyimagesgallery.extension

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment) {
    supportFragmentManager.inTransaction{ replace(frameId, fragment) }
}
