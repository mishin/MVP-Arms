package com.zkyc.arms.library

import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/17 15:36
 * desc   :
 */

/**
 * 获取字符资源
 */
fun Int.toStr(): String = StringUtils.getString(this)

/**
 * 获取颜色
 */
fun Int.toColor(): Int = ColorUtils.getColor(this)

/**
 * 获取 Drawable
 */
fun Int.toDrawable(): Drawable = ResourceUtils.getDrawable(this)

/**
 * 获取字符数组资源
 */
fun Int.toStringArray(): Array<String> = StringUtils.getStringArray(this)

/**
 * px转dp
 */
val Int.dp: Int
    get() = toFloat().dp

/**
 * px转dp
 */
val Float.dp: Int
    get() = SizeUtils.px2dp(this)

/**
 * dp转px
 */
val Int.px: Int
    get() = toFloat().px

/**
 * dp转px
 */
val Float.px: Int
    get() = SizeUtils.dp2px(this)