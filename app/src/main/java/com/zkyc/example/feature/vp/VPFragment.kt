package com.zkyc.example.feature.vp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.zkyc.arms.annotation.UseEventBus
import com.zkyc.arms.base.fragment.BaseFragment
import com.zkyc.example.databinding.VpFragmentBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/28 10:39
 * desc   :
 */

@UseEventBus
class VPFragment : BaseFragment<VpFragmentBinding>() {

    companion object {

        private const val ARG_POSITION = "arg_position"

        fun newInstance(position: Int) = VPFragment().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }
    }

    private var mPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onParseData(bundle: Bundle) {
        super.onParseData(bundle)
        mPosition = bundle.getInt(ARG_POSITION)
    }

    override fun onCreateVB(inflater: LayoutInflater, container: ViewGroup?): VpFragmentBinding {
        return VpFragmentBinding.inflate(inflater, container, false)
    }

    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)
        mBinding.tvPosition.text = mPosition.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        /* Do something */
    }
}