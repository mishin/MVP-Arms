package com.zkyc.arms.util

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/14 16:00
 * desc   :
 */
object ReLoginUtil {

    private var mClearCache: (() -> Unit)? = null
    private var mToLoginPage: (() -> Unit)? = null

    fun init(clearCache: (() -> Unit), toLoginPage: (() -> Unit)) {
        mClearCache = clearCache
        mToLoginPage = toLoginPage
    }

    /**
     * 重新登录
     */
    fun reLogin() {
        mClearCache?.invoke()
        mToLoginPage?.invoke()
    }
}