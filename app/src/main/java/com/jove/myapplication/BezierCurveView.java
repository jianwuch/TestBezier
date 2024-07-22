package com.jove.myapplication;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 贝塞尔曲线
 */
public class BezierCurveView extends View {

    private Paint paint;
    private Path path;

    public BezierCurveView(Context context) {
        super(context);
        init();
    }

    public BezierCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierCurveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        float dx = width / 4f;
        float dy = height / 4f;

        path.reset();

        path.moveTo(0, height / 2f); // 起点

        path.rQuadTo(dx, -dy, 2 * dx, 0f); // 控制点、终点
        path.rQuadTo(dx, dy, 2 * dx, 0f); // 控制点、终点
        path.rQuadTo(dx, -dy, 2 * dx, 0f); // 控制点、终点
        path.rQuadTo(dx, dy, 2 * dx, 0f); // 控制点、终点

        path.lineTo(width, height);
        path.lineTo(0f, height);

        path.close();
        canvas.drawPath(path, paint);
    }
}
