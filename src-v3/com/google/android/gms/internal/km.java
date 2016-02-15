package com.google.android.gms.internal;

import android.support.v4.view.MotionEventCompat;
import org.json.zip.JSONzip;

public class km {
    private final byte[] adH;
    private int adI;
    private int adJ;

    public km(byte[] bArr) {
        int i;
        this.adH = new byte[JSONzip.end];
        for (i = 0; i < JSONzip.end; i++) {
            this.adH[i] = (byte) i;
        }
        i = 0;
        for (int i2 = 0; i2 < JSONzip.end; i2++) {
            i = ((i + this.adH[i2]) + bArr[i2 % bArr.length]) & MotionEventCompat.ACTION_MASK;
            byte b = this.adH[i2];
            this.adH[i2] = this.adH[i];
            this.adH[i] = b;
        }
        this.adI = 0;
        this.adJ = 0;
    }

    public void m1122m(byte[] bArr) {
        int i = this.adI;
        int i2 = this.adJ;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) & MotionEventCompat.ACTION_MASK;
            i2 = (i2 + this.adH[i]) & MotionEventCompat.ACTION_MASK;
            byte b = this.adH[i];
            this.adH[i] = this.adH[i2];
            this.adH[i2] = b;
            bArr[i3] = (byte) (bArr[i3] ^ this.adH[(this.adH[i] + this.adH[i2]) & MotionEventCompat.ACTION_MASK]);
        }
        this.adI = i;
        this.adJ = i2;
    }
}
