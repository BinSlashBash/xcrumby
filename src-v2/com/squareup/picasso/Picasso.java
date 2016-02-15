/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Process
 *  android.widget.ImageView
 *  android.widget.RemoteViews
 */
package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.squareup.picasso.Action;
import com.squareup.picasso.BitmapHunter;
import com.squareup.picasso.Cache;
import com.squareup.picasso.DeferredRequestCreator;
import com.squareup.picasso.Dispatcher;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.PicassoExecutorService;
import com.squareup.picasso.RemoteViewsAction;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Stats;
import com.squareup.picasso.StatsSnapshot;
import com.squareup.picasso.Target;
import com.squareup.picasso.Utils;
import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

public class Picasso {
    static final Handler HANDLER = new Handler(Looper.getMainLooper()){

        public void handleMessage(Message object) {
            switch (object.what) {
                default: {
                    throw new AssertionError((Object)("Unknown handler message received: " + object.what));
                }
                case 8: {
                    object = (List)object.obj;
                    int n2 = object.size();
                    for (int i2 = 0; i2 < n2; ++i2) {
                        BitmapHunter bitmapHunter = (BitmapHunter)object.get(i2);
                        bitmapHunter.picasso.complete(bitmapHunter);
                    }
                    break;
                }
                case 3: {
                    object = (Action)object.obj;
                    object.picasso.cancelExistingRequest(object.getTarget());
                }
            }
        }
    };
    static final String TAG = "Picasso";
    static Picasso singleton = null;
    final Cache cache;
    private final CleanupThread cleanupThread;
    final Context context;
    final Dispatcher dispatcher;
    boolean indicatorsEnabled;
    private final Listener listener;
    volatile boolean loggingEnabled;
    final ReferenceQueue<Object> referenceQueue;
    private final RequestTransformer requestTransformer;
    boolean shutdown;
    final Stats stats;
    final Map<Object, Action> targetToAction;
    final Map<ImageView, DeferredRequestCreator> targetToDeferredRequestCreator;

    Picasso(Context context, Dispatcher dispatcher, Cache cache, Listener listener, RequestTransformer requestTransformer, Stats stats, boolean bl2, boolean bl3) {
        this.context = context;
        this.dispatcher = dispatcher;
        this.cache = cache;
        this.listener = listener;
        this.requestTransformer = requestTransformer;
        this.stats = stats;
        this.targetToAction = new WeakHashMap<Object, Action>();
        this.targetToDeferredRequestCreator = new WeakHashMap<ImageView, DeferredRequestCreator>();
        this.indicatorsEnabled = bl2;
        this.loggingEnabled = bl3;
        this.referenceQueue = new ReferenceQueue();
        this.cleanupThread = new CleanupThread(this.referenceQueue, HANDLER);
        this.cleanupThread.start();
    }

    private void cancelExistingRequest(Object object) {
        Utils.checkMain();
        Action action = this.targetToAction.remove(object);
        if (action != null) {
            action.cancel();
            this.dispatcher.dispatchCancel(action);
        }
        if (object instanceof ImageView) {
            object = (ImageView)object;
            if ((object = this.targetToDeferredRequestCreator.remove(object)) != null) {
                object.cancel();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void deliverAction(Bitmap bitmap, LoadedFrom loadedFrom, Action action, Exception exception) {
        if (action.isCancelled()) return;
        if (!action.willReplay()) {
            this.targetToAction.remove(action.getTarget());
        }
        if (bitmap != null) {
            if (loadedFrom == null) {
                throw new AssertionError((Object)"LoadedFrom cannot be null.");
            }
            action.complete(bitmap, loadedFrom);
            if (!this.loggingEnabled) return;
            {
                Utils.log("Main", "completed", action.request.logId(), "from " + (Object)((Object)loadedFrom));
                return;
            }
        }
        action.error(exception);
        if (!this.loggingEnabled) {
            return;
        }
        Utils.log("Main", "errored", action.request.logId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Picasso with(Context context) {
        if (singleton == null) {
            synchronized (Picasso.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }

    public boolean areIndicatorsEnabled() {
        return this.indicatorsEnabled;
    }

    public void cancelRequest(ImageView imageView) {
        this.cancelExistingRequest((Object)imageView);
    }

    public void cancelRequest(RemoteViews remoteViews, int n2) {
        this.cancelExistingRequest(new RemoteViewsAction.RemoteViewsTarget(remoteViews, n2));
    }

    public void cancelRequest(Target target) {
        this.cancelExistingRequest(target);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    void complete(BitmapHunter object) {
        int n2 = 0;
        Action action = object.getAction();
        List<Action> list = object.getActions();
        int n3 = list != null && !list.isEmpty() ? 1 : 0;
        if (action != null || n3 != 0) {
            n2 = 1;
        }
        if (n2 == 0) {
            return;
        }
        Uri uri = object.getData().uri;
        Exception exception = object.getException();
        Bitmap bitmap = object.getResult();
        object = object.getLoadedFrom();
        if (action != null) {
            this.deliverAction(bitmap, (LoadedFrom)((Object)object), action, exception);
        }
        if (n3 != 0) {
            n2 = list.size();
            for (n3 = 0; n3 < n2; ++n3) {
                this.deliverAction(bitmap, (LoadedFrom)((Object)object), list.get(n3), exception);
            }
        }
        if (this.listener == null) return;
        if (exception == null) return;
        this.listener.onImageLoadFailed(this, uri, exception);
    }

    void defer(ImageView imageView, DeferredRequestCreator deferredRequestCreator) {
        this.targetToDeferredRequestCreator.put(imageView, deferredRequestCreator);
    }

    void enqueueAndSubmit(Action action) {
        Object t2 = action.getTarget();
        if (t2 != null) {
            this.cancelExistingRequest(t2);
            this.targetToAction.put(t2, action);
        }
        this.submit(action);
    }

    public StatsSnapshot getSnapshot() {
        return this.stats.createSnapshot();
    }

    @Deprecated
    public boolean isDebugging() {
        if (this.areIndicatorsEnabled() && this.isLoggingEnabled()) {
            return true;
        }
        return false;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public RequestCreator load(int n2) {
        if (n2 == 0) {
            throw new IllegalArgumentException("Resource ID must not be zero.");
        }
        return new RequestCreator(this, null, n2);
    }

    public RequestCreator load(Uri uri) {
        return new RequestCreator(this, uri, 0);
    }

    public RequestCreator load(File file) {
        if (file == null) {
            return new RequestCreator(this, null, 0);
        }
        return this.load(Uri.fromFile((File)file));
    }

    public RequestCreator load(String string2) {
        if (string2 == null) {
            return new RequestCreator(this, null, 0);
        }
        if (string2.trim().length() == 0) {
            throw new IllegalArgumentException("Path must not be empty.");
        }
        return this.load(Uri.parse((String)string2));
    }

    Bitmap quickMemoryCacheCheck(String string2) {
        if ((string2 = this.cache.get(string2)) != null) {
            this.stats.dispatchCacheHit();
            return string2;
        }
        this.stats.dispatchCacheMiss();
        return string2;
    }

    @Deprecated
    public void setDebugging(boolean bl2) {
        this.setIndicatorsEnabled(bl2);
    }

    public void setIndicatorsEnabled(boolean bl2) {
        this.indicatorsEnabled = bl2;
    }

    public void setLoggingEnabled(boolean bl2) {
        this.loggingEnabled = bl2;
    }

    public void shutdown() {
        if (this == singleton) {
            throw new UnsupportedOperationException("Default singleton instance cannot be shutdown.");
        }
        if (this.shutdown) {
            return;
        }
        this.cache.clear();
        this.cleanupThread.shutdown();
        this.stats.shutdown();
        this.dispatcher.shutdown();
        Iterator<DeferredRequestCreator> iterator = this.targetToDeferredRequestCreator.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().cancel();
        }
        this.targetToDeferredRequestCreator.clear();
        this.shutdown = true;
    }

    void submit(Action action) {
        this.dispatcher.dispatchSubmit(action);
    }

    Request transformRequest(Request request) {
        Request request2 = this.requestTransformer.transformRequest(request);
        if (request2 == null) {
            throw new IllegalStateException("Request transformer " + this.requestTransformer.getClass().getCanonicalName() + " returned null for " + request);
        }
        return request2;
    }

    public static class Builder {
        private Cache cache;
        private final Context context;
        private Downloader downloader;
        private boolean indicatorsEnabled;
        private Listener listener;
        private boolean loggingEnabled;
        private ExecutorService service;
        private RequestTransformer transformer;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public Picasso build() {
            Context context = this.context;
            if (this.downloader == null) {
                this.downloader = Utils.createDefaultDownloader(context);
            }
            if (this.cache == null) {
                this.cache = new LruCache(context);
            }
            if (this.service == null) {
                this.service = new PicassoExecutorService();
            }
            if (this.transformer == null) {
                this.transformer = RequestTransformer.IDENTITY;
            }
            Stats stats = new Stats(this.cache);
            return new Picasso(context, new Dispatcher(context, this.service, Picasso.HANDLER, this.downloader, this.cache, stats), this.cache, this.listener, this.transformer, stats, this.indicatorsEnabled, this.loggingEnabled);
        }

        @Deprecated
        public Builder debugging(boolean bl2) {
            return this.indicatorsEnabled(bl2);
        }

        public Builder downloader(Downloader downloader) {
            if (downloader == null) {
                throw new IllegalArgumentException("Downloader must not be null.");
            }
            if (this.downloader != null) {
                throw new IllegalStateException("Downloader already set.");
            }
            this.downloader = downloader;
            return this;
        }

        public Builder executor(ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("Executor service must not be null.");
            }
            if (this.service != null) {
                throw new IllegalStateException("Executor service already set.");
            }
            this.service = executorService;
            return this;
        }

        public Builder indicatorsEnabled(boolean bl2) {
            this.indicatorsEnabled = bl2;
            return this;
        }

        public Builder listener(Listener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            }
            if (this.listener != null) {
                throw new IllegalStateException("Listener already set.");
            }
            this.listener = listener;
            return this;
        }

        public Builder loggingEnabled(boolean bl2) {
            this.loggingEnabled = bl2;
            return this;
        }

        public Builder memoryCache(Cache cache) {
            if (cache == null) {
                throw new IllegalArgumentException("Memory cache must not be null.");
            }
            if (this.cache != null) {
                throw new IllegalStateException("Memory cache already set.");
            }
            this.cache = cache;
            return this;
        }

        public Builder requestTransformer(RequestTransformer requestTransformer) {
            if (requestTransformer == null) {
                throw new IllegalArgumentException("Transformer must not be null.");
            }
            if (this.transformer != null) {
                throw new IllegalStateException("Transformer already set.");
            }
            this.transformer = requestTransformer;
            return this;
        }
    }

    private static class CleanupThread
    extends Thread {
        private final Handler handler;
        private final ReferenceQueue<?> referenceQueue;

        CleanupThread(ReferenceQueue<?> referenceQueue, Handler handler) {
            this.referenceQueue = referenceQueue;
            this.handler = handler;
            this.setDaemon(true);
            this.setName("Picasso-refQueue");
        }

        @Override
        public void run() {
            Process.setThreadPriority((int)10);
            try {
                do {
                    Action.RequestWeakReference requestWeakReference = (Action.RequestWeakReference)this.referenceQueue.remove();
                    this.handler.sendMessage(this.handler.obtainMessage(3, (Object)requestWeakReference.action));
                } while (true);
            }
            catch (InterruptedException var1_2) {
                return;
            }
            catch (Exception var1_3) {
                this.handler.post(new Runnable(){

                    @Override
                    public void run() {
                        throw new RuntimeException(var1_3);
                    }
                });
                return;
            }
        }

        void shutdown() {
            this.interrupt();
        }

    }

    public static interface Listener {
        public void onImageLoadFailed(Picasso var1, Uri var2, Exception var3);
    }

    public static enum LoadedFrom {
        MEMORY(-16711936),
        DISK(-256),
        NETWORK(-65536);
        
        final int debugColor;

        private LoadedFrom(int n3) {
            this.debugColor = n3;
        }
    }

    public static interface RequestTransformer {
        public static final RequestTransformer IDENTITY = new RequestTransformer(){

            @Override
            public Request transformRequest(Request request) {
                return request;
            }
        };

        public Request transformRequest(Request var1);

    }

}

