package com.zkyc.arms.extension

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.TextView

/**
 * 切换控件密码转换方法
 */
fun TextView.switchTransformationMethod() {
    val hideMethod = HideReturnsTransformationMethod.getInstance()
    transformationMethod = if (hideMethod == transformationMethod) {
        PasswordTransformationMethod.getInstance()
    } else {
        hideMethod
    }
}

/**
 * 清空控件数据及tag
 */
fun TextView.clear() {
    text = null
    tag = null
}