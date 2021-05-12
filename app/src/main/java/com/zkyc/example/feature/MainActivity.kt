package com.zkyc.example.feature

import android.os.Bundle
import android.view.LayoutInflater
import com.zkyc.arms.base.activity.BaseMVPActivity
import com.zkyc.example.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/10 14:35
 * desc   :
 */
@AndroidEntryPoint
class MainActivity : BaseMVPActivity<MainActivityBinding, MainContract.View, MainPresenter>() {

    override fun onCreateVB(inflater: LayoutInflater) = MainActivityBinding.inflate(inflater)

    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)
        mBinding.startRequest.setOnClickListener {
            presenter.startLooper()
        }
    }
}