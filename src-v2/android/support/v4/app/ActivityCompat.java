/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompatHoneycomb;
import android.support.v4.app.ActivityCompatJB;
import android.support.v4.content.ContextCompat;

public class ActivityCompat
extends ContextCompat {
    public static void finishAffinity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityCompatJB.finishAffinity(activity);
            return;
        }
        activity.finish();
    }

    public static boolean invalidateOptionsMenu(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            ActivityCompatHoneycomb.invalidateOptionsMenu(activity);
            return true;
        }
        return false;
    }

    public static void startActivity(Activity activity, Intent intent, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityCompatJB.startActivity((Context)activity, intent, bundle);
            return;
        }
        activity.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int n2, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityCompatJB.startActivityForResult(activity, intent, n2, bundle);
            return;
        }
        activity.startActivityForResult(intent, n2);
    }
}

