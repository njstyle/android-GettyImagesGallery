package com.njstyle.gettyimagesgallery.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseDataBindingFragment<BINDING: ViewDataBinding>: BaseFragment() {

    protected lateinit var binding: BINDING

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        rootView = binding.root

        return binding.root
    }
}