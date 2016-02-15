/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

public class DrawerLayout
extends ViewGroup {
    private static final boolean ALLOW_EDGE_LOCK = false;
    private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
    private static final int DEFAULT_SCRIM_COLOR = -1728053248;
    private static final int[] LAYOUT_ATTRS = new int[]{16842931};
    public static final int LOCK_MODE_LOCKED_CLOSED = 1;
    public static final int LOCK_MODE_LOCKED_OPEN = 2;
    public static final int LOCK_MODE_UNLOCKED = 0;
    private static final int MIN_DRAWER_MARGIN = 64;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int PEEK_DELAY = 160;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "DrawerLayout";
    private static final float TOUCH_SLOP_SENSITIVITY = 1.0f;
    private boolean mChildrenCanceledTouch;
    private boolean mDisallowInterceptRequested;
    private int mDrawerState;
    private boolean mFirstLayout = true;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private final ViewDragCallback mLeftCallback;
    private final ViewDragHelper mLeftDragger;
    private DrawerListener mListener;
    private int mLockModeLeft;
    private int mLockModeRight;
    private int mMinDrawerMargin;
    private final ViewDragCallback mRightCallback;
    private final ViewDragHelper mRightDragger;
    private int mScrimColor = -1728053248;
    private float mScrimOpacity;
    private Paint mScrimPaint = new Paint();
    private Drawable mShadowLeft;
    private Drawable mShadowRight;

    public DrawerLayout(Context context) {
        this(context, null);
    }

    public DrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DrawerLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        float f2 = this.getResources().getDisplayMetrics().density;
        this.mMinDrawerMargin = (int)(64.0f * f2 + 0.5f);
        f2 = 400.0f * f2;
        this.mLeftCallback = new ViewDragCallback(3);
        this.mRightCallback = new ViewDragCallback(5);
        this.mLeftDragger = ViewDragHelper.create(this, 1.0f, this.mLeftCallback);
        this.mLeftDragger.setEdgeTrackingEnabled(1);
        this.mLeftDragger.setMinVelocity(f2);
        this.mLeftCallback.setDragger(this.mLeftDragger);
        this.mRightDragger = ViewDragHelper.create(this, 1.0f, this.mRightCallback);
        this.mRightDragger.setEdgeTrackingEnabled(2);
        this.mRightDragger.setMinVelocity(f2);
        this.mRightCallback.setDragger(this.mRightDragger);
        this.setFocusableInTouchMode(true);
        ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
        ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
    }

    private View findVisibleDrawer() {
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            View view = this.getChildAt(i2);
            if (!this.isDrawerView(view) || !this.isDrawerVisible(view)) continue;
            return view;
        }
        return null;
    }

    static String gravityToString(int n2) {
        if ((n2 & 3) == 3) {
            return "LEFT";
        }
        if ((n2 & 5) == 5) {
            return "RIGHT";
        }
        return Integer.toHexString(n2);
    }

    private static boolean hasOpaqueBackground(View view) {
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

    private boolean hasPeekingDrawer() {
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (!((LayoutParams)this.getChildAt((int)i2).getLayoutParams()).isPeeking) continue;
            return true;
        }
        return false;
    }

    private boolean hasVisibleDrawer() {
        if (this.findVisibleDrawer() != null) {
            return true;
        }
        return false;
    }

    void cancelChildViewTouch() {
        if (!this.mChildrenCanceledTouch) {
            long l2 = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain((long)l2, (long)l2, (int)3, (float)0.0f, (float)0.0f, (int)0);
            int n2 = this.getChildCount();
            for (int i2 = 0; i2 < n2; ++i2) {
                this.getChildAt(i2).dispatchTouchEvent(motionEvent);
            }
            motionEvent.recycle();
            this.mChildrenCanceledTouch = true;
        }
    }

    boolean checkDrawerViewAbsoluteGravity(View view, int n2) {
        if ((this.getDrawerViewAbsoluteGravity(view) & n2) == n2) {
            return true;
        }
        return false;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams)) {
            return true;
        }
        return false;
    }

    public void closeDrawer(int n2) {
        View view = this.findDrawerWithGravity(n2);
        if (view == null) {
            throw new IllegalArgumentException("No drawer view found with gravity " + DrawerLayout.gravityToString(n2));
        }
        this.closeDrawer(view);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void closeDrawer(View object) {
        if (!this.isDrawerView((View)object)) {
            throw new IllegalArgumentException("View " + object + " is not a sliding drawer");
        }
        if (this.mFirstLayout) {
            object = (LayoutParams)object.getLayoutParams();
            object.onScreen = 0.0f;
            object.knownOpen = false;
        } else if (this.checkDrawerViewAbsoluteGravity((View)object, 3)) {
            this.mLeftDragger.smoothSlideViewTo((View)object, - object.getWidth(), object.getTop());
        } else {
            this.mRightDragger.smoothSlideViewTo((View)object, this.getWidth(), object.getTop());
        }
        this.invalidate();
    }

    public void closeDrawers() {
        this.closeDrawers(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    void closeDrawers(boolean bl2) {
        int n2 = 0;
        int n3 = this.getChildCount();
        for (int i2 = 0; i2 < n3; ++i2) {
            View view = this.getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n4 = n2;
            if (this.isDrawerView(view)) {
                if (bl2 && !layoutParams.isPeeking) {
                    n4 = n2;
                } else {
                    n4 = view.getWidth();
                    n2 = this.checkDrawerViewAbsoluteGravity(view, 3) ? (n2 |= this.mLeftDragger.smoothSlideViewTo(view, - n4, view.getTop())) : (n2 |= this.mRightDragger.smoothSlideViewTo(view, this.getWidth(), view.getTop()));
                    layoutParams.isPeeking = false;
                    n4 = n2;
                }
            }
            n2 = n4;
        }
        this.mLeftCallback.removeCallbacks();
        this.mRightCallback.removeCallbacks();
        if (n2 != 0) {
            this.invalidate();
        }
    }

    public void computeScroll() {
        int n2 = this.getChildCount();
        float f2 = 0.0f;
        for (int i2 = 0; i2 < n2; ++i2) {
            f2 = Math.max(f2, ((LayoutParams)this.getChildAt((int)i2).getLayoutParams()).onScreen);
        }
        this.mScrimOpacity = f2;
        if (this.mLeftDragger.continueSettling(true) | this.mRightDragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    void dispatchOnDrawerClosed(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.knownOpen) {
            layoutParams.knownOpen = false;
            if (this.mListener != null) {
                this.mListener.onDrawerClosed(view);
            }
            this.sendAccessibilityEvent(32);
        }
    }

    void dispatchOnDrawerOpened(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.knownOpen) {
            layoutParams.knownOpen = true;
            if (this.mListener != null) {
                this.mListener.onDrawerOpened(view);
            }
            view.sendAccessibilityEvent(32);
        }
    }

    void dispatchOnDrawerSlide(View view, float f2) {
        if (this.mListener != null) {
            this.mListener.onDrawerSlide(view, f2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean drawChild(Canvas canvas, View view, long l2) {
        int n2 = this.getHeight();
        boolean bl2 = this.isContentView(view);
        int n3 = 0;
        int n4 = 0;
        int n5 = this.getWidth();
        int n6 = canvas.save();
        int n7 = n5;
        if (bl2) {
            int n8 = this.getChildCount();
            n3 = n4;
            for (n7 = 0; n7 < n8; ++n7) {
                View view2 = this.getChildAt(n7);
                n4 = n3;
                int n9 = n5;
                if (view2 != view) {
                    n4 = n3;
                    n9 = n5;
                    if (view2.getVisibility() == 0) {
                        n4 = n3;
                        n9 = n5;
                        if (DrawerLayout.hasOpaqueBackground(view2)) {
                            n4 = n3;
                            n9 = n5;
                            if (this.isDrawerView(view2)) {
                                int n10;
                                if (view2.getHeight() < n2) {
                                    n9 = n5;
                                    n4 = n3;
                                } else if (this.checkDrawerViewAbsoluteGravity(view2, 3)) {
                                    n10 = view2.getRight();
                                    n4 = n3;
                                    n9 = n5;
                                    if (n10 > n3) {
                                        n4 = n10;
                                        n9 = n5;
                                    }
                                } else {
                                    n10 = view2.getLeft();
                                    n4 = n3;
                                    n9 = n5;
                                    if (n10 < n5) {
                                        n9 = n10;
                                        n4 = n3;
                                    }
                                }
                            }
                        }
                    }
                }
                n3 = n4;
                n5 = n9;
            }
            canvas.clipRect(n3, 0, n5, this.getHeight());
            n7 = n5;
        }
        boolean bl3 = super.drawChild(canvas, view, l2);
        canvas.restoreToCount(n6);
        if (this.mScrimOpacity > 0.0f && bl2) {
            n5 = (int)((float)((this.mScrimColor & -16777216) >>> 24) * this.mScrimOpacity);
            n4 = this.mScrimColor;
            this.mScrimPaint.setColor(n5 << 24 | n4 & 16777215);
            canvas.drawRect((float)n3, 0.0f, (float)n7, (float)this.getHeight(), this.mScrimPaint);
            return bl3;
        } else {
            if (this.mShadowLeft != null && this.checkDrawerViewAbsoluteGravity(view, 3)) {
                n3 = this.mShadowLeft.getIntrinsicWidth();
                n5 = view.getRight();
                n7 = this.mLeftDragger.getEdgeSize();
                float f2 = Math.max(0.0f, Math.min((float)n5 / (float)n7, 1.0f));
                this.mShadowLeft.setBounds(n5, view.getTop(), n5 + n3, view.getBottom());
                this.mShadowLeft.setAlpha((int)(255.0f * f2));
                this.mShadowLeft.draw(canvas);
                return bl3;
            }
            if (this.mShadowRight == null || !this.checkDrawerViewAbsoluteGravity(view, 5)) return bl3;
            {
                n3 = this.mShadowRight.getIntrinsicWidth();
                n5 = view.getLeft();
                n7 = this.getWidth();
                n4 = this.mRightDragger.getEdgeSize();
                float f3 = Math.max(0.0f, Math.min((float)(n7 - n5) / (float)n4, 1.0f));
                this.mShadowRight.setBounds(n5 - n3, view.getTop(), n5, view.getBottom());
                this.mShadowRight.setAlpha((int)(255.0f * f3));
                this.mShadowRight.draw(canvas);
                return bl3;
            }
        }
    }

    View findDrawerWithGravity(int n2) {
        int n3 = GravityCompat.getAbsoluteGravity(n2, ViewCompat.getLayoutDirection((View)this));
        int n4 = this.getChildCount();
        for (n2 = 0; n2 < n4; ++n2) {
            View view = this.getChildAt(n2);
            if ((this.getDrawerViewAbsoluteGravity(view) & 7) != (n3 & 7)) continue;
            return view;
        }
        return null;
    }

    View findOpenDrawer() {
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            View view = this.getChildAt(i2);
            if (!((LayoutParams)view.getLayoutParams()).knownOpen) continue;
            return view;
        }
        return null;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public int getDrawerLockMode(int n2) {
        if ((n2 = GravityCompat.getAbsoluteGravity(n2, ViewCompat.getLayoutDirection((View)this))) == 3) {
            return this.mLockModeLeft;
        }
        if (n2 == 5) {
            return this.mLockModeRight;
        }
        return 0;
    }

    public int getDrawerLockMode(View view) {
        int n2 = this.getDrawerViewAbsoluteGravity(view);
        if (n2 == 3) {
            return this.mLockModeLeft;
        }
        if (n2 == 5) {
            return this.mLockModeRight;
        }
        return 0;
    }

    int getDrawerViewAbsoluteGravity(View view) {
        return GravityCompat.getAbsoluteGravity(((LayoutParams)view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection((View)this));
    }

    float getDrawerViewOffset(View view) {
        return ((LayoutParams)view.getLayoutParams()).onScreen;
    }

    boolean isContentView(View view) {
        if (((LayoutParams)view.getLayoutParams()).gravity == 0) {
            return true;
        }
        return false;
    }

    public boolean isDrawerOpen(int n2) {
        View view = this.findDrawerWithGravity(n2);
        if (view != null) {
            return this.isDrawerOpen(view);
        }
        return false;
    }

    public boolean isDrawerOpen(View view) {
        if (!this.isDrawerView(view)) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a drawer");
        }
        return ((LayoutParams)view.getLayoutParams()).knownOpen;
    }

    boolean isDrawerView(View view) {
        if ((GravityCompat.getAbsoluteGravity(((LayoutParams)view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(view)) & 7) != 0) {
            return true;
        }
        return false;
    }

    public boolean isDrawerVisible(int n2) {
        View view = this.findDrawerWithGravity(n2);
        if (view != null) {
            return this.isDrawerVisible(view);
        }
        return false;
    }

    public boolean isDrawerVisible(View view) {
        if (!this.isDrawerView(view)) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a drawer");
        }
        if (((LayoutParams)view.getLayoutParams()).onScreen > 0.0f) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    void moveDrawerToOffset(View view, float f2) {
        float f3 = this.getDrawerViewOffset(view);
        int n2 = view.getWidth();
        int n3 = (int)((float)n2 * f3);
        n2 = (int)((float)n2 * f2) - n3;
        if (!this.checkDrawerViewAbsoluteGravity(view, 3)) {
            n2 = - n2;
        }
        view.offsetLeftAndRight(n2);
        this.setDrawerViewOffset(view, f2);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl2 = false;
        int n2 = MotionEventCompat.getActionMasked(motionEvent);
        boolean bl3 = this.mLeftDragger.shouldInterceptTouchEvent(motionEvent);
        boolean bl4 = this.mRightDragger.shouldInterceptTouchEvent(motionEvent);
        int n3 = 0;
        int n4 = 0;
        switch (n2) {
            default: {
                n2 = n4;
                break;
            }
            case 0: {
                float f2 = motionEvent.getX();
                float f3 = motionEvent.getY();
                this.mInitialMotionX = f2;
                this.mInitialMotionY = f3;
                n2 = n3;
                if (this.mScrimOpacity > 0.0f) {
                    n2 = n3;
                    if (this.isContentView(this.mLeftDragger.findTopChildUnder((int)f2, (int)f3))) {
                        n2 = 1;
                    }
                }
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            }
            case 2: {
                n2 = n4;
                if (!this.mLeftDragger.checkTouchSlop(3)) break;
                this.mLeftCallback.removeCallbacks();
                this.mRightCallback.removeCallbacks();
                n2 = n4;
                break;
            }
            case 1: 
            case 3: {
                this.closeDrawers(true);
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                n2 = n4;
            }
        }
        if (bl3 | bl4) return true;
        if (n2 != 0) return true;
        if (this.hasPeekingDrawer()) return true;
        if (!this.mChildrenCanceledTouch) return bl2;
        return true;
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        if (n2 == 4 && this.hasVisibleDrawer()) {
            KeyEventCompat.startTracking(keyEvent);
            return true;
        }
        return super.onKeyDown(n2, keyEvent);
    }

    public boolean onKeyUp(int n2, KeyEvent keyEvent) {
        if (n2 == 4) {
            keyEvent = this.findVisibleDrawer();
            if (keyEvent != null && this.getDrawerLockMode((View)keyEvent) == 0) {
                this.closeDrawers();
            }
            if (keyEvent != null) {
                return true;
            }
            return false;
        }
        return super.onKeyUp(n2, keyEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        this.mInLayout = true;
        int n6 = n4 - n2;
        int n7 = this.getChildCount();
        n4 = 0;
        do {
            if (n4 >= n7) {
                this.mInLayout = false;
                this.mFirstLayout = false;
                return;
            }
            View view = this.getChildAt(n4);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (this.isContentView(view)) {
                    view.layout(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + view.getMeasuredWidth(), layoutParams.topMargin + view.getMeasuredHeight());
                } else {
                    float f2;
                    int n8;
                    int n9 = view.getMeasuredWidth();
                    int n10 = view.getMeasuredHeight();
                    if (this.checkDrawerViewAbsoluteGravity(view, 3)) {
                        n8 = - n9 + (int)((float)n9 * layoutParams.onScreen);
                        f2 = (float)(n9 + n8) / (float)n9;
                    } else {
                        n8 = n6 - (int)((float)n9 * layoutParams.onScreen);
                        f2 = (float)(n6 - n8) / (float)n9;
                    }
                    boolean bl3 = f2 != layoutParams.onScreen;
                    switch (layoutParams.gravity & 112) {
                        default: {
                            view.layout(n8, layoutParams.topMargin, n8 + n9, layoutParams.topMargin + n10);
                            break;
                        }
                        case 80: {
                            n2 = n5 - n3;
                            view.layout(n8, n2 - layoutParams.bottomMargin - view.getMeasuredHeight(), n8 + n9, n2 - layoutParams.bottomMargin);
                            break;
                        }
                        case 16: {
                            int n11 = n5 - n3;
                            int n12 = (n11 - n10) / 2;
                            if (n12 < layoutParams.topMargin) {
                                n2 = layoutParams.topMargin;
                            } else {
                                n2 = n12;
                                if (n12 + n10 > n11 - layoutParams.bottomMargin) {
                                    n2 = n11 - layoutParams.bottomMargin - n10;
                                }
                            }
                            view.layout(n8, n2, n8 + n9, n2 + n10);
                        }
                    }
                    if (bl3) {
                        this.setDrawerViewOffset(view, f2);
                    }
                    n2 = layoutParams.onScreen > 0.0f ? 0 : 4;
                    if (view.getVisibility() != n2) {
                        view.setVisibility(n2);
                    }
                }
            }
            ++n4;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onMeasure(int var1_1, int var2_2) {
        var8_3 = View.MeasureSpec.getMode((int)var1_1);
        var7_4 = View.MeasureSpec.getMode((int)var2_2);
        var3_5 = View.MeasureSpec.getSize((int)var1_1);
        var6_6 = View.MeasureSpec.getSize((int)var2_2);
        if (var8_3 != 1073741824) ** GOTO lbl-1000
        var4_7 = var6_6;
        var5_8 = var3_5;
        if (var7_4 != 1073741824) lbl-1000: // 2 sources:
        {
            if (this.isInEditMode() == false) throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            if (var8_3 != Integer.MIN_VALUE && var8_3 == 0) {
                var3_5 = 300;
            }
            if (var7_4 == Integer.MIN_VALUE) {
                var5_8 = var3_5;
                var4_7 = var6_6;
            } else {
                var4_7 = var6_6;
                var5_8 = var3_5;
                if (var7_4 == 0) {
                    var4_7 = 300;
                    var5_8 = var3_5;
                }
            }
        }
        this.setMeasuredDimension(var5_8, var4_7);
        var6_6 = this.getChildCount();
        var3_5 = 0;
        while (var3_5 < var6_6) {
            var9_9 = this.getChildAt(var3_5);
            if (var9_9.getVisibility() != 8) {
                var10_10 = (LayoutParams)var9_9.getLayoutParams();
                if (this.isContentView(var9_9)) {
                    var9_9.measure(View.MeasureSpec.makeMeasureSpec((int)(var5_8 - var10_10.leftMargin - var10_10.rightMargin), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(var4_7 - var10_10.topMargin - var10_10.bottomMargin), (int)1073741824));
                } else {
                    if (this.isDrawerView(var9_9) == false) throw new IllegalStateException("Child " + (Object)var9_9 + " at index " + var3_5 + " does not have a valid layout_gravity - must be Gravity.LEFT, " + "Gravity.RIGHT or Gravity.NO_GRAVITY");
                    var7_4 = this.getDrawerViewAbsoluteGravity(var9_9) & 7;
                    if ((0 & var7_4) != 0) {
                        throw new IllegalStateException("Child drawer has absolute gravity " + DrawerLayout.gravityToString(var7_4) + " but this " + "DrawerLayout" + " already has a " + "drawer view along that edge");
                    }
                    var9_9.measure(DrawerLayout.getChildMeasureSpec((int)var1_1, (int)(this.mMinDrawerMargin + var10_10.leftMargin + var10_10.rightMargin), (int)var10_10.width), DrawerLayout.getChildMeasureSpec((int)var2_2, (int)(var10_10.topMargin + var10_10.bottomMargin), (int)var10_10.height));
                }
            }
            ++var3_5;
        }
    }

    protected void onRestoreInstanceState(Parcelable object) {
        View view;
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        if (object.openDrawerGravity != 0 && (view = this.findDrawerWithGravity(object.openDrawerGravity)) != null) {
            this.openDrawer(view);
        }
        this.setDrawerLockMode(object.lockModeLeft, 3);
        this.setDrawerLockMode(object.lockModeRight, 5);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            Object object = this.getChildAt(i2);
            if (!this.isDrawerView((View)object)) continue;
            object = (LayoutParams)object.getLayoutParams();
            if (!object.knownOpen) continue;
            savedState.openDrawerGravity = object.gravity;
            break;
        }
        savedState.lockModeLeft = this.mLockModeLeft;
        savedState.lockModeRight = this.mLockModeRight;
        return savedState;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mLeftDragger.processTouchEvent(motionEvent);
        this.mRightDragger.processTouchEvent(motionEvent);
        switch (motionEvent.getAction() & 255) {
            default: {
                return true;
            }
            case 0: {
                float f2 = motionEvent.getX();
                float f3 = motionEvent.getY();
                this.mInitialMotionX = f2;
                this.mInitialMotionY = f3;
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                return true;
            }
            case 1: {
                float f4 = motionEvent.getX();
                float f5 = motionEvent.getY();
                boolean bl2 = true;
                motionEvent = this.mLeftDragger.findTopChildUnder((int)f4, (int)f5);
                boolean bl3 = bl2;
                if (motionEvent != null) {
                    bl3 = bl2;
                    if (this.isContentView((View)motionEvent)) {
                        int n2 = this.mLeftDragger.getTouchSlop();
                        bl3 = bl2;
                        if (f4 * (f4 -= this.mInitialMotionX) + f5 * (f5 -= this.mInitialMotionY) < (float)(n2 * n2)) {
                            motionEvent = this.findOpenDrawer();
                            bl3 = bl2;
                            if (motionEvent != null) {
                                bl3 = this.getDrawerLockMode((View)motionEvent) == 2;
                            }
                        }
                    }
                }
                this.closeDrawers(bl3);
                this.mDisallowInterceptRequested = false;
                return true;
            }
            case 3: 
        }
        this.closeDrawers(true);
        this.mDisallowInterceptRequested = false;
        this.mChildrenCanceledTouch = false;
        return true;
    }

    public void openDrawer(int n2) {
        View view = this.findDrawerWithGravity(n2);
        if (view == null) {
            throw new IllegalArgumentException("No drawer view found with gravity " + DrawerLayout.gravityToString(n2));
        }
        this.openDrawer(view);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void openDrawer(View object) {
        if (!this.isDrawerView((View)object)) {
            throw new IllegalArgumentException("View " + object + " is not a sliding drawer");
        }
        if (this.mFirstLayout) {
            object = (LayoutParams)object.getLayoutParams();
            object.onScreen = 1.0f;
            object.knownOpen = true;
        } else if (this.checkDrawerViewAbsoluteGravity((View)object, 3)) {
            this.mLeftDragger.smoothSlideViewTo((View)object, 0, object.getTop());
        } else {
            this.mRightDragger.smoothSlideViewTo((View)object, this.getWidth() - object.getWidth(), object.getTop());
        }
        this.invalidate();
    }

    public void requestDisallowInterceptTouchEvent(boolean bl2) {
        super.requestDisallowInterceptTouchEvent(bl2);
        this.mDisallowInterceptRequested = bl2;
        if (bl2) {
            this.closeDrawers(true);
        }
    }

    public void requestLayout() {
        if (!this.mInLayout) {
            super.requestLayout();
        }
    }

    public void setDrawerListener(DrawerListener drawerListener) {
        this.mListener = drawerListener;
    }

    public void setDrawerLockMode(int n2) {
        this.setDrawerLockMode(n2, 3);
        this.setDrawerLockMode(n2, 5);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDrawerLockMode(int n2, int n3) {
        ViewDragHelper viewDragHelper;
        if ((n3 = GravityCompat.getAbsoluteGravity(n3, ViewCompat.getLayoutDirection((View)this))) == 3) {
            this.mLockModeLeft = n2;
        } else if (n3 == 5) {
            this.mLockModeRight = n2;
        }
        if (n2 != 0) {
            viewDragHelper = n3 == 3 ? this.mLeftDragger : this.mRightDragger;
            viewDragHelper.cancel();
        }
        switch (n2) {
            case 2: {
                viewDragHelper = this.findDrawerWithGravity(n3);
                if (viewDragHelper == null) return;
                {
                    this.openDrawer((View)viewDragHelper);
                    return;
                }
            }
            default: {
                return;
            }
            case 1: 
        }
        viewDragHelper = this.findDrawerWithGravity(n3);
        if (viewDragHelper == null) return;
        {
            this.closeDrawer((View)viewDragHelper);
            return;
        }
    }

    public void setDrawerLockMode(int n2, View view) {
        if (!this.isDrawerView(view)) {
            throw new IllegalArgumentException("View " + (Object)view + " is not a " + "drawer with appropriate layout_gravity");
        }
        this.setDrawerLockMode(n2, ((LayoutParams)view.getLayoutParams()).gravity);
    }

    public void setDrawerShadow(int n2, int n3) {
        this.setDrawerShadow(this.getResources().getDrawable(n2), n3);
    }

    public void setDrawerShadow(Drawable drawable2, int n2) {
        if (((n2 = GravityCompat.getAbsoluteGravity(n2, ViewCompat.getLayoutDirection((View)this))) & 3) == 3) {
            this.mShadowLeft = drawable2;
            this.invalidate();
        }
        if ((n2 & 5) == 5) {
            this.mShadowRight = drawable2;
            this.invalidate();
        }
    }

    void setDrawerViewOffset(View view, float f2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (f2 == layoutParams.onScreen) {
            return;
        }
        layoutParams.onScreen = f2;
        this.dispatchOnDrawerSlide(view, f2);
    }

    public void setScrimColor(int n2) {
        this.mScrimColor = n2;
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateDrawerState(int n2, int n3, View view) {
        n2 = this.mLeftDragger.getViewDragState();
        int n4 = this.mRightDragger.getViewDragState();
        n2 = n2 == 1 || n4 == 1 ? 1 : (n2 == 2 || n4 == 2 ? 2 : 0);
        if (view != null && n3 == 0) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.onScreen == 0.0f) {
                this.dispatchOnDrawerClosed(view);
            } else if (layoutParams.onScreen == 1.0f) {
                this.dispatchOnDrawerOpened(view);
            }
        }
        if (n2 != this.mDrawerState) {
            this.mDrawerState = n2;
            if (this.mListener != null) {
                this.mListener.onDrawerStateChanged(n2);
            }
        }
    }

    class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        private final Rect mTmpRect;

        AccessibilityDelegate() {
            this.mTmpRect = new Rect();
        }

        /*
         * Enabled aggressive block sorting
         */
        private void addChildrenForAccessibility(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, ViewGroup viewGroup) {
            int n2 = viewGroup.getChildCount();
            int n3 = 0;
            while (n3 < n2) {
                View view = viewGroup.getChildAt(n3);
                if (!this.filter(view)) {
                    switch (ViewCompat.getImportantForAccessibility(view)) {
                        case 4: {
                            break;
                        }
                        default: {
                            break;
                        }
                        case 0: {
                            ViewCompat.setImportantForAccessibility(view, 1);
                        }
                        case 1: {
                            accessibilityNodeInfoCompat.addChild(view);
                            break;
                        }
                        case 2: {
                            if (!(view instanceof ViewGroup)) break;
                            this.addChildrenForAccessibility(accessibilityNodeInfoCompat, (ViewGroup)view);
                        }
                    }
                }
                ++n3;
            }
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.mTmpRect;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
        }

        public boolean filter(View view) {
            View view2 = DrawerLayout.this.findOpenDrawer();
            if (view2 != null && view2 != view) {
                return true;
            }
            return false;
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat2);
            accessibilityNodeInfoCompat.setSource(view);
            ViewParent viewParent = ViewCompat.getParentForAccessibility(view);
            if (viewParent instanceof View) {
                accessibilityNodeInfoCompat.setParent((View)viewParent);
            }
            this.copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompat2);
            accessibilityNodeInfoCompat2.recycle();
            this.addChildrenForAccessibility(accessibilityNodeInfoCompat, (ViewGroup)view);
        }

        @Override
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (!this.filter(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }
    }

    public static interface DrawerListener {
        public void onDrawerClosed(View var1);

        public void onDrawerOpened(View var1);

        public void onDrawerSlide(View var1, float var2);

        public void onDrawerStateChanged(int var1);
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int gravity = 0;
        boolean isPeeking;
        boolean knownOpen;
        float onScreen;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(int n2, int n3, int n4) {
            this(n2, n3);
            this.gravity = n4;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
            this.gravity = context.getInt(0, 0);
            context.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    protected static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        int lockModeLeft = 0;
        int lockModeRight = 0;
        int openDrawerGravity = 0;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.openDrawerGravity = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.openDrawerGravity);
        }

    }

    public static abstract class SimpleDrawerListener
    implements DrawerListener {
        @Override
        public void onDrawerClosed(View view) {
        }

        @Override
        public void onDrawerOpened(View view) {
        }

        @Override
        public void onDrawerSlide(View view, float f2) {
        }

        @Override
        public void onDrawerStateChanged(int n2) {
        }
    }

    private class ViewDragCallback
    extends ViewDragHelper.Callback {
        private final int mAbsGravity;
        private ViewDragHelper mDragger;
        private final Runnable mPeekRunnable;

        public ViewDragCallback(int n2) {
            this.mPeekRunnable = new Runnable(){

                @Override
                public void run() {
                    ViewDragCallback.this.peekDrawer();
                }
            };
            this.mAbsGravity = n2;
        }

        private void closeOtherDrawer() {
            View view;
            int n2 = 3;
            if (this.mAbsGravity == 3) {
                n2 = 5;
            }
            if ((view = DrawerLayout.this.findDrawerWithGravity(n2)) != null) {
                DrawerLayout.this.closeDrawer(view);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void peekDrawer() {
            View view;
            int n2 = 0;
            int n3 = this.mDragger.getEdgeSize();
            boolean bl2 = this.mAbsGravity == 3;
            if (bl2) {
                view = DrawerLayout.this.findDrawerWithGravity(3);
                if (view != null) {
                    n2 = - view.getWidth();
                }
                n2 += n3;
            } else {
                view = DrawerLayout.this.findDrawerWithGravity(5);
                n2 = DrawerLayout.this.getWidth() - n3;
            }
            if (view != null && (bl2 && view.getLeft() < n2 || !bl2 && view.getLeft() > n2) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                this.mDragger.smoothSlideViewTo(view, n2, view.getTop());
                layoutParams.isPeeking = true;
                DrawerLayout.this.invalidate();
                this.closeOtherDrawer();
                DrawerLayout.this.cancelChildViewTouch();
            }
        }

        @Override
        public int clampViewPositionHorizontal(View view, int n2, int n3) {
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
                return Math.max(- view.getWidth(), Math.min(n2, 0));
            }
            n3 = DrawerLayout.this.getWidth();
            return Math.max(n3 - view.getWidth(), Math.min(n2, n3));
        }

        @Override
        public int clampViewPositionVertical(View view, int n2, int n3) {
            return view.getTop();
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            return view.getWidth();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onEdgeDragStarted(int n2, int n3) {
            View view = (n2 & 1) == 1 ? DrawerLayout.this.findDrawerWithGravity(3) : DrawerLayout.this.findDrawerWithGravity(5);
            if (view != null && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                this.mDragger.captureChildView(view, n3);
            }
        }

        @Override
        public boolean onEdgeLock(int n2) {
            return false;
        }

        @Override
        public void onEdgeTouched(int n2, int n3) {
            DrawerLayout.this.postDelayed(this.mPeekRunnable, 160);
        }

        @Override
        public void onViewCaptured(View view, int n2) {
            ((LayoutParams)view.getLayoutParams()).isPeeking = false;
            this.closeOtherDrawer();
        }

        @Override
        public void onViewDragStateChanged(int n2) {
            DrawerLayout.this.updateDrawerState(this.mAbsGravity, n2, this.mDragger.getCapturedView());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewPositionChanged(View view, int n2, int n3, int n4, int n5) {
            n3 = view.getWidth();
            float f2 = DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3) ? (float)(n3 + n2) / (float)n3 : (float)(DrawerLayout.this.getWidth() - n2) / (float)n3;
            DrawerLayout.this.setDrawerViewOffset(view, f2);
            n2 = f2 == 0.0f ? 4 : 0;
            view.setVisibility(n2);
            DrawerLayout.this.invalidate();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewReleased(View view, float f2, float f3) {
            int n2;
            f3 = DrawerLayout.this.getDrawerViewOffset(view);
            int n3 = view.getWidth();
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
                n2 = f2 > 0.0f || f2 == 0.0f && f3 > 0.5f ? 0 : - n3;
            } else {
                n2 = DrawerLayout.this.getWidth();
                if (f2 < 0.0f || f2 == 0.0f && f3 > 0.5f) {
                    n2 -= n3;
                }
            }
            this.mDragger.settleCapturedViewAt(n2, view.getTop());
            DrawerLayout.this.invalidate();
        }

        public void removeCallbacks() {
            DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
        }

        public void setDragger(ViewDragHelper viewDragHelper) {
            this.mDragger = viewDragHelper;
        }

        @Override
        public boolean tryCaptureView(View view, int n2) {
            if (DrawerLayout.this.isDrawerView(view) && DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                return true;
            }
            return false;
        }

    }

}

