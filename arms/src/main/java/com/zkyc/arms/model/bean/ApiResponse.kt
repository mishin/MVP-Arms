package com.zkyc.arms.model.bean

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/27 17:29
 * desc   :
 */
data class ApiResponse<out T>(val code: Int, val message: String, val data: T?) {

    companion object {
        /**
         * 请求成功标志
         */
        private const val REQUEST_SUCCEEDED = 1
    }

    /**
     * 判断请求是否成功
     */
    fun success() = REQUEST_SUCCEEDED == code
}