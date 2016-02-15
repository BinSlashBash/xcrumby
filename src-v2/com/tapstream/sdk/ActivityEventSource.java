/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

public class ActivityEventSource {
    protected ActivityListener listener = null;

    public void setListener(ActivityListener activityListener) {
        this.listener = activityListener;
    }

    public static interface ActivityListener {
        public void onOpen();
    }

}

