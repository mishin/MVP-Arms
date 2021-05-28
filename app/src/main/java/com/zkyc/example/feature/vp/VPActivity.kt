package com.zkyc.example.feature.vp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zkyc.arms.annotation.UseEventBus
import com.zkyc.arms.base.activity.BaseActivity
import com.zkyc.arms.base.adapter.viewpager2.BaseSimpleFragmentStateAdapter
import com.zkyc.example.databinding.VpActivityBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/28 10:40
 * desc   :
 */
@UseEventBus
class VPActivity : BaseActivity<VpActivityBinding>() {

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, VPActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreateVB(inflater: LayoutInflater): VpActivityBinding {
        return VpActivityBinding.inflate(inflater)
    }

    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)
//        mBinding.vpContainer.offscreenPageLimit = 12
        mBinding.vpContainer.adapter = FragmentsAdapter(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        /* Do something */
    }

    private class FragmentsAdapter(activity: AppCompatActivity) :
        BaseSimpleFragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return 12
        }

        override fun createFragment(position: Int): Fragment {
            return VPFragment.newInstance(position)
        }
    }
}