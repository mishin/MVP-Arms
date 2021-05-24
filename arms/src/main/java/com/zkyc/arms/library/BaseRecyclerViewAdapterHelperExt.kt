package com.zkyc.arms.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/24 13:51
 * desc   :
 */
class ViewBindingHolder<VB : ViewBinding>(val binding: VB) : BaseViewHolder(binding.root)

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/24 14:12
 * desc   :
 */
abstract class BaseViewBindingAdapter<T, VB : ViewBinding> : BaseQuickAdapter<T, ViewBindingHolder<VB>>(0) {

    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingHolder<VB> {
        val binding = createBinding(LayoutInflater.from(parent.context), parent, viewType)
        return ViewBindingHolder(binding)
    }

    override fun convert(holder: ViewBindingHolder<VB>, item: T) {
        bindView(holder.binding, item)
    }

    /**
     * 生成ViewBinding实例
     */
    abstract fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        viewType: Int
    ): VB

    /**
     * 布局绑定
     */
    abstract fun bindView(binding: VB, data: T)
}

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/24 14:19
 * desc   :
 */
abstract class BaseViewBindingProvider<T, VB : ViewBinding> : BaseItemProvider<T>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = createBinding(LayoutInflater.from(parent.context), parent, viewType)
        return ViewBindingHolder(binding)
    }

    @Suppress("UNCHECKED_CAST")
    override fun convert(helper: BaseViewHolder, item: T) {
        if (helper is ViewBindingHolder<*>) {
            val binding = helper.binding as? VB ?: return
            bindView(binding, item)
        }
    }

    /**
     * 生成ViewBinding实例
     */
    abstract fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        viewType: Int
    ): VB

    /**
     * 布局绑定
     */
    abstract fun bindView(binding: VB, data: T)
}