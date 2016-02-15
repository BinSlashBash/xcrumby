/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v4.view;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompatEclairMr1;
import android.support.v4.view.ViewCompatGingerbread;
import android.support.v4.view.ViewCompatHC;
import android.support.v4.view.ViewCompatICS;
import android.support.v4.view.ViewCompatJB;
import android.support.v4.view.ViewCompatJellybeanMr1;
import android.support.v4.view.ViewCompatKitKat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

public class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    private static final long FAKE_FRAME_TIME = 10;
    static final ViewCompatImpl IMPL;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;

    static {
        int n2 = Build.VERSION.SDK_INT;
        IMPL = n2 >= 19 ? new KitKatViewCompatImpl() : (n2 >= 17 ? new JbMr1ViewCompatImpl() : (n2 >= 16 ? new JBViewCompatImpl() : (n2 >= 14 ? new ICSViewCompatImpl() : (n2 >= 11 ? new HCViewCompatImpl() : (n2 >= 9 ? new GBViewCompatImpl() : new BaseViewCompatImpl())))));
    }

    public static boolean canScrollHorizontally(View view, int n2) {
        return IMPL.canScrollHorizontally(view, n2);
    }

    public static boolean canScrollVertically(View view, int n2) {
        return IMPL.canScrollVertically(view, n2);
    }

    public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        return IMPL.getAccessibilityNodeProvider(view);
    }

    public static float getAlpha(View view) {
        return IMPL.getAlpha(view);
    }

    public static int getImportantForAccessibility(View view) {
        return IMPL.getImportantForAccessibility(view);
    }

    public static int getLabelFor(View view) {
        return IMPL.getLabelFor(view);
    }

    public static int getLayerType(View view) {
        return IMPL.getLayerType(view);
    }

    public static int getLayoutDirection(View view) {
        return IMPL.getLayoutDirection(view);
    }

    public static int getMeasuredHeightAndState(View view) {
        return IMPL.getMeasuredHeightAndState(view);
    }

    public static int getMeasuredState(View view) {
        return IMPL.getMeasuredState(view);
    }

    public static int getMeasuredWidthAndState(View view) {
        return IMPL.getMeasuredWidthAndState(view);
    }

    public static int getOverScrollMode(View view) {
        return IMPL.getOverScrollMode(view);
    }

    public static ViewParent getParentForAccessibility(View view) {
        return IMPL.getParentForAccessibility(view);
    }

    public static boolean hasTransientState(View view) {
        return IMPL.hasTransientState(view);
    }

    public static boolean isOpaque(View view) {
        return IMPL.isOpaque(view);
    }

    public static void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public static void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        IMPL.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
    }

    public static void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public static boolean performAccessibilityAction(View view, int n2, Bundle bundle) {
        return IMPL.performAccessibilityAction(view, n2, bundle);
    }

    public static void postInvalidateOnAnimation(View view) {
        IMPL.postInvalidateOnAnimation(view);
    }

    public static void postInvalidateOnAnimation(View view, int n2, int n3, int n4, int n5) {
        IMPL.postInvalidateOnAnimation(view, n2, n3, n4, n5);
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        IMPL.postOnAnimation(view, runnable);
    }

    public static void postOnAnimationDelayed(View view, Runnable runnable, long l2) {
        IMPL.postOnAnimationDelayed(view, runnable, l2);
    }

    public static int resolveSizeAndState(int n2, int n3, int n4) {
        return IMPL.resolveSizeAndState(n2, n3, n4);
    }

    public static void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        IMPL.setAccessibilityDelegate(view, accessibilityDelegateCompat);
    }

    public static void setHasTransientState(View view, boolean bl2) {
        IMPL.setHasTransientState(view, bl2);
    }

    public static void setImportantForAccessibility(View view, int n2) {
        IMPL.setImportantForAccessibility(view, n2);
    }

    public static void setLabelFor(View view, int n2) {
        IMPL.setLabelFor(view, n2);
    }

    public static void setLayerPaint(View view, Paint paint) {
        IMPL.setLayerPaint(view, paint);
    }

    public static void setLayerType(View view, int n2, Paint paint) {
        IMPL.setLayerType(view, n2, paint);
    }

    public static void setLayoutDirection(View view, int n2) {
        IMPL.setLayoutDirection(view, n2);
    }

    public static void setOverScrollMode(View view, int n2) {
        IMPL.setOverScrollMode(view, n2);
    }

    public int getAccessibilityLiveRegion(View view) {
        return IMPL.getAccessibilityLiveRegion(view);
    }

    public void setAccessibilityLiveRegion(View view, int n2) {
        IMPL.setAccessibilityLiveRegion(view, n2);
    }

    static class BaseViewCompatImpl
    implements ViewCompatImpl {
        BaseViewCompatImpl() {
        }

        @Override
        public boolean canScrollHorizontally(View view, int n2) {
            return false;
        }

        @Override
        public boolean canScrollVertically(View view, int n2) {
            return false;
        }

        @Override
        public int getAccessibilityLiveRegion(View view) {
            return 0;
        }

        @Override
        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
            return null;
        }

        @Override
        public float getAlpha(View view) {
            return 1.0f;
        }

        long getFrameTime() {
            return 10;
        }

        @Override
        public int getImportantForAccessibility(View view) {
            return 0;
        }

        @Override
        public int getLabelFor(View view) {
            return 0;
        }

        @Override
        public int getLayerType(View view) {
            return 0;
        }

        @Override
        public int getLayoutDirection(View view) {
            return 0;
        }

        @Override
        public int getMeasuredHeightAndState(View view) {
            return view.getMeasuredHeight();
        }

        @Override
        public int getMeasuredState(View view) {
            return 0;
        }

        @Override
        public int getMeasuredWidthAndState(View view) {
            return view.getMeasuredWidth();
        }

        @Override
        public int getOverScrollMode(View view) {
            return 2;
        }

        @Override
        public ViewParent getParentForAccessibility(View view) {
            return view.getParent();
        }

        @Override
        public boolean hasTransientState(View view) {
            return false;
        }

        @Override
        public boolean isOpaque(View view) {
            boolean bl2 = false;
            view = view.getBackground();
            boolean bl3 = bl2;
            if (view != null) {
                bl3 = bl2;
                if (view.getOpacity() == -1) {
                    bl3 = true;
                }
            }
            return bl3;
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        }

        @Override
        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        }

        @Override
        public boolean performAccessibilityAction(View view, int n2, Bundle bundle) {
            return false;
        }

        @Override
        public void postInvalidateOnAnimation(View view) {
            view.postInvalidateDelayed(this.getFrameTime());
        }

        @Override
        public void postInvalidateOnAnimation(View view, int n2, int n3, int n4, int n5) {
            view.postInvalidateDelayed(this.getFrameTime(), n2, n3, n4, n5);
        }

        @Override
        public void postOnAnimation(View view, Runnable runnable) {
            view.postDelayed(runnable, this.getFrameTime());
        }

        @Override
        public void postOnAnimationDelayed(View view, Runnable runnable, long l2) {
            view.postDelayed(runnable, this.getFrameTime() + l2);
        }

        @Override
        public int resolveSizeAndState(int n2, int n3, int n4) {
            return View.resolveSize((int)n2, (int)n3);
        }

        @Override
        public void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        }

        @Override
        public void setAccessibilityLiveRegion(View view, int n2) {
        }

        @Override
        public void setHasTransientState(View view, boolean bl2) {
        }

        @Override
        public void setImportantForAccessibility(View view, int n2) {
        }

        @Override
        public void setLabelFor(View view, int n2) {
        }

        @Override
        public void setLayerPaint(View view, Paint paint) {
        }

        @Override
        public void setLayerType(View view, int n2, Paint paint) {
        }

        @Override
        public void setLayoutDirection(View view, int n2) {
        }

        @Override
        public void setOverScrollMode(View view, int n2) {
        }
    }

    static class EclairMr1ViewCompatImpl
    extends BaseViewCompatImpl {
        EclairMr1ViewCompatImpl() {
        }

        @Override
        public boolean isOpaque(View view) {
            return ViewCompatEclairMr1.isOpaque(view);
        }
    }

    static class GBViewCompatImpl
    extends EclairMr1ViewCompatImpl {
        GBViewCompatImpl() {
        }

        @Override
        public int getOverScrollMode(View view) {
            return ViewCompatGingerbread.getOverScrollMode(view);
        }

        @Override
        public void setOverScrollMode(View view, int n2) {
            ViewCompatGingerbread.setOverScrollMode(view, n2);
        }
    }

    static class HCViewCompatImpl
    extends GBViewCompatImpl {
        HCViewCompatImpl() {
        }

        @Override
        public float getAlpha(View view) {
            return ViewCompatHC.getAlpha(view);
        }

        @Override
        long getFrameTime() {
            return ViewCompatHC.getFrameTime();
        }

        @Override
        public int getLayerType(View view) {
            return ViewCompatHC.getLayerType(view);
        }

        @Override
        public int getMeasuredHeightAndState(View view) {
            return ViewCompatHC.getMeasuredHeightAndState(view);
        }

        @Override
        public int getMeasuredState(View view) {
            return ViewCompatHC.getMeasuredState(view);
        }

        @Override
        public int getMeasuredWidthAndState(View view) {
            return ViewCompatHC.getMeasuredWidthAndState(view);
        }

        @Override
        public int resolveSizeAndState(int n2, int n3, int n4) {
            return ViewCompatHC.resolveSizeAndState(n2, n3, n4);
        }

        @Override
        public void setLayerPaint(View view, Paint paint) {
            this.setLayerType(view, this.getLayerType(view), paint);
            view.invalidate();
        }

        @Override
        public void setLayerType(View view, int n2, Paint paint) {
            ViewCompatHC.setLayerType(view, n2, paint);
        }
    }

    static class ICSViewCompatImpl
    extends HCViewCompatImpl {
        ICSViewCompatImpl() {
        }

        @Override
        public boolean canScrollHorizontally(View view, int n2) {
            return ViewCompatICS.canScrollHorizontally(view, n2);
        }

        @Override
        public boolean canScrollVertically(View view, int n2) {
            return ViewCompatICS.canScrollVertically(view, n2);
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            ViewCompatICS.onInitializeAccessibilityEvent(view, accessibilityEvent);
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewCompatICS.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.getInfo());
        }

        @Override
        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            ViewCompatICS.onPopulateAccessibilityEvent(view, accessibilityEvent);
        }

        @Override
        public void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
            ViewCompatICS.setAccessibilityDelegate(view, accessibilityDelegateCompat.getBridge());
        }
    }

    static class JBViewCompatImpl
    extends ICSViewCompatImpl {
        JBViewCompatImpl() {
        }

        @Override
        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View object) {
            if ((object = ViewCompatJB.getAccessibilityNodeProvider((View)object)) != null) {
                return new AccessibilityNodeProviderCompat(object);
            }
            return null;
        }

        @Override
        public int getImportantForAccessibility(View view) {
            return ViewCompatJB.getImportantForAccessibility(view);
        }

        @Override
        public ViewParent getParentForAccessibility(View view) {
            return ViewCompatJB.getParentForAccessibility(view);
        }

        @Override
        public boolean hasTransientState(View view) {
            return ViewCompatJB.hasTransientState(view);
        }

        @Override
        public boolean performAccessibilityAction(View view, int n2, Bundle bundle) {
            return ViewCompatJB.performAccessibilityAction(view, n2, bundle);
        }

        @Override
        public void postInvalidateOnAnimation(View view) {
            ViewCompatJB.postInvalidateOnAnimation(view);
        }

        @Override
        public void postInvalidateOnAnimation(View view, int n2, int n3, int n4, int n5) {
            ViewCompatJB.postInvalidateOnAnimation(view, n2, n3, n4, n5);
        }

        @Override
        public void postOnAnimation(View view, Runnable runnable) {
            ViewCompatJB.postOnAnimation(view, runnable);
        }

        @Override
        public void postOnAnimationDelayed(View view, Runnable runnable, long l2) {
            ViewCompatJB.postOnAnimationDelayed(view, runnable, l2);
        }

        @Override
        public void setHasTransientState(View view, boolean bl2) {
            ViewCompatJB.setHasTransientState(view, bl2);
        }

        @Override
        public void setImportantForAccessibility(View view, int n2) {
            ViewCompatJB.setImportantForAccessibility(view, n2);
        }
    }

    static class JbMr1ViewCompatImpl
    extends JBViewCompatImpl {
        JbMr1ViewCompatImpl() {
        }

        @Override
        public int getLabelFor(View view) {
            return ViewCompatJellybeanMr1.getLabelFor(view);
        }

        @Override
        public int getLayoutDirection(View view) {
            return ViewCompatJellybeanMr1.getLayoutDirection(view);
        }

        @Override
        public void setLabelFor(View view, int n2) {
            ViewCompatJellybeanMr1.setLabelFor(view, n2);
        }

        @Override
        public void setLayerPaint(View view, Paint paint) {
            ViewCompatJellybeanMr1.setLayerPaint(view, paint);
        }

        @Override
        public void setLayoutDirection(View view, int n2) {
            ViewCompatJellybeanMr1.setLayoutDirection(view, n2);
        }
    }

    static class KitKatViewCompatImpl
    extends JbMr1ViewCompatImpl {
        KitKatViewCompatImpl() {
        }

        @Override
        public int getAccessibilityLiveRegion(View view) {
            return ViewCompatKitKat.getAccessibilityLiveRegion(view);
        }

        @Override
        public void setAccessibilityLiveRegion(View view, int n2) {
            ViewCompatKitKat.setAccessibilityLiveRegion(view, n2);
        }
    }

    static interface ViewCompatImpl {
        public boolean canScrollHorizontally(View var1, int var2);

        public boolean canScrollVertically(View var1, int var2);

        public int getAccessibilityLiveRegion(View var1);

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View var1);

        public float getAlpha(View var1);

        public int getImportantForAccessibility(View var1);

        public int getLabelFor(View var1);

        public int getLayerType(View var1);

        public int getLayoutDirection(View var1);

        public int getMeasuredHeightAndState(View var1);

        public int getMeasuredState(View var1);

        public int getMeasuredWidthAndState(View var1);

        public int getOverScrollMode(View var1);

        public ViewParent getParentForAccessibility(View var1);

        public boolean hasTransientState(View var1);

        public boolean isOpaque(View var1);

        public void onInitializeAccessibilityEvent(View var1, AccessibilityEvent var2);

        public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfoCompat var2);

        public void onPopulateAccessibilityEvent(View var1, AccessibilityEvent var2);

        public boolean performAccessibilityAction(View var1, int var2, Bundle var3);

        public void postInvalidateOnAnimation(View var1);

        public void postInvalidateOnAnimation(View var1, int var2, int var3, int var4, int var5);

        public void postOnAnimation(View var1, Runnable var2);

        public void postOnAnimationDelayed(View var1, Runnable var2, long var3);

        public int resolveSizeAndState(int var1, int var2, int var3);

        public void setAccessibilityDelegate(View var1, AccessibilityDelegateCompat var2);

        public void setAccessibilityLiveRegion(View var1, int var2);

        public void setHasTransientState(View var1, boolean var2);

        public void setImportantForAccessibility(View var1, int var2);

        public void setLabelFor(View var1, int var2);

        public void setLayerPaint(View var1, Paint var2);

        public void setLayerType(View var1, int var2, Paint var3);

        public void setLayoutDirection(View var1, int var2);

        public void setOverScrollMode(View var1, int var2);
    }

}

