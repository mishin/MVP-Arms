package com.zkyc.arms.base.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.blankj.utilcode.util.FileUtils
import com.zkyc.arms.base.view.IView
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/20 16:27
 * desc   : P层基类
 */
abstract class BasePresenter<V : IView> : IPresenter<V>,
    CoroutineScope by CoroutineScope(Job() + Dispatchers.Main) {

    companion object {
        private const val MEDIA_TYPE_IMAGE = "image/*"
    }

    private var mLifecycle: Lifecycle? = null
    var view: V? = null

    override fun bind(lifecycle: Lifecycle, view: V) {
        mLifecycle = lifecycle
        this.view = view
        mLifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        cancel()
        mLifecycle?.removeObserver(this)
        mLifecycle = null
        view = null
    }

    /**
     * 获取[MultipartBody]实例
     */
    protected fun obtainMultipartBody(
        map: Map<String, String>,
        images: List<String>?,
    ): MultipartBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        for ((k, v) in map) {
            builder.addFormDataPart(k, v)
        }
        if (images != null && images.isNotEmpty()) {
            val files = images.map { FileUtils.getFileByPath(it) }.filter { FileUtils.isFile(it) }
            files.forEach {
                builder.addFormDataPart(
                    it.name,
                    it.name,
                    it.asRequestBody(MEDIA_TYPE_IMAGE.toMediaType())
                )
            }
        }
        return builder.build()
    }
}