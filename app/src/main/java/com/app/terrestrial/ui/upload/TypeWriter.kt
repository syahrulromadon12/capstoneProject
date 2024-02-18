package com.app.terrestrial.ui.upload

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet

class TypeWriter(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    private var mText: CharSequence? = null
    private var mIndex = 0
    private var mDelay: Long = 150 // Delay in milliseconds

    private val mHandler = Handler(Looper.getMainLooper())

    private val characterAdder: Runnable = object : Runnable {
        override fun run() {
            text = mText?.subSequence(0, mIndex++)
            if (mIndex <= (mText?.length ?: 0)) {
                mHandler.postDelayed(this, mDelay)
            }
        }
    }

    fun animateText(text: CharSequence?) {
        mText = text
        mIndex = 0
        mHandler.removeCallbacks(characterAdder)
        mHandler.postDelayed(characterAdder, mDelay)
    }

    fun setCharacterDelay(m: Long) {
        mDelay = m
    }
}
