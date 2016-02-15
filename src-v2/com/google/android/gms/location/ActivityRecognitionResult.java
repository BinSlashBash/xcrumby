/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fq;
import com.google.android.gms.location.ActivityRecognitionResultCreator;
import com.google.android.gms.location.DetectedActivity;
import java.util.Collections;
import java.util.List;

public class ActivityRecognitionResult
implements SafeParcelable {
    public static final ActivityRecognitionResultCreator CREATOR = new ActivityRecognitionResultCreator();
    public static final String EXTRA_ACTIVITY_RESULT = "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT";
    List<DetectedActivity> NP;
    long NQ;
    long NR;
    private final int xH;

    public ActivityRecognitionResult(int n2, List<DetectedActivity> list, long l2, long l3) {
        this.xH = 1;
        this.NP = list;
        this.NQ = l2;
        this.NR = l3;
    }

    public ActivityRecognitionResult(DetectedActivity detectedActivity, long l2, long l3) {
        this(Collections.singletonList(detectedActivity), l2, l3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ActivityRecognitionResult(List<DetectedActivity> list, long l2, long l3) {
        boolean bl2 = list != null && list.size() > 0;
        fq.b(bl2, (Object)"Must have at least 1 detected activity");
        this.xH = 1;
        this.NP = list;
        this.NQ = l2;
        this.NR = l3;
    }

    public static ActivityRecognitionResult extractResult(Intent intent) {
        if (!ActivityRecognitionResult.hasResult(intent)) {
            return null;
        }
        return (ActivityRecognitionResult)intent.getExtras().get("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
    }

    public static boolean hasResult(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
    }

    public int describeContents() {
        return 0;
    }

    public int getActivityConfidence(int n2) {
        for (DetectedActivity detectedActivity : this.NP) {
            if (detectedActivity.getType() != n2) continue;
            return detectedActivity.getConfidence();
        }
        return 0;
    }

    public long getElapsedRealtimeMillis() {
        return this.NR;
    }

    public DetectedActivity getMostProbableActivity() {
        return this.NP.get(0);
    }

    public List<DetectedActivity> getProbableActivities() {
        return this.NP;
    }

    public long getTime() {
        return this.NQ;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public String toString() {
        return "ActivityRecognitionResult [probableActivities=" + this.NP + ", timeMillis=" + this.NQ + ", elapsedRealtimeMillis=" + this.NR + "]";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ActivityRecognitionResultCreator.a(this, parcel, n2);
    }
}

