package com.tapstream.sdk.api14;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import com.tapstream.sdk.ActivityEventSource;

public class ActivityCallbacks extends ActivityEventSource implements ActivityLifecycleCallbacks {
    private final Application app;
    private int startedActivities;

    public ActivityCallbacks(Application application) {
        this.startedActivities = -1;
        this.app = application;
        application.registerActivityLifecycleCallbacks(this);
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

    public void onActivityStarted(Activity activity) {
        if (this.app == activity.getApplication()) {
            synchronized (this) {
                this.startedActivities++;
                if (this.startedActivities == 1 && this.listener != null) {
                    this.listener.onOpen();
                }
            }
        }
    }

    public void onActivityStopped(Activity activity) {
        if (this.app == activity.getApplication()) {
            synchronized (this) {
                this.startedActivities--;
                if (this.startedActivities < 0) {
                    this.startedActivities = 0;
                }
            }
        }
    }
}
