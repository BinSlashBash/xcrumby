/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 */
package android.support.v4.view.accessibility;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompatKitKat;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityNodeProviderCompat {
    private static final AccessibilityNodeProviderImpl IMPL = Build.VERSION.SDK_INT >= 19 ? new AccessibilityNodeProviderKitKatImpl() : (Build.VERSION.SDK_INT >= 16 ? new AccessibilityNodeProviderJellyBeanImpl() : new AccessibilityNodeProviderStubImpl());
    private final Object mProvider;

    public AccessibilityNodeProviderCompat() {
        this.mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
    }

    public AccessibilityNodeProviderCompat(Object object) {
        this.mProvider = object;
    }

    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int n2) {
        return null;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String string2, int n2) {
        return null;
    }

    public AccessibilityNodeInfoCompat findFocus(int n2) {
        return null;
    }

    public Object getProvider() {
        return this.mProvider;
    }

    public boolean performAction(int n2, int n3, Bundle bundle) {
        return false;
    }

    static interface AccessibilityNodeProviderImpl {
        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat var1);
    }

    static class AccessibilityNodeProviderJellyBeanImpl
    extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderJellyBeanImpl() {
        }

        @Override
        public Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            return AccessibilityNodeProviderCompatJellyBean.newAccessibilityNodeProviderBridge(new AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge(){

                @Override
                public Object createAccessibilityNodeInfo(int n2) {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = accessibilityNodeProviderCompat.createAccessibilityNodeInfo(n2);
                    if (accessibilityNodeInfoCompat == null) {
                        return null;
                    }
                    return accessibilityNodeInfoCompat.getInfo();
                }

                @Override
                public List<Object> findAccessibilityNodeInfosByText(String object, int n2) {
                    object = accessibilityNodeProviderCompat.findAccessibilityNodeInfosByText((String)object, n2);
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    int n3 = object.size();
                    for (n2 = 0; n2 < n3; ++n2) {
                        arrayList.add(((AccessibilityNodeInfoCompat)object.get(n2)).getInfo());
                    }
                    return arrayList;
                }

                @Override
                public boolean performAction(int n2, int n3, Bundle bundle) {
                    return accessibilityNodeProviderCompat.performAction(n2, n3, bundle);
                }
            });
        }

    }

    static class AccessibilityNodeProviderKitKatImpl
    extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderKitKatImpl() {
        }

        @Override
        public Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            return AccessibilityNodeProviderCompatKitKat.newAccessibilityNodeProviderBridge(new AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge(){

                @Override
                public Object createAccessibilityNodeInfo(int n2) {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = accessibilityNodeProviderCompat.createAccessibilityNodeInfo(n2);
                    if (accessibilityNodeInfoCompat == null) {
                        return null;
                    }
                    return accessibilityNodeInfoCompat.getInfo();
                }

                @Override
                public List<Object> findAccessibilityNodeInfosByText(String object, int n2) {
                    object = accessibilityNodeProviderCompat.findAccessibilityNodeInfosByText((String)object, n2);
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    int n3 = object.size();
                    for (n2 = 0; n2 < n3; ++n2) {
                        arrayList.add(((AccessibilityNodeInfoCompat)object.get(n2)).getInfo());
                    }
                    return arrayList;
                }

                @Override
                public Object findFocus(int n2) {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = accessibilityNodeProviderCompat.findFocus(n2);
                    if (accessibilityNodeInfoCompat == null) {
                        return null;
                    }
                    return accessibilityNodeInfoCompat.getInfo();
                }

                @Override
                public boolean performAction(int n2, int n3, Bundle bundle) {
                    return accessibilityNodeProviderCompat.performAction(n2, n3, bundle);
                }
            });
        }

    }

    static class AccessibilityNodeProviderStubImpl
    implements AccessibilityNodeProviderImpl {
        AccessibilityNodeProviderStubImpl() {
        }

        @Override
        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            return null;
        }
    }

}

