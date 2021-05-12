package com.zkyc.arms.base.presenter

import androidx.lifecycle.LifecycleObserver
import com.zkyc.arms.base.view.IView

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/20 16:27
 * desc   : P层接口
 */
interface IPresenter<in V : IView> : LifecycleObserver {
    fun bindView(view: V)
    fun onCreate()
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
}