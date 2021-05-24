package com.zkyc.arms.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV
import com.zkyc.arms.library.EmptyCallback
import com.zkyc.arms.library.ErrorCallback
import com.zkyc.arms.library.LoadingCallback
import timber.log.Timber
import java.util.*

abstract class BaseApplication : Application() {

    // 存储Activity实例的栈
    private val mActivities = Stack<Activity>()

    // Activity生命周期监听事件
    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            addActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            removeActivity(activity)
        }
    }

    override fun onCreate() {
        super.onCreate()
        // 注册Activity生命周期监听事件
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        // 初始化日志记录框架
        initTimber()
        // 初始化MMKV
        initMMKV()
        // 初始化加载反馈页管理框架
        initLoadSir()
    }

    override fun onTerminate() {
        super.onTerminate()
        // 取消注册Activity生命周期监听事件
        unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    /**
     * activity入栈
     */
    private fun addActivity(activity: Activity?) {
        if (null == activity) return
        mActivities.add(activity)
    }

    /**
     * activity出栈
     */
    private fun removeActivity(activity: Activity?) {
        if (null == activity) return
        mActivities.remove(activity)
    }

    /**
     * 结束指定activity
     */
    private fun finishActivity(activity: Activity?) {
        if (null != activity && !activity.isFinishing) {
            activity.finish()
        }
    }

    /**
     * 初始化日志记录框架
     */
    private fun initTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (enableLog) {
                    super.log(priority, tag, message, t)
                }
            }
        })
    }

    /**
     * 初始化MMKV
     */
    private fun initMMKV() {
        val rootDir = MMKV.initialize(this)
        Timber.d("mmkv root：$rootDir")
    }

    /**
     * 初始化加载反馈页管理框架
     */
    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback.newInstance())
            .addCallback(EmptyCallback.newInstance())
            .addCallback(ErrorCallback.newInstance())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

    /* ************************ 公共方法 ************************ */

    /**
     * 是否开启日志打印
     */
    open val enableLog: Boolean = false

    /**
     * 获取当前Activity实例
     */
    val curActivity: Activity?
        get() = if (mActivities.isNullOrEmpty()) null else mActivities.lastElement()

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(vararg cls: Class<*>) {
        if (cls.isEmpty()) return
        mActivities.forEach { activity ->
            cls.forEach {
                if (it == activity.javaClass) {
                    finishActivity(activity)
                }
            }
        }
    }

    /**
     * 结束全部Activity
     */
    fun finishAllActivities() {
        mActivities.forEach { finishActivity(it) }
    }
}