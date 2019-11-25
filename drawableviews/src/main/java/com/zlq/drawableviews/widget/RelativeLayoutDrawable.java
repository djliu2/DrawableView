package com.zlq.drawableviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Writer：zlq on 2019/11/8 0008 15:26
 * description:
 */
public class RelativeLayoutDrawable extends RelativeLayout {

    public RectF mLayer;                   // 画布图层大小
    public Path mClipPath;                 // 剪裁区域路径
    public Paint mPaint;                   // 画笔
    public int width;
    public int height;
    private Paint mRectPaint;
    private float cx = 500f;
    private float cy = 500f;
    public RelativeLayoutDrawable(Context context) {
        super(context);
        init();
    }

    public RelativeLayoutDrawable(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RelativeLayoutDrawable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RelativeLayoutDrawable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //关闭硬件加速才能达到理想效果
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //创建画笔
        mRectPaint = new Paint();
        //设置画笔颜色
        mRectPaint.setColor(Color.BLACK);
        mRectPaint.setStrokeWidth(3);
        //设置画笔的样式
        mRectPaint.setStyle(Paint.Style.FILL);
        mClipPath = new Path();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        float d = width>=height?width:height;
        float r = d/2;
        mClipPath.reset();
        PointF center = new PointF(width / 2, height / 2);
        mRectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        canvas.drawCircle(width/2,height/2,d/2,mRectPaint);
        float y = height / 2 - r;
//        mClipPath.moveTo(areas.left, y);
        mClipPath.addCircle(center.x, y + r, r, Path.Direction.CW);
        canvas.drawPath(mClipPath, mRectPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mRectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawCircle(width/2,height/2,d/2,mRectPaint);
//        canvas.drawCircle(120, 20, 20, mRectPaint);// 大圆
        float d = width>=height?width:height;
        float r = d/2;
        mClipPath.reset();
        PointF center = new PointF(width / 2, height / 2);
        mRectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        mRectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawCircle(width/2,height/2,d/2,mRectPaint);
        float y = height / 2 - r;
//        mClipPath.moveTo(areas.left, y);
        mClipPath.addCircle(center.x, y + r, r, Path.Direction.CW);
        canvas.drawPath(mClipPath, mRectPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        if (mLayer == null){
            mLayer = new RectF();
        }
        mLayer.set(0, 0, w, h);
    }
}
