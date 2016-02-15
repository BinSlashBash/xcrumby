/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.ActivityManager
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Looper
 *  android.os.Process
 *  android.os.StatFs
 *  android.provider.Settings
 *  android.provider.Settings$System
 *  android.util.Log
 */
package com.squareup.picasso;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.os.StatFs;
import android.provider.Settings;
import android.util.Log;
import com.squareup.picasso.Action;
import com.squareup.picasso.BitmapHunter;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Request;
import com.squareup.picasso.Transformation;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ThreadFactory;

final class Utils {
    static final int DEFAULT_CONNECT_TIMEOUT = 15000;
    static final int DEFAULT_READ_TIMEOUT = 20000;
    private static final int KEY_PADDING = 50;
    static final StringBuilder MAIN_THREAD_KEY_BUILDER = new StringBuilder();
    private static final int MAX_DISK_CACHE_SIZE = 52428800;
    private static final int MIN_DISK_CACHE_SIZE = 5242880;
    static final String OWNER_DISPATCHER = "Dispatcher";
    static final String OWNER_HUNTER = "Hunter";
    static final String OWNER_MAIN = "Main";
    private static final String PICASSO_CACHE = "picasso-cache";
    static final String THREAD_IDLE_NAME = "Picasso-Idle";
    static final String THREAD_PREFIX = "Picasso-";
    static final String VERB_BATCHED = "batched";
    static final String VERB_CANCELED = "canceled";
    static final String VERB_CHANGED = "changed";
    static final String VERB_COMPLETED = "completed";
    static final String VERB_CREATED = "created";
    static final String VERB_DECODED = "decoded";
    static final String VERB_DELIVERED = "delivered";
    static final String VERB_ENQUEUED = "enqueued";
    static final String VERB_ERRORED = "errored";
    static final String VERB_EXECUTING = "executing";
    static final String VERB_IGNORED = "ignored";
    static final String VERB_JOINED = "joined";
    static final String VERB_REMOVED = "removed";
    static final String VERB_REPLAYING = "replaying";
    static final String VERB_RETRYING = "retrying";
    static final String VERB_TRANSFORMED = "transformed";
    private static final String WEBP_FILE_HEADER_RIFF = "RIFF";
    private static final int WEBP_FILE_HEADER_SIZE = 12;
    private static final String WEBP_FILE_HEADER_WEBP = "WEBP";

    private Utils() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static long calculateDiskCacheSize(File file) {
        long l2 = 0x500000;
        try {
            long l3;
            file = new StatFs(file.getAbsolutePath());
            l2 = l3 = (long)file.getBlockCount() * (long)file.getBlockSize() / 50;
        }
        catch (IllegalArgumentException var0_1) {
            return Math.max(Math.min(l2, 52428800), 0x500000);
        }
        do {
            return Math.max(Math.min(l2, 52428800), 0x500000);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    static int calculateMemoryCacheSize(Context context) {
        int n2;
        ActivityManager activityManager = (ActivityManager)Utils.getService(context, "activity");
        boolean bl2 = (context.getApplicationInfo().flags & 1048576) != 0;
        int n3 = n2 = activityManager.getMemoryClass();
        if (bl2) {
            n3 = n2;
            if (Build.VERSION.SDK_INT >= 11) {
                n3 = ActivityManagerHoneycomb.getLargeMemoryClass(activityManager);
            }
        }
        return 1048576 * n3 / 7;
    }

    static void checkMain() {
        if (!Utils.isMain()) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
    }

    static void checkNotMain() {
        if (Utils.isMain()) {
            throw new IllegalStateException("Method call should not happen from the main thread.");
        }
    }

    static void closeQuietly(InputStream inputStream) {
        if (inputStream == null) {
            return;
        }
        try {
            inputStream.close();
            return;
        }
        catch (IOException var0_1) {
            return;
        }
    }

    static File createDefaultCacheDir(Context object) {
        if (!(object = new File(object.getApplicationContext().getCacheDir(), "picasso-cache")).exists()) {
            object.mkdirs();
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Downloader createDefaultDownloader(Context context) {
        boolean bl2;
        boolean bl3;
        bl2 = false;
        try {
            Class.forName("com.squareup.okhttp.OkUrlFactory");
            bl2 = true;
        }
        catch (ClassNotFoundException var3_4) {}
        bl3 = false;
        try {
            Class.forName("com.squareup.okhttp.OkHttpClient");
            bl3 = true;
        }
        catch (ClassNotFoundException var3_3) {}
        if (bl3 == bl2) return OkHttpLoaderCreator.create(context);
        throw new RuntimeException("Picasso detected an unsupported OkHttp on the classpath.\nTo use OkHttp with this version of Picasso, you'll need:\n1. com.squareup.okhttp:okhttp:1.6.0 (or newer)\n2. com.squareup.okhttp:okhttp-urlconnection:1.6.0 (or newer)\nNote that OkHttp 2.0.0+ is supported!");
    }

    static String createKey(Request object) {
        object = Utils.createKey((Request)object, MAIN_THREAD_KEY_BUILDER);
        MAIN_THREAD_KEY_BUILDER.setLength(0);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    static String createKey(Request request, StringBuilder stringBuilder) {
        if (request.uri != null) {
            String string2 = request.uri.toString();
            stringBuilder.ensureCapacity(string2.length() + 50);
            stringBuilder.append(string2);
        } else {
            stringBuilder.ensureCapacity(50);
            stringBuilder.append(request.resourceId);
        }
        stringBuilder.append('\n');
        if (request.rotationDegrees != 0.0f) {
            stringBuilder.append("rotation:").append(request.rotationDegrees);
            if (request.hasRotationPivot) {
                stringBuilder.append('@').append(request.rotationPivotX).append('x').append(request.rotationPivotY);
            }
            stringBuilder.append('\n');
        }
        if (request.targetWidth != 0) {
            stringBuilder.append("resize:").append(request.targetWidth).append('x').append(request.targetHeight);
            stringBuilder.append('\n');
        }
        if (request.centerCrop) {
            stringBuilder.append("centerCrop\n");
        } else if (request.centerInside) {
            stringBuilder.append("centerInside\n");
        }
        if (request.transformations != null) {
            int n2 = request.transformations.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                stringBuilder.append(request.transformations.get(i2).key());
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    static int getBitmapBytes(Bitmap bitmap) {
        int n2 = Build.VERSION.SDK_INT >= 12 ? BitmapHoneycombMR1.getByteCount(bitmap) : bitmap.getRowBytes() * bitmap.getHeight();
        if (n2 < 0) {
            throw new IllegalStateException("Negative size: " + (Object)bitmap);
        }
        return n2;
    }

    static String getLogIdsForHunter(BitmapHunter bitmapHunter) {
        return Utils.getLogIdsForHunter(bitmapHunter, "");
    }

    static String getLogIdsForHunter(BitmapHunter object, String charSequence) {
        charSequence = new StringBuilder((String)charSequence);
        Action action = object.getAction();
        if (action != null) {
            charSequence.append(action.request.logId());
        }
        if ((object = object.getActions()) != null) {
            int n2 = object.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                if (i2 > 0 || action != null) {
                    charSequence.append(", ");
                }
                charSequence.append(((Action)object.get((int)i2)).request.logId());
            }
        }
        return charSequence.toString();
    }

    static int getResourceId(Resources resources, Request object) throws FileNotFoundException {
        if (object.resourceId != 0 || object.uri == null) {
            return object.resourceId;
        }
        String string2 = object.uri.getAuthority();
        if (string2 == null) {
            throw new FileNotFoundException("No package provided: " + (Object)object.uri);
        }
        List list = object.uri.getPathSegments();
        if (list == null || list.isEmpty()) {
            throw new FileNotFoundException("No path segments: " + (Object)object.uri);
        }
        if (list.size() == 1) {
            try {
                int n2 = Integer.parseInt((String)list.get(0));
                return n2;
            }
            catch (NumberFormatException var0_1) {
                throw new FileNotFoundException("Last path segment is not a resource ID: " + (Object)object.uri);
            }
        }
        if (list.size() == 2) {
            object = (String)list.get(0);
            return resources.getIdentifier((String)list.get(1), (String)object, string2);
        }
        throw new FileNotFoundException("More than two path segments: " + (Object)object.uri);
    }

    static Resources getResources(Context context, Request request) throws FileNotFoundException {
        if (request.resourceId != 0 || request.uri == null) {
            return context.getResources();
        }
        String string2 = request.uri.getAuthority();
        if (string2 == null) {
            throw new FileNotFoundException("No package provided: " + (Object)request.uri);
        }
        try {
            context = context.getPackageManager().getResourcesForApplication(string2);
            return context;
        }
        catch (PackageManager.NameNotFoundException var0_1) {
            throw new FileNotFoundException("Unable to obtain resources for package: " + (Object)request.uri);
        }
    }

    static <T> T getService(Context context, String string2) {
        return (T)context.getSystemService(string2);
    }

    static boolean hasPermission(Context context, String string2) {
        if (context.checkCallingOrSelfPermission(string2) == 0) {
            return true;
        }
        return false;
    }

    static boolean isAirplaneModeOn(Context context) {
        boolean bl2 = false;
        if (Settings.System.getInt((ContentResolver)context.getContentResolver(), (String)"airplane_mode_on", (int)0) != 0) {
            bl2 = true;
        }
        return bl2;
    }

    static boolean isMain() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean isWebPFile(InputStream inputStream) throws IOException {
        byte[] arrby = new byte[12];
        boolean bl2 = false;
        if (inputStream.read(arrby, 0, 12) != 12) return bl2;
        if (!"RIFF".equals(new String(arrby, 0, 4, "US-ASCII"))) return false;
        if (!"WEBP".equals(new String(arrby, 8, 4, "US-ASCII"))) return false;
        return true;
    }

    static void log(String string2, String string3, String string4) {
        Utils.log(string2, string3, string4, "");
    }

    static void log(String string2, String string3, String string4, String string5) {
        Log.d((String)"Picasso", (String)String.format("%1$-11s %2$-12s %3$s %4$s", string2, string3, string4, string5));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean parseResponseSourceHeader(String arrstring) {
        boolean bl2 = true;
        if (arrstring == null) {
            return false;
        }
        if ("CACHE".equals((arrstring = arrstring.split(" ", 2))[0])) {
            return true;
        }
        if (arrstring.length == 1) return false;
        try {
            if (!"CONDITIONAL_CACHE".equals(arrstring[0])) return false;
            int n2 = Integer.parseInt(arrstring[1]);
            if (n2 != 304) return false;
        }
        catch (NumberFormatException var0_1) {
            return false;
        }
        return bl2;
    }

    static byte[] toByteArray(InputStream inputStream) throws IOException {
        int n2;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby = new byte[4096];
        while (-1 != (n2 = inputStream.read(arrby))) {
            byteArrayOutputStream.write(arrby, 0, n2);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @TargetApi(value=11)
    private static class ActivityManagerHoneycomb {
        private ActivityManagerHoneycomb() {
        }

        static int getLargeMemoryClass(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    @TargetApi(value=12)
    private static class BitmapHoneycombMR1 {
        private BitmapHoneycombMR1() {
        }

        static int getByteCount(Bitmap bitmap) {
            return bitmap.getByteCount();
        }
    }

    private static class OkHttpLoaderCreator {
        private OkHttpLoaderCreator() {
        }

        static Downloader create(Context context) {
            return new OkHttpDownloader(context);
        }
    }

    private static class PicassoThread
    extends Thread {
        public PicassoThread(Runnable runnable) {
            super(runnable);
        }

        @Override
        public void run() {
            Process.setThreadPriority((int)10);
            super.run();
        }
    }

    static class PicassoThreadFactory
    implements ThreadFactory {
        PicassoThreadFactory() {
        }

        @Override
        public Thread newThread(Runnable runnable) {
            return new PicassoThread(runnable);
        }
    }

}

