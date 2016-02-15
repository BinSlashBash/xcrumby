package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;

public class VerticalScrollView extends ObservableScrollView {
    private float lastX;
    private float lastY;
    private Runnable runnable;
    private float xDistance;
    private float yDistance;

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case JSONzip.zipEmptyObject /*0*/:
                this.yDistance = 0.0f;
                this.xDistance = 0.0f;
                this.lastX = ev.getX();
                this.lastY = ev.getY();
                break;
            case Std.STD_URL /*2*/:
                float curX = ev.getX();
                float curY = ev.getY();
                this.xDistance += Math.abs(curX - this.lastX);
                this.yDistance += Math.abs(curY - this.lastY);
                this.lastX = curX;
                this.lastY = curY;
                if (this.xDistance > this.yDistance) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void loadInitial(Runnable runnable) {
        this.runnable = runnable;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.runnable != null) {
            this.runnable.run();
            this.runnable = null;
        }
    }
}
