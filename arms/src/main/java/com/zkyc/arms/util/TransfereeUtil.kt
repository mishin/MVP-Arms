package com.zkyc.arms.util

import android.content.Context
import android.widget.AbsListView
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import com.vansz.glideimageloader.GlideImageLoader

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/6/10 13:37
 * desc   :
 */
class TransfereeUtil private constructor() {

    companion object {
        /**
         * 获取[TransfereeUtil]单例对象
         */
        fun getInstance(): TransfereeUtil {
            return SingletonHolder.instance
        }
    }

    private object SingletonHolder {
        val instance = TransfereeUtil()
    }

    private var mTransfer: Transferee? = null

    fun bind(
        context: Context,
        view: AbsListView,
        @IdRes itemViewId: Int,
        position: Int,
        urlList: List<String>,
    ) {
        mTransfer = Transferee.getDefault(context)
        mTransfer?.apply(
            TransferConfig
                .build()
                .setImageLoader(GlideImageLoader.with(context))
                .setSourceUrlList(urlList)
                .setNowThumbnailIndex(position)
                .bindListView(view, itemViewId)
        )?.show()
    }

    fun bind(
        context: Context,
        view: RecyclerView,
        @IdRes itemViewId: Int,
        position: Int,
        urlList: List<String>,
    ) {
        mTransfer = Transferee.getDefault(context)
        mTransfer?.apply(
            TransferConfig
                .build()
                .setImageLoader(GlideImageLoader.with(context))
                .setSourceUrlList(urlList)
                .setNowThumbnailIndex(position)
                .bindRecyclerView(view, itemViewId)
        )?.show()
    }

    fun bind(context: Context, view: ImageView, url: String) {
        mTransfer = Transferee.getDefault(context)
        mTransfer?.apply(
            TransferConfig
                .build()
                .setImageLoader(GlideImageLoader.with(context))
                .bindImageView(view, url)
        )?.show()
    }

    fun destroy() {
        mTransfer?.destroy()
    }
}