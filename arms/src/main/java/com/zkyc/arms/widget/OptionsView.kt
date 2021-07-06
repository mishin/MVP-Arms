package com.zkyc.arms.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.zkyc.arms.R
import com.zkyc.arms.databinding.OptionsViewRecycleItemBinding
import com.zkyc.arms.extension.load
import com.zkyc.arms.library.BaseViewBindingAdapter

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/7/6 15:43
 * desc   :
 */
class OptionsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr),
    OnItemClickListener {

    companion object {
        // 单选标识
        private const val SINGLE = 1

        // 多选标识
        private const val MULTI = 2
    }

    // 适配器实例
    private val mOptionsAdapter: OptionsAdapter

    // 数据源
    private lateinit var mData: List<IOptionData>

    // 选择类型
    private var mSelectType: Int = SINGLE

    init {
        // 设置LayoutManager
        layoutManager = LinearLayoutManager(context)
        // 绑定适配器
        mOptionsAdapter = OptionsAdapter()
        adapter = mOptionsAdapter
        // 添加分割线
        context
            .dividerBuilder()
            .asSpace()
            .size(4, TypedValue.COMPLEX_UNIT_DIP)
            .build()
            .addTo(this)
        // 添加点击事件监听
        mOptionsAdapter.setOnItemClickListener(this)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (isEnabled) {
            with(mData) {
                if (SINGLE == mSelectType) {
                    find { it.checked }?.checked = false
                    get(position).checked = true
                    adapter.notifyDataSetChanged()
                } else {
                    with(get(position)) {
                        checked = checked.not()
                        adapter.notifyItemChanged(position, OptionsAdapter.sPayload)
                    }
                }
            }
        }
    }

    /**
     * 设置单选
     */
    fun setSingle() {
        this.mSelectType = SINGLE
    }

    /**
     * 设置多选
     */
    fun setMulti() {
        this.mSelectType = MULTI
    }

    /**
     * 填充数据
     */
    fun setList(data: List<IOptionData>?) {
        if (data.isNullOrEmpty()) return
        this.mData = data
        mOptionsAdapter.setList(mData)
    }

    /**
     * 获取选中项
     */
    fun getCheckedItems() = mData.filter { it.checked }

    /**
     * 获取是否有选项被选中
     */
    fun hasCheckedItem() = getCheckedItems().isNotEmpty()

    private class OptionsAdapter :
        BaseViewBindingAdapter<IOptionData, OptionsViewRecycleItemBinding>() {

        companion object {
            const val sPayload = 12 // 局部刷新标志
        }

        override fun createBinding(
            inflater: LayoutInflater,
            parent: ViewGroup?,
            viewType: Int,
        ): OptionsViewRecycleItemBinding {
            return OptionsViewRecycleItemBinding.inflate(inflater, parent, false)
        }

        override fun bindView(
            binding: OptionsViewRecycleItemBinding,
            data: IOptionData,
            position: Int,
        ) {
            // 选项编号
            val optionNumber = position + 1
            binding.tvOptionNumber.text =
                context.getString(R.string.options_view_option_number, optionNumber)
            // 选项内容
            binding.tvOptionContent.text = data.optionText
            // 图片
            with(binding.ivImage) {
                isVisible = !data.optionImageUrl.isNullOrEmpty()
                if (isVisible) {
                    load(data.optionImageUrl)
                }
            }
            // 是否选中
            binding.checkBox.isChecked = data.checked
        }

        override fun bindView(
            binding: OptionsViewRecycleItemBinding,
            data: IOptionData,
            position: Int,
            payloads: List<Any>,
        ) {
            super.bindView(binding, data, position, payloads)
            payloads.forEach {
                if (it is Int && it == sPayload) {
                    // 是否选中
                    binding.checkBox.isChecked = data.checked
                }
            }
        }
    }

    interface IOptionData {
        var checked: Boolean
        val optionText: String
        val optionImageUrl: String?
    }
}