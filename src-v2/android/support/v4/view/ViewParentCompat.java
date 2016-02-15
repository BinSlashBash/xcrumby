/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 */
package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewParentCompatICS;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

public class ViewParentCompat {
    static final ViewParentCompatImpl IMPL = Build.VERSION.SDK_INT >= 14 ? new ViewParentCompatICSImpl() : new ViewParentCompatStubImpl();

    private ViewParentCompat() {
    }

    public static boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        return IMPL.requestSendAccessibilityEvent(viewParent, view, accessibilityEvent);
    }

    static class ViewParentCompatICSImpl
    extends ViewParentCompatStubImpl {
        ViewParentCompatICSImpl() {
        }

        @Override
        public boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
            return ViewParentCompatICS.requestSendAccessibilityEvent(viewParent, view, accessibilityEvent);
        }
    }

    static interface ViewParentCompatImpl {
        public boolean requestSendAccessibilityEvent(ViewParent var1, View var2, AccessibilityEvent var3);
    }

    static class ViewParentCompatStubImpl
    implements ViewParentCompatImpl {
        ViewParentCompatStubImpl() {
        }

        @Override
        public boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
            if (view == null) {
                return false;
            }
            ((AccessibilityManager)view.getContext().getSystemService("accessibility")).sendAccessibilityEvent(accessibilityEvent);
            return true;
        }
    }

}

