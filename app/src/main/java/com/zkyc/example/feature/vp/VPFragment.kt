package com.zkyc.example.feature.vp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.os.postDelayed
import com.zkyc.arms.annotation.UseEventBus
import com.zkyc.arms.base.fragment.BaseFragment
import com.zkyc.arms.widget.Dot
import com.zkyc.arms.widget.OnLoadReadyListener
import com.zkyc.example.R
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

    override fun onLazyInit() {
        super.onLazyInit()

        mBinding.dotIv.loadImage("http://ci-image.gz.bcebos.com/yunApi/plane_graph/201805/3a06328a27644fce9c6dd1404c597d52.jpg")
        initIcon()

//        Handler(Looper.getMainLooper()).postDelayed(2000L) {

//            mBinding.dotIv.clear()
//            mBinding.dotIv.loadImage(null)

            Handler(Looper.getMainLooper()).postDelayed(2000L) {
                mBinding.dotIv.clear()
                mBinding.dotIv.loadImage("http://ci-image.gz.bcebos.com/yunApi/plane_graph/201806/752b365f953c49cb8a9edd6894dca24b.jpg")
                val iconBeanList = mutableListOf<Dot>()
                val bean = Dot(0.8f, 0.4f, R.drawable.icon_xhfa)
                iconBeanList.add(bean)
                val bean2 = Dot(0.6f, 0.5f, R.drawable.icon_xhfa, true)
                iconBeanList.add(bean2)
                mBinding.dotIv.setOnLoadReadyListener(object : OnLoadReadyListener {
                    override fun onLoadReady() {
                        mBinding.dotIv.addDots(iconBeanList)
                    }
                })
            }

//        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        /* Do something */
    }

    private fun initIcon() {

        val iconBeanList = mutableListOf<Dot>()

        val bean = Dot(0.3f, 0.3f, R.drawable.icon_xhfa)
        iconBeanList.add(bean)

        val bean2 = Dot(0.5f, 0.5f, R.drawable.icon_xhfa, true)
        iconBeanList.add(bean2)

        val bean3 = Dot(0.8f, 0.8f, R.drawable.icon_xhfa)
        iconBeanList.add(bean3)

        mBinding.dotIv.setOnLoadReadyListener(object : OnLoadReadyListener {
            override fun onLoadReady() {
                mBinding.dotIv.addDots(iconBeanList)
            }
        })
    }
}