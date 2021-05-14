package com.zkyc.example

import com.zkyc.arms.base.BaseApplication
import com.zkyc.arms.util.ReLoginUtil
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/11 17:47
 * desc   :
 */
@HiltAndroidApp
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ReLoginUtil.init({
            Timber.d("清空缓存")
        }, {
            Timber.d("跳转至登录页")
        })
    }
}