/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 */
package com.tapstream.sdk;

import android.app.Application;
import android.content.Context;
import com.tapstream.sdk.ActivityEventSource;
import com.tapstream.sdk.AdvertisingIdFetcher;
import com.tapstream.sdk.Api;
import com.tapstream.sdk.Config;
import com.tapstream.sdk.ConversionListener;
import com.tapstream.sdk.Core;
import com.tapstream.sdk.CoreListener;
import com.tapstream.sdk.CoreListenerImpl;
import com.tapstream.sdk.Delegate;
import com.tapstream.sdk.Event;
import com.tapstream.sdk.Hit;
import com.tapstream.sdk.Logging;
import com.tapstream.sdk.Platform;
import com.tapstream.sdk.PlatformImpl;
import java.lang.reflect.Constructor;

public class Tapstream
implements Api {
    private static Tapstream instance;
    private Core core;
    private Delegate delegate;
    private CoreListener listener;
    private Platform platform;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Tapstream(Application application, String string2, String string3, Config config) {
        ActivityEventSource activityEventSource;
        this.delegate = new DelegateImpl();
        this.platform = new PlatformImpl((Context)application);
        this.listener = new CoreListenerImpl();
        try {
            activityEventSource = (ActivityEventSource)Class.forName("com.tapstream.sdk.api14.ActivityCallbacks").getConstructor(Application.class).newInstance(new Object[]{application});
        }
        catch (Exception var5_6) {
            activityEventSource = new ActivityEventSource();
        }
        this.core = new Core(this.delegate, this.platform, this.listener, activityEventSource, new AdvertisingIdFetcher(application), string2, string3, config);
        this.core.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void create(Application application, String string2, String string3, Config config) {
        synchronized (Tapstream.class) {
            if (instance == null) {
                instance = new Tapstream(application, string2, string3, config);
            } else {
                Logging.log(5, "Tapstream Warning: Tapstream already instantiated, it cannot be re-created.", new Object[0]);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Tapstream getInstance() {
        synchronized (Tapstream.class) {
            if (instance != null) return instance;
            throw new RuntimeException("You must first call Tapstream.create");
        }
    }

    @Override
    public void fireEvent(Event event) {
        this.core.fireEvent(event);
    }

    @Override
    public void fireHit(Hit hit, Hit.CompletionHandler completionHandler) {
        this.core.fireHit(hit, completionHandler);
    }

    public void getConversionData(ConversionListener conversionListener) {
        this.core.getConversionData(conversionListener);
    }

    private class DelegateImpl
    implements Delegate {
        private DelegateImpl() {
        }

        @Override
        public int getDelay() {
            return Tapstream.this.core.getDelay();
        }

        @Override
        public boolean isRetryAllowed() {
            return true;
        }

        @Override
        public void setDelay(int n2) {
        }
    }

}

