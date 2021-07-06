package com.zkyc.example.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.zkyc.arms.annotation.HideHomeAsUp
import com.zkyc.arms.annotation.UseEventBus
import com.zkyc.arms.base.activity.BaseMVPActivity
import com.zkyc.arms.library.startPictureSelector
import com.zkyc.arms.widget.NineGridView
import com.zkyc.arms.widget.OptionsView
import com.zkyc.arms.widget.SearchEditText
import com.zkyc.example.R
import com.zkyc.example.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber


/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/10 14:35
 * desc   :
 */
@AndroidEntryPoint
@HideHomeAsUp
@UseEventBus
class MainActivity : BaseMVPActivity<MainActivityBinding, MainContract.View, MainPresenter>(),
    MainContract.View {

    override fun onCreateVB(inflater: LayoutInflater) = MainActivityBinding.inflate(inflater)

    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)
        mBinding.startRequest.setOnClickListener {
//            presenter.startLooper()
//            ToolbarActivity.start(this)
//            VPActivity.start(this)
//            FireAlarmDialog.show(supportFragmentManager)
            val items = mBinding.optionsView.getCheckedItems()
            Timber.d("------------ $items")
        }

        mBinding.etSearch.setOnTextChangedListener(object : SearchEditText.OnTextChangedListener {
            override fun afterTextChanged(text: String?) {
                Timber.d("------------> $text")
            }
        })

//        mBinding.ngv.addData(
//            listOf(
//                "https://img.yzcdn.cn/vant/cat.jpeg",
//                "https://img.yzcdn.cn/vant/cat.jpeg",
//                "https://img.yzcdn.cn/vant/cat.jpeg",
//                "https://img.yzcdn.cn/vant/cat.jpeg",
//                "https://img.yzcdn.cn/vant/cat.jpeg"
//            )
//        )
        mBinding.ngv.setOnAddPictureClickListener(object : NineGridView.OnAddPictureClickListener {
            override fun addPictureClick() {
                startPictureSelector(mBinding.ngv.missingCount) {
                    mBinding.ngv.addData(it)
                }
            }
        })

        with(mBinding.optionsView) {
            isEnabled = false
            setSingle()
            setList(listOf(
                Test("a", "name1", null),
                Test("b", "name2", "https://pic2.zhimg.com/50/v2-c998b77a6943bcbde819a72876e12bbd_hd.jpg"),
                Test("c", "name3", "https://pic3.zhimg.com/50/v2-e687a4da907a28c7808149d74db93237_hd.jpg"),
            ))
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

    private val sb = StringBuilder()

    override fun build(str: String) {
        sb.append(str)
        sb.append("\n")
        mBinding.tv.text = sb.toString()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        /* Do something */
    }
}

data class Test(val code: String, val name: String, val imageUrl: String?) :
    OptionsView.IOptionData {

    override var checked: Boolean = false

    override val optionText: String
        get() = name

    override val optionImageUrl: String?
        get() = imageUrl
}