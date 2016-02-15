package com.tapstream.sdk;

import android.app.Application;
import com.tapstream.sdk.Hit.CompletionHandler;

public class Tapstream implements Api {
    private static Tapstream instance;
    private Core core;
    private Delegate delegate;
    private CoreListener listener;
    private Platform platform;

    private class DelegateImpl implements Delegate {
        private DelegateImpl() {
        }

        public int getDelay() {
            return Tapstream.this.core.getDelay();
        }

        public boolean isRetryAllowed() {
            return true;
        }

        public void setDelay(int i) {
        }
    }

    private Tapstream(Application application, String str, String str2, Config config) {
        ActivityEventSource activityEventSource;
        this.delegate = new DelegateImpl();
        this.platform = new PlatformImpl(application);
        this.listener = new CoreListenerImpl();
        try {
            activityEventSource = (ActivityEventSource) Class.forName("com.tapstream.sdk.api14.ActivityCallbacks").getConstructor(new Class[]{Application.class}).newInstance(new Object[]{application});
        } catch (Exception e) {
            activityEventSource = new ActivityEventSource();
        }
        this.core = new Core(this.delegate, this.platform, this.listener, activityEventSource, new AdvertisingIdFetcher(application), str, str2, config);
        this.core.start();
    }

    public static void create(Application application, String str, String str2, Config config) {
        synchronized (Tapstream.class) {
            if (instance == null) {
                instance = new Tapstream(application, str, str2, config);
            } else {
                Logging.log(5, "Tapstream Warning: Tapstream already instantiated, it cannot be re-created.", new Object[0]);
            }
        }
    }

    public static Tapstream getInstance() {
        Tapstream tapstream;
        synchronized (Tapstream.class) {
            if (instance == null) {
                throw new RuntimeException("You must first call Tapstream.create");
            }
            tapstream = instance;
        }
        return tapstream;
    }

    public void fireEvent(Event event) {
        this.core.fireEvent(event);
    }

    public void fireHit(Hit hit, CompletionHandler completionHandler) {
        this.core.fireHit(hit, completionHandler);
    }

    public void getConversionData(ConversionListener conversionListener) {
        this.core.getConversionData(conversionListener);
    }
}
