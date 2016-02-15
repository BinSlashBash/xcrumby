/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package uk.co.senab.photoview.gestures;

import android.content.Context;
import android.os.Build;
import uk.co.senab.photoview.gestures.CupcakeGestureDetector;
import uk.co.senab.photoview.gestures.EclairGestureDetector;
import uk.co.senab.photoview.gestures.FroyoGestureDetector;
import uk.co.senab.photoview.gestures.GestureDetector;
import uk.co.senab.photoview.gestures.OnGestureListener;

public final class VersionedGestureDetector {
    /*
     * Enabled aggressive block sorting
     */
    public static GestureDetector newInstance(Context object, OnGestureListener onGestureListener) {
        int n2 = Build.VERSION.SDK_INT;
        object = n2 < 5 ? new CupcakeGestureDetector((Context)object) : (n2 < 8 ? new EclairGestureDetector((Context)object) : new FroyoGestureDetector((Context)object));
        object.setOnGestureListener(onGestureListener);
        return object;
    }
}

