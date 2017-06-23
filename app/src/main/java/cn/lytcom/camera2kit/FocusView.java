package cn.lytcom.camera2kit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FocusView extends View {
    private int mSize;
    private int mCenterX;
    private int mCenterY;
    private int mLength;
    private Paint mPaint;

    public FocusView(Context context, int size) {
        this(context);
        init(size);
    }

    public FocusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSize = context.getResources().getDimensionPixelSize(R.dimen.focus_view_size);
        init(mSize);
    }

    private FocusView(Context context) {
        super(context);
    }

    private void init(int size) {
        mSize = size;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = (int) (mSize / 2.0);
        mCenterY = (int) (mSize / 2.0);
        mLength = (int) (mSize / 2.0) - 2;
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mCenterX - mLength, mCenterY - mLength, mCenterX + mLength, mCenterY + mLength, mPaint);
        canvas.drawLine(2, getHeight() / 2, mSize / 10, getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() - 2, getHeight() / 2, getWidth() - mSize / 10, getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() / 2, 2, getWidth() / 2, mSize / 10, mPaint);
        canvas.drawLine(getWidth() / 2, getHeight() - 2, getWidth() / 2, getHeight() - mSize / 10, mPaint);
    }
}
