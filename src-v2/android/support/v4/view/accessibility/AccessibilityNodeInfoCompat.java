/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.View
 */
package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompatIcs;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompatJellyBean;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompatJellybeanMr2;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompatKitKat;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    private static final AccessibilityNodeInfoImpl IMPL = Build.VERSION.SDK_INT >= 19 ? new AccessibilityNodeInfoKitKatImpl() : (Build.VERSION.SDK_INT >= 18 ? new AccessibilityNodeInfoJellybeanMr2Impl() : (Build.VERSION.SDK_INT >= 16 ? new AccessibilityNodeInfoJellybeanImpl() : (Build.VERSION.SDK_INT >= 14 ? new AccessibilityNodeInfoIcsImpl() : new AccessibilityNodeInfoStubImpl())));
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private final Object mInfo;

    public AccessibilityNodeInfoCompat(Object object) {
        this.mInfo = object;
    }

    private static String getActionSymbolicName(int n2) {
        switch (n2) {
            default: {
                return "ACTION_UNKNOWN";
            }
            case 1: {
                return "ACTION_FOCUS";
            }
            case 2: {
                return "ACTION_CLEAR_FOCUS";
            }
            case 4: {
                return "ACTION_SELECT";
            }
            case 8: {
                return "ACTION_CLEAR_SELECTION";
            }
            case 16: {
                return "ACTION_CLICK";
            }
            case 32: {
                return "ACTION_LONG_CLICK";
            }
            case 64: {
                return "ACTION_ACCESSIBILITY_FOCUS";
            }
            case 128: {
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            }
            case 256: {
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            }
            case 512: {
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            }
            case 1024: {
                return "ACTION_NEXT_HTML_ELEMENT";
            }
            case 2048: {
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            }
            case 4096: {
                return "ACTION_SCROLL_FORWARD";
            }
            case 8192: {
                return "ACTION_SCROLL_BACKWARD";
            }
            case 65536: {
                return "ACTION_CUT";
            }
            case 16384: {
                return "ACTION_COPY";
            }
            case 32768: {
                return "ACTION_PASTE";
            }
            case 131072: 
        }
        return "ACTION_SET_SELECTION";
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.obtain(accessibilityNodeInfoCompat.mInfo));
    }

    public static AccessibilityNodeInfoCompat obtain(View view) {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.obtain(view));
    }

    public static AccessibilityNodeInfoCompat obtain(View view, int n2) {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.obtain(view, n2));
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object object) {
        if (object != null) {
            return new AccessibilityNodeInfoCompat(object);
        }
        return null;
    }

    public void addAction(int n2) {
        IMPL.addAction(this.mInfo, n2);
    }

    public void addChild(View view) {
        IMPL.addChild(this.mInfo, view);
    }

    public void addChild(View view, int n2) {
        IMPL.addChild(this.mInfo, view, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AccessibilityNodeInfoCompat)object;
        if (this.mInfo == null) {
            if (object.mInfo == null) return true;
            return false;
        }
        if (!this.mInfo.equals(object.mInfo)) return false;
        return true;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String object) {
        ArrayList<AccessibilityNodeInfoCompat> arrayList = new ArrayList<AccessibilityNodeInfoCompat>();
        object = IMPL.findAccessibilityNodeInfosByText(this.mInfo, (String)object);
        int n2 = object.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(new AccessibilityNodeInfoCompat(object.get(i2)));
        }
        return arrayList;
    }

    public AccessibilityNodeInfoCompat findFocus(int n2) {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.findFocus(this.mInfo, n2));
    }

    public AccessibilityNodeInfoCompat focusSearch(int n2) {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.focusSearch(this.mInfo, n2));
    }

    public int getActions() {
        return IMPL.getActions(this.mInfo);
    }

    public void getBoundsInParent(Rect rect) {
        IMPL.getBoundsInParent(this.mInfo, rect);
    }

    public void getBoundsInScreen(Rect rect) {
        IMPL.getBoundsInScreen(this.mInfo, rect);
    }

    public AccessibilityNodeInfoCompat getChild(int n2) {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.getChild(this.mInfo, n2));
    }

    public int getChildCount() {
        return IMPL.getChildCount(this.mInfo);
    }

    public CharSequence getClassName() {
        return IMPL.getClassName(this.mInfo);
    }

    public CharSequence getContentDescription() {
        return IMPL.getContentDescription(this.mInfo);
    }

    public Object getInfo() {
        return this.mInfo;
    }

    public int getLiveRegion() {
        return IMPL.getLiveRegion(this.mInfo);
    }

    public int getMovementGranularities() {
        return IMPL.getMovementGranularities(this.mInfo);
    }

    public CharSequence getPackageName() {
        return IMPL.getPackageName(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getParent() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.getParent(this.mInfo));
    }

    public CharSequence getText() {
        return IMPL.getText(this.mInfo);
    }

    public String getViewIdResourceName() {
        return IMPL.getViewIdResourceName(this.mInfo);
    }

    public int getWindowId() {
        return IMPL.getWindowId(this.mInfo);
    }

    public int hashCode() {
        if (this.mInfo == null) {
            return 0;
        }
        return this.mInfo.hashCode();
    }

    public boolean isAccessibilityFocused() {
        return IMPL.isAccessibilityFocused(this.mInfo);
    }

    public boolean isCheckable() {
        return IMPL.isCheckable(this.mInfo);
    }

    public boolean isChecked() {
        return IMPL.isChecked(this.mInfo);
    }

    public boolean isClickable() {
        return IMPL.isClickable(this.mInfo);
    }

    public boolean isEnabled() {
        return IMPL.isEnabled(this.mInfo);
    }

    public boolean isFocusable() {
        return IMPL.isFocusable(this.mInfo);
    }

    public boolean isFocused() {
        return IMPL.isFocused(this.mInfo);
    }

    public boolean isLongClickable() {
        return IMPL.isLongClickable(this.mInfo);
    }

    public boolean isPassword() {
        return IMPL.isPassword(this.mInfo);
    }

    public boolean isScrollable() {
        return IMPL.isScrollable(this.mInfo);
    }

    public boolean isSelected() {
        return IMPL.isSelected(this.mInfo);
    }

    public boolean isVisibleToUser() {
        return IMPL.isVisibleToUser(this.mInfo);
    }

    public boolean performAction(int n2) {
        return IMPL.performAction(this.mInfo, n2);
    }

    public boolean performAction(int n2, Bundle bundle) {
        return IMPL.performAction(this.mInfo, n2, bundle);
    }

    public void recycle() {
        IMPL.recycle(this.mInfo);
    }

    public void setAccessibilityFocused(boolean bl2) {
        IMPL.setAccessibilityFocused(this.mInfo, bl2);
    }

    public void setBoundsInParent(Rect rect) {
        IMPL.setBoundsInParent(this.mInfo, rect);
    }

    public void setBoundsInScreen(Rect rect) {
        IMPL.setBoundsInScreen(this.mInfo, rect);
    }

    public void setCheckable(boolean bl2) {
        IMPL.setCheckable(this.mInfo, bl2);
    }

    public void setChecked(boolean bl2) {
        IMPL.setChecked(this.mInfo, bl2);
    }

    public void setClassName(CharSequence charSequence) {
        IMPL.setClassName(this.mInfo, charSequence);
    }

    public void setClickable(boolean bl2) {
        IMPL.setClickable(this.mInfo, bl2);
    }

    public void setContentDescription(CharSequence charSequence) {
        IMPL.setContentDescription(this.mInfo, charSequence);
    }

    public void setEnabled(boolean bl2) {
        IMPL.setEnabled(this.mInfo, bl2);
    }

    public void setFocusable(boolean bl2) {
        IMPL.setFocusable(this.mInfo, bl2);
    }

    public void setFocused(boolean bl2) {
        IMPL.setFocused(this.mInfo, bl2);
    }

    public void setLiveRegion(int n2) {
        IMPL.setLiveRegion(this.mInfo, n2);
    }

    public void setLongClickable(boolean bl2) {
        IMPL.setLongClickable(this.mInfo, bl2);
    }

    public void setMovementGranularities(int n2) {
        IMPL.setMovementGranularities(this.mInfo, n2);
    }

    public void setPackageName(CharSequence charSequence) {
        IMPL.setPackageName(this.mInfo, charSequence);
    }

    public void setParent(View view) {
        IMPL.setParent(this.mInfo, view);
    }

    public void setParent(View view, int n2) {
        IMPL.setParent(this.mInfo, view, n2);
    }

    public void setPassword(boolean bl2) {
        IMPL.setPassword(this.mInfo, bl2);
    }

    public void setScrollable(boolean bl2) {
        IMPL.setScrollable(this.mInfo, bl2);
    }

    public void setSelected(boolean bl2) {
        IMPL.setSelected(this.mInfo, bl2);
    }

    public void setSource(View view) {
        IMPL.setSource(this.mInfo, view);
    }

    public void setSource(View view, int n2) {
        IMPL.setSource(this.mInfo, view, n2);
    }

    public void setText(CharSequence charSequence) {
        IMPL.setText(this.mInfo, charSequence);
    }

    public void setViewIdResourceName(String string2) {
        IMPL.setViewIdResourceName(this.mInfo, string2);
    }

    public void setVisibleToUser(boolean bl2) {
        IMPL.setVisibleToUser(this.mInfo, bl2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        Rect rect = new Rect();
        this.getBoundsInParent(rect);
        stringBuilder.append("; boundsInParent: " + (Object)rect);
        this.getBoundsInScreen(rect);
        stringBuilder.append("; boundsInScreen: " + (Object)rect);
        stringBuilder.append("; packageName: ").append(this.getPackageName());
        stringBuilder.append("; className: ").append(this.getClassName());
        stringBuilder.append("; text: ").append(this.getText());
        stringBuilder.append("; contentDescription: ").append(this.getContentDescription());
        stringBuilder.append("; viewId: ").append(this.getViewIdResourceName());
        stringBuilder.append("; checkable: ").append(this.isCheckable());
        stringBuilder.append("; checked: ").append(this.isChecked());
        stringBuilder.append("; focusable: ").append(this.isFocusable());
        stringBuilder.append("; focused: ").append(this.isFocused());
        stringBuilder.append("; selected: ").append(this.isSelected());
        stringBuilder.append("; clickable: ").append(this.isClickable());
        stringBuilder.append("; longClickable: ").append(this.isLongClickable());
        stringBuilder.append("; enabled: ").append(this.isEnabled());
        stringBuilder.append("; password: ").append(this.isPassword());
        stringBuilder.append("; scrollable: " + this.isScrollable());
        stringBuilder.append("; [");
        int n2 = this.getActions();
        while (n2 != 0) {
            int n3 = 1 << Integer.numberOfTrailingZeros(n2);
            int n4 = n2 & ~ n3;
            stringBuilder.append(AccessibilityNodeInfoCompat.getActionSymbolicName(n3));
            n2 = n4;
            if (n4 == 0) continue;
            stringBuilder.append(", ");
            n2 = n4;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static class AccessibilityNodeInfoIcsImpl
    extends AccessibilityNodeInfoStubImpl {
        AccessibilityNodeInfoIcsImpl() {
        }

        @Override
        public void addAction(Object object, int n2) {
            AccessibilityNodeInfoCompatIcs.addAction(object, n2);
        }

        @Override
        public void addChild(Object object, View view) {
            AccessibilityNodeInfoCompatIcs.addChild(object, view);
        }

        @Override
        public List<Object> findAccessibilityNodeInfosByText(Object object, String string2) {
            return AccessibilityNodeInfoCompatIcs.findAccessibilityNodeInfosByText(object, string2);
        }

        @Override
        public int getActions(Object object) {
            return AccessibilityNodeInfoCompatIcs.getActions(object);
        }

        @Override
        public void getBoundsInParent(Object object, Rect rect) {
            AccessibilityNodeInfoCompatIcs.getBoundsInParent(object, rect);
        }

        @Override
        public void getBoundsInScreen(Object object, Rect rect) {
            AccessibilityNodeInfoCompatIcs.getBoundsInScreen(object, rect);
        }

        @Override
        public Object getChild(Object object, int n2) {
            return AccessibilityNodeInfoCompatIcs.getChild(object, n2);
        }

        @Override
        public int getChildCount(Object object) {
            return AccessibilityNodeInfoCompatIcs.getChildCount(object);
        }

        @Override
        public CharSequence getClassName(Object object) {
            return AccessibilityNodeInfoCompatIcs.getClassName(object);
        }

        @Override
        public CharSequence getContentDescription(Object object) {
            return AccessibilityNodeInfoCompatIcs.getContentDescription(object);
        }

        @Override
        public CharSequence getPackageName(Object object) {
            return AccessibilityNodeInfoCompatIcs.getPackageName(object);
        }

        @Override
        public Object getParent(Object object) {
            return AccessibilityNodeInfoCompatIcs.getParent(object);
        }

        @Override
        public CharSequence getText(Object object) {
            return AccessibilityNodeInfoCompatIcs.getText(object);
        }

        @Override
        public int getWindowId(Object object) {
            return AccessibilityNodeInfoCompatIcs.getWindowId(object);
        }

        @Override
        public boolean isCheckable(Object object) {
            return AccessibilityNodeInfoCompatIcs.isCheckable(object);
        }

        @Override
        public boolean isChecked(Object object) {
            return AccessibilityNodeInfoCompatIcs.isChecked(object);
        }

        @Override
        public boolean isClickable(Object object) {
            return AccessibilityNodeInfoCompatIcs.isClickable(object);
        }

        @Override
        public boolean isEnabled(Object object) {
            return AccessibilityNodeInfoCompatIcs.isEnabled(object);
        }

        @Override
        public boolean isFocusable(Object object) {
            return AccessibilityNodeInfoCompatIcs.isFocusable(object);
        }

        @Override
        public boolean isFocused(Object object) {
            return AccessibilityNodeInfoCompatIcs.isFocused(object);
        }

        @Override
        public boolean isLongClickable(Object object) {
            return AccessibilityNodeInfoCompatIcs.isLongClickable(object);
        }

        @Override
        public boolean isPassword(Object object) {
            return AccessibilityNodeInfoCompatIcs.isPassword(object);
        }

        @Override
        public boolean isScrollable(Object object) {
            return AccessibilityNodeInfoCompatIcs.isScrollable(object);
        }

        @Override
        public boolean isSelected(Object object) {
            return AccessibilityNodeInfoCompatIcs.isSelected(object);
        }

        @Override
        public Object obtain() {
            return AccessibilityNodeInfoCompatIcs.obtain();
        }

        @Override
        public Object obtain(View view) {
            return AccessibilityNodeInfoCompatIcs.obtain(view);
        }

        @Override
        public Object obtain(Object object) {
            return AccessibilityNodeInfoCompatIcs.obtain(object);
        }

        @Override
        public boolean performAction(Object object, int n2) {
            return AccessibilityNodeInfoCompatIcs.performAction(object, n2);
        }

        @Override
        public void recycle(Object object) {
            AccessibilityNodeInfoCompatIcs.recycle(object);
        }

        @Override
        public void setBoundsInParent(Object object, Rect rect) {
            AccessibilityNodeInfoCompatIcs.setBoundsInParent(object, rect);
        }

        @Override
        public void setBoundsInScreen(Object object, Rect rect) {
            AccessibilityNodeInfoCompatIcs.setBoundsInScreen(object, rect);
        }

        @Override
        public void setCheckable(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setCheckable(object, bl2);
        }

        @Override
        public void setChecked(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setChecked(object, bl2);
        }

        @Override
        public void setClassName(Object object, CharSequence charSequence) {
            AccessibilityNodeInfoCompatIcs.setClassName(object, charSequence);
        }

        @Override
        public void setClickable(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setClickable(object, bl2);
        }

        @Override
        public void setContentDescription(Object object, CharSequence charSequence) {
            AccessibilityNodeInfoCompatIcs.setContentDescription(object, charSequence);
        }

        @Override
        public void setEnabled(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setEnabled(object, bl2);
        }

        @Override
        public void setFocusable(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setFocusable(object, bl2);
        }

        @Override
        public void setFocused(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setFocused(object, bl2);
        }

        @Override
        public void setLongClickable(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setLongClickable(object, bl2);
        }

        @Override
        public void setPackageName(Object object, CharSequence charSequence) {
            AccessibilityNodeInfoCompatIcs.setPackageName(object, charSequence);
        }

        @Override
        public void setParent(Object object, View view) {
            AccessibilityNodeInfoCompatIcs.setParent(object, view);
        }

        @Override
        public void setPassword(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setPassword(object, bl2);
        }

        @Override
        public void setScrollable(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setScrollable(object, bl2);
        }

        @Override
        public void setSelected(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatIcs.setSelected(object, bl2);
        }

        @Override
        public void setSource(Object object, View view) {
            AccessibilityNodeInfoCompatIcs.setSource(object, view);
        }

        @Override
        public void setText(Object object, CharSequence charSequence) {
            AccessibilityNodeInfoCompatIcs.setText(object, charSequence);
        }
    }

    static interface AccessibilityNodeInfoImpl {
        public void addAction(Object var1, int var2);

        public void addChild(Object var1, View var2);

        public void addChild(Object var1, View var2, int var3);

        public List<Object> findAccessibilityNodeInfosByText(Object var1, String var2);

        public Object findFocus(Object var1, int var2);

        public Object focusSearch(Object var1, int var2);

        public int getActions(Object var1);

        public void getBoundsInParent(Object var1, Rect var2);

        public void getBoundsInScreen(Object var1, Rect var2);

        public Object getChild(Object var1, int var2);

        public int getChildCount(Object var1);

        public CharSequence getClassName(Object var1);

        public CharSequence getContentDescription(Object var1);

        public int getLiveRegion(Object var1);

        public int getMovementGranularities(Object var1);

        public CharSequence getPackageName(Object var1);

        public Object getParent(Object var1);

        public CharSequence getText(Object var1);

        public String getViewIdResourceName(Object var1);

        public int getWindowId(Object var1);

        public boolean isAccessibilityFocused(Object var1);

        public boolean isCheckable(Object var1);

        public boolean isChecked(Object var1);

        public boolean isClickable(Object var1);

        public boolean isEnabled(Object var1);

        public boolean isFocusable(Object var1);

        public boolean isFocused(Object var1);

        public boolean isLongClickable(Object var1);

        public boolean isPassword(Object var1);

        public boolean isScrollable(Object var1);

        public boolean isSelected(Object var1);

        public boolean isVisibleToUser(Object var1);

        public Object obtain();

        public Object obtain(View var1);

        public Object obtain(View var1, int var2);

        public Object obtain(Object var1);

        public boolean performAction(Object var1, int var2);

        public boolean performAction(Object var1, int var2, Bundle var3);

        public void recycle(Object var1);

        public void setAccessibilityFocused(Object var1, boolean var2);

        public void setBoundsInParent(Object var1, Rect var2);

        public void setBoundsInScreen(Object var1, Rect var2);

        public void setCheckable(Object var1, boolean var2);

        public void setChecked(Object var1, boolean var2);

        public void setClassName(Object var1, CharSequence var2);

        public void setClickable(Object var1, boolean var2);

        public void setContentDescription(Object var1, CharSequence var2);

        public void setEnabled(Object var1, boolean var2);

        public void setFocusable(Object var1, boolean var2);

        public void setFocused(Object var1, boolean var2);

        public void setLiveRegion(Object var1, int var2);

        public void setLongClickable(Object var1, boolean var2);

        public void setMovementGranularities(Object var1, int var2);

        public void setPackageName(Object var1, CharSequence var2);

        public void setParent(Object var1, View var2);

        public void setParent(Object var1, View var2, int var3);

        public void setPassword(Object var1, boolean var2);

        public void setScrollable(Object var1, boolean var2);

        public void setSelected(Object var1, boolean var2);

        public void setSource(Object var1, View var2);

        public void setSource(Object var1, View var2, int var3);

        public void setText(Object var1, CharSequence var2);

        public void setViewIdResourceName(Object var1, String var2);

        public void setVisibleToUser(Object var1, boolean var2);
    }

    static class AccessibilityNodeInfoJellybeanImpl
    extends AccessibilityNodeInfoIcsImpl {
        AccessibilityNodeInfoJellybeanImpl() {
        }

        @Override
        public void addChild(Object object, View view, int n2) {
            AccessibilityNodeInfoCompatJellyBean.addChild(object, view, n2);
        }

        @Override
        public Object findFocus(Object object, int n2) {
            return AccessibilityNodeInfoCompatJellyBean.findFocus(object, n2);
        }

        @Override
        public Object focusSearch(Object object, int n2) {
            return AccessibilityNodeInfoCompatJellyBean.focusSearch(object, n2);
        }

        @Override
        public int getMovementGranularities(Object object) {
            return AccessibilityNodeInfoCompatJellyBean.getMovementGranularities(object);
        }

        @Override
        public boolean isAccessibilityFocused(Object object) {
            return AccessibilityNodeInfoCompatJellyBean.isAccessibilityFocused(object);
        }

        @Override
        public boolean isVisibleToUser(Object object) {
            return AccessibilityNodeInfoCompatJellyBean.isVisibleToUser(object);
        }

        @Override
        public Object obtain(View view, int n2) {
            return AccessibilityNodeInfoCompatJellyBean.obtain(view, n2);
        }

        @Override
        public boolean performAction(Object object, int n2, Bundle bundle) {
            return AccessibilityNodeInfoCompatJellyBean.performAction(object, n2, bundle);
        }

        @Override
        public void setAccessibilityFocused(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatJellyBean.setAccesibilityFocused(object, bl2);
        }

        @Override
        public void setMovementGranularities(Object object, int n2) {
            AccessibilityNodeInfoCompatJellyBean.setMovementGranularities(object, n2);
        }

        @Override
        public void setParent(Object object, View view, int n2) {
            AccessibilityNodeInfoCompatJellyBean.setParent(object, view, n2);
        }

        @Override
        public void setSource(Object object, View view, int n2) {
            AccessibilityNodeInfoCompatJellyBean.setSource(object, view, n2);
        }

        @Override
        public void setVisibleToUser(Object object, boolean bl2) {
            AccessibilityNodeInfoCompatJellyBean.setVisibleToUser(object, bl2);
        }
    }

    static class AccessibilityNodeInfoJellybeanMr2Impl
    extends AccessibilityNodeInfoJellybeanImpl {
        AccessibilityNodeInfoJellybeanMr2Impl() {
        }

        @Override
        public String getViewIdResourceName(Object object) {
            return AccessibilityNodeInfoCompatJellybeanMr2.getViewIdResourceName(object);
        }

        @Override
        public void setViewIdResourceName(Object object, String string2) {
            AccessibilityNodeInfoCompatJellybeanMr2.setViewIdResourceName(object, string2);
        }
    }

    static class AccessibilityNodeInfoKitKatImpl
    extends AccessibilityNodeInfoJellybeanMr2Impl {
        AccessibilityNodeInfoKitKatImpl() {
        }

        @Override
        public int getLiveRegion(Object object) {
            return AccessibilityNodeInfoCompatKitKat.getLiveRegion(object);
        }

        @Override
        public void setLiveRegion(Object object, int n2) {
            AccessibilityNodeInfoCompatKitKat.setLiveRegion(object, n2);
        }
    }

    static class AccessibilityNodeInfoStubImpl
    implements AccessibilityNodeInfoImpl {
        AccessibilityNodeInfoStubImpl() {
        }

        @Override
        public void addAction(Object object, int n2) {
        }

        @Override
        public void addChild(Object object, View view) {
        }

        @Override
        public void addChild(Object object, View view, int n2) {
        }

        @Override
        public List<Object> findAccessibilityNodeInfosByText(Object object, String string2) {
            return Collections.emptyList();
        }

        @Override
        public Object findFocus(Object object, int n2) {
            return null;
        }

        @Override
        public Object focusSearch(Object object, int n2) {
            return null;
        }

        @Override
        public int getActions(Object object) {
            return 0;
        }

        @Override
        public void getBoundsInParent(Object object, Rect rect) {
        }

        @Override
        public void getBoundsInScreen(Object object, Rect rect) {
        }

        @Override
        public Object getChild(Object object, int n2) {
            return null;
        }

        @Override
        public int getChildCount(Object object) {
            return 0;
        }

        @Override
        public CharSequence getClassName(Object object) {
            return null;
        }

        @Override
        public CharSequence getContentDescription(Object object) {
            return null;
        }

        @Override
        public int getLiveRegion(Object object) {
            return 0;
        }

        @Override
        public int getMovementGranularities(Object object) {
            return 0;
        }

        @Override
        public CharSequence getPackageName(Object object) {
            return null;
        }

        @Override
        public Object getParent(Object object) {
            return null;
        }

        @Override
        public CharSequence getText(Object object) {
            return null;
        }

        @Override
        public String getViewIdResourceName(Object object) {
            return null;
        }

        @Override
        public int getWindowId(Object object) {
            return 0;
        }

        @Override
        public boolean isAccessibilityFocused(Object object) {
            return false;
        }

        @Override
        public boolean isCheckable(Object object) {
            return false;
        }

        @Override
        public boolean isChecked(Object object) {
            return false;
        }

        @Override
        public boolean isClickable(Object object) {
            return false;
        }

        @Override
        public boolean isEnabled(Object object) {
            return false;
        }

        @Override
        public boolean isFocusable(Object object) {
            return false;
        }

        @Override
        public boolean isFocused(Object object) {
            return false;
        }

        @Override
        public boolean isLongClickable(Object object) {
            return false;
        }

        @Override
        public boolean isPassword(Object object) {
            return false;
        }

        @Override
        public boolean isScrollable(Object object) {
            return false;
        }

        @Override
        public boolean isSelected(Object object) {
            return false;
        }

        @Override
        public boolean isVisibleToUser(Object object) {
            return false;
        }

        @Override
        public Object obtain() {
            return null;
        }

        @Override
        public Object obtain(View view) {
            return null;
        }

        @Override
        public Object obtain(View view, int n2) {
            return null;
        }

        @Override
        public Object obtain(Object object) {
            return null;
        }

        @Override
        public boolean performAction(Object object, int n2) {
            return false;
        }

        @Override
        public boolean performAction(Object object, int n2, Bundle bundle) {
            return false;
        }

        @Override
        public void recycle(Object object) {
        }

        @Override
        public void setAccessibilityFocused(Object object, boolean bl2) {
        }

        @Override
        public void setBoundsInParent(Object object, Rect rect) {
        }

        @Override
        public void setBoundsInScreen(Object object, Rect rect) {
        }

        @Override
        public void setCheckable(Object object, boolean bl2) {
        }

        @Override
        public void setChecked(Object object, boolean bl2) {
        }

        @Override
        public void setClassName(Object object, CharSequence charSequence) {
        }

        @Override
        public void setClickable(Object object, boolean bl2) {
        }

        @Override
        public void setContentDescription(Object object, CharSequence charSequence) {
        }

        @Override
        public void setEnabled(Object object, boolean bl2) {
        }

        @Override
        public void setFocusable(Object object, boolean bl2) {
        }

        @Override
        public void setFocused(Object object, boolean bl2) {
        }

        @Override
        public void setLiveRegion(Object object, int n2) {
        }

        @Override
        public void setLongClickable(Object object, boolean bl2) {
        }

        @Override
        public void setMovementGranularities(Object object, int n2) {
        }

        @Override
        public void setPackageName(Object object, CharSequence charSequence) {
        }

        @Override
        public void setParent(Object object, View view) {
        }

        @Override
        public void setParent(Object object, View view, int n2) {
        }

        @Override
        public void setPassword(Object object, boolean bl2) {
        }

        @Override
        public void setScrollable(Object object, boolean bl2) {
        }

        @Override
        public void setSelected(Object object, boolean bl2) {
        }

        @Override
        public void setSource(Object object, View view) {
        }

        @Override
        public void setSource(Object object, View view, int n2) {
        }

        @Override
        public void setText(Object object, CharSequence charSequence) {
        }

        @Override
        public void setViewIdResourceName(Object object, String string2) {
        }

        @Override
        public void setVisibleToUser(Object object, boolean bl2) {
        }
    }

}

