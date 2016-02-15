package com.tapstream.sdk;

public class ActivityEventSource {
    protected ActivityListener listener;

    public interface ActivityListener {
        void onOpen();
    }

    public ActivityEventSource() {
        this.listener = null;
    }

    public void setListener(ActivityListener activityListener) {
        this.listener = activityListener;
    }
}
