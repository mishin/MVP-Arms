package com.zkyc.arms.extension

import android.content.Context
import android.widget.ImageView
import com.zkyc.arms.library.GlideApp

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/18 9:59
 * desc   : 加载图片
 */
fun ImageView.load(any: Any?, defaultImgId: Int? = null) {
    load(this.context, any, defaultImgId)
}

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/18 9:59
 * desc   : 加载图片
 */
fun ImageView.load(context: Context, any: Any?, placeholder: Int? = null) {
    GlideApp.with(context)
        .load(any)
        .apply {
            if (null != placeholder) {
                placeholder(placeholder)
            }
        }
        .into(this)
}