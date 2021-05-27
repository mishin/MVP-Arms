package com.zkyc.arms.base.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.zkyc.arms.base.presenter.IPresenter
import com.zkyc.arms.base.view.IView
import javax.inject.Inject

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/20 16:37
 * desc   :
 */
abstract class BaseMVPActivity<VB : ViewBinding, V : IView, P : IPresenter<V>> :
    BaseActivity<VB>() {

    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        @Suppress("UNCHECKED_CAST") presenter.bindView(this as V)
        lifecycle.addObserver(presenter)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        lifecycle.removeObserver(presenter)
        super.onDestroy()
    }
}