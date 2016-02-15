/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.DetectedActivityCreator;

public class DetectedActivity
implements SafeParcelable {
    public static final DetectedActivityCreator CREATOR = new DetectedActivityCreator();
    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int RUNNING = 8;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int UNKNOWN = 4;
    public static final int WALKING = 7;
    int NS;
    int NT;
    private final int xH;

    public DetectedActivity(int n2, int n3) {
        this.xH = 1;
        this.NS = n2;
        this.NT = n3;
    }

    public DetectedActivity(int n2, int n3, int n4) {
        this.xH = n2;
        this.NS = n3;
        this.NT = n4;
    }

    private int bv(int n2) {
        int n3 = n2;
        if (n2 > 8) {
            n3 = 4;
        }
        return n3;
    }

    public int describeContents() {
        return 0;
    }

    public int getConfidence() {
        return this.NT;
    }

    public int getType() {
        return this.bv(this.NS);
    }

    public int getVersionCode() {
        return this.xH;
    }

    public String toString() {
        return "DetectedActivity [type=" + this.getType() + ", confidence=" + this.NT + "]";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        DetectedActivityCreator.a(this, parcel, n2);
    }
}

