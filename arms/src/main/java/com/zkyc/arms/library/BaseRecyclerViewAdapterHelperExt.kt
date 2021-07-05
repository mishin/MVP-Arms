package com.zkyc.arms.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.provider.BaseNodeProvider
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
abstract class BaseViewBindingAdapter<T, VB : ViewBinding> :
    BaseQuickAdapter<T, ViewBindingHolder<VB>>(0) {

    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int,
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
        viewType: Int,
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
        viewType: Int,
    ): VB

    /**
     * 布局绑定
     */
    abstract fun bindView(binding: VB, data: T)
}

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/7/5 11:37
 * desc   :
 */
abstract class BaseViewBindingNodeProvider<T, VB : ViewBinding> : BaseNodeProvider() {

    override val layoutId: Int
        get() = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = createBinding(LayoutInflater.from(parent.context), parent, viewType)
        return ViewBindingHolder(binding)
    }

    @Suppress("UNCHECKED_CAST")
    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        if (helper is ViewBindingHolder<*>) {
            val binding = helper.binding as? VB ?: return
            val data = item as? T ?: return
            bindView(binding, data)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun convert(helper: BaseViewHolder, item: BaseNode, payloads: List<Any>) {
        super.convert(helper, item, payloads)
        if (helper is ViewBindingHolder<*>) {
            val binding = helper.binding as? VB ?: return
            val data = item as? T ?: return
            bindView(binding, data, payloads)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        super.onClick(helper, view, data, position)
        if (helper is ViewBindingHolder<*>) {
            val binding = helper.binding as? VB ?: return
            val item = data as? T ?: return
            onClick(binding, view, item, position)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onChildClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        super.onChildClick(helper, view, data, position)
        if (helper is ViewBindingHolder<*>) {
            val binding = helper.binding as? VB ?: return
            val item = data as? T ?: return
            onChildClick(binding, view, item, position)
        }
    }

    /**
     * 生成ViewBinding实例
     */
    abstract fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        viewType: Int,
    ): VB

    /**
     * 布局绑定
     */
    open fun bindView(binding: VB, data: T) {}

    open fun bindView(binding: VB, data: T, payloads: List<Any>) {}

    open fun onClick(binding: VB, view: View, data: T, position: Int) {}

    open fun onChildClick(binding: VB, view: View, data: T, position: Int) {}
}