package com.tapstream.sdk;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.wallet.WalletConstants;
import com.tapstream.sdk.ActivityEventSource.ActivityListener;
import com.tapstream.sdk.Hit.CompletionHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Hex;
import uk.co.senab.photoview.IPhotoView;

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
    private int delay;
    private Delegate delegate;
    private ScheduledThreadPoolExecutor executor;
    private String failingEventId;
    private Set<String> firedEvents;
    private Set<String> firingEvents;
    private CoreListener listener;
    private Platform platform;
    private StringBuilder postData;
    private boolean retainEvents;
    private List<Event> retainedEvents;
    private String secret;

    /* renamed from: com.tapstream.sdk.Core.2 */
    class C06152 implements Runnable {
        final /* synthetic */ Core val$self;

        C06152(Core core) {
            this.val$self = core;
        }

        public void run() {
            synchronized (this.val$self) {
                Core.this.retainEvents = false;
                String referrer = Core.this.platform.getReferrer();
                if (referrer != null && referrer.length() > 0) {
                    Core.this.appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "android-referrer", referrer);
                }
                if (this.val$self.config.getCollectAdvertisingId()) {
                    referrer = Core.this.platform.getAdvertisingId();
                    if (referrer == null || referrer.length() <= 0) {
                        Logging.log(5, "Advertising id could be collected. Is Google Play Services installed?", new Object[0]);
                    } else {
                        Core.this.appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "android-advertising-id", referrer);
                    }
                    Boolean limitAdTracking = Core.this.platform.getLimitAdTracking();
                    if (limitAdTracking != null) {
                        Core.this.appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "android-limit-ad-tracking", limitAdTracking);
                    }
                }
            }
            for (Event fireEvent : Core.this.retainedEvents) {
                Core.this.fireEvent(fireEvent);
            }
            Core.this.retainedEvents = null;
        }
    }

    /* renamed from: com.tapstream.sdk.Core.3 */
    class C06163 implements Runnable {
        final /* synthetic */ StringBuilder val$data;
        final /* synthetic */ Event val$e;
        final /* synthetic */ Core val$self;
        final /* synthetic */ String val$url;

        C06163(StringBuilder stringBuilder, String str, Core core, Event event) {
            this.val$data = stringBuilder;
            this.val$url = str;
            this.val$self = core;
            this.val$e = event;
        }

        public void innerRun() {
            int i;
            if (Core.this.config.getCollectTasteData()) {
                this.val$data.append("&processes=");
                Set processSet = Core.this.platform.getProcessSet();
                StringBuilder stringBuilder = new StringBuilder();
                String[] strArr = (String[]) processSet.toArray(new String[0]);
                for (i = 0; i < strArr.length; i += Core.MAX_THREADS) {
                    if (i != 0) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(strArr[i]);
                }
                this.val$data.append(Utils.encodeString(stringBuilder.toString()));
            }
            Response request = Core.this.platform.request(this.val$url, this.val$data.toString(), "POST");
            int i2 = (request.status < IPhotoView.DEFAULT_ZOOM_DURATION || request.status >= 300) ? Core.MAX_THREADS : 0;
            i = (request.status < 0 || (request.status >= 500 && request.status < 600)) ? Core.MAX_THREADS : 0;
            synchronized (this.val$self) {
                if (this.val$e.isOneTimeOnly()) {
                    this.val$self.firingEvents.remove(this.val$e.getName());
                }
                if (i2 == 0) {
                    if (this.val$e.isOneTimeOnly()) {
                        this.val$self.firedEvents.add(this.val$e.getName());
                        this.val$self.platform.saveFiredEvents(this.val$self.firedEvents);
                        this.val$self.listener.reportOperation("fired-list-saved", this.val$e.getEncodedName());
                    }
                    this.val$self.delay = 0;
                } else if (i != 0) {
                    if (this.val$self.delay == 0) {
                        this.val$self.failingEventId = this.val$e.getUid();
                        this.val$self.increaseDelay();
                    } else if (this.val$self.failingEventId == this.val$e.getUid()) {
                        this.val$self.increaseDelay();
                    }
                }
            }
            Object[] objArr;
            if (i2 != 0) {
                if (request.status < 0) {
                    objArr = new Object[Core.MAX_THREADS];
                    objArr[0] = request.message;
                    Logging.log(6, "Tapstream Error: Failed to fire event, error=%s", objArr);
                } else if (request.status == WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
                    objArr = new Object[Core.MAX_THREADS];
                    objArr[0] = Integer.valueOf(request.status);
                    Logging.log(6, "Tapstream Error: Failed to fire event, http code %d\nDoes your event name contain characters that are not url safe? This event will not be retried.", objArr);
                } else if (request.status == 403) {
                    objArr = new Object[Core.MAX_THREADS];
                    objArr[0] = Integer.valueOf(request.status);
                    Logging.log(6, "Tapstream Error: Failed to fire event, http code %d\nAre your account name and application secret correct?  This event will not be retried.", objArr);
                } else {
                    String str = UnsupportedUrlFragment.DISPLAY_NAME;
                    if (i == 0) {
                        str = "  This event will not be retried.";
                    }
                    Logging.log(6, "Tapstream Error: Failed to fire event, http code %d.%s", Integer.valueOf(request.status), str);
                }
                this.val$self.listener.reportOperation("event-failed", this.val$e.getEncodedName());
                if (i != 0) {
                    this.val$self.listener.reportOperation("retry", this.val$e.getEncodedName());
                    this.val$self.listener.reportOperation("job-ended", this.val$e.getEncodedName());
                    if (this.val$self.delegate.isRetryAllowed()) {
                        this.val$self.fireEvent(this.val$e);
                        return;
                    }
                    return;
                }
            }
            objArr = new Object[Core.MAX_THREADS];
            objArr[0] = this.val$e.getName();
            Logging.log(4, "Tapstream fired event named \"%s\"", objArr);
            this.val$self.listener.reportOperation("event-succeeded", this.val$e.getEncodedName());
            this.val$self.listener.reportOperation("job-ended", this.val$e.getEncodedName());
        }

        public void run() {
            try {
                innerRun();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.tapstream.sdk.Core.4 */
    class C06174 implements Runnable {
        final /* synthetic */ CompletionHandler val$completion;
        final /* synthetic */ String val$data;
        final /* synthetic */ Hit val$h;
        final /* synthetic */ String val$url;

        C06174(String str, String str2, Hit hit, CompletionHandler completionHandler) {
            this.val$url = str;
            this.val$data = str2;
            this.val$h = hit;
            this.val$completion = completionHandler;
        }

        public void run() {
            Response request = Core.this.platform.request(this.val$url, this.val$data, "POST");
            Object[] objArr;
            if (request.status < IPhotoView.DEFAULT_ZOOM_DURATION || request.status >= 300) {
                objArr = new Object[Core.MAX_THREADS];
                objArr[0] = Integer.valueOf(request.status);
                Logging.log(6, "Tapstream Error: Failed to fire hit, http code: %d", objArr);
                Core.this.listener.reportOperation("hit-failed");
            } else {
                objArr = new Object[Core.MAX_THREADS];
                objArr[0] = this.val$h.getTrackerName();
                Logging.log(4, "Tapstream fired hit to tracker: %s", objArr);
                Core.this.listener.reportOperation("hit-succeeded");
            }
            if (this.val$completion != null) {
                this.val$completion.complete(request);
            }
        }
    }

    /* renamed from: com.tapstream.sdk.Core.5 */
    class C06185 implements Runnable {
        private int tries;
        final /* synthetic */ ConversionListener val$completion;
        final /* synthetic */ String val$url;

        C06185(String str, ConversionListener conversionListener) {
            this.val$url = str;
            this.val$completion = conversionListener;
            this.tries = 0;
        }

        public void run() {
            this.tries += Core.MAX_THREADS;
            Response request = Core.this.platform.request(this.val$url, null, "GET");
            if (request.status >= IPhotoView.DEFAULT_ZOOM_DURATION && request.status < 300 && !Pattern.compile("^\\s*\\[\\s*\\]\\s*$").matcher(request.data).matches()) {
                this.val$completion.conversionData(request.data);
            } else if (this.tries >= Core.CONVERSION_POLL_COUNT) {
                this.val$completion.conversionData(null);
            } else {
                Core.this.executor.schedule(this, 1, TimeUnit.SECONDS);
            }
        }
    }

    /* renamed from: com.tapstream.sdk.Core.1 */
    class C11681 implements ActivityListener {
        final /* synthetic */ String val$appNameFinal;

        C11681(String str) {
            this.val$appNameFinal = str;
        }

        public void onOpen() {
            if (Core.this.config.getFireAutomaticOpenEvent()) {
                String openEventName = Core.this.config.getOpenEventName();
                if (openEventName != null) {
                    Core.this.fireEvent(new Event(openEventName, false));
                    return;
                }
                Core core = Core.this;
                Object[] objArr = new Object[Core.MAX_THREADS];
                objArr[0] = this.val$appNameFinal;
                core.fireEvent(new Event(String.format(Locale.US, "android-%s-open", objArr), false));
            }
        }
    }

    Core(Delegate delegate, Platform platform, CoreListener coreListener, ActivityEventSource activityEventSource, Runnable runnable, String str, String str2, Config config) {
        this.postData = null;
        this.firingEvents = new HashSet(16);
        this.firedEvents = new HashSet(16);
        this.failingEventId = null;
        this.delay = 0;
        this.retainEvents = true;
        this.retainedEvents = new ArrayList();
        this.delegate = delegate;
        this.platform = platform;
        this.listener = coreListener;
        this.activityEventSource = activityEventSource;
        this.adIdFetcher = runnable;
        this.config = config;
        this.accountName = clean(str);
        this.secret = str2;
        makePostArgs();
        this.firedEvents = platform.loadFiredEvents();
        this.executor = new ScheduledThreadPoolExecutor(MAX_THREADS, platform.makeWorkerThreadFactory());
        this.executor.prestartAllCoreThreads();
    }

    private void appendPostPair(String str, String str2, Object obj) {
        String encodeEventPair = Utils.encodeEventPair(str, str2, obj, true);
        if (encodeEventPair != null) {
            if (this.postData == null) {
                this.postData = new StringBuilder();
            } else {
                this.postData.append("&");
            }
            this.postData.append(encodeEventPair);
        }
    }

    private String clean(String str) {
        try {
            return URLEncoder.encode(str.toLowerCase().trim(), Hex.DEFAULT_CHARSET_NAME).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return UnsupportedUrlFragment.DISPLAY_NAME;
        }
    }

    private void increaseDelay() {
        int i = 60;
        if (this.delay == 0) {
            this.delay = 2;
        } else {
            int pow = (int) Math.pow(2.0d, (double) (Math.round(Math.log((double) this.delay) / Math.log(2.0d)) + 1));
            if (pow <= 60) {
                i = pow;
            }
            this.delay = i;
        }
        this.listener.reportOperation("increased-delay");
    }

    private void makePostArgs() {
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "secret", this.secret);
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "sdkversion", VERSION);
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "hardware", this.config.getHardware());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "hardware-odin1", this.config.getOdin1());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "hardware-open-udid", this.config.getOpenUdid());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "hardware", this.config.getHardware());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "hardware-wifi-mac", this.config.getWifiMac());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "hardware-android-device-id", this.config.getDeviceId());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "hardware-android-android-id", this.config.getAndroidId());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "uuid", this.platform.loadUuid());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "platform", "Android");
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "vendor", this.platform.getManufacturer());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "model", this.platform.getModel());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "os", this.platform.getOs());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "resolution", this.platform.getResolution());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "locale", this.platform.getLocale());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "app-name", this.platform.getAppName());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "app-version", this.platform.getAppVersion());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "package-name", this.platform.getPackageName());
        appendPostPair(UnsupportedUrlFragment.DISPLAY_NAME, "gmtoffset", Integer.valueOf(TimeZone.getDefault().getOffset(new Date().getTime()) / GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE));
    }

    public synchronized void fireEvent(Event event) {
        if (this.retainEvents) {
            this.retainedEvents.add(event);
        } else {
            if (event.isTransaction()) {
                event.setNamePrefix(this.appName);
            }
            event.prepare(this.config.globalEventParams);
            if (event.isOneTimeOnly()) {
                Object[] objArr;
                if (this.firedEvents.contains(event.getName())) {
                    objArr = new Object[MAX_THREADS];
                    objArr[0] = event.getName();
                    Logging.log(4, "Tapstream ignoring event named \"%s\" because it is a one-time-only event that has already been fired", objArr);
                    this.listener.reportOperation("event-ignored-already-fired", event.getEncodedName());
                    this.listener.reportOperation("job-ended", event.getEncodedName());
                } else if (this.firingEvents.contains(event.getName())) {
                    objArr = new Object[MAX_THREADS];
                    objArr[0] = event.getName();
                    Logging.log(4, "Tapstream ignoring event named \"%s\" because it is a one-time-only event that is already in progress", objArr);
                    this.listener.reportOperation("event-ignored-already-in-progress", event.getEncodedName());
                    this.listener.reportOperation("job-ended", event.getEncodedName());
                } else {
                    this.firingEvents.add(event.getName());
                }
            }
            String format = String.format(Locale.US, EVENT_URL_TEMPLATE, new Object[]{this.accountName, event.getEncodedName()});
            StringBuilder stringBuilder = new StringBuilder(this.postData.toString());
            stringBuilder.append(event.getPostData());
            this.executor.schedule(new C06163(stringBuilder, format, this, event), (long) this.delegate.getDelay(), TimeUnit.SECONDS);
        }
    }

    public void fireHit(Hit hit, CompletionHandler completionHandler) {
        this.executor.schedule(new C06174(String.format(Locale.US, HIT_URL_TEMPLATE, new Object[]{this.accountName, hit.getEncodedTrackerName()}), hit.getPostData(), hit, completionHandler), 0, TimeUnit.SECONDS);
    }

    public void getConversionData(ConversionListener conversionListener) {
        if (conversionListener != null) {
            this.executor.schedule(new C06185(String.format(Locale.US, CONVERSION_URL_TEMPLATE, new Object[]{this.secret, this.platform.loadUuid()}), conversionListener), 1, TimeUnit.SECONDS);
        }
    }

    public int getDelay() {
        return this.delay;
    }

    public String getPostData() {
        return this.postData.toString();
    }

    public void start() {
        String installEventName;
        this.appName = this.platform.getAppName();
        if (this.appName == null) {
            this.appName = UnsupportedUrlFragment.DISPLAY_NAME;
        }
        String str = this.appName;
        if (this.config.getFireAutomaticInstallEvent()) {
            installEventName = this.config.getInstallEventName();
            if (installEventName != null) {
                fireEvent(new Event(installEventName, true));
            } else {
                Object[] objArr = new Object[MAX_THREADS];
                objArr[0] = this.appName;
                fireEvent(new Event(String.format(Locale.US, "android-%s-install", objArr), true));
            }
        }
        if (this.config.getFireAutomaticOpenEvent()) {
            installEventName = this.config.getOpenEventName();
            if (installEventName != null) {
                fireEvent(new Event(installEventName, false));
            } else {
                objArr = new Object[MAX_THREADS];
                objArr[0] = this.appName;
                fireEvent(new Event(String.format(Locale.US, "android-%s-open", objArr), false));
            }
        }
        this.activityEventSource.setListener(new C11681(str));
        if (this.adIdFetcher != null && this.config.getCollectAdvertisingId()) {
            this.executor.schedule(this.adIdFetcher, 0, TimeUnit.SECONDS);
        }
        this.executor.schedule(new C06152(this), 3, TimeUnit.SECONDS);
    }
}
