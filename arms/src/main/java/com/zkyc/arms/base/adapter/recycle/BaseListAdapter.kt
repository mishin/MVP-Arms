package com.zkyc.arms.base.adapter.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/24 9:49
 * desc   :
 */
abstract class BaseListAdapter<T>(diff: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseListAdapter.ViewBindingViewHolder<ViewBinding>>(diff) {

    // 数据集
    private val mList = mutableListOf<T>()

    // 列表项点击事件
    private var mItemClickListener: OnItemClickListener<T>? = null

    // 保存item子控件id
    private val mChildClickViewIds = linkedSetOf<Int>()

    // 列表项子控件点击事件
    private var mItemChildClickListener: OnItemChildClickListener<T>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<ViewBinding> {
        val binding = createBinding(LayoutInflater.from(parent.context), parent, viewType)
        return ViewBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewBindingViewHolder<ViewBinding>, position: Int) {
        val data = mList[position]
        holder.itemView.setOnClickListener { mItemClickListener?.onItemClick(it, data, position) }
        if (null != mItemChildClickListener && mChildClickViewIds.isNotEmpty()) {
            mChildClickViewIds.forEach { id ->
                holder.itemView.findViewById<View>(id)?.setOnClickListener {
                    mItemChildClickListener?.onItemChildClick(it, data, position)
                }
            }
        }
        bindView(holder.binding, data, position)
    }

    /**
     * 生成ViewBinding实例
     */
    abstract fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        viewType: Int
    ): ViewBinding

    /**
     * 布局绑定
     */
    abstract fun bindView(binding: ViewBinding, data: T, position: Int)

    /**
     * 绑定监听事件
     */
    fun bingOnItemClickListener(listener: OnItemClickListener<T>) {
        mItemClickListener = listener
    }

    /**
     * 设置需要点击事件的子控件id
     */
    fun addChildClickViewIds(@IdRes vararg viewIds: Int) {
        viewIds.forEach { mChildClickViewIds.add(it) }
    }

    /**
     * 绑定item子控件点击事件
     */
    fun bindOnItemChildClickListener(listener: OnItemChildClickListener<T>) {
        mItemChildClickListener = listener
    }

    fun set(list: Collection<T>) {
        mList.clear()
        mList.addAll(list)
        submitList(mList)
    }

    fun add(item: T) {
        mList.add(item)
        submitList(mList)
    }

    fun add(position: Int, item: T) {
        mList.add(position, item)
        submitList(mList)
    }

    fun add(list: Collection<T>) {
        mList.addAll(list)
        submitList(mList)
    }

    fun add(position: Int, list: Collection<T>) {
        mList.addAll(position, list)
        submitList(mList)
    }

    fun remove(item: T) {
        mList.remove(item)
        submitList(mList)
    }

    fun removeAt(position: Int) {
        mList.removeAt(position)
        submitList(mList)
    }

    fun update(position: Int, item: T) {
        mList[position] = item
        submitList(mList)
    }

    fun update(position: Int, block: (T) -> T) {
        mList[position] = block.invoke(mList[position])
        submitList(mList)
    }

    fun get(position: Int): T {
        return mList[position]
    }

    fun clear() {
        mList.clear()
        submitList(mList)
    }

    /**
     * ViewBindingViewHolder
     */
    class ViewBindingViewHolder<VB : ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * item点击事件
     */
    interface OnItemClickListener<T> {
        fun onItemClick(v: View, data: T, position: Int)
    }

    /**
     * item子view点击事件
     */
    interface OnItemChildClickListener<T> {
        fun onItemChildClick(v: View, data: T, position: Int)
    }
}