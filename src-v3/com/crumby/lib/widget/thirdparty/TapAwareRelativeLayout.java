package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;

public class TapAwareRelativeLayout extends RelativeLayout {
    private final float MOVE_THRESHOLD_DP;
    private float mDownPosX;
    private float mDownPosY;
    private boolean mMoveOccured;

    public TapAwareRelativeLayout(Context context) {
        super(context);
        this.MOVE_THRESHOLD_DP = 20.0f * getResources().getDisplayMetrics().density;
    }

    public TapAwareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.MOVE_THRESHOLD_DP = 20.0f * getResources().getDisplayMetrics().density;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case JSONzip.zipEmptyObject /*0*/:
                this.mMoveOccured = false;
                this.mDownPosX = ev.getX();
                this.mDownPosY = ev.getY();
                break;
            case Std.STD_FILE /*1*/:
                if (!this.mMoveOccured) {
                    performClick();
                    break;
                }
                break;
            case Std.STD_URL /*2*/:
                if (Math.abs(ev.getX() - this.mDownPosX) > this.MOVE_THRESHOLD_DP || Math.abs(ev.getY() - this.mDownPosY) > this.MOVE_THRESHOLD_DP) {
                    this.mMoveOccured = true;
                    break;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
