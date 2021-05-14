package com.zkyc.example.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.zkyc.arms.annotation.HideHomeAsUp
import com.zkyc.arms.base.activity.BaseMVPActivity
import com.zkyc.example.R
import com.zkyc.example.databinding.MainActivityBinding
import com.zkyc.example.feature.toolbar.ToolbarActivity
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
//            presenter.startLooper()
            ToolbarActivity.start(this)
        }
    }

    override fun onNavigationInit(toolbar: Toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_person_24)
        toolbar.setNavigationOnClickListener { toast("个人中心") }
    }

    override fun getMenuId(): Int {
        return R.menu.toolbar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.test -> {
                toast("测试菜单点击")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}