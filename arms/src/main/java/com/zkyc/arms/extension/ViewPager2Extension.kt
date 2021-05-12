package com.zkyc.arms.extension

import androidx.viewpager2.widget.ViewPager2

/**
 * 翻到下一页
 */
fun ViewPager2.next() {
    currentItem = ++currentItem
}

/**
 * 翻倒上一页
 */
fun ViewPager2.previous() {
    currentItem = --currentItem
}