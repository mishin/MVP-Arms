package com.zkyc.arms.library

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.zkyc.arms.R

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/27 16:35
 * desc   : LoadSir扩展-无数据状态页
 */
class EmptyCallback private constructor() : Callback() {

    companion object {
        fun newInstance() = EmptyCallback()
    }

    override fun onCreateView() = R.layout.loadsir_empty
}

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/27 16:44
 * desc   : LoadSir扩展-加载失败状态页
 */
class ErrorCallback private constructor() : Callback() {

    companion object {
        fun newInstance() = ErrorCallback()
    }

    override fun onCreateView() = R.layout.loadsir_error
}

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/27 16:45
 * desc   : LoadSir扩展-加载中状态页
 */
class LoadingCallback private constructor() : Callback() {

    companion object {
        fun newInstance() = LoadingCallback()
    }

    override fun onCreateView() = R.layout.loadsir_loading

    override fun onReloadEvent(context: Context?, view: View?) = true
}