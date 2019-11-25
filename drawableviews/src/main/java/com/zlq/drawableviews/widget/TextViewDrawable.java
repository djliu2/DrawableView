package com.zlq.drawableviews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.zlq.drawableviews.R;

public class TextViewDrawable extends AppCompatTextView {
    public boolean mRoundAsCircle = false; // 圆形
    public int roundCorner;
    public float[] roundCorners = new float[8];
    public String roundCornerOptions = "";
    public String roundCornerStr;
    public Path mClipPath;                 // 剪裁区域路径
    public Paint mPaint;                   // 画笔
    public int width;
    public int height;
    private Paint mRectPaint;


    public TextViewDrawable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public TextViewDrawable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);

    }

    void initAttr(Context context, AttributeSet attrs){
        //关闭硬件加速才能达到理想效果
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //创建画笔
        mRectPaint = new Paint();
        //设置画笔的样式
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setAntiAlias(true);
        mClipPath = new Path();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextViewDrawable);
        mRoundAsCircle = ta.getBoolean(R.styleable.TextViewDrawable_round_as_circle, false);
        roundCorner = ta.getDimensionPixelSize(R.styleable.TextViewDrawable_round_corner, 0);
        roundCornerOptions = ta.getString(R.styleable.TextViewDrawable_round_corner_option_from_abcd).toLowerCase();
        try {
            roundCornerStr = ta.getString(R.styleable.TextViewDrawable_round_corner_abcd).replace(" ","");
            String[] strings = roundCornerStr.split(",");
            if (strings.length == 4){
                //a
                if (roundCornerOptions.contains("a")) {
                    roundCorners[0] = Integer.valueOf(strings[0]);
                    roundCorners[1] = Integer.valueOf(strings[0]);
                }else {
                    roundCorners[0] = 0;
                    roundCorners[1] = 0;
                }

                //b
                if (roundCornerOptions.contains("b")) {
                    roundCorners[2] = Integer.valueOf(strings[1]);
                    roundCorners[3] = Integer.valueOf(strings[1]);
                }else {
                    roundCorners[2] = 0;
                    roundCorners[3] = 0;
                }

                //c
                if (roundCornerOptions.contains("c")) {
                    roundCorners[4] = Integer.valueOf(strings[2]);
                    roundCorners[5] = Integer.valueOf(strings[2]);
                }else {
                    roundCorners[4] = 0;
                    roundCorners[5] = 0;
                }

                //d
                if (roundCornerOptions.contains("d")) {
                    roundCorners[6] = Integer.valueOf(strings[3]);
                    roundCorners[7] = Integer.valueOf(strings[3]);
                }else {
                    roundCorners[6] = 0;
                    roundCorners[7] = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initView(Canvas canvas){
        if (mRoundAsCircle) {
            float d = width >= height ? width : height;
            float r = d / 2;
            mClipPath.reset();
            PointF center = new PointF(width / 2, height / 2);
            mRectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            float y = height / 2 - r;
            mClipPath.addCircle(center.x, y + r, r, Path.Direction.CW);
            canvas.drawPath(mClipPath, mRectPaint);
        }else {
            RectF areas = new RectF();
            areas.left = 0;
            areas.top = 0;
            areas.right = width;
            areas.bottom = height;
            RectF rect1 =  new RectF(50, 50, 240, 200);
            mRectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            if (roundCorners.length == 8) {
                mClipPath.addRoundRect(areas, roundCorners, Path.Direction.CW);
            }
            canvas.drawPath(mClipPath, mRectPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initView(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }
}
