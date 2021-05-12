package com.zkyc.arms.extension

/**
 * value不为空时插入到map中
 */
fun <K, V> MutableMap<K, V>.putIfNotNull(k: K, v: V?) {
    if (null != v) {
        put(k, v)
    }
}