/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.graphics.Bitmap
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 */
package com.squareup.picasso;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.squareup.picasso.Action;
import com.squareup.picasso.BitmapHunter;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoExecutorService;
import com.squareup.picasso.Request;
import com.squareup.picasso.Stats;
import com.squareup.picasso.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class Dispatcher {
    static final int AIRPLANE_MODE_CHANGE = 10;
    private static final int AIRPLANE_MODE_OFF = 0;
    private static final int AIRPLANE_MODE_ON = 1;
    private static final int BATCH_DELAY = 200;
    private static final String DISPATCHER_THREAD_NAME = "Dispatcher";
    static final int HUNTER_BATCH_COMPLETE = 8;
    static final int HUNTER_COMPLETE = 4;
    static final int HUNTER_DECODE_FAILED = 6;
    static final int HUNTER_DELAY_NEXT_BATCH = 7;
    static final int HUNTER_RETRY = 5;
    static final int NETWORK_STATE_CHANGE = 9;
    static final int REQUEST_CANCEL = 2;
    static final int REQUEST_GCED = 3;
    static final int REQUEST_SUBMIT = 1;
    private static final int RETRY_DELAY = 500;
    boolean airplaneMode;
    final List<BitmapHunter> batch;
    final Cache cache;
    final Context context;
    final DispatcherThread dispatcherThread = new DispatcherThread();
    final Downloader downloader;
    final Map<Object, Action> failedActions;
    final Handler handler;
    final Map<String, BitmapHunter> hunterMap;
    final Handler mainThreadHandler;
    final NetworkBroadcastReceiver receiver;
    final boolean scansNetworkChanges;
    final ExecutorService service;
    final Stats stats;

    Dispatcher(Context context, ExecutorService executorService, Handler handler, Downloader downloader, Cache cache, Stats stats) {
        this.dispatcherThread.start();
        this.context = context;
        this.service = executorService;
        this.hunterMap = new LinkedHashMap<String, BitmapHunter>();
        this.failedActions = new WeakHashMap<Object, Action>();
        this.handler = new DispatcherHandler(this.dispatcherThread.getLooper(), this);
        this.downloader = downloader;
        this.mainThreadHandler = handler;
        this.cache = cache;
        this.stats = stats;
        this.batch = new ArrayList<BitmapHunter>(4);
        this.airplaneMode = Utils.isAirplaneModeOn(this.context);
        this.scansNetworkChanges = Utils.hasPermission(context, "android.permission.ACCESS_NETWORK_STATE");
        this.receiver = new NetworkBroadcastReceiver(this);
        this.receiver.register();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void batch(BitmapHunter bitmapHunter) {
        if (bitmapHunter.isCancelled()) {
            return;
        }
        this.batch.add(bitmapHunter);
        if (this.handler.hasMessages(7)) return;
        this.handler.sendEmptyMessageDelayed(7, 200);
    }

    private void flushFailedActions() {
        if (!this.failedActions.isEmpty()) {
            Iterator<Action> iterator = this.failedActions.values().iterator();
            while (iterator.hasNext()) {
                Action action = iterator.next();
                iterator.remove();
                if (action.getPicasso().loggingEnabled) {
                    Utils.log("Dispatcher", "replaying", action.getRequest().logId());
                }
                this.performSubmit(action);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void logBatch(List<BitmapHunter> iterator) {
        if (iterator == null || iterator.isEmpty() || !((BitmapHunter)iterator.get((int)0)).getPicasso().loggingEnabled) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        iterator = iterator.iterator();
        do {
            if (!iterator.hasNext()) {
                Utils.log("Dispatcher", "delivered", stringBuilder.toString());
                return;
            }
            BitmapHunter bitmapHunter = (BitmapHunter)iterator.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(Utils.getLogIdsForHunter(bitmapHunter));
        } while (true);
    }

    private void markForReplay(Action action) {
        Object t2 = action.getTarget();
        if (t2 != null) {
            action.willReplay = true;
            this.failedActions.put(t2, action);
        }
    }

    private void markForReplay(BitmapHunter object) {
        Action action = object.getAction();
        if (action != null) {
            this.markForReplay(action);
        }
        if ((object = object.getActions()) != null) {
            int n2 = object.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                this.markForReplay((Action)object.get(i2));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void dispatchAirplaneModeChange(boolean bl2) {
        Handler handler = this.handler;
        Handler handler2 = this.handler;
        int n2 = bl2 ? 1 : 0;
        handler.sendMessage(handler2.obtainMessage(10, n2, 0));
    }

    void dispatchCancel(Action action) {
        this.handler.sendMessage(this.handler.obtainMessage(2, (Object)action));
    }

    void dispatchComplete(BitmapHunter bitmapHunter) {
        this.handler.sendMessage(this.handler.obtainMessage(4, (Object)bitmapHunter));
    }

    void dispatchFailed(BitmapHunter bitmapHunter) {
        this.handler.sendMessage(this.handler.obtainMessage(6, (Object)bitmapHunter));
    }

    void dispatchNetworkStateChange(NetworkInfo networkInfo) {
        this.handler.sendMessage(this.handler.obtainMessage(9, (Object)networkInfo));
    }

    void dispatchRetry(BitmapHunter bitmapHunter) {
        this.handler.sendMessageDelayed(this.handler.obtainMessage(5, (Object)bitmapHunter), 500);
    }

    void dispatchSubmit(Action action) {
        this.handler.sendMessage(this.handler.obtainMessage(1, (Object)action));
    }

    void performAirplaneModeChange(boolean bl2) {
        this.airplaneMode = bl2;
    }

    void performBatchComplete() {
        ArrayList<BitmapHunter> arrayList = new ArrayList<BitmapHunter>(this.batch);
        this.batch.clear();
        this.mainThreadHandler.sendMessage(this.mainThreadHandler.obtainMessage(8, arrayList));
        this.logBatch(arrayList);
    }

    void performCancel(Action action) {
        String string2 = action.getKey();
        BitmapHunter bitmapHunter = this.hunterMap.get(string2);
        if (bitmapHunter != null) {
            bitmapHunter.detach(action);
            if (bitmapHunter.cancel()) {
                this.hunterMap.remove(string2);
                if (action.getPicasso().loggingEnabled) {
                    Utils.log("Dispatcher", "canceled", action.getRequest().logId());
                }
            }
        }
        if ((action = this.failedActions.remove(action.getTarget())) != null && action.getPicasso().loggingEnabled) {
            Utils.log("Dispatcher", "canceled", action.getRequest().logId(), "from replaying");
        }
    }

    void performComplete(BitmapHunter bitmapHunter) {
        if (!bitmapHunter.shouldSkipMemoryCache()) {
            this.cache.set(bitmapHunter.getKey(), bitmapHunter.getResult());
        }
        this.hunterMap.remove(bitmapHunter.getKey());
        this.batch(bitmapHunter);
        if (bitmapHunter.getPicasso().loggingEnabled) {
            Utils.log("Dispatcher", "batched", Utils.getLogIdsForHunter(bitmapHunter), "for completion");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void performError(BitmapHunter bitmapHunter, boolean bl2) {
        if (bitmapHunter.getPicasso().loggingEnabled) {
            String string2 = Utils.getLogIdsForHunter(bitmapHunter);
            StringBuilder stringBuilder = new StringBuilder().append("for error");
            String string3 = bl2 ? " (will replay)" : "";
            Utils.log("Dispatcher", "batched", string2, stringBuilder.append(string3).toString());
        }
        this.hunterMap.remove(bitmapHunter.getKey());
        this.batch(bitmapHunter);
    }

    void performNetworkStateChange(NetworkInfo networkInfo) {
        if (this.service instanceof PicassoExecutorService) {
            ((PicassoExecutorService)this.service).adjustThreadCount(networkInfo);
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            this.flushFailedActions();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void performRetry(BitmapHunter bitmapHunter) {
        if (bitmapHunter.isCancelled()) return;
        if (this.service.isShutdown()) {
            this.performError(bitmapHunter, false);
            return;
        }
        NetworkInfo networkInfo = null;
        if (this.scansNetworkChanges) {
            networkInfo = ((ConnectivityManager)Utils.getService(this.context, "connectivity")).getActiveNetworkInfo();
        }
        boolean bl2 = networkInfo != null && networkInfo.isConnected();
        boolean bl3 = bitmapHunter.shouldRetry(this.airplaneMode, networkInfo);
        boolean bl4 = bitmapHunter.supportsReplay();
        if (!bl3) {
            bl3 = this.scansNetworkChanges && bl4;
            this.performError(bitmapHunter, bl3);
            if (!bl3) return;
            {
                this.markForReplay(bitmapHunter);
                return;
            }
        }
        if (!this.scansNetworkChanges || bl2) {
            if (bitmapHunter.getPicasso().loggingEnabled) {
                Utils.log("Dispatcher", "retrying", Utils.getLogIdsForHunter(bitmapHunter));
            }
            bitmapHunter.future = this.service.submit(bitmapHunter);
            return;
        }
        this.performError(bitmapHunter, bl4);
        if (!bl4) {
            return;
        }
        this.markForReplay(bitmapHunter);
    }

    /*
     * Enabled aggressive block sorting
     */
    void performSubmit(Action action) {
        BitmapHunter bitmapHunter = this.hunterMap.get(action.getKey());
        if (bitmapHunter != null) {
            bitmapHunter.attach(action);
            return;
        } else if (this.service.isShutdown()) {
            if (!action.getPicasso().loggingEnabled) return;
            {
                Utils.log("Dispatcher", "ignored", action.request.logId(), "because shut down");
                return;
            }
        } else {
            bitmapHunter = BitmapHunter.forRequest(this.context, action.getPicasso(), this, this.cache, this.stats, action, this.downloader);
            bitmapHunter.future = this.service.submit(bitmapHunter);
            this.hunterMap.put(action.getKey(), bitmapHunter);
            this.failedActions.remove(action.getTarget());
            if (!action.getPicasso().loggingEnabled) return;
            {
                Utils.log("Dispatcher", "enqueued", action.request.logId());
                return;
            }
        }
    }

    void shutdown() {
        this.service.shutdown();
        this.dispatcherThread.quit();
        this.receiver.unregister();
    }

    private static class DispatcherHandler
    extends Handler {
        private final Dispatcher dispatcher;

        public DispatcherHandler(Looper looper, Dispatcher dispatcher) {
            super(looper);
            this.dispatcher = dispatcher;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void handleMessage(Message object) {
            boolean bl2 = true;
            switch (object.what) {
                default: {
                    Picasso.HANDLER.post(new Runnable((Message)object){
                        final /* synthetic */ Message val$msg;

                        @Override
                        public void run() {
                            throw new AssertionError((Object)("Unknown handler message received: " + this.val$msg.what));
                        }
                    });
                    return;
                }
                case 1: {
                    object = (Action)object.obj;
                    this.dispatcher.performSubmit((Action)object);
                    return;
                }
                case 2: {
                    object = (Action)object.obj;
                    this.dispatcher.performCancel((Action)object);
                    return;
                }
                case 4: {
                    object = (BitmapHunter)object.obj;
                    this.dispatcher.performComplete((BitmapHunter)object);
                    return;
                }
                case 5: {
                    object = (BitmapHunter)object.obj;
                    this.dispatcher.performRetry((BitmapHunter)object);
                    return;
                }
                case 6: {
                    object = (BitmapHunter)object.obj;
                    this.dispatcher.performError((BitmapHunter)object, false);
                    return;
                }
                case 7: {
                    this.dispatcher.performBatchComplete();
                    return;
                }
                case 9: {
                    object = (NetworkInfo)object.obj;
                    this.dispatcher.performNetworkStateChange((NetworkInfo)object);
                    return;
                }
                case 10: 
            }
            Dispatcher dispatcher = this.dispatcher;
            if (object.arg1 != 1) {
                bl2 = false;
            }
            dispatcher.performAirplaneModeChange(bl2);
        }

    }

    static class DispatcherThread
    extends HandlerThread {
        DispatcherThread() {
            super("Picasso-Dispatcher", 10);
        }
    }

    static class NetworkBroadcastReceiver
    extends BroadcastReceiver {
        static final String EXTRA_AIRPLANE_STATE = "state";
        private final Dispatcher dispatcher;

        NetworkBroadcastReceiver(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context context, Intent intent) {
            if (intent == null) return;
            String string2 = intent.getAction();
            if ("android.intent.action.AIRPLANE_MODE".equals(string2)) {
                if (!intent.hasExtra("state")) return;
                {
                    this.dispatcher.dispatchAirplaneModeChange(intent.getBooleanExtra("state", false));
                    return;
                }
            }
            if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(string2)) {
                return;
            }
            context = (ConnectivityManager)Utils.getService(context, "connectivity");
            this.dispatcher.dispatchNetworkStateChange(context.getActiveNetworkInfo());
        }

        void register() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            if (this.dispatcher.scansNetworkChanges) {
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
            this.dispatcher.context.registerReceiver((BroadcastReceiver)this, intentFilter);
        }

        void unregister() {
            this.dispatcher.context.unregisterReceiver((BroadcastReceiver)this);
        }
    }

}

