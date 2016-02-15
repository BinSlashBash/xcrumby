package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.net.Uri;
import android.widget.ImageView;

public final class ez extends ImageView {
    private Uri CO;
    private int CP;
    private int CQ;

    public void m915L(int i) {
        this.CP = i;
    }

    public void m916e(Uri uri) {
        this.CO = uri;
    }

    public int eB() {
        return this.CP;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.CQ != 0) {
            canvas.drawColor(this.CQ);
        }
    }
}
