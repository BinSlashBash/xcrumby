/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Matrix
 *  android.net.NetworkInfo
 *  android.net.Uri
 *  android.os.Handler
 *  android.provider.ContactsContract
 *  android.provider.ContactsContract$Contacts
 */
package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import com.squareup.picasso.Action;
import com.squareup.picasso.AssetBitmapHunter;
import com.squareup.picasso.Cache;
import com.squareup.picasso.ContactsPhotoBitmapHunter;
import com.squareup.picasso.ContentStreamBitmapHunter;
import com.squareup.picasso.Dispatcher;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.FileBitmapHunter;
import com.squareup.picasso.MediaStoreBitmapHunter;
import com.squareup.picasso.NetworkBitmapHunter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.ResourceBitmapHunter;
import com.squareup.picasso.Stats;
import com.squareup.picasso.StatsSnapshot;
import com.squareup.picasso.Transformation;
import com.squareup.picasso.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

abstract class BitmapHunter
implements Runnable {
    private static final Object DECODE_LOCK = new Object();
    private static final ThreadLocal<StringBuilder> NAME_BUILDER = new ThreadLocal<StringBuilder>(){

        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    };
    Action action;
    List<Action> actions;
    final Cache cache;
    final Request data;
    final Dispatcher dispatcher;
    Exception exception;
    int exifRotation;
    Future<?> future;
    final String key;
    Picasso.LoadedFrom loadedFrom;
    final Picasso picasso;
    Bitmap result;
    final boolean skipMemoryCache;
    final Stats stats;

    BitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        this.picasso = picasso;
        this.dispatcher = dispatcher;
        this.cache = cache;
        this.stats = stats;
        this.key = action.getKey();
        this.data = action.getRequest();
        this.skipMemoryCache = action.skipCache;
        this.action = action;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Bitmap applyCustomTransformations(List<Transformation> iterator, Bitmap object) {
        int n2 = 0;
        int n3 = iterator.size();
        do {
            Object object2 = object;
            if (n2 >= n3) return object2;
            final Transformation transformation = (Transformation)iterator.get(n2);
            object2 = transformation.transform((Bitmap)object);
            if (object2 == null) {
                object = new StringBuilder().append("Transformation ").append(transformation.key()).append(" returned null after ").append(n2).append(" previous transformation(s).\n\nTransformation list:\n");
                iterator = iterator.iterator();
                while (iterator.hasNext()) {
                    object.append(((Transformation)iterator.next()).key()).append('\n');
                }
                Picasso.HANDLER.post(new Runnable((StringBuilder)object){
                    final /* synthetic */ StringBuilder val$builder;

                    @Override
                    public void run() {
                        throw new NullPointerException(this.val$builder.toString());
                    }
                });
                return null;
            }
            if (object2 == object && object.isRecycled()) {
                Picasso.HANDLER.post(new Runnable(){

                    @Override
                    public void run() {
                        throw new IllegalStateException("Transformation " + transformation.key() + " returned input Bitmap but recycled it.");
                    }
                });
                return null;
            }
            if (object2 != object && !object.isRecycled()) {
                Picasso.HANDLER.post(new Runnable(){

                    @Override
                    public void run() {
                        throw new IllegalStateException("Transformation " + transformation.key() + " mutated input Bitmap but failed to recycle the original.");
                    }
                });
                return null;
            }
            object = object2;
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    static void calculateInSampleSize(int n2, int n3, int n4, int n5, BitmapFactory.Options options) {
        int n6 = 1;
        if ((n5 > n3 || n4 > n2) && (n6 = (int)Math.floor((float)n5 / (float)n3)) >= (n2 = (int)Math.floor((float)n4 / (float)n2))) {
            n6 = n2;
        }
        options.inSampleSize = n6;
        options.inJustDecodeBounds = false;
    }

    static void calculateInSampleSize(int n2, int n3, BitmapFactory.Options options) {
        BitmapHunter.calculateInSampleSize(n2, n3, options.outWidth, options.outHeight, options);
    }

    /*
     * Enabled aggressive block sorting
     */
    static BitmapFactory.Options createBitmapOptions(Request request) {
        boolean bl2 = request.hasSize();
        boolean bl3 = request.config != null;
        BitmapFactory.Options options = null;
        if (!bl2) {
            if (!bl3) return options;
        }
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inJustDecodeBounds = bl2;
        options = options2;
        if (!bl3) return options;
        options2.inPreferredConfig = request.config;
        return options2;
    }

    static BitmapHunter forRequest(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, Downloader downloader) {
        if (action.getRequest().resourceId != 0) {
            return new ResourceBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        }
        Uri uri = action.getRequest().uri;
        String string2 = uri.getScheme();
        if ("content".equals(string2)) {
            if (ContactsContract.Contacts.CONTENT_URI.getHost().equals(uri.getHost()) && !uri.getPathSegments().contains("photo")) {
                return new ContactsPhotoBitmapHunter(context, picasso, dispatcher, cache, stats, action);
            }
            if ("media".equals(uri.getAuthority())) {
                return new MediaStoreBitmapHunter(context, picasso, dispatcher, cache, stats, action);
            }
            return new ContentStreamBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        }
        if ("file".equals(string2)) {
            if (!uri.getPathSegments().isEmpty() && "android_asset".equals(uri.getPathSegments().get(0))) {
                return new AssetBitmapHunter(context, picasso, dispatcher, cache, stats, action);
            }
            return new FileBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        }
        if ("android.resource".equals(string2)) {
            return new ResourceBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        }
        return new NetworkBitmapHunter(picasso, dispatcher, cache, stats, action, downloader);
    }

    static boolean requiresInSampleSize(BitmapFactory.Options options) {
        if (options != null && options.inJustDecodeBounds) {
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static Bitmap transformResult(Request var0, Bitmap var1_1, int var2_2) {
        var14_3 = var1_1.getWidth();
        var15_4 = var1_1.getHeight();
        var16_5 = 0;
        var12_6 = 0;
        var17_7 = 0;
        var13_8 = 0;
        var7_9 = var14_3;
        var6_10 = var15_4;
        var20_11 = new Matrix();
        var11_12 = var16_5;
        var8_13 = var17_7;
        var9_14 = var7_9;
        var10_15 = var6_10;
        if (!var0.needsMatrixTransform()) ** GOTO lbl73
        var18_16 = var0.targetWidth;
        var19_17 = var0.targetHeight;
        var3_18 = var0.rotationDegrees;
        if (var3_18 != 0.0f) {
            if (var0.hasRotationPivot) {
                var20_11.setRotate(var3_18, var0.rotationPivotX, var0.rotationPivotY);
            } else {
                var20_11.setRotate(var3_18);
            }
        }
        if (!var0.centerCrop) ** GOTO lbl41
        var4_19 = (float)var18_16 / (float)var14_3;
        var5_21 = (float)var19_17 / (float)var15_4;
        if (var4_19 > var5_21) {
            var3_18 = var4_19;
            var6_10 = (int)Math.ceil((float)var15_4 * (var5_21 / var4_19));
            var8_13 = (var15_4 - var6_10) / 2;
            var9_14 = var7_9;
            var7_9 = var12_6;
        } else {
            var3_18 = var5_21;
            var9_14 = (int)Math.ceil((float)var14_3 * (var4_19 / var5_21));
            var7_9 = (var14_3 - var9_14) / 2;
            var8_13 = var13_8;
        }
        var20_11.preScale(var3_18, var3_18);
        var10_15 = var6_10;
        var11_12 = var7_9;
        ** GOTO lbl73
lbl41: // 1 sources:
        if (!var0.centerInside) ** GOTO lbl52
        var3_18 = (float)var18_16 / (float)var14_3;
        var4_20 = (float)var19_17 / (float)var15_4;
        if (var3_18 >= var4_20) {
            var3_18 = var4_20;
        }
        var20_11.preScale(var3_18, var3_18);
        var11_12 = var16_5;
        var8_13 = var17_7;
        var9_14 = var7_9;
        var10_15 = var6_10;
        ** GOTO lbl73
lbl52: // 1 sources:
        var11_12 = var16_5;
        var8_13 = var17_7;
        var9_14 = var7_9;
        var10_15 = var6_10;
        if (var18_16 == 0) ** GOTO lbl73
        var11_12 = var16_5;
        var8_13 = var17_7;
        var9_14 = var7_9;
        var10_15 = var6_10;
        if (var19_17 == 0) ** GOTO lbl73
        if (var18_16 != var14_3) ** GOTO lbl-1000
        var11_12 = var16_5;
        var8_13 = var17_7;
        var9_14 = var7_9;
        var10_15 = var6_10;
        if (var19_17 != var15_4) lbl-1000: // 2 sources:
        {
            var20_11.preScale((float)var18_16 / (float)var14_3, (float)var19_17 / (float)var15_4);
            var11_12 = var16_5;
            var8_13 = var17_7;
            var9_14 = var7_9;
            var10_15 = var6_10;
        }
lbl73: // 8 sources:
        if (var2_2 != 0) {
            var20_11.preRotate((float)var2_2);
        }
        var20_11 = Bitmap.createBitmap((Bitmap)var1_1, (int)var11_12, (int)var8_13, (int)var9_14, (int)var10_15, (Matrix)var20_11, (boolean)true);
        var0 = var1_1;
        if (var20_11 == var1_1) return var0;
        var1_1.recycle();
        return var20_11;
    }

    static void updateThreadName(Request object) {
        object = object.getName();
        StringBuilder stringBuilder = NAME_BUILDER.get();
        stringBuilder.ensureCapacity("Picasso-".length() + object.length());
        stringBuilder.replace("Picasso-".length(), stringBuilder.length(), (String)object);
        Thread.currentThread().setName(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    void attach(Action action) {
        boolean bl2 = this.picasso.loggingEnabled;
        Request request = action.request;
        if (this.action == null) {
            this.action = action;
            if (!bl2) return;
            {
                if (this.actions != null && !this.actions.isEmpty()) {
                    Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
                    return;
                }
                Utils.log("Hunter", "joined", request.logId(), "to empty hunter");
                return;
            }
        } else {
            if (this.actions == null) {
                this.actions = new ArrayList<Action>(3);
            }
            this.actions.add(action);
            if (!bl2) return;
            {
                Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
                return;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean cancel() {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (this.action != null) return bl3;
        if (this.actions != null) {
            bl3 = bl2;
            if (!this.actions.isEmpty()) return bl3;
        }
        bl3 = bl2;
        if (this.future == null) return bl3;
        bl3 = bl2;
        if (!this.future.cancel(false)) return bl3;
        return true;
    }

    abstract Bitmap decode(Request var1) throws IOException;

    /*
     * Enabled aggressive block sorting
     */
    void detach(Action action) {
        if (this.action == action) {
            this.action = null;
        } else if (this.actions != null) {
            this.actions.remove(action);
        }
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "removed", action.request.logId(), Utils.getLogIdsForHunter(this, "from "));
        }
    }

    Action getAction() {
        return this.action;
    }

    List<Action> getActions() {
        return this.actions;
    }

    Request getData() {
        return this.data;
    }

    Exception getException() {
        return this.exception;
    }

    String getKey() {
        return this.key;
    }

    Picasso.LoadedFrom getLoadedFrom() {
        return this.loadedFrom;
    }

    Picasso getPicasso() {
        return this.picasso;
    }

    Bitmap getResult() {
        return this.result;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    Bitmap hunt() throws IOException {
        if (!this.skipMemoryCache && (var1_1 = this.cache.get(this.key)) != null) {
            this.stats.dispatchCacheHit();
            this.loadedFrom = Picasso.LoadedFrom.MEMORY;
            if (this.picasso.loggingEnabled == false) return var1_1;
            Utils.log("Hunter", "decoded", this.data.logId(), "from cache");
            return var1_1;
        }
        var1_1 = var2_2 = this.decode(this.data);
        if (var2_2 == null) return var1_1;
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "decoded", this.data.logId());
        }
        this.stats.dispatchBitmapDecoded(var2_2);
        if (!this.data.needsTransformation()) {
            var1_1 = var2_2;
            if (this.exifRotation == 0) return var1_1;
        }
        var3_3 = BitmapHunter.DECODE_LOCK;
        // MONITORENTER : var3_3
        if (this.data.needsMatrixTransform()) ** GOTO lbl-1000
        var1_1 = var2_2;
        if (this.exifRotation != 0) lbl-1000: // 2 sources:
        {
            var1_1 = var2_2 = BitmapHunter.transformResult(this.data, var2_2, this.exifRotation);
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "transformed", this.data.logId());
                var1_1 = var2_2;
            }
        }
        var2_2 = var1_1;
        if (this.data.hasCustomTransformations()) {
            var2_2 = var1_1 = BitmapHunter.applyCustomTransformations(this.data.transformations, var1_1);
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "transformed", this.data.logId(), "from custom transformations");
                var2_2 = var1_1;
            }
        }
        // MONITOREXIT : var3_3
        var1_1 = var2_2;
        if (var2_2 == null) return var1_1;
        this.stats.dispatchBitmapTransformed(var2_2);
        return var2_2;
    }

    boolean isCancelled() {
        if (this.future != null && this.future.isCancelled()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        try {
            BitmapHunter.updateThreadName(this.data);
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "executing", Utils.getLogIdsForHunter(this));
            }
            this.result = this.hunt();
            if (this.result == null) {
                this.dispatcher.dispatchFailed(this);
                do {
                    return;
                    break;
                } while (true);
            }
            this.dispatcher.dispatchComplete(this);
            return;
        }
        catch (Downloader.ResponseException var1_1) {
            this.exception = var1_1;
            this.dispatcher.dispatchFailed(this);
            return;
        }
        catch (IOException var1_2) {
            this.exception = var1_2;
            this.dispatcher.dispatchRetry(this);
            return;
        }
        catch (OutOfMemoryError var1_3) {
            StringWriter stringWriter = new StringWriter();
            this.stats.createSnapshot().dump(new PrintWriter(stringWriter));
            this.exception = new RuntimeException(stringWriter.toString(), var1_3);
            this.dispatcher.dispatchFailed(this);
            return;
        }
        catch (Exception var1_4) {
            this.exception = var1_4;
            this.dispatcher.dispatchFailed(this);
            return;
        }
        finally {
            Thread.currentThread().setName("Picasso-Idle");
        }
    }

    protected void setExifRotation(int n2) {
        this.exifRotation = n2;
    }

    boolean shouldRetry(boolean bl2, NetworkInfo networkInfo) {
        return false;
    }

    boolean shouldSkipMemoryCache() {
        return this.skipMemoryCache;
    }

    boolean supportsReplay() {
        return false;
    }

}

