/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.os.Bundle
 */
package com.tapstream.sdk.api14;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.tapstream.sdk.ActivityEventSource;

public class ActivityCallbacks
extends ActivityEventSource
implements Application.ActivityLifecycleCallbacks {
    private final Application app;
    private int startedActivities = -1;

    public ActivityCallbacks(Application application) {
        this.app = application;
        application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)this);
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onActivityStarted(Activity activity) {
        if (this.app != activity.getApplication()) {
            return;
        }
        synchronized (this) {
            ++this.startedActivities;
            if (this.startedActivities == 1 && this.listener != null) {
                this.listener.onOpen();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onActivityStopped(Activity activity) {
        if (this.app != activity.getApplication()) {
            return;
        }
        synchronized (this) {
            --this.startedActivities;
            if (this.startedActivities < 0) {
                this.startedActivities = 0;
            }
            return;
        }
    }
}

