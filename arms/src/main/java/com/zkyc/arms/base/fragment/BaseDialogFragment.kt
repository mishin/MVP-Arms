package com.zkyc.arms.base.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/6/21 11:16
 * desc   :
 */
abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    /**
     * 视图绑定实例
     */
    private var _mBinding: VB? = null
    protected val mBinding get() = _mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dialog?.window?.apply {
            val params = attributes
            params.gravity = Gravity.CENTER
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        _mBinding = onCreateVB(inflater, container)
        return _mBinding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 解析传递至本页的数据
        val bundle = arguments
        if (bundle != null) {
            onParseData(bundle)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 初始化
        onInit(savedInstanceState)
    }

    protected open fun onParseData(bundle: Bundle) {}

    protected abstract fun onCreateVB(inflater: LayoutInflater, container: ViewGroup?): VB

    protected open fun onInit(savedInstanceState: Bundle?) {}
}