/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v13.app;

import android.app.Fragment;
import android.os.Build;
import android.support.v13.app.FragmentCompatICS;
import android.support.v13.app.FragmentCompatICSMR1;

public class FragmentCompat {
    static final FragmentCompatImpl IMPL = Build.VERSION.SDK_INT >= 15 ? new ICSMR1FragmentCompatImpl() : (Build.VERSION.SDK_INT >= 14 ? new ICSFragmentCompatImpl() : new BaseFragmentCompatImpl());

    public static void setMenuVisibility(Fragment fragment, boolean bl2) {
        IMPL.setMenuVisibility(fragment, bl2);
    }

    public static void setUserVisibleHint(Fragment fragment, boolean bl2) {
        IMPL.setUserVisibleHint(fragment, bl2);
    }

    static class BaseFragmentCompatImpl
    implements FragmentCompatImpl {
        BaseFragmentCompatImpl() {
        }

        @Override
        public void setMenuVisibility(Fragment fragment, boolean bl2) {
        }

        @Override
        public void setUserVisibleHint(Fragment fragment, boolean bl2) {
        }
    }

    static interface FragmentCompatImpl {
        public void setMenuVisibility(Fragment var1, boolean var2);

        public void setUserVisibleHint(Fragment var1, boolean var2);
    }

    static class ICSFragmentCompatImpl
    extends BaseFragmentCompatImpl {
        ICSFragmentCompatImpl() {
        }

        @Override
        public void setMenuVisibility(Fragment fragment, boolean bl2) {
            FragmentCompatICS.setMenuVisibility(fragment, bl2);
        }
    }

    static class ICSMR1FragmentCompatImpl
    extends ICSFragmentCompatImpl {
        ICSMR1FragmentCompatImpl() {
        }

        @Override
        public void setUserVisibleHint(Fragment fragment, boolean bl2) {
            FragmentCompatICSMR1.setUserVisibleHint(fragment, bl2);
        }
    }

}

