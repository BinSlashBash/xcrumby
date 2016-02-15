/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.net.Uri
 *  android.widget.ImageView
 */
package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.net.Uri;
import android.widget.ImageView;

public final class ez
extends ImageView {
    private Uri CO;
    private int CP;
    private int CQ;

    public void L(int n2) {
        this.CP = n2;
    }

    public void e(Uri uri) {
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

