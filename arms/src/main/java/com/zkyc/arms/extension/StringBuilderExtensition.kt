package com.zkyc.arms.extension

/**
 * 不为空时插入数据到StringBuilder中
 */
fun StringBuilder.appendIfNotNull(any: Any?) {
    if (null != any) {
        append(any)
    }
}