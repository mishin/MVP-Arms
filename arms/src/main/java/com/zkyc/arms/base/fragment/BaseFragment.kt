package com.zkyc.arms.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.kaopiz.kprogresshud.KProgressHUD
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.zkyc.arms.annotation.UseEventBus
import com.zkyc.arms.base.view.IView
import com.zkyc.arms.library.EmptyCallback
import com.zkyc.arms.library.ErrorCallback
import com.zkyc.arms.library.LoadingCallback
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.atomic.AtomicBoolean

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/4/20 17:37
 * desc   :
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), IView, Callback.OnReloadListener {

    /**
     * 视图绑定实例
     */
    private var _mBinding: VB? = null
    protected val mBinding get() = _mBinding!!

    // 是否使用事件发布-订阅总线
    private var mUseEventBus: Boolean = false

    // 初始化标识
    private var mInitialized = AtomicBoolean(false)

    // 加载进度对话框
    private var mHud: KProgressHUD? = null

    // 加载反馈页管理框架实例
    private var mLoadService: LoadService<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            onParseData(bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = onCreateVB(inflater, container)
        return _mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUseEventBus = javaClass.isAnnotationPresent(UseEventBus::class.java)

        val targetView = getLoadSirTargetView()
        if (null != targetView) {
            mLoadService = LoadSir.getDefault().register(mLoadService, this)
        }

        onInit(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (mUseEventBus) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (mInitialized.compareAndSet(false, true)) {
            onLazyInit()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mUseEventBus) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }

    override fun toast(msg: String?) {
        if (msg.isNullOrEmpty()) return
        ToastUtils.showShort(msg)
    }

    override fun toast(msgId: Int?) {
        if (null == msgId) return
        ToastUtils.showShort(msgId)
    }

    override fun loadMoreEnd() {}

    override fun loadMoreFail() {}

    override fun showProgress() {
        if (null == mHud) {
            mHud = KProgressHUD
                .create(requireContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        }
        mHud?.show()
    }

    override fun dismissProgress() {
        mHud?.dismiss()
    }

    override fun showLoading() {
        if (SuccessCallback::class.java != mLoadService?.currentCallback) {
            mLoadService?.showCallback(LoadingCallback::class.java)
        }
    }

    override fun showEmpty() {
        mLoadService?.showCallback(EmptyCallback::class.java)
    }

    override fun showError() {
        mLoadService?.showCallback(ErrorCallback::class.java)
    }

    override fun showSuccess() {
        mLoadService?.showCallback(SuccessCallback::class.java)
    }

    override fun onReload(v: View?) {}

    protected open fun onParseData(bundle: Bundle) {}

    protected abstract fun onCreateVB(inflater: LayoutInflater, container: ViewGroup?): VB

    protected open fun getLoadSirTargetView(): View? = null

    protected open fun onInit(savedInstanceState: Bundle?) {}

    protected open fun onLazyInit() {}
}