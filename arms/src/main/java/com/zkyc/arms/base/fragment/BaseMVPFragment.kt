package com.zkyc.arms.base.fragment

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.zkyc.arms.base.presenter.IPresenter
import com.zkyc.arms.base.view.IView
import javax.inject.Inject

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/20 17:41
 * desc   :
 */
abstract class BaseMVPFragment<VB : ViewBinding, V : IView, P : IPresenter<V>> :
    BaseFragment<VB>() {

    @Inject
    lateinit var presenter: P

    override fun onInit(savedInstanceState: Bundle?) {
        initPresenter()
        super.onInit(savedInstanceState)
    }

    override fun onDestroy() {
        removePresenter()
        super.onDestroy()
    }

    protected open fun initPresenter() {
        @Suppress("UNCHECKED_CAST") presenter.bindView(this as V)
        lifecycle.addObserver(presenter)
    }

    protected open fun removePresenter() {
        lifecycle.removeObserver(presenter)
    }
}