package com.zkyc.arms.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.use
import com.zkyc.arms.R

/**
 * author : Saxxhw
 * email  : xingwangwang@cloudinnov.com
 * time   : 2021/6/1 14:29
 * desc   :
 */
class SearchEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    companion object {

        //
        private const val DEFAULT_LIMIT = 1000
    }

    private var mLimit = DEFAULT_LIMIT

    private var mListener: OnTextChangedListener? = null

    // 记录开始输入前的文本内容
    private var mText: String? = null

    private val mRunnable = Runnable {
        if (mListener != null) {
            // 判断最终和开始前是否一致
            if (mText != text?.toString()) {
                mText = text?.toString()
                mListener?.afterTextChanged(mText)
            }
        }
    }

    init {
        isFocusableInTouchMode = true
        context.obtainStyledAttributes(attrs, R.styleable.SearchEditText).use {
            mLimit = it.getInt(R.styleable.SearchEditText_search_limit, DEFAULT_LIMIT)
        }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        removeCallbacks(mRunnable)
        postDelayed(mRunnable, mLimit.toLong())
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks(mRunnable)
    }

    fun setOnTextChangedListener(listener: OnTextChangedListener) {
        mListener = listener
    }

    interface OnTextChangedListener {
        fun afterTextChanged(text: String?)
    }
}