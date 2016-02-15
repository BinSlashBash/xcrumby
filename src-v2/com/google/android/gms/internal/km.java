/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

public class km {
    private final byte[] adH = new byte[256];
    private int adI;
    private int adJ;

    public km(byte[] arrby) {
        int n2;
        for (n2 = 0; n2 < 256; ++n2) {
            this.adH[n2] = (byte)n2;
        }
        int n3 = 0;
        for (n2 = 0; n2 < 256; ++n2) {
            n3 = n3 + this.adH[n2] + arrby[n2 % arrby.length] & 255;
            byte by2 = this.adH[n2];
            this.adH[n2] = this.adH[n3];
            this.adH[n3] = by2;
        }
        this.adI = 0;
        this.adJ = 0;
    }

    public void m(byte[] arrby) {
        int n2 = this.adI;
        int n3 = this.adJ;
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            n2 = n2 + 1 & 255;
            n3 = n3 + this.adH[n2] & 255;
            byte by2 = this.adH[n2];
            this.adH[n2] = this.adH[n3];
            this.adH[n3] = by2;
            arrby[i2] = (byte)(arrby[i2] ^ this.adH[this.adH[n2] + this.adH[n3] & 255]);
        }
        this.adI = n2;
        this.adJ = n3;
    }
}

