package com.zkyc.example.feature.toolbar

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.zkyc.arms.base.fragment.BaseFragment
import com.zkyc.example.R
import com.zkyc.example.databinding.ToolbarFragmentBinding

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/14 10:29
 * desc   :
 */
class ToolbarFragment : BaseFragment<ToolbarFragmentBinding>() {
    override fun onCreateVB(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ToolbarFragmentBinding.inflate(inflater, container, false)

    override fun getMenuId() = R.menu.toolbar

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.test -> {
                toast("测试菜单")
                return true
            }
        }
        return super.onMenuItemClick(item)
    }
}