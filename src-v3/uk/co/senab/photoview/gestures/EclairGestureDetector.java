package uk.co.senab.photoview.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;
import uk.co.senab.photoview.Compat;

@TargetApi(5)
public class EclairGestureDetector extends CupcakeGestureDetector {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId;
    private int mActivePointerIndex;

    public EclairGestureDetector(Context context) {
        super(context);
        this.mActivePointerId = INVALID_POINTER_ID;
        this.mActivePointerIndex = 0;
    }

    float getActiveX(MotionEvent ev) {
        try {
            return ev.getX(this.mActivePointerIndex);
        } catch (Exception e) {
            return ev.getX();
        }
    }

    float getActiveY(MotionEvent ev) {
        try {
            return ev.getY(this.mActivePointerIndex);
        } catch (Exception e) {
            return ev.getY();
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int i = 0;
        switch (ev.getAction() & MotionEventCompat.ACTION_MASK) {
            case JSONzip.zipEmptyObject /*0*/:
                this.mActivePointerId = ev.getPointerId(0);
                break;
            case Std.STD_FILE /*1*/:
            case Std.STD_URI /*3*/:
                this.mActivePointerId = INVALID_POINTER_ID;
                break;
            case Std.STD_CURRENCY /*6*/:
                int pointerIndex = Compat.getPointerIndex(ev.getAction());
                if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
                    int newPointerIndex;
                    if (pointerIndex == 0) {
                        newPointerIndex = 1;
                    } else {
                        newPointerIndex = 0;
                    }
                    this.mActivePointerId = ev.getPointerId(newPointerIndex);
                    this.mLastTouchX = ev.getX(newPointerIndex);
                    this.mLastTouchY = ev.getY(newPointerIndex);
                    break;
                }
                break;
        }
        if (this.mActivePointerId != INVALID_POINTER_ID) {
            i = this.mActivePointerId;
        }
        this.mActivePointerIndex = ev.findPointerIndex(i);
        return super.onTouchEvent(ev);
    }
}
