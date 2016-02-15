/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

import com.tapstream.sdk.Response;
import java.util.Set;
import java.util.concurrent.ThreadFactory;

interface Platform {
    public String getAdvertisingId();

    public String getAppName();

    public String getAppVersion();

    public Boolean getLimitAdTracking();

    public String getLocale();

    public String getManufacturer();

    public String getModel();

    public String getOs();

    public String getPackageName();

    public Set<String> getProcessSet();

    public String getReferrer();

    public String getResolution();

    public Set<String> loadFiredEvents();

    public String loadUuid();

    public ThreadFactory makeWorkerThreadFactory();

    public Response request(String var1, String var2, String var3);

    public void saveFiredEvents(Set<String> var1);
}

