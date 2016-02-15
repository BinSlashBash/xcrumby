/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.TransitionDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseBooleanArray
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SoundEffectConstants
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnTouchModeChangeListener
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$AdapterContextMenuInfo
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.Checkable
 *  android.widget.ListAdapter
 *  android.widget.Scroller
 */
package twowayview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.Scroller;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.lucasr.twowayview.R;

public class TwoWayView
extends AdapterView<ListAdapter>
implements ViewTreeObserver.OnTouchModeChangeListener {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    private static final int INVALID_POINTER = -1;
    private static final int LAYOUT_FORCE_BOTTOM = 3;
    private static final int LAYOUT_FORCE_TOP = 1;
    private static final int LAYOUT_MOVE_SELECTION = 6;
    private static final int LAYOUT_NORMAL = 0;
    private static final int LAYOUT_SET_SELECTION = 2;
    private static final int LAYOUT_SPECIFIC = 4;
    private static final int LAYOUT_SYNC = 5;
    private static final String LOGTAG = "TwoWayView";
    private static final float MAX_SCROLL_FACTOR = 0.33f;
    private static final int MIN_SCROLL_PREVIEW_PIXELS = 10;
    private static final int NO_POSITION = -1;
    public static final int[] STATE_NOTHING = new int[]{0};
    private static final int SYNC_FIRST_POSITION = 1;
    private static final int SYNC_MAX_DURATION_MILLIS = 100;
    private static final int SYNC_SELECTED_POSITION = 0;
    private static final int TOUCH_MODE_DONE_WAITING = 2;
    private static final int TOUCH_MODE_DOWN = 0;
    private static final int TOUCH_MODE_DRAGGING = 3;
    private static final int TOUCH_MODE_FLINGING = 4;
    private static final int TOUCH_MODE_OFF = 1;
    private static final int TOUCH_MODE_ON = 0;
    private static final int TOUCH_MODE_OVERSCROLL = 5;
    private static final int TOUCH_MODE_REST = -1;
    private static final int TOUCH_MODE_TAP = 1;
    private static final int TOUCH_MODE_UNKNOWN = -1;
    private ListItemAccessibilityDelegate mAccessibilityDelegate;
    private int mActivePointerId;
    private ListAdapter mAdapter;
    private boolean mAreAllItemsSelectable;
    private final ArrowScrollFocusResult mArrowScrollFocusResult;
    private boolean mBlockLayoutRequests;
    private SparseBooleanArray mCheckStates;
    LongSparseArray<Integer> mCheckedIdStates;
    private int mCheckedItemCount;
    private ChoiceMode mChoiceMode;
    private ContextMenu.ContextMenuInfo mContextMenuInfo = null;
    private boolean mDataChanged;
    private AdapterDataSetObserver mDataSetObserver;
    private boolean mDesiredFocusableInTouchModeState;
    private boolean mDesiredFocusableState;
    private boolean mDrawSelectorOnTop;
    private View mEmptyView;
    private EdgeEffectCompat mEndEdge;
    private int mFirstPosition;
    private final int mFlingVelocity;
    private boolean mHasStableIds;
    private boolean mInLayout;
    private boolean mIsAttached = false;
    private boolean mIsChildViewEnabled;
    final boolean[] mIsScrap = new boolean[1];
    private boolean mIsVertical;
    private int mItemCount;
    private int mItemMargin;
    private boolean mItemsCanFocus;
    private int mLastAccessibilityScrollEventFromIndex;
    private int mLastAccessibilityScrollEventToIndex;
    private int mLastScrollState = 0;
    private int mLastTouchMode = -1;
    private float mLastTouchPos;
    private int mLayoutMode = 0;
    private final int mMaximumVelocity;
    private int mMotionPosition;
    private boolean mNeedSync = false;
    private int mNextSelectedPosition;
    private long mNextSelectedRowId;
    private int mOldItemCount;
    private int mOldSelectedPosition;
    private long mOldSelectedRowId;
    private OnScrollListener mOnScrollListener = null;
    private int mOverScroll;
    private final int mOverscrollDistance;
    private CheckForKeyLongPress mPendingCheckForKeyLongPress;
    private CheckForLongPress mPendingCheckForLongPress;
    private CheckForTap mPendingCheckForTap;
    private SavedState mPendingSync;
    private PerformClick mPerformClick;
    private final RecycleBin mRecycler;
    private int mResurrectToPosition;
    private final Scroller mScroller;
    private int mSelectedPosition;
    private long mSelectedRowId;
    private int mSelectedStart;
    private SelectionNotifier mSelectionNotifier;
    private Drawable mSelector;
    private int mSelectorPosition;
    private final Rect mSelectorRect;
    private int mSpecificStart;
    private EdgeEffectCompat mStartEdge;
    private long mSyncHeight;
    private int mSyncMode;
    private int mSyncPosition;
    private long mSyncRowId;
    private final Rect mTempRect;
    private Rect mTouchFrame;
    private int mTouchMode = -1;
    private Runnable mTouchModeReset;
    private float mTouchRemainderPos;
    private final int mTouchSlop;
    private VelocityTracker mVelocityTracker = null;

    public TwoWayView(Context context) {
        this(context, null);
    }

    public TwoWayView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoWayView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mOverscrollDistance = this.getScaledOverscrollDistance(viewConfiguration);
        this.mOverScroll = 0;
        this.mScroller = new Scroller(context);
        this.mIsVertical = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.mArrowScrollFocusResult = new ArrowScrollFocusResult();
        this.mSelectorPosition = -1;
        this.mSelectorRect = new Rect();
        this.mSelectedStart = 0;
        this.mResurrectToPosition = -1;
        this.mSelectedStart = 0;
        this.mNextSelectedPosition = -1;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mSelectedPosition = -1;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        this.mChoiceMode = ChoiceMode.NONE;
        this.mCheckedItemCount = 0;
        this.mCheckedIdStates = null;
        this.mCheckStates = null;
        this.mRecycler = new RecycleBin();
        this.mDataSetObserver = null;
        this.mAreAllItemsSelectable = true;
        this.mStartEdge = null;
        this.mEndEdge = null;
        this.setClickable(true);
        this.setFocusableInTouchMode(true);
        this.setWillNotDraw(false);
        this.setAlwaysDrawnWithCacheEnabled(false);
        this.setWillNotDraw(false);
        this.setClipToPadding(false);
        ViewCompat.setOverScrollMode((View)this, 1);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.TwoWayView, n2, 0);
        this.mDrawSelectorOnTop = context.getBoolean(R.styleable.TwoWayView_android_drawSelectorOnTop, false);
        attributeSet = context.getDrawable(R.styleable.TwoWayView_android_listSelector);
        if (attributeSet != null) {
            this.setSelector((Drawable)attributeSet);
        }
        if ((n2 = context.getInt(R.styleable.TwoWayView_android_orientation, -1)) >= 0) {
            this.setOrientation(Orientation.values()[n2]);
        }
        if ((n2 = context.getInt(R.styleable.TwoWayView_android_choiceMode, -1)) >= 0) {
            this.setChoiceMode(ChoiceMode.values()[n2]);
        }
        context.recycle();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void adjustViewsStartOrEnd() {
        if (this.getChildCount() == 0) {
            return;
        }
        View view = this.getChildAt(0);
        int n2 = this.mIsVertical ? view.getTop() - this.getPaddingTop() - this.mItemMargin : view.getLeft() - this.getPaddingLeft() - this.mItemMargin;
        int n3 = n2;
        if (n2 < 0) {
            return;
        }
        if (n3 == 0) return;
        this.offsetChildren(- n3);
    }

    private int amountToScroll(int n2, int n3) {
        int n4;
        this.forceValidFocusDirection(n2);
        int n5 = this.getChildCount();
        if (n2 == 130 || n2 == 66) {
            int n6;
            int n7 = this.getEndEdge();
            int n8 = n5 - 1;
            if (n3 != -1) {
                n8 = n3 - this.mFirstPosition;
            }
            int n9 = this.mFirstPosition;
            View view = this.getChildAt(n8);
            n2 = n6 = n7;
            if (n9 + n8 < this.mItemCount - 1) {
                n2 = n6 - this.getArrowScrollPreviewLength();
            }
            n8 = this.getChildStartEdge(view);
            n6 = this.getChildEndEdge(view);
            if (n6 <= n2) {
                return 0;
            }
            if (n3 != -1 && n2 - n8 >= this.getMaxScrollAmount()) {
                return 0;
            }
            n2 = n3 = n6 - n2;
            if (this.mFirstPosition + n5 == this.mItemCount) {
                n2 = Math.min(n3, this.getChildEndEdge(this.getChildAt(n5 - 1)) - n7);
            }
            return Math.min(n2, this.getMaxScrollAmount());
        }
        int n10 = this.getStartEdge();
        int n11 = 0;
        if (n3 != -1) {
            n11 = n3 - this.mFirstPosition;
        }
        n5 = this.mFirstPosition;
        View view = this.getChildAt(n11);
        n2 = n4 = n10;
        if (n5 + n11 > 0) {
            n2 = n4 + this.getArrowScrollPreviewLength();
        }
        n11 = this.getChildStartEdge(view);
        n4 = this.getChildEndEdge(view);
        if (n11 >= n2) {
            return 0;
        }
        if (n3 != -1 && n4 - n2 >= this.getMaxScrollAmount()) {
            return 0;
        }
        n2 = n3 = n2 - n11;
        if (this.mFirstPosition == 0) {
            n2 = Math.min(n3, n10 - this.getChildStartEdge(this.getChildAt(0)));
        }
        return Math.min(n2, this.getMaxScrollAmount());
    }

    /*
     * Enabled aggressive block sorting
     */
    private int amountToScrollToNewFocus(int n2, View view, int n3) {
        this.forceValidFocusDirection(n2);
        int n4 = 0;
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        if (n2 == 33 || n2 == 17) {
            int n5 = this.getStartEdge();
            int n6 = this.mIsVertical ? this.mTempRect.top : this.mTempRect.left;
            n2 = n4;
            if (n6 >= n5) return n2;
            n2 = n6 = n5 - n6;
            if (n3 <= 0) return n2;
            return n6 + this.getArrowScrollPreviewLength();
        }
        int n7 = this.getEndEdge();
        int n8 = this.mIsVertical ? this.mTempRect.bottom : this.mTempRect.right;
        n2 = n4;
        if (n8 <= n7) return n2;
        n2 = n8 -= n7;
        if (n3 >= this.mItemCount - 1) return n2;
        return n8 + this.getArrowScrollPreviewLength();
    }

    private boolean arrowScroll(int n2) {
        boolean bl2;
        block4 : {
            this.forceValidFocusDirection(n2);
            this.mInLayout = true;
            bl2 = this.arrowScrollImpl(n2);
            if (!bl2) break block4;
            this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection((int)n2));
        }
        return bl2;
        finally {
            this.mInLayout = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private ArrowScrollFocusResult arrowScrollFocused(int n2) {
        int n3;
        int n4;
        this.forceValidFocusDirection(n2);
        View view = this.getSelectedView();
        if (view != null && view.hasFocus()) {
            view = view.findFocus();
            view = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view, n2);
        } else {
            if (n2 == 130 || n2 == 66) {
                n4 = this.getStartEdge();
                n3 = view != null ? (this.mIsVertical ? view.getTop() : view.getLeft()) : n4;
                n3 = Math.max(n3, n4);
            } else {
                n4 = this.getEndEdge();
                n3 = view != null ? this.getChildEndEdge(view) : n4;
                n3 = Math.min(n3, n4);
            }
            n4 = this.mIsVertical ? 0 : n3;
            if (!this.mIsVertical) {
                n3 = 0;
            }
            this.mTempRect.set(n4, n3, n4, n3);
            view = FocusFinder.getInstance().findNextFocusFromRect((ViewGroup)this, this.mTempRect, n2);
        }
        if (view != null) {
            int n5 = this.positionOfNewFocus(view);
            if (this.mSelectedPosition != -1 && n5 != this.mSelectedPosition) {
                int n6 = this.lookForSelectablePositionOnScreen(n2);
                n3 = n2 == 130 || n2 == 66 ? 1 : 0;
                n4 = n2 == 33 || n2 == 17 ? 1 : 0;
                if (n6 != -1 && (n3 != 0 && n6 < n5 || n4 != 0 && n6 > n5)) {
                    return null;
                }
            }
            if ((n3 = this.amountToScrollToNewFocus(n2, view, n5)) < (n4 = this.getMaxScrollAmount())) {
                view.requestFocus(n2);
                this.mArrowScrollFocusResult.populate(n5, n3);
                return this.mArrowScrollFocusResult;
            }
            if (this.distanceToView(view) < n4) {
                view.requestFocus(n2);
                this.mArrowScrollFocusResult.populate(n5, n4);
                return this.mArrowScrollFocusResult;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean arrowScrollImpl(int n2) {
        this.forceValidFocusDirection(n2);
        if (this.getChildCount() <= 0) {
            return false;
        }
        View view = this.getSelectedView();
        int n3 = this.mSelectedPosition;
        int n4 = this.lookForSelectablePositionOnScreen(n2);
        int n5 = this.amountToScroll(n2, n4);
        ArrowScrollFocusResult arrowScrollFocusResult = this.mItemsCanFocus ? this.arrowScrollFocused(n2) : null;
        if (arrowScrollFocusResult != null) {
            n4 = arrowScrollFocusResult.getSelectedPosition();
            n5 = arrowScrollFocusResult.getAmountToScroll();
        }
        int n6 = arrowScrollFocusResult != null ? 1 : 0;
        View view2 = view;
        if (n4 != -1) {
            boolean bl2 = arrowScrollFocusResult != null;
            this.handleNewSelectionChange(view, n2, n4, bl2);
            this.setSelectedPositionInt(n4);
            this.setNextSelectedPositionInt(n4);
            view2 = this.getSelectedView();
            n3 = n4;
            if (this.mItemsCanFocus && arrowScrollFocusResult == null && (view = this.getFocusedChild()) != null) {
                view.clearFocus();
            }
            n6 = 1;
            this.checkSelectionChanged();
        }
        if (n5 > 0) {
            n6 = n5;
            if (n2 != 33) {
                n6 = n2 == 17 ? n5 : - n5;
            }
            this.scrollListItemsBy(n6);
            n6 = 1;
        }
        if (this.mItemsCanFocus && arrowScrollFocusResult == null && view2 != null && view2.hasFocus() && (!this.isViewAncestorOf((View)(arrowScrollFocusResult = view2.findFocus()), (View)this) || this.distanceToView((View)arrowScrollFocusResult) > 0)) {
            arrowScrollFocusResult.clearFocus();
        }
        arrowScrollFocusResult = view2;
        if (n4 == -1) {
            arrowScrollFocusResult = view2;
            if (view2 != null) {
                arrowScrollFocusResult = view2;
                if (!this.isViewAncestorOf(view2, (View)this)) {
                    arrowScrollFocusResult = null;
                    this.hideSelector();
                    this.mResurrectToPosition = -1;
                }
            }
        }
        if (n6 == 0) return false;
        if (arrowScrollFocusResult != null) {
            this.positionSelector(n3, (View)arrowScrollFocusResult);
            this.mSelectedStart = arrowScrollFocusResult.getTop();
        }
        if (!this.awakenScrollbarsInternal()) {
            this.invalidate();
        }
        this.invokeOnItemScrollListener();
        return true;
    }

    @TargetApi(value=5)
    private boolean awakenScrollbarsInternal() {
        if (Build.VERSION.SDK_INT >= 5 && super.awakenScrollBars()) {
            return true;
        }
        return false;
    }

    private void cancelCheckForLongPress() {
        if (this.mPendingCheckForLongPress == null) {
            return;
        }
        this.removeCallbacks((Runnable)this.mPendingCheckForLongPress);
    }

    private void cancelCheckForTap() {
        if (this.mPendingCheckForTap == null) {
            return;
        }
        this.removeCallbacks((Runnable)this.mPendingCheckForTap);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void checkFocus() {
        boolean bl2 = true;
        ListAdapter listAdapter = this.getAdapter();
        boolean bl3 = listAdapter != null && listAdapter.getCount() > 0;
        boolean bl4 = bl3 && this.mDesiredFocusableInTouchModeState;
        super.setFocusableInTouchMode(bl4);
        bl4 = bl3 && this.mDesiredFocusableState ? bl2 : false;
        super.setFocusable(bl4);
        if (this.mEmptyView != null) {
            this.updateEmptyStatus();
        }
    }

    private void checkSelectionChanged() {
        if (this.mSelectedPosition != this.mOldSelectedPosition || this.mSelectedRowId != this.mOldSelectedRowId) {
            this.selectionChanged();
            this.mOldSelectedPosition = this.mSelectedPosition;
            this.mOldSelectedRowId = this.mSelectedRowId;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @TargetApi(value=14)
    private SparseBooleanArray cloneCheckStates() {
        if (this.mCheckStates == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            return this.mCheckStates.clone();
        }
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        int n2 = 0;
        do {
            SparseBooleanArray sparseBooleanArray2 = sparseBooleanArray;
            if (n2 >= this.mCheckStates.size()) return sparseBooleanArray2;
            sparseBooleanArray.put(this.mCheckStates.keyAt(n2), this.mCheckStates.valueAt(n2));
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean contentFits() {
        int n2 = this.getChildCount();
        if (n2 == 0) {
            return true;
        }
        if (n2 != this.mItemCount) {
            return false;
        }
        View view = this.getChildAt(0);
        View view2 = this.getChildAt(n2 - 1);
        if (this.getChildStartEdge(view) < this.getStartEdge()) return false;
        if (this.getChildEndEdge(view2) <= this.getEndEdge()) return true;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void correctTooHigh(int n2) {
        if (this.mFirstPosition + n2 - 1 != this.mItemCount - 1) return;
        if (n2 == 0) {
            return;
        }
        n2 = this.getChildEndEdge(this.getChildAt(n2 - 1));
        int n3 = this.getStartEdge();
        int n4 = this.getEndEdge() - n2;
        View view = this.getChildAt(0);
        int n5 = this.getChildStartEdge(view);
        if (n4 <= 0) return;
        if (this.mFirstPosition <= 0) {
            if (n5 >= n3) return;
        }
        n2 = n4;
        if (this.mFirstPosition == 0) {
            n2 = Math.min(n4, n3 - n5);
        }
        this.offsetChildren(n2);
        if (this.mFirstPosition <= 0) return;
        n2 = this.getChildStartEdge(view);
        this.fillBefore(this.mFirstPosition - 1, n2 - this.mItemMargin);
        this.adjustViewsStartOrEnd();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void correctTooLow(int n2) {
        if (this.mFirstPosition != 0 || n2 == 0) return;
        View view = this.getChildAt(0);
        int n3 = this.mIsVertical ? view.getTop() : view.getLeft();
        int n4 = this.getStartEdge();
        int n5 = this.getEndEdge();
        view = this.getChildAt(n2 - 1);
        int n6 = this.getChildEndEdge(view);
        n4 = this.mFirstPosition + n2 - 1;
        if ((n3 -= n4) <= 0) return;
        if (n4 < this.mItemCount - 1 || n6 > n5) {
            n2 = n3;
            if (n4 == this.mItemCount - 1) {
                n2 = Math.min(n3, n6 - n5);
            }
            this.offsetChildren(- n2);
            if (n4 >= this.mItemCount - 1) return;
            {
                n2 = this.getChildEndEdge(view);
                this.fillAfter(n4 + 1, this.mItemMargin + n2);
                this.adjustViewsStartOrEnd();
                return;
            }
        }
        if (n4 != this.mItemCount - 1) {
            return;
        }
        this.adjustViewsStartOrEnd();
    }

    private ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int n2, long l2) {
        return new AdapterView.AdapterContextMenuInfo(view, n2, l2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int distanceToView(View view) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        int n2 = this.getStartEdge();
        int n3 = this.getEndEdge();
        int n4 = this.mIsVertical ? this.mTempRect.top : this.mTempRect.left;
        int n5 = this.mIsVertical ? this.mTempRect.bottom : this.mTempRect.right;
        int n6 = 0;
        if (n5 < n2) {
            return n2 - n5;
        }
        n5 = n6;
        if (n4 <= n3) return n5;
        return n4 - n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean drawEndEdge(Canvas canvas) {
        if (this.mEndEdge.isFinished()) {
            return false;
        }
        int n2 = canvas.save();
        int n3 = this.getWidth();
        int n4 = this.getHeight();
        if (this.mIsVertical) {
            canvas.translate((float)(- n3), (float)n4);
            canvas.rotate(180.0f, (float)n3, 0.0f);
        } else {
            canvas.translate((float)n3, 0.0f);
            canvas.rotate(90.0f);
        }
        boolean bl2 = this.mEndEdge.draw(canvas);
        canvas.restoreToCount(n2);
        return bl2;
    }

    private void drawSelector(Canvas canvas) {
        if (!this.mSelectorRect.isEmpty()) {
            Drawable drawable2 = this.mSelector;
            drawable2.setBounds(this.mSelectorRect);
            drawable2.draw(canvas);
        }
    }

    private boolean drawStartEdge(Canvas canvas) {
        if (this.mStartEdge.isFinished()) {
            return false;
        }
        if (this.mIsVertical) {
            return this.mStartEdge.draw(canvas);
        }
        int n2 = canvas.save();
        canvas.translate(0.0f, (float)this.getHeight());
        canvas.rotate(270.0f);
        boolean bl2 = this.mStartEdge.draw(canvas);
        canvas.restoreToCount(n2);
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View fillAfter(int n2, int n3) {
        View view = null;
        int n4 = this.getEndEdge();
        while (n3 < n4 && n2 < this.mItemCount) {
            boolean bl2 = n2 == this.mSelectedPosition;
            View view2 = this.makeAndAddView(n2, n3, true, bl2);
            n3 = this.getChildEndEdge(view2) + this.mItemMargin;
            if (bl2) {
                view = view2;
            }
            ++n2;
        }
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View fillBefore(int n2, int n3) {
        View view = null;
        int n4 = this.getStartEdge();
        int n5 = n3;
        for (n3 = n2; n5 > n4 && n3 >= 0; --n3) {
            boolean bl2 = n3 == this.mSelectedPosition;
            View view2 = this.makeAndAddView(n3, n5, false, bl2);
            n2 = this.mIsVertical ? view2.getTop() - this.mItemMargin : view2.getLeft() - this.mItemMargin;
            if (bl2) {
                view = view2;
            }
            n5 = n2;
        }
        this.mFirstPosition = n3 + 1;
        return view;
    }

    private void fillBeforeAndAfter(View view, int n2) {
        this.fillBefore(n2 - 1, this.getChildStartEdge(view) + this.mItemMargin);
        this.adjustViewsStartOrEnd();
        this.fillAfter(n2 + 1, this.getChildEndEdge(view) + this.mItemMargin);
    }

    /*
     * Enabled aggressive block sorting
     */
    private View fillFromMiddle(int n2, int n3) {
        n3 -= n2;
        int n4 = this.reconcileSelectedPosition();
        View view = this.makeAndAddView(n4, n2, true, true);
        this.mFirstPosition = n4;
        if (this.mIsVertical) {
            n2 = view.getMeasuredHeight();
            if (n2 <= n3) {
                view.offsetTopAndBottom((n3 - n2) / 2);
            }
        } else {
            n2 = view.getMeasuredWidth();
            if (n2 <= n3) {
                view.offsetLeftAndRight((n3 - n2) / 2);
            }
        }
        this.fillBeforeAndAfter(view, n4);
        this.correctTooHigh(this.getChildCount());
        return view;
    }

    private View fillFromOffset(int n2) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return this.fillAfter(this.mFirstPosition, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private View fillFromSelection(int n2, int n3, int n4) {
        int n5 = this.mSelectedPosition;
        View view = this.makeAndAddView(n5, n2, true, true);
        n2 = this.getChildStartEdge(view);
        int n6 = this.getChildEndEdge(view);
        if (n6 > n4) {
            view.offsetTopAndBottom(- Math.min(n2 - n3, n6 - n4));
        } else if (n2 < n3) {
            view.offsetTopAndBottom(Math.min(n3 - n2, n4 - n6));
        }
        this.fillBeforeAndAfter(view, n5);
        this.correctTooHigh(this.getChildCount());
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View fillSpecific(int n2, int n3) {
        boolean bl2 = n2 == this.mSelectedPosition;
        View view = this.makeAndAddView(n2, n3, true, bl2);
        this.mFirstPosition = n2;
        View view2 = this.fillBefore(n2 - 1, this.getChildStartEdge(view) + this.mItemMargin);
        this.adjustViewsStartOrEnd();
        View view3 = this.fillAfter(n2 + 1, this.getChildEndEdge(view) + this.mItemMargin);
        n2 = this.getChildCount();
        if (n2 > 0) {
            this.correctTooHigh(n2);
        }
        if (bl2) {
            return view;
        }
        if (view2 != null) {
            return view2;
        }
        return view3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int findClosestMotionRowOrColumn(int n2) {
        int n3;
        int n4 = this.getChildCount();
        if (n4 == 0) {
            return -1;
        }
        n2 = n3 = this.findMotionRowOrColumn(n2);
        if (n3 != -1) return n2;
        return this.mFirstPosition + n4 - 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int findMotionRowOrColumn(int n2) {
        int n3 = this.getChildCount();
        if (n3 == 0) {
            return -1;
        }
        int n4 = 0;
        while (n4 < n3) {
            if (n2 <= this.getChildEndEdge(this.getChildAt(n4))) {
                return this.mFirstPosition + n4;
            }
            ++n4;
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int findSyncPosition() {
        var7_1 = this.mItemCount;
        if (var7_1 == 0) {
            return -1;
        }
        var8_3 = this.mSyncRowId;
        if (var8_3 == Long.MIN_VALUE) {
            return -1;
        }
        var1_4 = Math.min(var7_1 - 1, Math.max(0, this.mSyncPosition));
        var10_5 = SystemClock.uptimeMillis();
        var3_6 = var1_4;
        var4_7 = var1_4;
        var2_8 = false;
        var12_9 = this.mAdapter;
        if (var12_9 != null) ** GOTO lbl23
        return -1;
lbl-1000: // 1 sources:
        {
            if (var6_10 || var2_8 && var5_2 == 0) ** GOTO lbl20
            if (var5_2 != 0 || !var2_8 && !var6_10) {
                var1_4 = --var3_6;
                var2_8 = true;
            }
            ** GOTO lbl23
lbl20: // 1 sources:
            do {
                var1_4 = ++var4_7;
                var2_8 = false;
lbl23: // 3 sources:
                if (SystemClock.uptimeMillis() > var10_5 + 100) return -1;
                var5_2 = var1_4;
                if (var12_9.getItemId(var1_4) == var8_3) return var5_2;
                var5_2 = var4_7 == var7_1 - 1 ? 1 : 0;
                var6_10 = var3_6 == 0;
            } while (var5_2 == 0);
            ** while (!var6_10)
        }
lbl30: // 1 sources:
        return -1;
    }

    private void finishEdgeGlows() {
        if (this.mStartEdge != null) {
            this.mStartEdge.finish();
        }
        if (this.mEndEdge != null) {
            this.mEndEdge.finish();
        }
    }

    private void fireOnSelected() {
        AdapterView.OnItemSelectedListener onItemSelectedListener = this.getOnItemSelectedListener();
        if (onItemSelectedListener == null) {
            return;
        }
        int n2 = this.getSelectedItemPosition();
        if (n2 >= 0) {
            onItemSelectedListener.onItemSelected((AdapterView)this, this.getSelectedView(), n2, this.mAdapter.getItemId(n2));
            return;
        }
        onItemSelectedListener.onNothingSelected((AdapterView)this);
    }

    private void forceValidFocusDirection(int n2) {
        if (this.mIsVertical && n2 != 33 && n2 != 130) {
            throw new IllegalArgumentException("Focus direction must be one of {View.FOCUS_UP, View.FOCUS_DOWN} for vertical orientation");
        }
        if (!this.mIsVertical && n2 != 17 && n2 != 66) {
            throw new IllegalArgumentException("Focus direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT} for vertical orientation");
        }
    }

    private void forceValidInnerFocusDirection(int n2) {
        if (this.mIsVertical && n2 != 17 && n2 != 66) {
            throw new IllegalArgumentException("Direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT} for vertical orientation");
        }
        if (!this.mIsVertical && n2 != 33 && n2 != 130) {
            throw new IllegalArgumentException("direction must be one of {View.FOCUS_UP, View.FOCUS_DOWN} for horizontal orientation");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getArrowScrollPreviewLength() {
        int n2;
        if (this.mIsVertical) {
            n2 = this.getVerticalFadingEdgeLength();
            do {
                return this.mItemMargin + Math.max(10, n2);
                break;
            } while (true);
        }
        n2 = this.getHorizontalFadingEdgeLength();
        return this.mItemMargin + Math.max(10, n2);
    }

    private int getChildEndEdge(View view) {
        if (this.mIsVertical) {
            return view.getBottom();
        }
        return view.getRight();
    }

    private int getChildHeightMeasureSpec(LayoutParams layoutParams) {
        if (this.mIsVertical && layoutParams.height == -2) {
            return View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        }
        if (!this.mIsVertical) {
            return View.MeasureSpec.makeMeasureSpec((int)(this.getHeight() - this.getPaddingTop() - this.getPaddingBottom()), (int)1073741824);
        }
        return View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)1073741824);
    }

    private int getChildStartEdge(View view) {
        if (this.mIsVertical) {
            return view.getTop();
        }
        return view.getLeft();
    }

    private int getChildWidthMeasureSpec(LayoutParams layoutParams) {
        if (!this.mIsVertical && layoutParams.width == -2) {
            return View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        }
        if (this.mIsVertical) {
            return View.MeasureSpec.makeMeasureSpec((int)(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight()), (int)1073741824);
        }
        return View.MeasureSpec.makeMeasureSpec((int)layoutParams.width, (int)1073741824);
    }

    @TargetApi(value=14)
    private final float getCurrVelocity() {
        if (Build.VERSION.SDK_INT >= 14) {
            return this.mScroller.getCurrVelocity();
        }
        return 0.0f;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int getDistance(Rect rect, Rect rect2, int n2) {
        int n3;
        int n4;
        int n5;
        switch (n2) {
            default: {
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
            }
            case 66: {
                n5 = rect.right;
                n3 = rect.top + rect.height() / 2;
                n2 = rect2.left;
                n4 = rect2.top + rect2.height() / 2;
                do {
                    return n4 * (n4 -= n3) + n2 * (n2 -= n5);
                    break;
                } while (true);
            }
            case 130: {
                n5 = rect.left + rect.width() / 2;
                n3 = rect.bottom;
                n2 = rect2.left + rect2.width() / 2;
                n4 = rect2.top;
                return n4 * (n4 -= n3) + n2 * (n2 -= n5);
            }
            case 17: {
                n5 = rect.left;
                n3 = rect.top + rect.height() / 2;
                n2 = rect2.right;
                n4 = rect2.top + rect2.height() / 2;
                return n4 * (n4 -= n3) + n2 * (n2 -= n5);
            }
            case 33: {
                n5 = rect.left + rect.width() / 2;
                n3 = rect.top;
                n2 = rect2.left + rect2.width() / 2;
                n4 = rect2.bottom;
                return n4 * (n4 -= n3) + n2 * (n2 -= n5);
            }
            case 1: 
            case 2: 
        }
        n5 = rect.right + rect.width() / 2;
        n3 = rect.top + rect.height() / 2;
        n2 = rect2.left + rect2.width() / 2;
        n4 = rect2.top + rect2.height() / 2;
        return n4 * (n4 -= n3) + n2 * (n2 -= n5);
    }

    private int getEndEdge() {
        if (this.mIsVertical) {
            return this.getHeight() - this.getPaddingBottom();
        }
        return this.getWidth() - this.getPaddingRight();
    }

    @TargetApi(value=9)
    private int getScaledOverscrollDistance(ViewConfiguration viewConfiguration) {
        if (Build.VERSION.SDK_INT < 9) {
            return 0;
        }
        return viewConfiguration.getScaledOverscrollDistance();
    }

    private int getStartEdge() {
        if (this.mIsVertical) {
            return this.getPaddingTop();
        }
        return this.getPaddingLeft();
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void handleDataChanged() {
        block14 : {
            if (this.mChoiceMode != ChoiceMode.NONE && this.mAdapter != null && this.mAdapter.hasStableIds()) {
                this.confirmCheckedPositionsById();
            }
            this.mRecycler.clearTransientStateViews();
            var3_1 = this.mItemCount;
            if (var3_1 <= 0) ** GOTO lbl49
            if (!this.mNeedSync) ** GOTO lbl-1000
            this.mNeedSync = false;
            this.mPendingSync = null;
            block0 : switch (this.mSyncMode) {
                do {
                    default: lbl-1000: // 2 sources:
                    {
                        if (!this.isInTouchMode()) {
                            var1_2 = var2_3 = this.getSelectedItemPosition();
                            if (var2_3 >= var3_1) {
                                var1_2 = var3_1 - 1;
                            }
                            var2_3 = var1_2;
                            if (var1_2 < 0) {
                                var2_3 = 0;
                            }
                            if ((var1_2 = this.lookForSelectablePosition(var2_3, true)) < 0) break block0;
                            this.setNextSelectedPositionInt(var1_2);
lbl21: // 2 sources:
                            do {
                                return;
                                break;
                            } while (true);
                        }
                        break block14;
                    }
                    break;
                } while (true);
                case 0: {
                    if (this.isInTouchMode()) {
                        this.mLayoutMode = 5;
                        this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), var3_1 - 1);
                        return;
                    }
                    if ((var1_2 = this.findSyncPosition()) < 0 || this.lookForSelectablePosition(var1_2, true) != var1_2) ** continue;
                    this.mSyncPosition = var1_2;
                    if (this.mSyncHeight == (long)this.getHeight()) {
                        this.mLayoutMode = 5;
lbl33: // 2 sources:
                        do {
                            this.setNextSelectedPositionInt(var1_2);
                            return;
                            break;
                        } while (true);
                    }
                    this.mLayoutMode = 2;
                    ** continue;
                }
                case 1: {
                    this.mLayoutMode = 5;
                    this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), var3_1 - 1);
                    return;
                }
            }
            var1_2 = this.lookForSelectablePosition(var2_3, false);
            if (var1_2 >= 0) {
                this.setNextSelectedPositionInt(var1_2);
                return;
            }
            ** GOTO lbl49
        }
        ** while (this.mResurrectToPosition >= 0)
lbl49: // 3 sources:
        this.mLayoutMode = 1;
        this.mSelectedPosition = -1;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mNextSelectedPosition = -1;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mSelectorPosition = -1;
        this.checkSelectionChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleDragChange(int n2) {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(true);
        }
        int n3 = this.mMotionPosition >= 0 ? this.mMotionPosition - this.mFirstPosition : this.getChildCount() / 2;
        int n4 = 0;
        viewParent = this.getChildAt(n3);
        if (viewParent != null) {
            n4 = this.mIsVertical ? viewParent.getTop() : viewParent.getLeft();
        }
        boolean bl2 = this.scrollListItemsBy(n2);
        viewParent = this.getChildAt(n3);
        if (viewParent != null) {
            n3 = this.mIsVertical ? viewParent.getTop() : viewParent.getLeft();
            if (bl2) {
                this.updateOverScrollState(n2, - n2 - (n3 - n4));
            }
        }
    }

    private boolean handleFocusWithinItem(int n2) {
        View view;
        this.forceValidInnerFocusDirection(n2);
        int n3 = this.getChildCount();
        if (this.mItemsCanFocus && n3 > 0 && this.mSelectedPosition != -1 && (view = this.getSelectedView()) != null && view.hasFocus() && view instanceof ViewGroup) {
            View view2 = view.findFocus();
            view = FocusFinder.getInstance().findNextFocus((ViewGroup)view, view2, n2);
            if (view != null) {
                view2.getFocusedRect(this.mTempRect);
                this.offsetDescendantRectToMyCoords(view2, this.mTempRect);
                this.offsetRectIntoDescendantCoords(view, this.mTempRect);
                if (view.requestFocus(n2, this.mTempRect)) {
                    return true;
                }
            }
            if ((view2 = FocusFinder.getInstance().findNextFocus((ViewGroup)this.getRootView(), view2, n2)) != null) {
                return this.isViewAncestorOf(view2, (View)this);
            }
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean handleKeyEvent(int var1_1, int var2_2, KeyEvent var3_3) {
        var4_4 = 66;
        var5_5 = 33;
        var9_6 = true;
        if (this.mAdapter == null) return false;
        if (!this.mIsAttached) {
            return false;
        }
        if (this.mDataChanged) {
            this.layoutChildren();
        }
        var8_7 = false;
        var6_8 = var3_3.getAction();
        var7_9 = var8_7;
        if (var6_8 == 1) ** GOTO lbl136
        switch (var1_1) {
            default: {
                var7_9 = var8_7;
                break;
            }
            case 19: {
                if (this.mIsVertical) {
                    var7_9 = this.handleKeyScroll(var3_3, var2_2, 33);
                    break;
                }
                var7_9 = var8_7;
                if (!KeyEventCompat.hasNoModifiers(var3_3)) break;
                var7_9 = this.handleFocusWithinItem(33);
                break;
            }
            case 20: {
                if (this.mIsVertical) {
                    var7_9 = this.handleKeyScroll(var3_3, var2_2, 130);
                    break;
                }
                var7_9 = var8_7;
                if (!KeyEventCompat.hasNoModifiers(var3_3)) break;
                var7_9 = this.handleFocusWithinItem(130);
                break;
            }
            case 21: {
                if (!this.mIsVertical) {
                    var7_9 = this.handleKeyScroll(var3_3, var2_2, 17);
                    break;
                }
                var7_9 = var8_7;
                if (!KeyEventCompat.hasNoModifiers(var3_3)) break;
                var7_9 = this.handleFocusWithinItem(17);
                break;
            }
            case 22: {
                if (!this.mIsVertical) {
                    var7_9 = this.handleKeyScroll(var3_3, var2_2, 66);
                    break;
                }
                var7_9 = var8_7;
                if (!KeyEventCompat.hasNoModifiers(var3_3)) break;
                var7_9 = this.handleFocusWithinItem(66);
                break;
            }
            case 23: 
            case 66: {
                var7_9 = var8_7;
                if (!KeyEventCompat.hasNoModifiers(var3_3)) break;
                var7_9 = var8_7 = this.resurrectSelectionIfNeeded();
                if (var8_7) break;
                var7_9 = var8_7;
                if (var3_3.getRepeatCount() != 0) break;
                var7_9 = var8_7;
                if (this.getChildCount() <= 0) break;
                this.keyPressed();
                var7_9 = true;
                break;
            }
            case 62: {
                if (KeyEventCompat.hasNoModifiers(var3_3)) {
                    if (!this.resurrectSelectionIfNeeded()) {
                        if (this.mIsVertical) {
                            var4_4 = 130;
                        }
                        if (this.pageScroll(var4_4)) {
                            // empty if block
                        }
                    }
                } else if (KeyEventCompat.hasModifiers(var3_3, 1) && !this.resurrectSelectionIfNeeded()) {
                    var4_4 = this.mIsVertical != false ? 33 : 17;
                    if (!this.fullScroll(var4_4)) {
                        // empty if block
                    }
                }
                var7_9 = true;
                break;
            }
            case 92: {
                if (!KeyEventCompat.hasNoModifiers(var3_3)) ** GOTO lbl85
                if (this.resurrectSelectionIfNeeded()) ** GOTO lbl-1000
                if (!this.mIsVertical) {
                    var5_5 = 17;
                }
                if (this.pageScroll(var5_5)) lbl-1000: // 2 sources:
                {
                    var7_9 = true;
                    break;
                }
                var7_9 = false;
                break;
lbl85: // 1 sources:
                var7_9 = var8_7;
                if (!KeyEventCompat.hasModifiers(var3_3, 2)) break;
                if (this.resurrectSelectionIfNeeded()) ** GOTO lbl-1000
                if (!this.mIsVertical) {
                    var5_5 = 17;
                }
                if (this.fullScroll(var5_5)) lbl-1000: // 2 sources:
                {
                    var7_9 = true;
                    break;
                }
                var7_9 = false;
                break;
            }
            case 93: {
                if (!KeyEventCompat.hasNoModifiers(var3_3)) ** GOTO lbl105
                if (this.resurrectSelectionIfNeeded()) ** GOTO lbl-1000
                if (this.mIsVertical) {
                    var4_4 = 130;
                }
                if (this.pageScroll(var4_4)) lbl-1000: // 2 sources:
                {
                    var7_9 = true;
                    break;
                }
                var7_9 = false;
                break;
lbl105: // 1 sources:
                var7_9 = var8_7;
                if (!KeyEventCompat.hasModifiers(var3_3, 2)) break;
                if (this.resurrectSelectionIfNeeded()) ** GOTO lbl-1000
                if (this.mIsVertical) {
                    var4_4 = 130;
                }
                if (this.fullScroll(var4_4)) lbl-1000: // 2 sources:
                {
                    var7_9 = true;
                    break;
                }
                var7_9 = false;
                break;
            }
            case 122: {
                var7_9 = var8_7;
                if (!KeyEventCompat.hasNoModifiers(var3_3)) break;
                if (this.resurrectSelectionIfNeeded()) ** GOTO lbl-1000
                if (!this.mIsVertical) {
                    var5_5 = 17;
                }
                if (this.fullScroll(var5_5)) lbl-1000: // 2 sources:
                {
                    var7_9 = true;
                    break;
                }
                var7_9 = false;
                break;
            }
            case 123: {
                var7_9 = var8_7;
                if (!KeyEventCompat.hasNoModifiers(var3_3)) break;
                if (this.resurrectSelectionIfNeeded()) ** GOTO lbl-1000
                if (this.mIsVertical) {
                    var4_4 = 130;
                }
                if (this.fullScroll(var4_4)) lbl-1000: // 2 sources:
                {
                    var7_9 = true;
                    break;
                }
                var7_9 = false;
            }
        }
lbl136: // 36 sources:
        var8_7 = var9_6;
        if (var7_9 != false) return var8_7;
        switch (var6_8) {
            default: {
                return false;
            }
            case 0: {
                return super.onKeyDown(var1_1, var3_3);
            }
            case 1: {
                var8_7 = var9_6;
                if (this.isEnabled() == false) return var8_7;
                if (this.isClickable() == false) return false;
                if (this.isPressed() == false) return false;
                if (this.mSelectedPosition < 0) return false;
                if (this.mAdapter == null) return false;
                if (this.mSelectedPosition >= this.mAdapter.getCount()) return false;
                var3_3 = this.getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (var3_3 != null) {
                    this.performItemClick((View)var3_3, this.mSelectedPosition, this.mSelectedRowId);
                    var3_3.setPressed(false);
                }
                this.setPressed(false);
                return true;
            }
            case 2: 
        }
        return super.onKeyMultiple(var1_1, var2_2, var3_3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean handleKeyScroll(KeyEvent keyEvent, int n2, int n3) {
        boolean bl2 = false;
        if (KeyEventCompat.hasNoModifiers(keyEvent)) {
            boolean bl3;
            bl2 = bl3 = this.resurrectSelectionIfNeeded();
            if (bl3) return bl2;
            do {
                bl2 = bl3;
                if (n2 <= 0) return bl2;
                bl2 = bl3;
                if (!this.arrowScroll(n3)) return bl2;
                bl3 = true;
                --n2;
            } while (true);
        }
        if (!KeyEventCompat.hasModifiers(keyEvent, 2)) return bl2;
        if (this.resurrectSelectionIfNeeded()) return true;
        if (!this.fullScroll(n3)) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleNewSelectionChange(View view, int n2, int n3, boolean bl2) {
        View view2;
        this.forceValidFocusDirection(n2);
        if (n3 == -1) {
            throw new IllegalArgumentException("newSelectedPosition needs to be valid");
        }
        int n4 = this.mSelectedPosition - this.mFirstPosition;
        n3 -= this.mFirstPosition;
        int n5 = 0;
        if (n2 == 33 || n2 == 17) {
            n2 = n4;
            view2 = this.getChildAt(n3);
            n4 = 1;
        } else {
            n2 = n3;
            view2 = view;
            view = this.getChildAt(n2);
            n3 = n4;
            n4 = n5;
        }
        n5 = this.getChildCount();
        if (view2 != null) {
            boolean bl3 = !bl2 && n4 != 0;
            view2.setSelected(bl3);
            this.measureAndAdjustDown(view2, n3, n5);
        }
        if (view != null) {
            bl2 = !bl2 && n4 == 0;
            view.setSelected(bl2);
            this.measureAndAdjustDown(view, n2, n5);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleOverScrollChange(int n2) {
        int n3 = this.mOverScroll;
        int n4 = n3 - n2;
        int n5 = - n2;
        if (n4 < 0 && n3 >= 0 || n4 > 0 && n3 <= 0) {
            n3 = - n3;
            n5 = n2 + n3;
            n2 = n3;
        } else {
            n3 = 0;
            n2 = n5;
            n5 = n3;
        }
        if (n2 != 0) {
            this.updateOverScrollState(n5, n2);
        }
        if (n5 != 0) {
            if (this.mOverScroll != 0) {
                this.mOverScroll = 0;
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
            this.scrollListItemsBy(n5);
            this.mTouchMode = 3;
            this.mMotionPosition = this.findClosestMotionRowOrColumn((int)this.mLastTouchPos);
            this.mTouchRemainderPos = 0.0f;
        }
    }

    private void hideSelector() {
        if (this.mSelectedPosition != -1) {
            if (this.mLayoutMode != 4) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
            if (this.mNextSelectedPosition >= 0 && this.mNextSelectedPosition != this.mSelectedPosition) {
                this.mResurrectToPosition = this.mNextSelectedPosition;
            }
            this.setSelectedPositionInt(-1);
            this.setNextSelectedPositionInt(-1);
            this.mSelectedStart = 0;
        }
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

    private void invokeOnItemScrollListener() {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, this.mFirstPosition, this.getChildCount(), this.mItemCount);
        }
        this.onScrollChanged(0, 0, 0, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isViewAncestorOf(View view, View view2) {
        if (view == view2 || (view = view.getParent()) instanceof ViewGroup && this.isViewAncestorOf(view, view2)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void keyPressed() {
        if (!this.isEnabled()) return;
        if (!this.isClickable()) {
            return;
        }
        Drawable drawable2 = this.mSelector;
        Rect rect = this.mSelectorRect;
        if (drawable2 == null) return;
        if (!this.isFocused()) {
            if (!this.touchModeDrawsInPressedState()) return;
        }
        if (rect.isEmpty()) return;
        rect = this.getChildAt(this.mSelectedPosition - this.mFirstPosition);
        if (rect != null) {
            if (rect.hasFocusable()) return;
            rect.setPressed(true);
        }
        this.setPressed(true);
        boolean bl2 = this.isLongClickable();
        drawable2 = drawable2.getCurrent();
        if (drawable2 != null && drawable2 instanceof TransitionDrawable) {
            if (bl2) {
                ((TransitionDrawable)drawable2).startTransition(ViewConfiguration.getLongPressTimeout());
            } else {
                ((TransitionDrawable)drawable2).resetTransition();
            }
        }
        if (!bl2) return;
        if (this.mDataChanged) return;
        if (this.mPendingCheckForKeyLongPress == null) {
            this.mPendingCheckForKeyLongPress = new CheckForKeyLongPress();
        }
        this.mPendingCheckForKeyLongPress.rememberWindowAttachCount();
        this.postDelayed((Runnable)this.mPendingCheckForKeyLongPress, (long)ViewConfiguration.getLongPressTimeout());
    }

    /*
     * Exception decompiling
     */
    private void layoutChildren() {
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

    private int lookForSelectablePosition(int n2) {
        return this.lookForSelectablePosition(n2, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int lookForSelectablePosition(int n2, boolean bl2) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null || this.isInTouchMode()) return -1;
        int n3 = this.mItemCount;
        if (!this.mAreAllItemsSelectable) {
            int n4;
            if (bl2) {
                n2 = Math.max(0, n2);
                do {
                    n4 = n2;
                    if (n2 < n3) {
                        n4 = n2;
                        if (!listAdapter.isEnabled(n2)) {
                            ++n2;
                            continue;
                        }
                    }
                    break;
                    break;
                } while (true);
            } else {
                n2 = Math.min(n2, n3 - 1);
                do {
                    n4 = n2;
                    if (n2 < 0) break;
                    n4 = n2;
                    if (listAdapter.isEnabled(n2)) break;
                    --n2;
                } while (true);
            }
            if (n4 < 0 || n4 >= n3) return -1;
            return n4;
        }
        if (n2 >= 0 && n2 < n3) return n2;
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int lookForSelectablePositionOnScreen(int n2) {
        this.forceValidFocusDirection(n2);
        int n3 = this.mFirstPosition;
        ListAdapter listAdapter = this.getAdapter();
        if (n2 == 130 || n2 == 66) {
            int n4 = this.mSelectedPosition != -1 ? this.mSelectedPosition + 1 : n3;
            if (n4 >= listAdapter.getCount()) {
                return -1;
            }
            n2 = n4;
            if (n4 < n3) {
                n2 = n3;
            }
            int n5 = this.getLastVisiblePosition();
            n4 = n2;
            while (n4 <= n5) {
                if (listAdapter.isEnabled(n4)) {
                    n2 = n4;
                    if (this.getChildAt(n4 - n3).getVisibility() == 0) return n2;
                }
                ++n4;
            }
            return -1;
        }
        int n6 = this.getChildCount() + n3 - 1;
        n2 = this.mSelectedPosition != -1 ? this.mSelectedPosition - 1 : this.getChildCount() + n3 - 1;
        if (n2 < 0) return -1;
        if (n2 >= listAdapter.getCount()) {
            return -1;
        }
        int n7 = n2;
        if (n2 > n6) {
            n7 = n6;
        }
        while (n7 >= n3) {
            if (listAdapter.isEnabled(n7)) {
                n2 = n7;
                if (this.getChildAt(n7 - n3).getVisibility() == 0) return n2;
            }
            --n7;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View makeAndAddView(int n2, int n3, boolean bl2, boolean bl3) {
        int n4;
        View view;
        if (this.mIsVertical) {
            n4 = this.getPaddingLeft();
        } else {
            int n5 = this.getPaddingTop();
            n4 = n3;
            n3 = n5;
        }
        if (!this.mDataChanged && (view = this.mRecycler.getActiveView(n2)) != null) {
            this.setupChild(view, n2, n3, n4, bl2, bl3, true);
            return view;
        }
        view = this.obtainView(n2, this.mIsScrap);
        this.setupChild(view, n2, n3, n4, bl2, bl3, this.mIsScrap[0]);
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void maybeScroll(int n2) {
        if (this.mTouchMode == 3) {
            this.handleDragChange(n2);
            return;
        } else {
            if (this.mTouchMode != 5) return;
            {
                this.handleOverScrollChange(n2);
                return;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean maybeStartScrolling(int var1_1) {
        var2_2 = this.mOverScroll != 0;
        if (Math.abs(var1_1) > this.mTouchSlop) ** GOTO lbl-1000
        if (!var2_2) {
            return false;
        }
        if (var2_2) lbl-1000: // 2 sources:
        {
            this.mTouchMode = 5;
        } else {
            this.mTouchMode = 3;
        }
        if ((var3_3 = this.getParent()) != null) {
            var3_3.requestDisallowInterceptTouchEvent(true);
        }
        this.cancelCheckForLongPress();
        this.setPressed(false);
        var3_3 = this.getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (var3_3 != null) {
            var3_3.setPressed(false);
        }
        this.reportScrollStateChange(1);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureAndAdjustDown(View view, int n2, int n3) {
        int n4 = view.getHeight();
        this.measureChild(view);
        if (view.getMeasuredHeight() != n4) {
            this.relayoutMeasuredChild(view);
            int n5 = view.getMeasuredHeight();
            ++n2;
            while (n2 < n3) {
                this.getChildAt(n2).offsetTopAndBottom(n5 - n4);
                ++n2;
            }
        }
    }

    private void measureChild(View view) {
        this.measureChild(view, (LayoutParams)view.getLayoutParams());
    }

    private void measureChild(View view, LayoutParams layoutParams) {
        view.measure(this.getChildWidthMeasureSpec(layoutParams), this.getChildHeightMeasureSpec(layoutParams));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int measureHeightOfChildren(int n2, int n3, int n4, int n5, int n6) {
        int n7 = this.getPaddingTop();
        int n8 = this.getPaddingBottom();
        Object object = this.mAdapter;
        if (object == null) {
            return n7 + n8;
        }
        int n9 = n7 + n8;
        int n10 = this.mItemMargin;
        int n11 = 0;
        n7 = n4;
        if (n4 == -1) {
            n7 = object.getCount() - 1;
        }
        object = this.mRecycler;
        boolean bl2 = this.recycleOnMeasure();
        boolean[] arrbl = this.mIsScrap;
        n8 = n3;
        n4 = n9;
        n3 = n11;
        while (n8 <= n7) {
            View view = this.obtainView(n8, arrbl);
            this.measureScrapChild(view, n8, n2);
            n11 = n4;
            if (n8 > 0) {
                n11 = n4 + n10;
            }
            if (bl2) {
                object.addScrapView(view, -1);
            }
            if ((n4 = n11 + view.getMeasuredHeight()) >= n5) {
                if (n6 < 0) return n5;
                if (n8 <= n6) return n5;
                if (n3 <= 0) return n5;
                if (n4 != n5) return n3;
                return n5;
            }
            n11 = n3;
            if (n6 >= 0) {
                n11 = n3;
                if (n8 >= n6) {
                    n11 = n4;
                }
            }
            ++n8;
            n3 = n11;
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureScrapChild(View view, int n2, int n3) {
        LayoutParams layoutParams;
        LayoutParams layoutParams2 = layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = this.generateDefaultLayoutParams();
            view.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        }
        layoutParams2.viewType = this.mAdapter.getItemViewType(n2);
        layoutParams2.forceAdd = true;
        if (this.mIsVertical) {
            n2 = this.getChildHeightMeasureSpec(layoutParams2);
        } else {
            int n4 = this.getChildWidthMeasureSpec(layoutParams2);
            n2 = n3;
            n3 = n4;
        }
        view.measure(n3, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int measureWidthOfChildren(int n2, int n3, int n4, int n5, int n6) {
        int n7 = this.getPaddingLeft();
        int n8 = this.getPaddingRight();
        Object object = this.mAdapter;
        if (object == null) {
            return n7 + n8;
        }
        int n9 = n7 + n8;
        int n10 = this.mItemMargin;
        int n11 = 0;
        n7 = n4;
        if (n4 == -1) {
            n7 = object.getCount() - 1;
        }
        object = this.mRecycler;
        boolean bl2 = this.recycleOnMeasure();
        boolean[] arrbl = this.mIsScrap;
        n8 = n3;
        n4 = n9;
        n3 = n11;
        while (n8 <= n7) {
            View view = this.obtainView(n8, arrbl);
            this.measureScrapChild(view, n8, n2);
            n11 = n4;
            if (n8 > 0) {
                n11 = n4 + n10;
            }
            if (bl2) {
                object.addScrapView(view, -1);
            }
            if ((n4 = n11 + view.getMeasuredWidth()) >= n5) {
                if (n6 < 0) return n5;
                if (n8 <= n6) return n5;
                if (n3 <= 0) return n5;
                if (n4 != n5) return n3;
                return n5;
            }
            n11 = n3;
            if (n6 >= 0) {
                n11 = n3;
                if (n8 >= n6) {
                    n11 = n4;
                }
            }
            ++n8;
            n3 = n11;
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View moveSelection(View view, View view2, int n2, int n3, int n4) {
        int n5 = this.mSelectedPosition;
        int n6 = this.getChildStartEdge(view);
        int n7 = this.getChildEndEdge(view);
        if (n2 > 0) {
            view = this.makeAndAddView(n5 - 1, n6, true, false);
            n2 = this.mItemMargin;
            view2 = this.makeAndAddView(n5, n7 + n2, true, true);
            n5 = this.getChildStartEdge(view2);
            n6 = this.getChildEndEdge(view2);
            if (n6 > n4) {
                n7 = (n4 - n3) / 2;
                n3 = Math.min(Math.min(n5 - n3, n6 - n4), n7);
                if (this.mIsVertical) {
                    view.offsetTopAndBottom(- n3);
                    view2.offsetTopAndBottom(- n3);
                } else {
                    view.offsetLeftAndRight(- n3);
                    view2.offsetLeftAndRight(- n3);
                }
            }
            this.fillBefore(this.mSelectedPosition - 2, n5 - n2);
            this.adjustViewsStartOrEnd();
            this.fillAfter(this.mSelectedPosition + 1, n6 + n2);
            return view2;
        }
        if (n2 < 0) {
            if (view2 != null) {
                n2 = this.mIsVertical ? view2.getTop() : view2.getLeft();
                view = this.makeAndAddView(n5, n2, true, true);
            } else {
                view = this.makeAndAddView(n5, n6, false, true);
            }
            n2 = this.getChildStartEdge(view);
            n6 = this.getChildEndEdge(view);
            if (n2 < n3) {
                n7 = (n4 - n3) / 2;
                n2 = Math.min(Math.min(n3 - n2, n4 - n6), n7);
                if (this.mIsVertical) {
                    view.offsetTopAndBottom(n2);
                } else {
                    view.offsetLeftAndRight(n2);
                }
            }
            this.fillBeforeAndAfter(view, n5);
            return view;
        }
        view = this.makeAndAddView(n5, n6, true, true);
        n2 = this.getChildStartEdge(view);
        n4 = this.getChildEndEdge(view);
        if (n6 < n3 && n4 < n3 + 20) {
            if (this.mIsVertical) {
                view.offsetTopAndBottom(n3 - n2);
            } else {
                view.offsetLeftAndRight(n3 - n2);
            }
        }
        this.fillBeforeAndAfter(view, n5);
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=16)
    private View obtainView(int n2, boolean[] object) {
        object[0] = false;
        View view = this.mRecycler.getTransientStateView(n2);
        if (view != null) {
            return view;
        }
        Object object2 = this.mRecycler.getScrapView(n2);
        if (object2 != null) {
            view = this.mAdapter.getView(n2, (View)object2, (ViewGroup)this);
            if (view != object2) {
                this.mRecycler.addScrapView((View)object2, n2);
            } else {
                object[0] = true;
            }
        } else {
            view = this.mAdapter.getView(n2, null, (ViewGroup)this);
        }
        if (ViewCompat.getImportantForAccessibility(view) == 0) {
            ViewCompat.setImportantForAccessibility(view, 1);
        }
        if (this.mHasStableIds) {
            object2 = (LayoutParams)view.getLayoutParams();
            if (object2 == null) {
                object = this.generateDefaultLayoutParams();
            } else {
                object = object2;
                if (!this.checkLayoutParams((ViewGroup.LayoutParams)object2)) {
                    object = this.generateLayoutParams((ViewGroup.LayoutParams)object2);
                }
            }
            object.id = this.mAdapter.getItemId(n2);
            view.setLayoutParams((ViewGroup.LayoutParams)object);
        }
        if (this.mAccessibilityDelegate == null) {
            this.mAccessibilityDelegate = new ListItemAccessibilityDelegate();
        }
        ViewCompat.setAccessibilityDelegate(view, this.mAccessibilityDelegate);
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void offsetChildren(int n2) {
        int n3 = this.getChildCount();
        int n4 = 0;
        while (n4 < n3) {
            View view = this.getChildAt(n4);
            if (this.mIsVertical) {
                view.offsetTopAndBottom(n2);
            } else {
                view.offsetLeftAndRight(n2);
            }
            ++n4;
        }
    }

    @TargetApi(value=9)
    private boolean overScrollByInternal(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, boolean bl2) {
        if (Build.VERSION.SDK_INT < 9) {
            return false;
        }
        return super.overScrollBy(n2, n3, n4, n5, n6, n7, n8, n9, bl2);
    }

    private void performAccessibilityActionsOnSelected() {
        if (this.getSelectedItemPosition() >= 0) {
            this.sendAccessibilityEvent(4);
        }
    }

    private boolean performLongPress(View view, int n2, long l2) {
        boolean bl2 = false;
        AdapterView.OnItemLongClickListener onItemLongClickListener = this.getOnItemLongClickListener();
        if (onItemLongClickListener != null) {
            bl2 = onItemLongClickListener.onItemLongClick((AdapterView)this, view, n2, l2);
        }
        boolean bl3 = bl2;
        if (!bl2) {
            this.mContextMenuInfo = this.createContextMenuInfo(view, n2, l2);
            bl3 = super.showContextMenuForChild((View)this);
        }
        if (bl3) {
            this.performHapticFeedback(0);
        }
        return bl3;
    }

    private int positionOfNewFocus(View view) {
        int n2 = this.getChildCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (!this.isViewAncestorOf(view, this.getChildAt(i2))) continue;
            return this.mFirstPosition + i2;
        }
        throw new IllegalArgumentException("newFocus is not a child of any of the children of the list!");
    }

    /*
     * Enabled aggressive block sorting
     */
    private void positionSelector(int n2, View view) {
        if (n2 != -1) {
            this.mSelectorPosition = n2;
        }
        this.mSelectorRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        boolean bl2 = this.mIsChildViewEnabled;
        if (view.isEnabled() != bl2) {
            bl2 = !bl2;
            this.mIsChildViewEnabled = bl2;
            if (this.getSelectedItemPosition() != -1) {
                this.refreshDrawableState();
            }
        }
    }

    private int reconcileSelectedPosition() {
        int n2;
        int n3 = n2 = this.mSelectedPosition;
        if (n2 < 0) {
            n3 = this.mResurrectToPosition;
        }
        return Math.min(Math.max(0, n3), this.mItemCount - 1);
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void relayoutMeasuredChild(View view) {
        int n2 = view.getMeasuredWidth();
        int n3 = view.getMeasuredHeight();
        int n4 = this.getPaddingLeft();
        int n5 = view.getTop();
        view.layout(n4, n5, n4 + n2, n5 + n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void rememberSyncState() {
        if (this.getChildCount() == 0) {
            return;
        }
        this.mNeedSync = true;
        if (this.mSelectedPosition >= 0) {
            View view = this.getChildAt(this.mSelectedPosition - this.mFirstPosition);
            this.mSyncRowId = this.mNextSelectedRowId;
            this.mSyncPosition = this.mNextSelectedPosition;
            if (view != null) {
                int n2 = this.mIsVertical ? view.getTop() : view.getLeft();
                this.mSpecificStart = n2;
            }
            this.mSyncMode = 0;
            return;
        }
        View view = this.getChildAt(0);
        ListAdapter listAdapter = this.getAdapter();
        this.mSyncRowId = this.mFirstPosition >= 0 && this.mFirstPosition < listAdapter.getCount() ? listAdapter.getItemId(this.mFirstPosition) : -1;
        this.mSyncPosition = this.mFirstPosition;
        if (view != null) {
            int n3 = this.mIsVertical ? view.getTop() : view.getLeft();
            this.mSpecificStart = n3;
        }
        this.mSyncMode = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void reportScrollStateChange(int n2) {
        if (n2 == this.mLastScrollState || this.mOnScrollListener == null) {
            return;
        }
        this.mLastScrollState = n2;
        this.mOnScrollListener.onScrollStateChanged(this, n2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean scrollListItemsBy(int var1_1) {
        block16 : {
            var11_2 = this.getChildCount();
            if (var11_2 == 0) {
                return true;
            }
            var8_3 = this.getChildStartEdge(this.getChildAt(0));
            var9_4 = this.getChildEndEdge(this.getChildAt(var11_2 - 1));
            var2_5 = this.getPaddingTop();
            var5_6 = this.getPaddingBottom();
            var3_7 = this.getPaddingLeft();
            var6_8 = this.getPaddingRight();
            var4_9 = this.mIsVertical != false ? var2_5 : var3_7;
            var10_10 = this.getEndEdge();
            var2_5 = this.mIsVertical != false ? this.getHeight() - var5_6 - var2_5 : this.getWidth() - var6_8 - var3_7;
            var5_6 = var1_1 < 0 ? Math.max(- var2_5 - 1, var1_1) : Math.min(var2_5 - 1, var1_1);
            var12_11 = this.mFirstPosition;
            var1_1 = var12_11 == 0 && var8_3 >= var4_9 && var5_6 >= 0 ? 1 : 0;
            var2_5 = var12_11 + var11_2 == this.mItemCount && var9_4 <= var10_10 && var5_6 <= 0 ? 1 : 0;
            if (var1_1 != 0 || var2_5 != 0) {
                if (var5_6 == 0) return false;
                return true;
            }
            var15_12 = this.isInTouchMode();
            if (var15_12) {
                this.hideSelector();
            }
            var6_8 = 0;
            var3_7 = 0;
            var2_5 = 0;
            var1_1 = 0;
            var14_13 = var5_6 < 0;
            if (var14_13) ** GOTO lbl44
            var1_1 = var11_2 - 1;
            var3_7 = var6_8;
            do {
                var6_8 = var2_5;
                var7_15 = var3_7;
                if (var1_1 >= 0) {
                    var16_17 = this.getChildAt(var1_1);
                    var6_8 = var2_5++;
                    var7_15 = var3_7;
                    if (this.getChildStartEdge(var16_17) > var10_10 - var5_6) {
                        var3_7 = var1_1;
                        this.mRecycler.addScrapView(var16_17, var12_11 + var1_1);
                        --var1_1;
                        continue;
                    }
                }
                ** GOTO lbl54
                break;
            } while (true);
lbl44: // 1 sources:
            var13_14 = - var5_6;
            var2_5 = 0;
            do {
                var6_8 = var1_1++;
                var7_15 = var3_7;
                if (var2_5 >= var11_2) ** GOTO lbl54
                var16_16 = this.getChildAt(var2_5);
                if (this.getChildEndEdge(var16_16) >= var13_14 + var4_9) {
                    var7_15 = var3_7;
                    var6_8 = var1_1;
lbl54: // 3 sources:
                    this.mBlockLayoutRequests = true;
                    if (var6_8 > 0) {
                        this.detachViewsFromParent(var7_15, var6_8);
                    }
                    if (!this.awakenScrollbarsInternal()) {
                        this.invalidate();
                    }
                    this.offsetChildren(var5_6);
                    if (var14_13) {
                        this.mFirstPosition += var6_8;
                    }
                    if (var4_9 - var8_3 < (var1_1 = Math.abs(var5_6)) || var9_4 - var10_10 < var1_1) {
                        this.fillGap(var14_13);
                    }
                    if (var15_12 || this.mSelectedPosition == -1) break;
                    var1_1 = this.mSelectedPosition - this.mFirstPosition;
                    if (var1_1 >= 0 && var1_1 < this.getChildCount()) {
                        this.positionSelector(this.mSelectedPosition, this.getChildAt(var1_1));
                    }
                    break block16;
                }
                this.mRecycler.addScrapView(var16_16, var12_11 + var2_5);
                ++var2_5;
            } while (true);
            if (this.mSelectorPosition != -1) {
                var1_1 = this.mSelectorPosition - this.mFirstPosition;
                if (var1_1 >= 0 && var1_1 < this.getChildCount()) {
                    this.positionSelector(-1, this.getChildAt(var1_1));
                }
            } else {
                this.mSelectorRect.setEmpty();
            }
        }
        this.mBlockLayoutRequests = false;
        this.invokeOnItemScrollListener();
        return false;
    }

    private void selectionChanged() {
        if (this.getOnItemSelectedListener() == null) {
            return;
        }
        if (this.mInLayout || this.mBlockLayoutRequests) {
            if (this.mSelectionNotifier == null) {
                this.mSelectionNotifier = new SelectionNotifier();
            }
            this.post((Runnable)this.mSelectionNotifier);
            return;
        }
        this.fireOnSelected();
        this.performAccessibilityActionsOnSelected();
    }

    private void setNextSelectedPositionInt(int n2) {
        this.mNextSelectedPosition = n2;
        this.mNextSelectedRowId = this.getItemIdAtPosition(n2);
        if (this.mNeedSync && this.mSyncMode == 0 && n2 >= 0) {
            this.mSyncPosition = n2;
            this.mSyncRowId = this.mNextSelectedRowId;
        }
    }

    private void setSelectedPositionInt(int n2) {
        this.mSelectedPosition = n2;
        this.mSelectedRowId = this.getItemIdAtPosition(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setSelectionInt(int n2) {
        this.setNextSelectedPositionInt(n2);
        boolean bl2 = false;
        int n3 = this.mSelectedPosition;
        boolean bl3 = bl2;
        if (n3 >= 0) {
            if (n2 == n3 - 1) {
                bl3 = true;
            } else {
                bl3 = bl2;
                if (n2 == n3 + 1) {
                    bl3 = true;
                }
            }
        }
        this.layoutChildren();
        if (bl3) {
            this.awakenScrollbarsInternal();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=11)
    private void setupChild(View view, int n2, int n3, int n4, boolean bl2, boolean bl3, boolean bl4) {
        LayoutParams layoutParams;
        bl3 = bl3 && this.shouldShowSelector();
        int n5 = bl3 != view.isSelected() ? 1 : 0;
        int n6 = this.mTouchMode;
        boolean bl5 = n6 > 0 && n6 < 3 && this.mMotionPosition == n2;
        int n7 = bl5 != view.isPressed() ? 1 : 0;
        n6 = !bl4 || n5 != 0 || view.isLayoutRequested() ? 1 : 0;
        LayoutParams layoutParams2 = layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = this.generateDefaultLayoutParams();
        }
        layoutParams2.viewType = this.mAdapter.getItemViewType(n2);
        if (bl4 && !layoutParams2.forceAdd) {
            int n8 = bl2 ? -1 : 0;
            this.attachViewToParent(view, n8, (ViewGroup.LayoutParams)layoutParams2);
        } else {
            layoutParams2.forceAdd = false;
            int n9 = bl2 ? -1 : 0;
            this.addViewInLayout(view, n9, (ViewGroup.LayoutParams)layoutParams2, true);
        }
        if (n5 != 0) {
            view.setSelected(bl3);
        }
        if (n7 != 0) {
            view.setPressed(bl5);
        }
        if (this.mChoiceMode != ChoiceMode.NONE && this.mCheckStates != null) {
            if (view instanceof Checkable) {
                ((Checkable)view).setChecked(this.mCheckStates.get(n2));
            } else if (Build.VERSION.SDK_INT >= 11) {
                view.setActivated(this.mCheckStates.get(n2));
            }
        }
        if (n6 != 0) {
            this.measureChild(view, layoutParams2);
        } else {
            this.cleanupLayoutState(view);
        }
        n5 = view.getMeasuredWidth();
        n7 = view.getMeasuredHeight();
        n2 = this.mIsVertical && !bl2 ? n3 - n7 : n3;
        n3 = !this.mIsVertical && !bl2 ? n4 - n5 : n4;
        if (n6 != 0) {
            view.layout(n3, n2, n3 + n5, n2 + n7);
            return;
        }
        view.offsetLeftAndRight(n3 - view.getLeft());
        view.offsetTopAndBottom(n2 - view.getTop());
    }

    private boolean shouldShowSelector() {
        if (this.hasFocus() && !this.isInTouchMode() || this.touchModeDrawsInPressedState()) {
            return true;
        }
        return false;
    }

    private boolean touchModeDrawsInPressedState() {
        switch (this.mTouchMode) {
            default: {
                return false;
            }
            case 1: 
            case 2: 
        }
        return true;
    }

    private void triggerCheckForLongPress() {
        if (this.mPendingCheckForLongPress == null) {
            this.mPendingCheckForLongPress = new CheckForLongPress();
        }
        this.mPendingCheckForLongPress.rememberWindowAttachCount();
        this.postDelayed((Runnable)this.mPendingCheckForLongPress, (long)ViewConfiguration.getLongPressTimeout());
    }

    private void triggerCheckForTap() {
        if (this.mPendingCheckForTap == null) {
            this.mPendingCheckForTap = new CheckForTap();
        }
        this.postDelayed((Runnable)this.mPendingCheckForTap, (long)ViewConfiguration.getTapTimeout());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateEmptyStatus() {
        boolean bl2 = this.mAdapter == null || this.mAdapter.isEmpty();
        if (bl2) {
            if (this.mEmptyView != null) {
                this.mEmptyView.setVisibility(0);
                this.setVisibility(8);
            } else {
                this.setVisibility(0);
            }
            if (this.mDataChanged) {
                this.layout(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
            }
            return;
        }
        if (this.mEmptyView != null) {
            this.mEmptyView.setVisibility(8);
        }
        this.setVisibility(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=11)
    private void updateOnScreenCheckedViews() {
        int n2 = this.mFirstPosition;
        int n3 = this.getChildCount();
        int n4 = 0;
        while (n4 < n3) {
            View view = this.getChildAt(n4);
            int n5 = n2 + n4;
            if (view instanceof Checkable) {
                ((Checkable)view).setChecked(this.mCheckStates.get(n5));
            } else if (Build.VERSION.SDK_INT >= 11) {
                view.setActivated(this.mCheckStates.get(n5));
            }
            ++n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateOverScrollState(int n2, int n3) {
        int n4 = this.mIsVertical ? 0 : n3;
        int n5 = this.mIsVertical ? n3 : 0;
        int n6 = this.mIsVertical ? 0 : this.mOverScroll;
        int n7 = this.mIsVertical ? this.mOverScroll : 0;
        int n8 = this.mIsVertical ? 0 : this.mOverscrollDistance;
        int n9 = this.mIsVertical ? this.mOverscrollDistance : 0;
        this.overScrollByInternal(n4, n5, n6, n7, 0, 0, n8, n9, true);
        if (Math.abs(this.mOverscrollDistance) == Math.abs(this.mOverScroll) && this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        if ((n4 = ViewCompat.getOverScrollMode((View)this)) == 0 || n4 == 1 && !this.contentFits()) {
            this.mTouchMode = 5;
            float f2 = n3;
            n3 = this.mIsVertical ? this.getHeight() : this.getWidth();
            f2 /= (float)n3;
            if (n2 > 0) {
                this.mStartEdge.onPull(f2);
                if (!this.mEndEdge.isFinished()) {
                    this.mEndEdge.onRelease();
                }
            } else if (n2 < 0) {
                this.mEndEdge.onPull(f2);
                if (!this.mStartEdge.isFinished()) {
                    this.mStartEdge.onRelease();
                }
            }
            if (n2 != 0) {
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void updateSelectorState() {
        if (this.mSelector == null) return;
        if (this.shouldShowSelector()) {
            this.mSelector.setState(this.getDrawableState());
            return;
        }
        this.mSelector.setState(STATE_NOTHING);
    }

    private void useDefaultSelector() {
        this.setSelector(this.getResources().getDrawable(17301602));
    }

    protected boolean canAnimate() {
        if (super.canAnimate() && this.mItemCount > 0) {
            return true;
        }
        return false;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void clearChoices() {
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        this.mCheckedItemCount = 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected int computeHorizontalScrollExtent() {
        int n2 = this.getChildCount();
        if (n2 == 0) {
            return 0;
        }
        int n3 = n2 * 100;
        View view = this.getChildAt(0);
        int n4 = view.getLeft();
        int n5 = view.getWidth();
        int n6 = n3;
        if (n5 > 0) {
            n6 = n3 + n4 * 100 / n5;
        }
        view = this.getChildAt(n2 - 1);
        n2 = view.getRight();
        n4 = view.getWidth();
        n3 = n6;
        if (n4 <= 0) return n3;
        return n6 - (n2 - this.getWidth()) * 100 / n4;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected int computeHorizontalScrollOffset() {
        int n2 = this.mFirstPosition;
        int n3 = this.getChildCount();
        if (n2 < 0) return 0;
        if (n3 == 0) {
            return 0;
        }
        View view = this.getChildAt(0);
        n3 = view.getLeft();
        int n4 = view.getWidth();
        if (n4 <= 0) return 0;
        return Math.max(n2 * 100 - n3 * 100 / n4, 0);
    }

    protected int computeHorizontalScrollRange() {
        int n2;
        int n3 = n2 = Math.max(this.mItemCount * 100, 0);
        if (!this.mIsVertical) {
            n3 = n2;
            if (this.mOverScroll != 0) {
                n3 = n2 + Math.abs((int)((float)this.mOverScroll / (float)this.getWidth() * (float)this.mItemCount * 100.0f));
            }
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void computeScroll() {
        if (!this.mScroller.computeScrollOffset()) {
            return;
        }
        int n2 = this.mIsVertical ? this.mScroller.getCurrY() : this.mScroller.getCurrX();
        int n3 = (int)((float)n2 - this.mLastTouchPos);
        this.mLastTouchPos = n2;
        boolean bl2 = this.scrollListItemsBy(n3);
        if (!bl2 && !this.mScroller.isFinished()) {
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        if (bl2) {
            if (ViewCompat.getOverScrollMode((View)this) != 2) {
                EdgeEffectCompat edgeEffectCompat = n3 > 0 ? this.mStartEdge : this.mEndEdge;
                if (edgeEffectCompat.onAbsorb(Math.abs((int)this.getCurrVelocity()))) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
            }
            this.mScroller.abortAnimation();
        }
        this.mTouchMode = -1;
        this.reportScrollStateChange(0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected int computeVerticalScrollExtent() {
        int n2 = this.getChildCount();
        if (n2 == 0) {
            return 0;
        }
        int n3 = n2 * 100;
        View view = this.getChildAt(0);
        int n4 = view.getTop();
        int n5 = view.getHeight();
        int n6 = n3;
        if (n5 > 0) {
            n6 = n3 + n4 * 100 / n5;
        }
        view = this.getChildAt(n2 - 1);
        n2 = view.getBottom();
        n4 = view.getHeight();
        n3 = n6;
        if (n4 <= 0) return n3;
        return n6 - (n2 - this.getHeight()) * 100 / n4;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected int computeVerticalScrollOffset() {
        int n2 = this.mFirstPosition;
        int n3 = this.getChildCount();
        if (n2 < 0) return 0;
        if (n3 == 0) {
            return 0;
        }
        View view = this.getChildAt(0);
        n3 = view.getTop();
        int n4 = view.getHeight();
        if (n4 <= 0) return 0;
        return Math.max(n2 * 100 - n3 * 100 / n4, 0);
    }

    protected int computeVerticalScrollRange() {
        int n2;
        int n3 = n2 = Math.max(this.mItemCount * 100, 0);
        if (this.mIsVertical) {
            n3 = n2;
            if (this.mOverScroll != 0) {
                n3 = n2 + Math.abs((int)((float)this.mOverScroll / (float)this.getHeight() * (float)this.mItemCount * 100.0f));
            }
        }
        return n3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    void confirmCheckedPositionsById() {
        this.mCheckStates.clear();
        var1_1 = 0;
        block0 : do {
            if (var1_1 >= this.mCheckedIdStates.size()) return;
            var6_6 = this.mCheckedIdStates.keyAt(var1_1);
            if (var6_6 != this.mAdapter.getItemId(var3_3 = this.mCheckedIdStates.valueAt(var1_1).intValue())) ** GOTO lbl10
            this.mCheckStates.put(var3_3, true);
            var2_2 = var1_1;
            ** GOTO lbl25
lbl10: // 1 sources:
            var2_2 = Math.max(0, var3_3 - 20);
            var5_5 = Math.min(var3_3 + 20, this.mItemCount);
            var4_4 = 0;
            do {
                var3_3 = var4_4;
                if (var2_2 >= var5_5) ** GOTO lbl20
                if (var6_6 == this.mAdapter.getItemId(var2_2)) {
                    var3_3 = 1;
                    this.mCheckStates.put(var2_2, true);
                    this.mCheckedIdStates.setValueAt(var1_1, var2_2);
lbl20: // 2 sources:
                    var2_2 = var1_1;
                    if (var3_3 == 0) {
                        this.mCheckedIdStates.delete(var6_6);
                        var2_2 = var1_1 - 1;
                        --this.mCheckedItemCount;
                    }
lbl25: // 4 sources:
                    var1_1 = var2_2 + 1;
                    continue block0;
                }
                ++var2_2;
            } while (true);
            break;
        } while (true);
    }

    protected void dispatchDraw(Canvas canvas) {
        boolean bl2 = this.mDrawSelectorOnTop;
        if (!bl2) {
            this.drawSelector(canvas);
        }
        super.dispatchDraw(canvas);
        if (bl2) {
            this.drawSelector(canvas);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean bl2;
        boolean bl3 = bl2 = super.dispatchKeyEvent(keyEvent);
        if (!bl2) {
            bl3 = bl2;
            if (this.getFocusedChild() != null) {
                bl3 = bl2;
                if (keyEvent.getAction() == 0) {
                    bl3 = this.onKeyDown(keyEvent.getKeyCode(), keyEvent);
                }
            }
        }
        return bl3;
    }

    protected void dispatchSetPressed(boolean bl2) {
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        boolean bl2 = false;
        if (this.mStartEdge != null) {
            bl2 = false | this.drawStartEdge(canvas);
        }
        boolean bl3 = bl2;
        if (this.mEndEdge != null) {
            bl3 = bl2 | this.drawEndEdge(canvas);
        }
        if (bl3) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.updateSelectorState();
    }

    /*
     * Enabled aggressive block sorting
     */
    void fillGap(boolean bl2) {
        int n2 = this.getChildCount();
        if (bl2) {
            int n3 = this.mIsVertical ? this.getPaddingTop() : this.getPaddingLeft();
            int n4 = this.getChildEndEdge(this.getChildAt(n2 - 1));
            if (n2 > 0) {
                n3 = n4 + this.mItemMargin;
            }
            this.fillAfter(this.mFirstPosition + n2, n3);
            this.correctTooHigh(this.getChildCount());
            return;
        }
        int n5 = this.getEndEdge();
        int n6 = this.mIsVertical ? this.getChildAt(0).getTop() : this.getChildAt(0).getLeft();
        n6 = n2 > 0 ? (n6 -= this.mItemMargin) : n5;
        this.fillBefore(this.mFirstPosition - 1, n6);
        this.correctTooLow(this.getChildCount());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    boolean fullScroll(int var1_1) {
        this.forceValidFocusDirection(var1_1);
        var3_2 = false;
        if (var1_1 != 33 && var1_1 != 17) ** GOTO lbl13
        var2_3 = var3_2;
        if (this.mSelectedPosition != 0) {
            var1_1 = this.lookForSelectablePosition(0, true);
            if (var1_1 >= 0) {
                this.mLayoutMode = 1;
                this.setSelectionInt(var1_1);
                this.invokeOnItemScrollListener();
            }
            var2_3 = true;
        }
        ** GOTO lbl24
lbl13: // 1 sources:
        if (var1_1 == 130) ** GOTO lbl-1000
        var2_3 = var3_2;
        if (var1_1 == 66) lbl-1000: // 2 sources:
        {
            var2_3 = var3_2;
            if (this.mSelectedPosition < this.mItemCount - 1) {
                var1_1 = this.lookForSelectablePosition(this.mItemCount - 1, true);
                if (var1_1 >= 0) {
                    this.mLayoutMode = 3;
                    this.setSelectionInt(var1_1);
                    this.invokeOnItemScrollListener();
                }
                var2_3 = true;
            }
        }
lbl24: // 6 sources:
        if (var2_3 == false) return var2_3;
        if (this.awakenScrollbarsInternal() != false) return var2_3;
        this.awakenScrollbarsInternal();
        this.invalidate();
        return var2_3;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        if (this.mIsVertical) {
            return new LayoutParams(-1, -2);
        }
        return new LayoutParams(-2, -1);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public long[] getCheckedItemIds() {
        if (this.mChoiceMode == ChoiceMode.NONE) return new long[0];
        if (this.mCheckedIdStates == null) return new long[0];
        if (this.mAdapter == null) {
            return new long[0];
        }
        LongSparseArray<Integer> longSparseArray = this.mCheckedIdStates;
        int n2 = longSparseArray.size();
        long[] arrl = new long[n2];
        int n3 = 0;
        do {
            long[] arrl2 = arrl;
            if (n3 >= n2) return arrl2;
            arrl[n3] = longSparseArray.keyAt(n3);
            ++n3;
        } while (true);
    }

    public int getCheckedItemPosition() {
        if (this.mChoiceMode == ChoiceMode.SINGLE && this.mCheckStates != null && this.mCheckStates.size() == 1) {
            return this.mCheckStates.keyAt(0);
        }
        return -1;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode != ChoiceMode.NONE) {
            return this.mCheckStates;
        }
        return null;
    }

    public ChoiceMode getChoiceMode() {
        return this.mChoiceMode;
    }

    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    public int getCount() {
        return this.mItemCount;
    }

    public int getFirstVisiblePosition() {
        return this.mFirstPosition;
    }

    public void getFocusedRect(Rect rect) {
        View view = this.getSelectedView();
        if (view != null && view.getParent() == this) {
            view.getFocusedRect(rect);
            this.offsetDescendantRectToMyCoords(view, rect);
            return;
        }
        super.getFocusedRect(rect);
    }

    public int getItemMargin() {
        return this.mItemMargin;
    }

    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    public int getLastVisiblePosition() {
        return this.mFirstPosition + this.getChildCount() - 1;
    }

    public int getMaxScrollAmount() {
        return (int)(0.33f * (float)this.getHeight());
    }

    public Orientation getOrientation() {
        if (this.mIsVertical) {
            return Orientation.VERTICAL;
        }
        return Orientation.HORIZONTAL;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getPositionForView(View view) {
        do {
            try {
                View view2 = (View)view.getParent();
                boolean bl2 = view2.equals((Object)this);
                if (bl2) break;
                view = view2;
                continue;
            }
            catch (ClassCastException var1_2) {
                return -1;
            }
        } while (true);
        int n2 = this.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            if (this.getChildAt(n3).equals((Object)view)) {
                return this.mFirstPosition + n3;
            }
            ++n3;
        }
        return -1;
    }

    public long getSelectedItemId() {
        return this.mNextSelectedRowId;
    }

    public int getSelectedItemPosition() {
        return this.mNextSelectedPosition;
    }

    public View getSelectedView() {
        if (this.mItemCount > 0 && this.mSelectedPosition >= 0) {
            return this.getChildAt(this.mSelectedPosition - this.mFirstPosition);
        }
        return null;
    }

    public Drawable getSelector() {
        return this.mSelector;
    }

    public boolean isItemChecked(int n2) {
        if (this.mChoiceMode == ChoiceMode.NONE && this.mCheckStates != null) {
            return this.mCheckStates.get(n2);
        }
        return false;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getViewTreeObserver().addOnTouchModeChangeListener((ViewTreeObserver.OnTouchModeChangeListener)this);
        if (this.mAdapter != null && this.mDataSetObserver == null) {
            this.mDataSetObserver = new AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver((DataSetObserver)this.mDataSetObserver);
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
        this.mIsAttached = true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected int[] onCreateDrawableState(int var1_1) {
        if (this.mIsChildViewEnabled) {
            return super.onCreateDrawableState(var1_1);
        }
        var4_3 = TwoWayView.ENABLED_STATE_SET[0];
        var6_4 = super.onCreateDrawableState(var1_1 + 1);
        var3_5 = -1;
        var1_1 = var6_4.length - 1;
        do {
            var2_6 = var3_5;
            if (var1_1 < 0) ** GOTO lbl12
            if (var6_4[var1_1] == var4_3) {
                var2_6 = var1_1;
lbl12: // 2 sources:
                var5_2 = var6_4;
                if (var2_6 < 0) return var5_2;
                System.arraycopy(var6_4, var2_6 + 1, var6_4, var2_6, var6_4.length - var2_6 - 1);
                return var6_4;
            }
            --var1_1;
        } while (true);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRecycler.clear();
        this.getViewTreeObserver().removeOnTouchModeChangeListener((ViewTreeObserver.OnTouchModeChangeListener)this);
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver((DataSetObserver)this.mDataSetObserver);
            this.mDataSetObserver = null;
        }
        if (this.mPerformClick != null) {
            this.removeCallbacks((Runnable)this.mPerformClick);
        }
        if (this.mTouchModeReset != null) {
            this.removeCallbacks(this.mTouchModeReset);
            this.mTouchModeReset.run();
        }
        this.mIsAttached = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onFocusChanged(boolean bl2, int n2, Rect rect) {
        super.onFocusChanged(bl2, n2, rect);
        if (bl2 && this.mSelectedPosition < 0 && !this.isInTouchMode()) {
            if (!this.mIsAttached && this.mAdapter != null) {
                this.mDataChanged = true;
                this.mOldItemCount = this.mItemCount;
                this.mItemCount = this.mAdapter.getCount();
            }
            this.resurrectSelection();
        }
        ListAdapter listAdapter = this.mAdapter;
        int n3 = -1;
        int n4 = 0;
        int n5 = 0;
        int n6 = n4;
        int n7 = n3;
        if (listAdapter != null) {
            n6 = n4;
            n7 = n3;
            if (bl2) {
                n6 = n4;
                n7 = n3;
                if (rect != null) {
                    rect.offset(this.getScrollX(), this.getScrollY());
                    if (listAdapter.getCount() < this.getChildCount() + this.mFirstPosition) {
                        this.mLayoutMode = 0;
                        this.layoutChildren();
                    }
                    Rect rect2 = this.mTempRect;
                    int n8 = Integer.MAX_VALUE;
                    int n9 = this.getChildCount();
                    int n10 = this.mFirstPosition;
                    n4 = 0;
                    do {
                        n6 = n5;
                        n7 = n3;
                        if (n4 >= n9) break;
                        if (!listAdapter.isEnabled(n10 + n4)) {
                            n6 = n8;
                        } else {
                            View view = this.getChildAt(n4);
                            view.getDrawingRect(rect2);
                            this.offsetDescendantRectToMyCoords(view, rect2);
                            n7 = TwoWayView.getDistance(rect, rect2, n2);
                            n6 = n8;
                            if (n7 < n8) {
                                n6 = n7;
                                n8 = n4;
                                n3 = this.mIsVertical ? view.getTop() : view.getLeft();
                                n5 = n3;
                                n3 = n8;
                            }
                        }
                        ++n4;
                        n8 = n6;
                    } while (true);
                }
            }
        }
        if (n7 >= 0) {
            this.setSelectionFromOffset(this.mFirstPosition + n7, n6);
            return;
        }
        this.requestLayout();
    }

    @TargetApi(value=14)
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)TwoWayView.class.getName());
    }

    @TargetApi(value=14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
        super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        object.setClassName((CharSequence)TwoWayView.class.getName());
        object = new AccessibilityNodeInfoCompat(object);
        if (this.isEnabled()) {
            if (this.getFirstVisiblePosition() > 0) {
                object.addAction(8192);
            }
            if (this.getLastVisiblePosition() < this.getCount() - 1) {
                object.addAction(4096);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl2 = true;
        if (!this.mIsAttached) return false;
        if (this.mAdapter == null) {
            return false;
        }
        switch (motionEvent.getAction() & 255) {
            case 0: {
                this.initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mScroller.abortAnimation();
                float f2 = motionEvent.getX();
                float f3 = motionEvent.getY();
                if (this.mIsVertical) {
                    f2 = f3;
                }
                this.mLastTouchPos = f2;
                int n2 = this.findMotionRowOrColumn((int)this.mLastTouchPos);
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.mTouchRemainderPos = 0.0f;
                if (this.mTouchMode == 4) return bl2;
                if (n2 < 0) return false;
                this.mMotionPosition = n2;
                this.mTouchMode = 0;
            }
            default: {
                return false;
            }
            case 2: {
                if (this.mTouchMode != 0) return false;
                this.initVelocityTrackerIfNotExists();
                this.mVelocityTracker.addMovement(motionEvent);
                int n3 = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                if (n3 < 0) {
                    Log.e((String)"TwoWayView", (String)("onInterceptTouchEvent could not find pointer with id " + this.mActivePointerId + " - did TwoWayView receive an inconsistent " + "event stream?"));
                    return false;
                }
                float f4 = this.mIsVertical ? MotionEventCompat.getY(motionEvent, n3) : MotionEventCompat.getX(motionEvent, n3);
                f4 = f4 - this.mLastTouchPos + this.mTouchRemainderPos;
                n3 = (int)f4;
                this.mTouchRemainderPos = f4 - (float)n3;
                if (!this.maybeStartScrolling(n3)) return false;
                return true;
            }
            case 1: 
            case 3: 
        }
        this.mActivePointerId = -1;
        this.mTouchMode = -1;
        this.recycleVelocityTracker();
        this.reportScrollStateChange(0);
        return false;
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        return this.handleKeyEvent(n2, 1, keyEvent);
    }

    public boolean onKeyMultiple(int n2, int n3, KeyEvent keyEvent) {
        return this.handleKeyEvent(n2, n3, keyEvent);
    }

    public boolean onKeyUp(int n2, KeyEvent keyEvent) {
        return this.handleKeyEvent(n2, 1, keyEvent);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        this.mInLayout = true;
        if (bl2) {
            int n6 = this.getChildCount();
            for (int i2 = 0; i2 < n6; ++i2) {
                this.getChildAt(i2).forceLayout();
            }
            this.mRecycler.markChildrenDirty();
        }
        this.layoutChildren();
        this.mInLayout = false;
        n2 = n4 - n2 - this.getPaddingLeft() - this.getPaddingRight();
        n3 = n5 - n3 - this.getPaddingTop() - this.getPaddingBottom();
        if (this.mStartEdge == null || this.mEndEdge == null) return;
        if (this.mIsVertical) {
            this.mStartEdge.setSize(n2, n3);
            this.mEndEdge.setSize(n2, n3);
            return;
        }
        this.mStartEdge.setSize(n3, n2);
        this.mEndEdge.setSize(n3, n2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onMeasure(int var1_1, int var2_2) {
        if (this.mSelector == null) {
            this.useDefaultSelector();
        }
        var10_3 = View.MeasureSpec.getMode((int)var1_1);
        var11_4 = View.MeasureSpec.getMode((int)var2_2);
        var7_5 = View.MeasureSpec.getSize((int)var1_1);
        var6_6 = View.MeasureSpec.getSize((int)var2_2);
        var8_7 = 0;
        var9_8 = 0;
        var3_9 = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        this.mItemCount = var3_9;
        var5_10 = var9_8;
        var4_11 = var8_7;
        if (this.mItemCount <= 0) ** GOTO lbl28
        if (var10_3 == 0) ** GOTO lbl-1000
        var5_10 = var9_8;
        var4_11 = var8_7;
        if (var11_4 == 0) lbl-1000: // 2 sources:
        {
            var12_12 = this.obtainView(0, this.mIsScrap);
            var3_9 = this.mIsVertical != false ? var1_1 : var2_2;
            this.measureScrapChild(var12_12, 0, var3_9);
            var3_9 = var12_12.getMeasuredWidth();
            var5_10 = var8_7 = var12_12.getMeasuredHeight();
            var4_11 = var3_9;
            if (this.recycleOnMeasure()) {
                this.mRecycler.addScrapView(var12_12, -1);
                var4_11 = var3_9;
                var5_10 = var8_7;
            }
        }
lbl28: // 6 sources:
        var3_9 = var7_5;
        if (var10_3 == 0) {
            var3_9 = var4_11 = this.getPaddingLeft() + this.getPaddingRight() + var4_11;
            if (this.mIsVertical) {
                var3_9 = var4_11 + this.getVerticalScrollbarWidth();
            }
        }
        var4_11 = var6_6;
        if (var11_4 == 0) {
            var4_11 = var5_10 = this.getPaddingTop() + this.getPaddingBottom() + var5_10;
            if (!this.mIsVertical) {
                var4_11 = var5_10 + this.getHorizontalScrollbarHeight();
            }
        }
        var5_10 = var4_11;
        if (this.mIsVertical) {
            var5_10 = var4_11;
            if (var11_4 == Integer.MIN_VALUE) {
                var5_10 = this.measureHeightOfChildren(var1_1, 0, -1, var4_11, -1);
            }
        }
        var1_1 = var3_9;
        if (!this.mIsVertical) {
            var1_1 = var3_9;
            if (var10_3 == Integer.MIN_VALUE) {
                var1_1 = this.measureWidthOfChildren(var2_2, 0, -1, var3_9, -1);
            }
        }
        this.setMeasuredDimension(var1_1, var5_10);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onOverScrolled(int n2, int n3, boolean bl2, boolean bl3) {
        int n4 = 0;
        if (this.mIsVertical && this.mOverScroll != n3) {
            this.onScrollChanged(this.getScrollX(), n3, this.getScrollX(), this.mOverScroll);
            this.mOverScroll = n3;
            n3 = 1;
        } else {
            n3 = n4;
            if (!this.mIsVertical) {
                n3 = n4;
                if (this.mOverScroll != n2) {
                    this.onScrollChanged(n2, this.getScrollY(), this.mOverScroll, this.getScrollY());
                    this.mOverScroll = n2;
                    n3 = 1;
                }
            }
        }
        if (n3 != 0) {
            this.invalidate();
            this.awakenScrollbarsInternal();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = object.height;
        if (object.selectedId >= 0) {
            this.mNeedSync = true;
            this.mPendingSync = object;
            this.mSyncRowId = object.selectedId;
            this.mSyncPosition = object.position;
            this.mSpecificStart = object.viewStart;
            this.mSyncMode = 0;
        } else if (object.firstId >= 0) {
            this.setSelectedPositionInt(-1);
            this.setNextSelectedPositionInt(-1);
            this.mSelectorPosition = -1;
            this.mNeedSync = true;
            this.mPendingSync = object;
            this.mSyncRowId = object.firstId;
            this.mSyncPosition = object.position;
            this.mSpecificStart = object.viewStart;
            this.mSyncMode = 1;
        }
        if (object.checkState != null) {
            this.mCheckStates = object.checkState;
        }
        if (object.checkIdState != null) {
            this.mCheckedIdStates = object.checkIdState;
        }
        this.mCheckedItemCount = object.checkedItemCount;
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Parcelable onSaveInstanceState() {
        long l2;
        int n2;
        Object object;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSync != null) {
            savedState.selectedId = this.mPendingSync.selectedId;
            savedState.firstId = this.mPendingSync.firstId;
            savedState.viewStart = this.mPendingSync.viewStart;
            savedState.position = this.mPendingSync.position;
            savedState.height = this.mPendingSync.height;
            return savedState;
        }
        int n3 = this.getChildCount() > 0 && this.mItemCount > 0 ? 1 : 0;
        savedState.selectedId = l2 = this.getSelectedItemId();
        savedState.height = this.getHeight();
        if (l2 >= 0) {
            savedState.viewStart = this.mSelectedStart;
            savedState.position = this.getSelectedItemPosition();
            savedState.firstId = -1;
        } else if (n3 != 0 && this.mFirstPosition > 0) {
            object = this.getChildAt(0);
            n3 = this.mIsVertical ? object.getTop() : object.getLeft();
            savedState.viewStart = n3;
            n3 = n2 = this.mFirstPosition;
            if (n2 >= this.mItemCount) {
                n3 = this.mItemCount - 1;
            }
            savedState.position = n3;
            savedState.firstId = this.mAdapter.getItemId(n3);
        } else {
            savedState.viewStart = 0;
            savedState.firstId = -1;
            savedState.position = 0;
        }
        if (this.mCheckStates != null) {
            savedState.checkState = this.cloneCheckStates();
        }
        if (this.mCheckedIdStates != null) {
            object = new LongSparseArray();
            n2 = this.mCheckedIdStates.size();
            for (n3 = 0; n3 < n2; ++n3) {
                object.put(this.mCheckedIdStates.keyAt(n3), this.mCheckedIdStates.valueAt(n3));
            }
            savedState.checkIdState = object;
        }
        savedState.checkedItemCount = this.mCheckedItemCount;
        return savedState;
    }

    /*
     * Exception decompiling
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:486)
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

    /*
     * Enabled aggressive block sorting
     */
    public void onTouchModeChanged(boolean bl2) {
        if (bl2) {
            this.hideSelector();
            if (this.getWidth() > 0 && this.getHeight() > 0 && this.getChildCount() > 0) {
                this.layoutChildren();
            }
            this.updateSelectorState();
            return;
        } else {
            if (this.mTouchMode != 5 || this.mOverScroll == 0) return;
            {
                this.mOverScroll = 0;
                this.finishEdgeGlows();
                this.invalidate();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onWindowFocusChanged(boolean bl2) {
        super.onWindowFocusChanged(bl2);
        int n2 = this.isInTouchMode() ? 0 : 1;
        if (!bl2) {
            if (n2 == 1) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
        } else if (n2 != this.mLastTouchMode && this.mLastTouchMode != -1) {
            if (n2 == 1) {
                this.resurrectSelection();
            } else {
                this.hideSelector();
                this.mLayoutMode = 0;
                this.layoutChildren();
            }
        }
        this.mLastTouchMode = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean pageScroll(int n2) {
        this.forceValidFocusDirection(n2);
        boolean bl2 = false;
        int n3 = -1;
        if (n2 == 33 || n2 == 17) {
            n3 = Math.max(0, this.mSelectedPosition - this.getChildCount() - 1);
        } else if (n2 == 130 || n2 == 66) {
            n3 = Math.min(this.mItemCount - 1, this.mSelectedPosition + this.getChildCount() - 1);
            bl2 = true;
        }
        if (n3 < 0 || (n3 = this.lookForSelectablePosition(n3, bl2)) < 0) {
            return false;
        }
        this.mLayoutMode = 4;
        n2 = this.mIsVertical ? this.getPaddingTop() : this.getPaddingLeft();
        this.mSpecificStart = n2;
        if (bl2 && n3 > this.mItemCount - this.getChildCount()) {
            this.mLayoutMode = 3;
        }
        if (!bl2 && n3 < this.getChildCount()) {
            this.mLayoutMode = 1;
        }
        this.setSelectionInt(n3);
        this.invokeOnItemScrollListener();
        if (!this.awakenScrollbarsInternal()) {
            this.invalidate();
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=16)
    public boolean performAccessibilityAction(int n2, Bundle bundle) {
        if (super.performAccessibilityAction(n2, bundle)) {
            return true;
        }
        switch (n2) {
            default: {
                return false;
            }
            case 4096: {
                if (this.isEnabled() && this.getLastVisiblePosition() < this.getCount() - 1) {
                    n2 = this.mIsVertical ? this.getHeight() - this.getPaddingTop() - this.getPaddingBottom() : this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                    this.scrollListItemsBy(n2);
                    return true;
                }
                return false;
            }
            case 8192: 
        }
        if (this.isEnabled() && this.mFirstPosition > 0) {
            n2 = this.mIsVertical ? this.getHeight() - this.getPaddingTop() - this.getPaddingBottom() : this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
            this.scrollListItemsBy(- n2);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean performItemClick(View view, int n2, long l2) {
        boolean bl2 = false;
        if (this.mChoiceMode == ChoiceMode.MULTIPLE) {
            boolean bl3 = !this.mCheckStates.get(n2, false);
            this.mCheckStates.put(n2, bl3);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (bl3) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(n2), n2);
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(n2));
                }
            }
            this.mCheckedItemCount = bl3 ? ++this.mCheckedItemCount : --this.mCheckedItemCount;
            bl2 = true;
        } else if (this.mChoiceMode == ChoiceMode.SINGLE) {
            bl2 = !this.mCheckStates.get(n2, false);
            if (bl2) {
                this.mCheckStates.clear();
                this.mCheckStates.put(n2, true);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    this.mCheckedIdStates.clear();
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(n2), n2);
                }
                this.mCheckedItemCount = 1;
            } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                this.mCheckedItemCount = 0;
            }
            bl2 = true;
        }
        if (bl2) {
            this.updateOnScreenCheckedViews();
        }
        return super.performItemClick(view, n2, l2);
    }

    public int pointToPosition(int n2, int n3) {
        Rect rect;
        Rect rect2 = rect = this.mTouchFrame;
        if (rect == null) {
            rect2 = this.mTouchFrame = new Rect();
        }
        for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
            rect = this.getChildAt(i2);
            if (rect.getVisibility() != 0) continue;
            rect.getHitRect(rect2);
            if (!rect2.contains(n2, n3)) continue;
            return this.mFirstPosition + i2;
        }
        return -1;
    }

    protected boolean recycleOnMeasure() {
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean bl2) {
        if (bl2) {
            this.recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(bl2);
    }

    public void requestLayout() {
        if (!this.mInLayout && !this.mBlockLayoutRequests) {
            super.requestLayout();
        }
    }

    void resetState() {
        this.mScroller.forceFinished(true);
        this.removeAllViewsInLayout();
        this.mSelectedStart = 0;
        this.mFirstPosition = 0;
        this.mDataChanged = false;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        this.mOverScroll = 0;
        this.setSelectedPositionInt(-1);
        this.setNextSelectedPositionInt(-1);
        this.mSelectorPosition = -1;
        this.mSelectorRect.setEmpty();
        this.invalidate();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    boolean resurrectSelection() {
        var7_1 = this.getChildCount();
        if (var7_1 <= 0) {
            return false;
        }
        var1_2 = 0;
        var3_3 = 0;
        var8_4 = this.getStartEdge();
        var9_5 = this.getEndEdge();
        var6_6 = this.mFirstPosition;
        var2_7 = this.mResurrectToPosition;
        var11_8 = true;
        if (var2_7 < var6_6 || var2_7 >= var6_6 + var7_1) ** GOTO lbl20
        var12_9 = this.getChildAt(var2_7 - this.mFirstPosition);
        if (this.mIsVertical) {
            var1_2 = var12_9.getTop();
            var10_12 = var11_8;
        } else {
            var1_2 = var12_9.getLeft();
            var10_12 = var11_8;
        }
        ** GOTO lbl22
lbl20: // 1 sources:
        if (var2_7 < var6_6) ** GOTO lbl37
        ** GOTO lbl54
lbl22: // 6 sources:
        do {
            this.mResurrectToPosition = -1;
            this.mTouchMode = -1;
            this.reportScrollStateChange(0);
            this.mSpecificStart = var1_2;
            var1_2 = this.lookForSelectablePosition(var2_7, var10_12);
            if (var1_2 >= var6_6 && var1_2 <= this.getLastVisiblePosition()) {
                this.mLayoutMode = 4;
                this.updateSelectorState();
                this.setSelectionInt(var1_2);
                this.invokeOnItemScrollListener();
            } else {
                var1_2 = -1;
            }
            if (var1_2 < 0) return false;
            return true;
            break;
        } while (true);
lbl37: // 1 sources:
        var4_13 = var6_6;
        var5_15 = 0;
        do {
            var10_12 = var11_8;
            var2_7 = var4_13;
            var1_2 = var3_3;
            if (var5_15 >= var7_1) ** GOTO lbl22
            var12_10 = this.getChildAt(var5_15);
            var1_2 = this.mIsVertical != false ? var12_10.getTop() : var12_10.getLeft();
            if (var5_15 == 0) {
                var3_3 = var1_2;
            }
            if (var1_2 < var8_4) ** GOTO lbl52
            var2_7 = var6_6 + var5_15;
            var10_12 = var11_8;
            ** GOTO lbl22
lbl52: // 1 sources:
            ++var5_15;
        } while (true);
lbl54: // 1 sources:
        var5_16 = var6_6 + var7_1 - 1;
        var11_8 = false;
        var4_14 = var7_1 - 1;
        var3_3 = var1_2;
        do {
            var10_12 = var11_8;
            var2_7 = var5_16;
            var1_2 = var3_3;
            if (var4_14 < 0) ** GOTO lbl22
            var12_11 = this.getChildAt(var4_14);
            var1_2 = this.getChildStartEdge(var12_11);
            var2_7 = this.getChildEndEdge(var12_11);
            if (var4_14 == var7_1 - 1) {
                var3_3 = var1_2;
            }
            if (var2_7 <= var9_5) {
                var2_7 = var6_6 + var4_14;
                var10_12 = var11_8;
                ** continue;
            }
            --var4_14;
        } while (true);
    }

    boolean resurrectSelectionIfNeeded() {
        if (this.mSelectedPosition < 0 && this.resurrectSelection()) {
            this.updateSelectorState();
            return true;
        }
        return false;
    }

    public void scrollBy(int n2) {
        this.scrollListItemsBy(n2);
    }

    public void sendAccessibilityEvent(int n2) {
        if (n2 == 4096) {
            int n3 = this.getFirstVisiblePosition();
            int n4 = this.getLastVisiblePosition();
            if (this.mLastAccessibilityScrollEventFromIndex == n3 && this.mLastAccessibilityScrollEventToIndex == n4) {
                return;
            }
            this.mLastAccessibilityScrollEventFromIndex = n3;
            this.mLastAccessibilityScrollEventToIndex = n4;
        }
        super.sendAccessibilityEvent(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(ListAdapter listAdapter) {
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver((DataSetObserver)this.mDataSetObserver);
        }
        this.resetState();
        this.mRecycler.clear();
        this.mAdapter = listAdapter;
        this.mDataChanged = true;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = listAdapter.getCount();
            this.mDataSetObserver = new AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver((DataSetObserver)this.mDataSetObserver);
            this.mRecycler.setViewTypeCount(listAdapter.getViewTypeCount());
            this.mHasStableIds = listAdapter.hasStableIds();
            this.mAreAllItemsSelectable = listAdapter.areAllItemsEnabled();
            if (this.mChoiceMode != ChoiceMode.NONE && this.mHasStableIds && this.mCheckedIdStates == null) {
                this.mCheckedIdStates = new LongSparseArray();
            }
            int n2 = this.lookForSelectablePosition(0);
            this.setSelectedPositionInt(n2);
            this.setNextSelectedPositionInt(n2);
            if (this.mItemCount == 0) {
                this.checkSelectionChanged();
            }
        } else {
            this.mItemCount = 0;
            this.mHasStableIds = false;
            this.mAreAllItemsSelectable = true;
            this.checkSelectionChanged();
        }
        this.checkFocus();
        this.requestLayout();
    }

    public void setChoiceMode(ChoiceMode choiceMode) {
        this.mChoiceMode = choiceMode;
        if (this.mChoiceMode != ChoiceMode.NONE) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new SparseBooleanArray();
            }
            if (this.mCheckedIdStates == null && this.mAdapter != null && this.mAdapter.hasStableIds()) {
                this.mCheckedIdStates = new LongSparseArray();
            }
        }
    }

    public void setDrawSelectorOnTop(boolean bl2) {
        this.mDrawSelectorOnTop = bl2;
    }

    public void setEmptyView(View view) {
        super.setEmptyView(view);
        this.mEmptyView = view;
        this.updateEmptyStatus();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setFocusable(boolean bl2) {
        boolean bl3 = true;
        ListAdapter listAdapter = this.getAdapter();
        boolean bl4 = listAdapter == null || listAdapter.getCount() == 0;
        this.mDesiredFocusableState = bl2;
        if (!bl2) {
            this.mDesiredFocusableInTouchModeState = false;
        }
        bl2 = bl2 && !bl4 ? bl3 : false;
        super.setFocusable(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setFocusableInTouchMode(boolean bl2) {
        boolean bl3 = true;
        ListAdapter listAdapter = this.getAdapter();
        boolean bl4 = listAdapter == null || listAdapter.getCount() == 0;
        this.mDesiredFocusableInTouchModeState = bl2;
        if (bl2) {
            this.mDesiredFocusableState = true;
        }
        bl2 = bl2 && !bl4 ? bl3 : false;
        super.setFocusableInTouchMode(bl2);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void setItemChecked(int n2, boolean bl2) {
        if (this.mChoiceMode == ChoiceMode.NONE) {
            return;
        }
        if (this.mChoiceMode == ChoiceMode.MULTIPLE) {
            boolean bl3 = this.mCheckStates.get(n2);
            this.mCheckStates.put(n2, bl2);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (bl2) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(n2), n2);
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(n2));
                }
            }
            if (bl3 != bl2) {
                this.mCheckedItemCount = bl2 ? ++this.mCheckedItemCount : --this.mCheckedItemCount;
            }
        } else {
            boolean bl4 = this.mCheckedIdStates != null && this.mAdapter.hasStableIds();
            if (bl2 || this.isItemChecked(n2)) {
                this.mCheckStates.clear();
                if (bl4) {
                    this.mCheckedIdStates.clear();
                }
            }
            if (bl2) {
                this.mCheckStates.put(n2, true);
                if (bl4) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(n2), n2);
                }
                this.mCheckedItemCount = 1;
            } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                this.mCheckedItemCount = 0;
            }
        }
        if (this.mInLayout) return;
        if (this.mBlockLayoutRequests) return;
        this.mDataChanged = true;
        this.rememberSyncState();
        this.requestLayout();
    }

    public void setItemMargin(int n2) {
        if (this.mItemMargin == n2) {
            return;
        }
        this.mItemMargin = n2;
        this.requestLayout();
    }

    public void setItemsCanFocus(boolean bl2) {
        this.mItemsCanFocus = bl2;
        if (!bl2) {
            this.setDescendantFocusability(393216);
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
        this.invokeOnItemScrollListener();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setOrientation(Orientation orientation) {
        boolean bl2 = orientation == Orientation.VERTICAL;
        if (this.mIsVertical == bl2) {
            return;
        }
        this.mIsVertical = bl2;
        this.resetState();
        this.mRecycler.clear();
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=9)
    public void setOverScrollMode(int n2) {
        if (Build.VERSION.SDK_INT < 9) {
            return;
        }
        if (n2 != 2) {
            if (this.mStartEdge == null) {
                Context context = this.getContext();
                this.mStartEdge = new EdgeEffectCompat(context);
                this.mEndEdge = new EdgeEffectCompat(context);
            }
        } else {
            this.mStartEdge = null;
            this.mEndEdge = null;
        }
        super.setOverScrollMode(n2);
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecycler.mRecyclerListener = recyclerListener;
    }

    public void setSelection(int n2) {
        this.setSelectionFromOffset(n2, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void setSelectionFromOffset(int n2, int n3) {
        if (this.mAdapter == null) {
            return;
        }
        if (!this.isInTouchMode()) {
            int n4;
            n2 = n4 = this.lookForSelectablePosition(n2);
            if (n4 >= 0) {
                this.setNextSelectedPositionInt(n4);
                n2 = n4;
            }
        } else {
            this.mResurrectToPosition = n2;
        }
        if (n2 < 0) return;
        this.mLayoutMode = 4;
        this.mSpecificStart = this.mIsVertical ? this.getPaddingTop() + n3 : this.getPaddingLeft() + n3;
        if (this.mNeedSync) {
            this.mSyncPosition = n2;
            this.mSyncRowId = this.mAdapter.getItemId(n2);
        }
        this.requestLayout();
    }

    public void setSelector(int n2) {
        this.setSelector(this.getResources().getDrawable(n2));
    }

    public void setSelector(Drawable drawable2) {
        if (this.mSelector != null) {
            this.mSelector.setCallback(null);
            this.unscheduleDrawable(this.mSelector);
        }
        this.mSelector = drawable2;
        drawable2.getPadding(new Rect());
        drawable2.setCallback((Drawable.Callback)this);
        this.updateSelectorState();
    }

    public boolean showContextMenuForChild(View view) {
        int n2 = this.getPositionForView(view);
        if (n2 >= 0) {
            long l2 = this.mAdapter.getItemId(n2);
            boolean bl2 = false;
            AdapterView.OnItemLongClickListener onItemLongClickListener = this.getOnItemLongClickListener();
            if (onItemLongClickListener != null) {
                bl2 = onItemLongClickListener.onItemLongClick((AdapterView)this, view, n2, l2);
            }
            boolean bl3 = bl2;
            if (!bl2) {
                this.mContextMenuInfo = this.createContextMenuInfo(this.getChildAt(n2 - this.mFirstPosition), n2, l2);
                bl3 = super.showContextMenuForChild(view);
            }
            return bl3;
        }
        return false;
    }

    private class AdapterDataSetObserver
    extends DataSetObserver {
        private Parcelable mInstanceState;

        private AdapterDataSetObserver() {
            this.mInstanceState = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onChanged() {
            TwoWayView.this.mDataChanged = true;
            TwoWayView.this.mOldItemCount = TwoWayView.this.mItemCount;
            TwoWayView.this.mItemCount = TwoWayView.this.getAdapter().getCount();
            if (TwoWayView.this.mHasStableIds && this.mInstanceState != null && TwoWayView.this.mOldItemCount == 0 && TwoWayView.this.mItemCount > 0) {
                TwoWayView.this.onRestoreInstanceState(this.mInstanceState);
                this.mInstanceState = null;
            } else {
                TwoWayView.this.rememberSyncState();
            }
            TwoWayView.this.checkFocus();
            TwoWayView.this.requestLayout();
        }

        public void onInvalidated() {
            TwoWayView.this.mDataChanged = true;
            if (TwoWayView.this.mHasStableIds) {
                this.mInstanceState = TwoWayView.this.onSaveInstanceState();
            }
            TwoWayView.this.mOldItemCount = TwoWayView.this.mItemCount;
            TwoWayView.this.mItemCount = 0;
            TwoWayView.this.mSelectedPosition = -1;
            TwoWayView.this.mSelectedRowId = Long.MIN_VALUE;
            TwoWayView.this.mNextSelectedPosition = -1;
            TwoWayView.this.mNextSelectedRowId = Long.MIN_VALUE;
            TwoWayView.this.mNeedSync = false;
            TwoWayView.this.checkFocus();
            TwoWayView.this.requestLayout();
        }
    }

    private static class ArrowScrollFocusResult {
        private int mAmountToScroll;
        private int mSelectedPosition;

        private ArrowScrollFocusResult() {
        }

        public int getAmountToScroll() {
            return this.mAmountToScroll;
        }

        public int getSelectedPosition() {
            return this.mSelectedPosition;
        }

        void populate(int n2, int n3) {
            this.mSelectedPosition = n2;
            this.mAmountToScroll = n3;
        }
    }

    private class CheckForKeyLongPress
    extends WindowRunnnable
    implements Runnable {
        private CheckForKeyLongPress() {
            super();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void run() {
            if (!TwoWayView.this.isPressed() || TwoWayView.this.mSelectedPosition < 0) return;
            int n2 = TwoWayView.this.mSelectedPosition;
            int n3 = TwoWayView.this.mFirstPosition;
            View view = TwoWayView.this.getChildAt(n2 - n3);
            if (!TwoWayView.this.mDataChanged) {
                boolean bl2 = false;
                if (!this.sameWindow()) return;
                bl2 = TwoWayView.this.performLongPress(view, TwoWayView.this.mSelectedPosition, TwoWayView.this.mSelectedRowId);
                if (!bl2) return;
                {
                    TwoWayView.this.setPressed(false);
                    view.setPressed(false);
                    return;
                }
            }
            TwoWayView.this.setPressed(false);
            if (view == null) {
                return;
            }
            view.setPressed(false);
        }
    }

    private class CheckForLongPress
    extends WindowRunnnable
    implements Runnable {
        private CheckForLongPress() {
            super();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            boolean bl2;
            int n2 = TwoWayView.this.mMotionPosition;
            View view = TwoWayView.this.getChildAt(n2 - TwoWayView.this.mFirstPosition);
            if (view == null) return;
            long l2 = TwoWayView.this.mAdapter.getItemId(TwoWayView.this.mMotionPosition);
            boolean bl3 = bl2 = false;
            if (this.sameWindow()) {
                bl3 = bl2;
                if (!TwoWayView.this.mDataChanged) {
                    bl3 = TwoWayView.this.performLongPress(view, n2, l2);
                }
                if (bl3) {
                    TwoWayView.this.mTouchMode = -1;
                    TwoWayView.this.setPressed(false);
                    view.setPressed(false);
                    return;
                }
            }
            TwoWayView.this.mTouchMode = 2;
        }
    }

    private final class CheckForTap
    implements Runnable {
        private CheckForTap() {
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            if (TwoWayView.this.mTouchMode != 0) {
                return;
            }
            TwoWayView.this.mTouchMode = 1;
            View view = TwoWayView.this.getChildAt(TwoWayView.this.mMotionPosition - TwoWayView.this.mFirstPosition);
            if (view == null) return;
            if (view.hasFocusable()) return;
            TwoWayView.this.mLayoutMode = 0;
            if (TwoWayView.this.mDataChanged) {
                TwoWayView.this.mTouchMode = 2;
                return;
            }
            TwoWayView.this.setPressed(true);
            view.setPressed(true);
            TwoWayView.this.layoutChildren();
            TwoWayView.this.positionSelector(TwoWayView.this.mMotionPosition, view);
            TwoWayView.this.refreshDrawableState();
            TwoWayView.this.positionSelector(TwoWayView.this.mMotionPosition, view);
            TwoWayView.this.refreshDrawableState();
            boolean bl2 = TwoWayView.this.isLongClickable();
            if (TwoWayView.this.mSelector != null && (view = TwoWayView.this.mSelector.getCurrent()) != null && view instanceof TransitionDrawable) {
                if (bl2) {
                    int n2 = ViewConfiguration.getLongPressTimeout();
                    ((TransitionDrawable)view).startTransition(n2);
                } else {
                    ((TransitionDrawable)view).resetTransition();
                }
            }
            if (bl2) {
                TwoWayView.this.triggerCheckForLongPress();
                return;
            }
            TwoWayView.this.mTouchMode = 2;
        }
    }

    public static enum ChoiceMode {
        NONE,
        SINGLE,
        MULTIPLE;
        

        private ChoiceMode() {
        }
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        boolean forceAdd;
        long id = -1;
        int scrappedFromPosition;
        int viewType;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
            if (this.width == -1) {
                Log.w((String)"TwoWayView", (String)"Constructing LayoutParams with width FILL_PARENT does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.width = -2;
            }
            if (this.height == -1) {
                Log.w((String)"TwoWayView", (String)"Constructing LayoutParams with height FILL_PARENT does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.height = -2;
            }
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            if (this.width == -1) {
                Log.w((String)"TwoWayView", (String)"Inflation setting LayoutParams width to MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.width = -1;
            }
            if (this.height == -1) {
                Log.w((String)"TwoWayView", (String)"Inflation setting LayoutParams height to MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.height = -2;
            }
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            if (this.width == -1) {
                Log.w((String)"TwoWayView", (String)"Constructing LayoutParams with width MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.width = -2;
            }
            if (this.height == -1) {
                Log.w((String)"TwoWayView", (String)"Constructing LayoutParams with height MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.height = -2;
            }
        }
    }

    private class ListItemAccessibilityDelegate
    extends AccessibilityDelegateCompat {
        private ListItemAccessibilityDelegate() {
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            int n2 = TwoWayView.this.getPositionForView(view);
            view = TwoWayView.this.getAdapter();
            if (n2 == -1) return;
            if (view == null) {
                return;
            }
            if (!TwoWayView.this.isEnabled()) return;
            if (!view.isEnabled(n2)) return;
            if (n2 == TwoWayView.this.getSelectedItemPosition()) {
                accessibilityNodeInfoCompat.setSelected(true);
                accessibilityNodeInfoCompat.addAction(8);
            } else {
                accessibilityNodeInfoCompat.addAction(4);
            }
            if (TwoWayView.this.isClickable()) {
                accessibilityNodeInfoCompat.addAction(16);
                accessibilityNodeInfoCompat.setClickable(true);
            }
            if (!TwoWayView.this.isLongClickable()) return;
            accessibilityNodeInfoCompat.addAction(32);
            accessibilityNodeInfoCompat.setLongClickable(true);
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean performAccessibilityAction(View view, int n2, Bundle bundle) {
            if (super.performAccessibilityAction(view, n2, bundle)) {
                return true;
            }
            int n3 = TwoWayView.this.getPositionForView(view);
            bundle = TwoWayView.this.getAdapter();
            if (n3 == -1) return false;
            if (bundle == null) {
                return false;
            }
            if (!TwoWayView.this.isEnabled()) return false;
            if (!bundle.isEnabled(n3)) {
                return false;
            }
            long l2 = TwoWayView.this.getItemIdAtPosition(n3);
            switch (n2) {
                default: {
                    return false;
                }
                case 8: {
                    if (TwoWayView.this.getSelectedItemPosition() != n3) return false;
                    TwoWayView.this.setSelection(-1);
                    return true;
                }
                case 4: {
                    if (TwoWayView.this.getSelectedItemPosition() == n3) return false;
                    TwoWayView.this.setSelection(n3);
                    return true;
                }
                case 16: {
                    if (!TwoWayView.this.isClickable()) return false;
                    if (TwoWayView.this.performItemClick(view, n3, l2)) return true;
                    return false;
                }
                case 32: 
            }
            if (!TwoWayView.this.isLongClickable()) return false;
            if (TwoWayView.this.performLongPress(view, n3, l2)) return true;
            return false;
        }
    }

    public static interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        public void onScroll(TwoWayView var1, int var2, int var3, int var4);

        public void onScrollStateChanged(TwoWayView var1, int var2);
    }

    public static enum Orientation {
        HORIZONTAL,
        VERTICAL;
        

        private Orientation() {
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
            if (TwoWayView.this.mDataChanged) {
                return;
            }
            ListAdapter listAdapter = TwoWayView.this.mAdapter;
            int n2 = this.mClickMotionPosition;
            if (listAdapter == null) return;
            if (TwoWayView.this.mItemCount <= 0) return;
            if (n2 == -1) return;
            if (n2 >= listAdapter.getCount()) return;
            if (!this.sameWindow()) return;
            View view = TwoWayView.this.getChildAt(n2 - TwoWayView.this.mFirstPosition);
            if (view == null) return;
            TwoWayView.this.performItemClick(view, n2, listAdapter.getItemId(n2));
        }
    }

    class RecycleBin {
        private View[] mActiveViews;
        private ArrayList<View> mCurrentScrap;
        private int mFirstActivePosition;
        private RecyclerListener mRecyclerListener;
        private ArrayList<View>[] mScrapViews;
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
                    TwoWayView.this.removeDetachedView(arrayList.remove(n3), false);
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
         */
        @TargetApi(value=14)
        void addScrapView(View view, int n2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams == null) return;
            layoutParams.scrappedFromPosition = n2;
            int n3 = layoutParams.viewType;
            boolean bl2 = ViewCompat.hasTransientState(view);
            if (!this.shouldRecycleViewType(n3) || bl2) {
                if (!bl2) return;
                {
                    if (this.mTransientStateViews == null) {
                        this.mTransientStateViews = new SparseArrayCompat();
                    }
                    this.mTransientStateViews.put(n2, view);
                    return;
                }
            }
            if (this.mViewTypeCount == 1) {
                this.mCurrentScrap.add(view);
            } else {
                this.mScrapViews[n3].add(view);
            }
            if (Build.VERSION.SDK_INT >= 14) {
                view.setAccessibilityDelegate(null);
            }
            if (this.mRecyclerListener == null) {
                return;
            }
            this.mRecyclerListener.onMovedToScrapHeap(view);
        }

        void clear() {
            if (this.mViewTypeCount == 1) {
                ArrayList<View> arrayList = this.mCurrentScrap;
                int n2 = arrayList.size();
                for (int i2 = 0; i2 < n2; ++i2) {
                    TwoWayView.this.removeDetachedView(arrayList.remove(n2 - 1 - i2), false);
                }
            } else {
                int n3 = this.mViewTypeCount;
                for (int i3 = 0; i3 < n3; ++i3) {
                    ArrayList<View> arrayList = this.mScrapViews[i3];
                    int n4 = arrayList.size();
                    for (int i4 = 0; i4 < n4; ++i4) {
                        TwoWayView.this.removeDetachedView(arrayList.remove(n4 - 1 - i4), false);
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
                arrview[n3] = TwoWayView.this.getChildAt(n3);
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
                return this.retrieveFromScrap(this.mCurrentScrap, n2);
            }
            int n3 = TwoWayView.this.mAdapter.getItemViewType(n2);
            if (n3 >= 0 && n3 < this.mScrapViews.length) {
                return this.retrieveFromScrap(this.mScrapViews[n3], n2);
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
            Object object;
            int n2;
            int n3;
            if (this.mViewTypeCount == 1) {
                object = this.mCurrentScrap;
                n3 = object.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    object.get(n2).forceLayout();
                }
            } else {
                n3 = this.mViewTypeCount;
                for (n2 = 0; n2 < n3; ++n2) {
                    object = this.mScrapViews[n2].iterator();
                    while (object.hasNext()) {
                        ((View)object.next()).forceLayout();
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

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void reclaimScrapViews(List<View> list) {
            if (this.mViewTypeCount == 1) {
                list.addAll(this.mCurrentScrap);
                return;
            }
            int n2 = this.mViewTypeCount;
            ArrayList<View>[] arrarrayList = this.mScrapViews;
            int n3 = 0;
            while (n3 < n2) {
                list.addAll(arrarrayList[n3]);
                ++n3;
            }
        }

        View retrieveFromScrap(ArrayList<View> arrayList, int n2) {
            int n3 = arrayList.size();
            if (n3 <= 0) {
                return null;
            }
            for (int i2 = 0; i2 < n3; ++i2) {
                View view = arrayList.get(i2);
                if (((LayoutParams)view.getLayoutParams()).scrappedFromPosition != n2) continue;
                arrayList.remove(i2);
                return view;
            }
            return arrayList.remove(n3 - 1);
        }

        /*
         * Enabled aggressive block sorting
         */
        @TargetApi(value=14)
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
                    int n3 = arrayList2.viewType;
                    arrview[n2] = null;
                    boolean bl3 = ViewCompat.hasTransientState(view);
                    if (!this.shouldRecycleViewType(n3) || bl3) {
                        arrayList2 = arrayList;
                        if (bl3) {
                            TwoWayView.this.removeDetachedView(view, false);
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
                        arrayList2.scrappedFromPosition = this.mFirstActivePosition + n2;
                        arrayList.add(view);
                        if (Build.VERSION.SDK_INT >= 14) {
                            view.setAccessibilityDelegate(null);
                        }
                        arrayList2 = arrayList;
                        if (this.mRecyclerListener != null) {
                            this.mRecyclerListener.onMovedToScrapHeap(view);
                            arrayList2 = arrayList;
                        }
                    }
                }
                --n2;
                arrayList = arrayList2;
            } while (true);
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

    public static interface RecyclerListener {
        public void onMovedToScrapHeap(View var1);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        LongSparseArray<Integer> checkIdState;
        SparseBooleanArray checkState;
        int checkedItemCount;
        long firstId;
        int height;
        int position;
        long selectedId;
        int viewStart;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.firstId = parcel.readLong();
            this.viewStart = parcel.readInt();
            this.position = parcel.readInt();
            this.height = parcel.readInt();
            this.checkedItemCount = parcel.readInt();
            this.checkState = parcel.readSparseBooleanArray();
            int n2 = parcel.readInt();
            if (n2 > 0) {
                this.checkIdState = new LongSparseArray();
                for (int i2 = 0; i2 < n2; ++i2) {
                    long l2 = parcel.readLong();
                    int n3 = parcel.readInt();
                    this.checkIdState.put(l2, n3);
                }
            }
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "TwoWayView.SavedState{" + Integer.toHexString(System.identityHashCode((Object)this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewStart=" + this.viewStart + " height=" + this.height + " position=" + this.position + " checkState=" + (Object)this.checkState + "}";
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeLong(this.selectedId);
            parcel.writeLong(this.firstId);
            parcel.writeInt(this.viewStart);
            parcel.writeInt(this.position);
            parcel.writeInt(this.height);
            parcel.writeInt(this.checkedItemCount);
            parcel.writeSparseBooleanArray(this.checkState);
            n2 = this.checkIdState != null ? this.checkIdState.size() : 0;
            parcel.writeInt(n2);
            int n3 = 0;
            while (n3 < n2) {
                parcel.writeLong(this.checkIdState.keyAt(n3));
                parcel.writeInt(this.checkIdState.valueAt(n3).intValue());
                ++n3;
            }
        }

    }

    private class SelectionNotifier
    implements Runnable {
        private SelectionNotifier() {
        }

        @Override
        public void run() {
            if (TwoWayView.this.mDataChanged) {
                if (TwoWayView.this.mAdapter != null) {
                    TwoWayView.this.post((Runnable)this);
                }
                return;
            }
            TwoWayView.this.fireOnSelected();
            TwoWayView.this.performAccessibilityActionsOnSelected();
        }
    }

    private class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {
        }

        public void rememberWindowAttachCount() {
            this.mOriginalAttachCount = TwoWayView.this.getWindowAttachCount();
        }

        public boolean sameWindow() {
            if (TwoWayView.this.hasWindowFocus() && TwoWayView.this.getWindowAttachCount() == this.mOriginalAttachCount) {
                return true;
            }
            return false;
        }
    }

}

