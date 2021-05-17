package com.zkyc.example.feature.toolbar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.zkyc.arms.annotation.HideHomeAsUp
import com.zkyc.arms.base.activity.BaseActivity
import com.zkyc.example.databinding.ToolbarActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/14 10:31
 * desc   :
 */
@HideHomeAsUp
@AndroidEntryPoint
class ToolbarActivity : BaseActivity<ToolbarActivityBinding>() {

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, ToolbarActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreateVB(inflater: LayoutInflater) =
        ToolbarActivityBinding.inflate(inflater)
}