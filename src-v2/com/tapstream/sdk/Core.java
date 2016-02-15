/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

import com.tapstream.sdk.ActivityEventSource;
import com.tapstream.sdk.Config;
import com.tapstream.sdk.ConversionListener;
import com.tapstream.sdk.CoreListener;
import com.tapstream.sdk.Delegate;
import com.tapstream.sdk.Event;
import com.tapstream.sdk.Hit;
import com.tapstream.sdk.Logging;
import com.tapstream.sdk.Platform;
import com.tapstream.sdk.Response;
import com.tapstream.sdk.Utils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Core {
    private static final int CONVERSION_POLL_COUNT = 10;
    private static final int CONVERSION_POLL_INTERVAL = 1;
    private static final String CONVERSION_URL_TEMPLATE = "https://reporting.tapstream.com/v1/timelines/lookup?secret=%s&event_session=%s";
    private static final int EVENT_RETENTION_TIME = 3;
    private static final String EVENT_URL_TEMPLATE = "https://api.tapstream.com/%s/event/%s/";
    private static final String HIT_URL_TEMPLATE = "http://api.tapstream.com/%s/hit/%s.gif";
    private static final int MAX_THREADS = 1;
    public static final String VERSION = "2.8.0";
    private String accountName;
    private ActivityEventSource activityEventSource;
    private Runnable adIdFetcher;
    private String appName;
    private Config config;
    private int delay = 0;
    private Delegate delegate;
    private ScheduledThreadPoolExecutor executor;
    private String failingEventId = null;
    private Set<String> firedEvents = new HashSet<String>(16);
    private Set<String> firingEvents = new HashSet<String>(16);
    private CoreListener listener;
    private Platform platform;
    private StringBuilder postData = null;
    private boolean retainEvents = true;
    private List<Event> retainedEvents = new ArrayList<Event>();
    private String secret;

    Core(Delegate delegate, Platform platform, CoreListener coreListener, ActivityEventSource activityEventSource, Runnable runnable, String string2, String string3, Config config) {
        this.delegate = delegate;
        this.platform = platform;
        this.listener = coreListener;
        this.activityEventSource = activityEventSource;
        this.adIdFetcher = runnable;
        this.config = config;
        this.accountName = this.clean(string2);
        this.secret = string3;
        this.makePostArgs();
        this.firedEvents = platform.loadFiredEvents();
        this.executor = new ScheduledThreadPoolExecutor(1, platform.makeWorkerThreadFactory());
        this.executor.prestartAllCoreThreads();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void appendPostPair(String string2, String string3, Object object) {
        if ((string2 = Utils.encodeEventPair(string2, string3, object, true)) == null) {
            return;
        }
        if (this.postData == null) {
            this.postData = new StringBuilder();
        } else {
            this.postData.append("&");
        }
        this.postData.append(string2);
    }

    private String clean(String string2) {
        try {
            string2 = URLEncoder.encode(string2.toLowerCase().trim(), "UTF-8").replace("+", "%20");
            return string2;
        }
        catch (UnsupportedEncodingException var1_2) {
            var1_2.printStackTrace();
            return "";
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void increaseDelay() {
        int n2 = 60;
        if (this.delay == 0) {
            this.delay = 2;
        } else {
            int n3 = (int)Math.pow(2.0, Math.round(Math.log(this.delay) / Math.log(2.0)) + 1);
            if (n3 <= 60) {
                n2 = n3;
            }
            this.delay = n2;
        }
        this.listener.reportOperation("increased-delay");
    }

    private void makePostArgs() {
        this.appendPostPair("", "secret", this.secret);
        this.appendPostPair("", "sdkversion", "2.8.0");
        this.appendPostPair("", "hardware", this.config.getHardware());
        this.appendPostPair("", "hardware-odin1", this.config.getOdin1());
        this.appendPostPair("", "hardware-open-udid", this.config.getOpenUdid());
        this.appendPostPair("", "hardware", this.config.getHardware());
        this.appendPostPair("", "hardware-wifi-mac", this.config.getWifiMac());
        this.appendPostPair("", "hardware-android-device-id", this.config.getDeviceId());
        this.appendPostPair("", "hardware-android-android-id", this.config.getAndroidId());
        this.appendPostPair("", "uuid", this.platform.loadUuid());
        this.appendPostPair("", "platform", "Android");
        this.appendPostPair("", "vendor", this.platform.getManufacturer());
        this.appendPostPair("", "model", this.platform.getModel());
        this.appendPostPair("", "os", this.platform.getOs());
        this.appendPostPair("", "resolution", this.platform.getResolution());
        this.appendPostPair("", "locale", this.platform.getLocale());
        this.appendPostPair("", "app-name", this.platform.getAppName());
        this.appendPostPair("", "app-version", this.platform.getAppVersion());
        this.appendPostPair("", "package-name", this.platform.getPackageName());
        this.appendPostPair("", "gmtoffset", TimeZone.getDefault().getOffset(new Date().getTime()) / 1000);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void fireEvent(Event object) {
        // MONITORENTER : this
        if (this.retainEvents) {
            this.retainedEvents.add((Event)object);
            // MONITOREXIT : this
            return;
        }
        if (object.isTransaction()) {
            object.setNamePrefix(this.appName);
        }
        object.prepare(this.config.globalEventParams);
        if (object.isOneTimeOnly()) {
            if (this.firedEvents.contains(object.getName())) {
                Logging.log(4, "Tapstream ignoring event named \"%s\" because it is a one-time-only event that has already been fired", object.getName());
                this.listener.reportOperation("event-ignored-already-fired", object.getEncodedName());
                this.listener.reportOperation("job-ended", object.getEncodedName());
                return;
            }
            if (this.firingEvents.contains(object.getName())) {
                Logging.log(4, "Tapstream ignoring event named \"%s\" because it is a one-time-only event that is already in progress", object.getName());
                this.listener.reportOperation("event-ignored-already-in-progress", object.getEncodedName());
                this.listener.reportOperation("job-ended", object.getEncodedName());
                return;
            }
            this.firingEvents.add(object.getName());
        }
        final String string2 = String.format(Locale.US, "https://api.tapstream.com/%s/event/%s/", this.accountName, object.getEncodedName());
        final StringBuilder stringBuilder = new StringBuilder(this.postData.toString());
        stringBuilder.append(object.getPostData());
        object = new Runnable((Event)object){
            final /* synthetic */ Event val$e;

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            public void innerRun() {
                int n2;
                String[] arrstring;
                Object object;
                if (Core.this.config.getCollectTasteData()) {
                    stringBuilder.append("&processes=");
                    arrstring = Core.this.platform.getProcessSet();
                    object = new StringBuilder();
                    arrstring = arrstring.toArray(new String[0]);
                    for (n2 = 0; n2 < arrstring.length; ++n2) {
                        if (n2 != 0) {
                            object.append(',');
                        }
                        object.append(arrstring[n2]);
                    }
                    stringBuilder.append(Utils.encodeString(object.toString()));
                }
                arrstring = Core.this.platform.request(string2, stringBuilder.toString(), "POST");
                n2 = arrstring.status < 200 || arrstring.status >= 300 ? 1 : 0;
                boolean bl2 = arrstring.status < 0 || arrstring.status >= 500 && arrstring.status < 600;
                object = this;
                // MONITORENTER : object
                if (this.val$e.isOneTimeOnly()) {
                    this.firingEvents.remove(this.val$e.getName());
                }
                if (n2 != 0) {
                    if (bl2) {
                        if (this.delay == 0) {
                            this.failingEventId = this.val$e.getUid();
                            this.increaseDelay();
                        } else if (this.failingEventId == this.val$e.getUid()) {
                            this.increaseDelay();
                        }
                    }
                } else {
                    if (this.val$e.isOneTimeOnly()) {
                        this.firedEvents.add(this.val$e.getName());
                        this.platform.saveFiredEvents(this.firedEvents);
                        this.listener.reportOperation("fired-list-saved", this.val$e.getEncodedName());
                    }
                    this.delay = 0;
                }
                // MONITOREXIT : object
                if (n2 != 0) {
                    if (arrstring.status < 0) {
                        Logging.log(6, "Tapstream Error: Failed to fire event, error=%s", arrstring.message);
                    } else if (arrstring.status == 404) {
                        Logging.log(6, "Tapstream Error: Failed to fire event, http code %d\nDoes your event name contain characters that are not url safe? This event will not be retried.", arrstring.status);
                    } else if (arrstring.status == 403) {
                        Logging.log(6, "Tapstream Error: Failed to fire event, http code %d\nAre your account name and application secret correct?  This event will not be retried.", arrstring.status);
                    } else {
                        object = "";
                        if (!bl2) {
                            object = "  This event will not be retried.";
                        }
                        Logging.log(6, "Tapstream Error: Failed to fire event, http code %d.%s", arrstring.status, object);
                    }
                    this.listener.reportOperation("event-failed", this.val$e.getEncodedName());
                    if (bl2) {
                        this.listener.reportOperation("retry", this.val$e.getEncodedName());
                        this.listener.reportOperation("job-ended", this.val$e.getEncodedName());
                        if (!this.delegate.isRetryAllowed()) return;
                        this.fireEvent(this.val$e);
                        return;
                    }
                } else {
                    Logging.log(4, "Tapstream fired event named \"%s\"", this.val$e.getName());
                    this.listener.reportOperation("event-succeeded", this.val$e.getEncodedName());
                }
                this.listener.reportOperation("job-ended", this.val$e.getEncodedName());
            }

            @Override
            public void run() {
                try {
                    this.innerRun();
                    return;
                }
                catch (Exception var1_1) {
                    var1_1.printStackTrace();
                    return;
                }
            }
        };
        int n2 = this.delegate.getDelay();
        this.executor.schedule((Runnable)object, (long)n2, TimeUnit.SECONDS);
    }

    public void fireHit(Hit object, Hit.CompletionHandler completionHandler) {
        object = new Runnable(String.format(Locale.US, "http://api.tapstream.com/%s/hit/%s.gif", this.accountName, object.getEncodedTrackerName()), object.getPostData(), (Hit)object, completionHandler){
            final /* synthetic */ Hit.CompletionHandler val$completion;
            final /* synthetic */ String val$data;
            final /* synthetic */ Hit val$h;
            final /* synthetic */ String val$url;

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void run() {
                Response response = Core.this.platform.request(this.val$url, this.val$data, "POST");
                if (response.status < 200 || response.status >= 300) {
                    Logging.log(6, "Tapstream Error: Failed to fire hit, http code: %d", response.status);
                    Core.this.listener.reportOperation("hit-failed");
                } else {
                    Logging.log(4, "Tapstream fired hit to tracker: %s", this.val$h.getTrackerName());
                    Core.this.listener.reportOperation("hit-succeeded");
                }
                if (this.val$completion != null) {
                    this.val$completion.complete(response);
                }
            }
        };
        this.executor.schedule((Runnable)object, 0, TimeUnit.SECONDS);
    }

    public void getConversionData(ConversionListener object) {
        if (object != null) {
            object = new Runnable(String.format(Locale.US, "https://reporting.tapstream.com/v1/timelines/lookup?secret=%s&event_session=%s", this.secret, this.platform.loadUuid()), (ConversionListener)object){
                private int tries;
                final /* synthetic */ ConversionListener val$completion;
                final /* synthetic */ String val$url;

                @Override
                public void run() {
                    ++this.tries;
                    Response response = Core.this.platform.request(this.val$url, null, "GET");
                    if (response.status >= 200 && response.status < 300 && !Pattern.compile("^\\s*\\[\\s*\\]\\s*$").matcher(response.data).matches()) {
                        this.val$completion.conversionData(response.data);
                        return;
                    }
                    if (this.tries >= 10) {
                        this.val$completion.conversionData(null);
                        return;
                    }
                    Core.this.executor.schedule(this, 1, TimeUnit.SECONDS);
                }
            };
            this.executor.schedule((Runnable)object, 1, TimeUnit.SECONDS);
        }
    }

    public int getDelay() {
        return this.delay;
    }

    public String getPostData() {
        return this.postData.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void start() {
        String string2;
        this.appName = this.platform.getAppName();
        if (this.appName == null) {
            this.appName = "";
        }
        final String string3 = this.appName;
        if (this.config.getFireAutomaticInstallEvent()) {
            string2 = this.config.getInstallEventName();
            if (string2 != null) {
                this.fireEvent(new Event(string2, true));
            } else {
                this.fireEvent(new Event(String.format(Locale.US, "android-%s-install", this.appName), true));
            }
        }
        if (this.config.getFireAutomaticOpenEvent()) {
            string2 = this.config.getOpenEventName();
            if (string2 != null) {
                this.fireEvent(new Event(string2, false));
            } else {
                this.fireEvent(new Event(String.format(Locale.US, "android-%s-open", this.appName), false));
            }
        }
        this.activityEventSource.setListener(new ActivityEventSource.ActivityListener(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public void onOpen() {
                if (!Core.this.config.getFireAutomaticOpenEvent()) return;
                String string2 = Core.this.config.getOpenEventName();
                if (string2 != null) {
                    Core.this.fireEvent(new Event(string2, false));
                    return;
                }
                Core.this.fireEvent(new Event(String.format(Locale.US, "android-%s-open", string3), false));
            }
        });
        if (this.adIdFetcher != null && this.config.getCollectAdvertisingId()) {
            this.executor.schedule(this.adIdFetcher, 0, TimeUnit.SECONDS);
        }
        this.executor.schedule(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object;
                Object object2 = this;
                synchronized (object2) {
                    Core.this.retainEvents = false;
                    object = Core.this.platform.getReferrer();
                    if (object != null && object.length() > 0) {
                        Core.this.appendPostPair("", "android-referrer", object);
                    }
                    if (this.config.getCollectAdvertisingId()) {
                        object = Core.this.platform.getAdvertisingId();
                        if (object != null && object.length() > 0) {
                            Core.this.appendPostPair("", "android-advertising-id", object);
                        } else {
                            Logging.log(5, "Advertising id could be collected. Is Google Play Services installed?", new Object[0]);
                        }
                        if ((object = Core.this.platform.getLimitAdTracking()) != null) {
                            Core.this.appendPostPair("", "android-limit-ad-tracking", object);
                        }
                    }
                }
                object2 = Core.this.retainedEvents.iterator();
                do {
                    if (!object2.hasNext()) {
                        Core.this.retainedEvents = null;
                        return;
                    }
                    object = (Event)object2.next();
                    Core.this.fireEvent((Event)object);
                } while (true);
            }
        }, 3, TimeUnit.SECONDS);
    }

}

