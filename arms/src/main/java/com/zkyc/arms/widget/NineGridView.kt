package com.zkyc.arms.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import androidx.core.content.res.use
import com.zkyc.arms.R
import com.zkyc.arms.base.adapter.list.BaseAdapter
import com.zkyc.arms.databinding.NgvItemBinding
import com.zkyc.arms.extension.load
import com.zkyc.arms.library.TransfereeUtil

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/6/9 18:55
 * desc   :
 */
class NineGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : GridView(context, attrs, defStyleAttr),
    AdapterView.OnItemClickListener {

    companion object {
        private const val DEFAULT_EDITABLE = false
        private const val DEFAULT_MAX_ITEM_COUNT = 9
    }

    // 是否允许编辑
    private var mEditable: Boolean = DEFAULT_EDITABLE

    // 图片最大张数
    private var mMaxItemCount: Int = DEFAULT_MAX_ITEM_COUNT

    // 适配器
    private val mAdapter: NineAdapter

    // 点击事件
    private var mListener: OnAddPictureClickListener? = null

    init {
        // 初始化自定义参数
        context.obtainStyledAttributes(attrs, R.styleable.NineGridView).use {
            mEditable = it.getBoolean(R.styleable.NineGridView_ngv_editable, DEFAULT_EDITABLE)
            mMaxItemCount =
                it.getInt(R.styleable.NineGridView_ngv_maxItemCount, DEFAULT_MAX_ITEM_COUNT)
        }
        // 绑定适配器
        mAdapter = NineAdapter().apply {
            maxItemCount = mMaxItemCount
            editable = mEditable
        }
        adapter = mAdapter
        // 绑定监听事件
        onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (mEditable && position >= mAdapter.dataSize) {
            mListener?.addPictureClick()
        } else {
            TransfereeUtil.getInstance()
                .bind(context, this, R.id.iv_image, position, mAdapter.getList())
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        TransfereeUtil.getInstance().destroy()
    }

    val missingCount: Int
        get() = mMaxItemCount - mAdapter.dataSize

    fun addData(data: String?) {
        if (data.isNullOrEmpty()) return
        mAdapter.addItem(data)
    }

    fun addData(list: List<String>?) {
        if (list.isNullOrEmpty()) return
        mAdapter.addItems(list)
    }

    fun setNewList(list: List<String>?) {
        if (list.isNullOrEmpty()) return
        mAdapter.setNewList(list)
    }

    fun setEditable(editable: Boolean) {
        mAdapter.editable = editable
    }

    fun setMaxItemCount(count: Int) {
        mAdapter.maxItemCount = count
    }

    fun setOnAddPictureClickListener(listener: OnAddPictureClickListener) {
        mListener = listener
    }

    interface OnAddPictureClickListener {
        fun addPictureClick()
    }

    /**
     * 适配器
     */
    private class NineAdapter : BaseAdapter<NgvItemBinding, String>() {

        var maxItemCount: Int = 9

        var editable: Boolean = false
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getCount(): Int {
            if (editable && dataSize < maxItemCount) {
                return dataSize + 1
            }
            return super.getCount()
        }

        override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): NgvItemBinding {
            return NgvItemBinding.inflate(inflater, parent, false)
        }

        override fun bindView(binding: NgvItemBinding, position: Int) {
            // 允许编辑
            if (editable) {
                // 已添加图片数量未达上限
                if (dataSize < maxItemCount) {
                    // 展示已添加图片
                    if (position < dataSize) {
                        binding.btnDelete.visibility = View.VISIBLE
                        binding.ivImage.load(getItem(position))
                    }
                    // 展示添加图片按钮
                    else {
                        binding.btnDelete.visibility = View.INVISIBLE
                        binding.ivImage.load(R.drawable.ngv_ic_plus)
                    }
                }
                // 图片数量达到上限
                else {
                    binding.btnDelete.visibility = View.VISIBLE
                    binding.ivImage.load(getItem(position))
                }
            } else {
                binding.btnDelete.visibility = View.INVISIBLE
                binding.ivImage.load(getItem(position))
            }
            // 添加点击事件
            binding.btnDelete.setOnClickListener { removeAt(position) }
        }
    }
}