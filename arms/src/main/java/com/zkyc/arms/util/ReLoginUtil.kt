package com.zkyc.arms.util

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/14 16:00
 * desc   :
 */
object ReLoginUtil {

    private var mLogout: (() -> Unit)? = null
    private var mToLoginPage: (() -> Unit)? = null

    fun init(logout: (() -> Unit), toLoginPage: (() -> Unit)) {
        mLogout = logout
        mToLoginPage = toLoginPage
    }

    /**
     * 重新登录
     */
    fun reLogin() {
        mLogout?.invoke()
        mToLoginPage?.invoke()
    }
}