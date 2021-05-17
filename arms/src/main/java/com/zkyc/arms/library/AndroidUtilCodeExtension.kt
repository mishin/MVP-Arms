package com.zkyc.arms.library

import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.StringUtils

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/17 15:36
 * desc   :
 */

/**
 * 将String资源转换成字符串
 */
fun Int.toStr(): String = StringUtils.getString(this)

/**
 * 将color资源id转换成颜色
 */
fun Int.toColor(): Int = ColorUtils.getColor(this)