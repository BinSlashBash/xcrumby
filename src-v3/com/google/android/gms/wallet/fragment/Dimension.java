package com.google.android.gms.wallet.fragment;

import android.support.v4.media.TransportMediator;
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

    public static int m1493a(long j, DisplayMetrics displayMetrics) {
        int i = (int) (j >>> 32);
        int i2 = (int) j;
        switch (i) {
            case UNIT_PX /*0*/:
                i = UNIT_PX;
                break;
            case UNIT_DIP /*1*/:
                i = UNIT_DIP;
                break;
            case UNIT_SP /*2*/:
                i = UNIT_SP;
                break;
            case UNIT_PT /*3*/:
                i = UNIT_PT;
                break;
            case UNIT_IN /*4*/:
                i = UNIT_IN;
                break;
            case UNIT_MM /*5*/:
                i = UNIT_MM;
                break;
            case TransportMediator.FLAG_KEY_MEDIA_NEXT /*128*/:
                return TypedValue.complexToDimensionPixelSize(i2, displayMetrics);
            case 129:
                return i2;
            default:
                throw new IllegalStateException("Unexpected unit or type: " + i);
        }
        return Math.round(TypedValue.applyDimension(i, Float.intBitsToFloat(i2), displayMetrics));
    }

    public static long m1494a(int i, float f) {
        switch (i) {
            case UNIT_PX /*0*/:
            case UNIT_DIP /*1*/:
            case UNIT_SP /*2*/:
            case UNIT_PT /*3*/:
            case UNIT_IN /*4*/:
            case UNIT_MM /*5*/:
                return m1496f(i, Float.floatToIntBits(f));
            default:
                throw new IllegalArgumentException("Unrecognized unit: " + i);
        }
    }

    public static long m1495a(TypedValue typedValue) {
        switch (typedValue.type) {
            case UNIT_MM /*5*/:
                return m1496f(TransportMediator.FLAG_KEY_MEDIA_NEXT, typedValue.data);
            case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                return cz(typedValue.data);
            default:
                throw new IllegalArgumentException("Unexpected dimension type: " + typedValue.type);
        }
    }

    public static long cz(int i) {
        if (i >= 0) {
            return m1494a((int) UNIT_PX, (float) i);
        }
        if (i == MATCH_PARENT || i == WRAP_CONTENT) {
            return m1496f(129, i);
        }
        throw new IllegalArgumentException("Unexpected dimension value: " + i);
    }

    private static long m1496f(int i, int i2) {
        return (((long) i) << 32) | (((long) i2) & 4294967295L);
    }
}
