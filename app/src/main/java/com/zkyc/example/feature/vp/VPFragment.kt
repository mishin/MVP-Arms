package com.zkyc.example.feature.vp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.zkyc.arms.annotation.UseEventBus
import com.zkyc.arms.base.fragment.BaseFragment
import com.zkyc.arms.widget.Dot
import com.zkyc.arms.widget.OnDotClickListener
import com.zkyc.arms.widget.OnLoadReadyListener
import com.zkyc.example.R
import com.zkyc.example.databinding.VpFragmentBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

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

    override fun onLazyInit() {
        super.onLazyInit()

        mBinding.dotIv.loadImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0527%252F81198ae1j00qtquv4002jc000hs00oic.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1627199075&t=db4f5612abecea4f3047f3c71a87d833")
        initIcon()

        mBinding.dotIv.setOnDotClickListener(object : OnDotClickListener {
            override fun onDotClick(v: View) {
                val dot = v.tag as Dot
                Timber.d("------------ $dot")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
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