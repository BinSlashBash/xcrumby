/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package com.google.android.gms.wallet.fragment;

import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Dimension {
    public static final int MATCH_PARENT = -1;
    public static final int UNIT_DIP = 1;
    public static final int UNIT_IN = 4;
    public static final int UNIT_MM = 5;
    public static final int UNIT_PT = 3;
    public static final int UNIT_PX = 0;
    public static final int UNIT_SP = 2;
    public static final int WRAP_CONTENT = -2;

    private Dimension() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int a(long l2, DisplayMetrics displayMetrics) {
        int n2 = (int)(l2 >>> 32);
        int n3 = (int)l2;
        switch (n2) {
            default: {
                throw new IllegalStateException("Unexpected unit or type: " + n2);
            }
            case 129: {
                return n3;
            }
            case 128: {
                return TypedValue.complexToDimensionPixelSize((int)n3, (DisplayMetrics)displayMetrics);
            }
            case 0: {
                n2 = 0;
                do {
                    return Math.round(TypedValue.applyDimension((int)n2, (float)Float.intBitsToFloat(n3), (DisplayMetrics)displayMetrics));
                    break;
                } while (true);
            }
            case 1: {
                n2 = 1;
                return Math.round(TypedValue.applyDimension((int)n2, (float)Float.intBitsToFloat(n3), (DisplayMetrics)displayMetrics));
            }
            case 2: {
                n2 = 2;
                return Math.round(TypedValue.applyDimension((int)n2, (float)Float.intBitsToFloat(n3), (DisplayMetrics)displayMetrics));
            }
            case 3: {
                n2 = 3;
                return Math.round(TypedValue.applyDimension((int)n2, (float)Float.intBitsToFloat(n3), (DisplayMetrics)displayMetrics));
            }
            case 4: {
                n2 = 4;
                return Math.round(TypedValue.applyDimension((int)n2, (float)Float.intBitsToFloat(n3), (DisplayMetrics)displayMetrics));
            }
            case 5: 
        }
        n2 = 5;
        return Math.round(TypedValue.applyDimension((int)n2, (float)Float.intBitsToFloat(n3), (DisplayMetrics)displayMetrics));
    }

    public static long a(int n2, float f2) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Unrecognized unit: " + n2);
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
        }
        return Dimension.f(n2, Float.floatToIntBits(f2));
    }

    public static long a(TypedValue typedValue) {
        switch (typedValue.type) {
            default: {
                throw new IllegalArgumentException("Unexpected dimension type: " + typedValue.type);
            }
            case 16: {
                return Dimension.cz(typedValue.data);
            }
            case 5: 
        }
        return Dimension.f(128, typedValue.data);
    }

    public static long cz(int n2) {
        if (n2 < 0) {
            if (n2 == -1 || n2 == -2) {
                return Dimension.f(129, n2);
            }
            throw new IllegalArgumentException("Unexpected dimension value: " + n2);
        }
        return Dimension.a(0, n2);
    }

    private static long f(int n2, int n3) {
        return (long)n2 << 32 | (long)n3 & 0xFFFFFFFFL;
    }
}

