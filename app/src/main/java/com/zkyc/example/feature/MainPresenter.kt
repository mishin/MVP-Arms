package com.zkyc.example.feature

import com.google.gson.JsonObject
import com.zkyc.arms.base.presenter.BasePresenter
import com.zkyc.arms.base.presenter.IPresenter
import com.zkyc.arms.base.view.IView
import com.zkyc.arms.model.http.request
import com.zkyc.example.model.http.LoginApis
import timber.log.Timber
import javax.inject.Inject

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/5/12 9:26
 * desc   :
 */
class MainPresenter @Inject constructor(private var apis: LoginApis) :
    BasePresenter<MainContract.View>(), MainContract.Presenter {

    override fun startLooper() {
        request<JsonObject> {
            call {
                val map = mapOf(
                    Pair("loginName", "YTlikai"),
                    Pair("loginPassword", "likai0312"),
                    Pair("type", "oem")
                )
                apis.login(map)
            }
            success {
                Timber.d(it.toString())
            }
        }
    }
}

interface MainContract {

    interface View : IView {

    }

    interface Presenter : IPresenter<View> {
        fun startLooper()
    }
}