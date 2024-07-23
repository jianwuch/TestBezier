package com.jove.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ProgressTextView : AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {

    }

    private var mCurrentTextColor = 0
    override fun onDraw(canvas: Canvas) {
        val canvasWidth = width
        if (mCurrentTextColor == 0) {
            mCurrentTextColor = currentTextColor
        }
        paint.setColor(mCurrentTextColor)
        super.onDraw(canvas)
        canvas.save()
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP))
        setTextColor(Color.RED)
        canvas.drawColor(Color.TRANSPARENT)
//        canvas.drawRect(0f, 0f, canvasWidth*0.5f, height.toFloat(), paint);
        canvas.clipRect(0f, 0f, canvasWidth * progress, height.toFloat())
        super.onDraw(canvas)
        paint.setXfermode(null)
        canvas.restore()
        setTextColor(mCurrentTextColor)
    }

    private var progress:Float = 0f
    public fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

}