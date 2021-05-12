package com.zkyc.arms.base.view

import androidx.annotation.StringRes

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/20 16:18
 * desc   : V层接口
 */
interface IView {

    /**
     * 弹出信息提示
     * @param msg 信息
     */
    fun toast(msg: String?)

    /**
     * 弹出提示信息
     * @param msgId 信息资源id
     */
    fun toast(@StringRes msgId: Int?)

    /**
     * 结束加载更多
     */
    fun loadMoreEnd()

    /**
     * 加载更多失败
     */
    fun loadMoreFail()

    /**
     * 显示加载中对话框
     */
    fun showProgress()

    /**
     * 隐藏加载中对话框
     */
    fun dismissProgress()

    /**
     * 展示加载中状态
     */
    fun showLoading()

    /**
     * 展示空数据状态
     */
    fun showEmpty()

    /**
     * 展示加载异常状态
     */
    fun showError()

    /**
     * 展示加载成功状态
     */
    fun showSuccess()
}