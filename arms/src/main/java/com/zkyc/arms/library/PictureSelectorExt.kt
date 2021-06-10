package com.zkyc.arms.library

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.ImageViewTarget
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnImageCompleteCallback
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.luck.picture.lib.tools.MediaUtils
import com.luck.picture.lib.widget.longimage.ImageSource
import com.luck.picture.lib.widget.longimage.ImageViewState
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import com.zkyc.arms.R
import timber.log.Timber

/**
 * 启动图片选中器
 */
fun Activity.startPictureSelector(maxSelectNum: Int, block: (List<String>) -> Unit) {
    PictureSelector.create(this)
        .openGallery(PictureMimeType.ofImage())
        .imageEngine(GlideEngine.instance)
        .isCamera(true)
        .isCompress(true)
        .isPreviewEggs(true)
        .maxSelectNum(maxSelectNum)
        .forResult(object : OnResultCallbackListener<LocalMedia> {

            override fun onResult(result: MutableList<LocalMedia>?) {
                if (result.isNullOrEmpty()) {
                    Timber.d("PictureSelector result isNullOrEmpty. result = $result")
                    return
                }
                block.invoke(result.map { if (it.isCompressed) it.compressPath else it.path })
            }

            override fun onCancel() {
                Timber.d("PictureSelector Cancel")
            }
        })
}

class GlideEngine private constructor() : ImageEngine {

    companion object {
        val instance: GlideEngine by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { GlideEngine() }
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    override fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    /**
     * 加载网络图片适配长图方案
     * # 注意：此方法只有加载网络图片才会回调
     *
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @param callback      网络图片加载回调监听 {link after version 2.5.1 Please use the #OnImageCompleteCallback#}
     */
    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?,
        callback: OnImageCompleteCallback?,
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : ImageViewTarget<Bitmap>(imageView) {

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    callback?.onShowLoading()
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    callback?.onHideLoading()
                }

                override fun setResource(resource: Bitmap?) {
                    callback?.onHideLoading()
                    if (resource != null) {
                        val eqLongImage = MediaUtils.isLongImg(resource.width, resource.height)
                        longImageView?.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                        imageView.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                        if (eqLongImage) {
                            // 加载长图
                            longImageView?.run {
                                isQuickScaleEnabled = true
                                isZoomEnabled = true
                                setDoubleTapZoomDuration(100)
                                setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                                setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                                setImage(
                                    ImageSource.bitmap(resource),
                                    ImageViewState(0f, PointF(0f, 0f), 0)
                                )
                            }
                        } else {
                            // 普通图片
                            imageView.setImageBitmap(resource)
                        }
                    }
                }
            })
    }

    /**
     * 加载网络图片适配长图方案
     * # 注意：此方法只有加载网络图片才会回调
     *
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @ 已废弃
     */
    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?,
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : ImageViewTarget<Bitmap>(imageView) {

                override fun setResource(resource: Bitmap?) {
                    if (resource != null) {
                        val eqLongImage = MediaUtils.isLongImg(resource.width, resource.height)
                        longImageView?.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                        imageView.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                        if (eqLongImage) {
                            // 加载长图
                            longImageView?.run {
                                isQuickScaleEnabled = true
                                isZoomEnabled = true
                                setDoubleTapZoomDuration(100)
                                setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                                setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                                setImage(
                                    ImageSource.bitmap(resource),
                                    ImageViewState(0f, PointF(0f, 0f), 0)
                                )
                            }
                        } else {
                            // 普通图片
                            imageView.setImageBitmap(resource)
                        }
                    }
                }
            })
    }

    /**
     * 加载相册目录
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    override fun loadFolderImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .override(180, 180)
            .centerCrop()
            .sizeMultiplier(0.5f)
            .apply(RequestOptions().placeholder(R.drawable.picture_image_placeholder))
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    super.setResource(resource)
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.cornerRadius = 8f
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }

    /**
     * 加载gif
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    override fun loadAsGifImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .asGif()
            .load(url)
            .into(imageView)
    }

    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    override fun loadGridImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .override(200, 200)
            .centerCrop()
            .apply(RequestOptions().placeholder(R.drawable.picture_image_placeholder))
            .into(imageView)
    }
}