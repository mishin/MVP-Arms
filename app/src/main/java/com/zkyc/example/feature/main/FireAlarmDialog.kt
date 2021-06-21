package com.zkyc.example.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.zkyc.arms.base.fragment.BaseDialogFragment
import com.zkyc.example.databinding.ProcessFireAlarmDialogBinding

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/6/21 11:21
 * desc   :
 */
class FireAlarmDialog : BaseDialogFragment<ProcessFireAlarmDialogBinding>() {

    companion object {

        private const val TAG = "fire_alarm"

        fun show(fm: FragmentManager) {
            FireAlarmDialog().show(fm, TAG)
        }
    }

    override fun onCreateVB(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ProcessFireAlarmDialogBinding {
        return ProcessFireAlarmDialogBinding.inflate(inflater, container, false)
    }
}