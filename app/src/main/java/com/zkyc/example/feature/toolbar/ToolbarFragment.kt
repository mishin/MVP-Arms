package com.zkyc.example.feature.toolbar

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.zkyc.arms.annotation.UseEventBus
import com.zkyc.arms.base.fragment.BaseFragment
import com.zkyc.example.R
import com.zkyc.example.databinding.ToolbarFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/14 10:29
 * desc   :
 */
@AndroidEntryPoint
@UseEventBus
class ToolbarFragment : BaseFragment<ToolbarFragmentBinding>() {

    override fun onCreateVB(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ToolbarFragmentBinding.inflate(inflater, container, false)

    override fun getLoadSirTargetView(): View {
        return mBinding.rv
    }

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

    override fun onLazyInit() {
        super.onLazyInit()

        showProgress()

        lifecycleScope.launchWhenResumed {

            delay(1500)

            dismissProgress()

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        /* Do something */
    }
}