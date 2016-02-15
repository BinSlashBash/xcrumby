/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.DataSetObserver
 *  android.graphics.Rect
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.AbsListView
 *  android.widget.AbsListView$LayoutParams
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.Adapter
 *  android.widget.ListAdapter
 *  android.widget.Scroller
 */
package com.crumby.lib.widget.thirdparty.grid;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.Scroller;
import com.crumby.lib.widget.thirdparty.grid.ClassLoaderSavedState;
import com.crumby.lib.widget.thirdparty.grid.HeaderViewListAdapter;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ExtendableListView
extends AbsListView {
    private static final boolean DBG = false;
    private static final int INVALID_POINTER = -1;
    private static final int LAYOUT_FORCE_TOP = 1;
    private static final int LAYOUT_NORMAL = 0;
    private static final int LAYOUT_SYNC = 2;
    private static final String TAG = "ExtendableListView";
    private static final int TOUCH_MODE_DONE_WAITING = 5;
    private static final int TOUCH_MODE_DOWN = 3;
    private static final int TOUCH_MODE_FLINGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private static final int TOUCH_MODE_SCROLLING = 1;
    private static final int TOUCH_MODE_TAP = 4;
    private int mActivePointerId = -1;
    ListAdapter mAdapter;
    private boolean mBlockLayoutRequests = false;
    protected boolean mClipToPadding;
    private boolean mDataChanged;
    protected int mFirstPosition;
    private FlingRunnable mFlingRunnable;
    private int mFlingVelocity;
    private ArrayList<FixedViewInfo> mFooterViewInfos;
    private ArrayList<FixedViewInfo> mHeaderViewInfos;
    private boolean mInLayout;
    private boolean mIsAttached;
    final boolean[] mIsScrap = new boolean[1];
    private int mItemCount;
    private int mLastY;
    private int mLayoutMode;
    private int mMaximumVelocity;
    private int mMotionCorrection;
    private int mMotionPosition;
    private int mMotionX;
    private int mMotionY;
    boolean mNeedSync = false;
    private AdapterDataSetObserver mObserver;
    private int mOldItemCount;
    private AbsListView.OnScrollListener mOnScrollListener;
    private PerformClick mPerformClick;
    private RecycleBin mRecycleBin;
    protected int mSpecificTop;
    long mSyncHeight;
    protected int mSyncPosition;
    long mSyncRowId = Long.MIN_VALUE;
    private ListSavedState mSyncState;
    private int mTouchMode;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker = null;
    private int mWidthMeasureSpec;

    public ExtendableListView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.setWillNotDraw(false);
        this.setClipToPadding(false);
        this.setFocusableInTouchMode(false);
        context = ViewConfiguration.get((Context)context);
        this.mTouchSlop = context.getScaledTouchSlop();
        this.mMaximumVelocity = context.getScaledMaximumFlingVelocity();
        this.mFlingVelocity = context.getScaledMinimumFlingVelocity();
        this.mRecycleBin = new RecycleBin();
        this.mObserver = new AdapterDataSetObserver();
        this.mHeaderViewInfos = new ArrayList();
        this.mFooterViewInfos = new ArrayList();
        this.mLayoutMode = 0;
    }

    private void adjustViewsUpOrDown() {
        if (this.getChildCount() > 0) {
            int n2;
            int n3 = n2 = this.getHighestChildTop() - this.getListPaddingTop();
            if (n2 < 0) {
                n3 = 0;
            }
            if (n3 != 0) {
                this.offsetChildrenTopAndBottom(- n3);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void clearRecycledState(ArrayList<FixedViewInfo> iterator) {
        if (iterator != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                LayoutParams layoutParams = (LayoutParams)((FixedViewInfo)iterator.next()).view.getLayoutParams();
                if (layoutParams == null) continue;
                layoutParams.recycledHeaderFooter = false;
            }
        }
    }

    private void clearState() {
        this.clearRecycledState(this.mHeaderViewInfos);
        this.clearRecycledState(this.mFooterViewInfos);
        this.removeAllViewsInLayout();
        this.mFirstPosition = 0;
        this.mDataChanged = false;
        this.mRecycleBin.clear();
        this.mNeedSync = false;
        this.mSyncState = null;
        this.mLayoutMode = 0;
        this.invalidate();
    }

    private void correctTooHigh(int n2) {
        if (this.mFirstPosition + n2 - 1 == this.mItemCount - 1 && n2 > 0) {
            n2 = this.getLowestChildBottom();
            int n3 = this.getBottom() - this.getTop() - this.getListPaddingBottom() - n2;
            int n4 = this.getHighestChildTop();
            if (n3 > 0 && (this.mFirstPosition > 0 || n4 < this.getListPaddingTop())) {
                n2 = n3;
                if (this.mFirstPosition == 0) {
                    n2 = Math.min(n3, this.getListPaddingTop() - n4);
                }
                this.offsetChildrenTopAndBottom(n2);
                if (this.mFirstPosition > 0) {
                    n2 = this.mFirstPosition - 1;
                    this.fillUp(n2, this.getNextChildUpsBottom(n2));
                    this.adjustViewsUpOrDown();
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void correctTooLow(int n2) {
        if (this.mFirstPosition != 0 || n2 <= 0) return;
        {
            int n3 = this.getHighestChildTop();
            int n4 = this.getListPaddingTop();
            int n5 = this.getTop() - this.getBottom() - this.getListPaddingBottom();
            n4 = this.getLowestChildBottom();
            int n6 = this.mFirstPosition + n2 - 1;
            if ((n3 -= n4) <= 0) return;
            {
                if (n6 < this.mItemCount - 1 || n4 > n5) {
                    n2 = n3;
                    if (n6 == this.mItemCount - 1) {
                        n2 = Math.min(n3, n4 - n5);
                    }
                    this.offsetChildrenTopAndBottom(- n2);
                    if (n6 >= this.mItemCount - 1) return;
                    {
                        n2 = n6 + 1;
                        this.fillDown(n2, this.getNextChildDownsTop(n2));
                        this.adjustViewsUpOrDown();
                        return;
                    }
                } else {
                    if (n6 != this.mItemCount - 1) return;
                    {
                        this.adjustViewsUpOrDown();
                        return;
                    }
                }
            }
        }
    }

    private View fillDown(int n2, int n3) {
        int n4;
        int n5 = n4 = this.getHeight();
        int n6 = n2;
        int n7 = n3;
        if (this.mClipToPadding) {
            n5 = n4 - this.getListPaddingBottom();
            n7 = n3;
            n6 = n2;
        }
        while ((n7 < n5 || this.hasSpaceDown()) && n6 < this.mItemCount) {
            this.makeAndAddView(n6, n7, true, false);
            n7 = this.getNextChildDownsTop(++n6);
        }
        return null;
    }

    private View fillFromTop(int n2) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return this.fillDown(this.mFirstPosition, n2);
    }

    private View fillSpecific(int n2, int n3) {
        View view = this.makeAndAddView(n2, n3, true, false);
        this.mFirstPosition = n2;
        n3 = this.getNextChildUpsBottom(n2 - 1);
        int n4 = this.getNextChildDownsTop(n2 + 1);
        View view2 = this.fillUp(n2 - 1, n3);
        this.adjustViewsUpOrDown();
        View view3 = this.fillDown(n2 + 1, n4);
        n2 = this.getChildCount();
        if (n2 > 0) {
            this.correctTooHigh(n2);
        }
        if (false) {
            return view;
        }
        if (view2 != null) {
            return view2;
        }
        return view3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View fillUp(int n2, int n3) {
        int n4 = this.mClipToPadding ? this.getListPaddingTop() : 0;
        while ((n3 > n4 || this.hasSpaceUp()) && n2 >= 0) {
            this.makeAndAddView(n2, n3, false, false);
            n3 = this.getNextChildUpsBottom(--n2);
        }
        this.mFirstPosition = n2 + 1;
        return null;
    }

    private int findMotionRow(int n2) {
        int n3 = this.getChildCount();
        if (n3 > 0) {
            for (int i2 = 0; i2 < n3; ++i2) {
                if (n2 > this.getChildAt(i2).getBottom()) continue;
                return this.mFirstPosition + i2;
            }
        }
        return -1;
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
            return;
        }
        this.mVelocityTracker.clear();
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private View makeAndAddView(int n2, int n3, boolean bl2, boolean bl3) {
        View view;
        this.onChildCreated(n2, bl2);
        if (!this.mDataChanged && (view = this.mRecycleBin.getActiveView(n2)) != null) {
            this.setupChild(view, n2, n3, bl2, bl3, true);
            return view;
        }
        view = this.obtainView(n2, this.mIsScrap);
        this.setupChild(view, n2, n3, bl2, bl3, this.mIsScrap[0]);
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean moveTheChildren(int n2, int n3) {
        if (!this.hasChildren()) {
            return true;
        }
        n2 = this.getHighestChildTop();
        int n4 = this.getLowestChildBottom();
        int n5 = 0;
        int n6 = 0;
        if (this.mClipToPadding) {
            n5 = this.getListPaddingTop();
            n6 = this.getListPaddingBottom();
        }
        int n7 = this.getHeight();
        int n8 = this.getFirstChildTop();
        int n9 = this.getLastChildBottom();
        int n10 = n7 - this.getListPaddingBottom() - this.getListPaddingTop();
        n10 = n3 < 0 ? Math.max(- n10 - 1, n3) : Math.min(n10 - 1, n3);
        int n11 = this.mFirstPosition;
        int n12 = this.getListPaddingTop();
        n3 = this.getListPaddingBottom();
        int n13 = this.getChildCount();
        n2 = n11 == 0 && n2 >= n12 && n10 >= 0 ? 1 : 0;
        n3 = n11 + n13 == this.mItemCount && n4 <= n7 - n3 && n10 <= 0 ? 1 : 0;
        if (n2 != 0) {
            if (n10 != 0) {
                return true;
            }
            return false;
        }
        if (n10 != 0) {
            return true;
        }
        return false;
    }

    private View obtainView(int n2, boolean[] arrbl) {
        arrbl[0] = false;
        View view = this.mRecycleBin.getScrapView(n2);
        if (view != null) {
            View view2 = this.mAdapter.getView(n2, view, (ViewGroup)this);
            if (view2 != view) {
                this.mRecycleBin.addScrapView(view, n2);
                return view2;
            }
            arrbl[0] = true;
            return view2;
        }
        return this.mAdapter.getView(n2, null, (ViewGroup)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n2 = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(n2) == this.mActivePointerId) {
            n2 = n2 == 0 ? 1 : 0;
            this.mMotionX = (int)motionEvent.getX(n2);
            this.mMotionY = (int)motionEvent.getY(n2);
            this.mActivePointerId = motionEvent.getPointerId(n2);
            this.recycleVelocityTracker();
        }
    }

    private boolean onTouchCancel(MotionEvent motionEvent) {
        this.mTouchMode = 0;
        this.setPressed(false);
        this.invalidate();
        this.recycleVelocityTracker();
        this.mActivePointerId = -1;
        return true;
    }

    private boolean onTouchDown(MotionEvent motionEvent) {
        int n2;
        int n3 = (int)motionEvent.getX();
        int n4 = (int)motionEvent.getY();
        int n5 = this.pointToPosition(n3, n4);
        this.mVelocityTracker.clear();
        this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
        if (this.mTouchMode != 2 && !this.mDataChanged && n5 >= 0 && this.getAdapter().isEnabled(n5)) {
            this.mTouchMode = 3;
            n2 = n5;
            if (motionEvent.getEdgeFlags() != 0) {
                n2 = n5;
                if (n5 < 0) {
                    return false;
                }
            }
        } else {
            n2 = n5;
            if (this.mTouchMode == 2) {
                this.mTouchMode = 1;
                this.mMotionCorrection = 0;
                n2 = this.findMotionRow(n4);
            }
        }
        this.mMotionX = n3;
        this.mMotionY = n4;
        this.mMotionPosition = n2;
        this.mLastY = Integer.MIN_VALUE;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean onTouchMove(MotionEvent motionEvent) {
        int n2 = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
        if (n2 < 0) {
            Log.e((String)"ExtendableListView", (String)("onTouchMove could not find pointer with id " + this.mActivePointerId + " - did ExtendableListView receive an inconsistent " + "event stream?"));
            return false;
        }
        n2 = (int)MotionEventCompat.getY(motionEvent, n2);
        if (this.mDataChanged) {
            this.layoutChildren();
        }
        switch (this.mTouchMode) {
            case 3: 
            case 4: 
            case 5: {
                this.startScrollIfNeeded(n2);
            }
            default: {
                return true;
            }
            case 1: 
        }
        this.scrollIfNeeded(n2);
        return true;
    }

    private boolean onTouchPointerUp(MotionEvent motionEvent) {
        this.onSecondaryPointerUp(motionEvent);
        int n2 = this.mMotionX;
        int n3 = this.mMotionY;
        n2 = this.pointToPosition(n2, n3);
        if (n2 >= 0) {
            this.mMotionPosition = n2;
        }
        this.mLastY = n3;
        return true;
    }

    private boolean onTouchUp(MotionEvent motionEvent) {
        switch (this.mTouchMode) {
            default: {
                this.setPressed(false);
                this.invalidate();
                this.recycleVelocityTracker();
                this.mActivePointerId = -1;
                return true;
            }
            case 3: 
            case 4: 
            case 5: {
                return this.onTouchUpTap(motionEvent);
            }
            case 1: 
        }
        return this.onTouchUpScrolling(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean onTouchUpScrolling(MotionEvent motionEvent) {
        if (this.hasChildren()) {
            int n2 = this.getFirstChildTop();
            int n3 = this.getLastChildBottom();
            n2 = this.mFirstPosition == 0 && n2 >= this.getListPaddingTop() && this.mFirstPosition + this.getChildCount() < this.mItemCount && n3 <= this.getHeight() - this.getListPaddingBottom() ? 1 : 0;
            if (n2 == 0) {
                this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                float f2 = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                if (Math.abs(f2) > (float)this.mFlingVelocity) {
                    this.startFlingRunnable(f2);
                    this.mTouchMode = 2;
                    this.mMotionY = 0;
                    this.invalidate();
                    return true;
                }
            }
        }
        this.stopFlingRunnable();
        this.recycleVelocityTracker();
        this.mTouchMode = 0;
        return true;
    }

    private boolean onTouchUpTap(MotionEvent object) {
        if (this.mPerformClick == null) {
            this.invalidate();
            this.mPerformClick = new PerformClick();
        }
        int n2 = this.mMotionPosition;
        if (!this.mDataChanged && n2 >= 0 && this.mAdapter.isEnabled(n2)) {
            object = this.mPerformClick;
            object.mClickMotionPosition = n2;
            object.rememberWindowAttachCount();
            object.run();
        }
        return true;
    }

    private void postOnAnimate(Runnable runnable) {
        ViewCompat.postOnAnimation((View)this, runnable);
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void removeFixedViewInfo(View view, ArrayList<FixedViewInfo> arrayList) {
        int n2 = arrayList.size();
        int n3 = 0;
        while (n3 < n2) {
            if (arrayList.get((int)n3).view == view) {
                arrayList.remove(n3);
                return;
            }
            ++n3;
        }
    }

    static View retrieveFromScrap(ArrayList<View> arrayList, int n2) {
        int n3 = arrayList.size();
        if (n3 > 0) {
            for (int i2 = 0; i2 < n3; ++i2) {
                View view = arrayList.get(i2);
                if (((LayoutParams)view.getLayoutParams()).position != n2) continue;
                arrayList.remove(i2);
                return view;
            }
            return arrayList.remove(n3 - 1);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void scrollIfNeeded(int n2) {
        int n3 = n2 - this.mMotionY;
        int n4 = n3 - this.mMotionCorrection;
        int n5 = this.mLastY != Integer.MIN_VALUE ? n2 - this.mLastY : n4;
        if (this.mTouchMode == 1 && n2 != this.mLastY) {
            ViewParent viewParent;
            if (Math.abs(n3) > this.mTouchSlop && (viewParent = this.getParent()) != null) {
                viewParent.requestDisallowInterceptTouchEvent(true);
            }
            n3 = this.mMotionPosition >= 0 ? this.mMotionPosition - this.mFirstPosition : this.getChildCount() / 2;
            boolean bl2 = false;
            if (n5 != 0) {
                bl2 = this.moveTheChildren(n4, n5);
            }
            if (this.getChildAt(n3) != null) {
                if (bl2) {
                    this.mTouchMode = 5;
                }
                this.mMotionY = n2;
            }
            this.mLastY = n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setupChild(View view, int n2, int n3, boolean bl2, boolean bl3, boolean bl4) {
        int n4 = view.isSelected() ? 1 : 0;
        int n5 = this.mTouchMode;
        bl3 = n5 > 3 && n5 < 1 && this.mMotionPosition == n2;
        int n6 = bl3 != view.isPressed() ? 1 : 0;
        n5 = !bl4 || n4 != 0 || view.isLayoutRequested() ? 1 : 0;
        int n7 = this.mAdapter.getItemViewType(n2);
        LayoutParams layoutParams = n7 == -2 ? this.generateWrapperLayoutParams(view) : this.generateChildLayoutParams(view);
        layoutParams.viewType = n7;
        layoutParams.position = n2;
        if (bl4 || layoutParams.recycledHeaderFooter && layoutParams.viewType == -2) {
            n7 = bl2 ? -1 : 0;
            this.attachViewToParent(view, n7, (ViewGroup.LayoutParams)layoutParams);
        } else {
            if (layoutParams.viewType == -2) {
                layoutParams.recycledHeaderFooter = true;
            }
            n7 = bl2 ? -1 : 0;
            this.addViewInLayout(view, n7, (ViewGroup.LayoutParams)layoutParams, true);
        }
        if (n4 != 0) {
            view.setSelected(false);
        }
        if (n6 != 0) {
            view.setPressed(bl3);
        }
        if (n5 != 0) {
            this.onMeasureChild(view, layoutParams);
        } else {
            this.cleanupLayoutState(view);
        }
        n4 = view.getMeasuredWidth();
        n6 = view.getMeasuredHeight();
        if (!bl2) {
            n3 -= n6;
        }
        n7 = this.getChildLeft(n2);
        if (n5 != 0) {
            this.onLayoutChild(view, n2, bl2, n7, n3, n7 + n4, n3 + n6);
            return;
        }
        this.onOffsetChild(view, n2, bl2, n7, n3);
    }

    private void startFlingRunnable(float f2) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.start((int)(- f2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean startScrollIfNeeded(int n2) {
        int n3 = n2 - this.mMotionY;
        if (Math.abs(n3) <= this.mTouchSlop) {
            return false;
        }
        this.mTouchMode = 1;
        n3 = n3 > 0 ? this.mTouchSlop : - this.mTouchSlop;
        this.mMotionCorrection = n3;
        this.setPressed(false);
        View view = this.getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (view != null) {
            view.setPressed(false);
        }
        if ((view = this.getParent()) != null) {
            view.requestDisallowInterceptTouchEvent(true);
        }
        this.scrollIfNeeded(n2);
        return true;
    }

    private void stopFlingRunnable() {
        if (this.mFlingRunnable != null) {
            this.mFlingRunnable.endFling();
        }
    }

    public void addFooterView(View view) {
        this.addFooterView(view, null, true);
    }

    public void addFooterView(View view, Object object, boolean bl2) {
        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.data = object;
        fixedViewInfo.isSelectable = bl2;
        this.mFooterViewInfos.add(fixedViewInfo);
        if (this.mAdapter != null && this.mObserver != null) {
            this.mObserver.onChanged();
        }
    }

    public void addHeaderView(View view) {
        this.addHeaderView(view, null, true);
    }

    public void addHeaderView(View view, Object object, boolean bl2) {
        if (this.mAdapter != null && !(this.mAdapter instanceof HeaderViewListAdapter)) {
            throw new IllegalStateException("Cannot add header view to list -- setAdapter has already been called.");
        }
        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.data = object;
        fixedViewInfo.isSelectable = bl2;
        this.mHeaderViewInfos.add(fixedViewInfo);
        if (this.mAdapter != null && this.mObserver != null) {
            this.mObserver.onChanged();
        }
    }

    protected void adjustViewsAfterFillGap(boolean bl2) {
        if (bl2) {
            this.correctTooHigh(this.getChildCount());
            return;
        }
        this.correctTooLow(this.getChildCount());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void fillGap(boolean bl2) {
        int n2 = this.getChildCount();
        if (bl2) {
            n2 = this.mFirstPosition + n2;
            this.fillDown(n2, this.getChildTop(n2));
        } else {
            n2 = this.mFirstPosition - 1;
            this.fillUp(n2, this.getChildBottom(n2));
        }
        this.adjustViewsAfterFillGap(bl2);
    }

    protected LayoutParams generateChildLayoutParams(View view) {
        return this.generateWrapperLayoutParams(view);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2, 0);
    }

    protected LayoutParams generateHeaderFooterLayoutParams(View view) {
        return new LayoutParams(-1, -2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected LayoutParams generateWrapperLayoutParams(View object) {
        Object object2 = null;
        ViewGroup.LayoutParams layoutParams = object.getLayoutParams();
        object = object2;
        if (layoutParams != null) {
            object = layoutParams instanceof LayoutParams ? (LayoutParams)layoutParams : new LayoutParams(layoutParams);
        }
        object2 = object;
        if (object != null) return object2;
        return this.generateDefaultLayoutParams();
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    protected int getChildBottom(int n2) {
        int n3 = this.getChildCount();
        n2 = 0;
        if (this.mClipToPadding) {
            n2 = this.getListPaddingBottom();
        }
        if (n3 > 0) {
            return this.getChildAt(0).getTop();
        }
        return this.getHeight() - n2;
    }

    protected int getChildLeft(int n2) {
        return this.getListPaddingLeft();
    }

    protected int getChildTop(int n2) {
        int n3 = this.getChildCount();
        n2 = 0;
        if (this.mClipToPadding) {
            n2 = this.getListPaddingTop();
        }
        if (n3 > 0) {
            n2 = this.getChildAt(n3 - 1).getBottom();
        }
        return n2;
    }

    public int getCount() {
        return this.mItemCount;
    }

    protected int getFirstChildTop() {
        int n2 = 0;
        if (this.hasChildren()) {
            n2 = this.getChildAt(0).getTop();
        }
        return n2;
    }

    public int getFirstVisiblePosition() {
        return Math.max(0, this.mFirstPosition - this.getHeaderViewsCount());
    }

    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    protected int getHighestChildTop() {
        int n2 = 0;
        if (this.hasChildren()) {
            n2 = this.getChildAt(0).getTop();
        }
        return n2;
    }

    protected int getLastChildBottom() {
        if (this.hasChildren()) {
            return this.getChildAt(this.getChildCount() - 1).getBottom();
        }
        return 0;
    }

    public int getLastVisiblePosition() {
        return Math.min(this.mFirstPosition + this.getChildCount() - 1, this.mAdapter.getCount() - 1);
    }

    protected int getLowestChildBottom() {
        if (this.hasChildren()) {
            return this.getChildAt(this.getChildCount() - 1).getBottom();
        }
        return 0;
    }

    protected int getNextChildDownsTop(int n2) {
        n2 = this.getChildCount();
        if (n2 > 0) {
            return this.getChildAt(n2 - 1).getBottom();
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int getNextChildUpsBottom(int n2) {
        n2 = this.getChildCount();
        if (n2 == 0 || n2 <= 0) {
            return 0;
        }
        return this.getChildAt(0).getTop();
    }

    public View getSelectedView() {
        return null;
    }

    protected void handleDataChanged() {
        super.handleDataChanged();
        int n2 = this.mItemCount;
        if (n2 > 0 && this.mNeedSync) {
            this.mNeedSync = false;
            this.mSyncState = null;
            this.mLayoutMode = 2;
            this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), n2 - 1);
            if (this.mSyncPosition == 0) {
                this.mLayoutMode = 1;
            }
            return;
        }
        this.mLayoutMode = 1;
        this.mNeedSync = false;
        this.mSyncState = null;
    }

    protected boolean hasChildren() {
        if (this.getChildCount() > 0) {
            return true;
        }
        return false;
    }

    protected boolean hasSpaceDown() {
        return false;
    }

    protected boolean hasSpaceUp() {
        return false;
    }

    void invokeOnItemScrollListener() {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll((AbsListView)this, this.mFirstPosition, this.getChildCount(), this.mItemCount);
        }
    }

    /*
     * Exception decompiling
     */
    protected void layoutChildren() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:366)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    public void notifyTouchMode() {
        switch (this.mTouchMode) {
            default: {
                return;
            }
            case 1: {
                this.reportScrollStateChange(1);
                return;
            }
            case 2: {
                this.reportScrollStateChange(2);
                return;
            }
            case 0: 
        }
        this.reportScrollStateChange(0);
    }

    protected void offsetChildrenTopAndBottom(int n2) {
        int n3 = this.getChildCount();
        for (int i2 = 0; i2 < n3; ++i2) {
            this.getChildAt(i2).offsetTopAndBottom(n2);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mAdapter != null) {
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
        this.mIsAttached = true;
    }

    protected void onChildCreated(int n2, boolean bl2) {
    }

    protected void onChildrenDetached(int n2, int n3) {
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRecycleBin.clear();
        if (this.mFlingRunnable != null) {
            this.removeCallbacks((Runnable)this.mFlingRunnable);
        }
        this.mIsAttached = false;
    }

    protected void onFocusChanged(boolean bl2, int n2, Rect rect) {
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n2 = motionEvent.getAction();
        if (!this.mIsAttached) return false;
        switch (n2 & 255) {
            default: {
                return false;
            }
            case 0: {
                n2 = this.mTouchMode;
                int n3 = (int)motionEvent.getX();
                int n4 = (int)motionEvent.getY();
                this.mActivePointerId = motionEvent.getPointerId(0);
                int n5 = this.findMotionRow(n4);
                if (n2 != 2 && n5 >= 0) {
                    this.mMotionX = n3;
                    this.mMotionY = n4;
                    this.mMotionPosition = n5;
                    this.mTouchMode = 3;
                }
                this.mLastY = Integer.MIN_VALUE;
                this.initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(motionEvent);
                if (n2 != 2) return false;
                return true;
            }
            case 2: {
                int n6;
                switch (this.mTouchMode) {
                    default: {
                        return false;
                    }
                    case 3: 
                }
                n2 = n6 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (n6 == -1) {
                    n2 = 0;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                }
                n2 = (int)motionEvent.getY(n2);
                this.initVelocityTrackerIfNotExists();
                this.mVelocityTracker.addMovement(motionEvent);
                if (this.startScrollIfNeeded(n2)) return true;
                return false;
            }
            case 1: 
            case 3: {
                this.mTouchMode = 0;
                this.mActivePointerId = -1;
                this.recycleVelocityTracker();
                this.reportScrollStateChange(0);
                return false;
            }
            case 6: 
        }
        this.onSecondaryPointerUp(motionEvent);
        return false;
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        if (this.mAdapter == null) {
            return;
        }
        if (bl2) {
            n3 = this.getChildCount();
            for (n2 = 0; n2 < n3; ++n2) {
                this.getChildAt(n2).forceLayout();
            }
            this.mRecycleBin.markChildrenDirty();
        }
        this.mInLayout = true;
        this.layoutChildren();
        this.mInLayout = false;
    }

    protected void onLayoutChild(View view, int n2, boolean bl2, int n3, int n4, int n5, int n6) {
        view.layout(n3, n4, n5, n6);
    }

    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        this.setMeasuredDimension(View.MeasureSpec.getSize((int)n2), View.MeasureSpec.getSize((int)n3));
        this.mWidthMeasureSpec = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasureChild(View view, LayoutParams layoutParams) {
        int n2 = ViewGroup.getChildMeasureSpec((int)this.mWidthMeasureSpec, (int)(this.getListPaddingLeft() + this.getListPaddingRight()), (int)layoutParams.width);
        int n3 = layoutParams.height;
        n3 = n3 > 0 ? View.MeasureSpec.makeMeasureSpec((int)n3, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        view.measure(n2, n3);
    }

    protected void onOffsetChild(View view, int n2, boolean bl2, int n3, int n4) {
        view.offsetLeftAndRight(n3 - view.getLeft());
        view.offsetTopAndBottom(n4 - view.getTop());
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (ListSavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = parcelable.height;
        if (parcelable.firstId >= 0) {
            this.mNeedSync = true;
            this.mSyncState = parcelable;
            this.mSyncRowId = parcelable.firstId;
            this.mSyncPosition = parcelable.position;
            this.mSpecificTop = parcelable.viewTop;
        }
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Parcelable onSaveInstanceState() {
        ListSavedState listSavedState = new ListSavedState(super.onSaveInstanceState());
        if (this.mSyncState != null) {
            listSavedState.selectedId = this.mSyncState.selectedId;
            listSavedState.firstId = this.mSyncState.firstId;
            listSavedState.viewTop = this.mSyncState.viewTop;
            listSavedState.position = this.mSyncState.position;
            listSavedState.height = this.mSyncState.height;
            return listSavedState;
        }
        int n2 = this.getChildCount() > 0 && this.mItemCount > 0 ? 1 : 0;
        listSavedState.selectedId = this.getSelectedItemId();
        listSavedState.height = this.getHeight();
        if (n2 != 0 && this.mFirstPosition > 0) {
            int n3;
            listSavedState.viewTop = this.getChildAt(0).getTop();
            n2 = n3 = this.mFirstPosition;
            if (n3 >= this.mItemCount) {
                n2 = this.mItemCount - 1;
            }
            listSavedState.position = n2;
            listSavedState.firstId = this.mAdapter.getItemId(n2);
            return listSavedState;
        }
        listSavedState.viewTop = 0;
        listSavedState.firstId = -1;
        listSavedState.position = 0;
        return listSavedState;
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        if (this.getChildCount() > 0) {
            this.mDataChanged = true;
            this.rememberSyncState();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl2 = false;
        if (!this.isEnabled()) {
            if (this.isClickable()) return true;
            if (!this.isLongClickable()) return bl2;
            return true;
        }
        this.initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(motionEvent);
        if (!this.hasChildren()) return bl2;
        switch (motionEvent.getAction() & 255) {
            default: {
                bl2 = this.onTouchUp(motionEvent);
                break;
            }
            case 0: {
                bl2 = this.onTouchDown(motionEvent);
                break;
            }
            case 2: {
                bl2 = this.onTouchMove(motionEvent);
                break;
            }
            case 3: {
                bl2 = this.onTouchCancel(motionEvent);
                break;
            }
            case 6: {
                bl2 = this.onTouchPointerUp(motionEvent);
            }
        }
        this.notifyTouchMode();
        return bl2;
    }

    public void onWindowFocusChanged(boolean bl2) {
    }

    /*
     * Enabled aggressive block sorting
     */
    void rememberSyncState() {
        if (this.getChildCount() > 0) {
            this.mNeedSync = true;
            this.mSyncHeight = this.getHeight();
            View view = this.getChildAt(0);
            ListAdapter listAdapter = this.getAdapter();
            this.mSyncRowId = this.mFirstPosition >= 0 && this.mFirstPosition < listAdapter.getCount() ? listAdapter.getItemId(this.mFirstPosition) : -1;
            if (view != null) {
                this.mSpecificTop = view.getTop();
            }
            this.mSyncPosition = this.mFirstPosition;
        }
    }

    public boolean removeFooterView(View view) {
        if (this.mFooterViewInfos.size() > 0) {
            boolean bl2;
            boolean bl3 = bl2 = false;
            if (this.mAdapter != null) {
                bl3 = bl2;
                if (((HeaderViewListAdapter)this.mAdapter).removeFooter(view)) {
                    if (this.mObserver != null) {
                        this.mObserver.onChanged();
                    }
                    bl3 = true;
                }
            }
            this.removeFixedViewInfo(view, this.mFooterViewInfos);
            return bl3;
        }
        return false;
    }

    public boolean removeHeaderView(View view) {
        if (this.mHeaderViewInfos.size() > 0) {
            boolean bl2;
            boolean bl3 = bl2 = false;
            if (this.mAdapter != null) {
                bl3 = bl2;
                if (((HeaderViewListAdapter)this.mAdapter).removeHeader(view)) {
                    if (this.mObserver != null) {
                        this.mObserver.onChanged();
                    }
                    bl3 = true;
                }
            }
            this.removeFixedViewInfo(view, this.mHeaderViewInfos);
            return bl3;
        }
        return false;
    }

    void reportScrollStateChange(int n2) {
        if (n2 != this.mTouchMode) {
            this.mTouchMode = n2;
            if (this.mOnScrollListener != null) {
                this.mOnScrollListener.onScrollStateChanged((AbsListView)this, n2);
            }
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean bl2) {
        if (bl2) {
            this.recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(bl2);
    }

    public void requestLayout() {
        if (!this.mBlockLayoutRequests && !this.mInLayout) {
            super.requestLayout();
        }
    }

    public void resetToTop() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(ListAdapter object) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver((DataSetObserver)this.mObserver);
        }
        this.mAdapter = this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0 ? new HeaderViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, (ListAdapter)object) : object;
        this.mDataChanged = true;
        int n2 = object != null ? object.getCount() : 0;
        this.mItemCount = n2;
        if (object != null) {
            object.registerDataSetObserver((DataSetObserver)this.mObserver);
            this.mRecycleBin.setViewTypeCount(object.getViewTypeCount());
        }
        this.requestLayout();
    }

    public void setClipToPadding(boolean bl2) {
        super.setClipToPadding(bl2);
        this.mClipToPadding = bl2;
    }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        super.setOnScrollListener(onScrollListener);
        this.mOnScrollListener = onScrollListener;
    }

    public void setSelection(int n2) {
        if (n2 >= 0) {
            this.mLayoutMode = 2;
            this.mSpecificTop = this.getListPaddingTop();
            this.mFirstPosition = 0;
            if (this.mNeedSync) {
                this.mSyncPosition = n2;
                this.mSyncRowId = this.mAdapter.getItemId(n2);
            }
            this.requestLayout();
        }
    }

    class AdapterDataSetObserver
    extends DataSetObserver {
        private Parcelable mInstanceState;

        AdapterDataSetObserver() {
            this.mInstanceState = null;
        }

        public void clearSavedState() {
            this.mInstanceState = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onChanged() {
            ExtendableListView.this.mDataChanged = true;
            ExtendableListView.this.mOldItemCount = ExtendableListView.this.mItemCount;
            ExtendableListView.this.mItemCount = ExtendableListView.this.getAdapter().getCount();
            ExtendableListView.this.mRecycleBin.clearTransientStateViews();
            if (ExtendableListView.this.getAdapter().hasStableIds() && this.mInstanceState != null && ExtendableListView.this.mOldItemCount == 0 && ExtendableListView.this.mItemCount > 0) {
                ExtendableListView.this.onRestoreInstanceState(this.mInstanceState);
                this.mInstanceState = null;
            } else {
                ExtendableListView.this.rememberSyncState();
            }
            ExtendableListView.this.requestLayout();
        }

        public void onInvalidated() {
            ExtendableListView.this.mDataChanged = true;
            if (ExtendableListView.this.getAdapter().hasStableIds()) {
                this.mInstanceState = ExtendableListView.this.onSaveInstanceState();
            }
            ExtendableListView.this.mOldItemCount = ExtendableListView.this.mItemCount;
            ExtendableListView.this.mItemCount = 0;
            ExtendableListView.this.mNeedSync = false;
            ExtendableListView.this.requestLayout();
        }
    }

    public class FixedViewInfo {
        public Object data;
        public boolean isSelectable;
        public View view;
    }

    private class FlingRunnable
    implements Runnable {
        private int mLastFlingY;
        private final Scroller mScroller;

        FlingRunnable() {
            this.mScroller = new Scroller(ExtendableListView.this.getContext());
        }

        private void endFling() {
            this.mLastFlingY = 0;
            ExtendableListView.this.mTouchMode = 0;
            ExtendableListView.this.reportScrollStateChange(0);
            ExtendableListView.this.removeCallbacks((Runnable)this);
            this.mScroller.forceFinished(true);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void run() {
            switch (ExtendableListView.this.mTouchMode) {
                default: {
                    return;
                }
                case 2: 
            }
            if (ExtendableListView.this.mItemCount == 0 || ExtendableListView.this.getChildCount() == 0) {
                this.endFling();
                return;
            }
            Scroller scroller = this.mScroller;
            boolean bl2 = scroller.computeScrollOffset();
            int n2 = scroller.getCurrY();
            int n3 = this.mLastFlingY - n2;
            if (n3 > 0) {
                ExtendableListView.this.mMotionPosition = ExtendableListView.this.mFirstPosition;
                n3 = Math.min(ExtendableListView.this.getHeight() - ExtendableListView.this.getPaddingBottom() - ExtendableListView.this.getPaddingTop() - 1, n3);
            } else {
                int n4 = ExtendableListView.this.getChildCount();
                ExtendableListView.this.mMotionPosition = ExtendableListView.this.mFirstPosition + (n4 - 1);
                n3 = Math.max(- ExtendableListView.this.getHeight() - ExtendableListView.this.getPaddingBottom() - ExtendableListView.this.getPaddingTop() - 1, n3);
            }
            boolean bl3 = ExtendableListView.this.moveTheChildren(n3, n3);
            if (bl2 && !bl3) {
                ExtendableListView.this.invalidate();
                this.mLastFlingY = n2;
                ExtendableListView.this.postOnAnimate(this);
                return;
            }
            this.endFling();
        }

        /*
         * Enabled aggressive block sorting
         */
        void start(int n2) {
            int n3 = n2 < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = n3;
            this.mScroller.forceFinished(true);
            this.mScroller.fling(0, n3, 0, n2, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            ExtendableListView.this.mTouchMode = 2;
            ExtendableListView.this.postOnAnimate(this);
        }

        /*
         * Enabled aggressive block sorting
         */
        void startScroll(int n2, int n3) {
            int n4 = n2 < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = n4;
            this.mScroller.startScroll(0, n4, 0, n2, n3);
            ExtendableListView.this.mTouchMode = 2;
            ExtendableListView.this.postOnAnimate(this);
        }
    }

    public static class LayoutParams
    extends AbsListView.LayoutParams {
        long itemId = -1;
        int position;
        boolean recycledHeaderFooter;
        int viewType;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(int n2, int n3, int n4) {
            super(n2, n3);
            this.viewType = n4;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public static class ListSavedState
    extends ClassLoaderSavedState {
        public static final Parcelable.Creator<ListSavedState> CREATOR = new Parcelable.Creator<ListSavedState>(){

            public ListSavedState createFromParcel(Parcel parcel) {
                return new ListSavedState(parcel);
            }

            public ListSavedState[] newArray(int n2) {
                return new ListSavedState[n2];
            }
        };
        protected long firstId;
        protected int height;
        protected int position;
        protected long selectedId;
        protected int viewTop;

        public ListSavedState(Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.firstId = parcel.readLong();
            this.viewTop = parcel.readInt();
            this.position = parcel.readInt();
            this.height = parcel.readInt();
        }

        public ListSavedState(Parcelable parcelable) {
            super(parcelable, AbsListView.class.getClassLoader());
        }

        public String toString() {
            return "ExtendableListView.ListSavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewTop=" + this.viewTop + " position=" + this.position + " height=" + this.height + "}";
        }

        @Override
        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeLong(this.selectedId);
            parcel.writeLong(this.firstId);
            parcel.writeInt(this.viewTop);
            parcel.writeInt(this.position);
            parcel.writeInt(this.height);
        }

    }

    private class PerformClick
    extends WindowRunnnable
    implements Runnable {
        int mClickMotionPosition;

        private PerformClick() {
            super();
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            if (ExtendableListView.this.mDataChanged) {
                return;
            }
            ListAdapter listAdapter = ExtendableListView.this.mAdapter;
            int n2 = this.mClickMotionPosition;
            if (listAdapter == null) return;
            if (ExtendableListView.this.mItemCount <= 0) return;
            if (n2 == -1) return;
            if (n2 >= listAdapter.getCount()) return;
            if (!this.sameWindow()) return;
            View view = ExtendableListView.this.getChildAt(n2);
            if (view == null) return;
            ExtendableListView.this.performItemClick(view, ExtendableListView.this.mFirstPosition + n2, listAdapter.getItemId(n2));
        }
    }

    class RecycleBin {
        private View[] mActiveViews;
        private ArrayList<View> mCurrentScrap;
        private int mFirstActivePosition;
        private ArrayList<View>[] mScrapViews;
        private ArrayList<View> mSkippedScrap;
        private SparseArrayCompat<View> mTransientStateViews;
        private int mViewTypeCount;

        RecycleBin() {
            this.mActiveViews = new View[0];
        }

        private void pruneScrapViews() {
            int n2;
            int n3;
            int n4 = this.mActiveViews.length;
            int n5 = this.mViewTypeCount;
            ArrayList<View>[] arrarrayList = this.mScrapViews;
            for (n2 = 0; n2 < n5; ++n2) {
                ArrayList<View> arrayList = arrarrayList[n2];
                int n6 = arrayList.size();
                int n7 = 0;
                n3 = n6 - 1;
                while (n7 < n6 - n4) {
                    ExtendableListView.this.removeDetachedView(arrayList.remove(n3), false);
                    ++n7;
                    --n3;
                }
            }
            if (this.mTransientStateViews != null) {
                n2 = 0;
                while (n2 < this.mTransientStateViews.size()) {
                    n3 = n2;
                    if (!ViewCompat.hasTransientState(this.mTransientStateViews.valueAt(n2))) {
                        this.mTransientStateViews.removeAt(n2);
                        n3 = n2 - 1;
                    }
                    n2 = n3 + 1;
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        void addScrapView(View view, int n2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            layoutParams.position = n2;
            int n3 = layoutParams.viewType;
            boolean bl2 = ViewCompat.hasTransientState(view);
            if (!this.shouldRecycleViewType(n3) || bl2) {
                if (n3 != -2 || bl2) {
                    if (this.mSkippedScrap == null) {
                        this.mSkippedScrap = new ArrayList();
                    }
                    this.mSkippedScrap.add(view);
                }
                if (!bl2) return;
                if (this.mTransientStateViews == null) {
                    this.mTransientStateViews = new SparseArrayCompat();
                }
                this.mTransientStateViews.put(n2, view);
                return;
            }
            if (this.mViewTypeCount == 1) {
                this.mCurrentScrap.add(view);
                return;
            }
            this.mScrapViews[n3].add(view);
        }

        void clear() {
            if (this.mViewTypeCount == 1) {
                ArrayList<View> arrayList = this.mCurrentScrap;
                int n2 = arrayList.size();
                for (int i2 = 0; i2 < n2; ++i2) {
                    ExtendableListView.this.removeDetachedView(arrayList.remove(n2 - 1 - i2), false);
                }
            } else {
                int n3 = this.mViewTypeCount;
                for (int i3 = 0; i3 < n3; ++i3) {
                    ArrayList<View> arrayList = this.mScrapViews[i3];
                    int n4 = arrayList.size();
                    for (int i4 = 0; i4 < n4; ++i4) {
                        ExtendableListView.this.removeDetachedView(arrayList.remove(n4 - 1 - i4), false);
                    }
                }
            }
            if (this.mTransientStateViews != null) {
                this.mTransientStateViews.clear();
            }
        }

        void clearTransientStateViews() {
            if (this.mTransientStateViews != null) {
                this.mTransientStateViews.clear();
            }
        }

        void fillActiveViews(int n2, int n3) {
            if (this.mActiveViews.length < n2) {
                this.mActiveViews = new View[n2];
            }
            this.mFirstActivePosition = n3;
            View[] arrview = this.mActiveViews;
            for (n3 = 0; n3 < n2; ++n3) {
                View view = ExtendableListView.this.getChildAt(n3);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams == null || layoutParams.viewType == -2) continue;
                arrview[n3] = view;
            }
        }

        View getActiveView(int n2) {
            View[] arrview = this.mActiveViews;
            if ((n2 -= this.mFirstActivePosition) >= 0 && n2 < arrview.length) {
                View view = arrview[n2];
                arrview[n2] = null;
                return view;
            }
            return null;
        }

        View getScrapView(int n2) {
            if (this.mViewTypeCount == 1) {
                return ExtendableListView.retrieveFromScrap(this.mCurrentScrap, n2);
            }
            int n3 = ExtendableListView.this.mAdapter.getItemViewType(n2);
            if (n3 >= 0 && n3 < this.mScrapViews.length) {
                return ExtendableListView.retrieveFromScrap(this.mScrapViews[n3], n2);
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         */
        View getTransientStateView(int n2) {
            if (this.mTransientStateViews == null || (n2 = this.mTransientStateViews.indexOfKey(n2)) < 0) {
                return null;
            }
            View view = this.mTransientStateViews.valueAt(n2);
            this.mTransientStateViews.removeAt(n2);
            return view;
        }

        public void markChildrenDirty() {
            int n2;
            int n3;
            ArrayList<View> arrayList;
            if (this.mViewTypeCount == 1) {
                arrayList = this.mCurrentScrap;
                n3 = arrayList.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    arrayList.get(n2).forceLayout();
                }
            } else {
                int n4 = this.mViewTypeCount;
                for (n2 = 0; n2 < n4; ++n2) {
                    arrayList = this.mScrapViews[n2];
                    int n5 = arrayList.size();
                    for (n3 = 0; n3 < n5; ++n3) {
                        arrayList.get(n3).forceLayout();
                    }
                }
            }
            if (this.mTransientStateViews != null) {
                n3 = this.mTransientStateViews.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    this.mTransientStateViews.valueAt(n2).forceLayout();
                }
            }
        }

        void removeSkippedScrap() {
            if (this.mSkippedScrap == null) {
                return;
            }
            int n2 = this.mSkippedScrap.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                ExtendableListView.this.removeDetachedView(this.mSkippedScrap.get(i2), false);
            }
            this.mSkippedScrap.clear();
        }

        /*
         * Enabled aggressive block sorting
         */
        void scrapActiveViews() {
            boolean bl2 = true;
            View[] arrview = this.mActiveViews;
            if (this.mViewTypeCount <= 1) {
                bl2 = false;
            }
            ArrayList<View> arrayList = this.mCurrentScrap;
            int n2 = arrview.length - 1;
            do {
                if (n2 < 0) {
                    this.pruneScrapViews();
                    return;
                }
                View view = arrview[n2];
                ArrayList<View> arrayList2 = arrayList;
                if (view != null) {
                    arrayList2 = (LayoutParams)view.getLayoutParams();
                    arrview[n2] = null;
                    boolean bl3 = ViewCompat.hasTransientState(view);
                    int n3 = arrayList2.viewType;
                    if (!this.shouldRecycleViewType(n3) || bl3) {
                        if (n3 != -2 || bl3) {
                            ExtendableListView.this.removeDetachedView(view, false);
                        }
                        arrayList2 = arrayList;
                        if (bl3) {
                            if (this.mTransientStateViews == null) {
                                this.mTransientStateViews = new SparseArrayCompat();
                            }
                            this.mTransientStateViews.put(this.mFirstActivePosition + n2, view);
                            arrayList2 = arrayList;
                        }
                    } else {
                        if (bl2) {
                            arrayList = this.mScrapViews[n3];
                        }
                        arrayList2.position = this.mFirstActivePosition + n2;
                        arrayList.add(view);
                        arrayList2 = arrayList;
                    }
                }
                --n2;
                arrayList = arrayList2;
            } while (true);
        }

        void setCacheColorHint(int n2) {
            Object object;
            int n3;
            int n4;
            if (this.mViewTypeCount == 1) {
                object = this.mCurrentScrap;
                n4 = object.size();
                for (n3 = 0; n3 < n4; ++n3) {
                    ((View)object.get(n3)).setDrawingCacheBackgroundColor(n2);
                }
            } else {
                int n5 = this.mViewTypeCount;
                for (n3 = 0; n3 < n5; ++n3) {
                    object = this.mScrapViews[n3];
                    int n6 = object.size();
                    for (n4 = 0; n4 < n6; ++n4) {
                        ((View)object.get(n4)).setDrawingCacheBackgroundColor(n2);
                    }
                }
            }
            for (View view : this.mActiveViews) {
                if (view == null) continue;
                view.setDrawingCacheBackgroundColor(n2);
            }
        }

        public void setViewTypeCount(int n2) {
            if (n2 < 1) {
                throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
            }
            ArrayList[] arrarrayList = new ArrayList[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                arrarrayList[i2] = new ArrayList();
            }
            this.mViewTypeCount = n2;
            this.mCurrentScrap = arrarrayList[0];
            this.mScrapViews = arrarrayList;
        }

        public boolean shouldRecycleViewType(int n2) {
            if (n2 >= 0) {
                return true;
            }
            return false;
        }
    }

    private class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {
        }

        public void rememberWindowAttachCount() {
            this.mOriginalAttachCount = ExtendableListView.this.getWindowAttachCount();
        }

        public boolean sameWindow() {
            if (ExtendableListView.this.hasWindowFocus() && ExtendableListView.this.getWindowAttachCount() == this.mOriginalAttachCount) {
                return true;
            }
            return false;
        }
    }

}

