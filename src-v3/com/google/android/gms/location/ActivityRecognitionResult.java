package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fq;
import java.util.Collections;
import java.util.List;

public class ActivityRecognitionResult implements SafeParcelable {
    public static final ActivityRecognitionResultCreator CREATOR;
    public static final String EXTRA_ACTIVITY_RESULT = "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT";
    List<DetectedActivity> NP;
    long NQ;
    long NR;
    private final int xH;

    static {
        CREATOR = new ActivityRecognitionResultCreator();
    }

    public ActivityRecognitionResult(int versionCode, List<DetectedActivity> probableActivities, long timeMillis, long elapsedRealtimeMillis) {
        this.xH = 1;
        this.NP = probableActivities;
        this.NQ = timeMillis;
        this.NR = elapsedRealtimeMillis;
    }

    public ActivityRecognitionResult(DetectedActivity mostProbableActivity, long time, long elapsedRealtimeMillis) {
        this(Collections.singletonList(mostProbableActivity), time, elapsedRealtimeMillis);
    }

    public ActivityRecognitionResult(List<DetectedActivity> probableActivities, long time, long elapsedRealtimeMillis) {
        boolean z = probableActivities != null && probableActivities.size() > 0;
        fq.m984b(z, (Object) "Must have at least 1 detected activity");
        this.xH = 1;
        this.NP = probableActivities;
        this.NQ = time;
        this.NR = elapsedRealtimeMillis;
    }

    public static ActivityRecognitionResult extractResult(Intent intent) {
        return !hasResult(intent) ? null : (ActivityRecognitionResult) intent.getExtras().get(EXTRA_ACTIVITY_RESULT);
    }

    public static boolean hasResult(Intent intent) {
        return intent == null ? false : intent.hasExtra(EXTRA_ACTIVITY_RESULT);
    }

    public int describeContents() {
        return 0;
    }

    public int getActivityConfidence(int activityType) {
        for (DetectedActivity detectedActivity : this.NP) {
            if (detectedActivity.getType() == activityType) {
                return detectedActivity.getConfidence();
            }
        }
        return 0;
    }

    public long getElapsedRealtimeMillis() {
        return this.NR;
    }

    public DetectedActivity getMostProbableActivity() {
        return (DetectedActivity) this.NP.get(0);
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

    public void writeToParcel(Parcel out, int flags) {
        ActivityRecognitionResultCreator.m1218a(this, out, flags);
    }
}
