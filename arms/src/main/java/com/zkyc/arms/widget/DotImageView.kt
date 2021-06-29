package com.zkyc.arms.widget

import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.res.use
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.luck.picture.lib.photoview.PhotoView
import com.zkyc.arms.R
import com.zkyc.arms.extension.load
import com.zkyc.arms.library.GlideApp
import com.zkyc.arms.library.px

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/6/25 15:58
 * desc   :
 */
class DotImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr),
    View.OnClickListener, View.OnLongClickListener {

    private var mDotWidth: Int = 32f.px
    private var mDotHeight: Int = 32f.px

    private val mLayouts = mutableListOf<View>()

    private val mPhotoView: PhotoView = PhotoView(context)

    private lateinit var mTempRectF: RectF
    private var mMatrix: Matrix? = null

    private var mOnLoadReadyListener: OnLoadReadyListener? = null
    private var mOnDotClickListener: OnDotClickListener? = null
    private var mOnDotLongClickListener: OnDotLongClickListener? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.DotImageView).use {
            mDotWidth = it.getDimensionPixelSize(R.styleable.DotImageView_div_dot_width, mDotWidth)
            mDotHeight =
                it.getDimensionPixelSize(R.styleable.DotImageView_div_dot_height, mDotHeight)
        }
        addView(mPhotoView, LayoutParams(MATCH_PARENT, MATCH_PARENT))
        mPhotoView.setOnMatrixChangeListener { rect ->
            mLayouts.forEach { layout ->
                val dot = layout.tag as Dot
                val newX = dot.sx * (rect.right - rect.left)
                val newY = dot.sy * (rect.bottom - rect.top)
                // 保持中心位置不变
                layout.x = rect.left + newX - mDotWidth / 2
                layout.y = rect.top + newY - mDotHeight / 2
            }
            mTempRectF = rect
            // 图片加载完成后才可以添加图标
            if (mOnLoadReadyListener != null) {
                mOnLoadReadyListener?.onLoadReady()
                mOnLoadReadyListener = null // 只执行一次
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (mMatrix != null) {
            mPhotoView.setDisplayMatrix(mMatrix)
        }
    }

    override fun onClick(v: View) {
        mOnDotClickListener?.onDotClick(v)
    }

    override fun onLongClick(v: View): Boolean {
        if (mOnDotLongClickListener == null) {
            return false
        }
        mOnDotLongClickListener?.onDotLongClick(v)
        return true
    }

    /* ****************** 公共方法 ****************** */

    fun loadImage(any: Any?) {
        mPhotoView.load(context, any)
    }

    /**
     * 添加一个
     */
    fun addDots(dots: Collection<Dot>?) {
        dots?.forEach { addDot(it) }
    }

    /**
     * 批量添加
     */
    fun addDot(dot: Dot) {
        // 记住此时photoView的Matrix
        if (mMatrix == null) {
            mMatrix = Matrix()
        }
        mPhotoView.getSuppMatrix(mMatrix)
        val layout =
            LayoutInflater.from(context).inflate(R.layout.dot_image_view, this, false).apply {
                tag = dot
                val newX = dot.sx * (mTempRectF.left - mTempRectF.right)
                x = mTempRectF.left + newX
                val newY = dot.sy * (mTempRectF.bottom - mTempRectF.right)
                y = mTempRectF.top + newY
                findViewById<ImageView>(R.id.iv_icon).run {
                    setImageResource(dot.icon)
                }
                findViewById<ImageView>(R.id.iv_bg).run {
                    isInvisible = !dot.selected
                    if (isVisible) {
                        GlideApp.with(context).asGif().load(R.drawable.dot_iv_ic_dot_selected)
                            .into(this)
                    }
                }
                setOnClickListener(this@DotImageView)
                setOnLongClickListener(this@DotImageView)
            }
        addView(layout, LayoutParams(mDotWidth, mDotHeight))
        mLayouts.add(layout)
    }

    /**
     * 移除一个
     */
    fun remove(view: View) {
        removeView(view)
    }

    /**
     * 重置
     */
    fun clear() {
        mLayouts.forEach { removeView(it) }
        mLayouts.clear()
    }

    fun setOnLoadReadyListener(listener: OnLoadReadyListener) {
        mOnLoadReadyListener = listener
    }

    fun setOnDotClickListener(listener: OnDotClickListener) {
        mOnDotClickListener = listener
    }

    fun setOnDotLongClickListener(listener: OnDotLongClickListener) {
        mOnDotLongClickListener = listener
    }
}

data class Dot(
    val sx: Float,
    val sy: Float,
    val icon: Int,
    val selected: Boolean = false,
    val data: Any? = null,
)

interface OnLoadReadyListener {
    fun onLoadReady()
}

interface OnDotClickListener {
    fun onDotClick(v: View)
}

interface OnDotLongClickListener {
    fun onDotLongClick(v: View)
}