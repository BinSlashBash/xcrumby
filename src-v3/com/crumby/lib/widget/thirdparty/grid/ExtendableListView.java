package com.crumby.lib.widget.thirdparty.grid;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.Scroller;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ExtendableListView extends AbsListView {
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
    private int mActivePointerId;
    ListAdapter mAdapter;
    private boolean mBlockLayoutRequests;
    protected boolean mClipToPadding;
    private boolean mDataChanged;
    protected int mFirstPosition;
    private FlingRunnable mFlingRunnable;
    private int mFlingVelocity;
    private ArrayList<FixedViewInfo> mFooterViewInfos;
    private ArrayList<FixedViewInfo> mHeaderViewInfos;
    private boolean mInLayout;
    private boolean mIsAttached;
    final boolean[] mIsScrap;
    private int mItemCount;
    private int mLastY;
    private int mLayoutMode;
    private int mMaximumVelocity;
    private int mMotionCorrection;
    private int mMotionPosition;
    private int mMotionX;
    private int mMotionY;
    boolean mNeedSync;
    private AdapterDataSetObserver mObserver;
    private int mOldItemCount;
    private OnScrollListener mOnScrollListener;
    private PerformClick mPerformClick;
    private RecycleBin mRecycleBin;
    protected int mSpecificTop;
    long mSyncHeight;
    protected int mSyncPosition;
    long mSyncRowId;
    private ListSavedState mSyncState;
    private int mTouchMode;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private int mWidthMeasureSpec;

    class AdapterDataSetObserver extends DataSetObserver {
        private Parcelable mInstanceState;

        AdapterDataSetObserver() {
            this.mInstanceState = null;
        }

        public void onChanged() {
            ExtendableListView.this.mDataChanged = true;
            ExtendableListView.this.mOldItemCount = ExtendableListView.this.mItemCount;
            ExtendableListView.this.mItemCount = ExtendableListView.this.getAdapter().getCount();
            ExtendableListView.this.mRecycleBin.clearTransientStateViews();
            if (!ExtendableListView.this.getAdapter().hasStableIds() || this.mInstanceState == null || ExtendableListView.this.mOldItemCount != 0 || ExtendableListView.this.mItemCount <= 0) {
                ExtendableListView.this.rememberSyncState();
            } else {
                ExtendableListView.this.onRestoreInstanceState(this.mInstanceState);
                this.mInstanceState = null;
            }
            ExtendableListView.this.requestLayout();
        }

        public void onInvalidated() {
            ExtendableListView.this.mDataChanged = true;
            if (ExtendableListView.this.getAdapter().hasStableIds()) {
                this.mInstanceState = ExtendableListView.this.onSaveInstanceState();
            }
            ExtendableListView.this.mOldItemCount = ExtendableListView.this.mItemCount;
            ExtendableListView.this.mItemCount = ExtendableListView.TOUCH_MODE_IDLE;
            ExtendableListView.this.mNeedSync = ExtendableListView.DBG;
            ExtendableListView.this.requestLayout();
        }

        public void clearSavedState() {
            this.mInstanceState = null;
        }
    }

    public class FixedViewInfo {
        public Object data;
        public boolean isSelectable;
        public View view;
    }

    private class FlingRunnable implements Runnable {
        private int mLastFlingY;
        private final Scroller mScroller;

        FlingRunnable() {
            this.mScroller = new Scroller(ExtendableListView.this.getContext());
        }

        void start(int initialVelocity) {
            int initialY;
            if (initialVelocity < 0) {
                initialY = Integer.MAX_VALUE;
            } else {
                initialY = ExtendableListView.TOUCH_MODE_IDLE;
            }
            this.mLastFlingY = initialY;
            this.mScroller.forceFinished(true);
            this.mScroller.fling(ExtendableListView.TOUCH_MODE_IDLE, initialY, ExtendableListView.TOUCH_MODE_IDLE, initialVelocity, ExtendableListView.TOUCH_MODE_IDLE, Integer.MAX_VALUE, ExtendableListView.TOUCH_MODE_IDLE, Integer.MAX_VALUE);
            ExtendableListView.this.mTouchMode = ExtendableListView.TOUCH_MODE_FLINGING;
            ExtendableListView.this.postOnAnimate(this);
        }

        void startScroll(int distance, int duration) {
            int initialY;
            if (distance < 0) {
                initialY = Integer.MAX_VALUE;
            } else {
                initialY = ExtendableListView.TOUCH_MODE_IDLE;
            }
            this.mLastFlingY = initialY;
            this.mScroller.startScroll(ExtendableListView.TOUCH_MODE_IDLE, initialY, ExtendableListView.TOUCH_MODE_IDLE, distance, duration);
            ExtendableListView.this.mTouchMode = ExtendableListView.TOUCH_MODE_FLINGING;
            ExtendableListView.this.postOnAnimate(this);
        }

        private void endFling() {
            this.mLastFlingY = ExtendableListView.TOUCH_MODE_IDLE;
            ExtendableListView.this.mTouchMode = ExtendableListView.TOUCH_MODE_IDLE;
            ExtendableListView.this.reportScrollStateChange(ExtendableListView.TOUCH_MODE_IDLE);
            ExtendableListView.this.removeCallbacks(this);
            this.mScroller.forceFinished(true);
        }

        public void run() {
            switch (ExtendableListView.this.mTouchMode) {
                case ExtendableListView.TOUCH_MODE_FLINGING /*2*/:
                    if (ExtendableListView.this.mItemCount == 0 || ExtendableListView.this.getChildCount() == 0) {
                        endFling();
                        return;
                    }
                    Scroller scroller = this.mScroller;
                    boolean more = scroller.computeScrollOffset();
                    int y = scroller.getCurrY();
                    int delta = this.mLastFlingY - y;
                    if (delta > 0) {
                        ExtendableListView.this.mMotionPosition = ExtendableListView.this.mFirstPosition;
                        delta = Math.min(((ExtendableListView.this.getHeight() - ExtendableListView.this.getPaddingBottom()) - ExtendableListView.this.getPaddingTop()) + ExtendableListView.INVALID_POINTER, delta);
                    } else {
                        ExtendableListView.this.mMotionPosition = ExtendableListView.this.mFirstPosition + (ExtendableListView.this.getChildCount() + ExtendableListView.INVALID_POINTER);
                        delta = Math.max(-(((ExtendableListView.this.getHeight() - ExtendableListView.this.getPaddingBottom()) - ExtendableListView.this.getPaddingTop()) + ExtendableListView.INVALID_POINTER), delta);
                    }
                    boolean atEnd = ExtendableListView.this.moveTheChildren(delta, delta);
                    if (!more || atEnd) {
                        endFling();
                        return;
                    }
                    ExtendableListView.this.invalidate();
                    this.mLastFlingY = y;
                    ExtendableListView.this.postOnAnimate(this);
                default:
            }
        }
    }

    public static class LayoutParams extends android.widget.AbsListView.LayoutParams {
        long itemId;
        int position;
        boolean recycledHeaderFooter;
        int viewType;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.itemId = -1;
        }

        public LayoutParams(int w, int h) {
            super(w, h);
            this.itemId = -1;
        }

        public LayoutParams(int w, int h, int viewType) {
            super(w, h);
            this.itemId = -1;
            this.viewType = viewType;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
            this.itemId = -1;
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
            this.mActiveViews = new View[ExtendableListView.TOUCH_MODE_IDLE];
        }

        public void setViewTypeCount(int viewTypeCount) {
            if (viewTypeCount < ExtendableListView.TOUCH_MODE_SCROLLING) {
                throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
            }
            ArrayList<View>[] scrapViews = new ArrayList[viewTypeCount];
            for (int i = ExtendableListView.TOUCH_MODE_IDLE; i < viewTypeCount; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                scrapViews[i] = new ArrayList();
            }
            this.mViewTypeCount = viewTypeCount;
            this.mCurrentScrap = scrapViews[ExtendableListView.TOUCH_MODE_IDLE];
            this.mScrapViews = scrapViews;
        }

        public void markChildrenDirty() {
            int i;
            ArrayList<View> scrap;
            int scrapCount;
            if (this.mViewTypeCount == ExtendableListView.TOUCH_MODE_SCROLLING) {
                scrap = this.mCurrentScrap;
                scrapCount = scrap.size();
                for (i = ExtendableListView.TOUCH_MODE_IDLE; i < scrapCount; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                    ((View) scrap.get(i)).forceLayout();
                }
            } else {
                int typeCount = this.mViewTypeCount;
                for (i = ExtendableListView.TOUCH_MODE_IDLE; i < typeCount; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                    scrap = this.mScrapViews[i];
                    scrapCount = scrap.size();
                    for (int j = ExtendableListView.TOUCH_MODE_IDLE; j < scrapCount; j += ExtendableListView.TOUCH_MODE_SCROLLING) {
                        ((View) scrap.get(j)).forceLayout();
                    }
                }
            }
            if (this.mTransientStateViews != null) {
                int count = this.mTransientStateViews.size();
                for (i = ExtendableListView.TOUCH_MODE_IDLE; i < count; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                    ((View) this.mTransientStateViews.valueAt(i)).forceLayout();
                }
            }
        }

        public boolean shouldRecycleViewType(int viewType) {
            return viewType >= 0 ? true : ExtendableListView.DBG;
        }

        void clear() {
            ArrayList<View> scrap;
            int scrapCount;
            int i;
            if (this.mViewTypeCount == ExtendableListView.TOUCH_MODE_SCROLLING) {
                scrap = this.mCurrentScrap;
                scrapCount = scrap.size();
                for (i = ExtendableListView.TOUCH_MODE_IDLE; i < scrapCount; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                    ExtendableListView.this.removeDetachedView((View) scrap.remove((scrapCount + ExtendableListView.INVALID_POINTER) - i), ExtendableListView.DBG);
                }
            } else {
                int typeCount = this.mViewTypeCount;
                for (i = ExtendableListView.TOUCH_MODE_IDLE; i < typeCount; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                    scrap = this.mScrapViews[i];
                    scrapCount = scrap.size();
                    for (int j = ExtendableListView.TOUCH_MODE_IDLE; j < scrapCount; j += ExtendableListView.TOUCH_MODE_SCROLLING) {
                        ExtendableListView.this.removeDetachedView((View) scrap.remove((scrapCount + ExtendableListView.INVALID_POINTER) - j), ExtendableListView.DBG);
                    }
                }
            }
            if (this.mTransientStateViews != null) {
                this.mTransientStateViews.clear();
            }
        }

        void fillActiveViews(int childCount, int firstActivePosition) {
            if (this.mActiveViews.length < childCount) {
                this.mActiveViews = new View[childCount];
            }
            this.mFirstActivePosition = firstActivePosition;
            View[] activeViews = this.mActiveViews;
            for (int i = ExtendableListView.TOUCH_MODE_IDLE; i < childCount; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                View child = ExtendableListView.this.getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (!(lp == null || lp.viewType == -2)) {
                    activeViews[i] = child;
                }
            }
        }

        View getActiveView(int position) {
            int index = position - this.mFirstActivePosition;
            View[] activeViews = this.mActiveViews;
            if (index < 0 || index >= activeViews.length) {
                return null;
            }
            View match = activeViews[index];
            activeViews[index] = null;
            return match;
        }

        View getTransientStateView(int position) {
            if (this.mTransientStateViews == null) {
                return null;
            }
            int index = this.mTransientStateViews.indexOfKey(position);
            if (index < 0) {
                return null;
            }
            View result = (View) this.mTransientStateViews.valueAt(index);
            this.mTransientStateViews.removeAt(index);
            return result;
        }

        void clearTransientStateViews() {
            if (this.mTransientStateViews != null) {
                this.mTransientStateViews.clear();
            }
        }

        View getScrapView(int position) {
            if (this.mViewTypeCount == ExtendableListView.TOUCH_MODE_SCROLLING) {
                return ExtendableListView.retrieveFromScrap(this.mCurrentScrap, position);
            }
            int whichScrap = ExtendableListView.this.mAdapter.getItemViewType(position);
            if (whichScrap < 0 || whichScrap >= this.mScrapViews.length) {
                return null;
            }
            return ExtendableListView.retrieveFromScrap(this.mScrapViews[whichScrap], position);
        }

        void addScrapView(View scrap, int position) {
            LayoutParams lp = (LayoutParams) scrap.getLayoutParams();
            if (lp != null) {
                lp.position = position;
                int viewType = lp.viewType;
                boolean scrapHasTransientState = ViewCompat.hasTransientState(scrap);
                if (!shouldRecycleViewType(viewType) || scrapHasTransientState) {
                    if (viewType != -2 || scrapHasTransientState) {
                        if (this.mSkippedScrap == null) {
                            this.mSkippedScrap = new ArrayList();
                        }
                        this.mSkippedScrap.add(scrap);
                    }
                    if (scrapHasTransientState) {
                        if (this.mTransientStateViews == null) {
                            this.mTransientStateViews = new SparseArrayCompat();
                        }
                        this.mTransientStateViews.put(position, scrap);
                    }
                } else if (this.mViewTypeCount == ExtendableListView.TOUCH_MODE_SCROLLING) {
                    this.mCurrentScrap.add(scrap);
                } else {
                    this.mScrapViews[viewType].add(scrap);
                }
            }
        }

        void removeSkippedScrap() {
            if (this.mSkippedScrap != null) {
                int count = this.mSkippedScrap.size();
                for (int i = ExtendableListView.TOUCH_MODE_IDLE; i < count; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                    ExtendableListView.this.removeDetachedView((View) this.mSkippedScrap.get(i), ExtendableListView.DBG);
                }
                this.mSkippedScrap.clear();
            }
        }

        void scrapActiveViews() {
            boolean multipleScraps = true;
            View[] activeViews = this.mActiveViews;
            if (this.mViewTypeCount <= ExtendableListView.TOUCH_MODE_SCROLLING) {
                multipleScraps = ExtendableListView.DBG;
            }
            ArrayList<View> scrapViews = this.mCurrentScrap;
            for (int i = activeViews.length + ExtendableListView.INVALID_POINTER; i >= 0; i += ExtendableListView.INVALID_POINTER) {
                View victim = activeViews[i];
                if (victim != null) {
                    LayoutParams lp = (LayoutParams) victim.getLayoutParams();
                    activeViews[i] = null;
                    boolean scrapHasTransientState = ViewCompat.hasTransientState(victim);
                    int viewType = lp.viewType;
                    if (!shouldRecycleViewType(viewType) || scrapHasTransientState) {
                        if (viewType != -2 || scrapHasTransientState) {
                            ExtendableListView.this.removeDetachedView(victim, ExtendableListView.DBG);
                        }
                        if (scrapHasTransientState) {
                            if (this.mTransientStateViews == null) {
                                this.mTransientStateViews = new SparseArrayCompat();
                            }
                            this.mTransientStateViews.put(this.mFirstActivePosition + i, victim);
                        }
                    } else {
                        if (multipleScraps) {
                            scrapViews = this.mScrapViews[viewType];
                        }
                        lp.position = this.mFirstActivePosition + i;
                        scrapViews.add(victim);
                    }
                }
            }
            pruneScrapViews();
        }

        private void pruneScrapViews() {
            int i;
            int maxViews = this.mActiveViews.length;
            int viewTypeCount = this.mViewTypeCount;
            ArrayList<View>[] scrapViews = this.mScrapViews;
            for (i = ExtendableListView.TOUCH_MODE_IDLE; i < viewTypeCount; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                ArrayList<View> scrapPile = scrapViews[i];
                int size = scrapPile.size();
                int extras = size - maxViews;
                size += ExtendableListView.INVALID_POINTER;
                int j = ExtendableListView.TOUCH_MODE_IDLE;
                int size2 = size;
                while (j < extras) {
                    size = size2 + ExtendableListView.INVALID_POINTER;
                    ExtendableListView.this.removeDetachedView((View) scrapPile.remove(size2), ExtendableListView.DBG);
                    j += ExtendableListView.TOUCH_MODE_SCROLLING;
                    size2 = size;
                }
            }
            if (this.mTransientStateViews != null) {
                i = ExtendableListView.TOUCH_MODE_IDLE;
                while (i < this.mTransientStateViews.size()) {
                    if (!ViewCompat.hasTransientState((View) this.mTransientStateViews.valueAt(i))) {
                        this.mTransientStateViews.removeAt(i);
                        i += ExtendableListView.INVALID_POINTER;
                    }
                    i += ExtendableListView.TOUCH_MODE_SCROLLING;
                }
            }
        }

        void setCacheColorHint(int color) {
            int i;
            ArrayList<View> scrap;
            int scrapCount;
            if (this.mViewTypeCount == ExtendableListView.TOUCH_MODE_SCROLLING) {
                scrap = this.mCurrentScrap;
                scrapCount = scrap.size();
                for (i = ExtendableListView.TOUCH_MODE_IDLE; i < scrapCount; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                    ((View) scrap.get(i)).setDrawingCacheBackgroundColor(color);
                }
            } else {
                int typeCount = this.mViewTypeCount;
                for (i = ExtendableListView.TOUCH_MODE_IDLE; i < typeCount; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                    scrap = this.mScrapViews[i];
                    scrapCount = scrap.size();
                    for (int j = ExtendableListView.TOUCH_MODE_IDLE; j < scrapCount; j += ExtendableListView.TOUCH_MODE_SCROLLING) {
                        ((View) scrap.get(j)).setDrawingCacheBackgroundColor(color);
                    }
                }
            }
            View[] activeViews = this.mActiveViews;
            int count = activeViews.length;
            for (i = ExtendableListView.TOUCH_MODE_IDLE; i < count; i += ExtendableListView.TOUCH_MODE_SCROLLING) {
                View victim = activeViews[i];
                if (victim != null) {
                    victim.setDrawingCacheBackgroundColor(color);
                }
            }
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
            return (ExtendableListView.this.hasWindowFocus() && ExtendableListView.this.getWindowAttachCount() == this.mOriginalAttachCount) ? true : ExtendableListView.DBG;
        }
    }

    public static class ListSavedState extends ClassLoaderSavedState {
        public static final Creator<ListSavedState> CREATOR;
        protected long firstId;
        protected int height;
        protected int position;
        protected long selectedId;
        protected int viewTop;

        /* renamed from: com.crumby.lib.widget.thirdparty.grid.ExtendableListView.ListSavedState.1 */
        static class C01671 implements Creator<ListSavedState> {
            C01671() {
            }

            public ListSavedState createFromParcel(Parcel in) {
                return new ListSavedState(in);
            }

            public ListSavedState[] newArray(int size) {
                return new ListSavedState[size];
            }
        }

        public ListSavedState(Parcelable superState) {
            super(superState, AbsListView.class.getClassLoader());
        }

        public ListSavedState(Parcel in) {
            super(in);
            this.selectedId = in.readLong();
            this.firstId = in.readLong();
            this.viewTop = in.readInt();
            this.position = in.readInt();
            this.height = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(this.selectedId);
            out.writeLong(this.firstId);
            out.writeInt(this.viewTop);
            out.writeInt(this.position);
            out.writeInt(this.height);
        }

        public String toString() {
            return "ExtendableListView.ListSavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewTop=" + this.viewTop + " position=" + this.position + " height=" + this.height + "}";
        }

        static {
            CREATOR = new C01671();
        }
    }

    private class PerformClick extends WindowRunnnable implements Runnable {
        int mClickMotionPosition;

        private PerformClick() {
            super(null);
        }

        public void run() {
            if (!ExtendableListView.this.mDataChanged) {
                ListAdapter adapter = ExtendableListView.this.mAdapter;
                int motionPosition = this.mClickMotionPosition;
                if (adapter != null && ExtendableListView.this.mItemCount > 0 && motionPosition != ExtendableListView.INVALID_POINTER && motionPosition < adapter.getCount() && sameWindow()) {
                    View view = ExtendableListView.this.getChildAt(motionPosition);
                    if (view != null) {
                        ExtendableListView.this.performItemClick(view, ExtendableListView.this.mFirstPosition + motionPosition, adapter.getItemId(motionPosition));
                    }
                }
            }
        }
    }

    public ExtendableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mVelocityTracker = null;
        this.mActivePointerId = INVALID_POINTER;
        this.mBlockLayoutRequests = DBG;
        this.mIsScrap = new boolean[TOUCH_MODE_SCROLLING];
        this.mSyncRowId = Long.MIN_VALUE;
        this.mNeedSync = DBG;
        setWillNotDraw(DBG);
        setClipToPadding(DBG);
        setFocusableInTouchMode(DBG);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mRecycleBin = new RecycleBin();
        this.mObserver = new AdapterDataSetObserver();
        this.mHeaderViewInfos = new ArrayList();
        this.mFooterViewInfos = new ArrayList();
        this.mLayoutMode = TOUCH_MODE_IDLE;
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

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRecycleBin.clear();
        if (this.mFlingRunnable != null) {
            removeCallbacks(this.mFlingRunnable);
        }
        this.mIsAttached = DBG;
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (getChildCount() > 0) {
            this.mDataChanged = true;
            rememberSyncState();
        }
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            this.mAdapter = new HeaderViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, adapter);
        } else {
            this.mAdapter = adapter;
        }
        this.mDataChanged = true;
        this.mItemCount = adapter != null ? adapter.getCount() : TOUCH_MODE_IDLE;
        if (adapter != null) {
            adapter.registerDataSetObserver(this.mObserver);
            this.mRecycleBin.setViewTypeCount(adapter.getViewTypeCount());
        }
        requestLayout();
    }

    public int getCount() {
        return this.mItemCount;
    }

    public View getSelectedView() {
        return null;
    }

    public void setSelection(int position) {
        if (position >= 0) {
            this.mLayoutMode = TOUCH_MODE_FLINGING;
            this.mSpecificTop = getListPaddingTop();
            this.mFirstPosition = TOUCH_MODE_IDLE;
            if (this.mNeedSync) {
                this.mSyncPosition = position;
                this.mSyncRowId = this.mAdapter.getItemId(position);
            }
            requestLayout();
        }
    }

    public void addHeaderView(View v, Object data, boolean isSelectable) {
        if (this.mAdapter == null || (this.mAdapter instanceof HeaderViewListAdapter)) {
            FixedViewInfo info = new FixedViewInfo();
            info.view = v;
            info.data = data;
            info.isSelectable = isSelectable;
            this.mHeaderViewInfos.add(info);
            if (this.mAdapter != null && this.mObserver != null) {
                this.mObserver.onChanged();
                return;
            }
            return;
        }
        throw new IllegalStateException("Cannot add header view to list -- setAdapter has already been called.");
    }

    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    public boolean removeHeaderView(View v) {
        if (this.mHeaderViewInfos.size() <= 0) {
            return DBG;
        }
        boolean result = DBG;
        if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeHeader(v)) {
            if (this.mObserver != null) {
                this.mObserver.onChanged();
            }
            result = true;
        }
        removeFixedViewInfo(v, this.mHeaderViewInfos);
        return result;
    }

    private void removeFixedViewInfo(View v, ArrayList<FixedViewInfo> where) {
        int len = where.size();
        for (int i = TOUCH_MODE_IDLE; i < len; i += TOUCH_MODE_SCROLLING) {
            if (((FixedViewInfo) where.get(i)).view == v) {
                where.remove(i);
                return;
            }
        }
    }

    public void addFooterView(View v, Object data, boolean isSelectable) {
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        this.mFooterViewInfos.add(info);
        if (this.mAdapter != null && this.mObserver != null) {
            this.mObserver.onChanged();
        }
    }

    public void addFooterView(View v) {
        addFooterView(v, null, true);
    }

    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    public boolean removeFooterView(View v) {
        if (this.mFooterViewInfos.size() <= 0) {
            return DBG;
        }
        boolean result = DBG;
        if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeFooter(v)) {
            if (this.mObserver != null) {
                this.mObserver.onChanged();
            }
            result = true;
        }
        removeFixedViewInfo(v, this.mFooterViewInfos);
        return result;
    }

    public void setClipToPadding(boolean clipToPadding) {
        super.setClipToPadding(clipToPadding);
        this.mClipToPadding = clipToPadding;
    }

    public void requestLayout() {
        if (!this.mBlockLayoutRequests && !this.mInLayout) {
            super.requestLayout();
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mAdapter != null) {
            if (changed) {
                int childCount = getChildCount();
                for (int i = TOUCH_MODE_IDLE; i < childCount; i += TOUCH_MODE_SCROLLING) {
                    getChildAt(i).forceLayout();
                }
                this.mRecycleBin.markChildrenDirty();
            }
            this.mInLayout = true;
            layoutChildren();
            this.mInLayout = DBG;
        }
    }

    protected void layoutChildren() {
        if (!this.mBlockLayoutRequests) {
            this.mBlockLayoutRequests = true;
            try {
                super.layoutChildren();
                invalidate();
                if (this.mAdapter == null) {
                    clearState();
                    return;
                }
                int childrenTop = getListPaddingTop();
                int childCount = getChildCount();
                View oldFirst = null;
                if (this.mLayoutMode == 0) {
                    oldFirst = getChildAt(TOUCH_MODE_IDLE);
                }
                boolean dataChanged = this.mDataChanged;
                if (dataChanged) {
                    handleDataChanged();
                }
                if (this.mItemCount == 0) {
                    clearState();
                    this.mBlockLayoutRequests = DBG;
                } else if (this.mItemCount != this.mAdapter.getCount()) {
                    throw new IllegalStateException("The content of the adapter has changed but ExtendableListView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. [in ExtendableListView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
                } else {
                    int firstPosition = this.mFirstPosition;
                    RecycleBin recycleBin = this.mRecycleBin;
                    if (dataChanged) {
                        for (int i = TOUCH_MODE_IDLE; i < childCount; i += TOUCH_MODE_SCROLLING) {
                            recycleBin.addScrapView(getChildAt(i), firstPosition + i);
                        }
                    } else {
                        recycleBin.fillActiveViews(childCount, firstPosition);
                    }
                    detachAllViewsFromParent();
                    recycleBin.removeSkippedScrap();
                    switch (this.mLayoutMode) {
                        case TOUCH_MODE_SCROLLING /*1*/:
                            this.mFirstPosition = TOUCH_MODE_IDLE;
                            resetToTop();
                            adjustViewsUpOrDown();
                            fillFromTop(childrenTop);
                            adjustViewsUpOrDown();
                            break;
                        case TOUCH_MODE_FLINGING /*2*/:
                            fillSpecific(this.mSyncPosition, this.mSpecificTop);
                            break;
                        default:
                            if (childCount != 0) {
                                if (this.mFirstPosition >= this.mItemCount) {
                                    fillSpecific(TOUCH_MODE_IDLE, childrenTop);
                                    break;
                                }
                                int i2 = this.mFirstPosition;
                                if (oldFirst != null) {
                                    childrenTop = oldFirst.getTop();
                                }
                                fillSpecific(i2, childrenTop);
                                break;
                            }
                            fillFromTop(childrenTop);
                            break;
                    }
                    recycleBin.scrapActiveViews();
                    this.mDataChanged = DBG;
                    this.mNeedSync = DBG;
                    this.mLayoutMode = TOUCH_MODE_IDLE;
                    this.mBlockLayoutRequests = DBG;
                }
            } finally {
                this.mBlockLayoutRequests = DBG;
            }
        }
    }

    protected void handleDataChanged() {
        super.handleDataChanged();
        int count = this.mItemCount;
        if (count <= 0 || !this.mNeedSync) {
            this.mLayoutMode = TOUCH_MODE_SCROLLING;
            this.mNeedSync = DBG;
            this.mSyncState = null;
            return;
        }
        this.mNeedSync = DBG;
        this.mSyncState = null;
        this.mLayoutMode = TOUCH_MODE_FLINGING;
        this.mSyncPosition = Math.min(Math.max(TOUCH_MODE_IDLE, this.mSyncPosition), count + INVALID_POINTER);
        if (this.mSyncPosition == 0) {
            this.mLayoutMode = TOUCH_MODE_SCROLLING;
        }
    }

    public void resetToTop() {
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        this.mWidthMeasureSpec = widthMeasureSpec;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            initVelocityTrackerIfNotExists();
            this.mVelocityTracker.addMovement(event);
            if (!hasChildren()) {
                return DBG;
            }
            boolean handled;
            switch (event.getAction() & MotionEventCompat.ACTION_MASK) {
                case TOUCH_MODE_IDLE /*0*/:
                    handled = onTouchDown(event);
                    break;
                case TOUCH_MODE_FLINGING /*2*/:
                    handled = onTouchMove(event);
                    break;
                case TOUCH_MODE_DOWN /*3*/:
                    handled = onTouchCancel(event);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    handled = onTouchPointerUp(event);
                    break;
                default:
                    handled = onTouchUp(event);
                    break;
            }
            notifyTouchMode();
            return handled;
        } else if (isClickable() || isLongClickable()) {
            return true;
        } else {
            return DBG;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (!this.mIsAttached) {
            return DBG;
        }
        int y;
        switch (action & MotionEventCompat.ACTION_MASK) {
            case TOUCH_MODE_IDLE /*0*/:
                int touchMode = this.mTouchMode;
                int x = (int) ev.getX();
                y = (int) ev.getY();
                this.mActivePointerId = ev.getPointerId(TOUCH_MODE_IDLE);
                int motionPosition = findMotionRow(y);
                if (touchMode != TOUCH_MODE_FLINGING && motionPosition >= 0) {
                    this.mMotionX = x;
                    this.mMotionY = y;
                    this.mMotionPosition = motionPosition;
                    this.mTouchMode = TOUCH_MODE_DOWN;
                }
                this.mLastY = ExploreByTouchHelper.INVALID_ID;
                initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(ev);
                if (touchMode == TOUCH_MODE_FLINGING) {
                    return true;
                }
                return DBG;
            case TOUCH_MODE_SCROLLING /*1*/:
            case TOUCH_MODE_DOWN /*3*/:
                this.mTouchMode = TOUCH_MODE_IDLE;
                this.mActivePointerId = INVALID_POINTER;
                recycleVelocityTracker();
                reportScrollStateChange(TOUCH_MODE_IDLE);
                return DBG;
            case TOUCH_MODE_FLINGING /*2*/:
                switch (this.mTouchMode) {
                    case TOUCH_MODE_DOWN /*3*/:
                        int pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                        if (pointerIndex == INVALID_POINTER) {
                            pointerIndex = TOUCH_MODE_IDLE;
                            this.mActivePointerId = ev.getPointerId(TOUCH_MODE_IDLE);
                        }
                        y = (int) ev.getY(pointerIndex);
                        initVelocityTrackerIfNotExists();
                        this.mVelocityTracker.addMovement(ev);
                        if (startScrollIfNeeded(y)) {
                            return true;
                        }
                        return DBG;
                    default:
                        return DBG;
                }
            case Std.STD_CURRENCY /*6*/:
                onSecondaryPointerUp(ev);
                return DBG;
            default:
                return DBG;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    private boolean onTouchDown(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int motionPosition = pointToPosition(x, y);
        this.mVelocityTracker.clear();
        this.mActivePointerId = MotionEventCompat.getPointerId(event, TOUCH_MODE_IDLE);
        if (this.mTouchMode != TOUCH_MODE_FLINGING && !this.mDataChanged && motionPosition >= 0 && getAdapter().isEnabled(motionPosition)) {
            this.mTouchMode = TOUCH_MODE_DOWN;
            if (event.getEdgeFlags() != 0 && motionPosition < 0) {
                return DBG;
            }
        } else if (this.mTouchMode == TOUCH_MODE_FLINGING) {
            this.mTouchMode = TOUCH_MODE_SCROLLING;
            this.mMotionCorrection = TOUCH_MODE_IDLE;
            motionPosition = findMotionRow(y);
        }
        this.mMotionX = x;
        this.mMotionY = y;
        this.mMotionPosition = motionPosition;
        this.mLastY = ExploreByTouchHelper.INVALID_ID;
        return true;
    }

    private boolean onTouchMove(MotionEvent event) {
        int index = MotionEventCompat.findPointerIndex(event, this.mActivePointerId);
        if (index < 0) {
            Log.e(TAG, "onTouchMove could not find pointer with id " + this.mActivePointerId + " - did ExtendableListView receive an inconsistent " + "event stream?");
            return DBG;
        }
        int y = (int) MotionEventCompat.getY(event, index);
        if (this.mDataChanged) {
            layoutChildren();
        }
        switch (this.mTouchMode) {
            case TOUCH_MODE_SCROLLING /*1*/:
                scrollIfNeeded(y);
                break;
            case TOUCH_MODE_DOWN /*3*/:
            case TOUCH_MODE_TAP /*4*/:
            case TOUCH_MODE_DONE_WAITING /*5*/:
                startScrollIfNeeded(y);
                break;
        }
        return true;
    }

    private boolean onTouchCancel(MotionEvent event) {
        this.mTouchMode = TOUCH_MODE_IDLE;
        setPressed(DBG);
        invalidate();
        recycleVelocityTracker();
        this.mActivePointerId = INVALID_POINTER;
        return true;
    }

    private boolean onTouchUp(MotionEvent event) {
        switch (this.mTouchMode) {
            case TOUCH_MODE_SCROLLING /*1*/:
                return onTouchUpScrolling(event);
            case TOUCH_MODE_DOWN /*3*/:
            case TOUCH_MODE_TAP /*4*/:
            case TOUCH_MODE_DONE_WAITING /*5*/:
                return onTouchUpTap(event);
            default:
                setPressed(DBG);
                invalidate();
                recycleVelocityTracker();
                this.mActivePointerId = INVALID_POINTER;
                return true;
        }
    }

    private boolean onTouchUpScrolling(MotionEvent event) {
        if (hasChildren()) {
            boolean atEdge;
            int top = getFirstChildTop();
            int bottom = getLastChildBottom();
            if (this.mFirstPosition != 0 || top < getListPaddingTop() || this.mFirstPosition + getChildCount() >= this.mItemCount || bottom > getHeight() - getListPaddingBottom()) {
                atEdge = DBG;
            } else {
                atEdge = true;
            }
            if (!atEdge) {
                this.mVelocityTracker.computeCurrentVelocity(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, (float) this.mMaximumVelocity);
                float velocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                if (Math.abs(velocity) > ((float) this.mFlingVelocity)) {
                    startFlingRunnable(velocity);
                    this.mTouchMode = TOUCH_MODE_FLINGING;
                    this.mMotionY = TOUCH_MODE_IDLE;
                    invalidate();
                    return true;
                }
            }
        }
        stopFlingRunnable();
        recycleVelocityTracker();
        this.mTouchMode = TOUCH_MODE_IDLE;
        return true;
    }

    private boolean onTouchUpTap(MotionEvent event) {
        if (this.mPerformClick == null) {
            invalidate();
            this.mPerformClick = new PerformClick();
        }
        int motionPosition = this.mMotionPosition;
        if (!this.mDataChanged && motionPosition >= 0 && this.mAdapter.isEnabled(motionPosition)) {
            PerformClick performClick = this.mPerformClick;
            performClick.mClickMotionPosition = motionPosition;
            performClick.rememberWindowAttachCount();
            performClick.run();
        }
        return true;
    }

    private boolean onTouchPointerUp(MotionEvent event) {
        onSecondaryPointerUp(event);
        int x = this.mMotionX;
        int y = this.mMotionY;
        int motionPosition = pointToPosition(x, y);
        if (motionPosition >= 0) {
            this.mMotionPosition = motionPosition;
        }
        this.mLastY = y;
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent event) {
        int pointerIndex = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (event.getPointerId(pointerIndex) == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? TOUCH_MODE_SCROLLING : TOUCH_MODE_IDLE;
            this.mMotionX = (int) event.getX(newPointerIndex);
            this.mMotionY = (int) event.getY(newPointerIndex);
            this.mActivePointerId = event.getPointerId(newPointerIndex);
            recycleVelocityTracker();
        }
    }

    private boolean startScrollIfNeeded(int y) {
        int deltaY = y - this.mMotionY;
        if (Math.abs(deltaY) <= this.mTouchSlop) {
            return DBG;
        }
        int i;
        this.mTouchMode = TOUCH_MODE_SCROLLING;
        if (deltaY > 0) {
            i = this.mTouchSlop;
        } else {
            i = -this.mTouchSlop;
        }
        this.mMotionCorrection = i;
        setPressed(DBG);
        View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (motionView != null) {
            motionView.setPressed(DBG);
        }
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        scrollIfNeeded(y);
        return true;
    }

    private void scrollIfNeeded(int y) {
        int incrementalDeltaY;
        int rawDeltaY = y - this.mMotionY;
        int deltaY = rawDeltaY - this.mMotionCorrection;
        if (this.mLastY != ExploreByTouchHelper.INVALID_ID) {
            incrementalDeltaY = y - this.mLastY;
        } else {
            incrementalDeltaY = deltaY;
        }
        if (this.mTouchMode == TOUCH_MODE_SCROLLING && y != this.mLastY) {
            int motionIndex;
            if (Math.abs(rawDeltaY) > this.mTouchSlop) {
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
            if (this.mMotionPosition >= 0) {
                motionIndex = this.mMotionPosition - this.mFirstPosition;
            } else {
                motionIndex = getChildCount() / TOUCH_MODE_FLINGING;
            }
            boolean atEdge = DBG;
            if (incrementalDeltaY != 0) {
                atEdge = moveTheChildren(deltaY, incrementalDeltaY);
            }
            if (getChildAt(motionIndex) != null) {
                if (atEdge) {
                    this.mTouchMode = TOUCH_MODE_DONE_WAITING;
                }
                this.mMotionY = y;
            }
            this.mLastY = y;
        }
    }

    private int findMotionRow(int y) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = TOUCH_MODE_IDLE; i < childCount; i += TOUCH_MODE_SCROLLING) {
                if (y <= getChildAt(i).getBottom()) {
                    return this.mFirstPosition + i;
                }
            }
        }
        return INVALID_POINTER;
    }

    private boolean moveTheChildren(int deltaY, int incrementalDeltaY) {
        if (!hasChildren()) {
            return true;
        }
        boolean cannotScrollUp;
        int firstTop = getHighestChildTop();
        int lastBottom = getLowestChildBottom();
        int effectivePaddingTop = TOUCH_MODE_IDLE;
        int effectivePaddingBottom = TOUCH_MODE_IDLE;
        if (this.mClipToPadding) {
            effectivePaddingTop = getListPaddingTop();
            effectivePaddingBottom = getListPaddingBottom();
        }
        int gridHeight = getHeight();
        int spaceAbove = effectivePaddingTop - getFirstChildTop();
        int spaceBelow = getLastChildBottom() - (gridHeight - effectivePaddingBottom);
        int height = (gridHeight - getListPaddingBottom()) - getListPaddingTop();
        if (incrementalDeltaY < 0) {
            incrementalDeltaY = Math.max(-(height + INVALID_POINTER), incrementalDeltaY);
        } else {
            incrementalDeltaY = Math.min(height + INVALID_POINTER, incrementalDeltaY);
        }
        int firstPosition = this.mFirstPosition;
        int maxTop = getListPaddingTop();
        int maxBottom = gridHeight - getListPaddingBottom();
        int childCount = getChildCount();
        boolean cannotScrollDown = (firstPosition != 0 || firstTop < maxTop || incrementalDeltaY < 0) ? DBG : true;
        if (firstPosition + childCount != this.mItemCount || lastBottom > maxBottom || incrementalDeltaY > 0) {
            cannotScrollUp = DBG;
        } else {
            cannotScrollUp = true;
        }
        if (cannotScrollDown) {
            if (incrementalDeltaY != 0) {
                return true;
            }
            return DBG;
        } else if (cannotScrollUp) {
            return incrementalDeltaY != 0 ? true : DBG;
        } else {
            boolean isDown = incrementalDeltaY < 0 ? true : DBG;
            int headerViewsCount = getHeaderViewsCount();
            int footerViewsStart = this.mItemCount - getFooterViewsCount();
            int start = TOUCH_MODE_IDLE;
            int count = TOUCH_MODE_IDLE;
            int i;
            View child;
            int position;
            if (!isDown) {
                int bottom = gridHeight - incrementalDeltaY;
                if (this.mClipToPadding) {
                    bottom -= getListPaddingBottom();
                }
                for (i = childCount + INVALID_POINTER; i >= 0; i += INVALID_POINTER) {
                    child = getChildAt(i);
                    if (child.getTop() <= bottom) {
                        break;
                    }
                    start = i;
                    count += TOUCH_MODE_SCROLLING;
                    position = firstPosition + i;
                    if (position >= headerViewsCount && position < footerViewsStart) {
                        this.mRecycleBin.addScrapView(child, position);
                    }
                }
            } else {
                int top = -incrementalDeltaY;
                if (this.mClipToPadding) {
                    top += getListPaddingTop();
                }
                for (i = TOUCH_MODE_IDLE; i < childCount; i += TOUCH_MODE_SCROLLING) {
                    child = getChildAt(i);
                    if (child.getBottom() >= top) {
                        break;
                    }
                    count += TOUCH_MODE_SCROLLING;
                    position = firstPosition + i;
                    if (position >= headerViewsCount && position < footerViewsStart) {
                        this.mRecycleBin.addScrapView(child, position);
                    }
                }
            }
            this.mBlockLayoutRequests = true;
            if (count > 0) {
                detachViewsFromParent(start, count);
                this.mRecycleBin.removeSkippedScrap();
                onChildrenDetached(start, count);
            }
            if (!awakenScrollBars()) {
                invalidate();
            }
            offsetChildrenTopAndBottom(incrementalDeltaY);
            if (isDown) {
                this.mFirstPosition += count;
            }
            int absIncrementalDeltaY = Math.abs(incrementalDeltaY);
            if (spaceAbove < absIncrementalDeltaY || spaceBelow < absIncrementalDeltaY) {
                fillGap(isDown);
            }
            this.mBlockLayoutRequests = DBG;
            invokeOnItemScrollListener();
            return DBG;
        }
    }

    protected void onChildrenDetached(int start, int count) {
    }

    protected void fillGap(boolean down) {
        int count = getChildCount();
        int position;
        if (down) {
            position = this.mFirstPosition + count;
            fillDown(position, getChildTop(position));
        } else {
            position = this.mFirstPosition + INVALID_POINTER;
            fillUp(position, getChildBottom(position));
        }
        adjustViewsAfterFillGap(down);
    }

    protected void adjustViewsAfterFillGap(boolean down) {
        if (down) {
            correctTooHigh(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.View fillDown(int r5, int r6) {
        /*
        r4 = this;
        r1 = 0;
        r0 = r4.getHeight();
        r2 = r4.mClipToPadding;
        if (r2 == 0) goto L_0x000e;
    L_0x0009:
        r2 = r4.getListPaddingBottom();
        r0 = r0 - r2;
    L_0x000e:
        if (r6 < r0) goto L_0x0016;
    L_0x0010:
        r2 = r4.hasSpaceDown();
        if (r2 == 0) goto L_0x0026;
    L_0x0016:
        r2 = r4.mItemCount;
        if (r5 >= r2) goto L_0x0026;
    L_0x001a:
        r2 = 1;
        r3 = 0;
        r4.makeAndAddView(r5, r6, r2, r3);
        r5 = r5 + 1;
        r6 = r4.getNextChildDownsTop(r5);
        goto L_0x000e;
    L_0x0026:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crumby.lib.widget.thirdparty.grid.ExtendableListView.fillDown(int, int):android.view.View");
    }

    protected boolean hasSpaceDown() {
        return DBG;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.View fillUp(int r5, int r6) {
        /*
        r4 = this;
        r2 = 0;
        r1 = 0;
        r3 = r4.mClipToPadding;
        if (r3 == 0) goto L_0x001e;
    L_0x0006:
        r0 = r4.getListPaddingTop();
    L_0x000a:
        if (r6 > r0) goto L_0x0012;
    L_0x000c:
        r3 = r4.hasSpaceUp();
        if (r3 == 0) goto L_0x0020;
    L_0x0012:
        if (r5 < 0) goto L_0x0020;
    L_0x0014:
        r4.makeAndAddView(r5, r6, r2, r2);
        r5 = r5 + -1;
        r6 = r4.getNextChildUpsBottom(r5);
        goto L_0x000a;
    L_0x001e:
        r0 = r2;
        goto L_0x000a;
    L_0x0020:
        r2 = r5 + 1;
        r4.mFirstPosition = r2;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crumby.lib.widget.thirdparty.grid.ExtendableListView.fillUp(int, int):android.view.View");
    }

    protected boolean hasSpaceUp() {
        return DBG;
    }

    private View fillFromTop(int nextTop) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount + INVALID_POINTER);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = TOUCH_MODE_IDLE;
        }
        return fillDown(this.mFirstPosition, nextTop);
    }

    private View fillSpecific(int position, int top) {
        View temp = makeAndAddView(position, top, true, DBG);
        this.mFirstPosition = position;
        int nextBottom = getNextChildUpsBottom(position + INVALID_POINTER);
        int nextTop = getNextChildDownsTop(position + TOUCH_MODE_SCROLLING);
        View above = fillUp(position + INVALID_POINTER, nextBottom);
        adjustViewsUpOrDown();
        View below = fillDown(position + TOUCH_MODE_SCROLLING, nextTop);
        int childCount = getChildCount();
        if (childCount > 0) {
            correctTooHigh(childCount);
        }
        if (TOUCH_MODE_IDLE != null) {
            return temp;
        }
        if (above != null) {
            return above;
        }
        return below;
    }

    private View makeAndAddView(int position, int y, boolean flowDown, boolean selected) {
        View child;
        onChildCreated(position, flowDown);
        if (!this.mDataChanged) {
            child = this.mRecycleBin.getActiveView(position);
            if (child != null) {
                setupChild(child, position, y, flowDown, selected, true);
                return child;
            }
        }
        child = obtainView(position, this.mIsScrap);
        setupChild(child, position, y, flowDown, selected, this.mIsScrap[TOUCH_MODE_IDLE]);
        return child;
    }

    private void setupChild(View child, int position, int y, boolean flowDown, boolean selected, boolean recycled) {
        LayoutParams layoutParams;
        boolean updateChildSelected = child.isSelected() ? true : DBG;
        int mode = this.mTouchMode;
        boolean isPressed = (mode <= TOUCH_MODE_DOWN || mode >= TOUCH_MODE_SCROLLING || this.mMotionPosition != position) ? DBG : true;
        boolean updateChildPressed = isPressed != child.isPressed() ? true : DBG;
        boolean needToMeasure = (!recycled || updateChildSelected || child.isLayoutRequested()) ? true : DBG;
        int itemViewType = this.mAdapter.getItemViewType(position);
        if (itemViewType == -2) {
            layoutParams = generateWrapperLayoutParams(child);
        } else {
            layoutParams = generateChildLayoutParams(child);
        }
        layoutParams.viewType = itemViewType;
        layoutParams.position = position;
        if (recycled || (layoutParams.recycledHeaderFooter && layoutParams.viewType == -2)) {
            attachViewToParent(child, flowDown ? INVALID_POINTER : TOUCH_MODE_IDLE, layoutParams);
        } else {
            if (layoutParams.viewType == -2) {
                layoutParams.recycledHeaderFooter = true;
            }
            addViewInLayout(child, flowDown ? INVALID_POINTER : TOUCH_MODE_IDLE, layoutParams, true);
        }
        if (updateChildSelected) {
            child.setSelected(DBG);
        }
        if (updateChildPressed) {
            child.setPressed(isPressed);
        }
        if (needToMeasure) {
            onMeasureChild(child, layoutParams);
        } else {
            cleanupLayoutState(child);
        }
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int childTop = flowDown ? y : y - h;
        int childrenLeft = getChildLeft(position);
        if (needToMeasure) {
            onLayoutChild(child, position, flowDown, childrenLeft, childTop, childrenLeft + w, childTop + h);
        } else {
            onOffsetChild(child, position, flowDown, childrenLeft, childTop);
        }
    }

    protected LayoutParams generateChildLayoutParams(View child) {
        return generateWrapperLayoutParams(child);
    }

    protected LayoutParams generateWrapperLayoutParams(View child) {
        LayoutParams layoutParams = null;
        android.view.ViewGroup.LayoutParams childParams = child.getLayoutParams();
        if (childParams != null) {
            if (childParams instanceof LayoutParams) {
                layoutParams = (LayoutParams) childParams;
            } else {
                layoutParams = new LayoutParams(childParams);
            }
        }
        if (layoutParams == null) {
            return generateDefaultLayoutParams();
        }
        return layoutParams;
    }

    protected void onMeasureChild(View child, LayoutParams layoutParams) {
        int childHeightSpec;
        int childWidthSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, getListPaddingLeft() + getListPaddingRight(), layoutParams.width);
        int lpHeight = layoutParams.height;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(TOUCH_MODE_IDLE, TOUCH_MODE_IDLE);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(INVALID_POINTER, -2, TOUCH_MODE_IDLE);
    }

    protected LayoutParams generateHeaderFooterLayoutParams(View child) {
        return new LayoutParams(INVALID_POINTER, -2, TOUCH_MODE_IDLE);
    }

    private View obtainView(int position, boolean[] isScrap) {
        isScrap[TOUCH_MODE_IDLE] = DBG;
        View scrapView = this.mRecycleBin.getScrapView(position);
        if (scrapView == null) {
            return this.mAdapter.getView(position, null, this);
        }
        View child = this.mAdapter.getView(position, scrapView, this);
        if (child != scrapView) {
            this.mRecycleBin.addScrapView(scrapView, position);
            return child;
        }
        isScrap[TOUCH_MODE_IDLE] = true;
        return child;
    }

    private void correctTooHigh(int childCount) {
        if ((this.mFirstPosition + childCount) + INVALID_POINTER == this.mItemCount + INVALID_POINTER && childCount > 0) {
            int bottomOffset = ((getBottom() - getTop()) - getListPaddingBottom()) - getLowestChildBottom();
            int firstTop = getHighestChildTop();
            if (bottomOffset <= 0) {
                return;
            }
            if (this.mFirstPosition > 0 || firstTop < getListPaddingTop()) {
                if (this.mFirstPosition == 0) {
                    bottomOffset = Math.min(bottomOffset, getListPaddingTop() - firstTop);
                }
                offsetChildrenTopAndBottom(bottomOffset);
                if (this.mFirstPosition > 0) {
                    int previousPosition = this.mFirstPosition + INVALID_POINTER;
                    fillUp(previousPosition, getNextChildUpsBottom(previousPosition));
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    private void correctTooLow(int childCount) {
        if (this.mFirstPosition == 0 && childCount > 0) {
            int end = (getTop() - getBottom()) - getListPaddingBottom();
            int topOffset = getHighestChildTop() - getListPaddingTop();
            int lastBottom = getLowestChildBottom();
            int lastPosition = (this.mFirstPosition + childCount) + INVALID_POINTER;
            if (topOffset <= 0) {
                return;
            }
            if (lastPosition < this.mItemCount + INVALID_POINTER || lastBottom > end) {
                if (lastPosition == this.mItemCount + INVALID_POINTER) {
                    topOffset = Math.min(topOffset, lastBottom - end);
                }
                offsetChildrenTopAndBottom(-topOffset);
                if (lastPosition < this.mItemCount + INVALID_POINTER) {
                    int nextPosition = lastPosition + TOUCH_MODE_SCROLLING;
                    fillDown(nextPosition, getNextChildDownsTop(nextPosition));
                    adjustViewsUpOrDown();
                }
            } else if (lastPosition == this.mItemCount + INVALID_POINTER) {
                adjustViewsUpOrDown();
            }
        }
    }

    private void adjustViewsUpOrDown() {
        if (getChildCount() > 0) {
            int delta = getHighestChildTop() - getListPaddingTop();
            if (delta < 0) {
                delta = TOUCH_MODE_IDLE;
            }
            if (delta != 0) {
                offsetChildrenTopAndBottom(-delta);
            }
        }
    }

    protected void onChildCreated(int position, boolean flowDown) {
    }

    protected void onLayoutChild(View child, int position, boolean flowDown, int childrenLeft, int childTop, int childRight, int childBottom) {
        child.layout(childrenLeft, childTop, childRight, childBottom);
    }

    protected void onOffsetChild(View child, int position, boolean flowDown, int childrenLeft, int childTop) {
        child.offsetLeftAndRight(childrenLeft - child.getLeft());
        child.offsetTopAndBottom(childTop - child.getTop());
    }

    protected int getChildLeft(int position) {
        return getListPaddingLeft();
    }

    protected int getChildTop(int position) {
        int count = getChildCount();
        int paddingTop = TOUCH_MODE_IDLE;
        if (this.mClipToPadding) {
            paddingTop = getListPaddingTop();
        }
        return count > 0 ? getChildAt(count + INVALID_POINTER).getBottom() : paddingTop;
    }

    protected int getChildBottom(int position) {
        int count = getChildCount();
        int paddingBottom = TOUCH_MODE_IDLE;
        if (this.mClipToPadding) {
            paddingBottom = getListPaddingBottom();
        }
        return count > 0 ? getChildAt(TOUCH_MODE_IDLE).getTop() : getHeight() - paddingBottom;
    }

    protected int getNextChildDownsTop(int position) {
        int count = getChildCount();
        return count > 0 ? getChildAt(count + INVALID_POINTER).getBottom() : TOUCH_MODE_IDLE;
    }

    protected int getNextChildUpsBottom(int position) {
        int count = getChildCount();
        if (count != 0 && count > 0) {
            return getChildAt(TOUCH_MODE_IDLE).getTop();
        }
        return TOUCH_MODE_IDLE;
    }

    protected int getFirstChildTop() {
        return hasChildren() ? getChildAt(TOUCH_MODE_IDLE).getTop() : TOUCH_MODE_IDLE;
    }

    protected int getHighestChildTop() {
        return hasChildren() ? getChildAt(TOUCH_MODE_IDLE).getTop() : TOUCH_MODE_IDLE;
    }

    protected int getLastChildBottom() {
        return hasChildren() ? getChildAt(getChildCount() + INVALID_POINTER).getBottom() : TOUCH_MODE_IDLE;
    }

    protected int getLowestChildBottom() {
        return hasChildren() ? getChildAt(getChildCount() + INVALID_POINTER).getBottom() : TOUCH_MODE_IDLE;
    }

    protected boolean hasChildren() {
        return getChildCount() > 0 ? true : DBG;
    }

    protected void offsetChildrenTopAndBottom(int offset) {
        int count = getChildCount();
        for (int i = TOUCH_MODE_IDLE; i < count; i += TOUCH_MODE_SCROLLING) {
            getChildAt(i).offsetTopAndBottom(offset);
        }
    }

    public int getFirstVisiblePosition() {
        return Math.max(TOUCH_MODE_IDLE, this.mFirstPosition - getHeaderViewsCount());
    }

    public int getLastVisiblePosition() {
        return Math.min((this.mFirstPosition + getChildCount()) + INVALID_POINTER, this.mAdapter.getCount() + INVALID_POINTER);
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void startFlingRunnable(float velocity) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.start((int) (-velocity));
    }

    private void stopFlingRunnable() {
        if (this.mFlingRunnable != null) {
            this.mFlingRunnable.endFling();
        }
    }

    private void postOnAnimate(Runnable runnable) {
        ViewCompat.postOnAnimation(this, runnable);
    }

    public void notifyTouchMode() {
        switch (this.mTouchMode) {
            case TOUCH_MODE_IDLE /*0*/:
                reportScrollStateChange(TOUCH_MODE_IDLE);
            case TOUCH_MODE_SCROLLING /*1*/:
                reportScrollStateChange(TOUCH_MODE_SCROLLING);
            case TOUCH_MODE_FLINGING /*2*/:
                reportScrollStateChange(TOUCH_MODE_FLINGING);
            default:
        }
    }

    public void setOnScrollListener(OnScrollListener scrollListener) {
        super.setOnScrollListener(scrollListener);
        this.mOnScrollListener = scrollListener;
    }

    void reportScrollStateChange(int newState) {
        if (newState != this.mTouchMode) {
            this.mTouchMode = newState;
            if (this.mOnScrollListener != null) {
                this.mOnScrollListener.onScrollStateChanged(this, newState);
            }
        }
    }

    void invokeOnItemScrollListener() {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
    }

    static View retrieveFromScrap(ArrayList<View> scrapViews, int position) {
        int size = scrapViews.size();
        if (size <= 0) {
            return null;
        }
        for (int i = TOUCH_MODE_IDLE; i < size; i += TOUCH_MODE_SCROLLING) {
            View view = (View) scrapViews.get(i);
            if (((LayoutParams) view.getLayoutParams()).position == position) {
                scrapViews.remove(i);
                return view;
            }
        }
        return (View) scrapViews.remove(size + INVALID_POINTER);
    }

    void rememberSyncState() {
        if (getChildCount() > 0) {
            this.mNeedSync = true;
            this.mSyncHeight = (long) getHeight();
            View v = getChildAt(TOUCH_MODE_IDLE);
            ListAdapter adapter = getAdapter();
            if (this.mFirstPosition < 0 || this.mFirstPosition >= adapter.getCount()) {
                this.mSyncRowId = -1;
            } else {
                this.mSyncRowId = adapter.getItemId(this.mFirstPosition);
            }
            if (v != null) {
                this.mSpecificTop = v.getTop();
            }
            this.mSyncPosition = this.mFirstPosition;
        }
    }

    private void clearState() {
        clearRecycledState(this.mHeaderViewInfos);
        clearRecycledState(this.mFooterViewInfos);
        removeAllViewsInLayout();
        this.mFirstPosition = TOUCH_MODE_IDLE;
        this.mDataChanged = DBG;
        this.mRecycleBin.clear();
        this.mNeedSync = DBG;
        this.mSyncState = null;
        this.mLayoutMode = TOUCH_MODE_IDLE;
        invalidate();
    }

    private void clearRecycledState(ArrayList<FixedViewInfo> infos) {
        if (infos != null) {
            Iterator i$ = infos.iterator();
            while (i$.hasNext()) {
                LayoutParams p = (LayoutParams) ((FixedViewInfo) i$.next()).view.getLayoutParams();
                if (p != null) {
                    p.recycledHeaderFooter = DBG;
                }
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        ListSavedState ss = new ListSavedState(super.onSaveInstanceState());
        if (this.mSyncState != null) {
            ss.selectedId = this.mSyncState.selectedId;
            ss.firstId = this.mSyncState.firstId;
            ss.viewTop = this.mSyncState.viewTop;
            ss.position = this.mSyncState.position;
            ss.height = this.mSyncState.height;
        } else {
            boolean haveChildren;
            if (getChildCount() <= 0 || this.mItemCount <= 0) {
                haveChildren = DBG;
            } else {
                haveChildren = true;
            }
            ss.selectedId = getSelectedItemId();
            ss.height = getHeight();
            if (!haveChildren || this.mFirstPosition <= 0) {
                ss.viewTop = TOUCH_MODE_IDLE;
                ss.firstId = -1;
                ss.position = TOUCH_MODE_IDLE;
            } else {
                ss.viewTop = getChildAt(TOUCH_MODE_IDLE).getTop();
                int firstPos = this.mFirstPosition;
                if (firstPos >= this.mItemCount) {
                    firstPos = this.mItemCount + INVALID_POINTER;
                }
                ss.position = firstPos;
                ss.firstId = this.mAdapter.getItemId(firstPos);
            }
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        ListSavedState ss = (ListSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = (long) ss.height;
        if (ss.firstId >= 0) {
            this.mNeedSync = true;
            this.mSyncState = ss;
            this.mSyncRowId = ss.firstId;
            this.mSyncPosition = ss.position;
            this.mSpecificTop = ss.viewTop;
        }
        requestLayout();
    }
}
