package cn.lytcom.camera2kit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class PreviewOverlay extends View {
    private GestureDetector mGestureDetector = null;

    public PreviewOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent m) {
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(m);
        }
        return true;
    }

    public void setGestureListener(GestureDetector.OnGestureListener gestureListener) {
        if (gestureListener != null) {
            mGestureDetector = new GestureDetector(getContext(), gestureListener);
        } else {
            mGestureDetector = null;
        }
    }

    public boolean hasGestureDetector() {
        return mGestureDetector != null;
    }
}
