/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 */
package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompatFroyo;
import android.support.v4.content.ContextCompatHoneycomb;
import android.support.v4.content.ContextCompatJellybean;
import android.support.v4.content.ContextCompatKitKat;
import java.io.File;

public class ContextCompat {
    private static final String DIR_ANDROID = "Android";
    private static final String DIR_CACHE = "cache";
    private static final String DIR_DATA = "data";
    private static final String DIR_FILES = "files";
    private static final String DIR_OBB = "obb";

    /*
     * Enabled aggressive block sorting
     */
    private static /* varargs */ File buildPath(File file, String ... arrstring) {
        int n2 = arrstring.length;
        int n3 = 0;
        while (n3 < n2) {
            String string2 = arrstring[n3];
            if (file == null) {
                file = new File(string2);
            } else if (string2 != null) {
                file = new File(file, string2);
            }
            ++n3;
        }
        return file;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static File[] getExternalCacheDirs(Context object) {
        int n2 = Build.VERSION.SDK_INT;
        if (n2 >= 19) {
            return ContextCompatKitKat.getExternalCacheDirs((Context)object);
        }
        if (n2 >= 8) {
            object = ContextCompatFroyo.getExternalCacheDir((Context)object);
            do {
                return new File[]{object};
                break;
            } while (true);
        }
        object = ContextCompat.buildPath(Environment.getExternalStorageDirectory(), "Android", "data", object.getPackageName(), "cache");
        return new File[]{object};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static File[] getExternalFilesDirs(Context object, String string2) {
        int n2 = Build.VERSION.SDK_INT;
        if (n2 >= 19) {
            return ContextCompatKitKat.getExternalFilesDirs((Context)object, string2);
        }
        if (n2 >= 8) {
            object = ContextCompatFroyo.getExternalFilesDir((Context)object, string2);
            do {
                return new File[]{object};
                break;
            } while (true);
        }
        object = ContextCompat.buildPath(Environment.getExternalStorageDirectory(), "Android", "data", object.getPackageName(), "files", string2);
        return new File[]{object};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static File[] getObbDirs(Context object) {
        int n2 = Build.VERSION.SDK_INT;
        if (n2 >= 19) {
            return ContextCompatKitKat.getObbDirs((Context)object);
        }
        if (n2 >= 11) {
            object = ContextCompatHoneycomb.getObbDir((Context)object);
            do {
                return new File[]{object};
                break;
            } while (true);
        }
        object = ContextCompat.buildPath(Environment.getExternalStorageDirectory(), "Android", "obb", object.getPackageName());
        return new File[]{object};
    }

    public static boolean startActivities(Context context, Intent[] arrintent) {
        return ContextCompat.startActivities(context, arrintent, null);
    }

    public static boolean startActivities(Context context, Intent[] arrintent, Bundle bundle) {
        int n2 = Build.VERSION.SDK_INT;
        if (n2 >= 16) {
            ContextCompatJellybean.startActivities(context, arrintent, bundle);
            return true;
        }
        if (n2 >= 11) {
            ContextCompatHoneycomb.startActivities(context, arrintent);
            return true;
        }
        return false;
    }
}

