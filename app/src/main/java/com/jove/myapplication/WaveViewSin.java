package com.jove.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * 正玄sin的动画
 * +波的干涉
 */
public class WaveViewSin extends View {
    private Paint paint;
    private float amplitude;//振幅
    private float frequency;//频率
    private float phase;//相位

    public WaveViewSin(Context context) {
        this(context, null);
    }

    public WaveViewSin(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        amplitude = 25;
        frequency = 0.02f;
        phase = 90;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE); // 清空画布

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        float midY = height / 2f;

        float startX = 0;
        float startY = midY + amplitude * (float) Math.sin(startX * frequency + phase);

        //波的干涉
        for (float x = 1; x <= width; x++) {
            float y = midY + amplitude * (float) Math.sin(x * frequency + phase);
            y += amplitude * (float) Math.sin(x * frequency);//添加波的干涉，不加就是正常的sin
            canvas.drawLine(startX, startY, x, y, paint);
            startX = x;
            startY = y;
        }
    }

    public void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setDuration(500);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //水平方向的偏移量,产生左移sin线的动画
                phase = (float) Math.toRadians((int) animation.getAnimatedValue());
                invalidate();
            }

        });
        valueAnimator.start();
    }
}