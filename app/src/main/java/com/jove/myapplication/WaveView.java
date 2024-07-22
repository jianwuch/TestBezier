package com.jove.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * 基于贝塞尔曲线的波动动画
 */
public class WaveView extends View {
    public Boolean showControlPoint = true;
    private Paint mPaint;
    private Paint mRedPaint;
    private int mWidth;
    private int mHeight;
    private int mWaveHeight;
    private int mWaveDx;
    private int dx;//动画偏移量

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor("#80FF3891"));
        mPaint.setStyle(Paint.Style.FILL);

        mRedPaint = new Paint();
        mRedPaint.setAntiAlias(true);
        mRedPaint.setDither(true);
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStrokeWidth(6);
        //波长的长度(这里设置为屏幕的宽度)
        mWaveDx = getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //控件的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //水波的高度
        mWaveHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                32, getContext().getResources().getDisplayMetrics());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWave(canvas);
    }


    public Point lastPoint = new Point();

    private void drawWave(Canvas canvas) {
        Path path = new Path();
        path.reset();
        path.moveTo(-mWaveDx + dx, mHeight / 2);
        lastPoint = new Point(-mWaveDx + dx, mHeight / 2);
        for (int i = -mWaveDx; i < getWidth() + mWaveDx; i += mWaveDx) {
            //打印控制点
            if (showControlPoint) {
                canvas.drawPoint(lastPoint.x + mWaveDx / 4, lastPoint.y - mWaveHeight, mRedPaint);
            }
            path.rQuadTo(mWaveDx / 4, -mWaveHeight, mWaveDx / 2, 0);
            //控制点计算最后绝对位置
            lastPoint.x = lastPoint.x + mWaveDx / 2;

            //打印控制点
            if (showControlPoint) {
                canvas.drawPoint(lastPoint.x + mWaveDx / 4, lastPoint.y + mWaveHeight,  mRedPaint);
            }
            path.rQuadTo(mWaveDx / 4, mWaveHeight, mWaveDx / 2, 0);
            //控制点计算最后绝对位置
            lastPoint.x = lastPoint.x + mWaveDx / 2;

        }
        //绘制封闭的区域
        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        //path.close() 绘制封闭的区域
        path.close();
        canvas.drawPath(path, mPaint);
    }

    public void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mWaveDx);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //水平方向的偏移量
                dx = (int) animation.getAnimatedValue();
                invalidate();
            }

        });
        valueAnimator.start();
    }
}
