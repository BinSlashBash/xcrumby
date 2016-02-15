/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.View
 */
package android.support.v4.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompatJB;
import android.view.View;

public class ActivityOptionsCompat {
    protected ActivityOptionsCompat() {
    }

    public static ActivityOptionsCompat makeCustomAnimation(Context context, int n2, int n3) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeCustomAnimation(context, n2, n3));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeScaleUpAnimation(View view, int n2, int n3, int n4, int n5) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeScaleUpAnimation(view, n2, n3, n4, n5));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int n2, int n3) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeThumbnailScaleUpAnimation(view, bitmap, n2, n3));
        }
        return new ActivityOptionsCompat();
    }

    public Bundle toBundle() {
        return null;
    }

    public void update(ActivityOptionsCompat activityOptionsCompat) {
    }

    private static class ActivityOptionsImplJB
    extends ActivityOptionsCompat {
        private final ActivityOptionsCompatJB mImpl;

        ActivityOptionsImplJB(ActivityOptionsCompatJB activityOptionsCompatJB) {
            this.mImpl = activityOptionsCompatJB;
        }

        @Override
        public Bundle toBundle() {
            return this.mImpl.toBundle();
        }

        @Override
        public void update(ActivityOptionsCompat activityOptionsCompat) {
            if (activityOptionsCompat instanceof ActivityOptionsImplJB) {
                activityOptionsCompat = (ActivityOptionsImplJB)activityOptionsCompat;
                this.mImpl.update(activityOptionsCompat.mImpl);
            }
        }
    }

}

