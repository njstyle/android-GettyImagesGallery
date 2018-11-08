package com.njstyle.gettyimagesgallery.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.njstyle.gettyimagesgallery.R
import com.njstyle.gettyimagesgallery.base.BaseActivity
import com.njstyle.gettyimagesgallery.base.ViewEventType
import com.njstyle.gettyimagesgallery.extension.isNetworkConnected
import com.njstyle.gettyimagesgallery.extension.replaceFragment
import com.njstyle.gettyimagesgallery.ui.image.ImageThumbnailFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var imageThumbnailFragment: ImageThumbnailFragment
    @Inject lateinit var mainViewModel: MainViewModel

    private var isNetworkErrorAlertDlgShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        mainViewModel.viewEventSender.observe(this, Observer(::receiveMainViewEvent))

        replaceImageThumbnailFragment()
    }

    private fun receiveMainViewEvent(event: ViewEventType<MainViewEvent>?) {
        if (event == null) return

        when (event.first) {
            MainViewEvent.NETWORK_ERROR -> showNetworkErrorAlertDlg()
        }

        mainViewModel.initEvent()
    }

    override fun onStart() {
        super.onStart()

        if (!isNetworkConnected()) showNetworkErrorAlertDlg()
    }

    private fun showNetworkErrorAlertDlg() {
        if (isNetworkErrorAlertDlgShowing) return

        isNetworkErrorAlertDlgShowing = true

        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.error))
            .setMessage(resources.getString(R.string.networkError))
            .setPositiveButton(resources.getString(R.string.confirm)) { _, _ ->
                isNetworkErrorAlertDlgShowing = false
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun replaceImageThumbnailFragment() {
        replaceFragment(R.id.contentFragment, imageThumbnailFragment)
    }
}
