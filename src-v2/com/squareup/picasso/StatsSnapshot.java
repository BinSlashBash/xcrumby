/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.squareup.picasso;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class StatsSnapshot {
    public final long averageDownloadSize;
    public final long averageOriginalBitmapSize;
    public final long averageTransformedBitmapSize;
    public final long cacheHits;
    public final long cacheMisses;
    public final int downloadCount;
    public final int maxSize;
    public final int originalBitmapCount;
    public final int size;
    public final long timeStamp;
    public final long totalDownloadSize;
    public final long totalOriginalBitmapSize;
    public final long totalTransformedBitmapSize;
    public final int transformedBitmapCount;

    public StatsSnapshot(int n2, int n3, long l2, long l3, long l4, long l5, long l6, long l7, long l8, long l9, int n4, int n5, int n6, long l10) {
        this.maxSize = n2;
        this.size = n3;
        this.cacheHits = l2;
        this.cacheMisses = l3;
        this.totalDownloadSize = l4;
        this.totalOriginalBitmapSize = l5;
        this.totalTransformedBitmapSize = l6;
        this.averageDownloadSize = l7;
        this.averageOriginalBitmapSize = l8;
        this.averageTransformedBitmapSize = l9;
        this.downloadCount = n4;
        this.originalBitmapCount = n5;
        this.transformedBitmapCount = n6;
        this.timeStamp = l10;
    }

    public void dump() {
        StringWriter stringWriter = new StringWriter();
        this.dump(new PrintWriter(stringWriter));
        Log.i((String)"Picasso", (String)stringWriter.toString());
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("===============BEGIN PICASSO STATS ===============");
        printWriter.println("Memory Cache Stats");
        printWriter.print("  Max Cache Size: ");
        printWriter.println(this.maxSize);
        printWriter.print("  Cache Size: ");
        printWriter.println(this.size);
        printWriter.print("  Cache % Full: ");
        printWriter.println((int)Math.ceil((float)this.size / (float)this.maxSize * 100.0f));
        printWriter.print("  Cache Hits: ");
        printWriter.println(this.cacheHits);
        printWriter.print("  Cache Misses: ");
        printWriter.println(this.cacheMisses);
        printWriter.println("Network Stats");
        printWriter.print("  Download Count: ");
        printWriter.println(this.downloadCount);
        printWriter.print("  Total Download Size: ");
        printWriter.println(this.totalDownloadSize);
        printWriter.print("  Average Download Size: ");
        printWriter.println(this.averageDownloadSize);
        printWriter.println("Bitmap Stats");
        printWriter.print("  Total Bitmaps Decoded: ");
        printWriter.println(this.originalBitmapCount);
        printWriter.print("  Total Bitmap Size: ");
        printWriter.println(this.totalOriginalBitmapSize);
        printWriter.print("  Total Transformed Bitmaps: ");
        printWriter.println(this.transformedBitmapCount);
        printWriter.print("  Total Transformed Bitmap Size: ");
        printWriter.println(this.totalTransformedBitmapSize);
        printWriter.print("  Average Bitmap Size: ");
        printWriter.println(this.averageOriginalBitmapSize);
        printWriter.print("  Average Transformed Bitmap Size: ");
        printWriter.println(this.averageTransformedBitmapSize);
        printWriter.println("===============END PICASSO STATS ===============");
        printWriter.flush();
    }

    public String toString() {
        return "StatsSnapshot{maxSize=" + this.maxSize + ", size=" + this.size + ", cacheHits=" + this.cacheHits + ", cacheMisses=" + this.cacheMisses + ", downloadCount=" + this.downloadCount + ", totalDownloadSize=" + this.totalDownloadSize + ", averageDownloadSize=" + this.averageDownloadSize + ", totalOriginalBitmapSize=" + this.totalOriginalBitmapSize + ", totalTransformedBitmapSize=" + this.totalTransformedBitmapSize + ", averageOriginalBitmapSize=" + this.averageOriginalBitmapSize + ", averageTransformedBitmapSize=" + this.averageTransformedBitmapSize + ", originalBitmapCount=" + this.originalBitmapCount + ", transformedBitmapCount=" + this.transformedBitmapCount + ", timeStamp=" + this.timeStamp + '}';
    }
}

