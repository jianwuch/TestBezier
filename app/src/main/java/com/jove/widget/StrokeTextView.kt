package com.jove.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView

class StrokeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {

    private var mStrokeWidth = 4f

    override fun onDraw(canvas: Canvas) {
        //原实值
        val originalPaint = TextPaint().apply { set(paint) }
        val originalColor = currentTextColor

        //画描边
        paint.apply {
            strokeWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                mStrokeWidth,
                resources.displayMetrics
            )
            style = Paint.Style.STROKE
            setTextColor(Color.RED)
        }
        super.onDraw(canvas)

        //画真实文字
        paint.run {
            set(originalPaint)
            style = Paint.Style.FILL
        }
        setTextColor(originalColor)
        super.onDraw(canvas)
    }
}
