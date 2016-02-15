/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.view.MotionEvent
 */
package uk.co.senab.photoview.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import uk.co.senab.photoview.Compat;
import uk.co.senab.photoview.gestures.CupcakeGestureDetector;

@TargetApi(value=5)
public class EclairGestureDetector
extends CupcakeGestureDetector {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = -1;
    private int mActivePointerIndex = 0;

    public EclairGestureDetector(Context context) {
        super(context);
    }

    @Override
    float getActiveX(MotionEvent motionEvent) {
        try {
            float f2 = motionEvent.getX(this.mActivePointerIndex);
            return f2;
        }
        catch (Exception var3_3) {
            return motionEvent.getX();
        }
    }

    @Override
    float getActiveY(MotionEvent motionEvent) {
        try {
            float f2 = motionEvent.getY(this.mActivePointerIndex);
            return f2;
        }
        catch (Exception var3_3) {
            return motionEvent.getY();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean onTouchEvent(MotionEvent var1_1) {
        var3_2 = 0;
        switch (var1_1.getAction() & 255) {
            case 0: {
                this.mActivePointerId = var1_1.getPointerId(0);
                ** break;
            }
            case 1: 
            case 3: {
                this.mActivePointerId = -1;
            }
lbl8: // 3 sources:
            default: {
                ** GOTO lbl17
            }
            case 6: 
        }
        var2_3 = Compat.getPointerIndex(var1_1.getAction());
        if (var1_1.getPointerId(var2_3) == this.mActivePointerId) {
            var2_3 = var2_3 == 0 ? 1 : 0;
            this.mActivePointerId = var1_1.getPointerId(var2_3);
            this.mLastTouchX = var1_1.getX(var2_3);
            this.mLastTouchY = var1_1.getY(var2_3);
        }
lbl17: // 4 sources:
        var2_3 = var3_2;
        if (this.mActivePointerId != -1) {
            var2_3 = this.mActivePointerId;
        }
        this.mActivePointerIndex = var1_1.findPointerIndex(var2_3);
        return super.onTouchEvent(var1_1);
    }
}

