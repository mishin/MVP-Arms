package com.zkyc.arms.model.http

import com.zkyc.arms.base.presenter.BasePresenter
import com.zkyc.arms.base.view.IView
import com.zkyc.arms.model.bean.ApiException
import com.zkyc.arms.model.bean.ApiResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.await
import timber.log.Timber

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/27 17:32
 * desc   :
 */
class Request<T> {

    /**
     * 是否执行默认的异常处理逻辑
     */
    var performDefaultErrorLogic: Boolean = true

    /**
     * 封装网络请求
     */
    private lateinit var mCall: suspend () -> Call<ApiResponse<T>>

    /**
     * 接口返回实体数据脱壳
     */
    private var mProcess: ((ApiResponse<T>) -> T?) = { it.data }

    /**
     * 请求开始
     */
    private var mStart: (() -> Unit)? = null

    /**
     * 请求成功
     */
    private var mSuccess: ((T?) -> Unit)? = null

    /**
     * 请求失败
     */
    private var mFail: ((Exception) -> Unit)? = null

    infix fun call(call: suspend () -> Call<ApiResponse<T>>) {
        mCall = call
    }

    infix fun process(process: ((ApiResponse<T>) -> T?)) {
        mProcess = process
    }

    infix fun start(start: (() -> Unit)?) {
        mStart = start
    }

    infix fun success(success: ((T?) -> Unit)?) {
        mSuccess = success
    }

    infix fun fail(fail: ((Exception) -> Unit)?) {
        mFail = fail
    }

    fun request(scope: CoroutineScope, view: IView?) {
        mStart?.invoke()
        scope.launch {
            val flow = flow { emit(mCall.invoke().await()) }
            try {
                flow.flowOn(Dispatchers.IO)
                    .collect { response ->
                        val data = mProcess.invoke(response)
                        if (response.success()) {
                            mSuccess?.invoke(data)
                        } else {
                            throw ApiException.create(response)
                        }
                    }
            } catch (e: CancellationException) {
                // 取消请求
                Timber.e(e)
            } catch (e: Exception) {
                Timber.e(e)
                if (performDefaultErrorLogic) {
                    view?.dismissProgress()
                    view?.showError()
                    view?.toast(e.toStr())
                    if (e.tokenTimeout()) {
                        view?.reLogin()
                    }
                }
                mFail?.invoke(e)
            }
        }
    }
}

inline fun <T> BasePresenter<*>.request(block: Request<T>.() -> Unit) {
    Request<T>().apply(block).request(this, view)
}