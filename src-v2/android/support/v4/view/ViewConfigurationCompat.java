/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.ViewConfiguration
 */
package android.support.v4.view;

import android.os.Build;
import android.support.v4.view.ViewConfigurationCompatFroyo;
import android.view.ViewConfiguration;

public class ViewConfigurationCompat {
    static final ViewConfigurationVersionImpl IMPL = Build.VERSION.SDK_INT >= 11 ? new FroyoViewConfigurationVersionImpl() : new BaseViewConfigurationVersionImpl();

    public static int getScaledPagingTouchSlop(ViewConfiguration viewConfiguration) {
        return IMPL.getScaledPagingTouchSlop(viewConfiguration);
    }

    static class BaseViewConfigurationVersionImpl
    implements ViewConfigurationVersionImpl {
        BaseViewConfigurationVersionImpl() {
        }

        @Override
        public int getScaledPagingTouchSlop(ViewConfiguration viewConfiguration) {
            return viewConfiguration.getScaledTouchSlop();
        }
    }

    static class FroyoViewConfigurationVersionImpl
    implements ViewConfigurationVersionImpl {
        FroyoViewConfigurationVersionImpl() {
        }

        @Override
        public int getScaledPagingTouchSlop(ViewConfiguration viewConfiguration) {
            return ViewConfigurationCompatFroyo.getScaledPagingTouchSlop(viewConfiguration);
        }
    }

    static interface ViewConfigurationVersionImpl {
        public int getScaledPagingTouchSlop(ViewConfiguration var1);
    }

}

