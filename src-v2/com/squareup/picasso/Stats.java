/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 */
package com.squareup.picasso;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.StatsSnapshot;
import com.squareup.picasso.Utils;

class Stats {
    private static final int BITMAP_DECODE_FINISHED = 2;
    private static final int BITMAP_TRANSFORMED_FINISHED = 3;
    private static final int CACHE_HIT = 0;
    private static final int CACHE_MISS = 1;
    private static final int DOWNLOAD_FINISHED = 4;
    private static final String STATS_THREAD_NAME = "Picasso-Stats";
    long averageDownloadSize;
    long averageOriginalBitmapSize;
    long averageTransformedBitmapSize;
    final Cache cache;
    long cacheHits;
    long cacheMisses;
    int downloadCount;
    final Handler handler;
    int originalBitmapCount;
    final HandlerThread statsThread;
    long totalDownloadSize;
    long totalOriginalBitmapSize;
    long totalTransformedBitmapSize;
    int transformedBitmapCount;

    Stats(Cache cache) {
        this.cache = cache;
        this.statsThread = new HandlerThread("Picasso-Stats", 10);
        this.statsThread.start();
        this.handler = new StatsHandler(this.statsThread.getLooper(), this);
    }

    private static long getAverage(int n2, long l2) {
        return l2 / (long)n2;
    }

    private void processBitmap(Bitmap bitmap, int n2) {
        int n3 = Utils.getBitmapBytes(bitmap);
        this.handler.sendMessage(this.handler.obtainMessage(n2, n3, 0));
    }

    StatsSnapshot createSnapshot() {
        return new StatsSnapshot(this.cache.maxSize(), this.cache.size(), this.cacheHits, this.cacheMisses, this.totalDownloadSize, this.totalOriginalBitmapSize, this.totalTransformedBitmapSize, this.averageDownloadSize, this.averageOriginalBitmapSize, this.averageTransformedBitmapSize, this.downloadCount, this.originalBitmapCount, this.transformedBitmapCount, System.currentTimeMillis());
    }

    void dispatchBitmapDecoded(Bitmap bitmap) {
        this.processBitmap(bitmap, 2);
    }

    void dispatchBitmapTransformed(Bitmap bitmap) {
        this.processBitmap(bitmap, 3);
    }

    void dispatchCacheHit() {
        this.handler.sendEmptyMessage(0);
    }

    void dispatchCacheMiss() {
        this.handler.sendEmptyMessage(1);
    }

    void dispatchDownloadFinished(long l2) {
        this.handler.sendMessage(this.handler.obtainMessage(4, (Object)l2));
    }

    void performBitmapDecoded(long l2) {
        ++this.originalBitmapCount;
        this.totalOriginalBitmapSize += l2;
        this.averageOriginalBitmapSize = Stats.getAverage(this.originalBitmapCount, this.totalOriginalBitmapSize);
    }

    void performBitmapTransformed(long l2) {
        ++this.transformedBitmapCount;
        this.totalTransformedBitmapSize += l2;
        this.averageTransformedBitmapSize = Stats.getAverage(this.originalBitmapCount, this.totalTransformedBitmapSize);
    }

    void performCacheHit() {
        ++this.cacheHits;
    }

    void performCacheMiss() {
        ++this.cacheMisses;
    }

    void performDownloadFinished(Long l2) {
        ++this.downloadCount;
        this.totalDownloadSize += l2.longValue();
        this.averageDownloadSize = Stats.getAverage(this.downloadCount, this.totalDownloadSize);
    }

    void shutdown() {
        this.statsThread.quit();
    }

    private static class StatsHandler
    extends Handler {
        private final Stats stats;

        public StatsHandler(Looper looper, Stats stats) {
            super(looper);
            this.stats = stats;
        }

        public void handleMessage(final Message message) {
            switch (message.what) {
                default: {
                    Picasso.HANDLER.post(new Runnable(){

                        @Override
                        public void run() {
                            throw new AssertionError((Object)("Unhandled stats message." + message.what));
                        }
                    });
                    return;
                }
                case 0: {
                    this.stats.performCacheHit();
                    return;
                }
                case 1: {
                    this.stats.performCacheMiss();
                    return;
                }
                case 2: {
                    this.stats.performBitmapDecoded(message.arg1);
                    return;
                }
                case 3: {
                    this.stats.performBitmapTransformed(message.arg1);
                    return;
                }
                case 4: 
            }
            this.stats.performDownloadFinished((Long)message.obj);
        }

    }

}

