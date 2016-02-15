/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.DetectedActivityCreator;
import java.util.ArrayList;
import java.util.List;

public class ActivityRecognitionResultCreator
implements Parcelable.Creator<ActivityRecognitionResult> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(ActivityRecognitionResult activityRecognitionResult, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.b(parcel, 1, activityRecognitionResult.NP, false);
        b.c(parcel, 1000, activityRecognitionResult.getVersionCode());
        b.a(parcel, 2, activityRecognitionResult.NQ);
        b.a(parcel, 3, activityRecognitionResult.NR);
        b.F(parcel, n2);
    }

    public ActivityRecognitionResult createFromParcel(Parcel parcel) {
        long l2 = 0;
        int n2 = a.o(parcel);
        int n3 = 0;
        ArrayList<DetectedActivity> arrayList = null;
        long l3 = 0;
        block6 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block6;
                }
                case 1: {
                    arrayList = a.c(parcel, n4, DetectedActivity.CREATOR);
                    continue block6;
                }
                case 1000: {
                    n3 = a.g(parcel, n4);
                    continue block6;
                }
                case 2: {
                    l3 = a.i(parcel, n4);
                    continue block6;
                }
                case 3: 
            }
            l2 = a.i(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ActivityRecognitionResult(n3, arrayList, l3, l2);
    }

    public ActivityRecognitionResult[] newArray(int n2) {
        return new ActivityRecognitionResult[n2];
    }
}

