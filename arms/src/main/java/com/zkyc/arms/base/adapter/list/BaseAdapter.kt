package com.zkyc.arms.base.adapter.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.viewbinding.ViewBinding

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/6/10 9:34
 * desc   :
 */
abstract class BaseAdapter<VB : ViewBinding, T> : BaseAdapter() {

    // 数据集
    private val mList = mutableListOf<T>()

    override fun getCount(): Int = mList.size

    override fun getItem(position: Int): T = mList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val binding: VB
        if (null == view) {
            binding = createBinding(LayoutInflater.from(parent?.context), parent)
            view = binding.root
            view.tag = binding
        } else {
            @Suppress("UNCHECKED_CAST")
            binding = view.tag as VB
        }
        bindView(binding, position)
        return view
    }

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): VB

    abstract fun bindView(binding: VB, position: Int)

    fun getList(): List<T> = mList

    val dataSize: Int get() = mList.size

    fun setNewList(collection: Collection<T>) {
        mList.clear()
        mList.addAll(collection)
        notifyDataSetChanged()
    }

    fun addItem(data: T) {
        mList.add(data)
        notifyDataSetChanged()
    }

    fun addItems(collection: Collection<T>) {
        mList.addAll(collection)
        notifyDataSetChanged()
    }

    fun remove(data: T) {
        mList.remove(data)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        mList.removeAt(position)
        notifyDataSetChanged()
    }

    fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }
}