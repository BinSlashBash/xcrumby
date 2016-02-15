/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeProvider
 */
package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

class AccessibilityNodeProviderCompatJellyBean {
    AccessibilityNodeProviderCompatJellyBean() {
    }

    public static Object newAccessibilityNodeProviderBridge(final AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
        return new AccessibilityNodeProvider(){

            public AccessibilityNodeInfo createAccessibilityNodeInfo(int n2) {
                return (AccessibilityNodeInfo)accessibilityNodeInfoBridge.createAccessibilityNodeInfo(n2);
            }

            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String string2, int n2) {
                return accessibilityNodeInfoBridge.findAccessibilityNodeInfosByText(string2, n2);
            }

            public boolean performAction(int n2, int n3, Bundle bundle) {
                return accessibilityNodeInfoBridge.performAction(n2, n3, bundle);
            }
        };
    }

    static interface AccessibilityNodeInfoBridge {
        public Object createAccessibilityNodeInfo(int var1);

        public List<Object> findAccessibilityNodeInfosByText(String var1, int var2);

        public boolean performAction(int var1, int var2, Bundle var3);
    }

}

