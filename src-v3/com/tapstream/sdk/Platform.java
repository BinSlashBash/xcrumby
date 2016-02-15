package com.tapstream.sdk;

import java.util.Set;
import java.util.concurrent.ThreadFactory;

interface Platform {
    String getAdvertisingId();

    String getAppName();

    String getAppVersion();

    Boolean getLimitAdTracking();

    String getLocale();

    String getManufacturer();

    String getModel();

    String getOs();

    String getPackageName();

    Set<String> getProcessSet();

    String getReferrer();

    String getResolution();

    Set<String> loadFiredEvents();

    String loadUuid();

    ThreadFactory makeWorkerThreadFactory();

    Response request(String str, String str2, String str3);

    void saveFiredEvents(Set<String> set);
}
