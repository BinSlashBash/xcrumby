package twowayview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.media.TransportMediator;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnTouchModeChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.Scroller;
import com.crumby.C0065R;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lucasr.twowayview.C0708R;

public class TwoWayView extends AdapterView<ListAdapter> implements OnTouchModeChangeListener {
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
    public static final int[] STATE_NOTHING;
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
    private ContextMenuInfo mContextMenuInfo;
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
    private boolean mIsAttached;
    private boolean mIsChildViewEnabled;
    final boolean[] mIsScrap;
    private boolean mIsVertical;
    private int mItemCount;
    private int mItemMargin;
    private boolean mItemsCanFocus;
    private int mLastAccessibilityScrollEventFromIndex;
    private int mLastAccessibilityScrollEventToIndex;
    private int mLastScrollState;
    private int mLastTouchMode;
    private float mLastTouchPos;
    private int mLayoutMode;
    private final int mMaximumVelocity;
    private int mMotionPosition;
    private boolean mNeedSync;
    private int mNextSelectedPosition;
    private long mNextSelectedRowId;
    private int mOldItemCount;
    private int mOldSelectedPosition;
    private long mOldSelectedRowId;
    private OnScrollListener mOnScrollListener;
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
    private int mTouchMode;
    private Runnable mTouchModeReset;
    private float mTouchRemainderPos;
    private final int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    /* renamed from: twowayview.TwoWayView.1 */
    class C07111 implements Runnable {
        final /* synthetic */ View val$child;
        final /* synthetic */ PerformClick val$performClick;

        C07111(View view, PerformClick performClick) {
            this.val$child = view;
            this.val$performClick = performClick;
        }

        public void run() {
            TwoWayView.this.mTouchMode = TwoWayView.TOUCH_MODE_UNKNOWN;
            TwoWayView.this.setPressed(false);
            this.val$child.setPressed(false);
            if (!TwoWayView.this.mDataChanged) {
                this.val$performClick.run();
            }
            TwoWayView.this.mTouchModeReset = null;
        }
    }

    private class AdapterDataSetObserver extends DataSetObserver {
        private Parcelable mInstanceState;

        private AdapterDataSetObserver() {
            this.mInstanceState = null;
        }

        public void onChanged() {
            TwoWayView.this.mDataChanged = true;
            TwoWayView.this.mOldItemCount = TwoWayView.this.mItemCount;
            TwoWayView.this.mItemCount = TwoWayView.this.getAdapter().getCount();
            if (!TwoWayView.this.mHasStableIds || this.mInstanceState == null || TwoWayView.this.mOldItemCount != 0 || TwoWayView.this.mItemCount <= 0) {
                TwoWayView.this.rememberSyncState();
            } else {
                TwoWayView.this.onRestoreInstanceState(this.mInstanceState);
                this.mInstanceState = null;
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
            TwoWayView.this.mItemCount = TwoWayView.TOUCH_MODE_ON;
            TwoWayView.this.mSelectedPosition = TwoWayView.TOUCH_MODE_UNKNOWN;
            TwoWayView.this.mSelectedRowId = Long.MIN_VALUE;
            TwoWayView.this.mNextSelectedPosition = TwoWayView.TOUCH_MODE_UNKNOWN;
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

        void populate(int selectedPosition, int amountToScroll) {
            this.mSelectedPosition = selectedPosition;
            this.mAmountToScroll = amountToScroll;
        }

        public int getSelectedPosition() {
            return this.mSelectedPosition;
        }

        public int getAmountToScroll() {
            return this.mAmountToScroll;
        }
    }

    private final class CheckForTap implements Runnable {
        private CheckForTap() {
        }

        public void run() {
            if (TwoWayView.this.mTouchMode == 0) {
                TwoWayView.this.mTouchMode = TwoWayView.TOUCH_MODE_TAP;
                View child = TwoWayView.this.getChildAt(TwoWayView.this.mMotionPosition - TwoWayView.this.mFirstPosition);
                if (child != null && !child.hasFocusable()) {
                    TwoWayView.this.mLayoutMode = TwoWayView.TOUCH_MODE_ON;
                    if (TwoWayView.this.mDataChanged) {
                        TwoWayView.this.mTouchMode = TwoWayView.TOUCH_MODE_DONE_WAITING;
                        return;
                    }
                    TwoWayView.this.setPressed(true);
                    child.setPressed(true);
                    TwoWayView.this.layoutChildren();
                    TwoWayView.this.positionSelector(TwoWayView.this.mMotionPosition, child);
                    TwoWayView.this.refreshDrawableState();
                    TwoWayView.this.positionSelector(TwoWayView.this.mMotionPosition, child);
                    TwoWayView.this.refreshDrawableState();
                    boolean longClickable = TwoWayView.this.isLongClickable();
                    if (TwoWayView.this.mSelector != null) {
                        Drawable d = TwoWayView.this.mSelector.getCurrent();
                        if (d != null && (d instanceof TransitionDrawable)) {
                            if (longClickable) {
                                ((TransitionDrawable) d).startTransition(ViewConfiguration.getLongPressTimeout());
                            } else {
                                ((TransitionDrawable) d).resetTransition();
                            }
                        }
                    }
                    if (longClickable) {
                        TwoWayView.this.triggerCheckForLongPress();
                    } else {
                        TwoWayView.this.mTouchMode = TwoWayView.TOUCH_MODE_DONE_WAITING;
                    }
                }
            }
        }
    }

    public enum ChoiceMode {
        NONE,
        SINGLE,
        MULTIPLE
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        boolean forceAdd;
        long id;
        int scrappedFromPosition;
        int viewType;

        public LayoutParams(int width, int height) {
            super(width, height);
            this.id = -1;
            if (this.width == TwoWayView.TOUCH_MODE_UNKNOWN) {
                Log.w(TwoWayView.LOGTAG, "Constructing LayoutParams with width FILL_PARENT does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.width = -2;
            }
            if (this.height == TwoWayView.TOUCH_MODE_UNKNOWN) {
                Log.w(TwoWayView.LOGTAG, "Constructing LayoutParams with height FILL_PARENT does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.height = -2;
            }
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.id = -1;
            if (this.width == TwoWayView.TOUCH_MODE_UNKNOWN) {
                Log.w(TwoWayView.LOGTAG, "Inflation setting LayoutParams width to MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.width = TwoWayView.TOUCH_MODE_UNKNOWN;
            }
            if (this.height == TwoWayView.TOUCH_MODE_UNKNOWN) {
                Log.w(TwoWayView.LOGTAG, "Inflation setting LayoutParams height to MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.height = -2;
            }
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams other) {
            super(other);
            this.id = -1;
            if (this.width == TwoWayView.TOUCH_MODE_UNKNOWN) {
                Log.w(TwoWayView.LOGTAG, "Constructing LayoutParams with width MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.width = -2;
            }
            if (this.height == TwoWayView.TOUCH_MODE_UNKNOWN) {
                Log.w(TwoWayView.LOGTAG, "Constructing LayoutParams with height MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.height = -2;
            }
        }
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScroll(TwoWayView twoWayView, int i, int i2, int i3);

        void onScrollStateChanged(TwoWayView twoWayView, int i);
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
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
            this.mActiveViews = new View[TwoWayView.TOUCH_MODE_ON];
        }

        public void setViewTypeCount(int viewTypeCount) {
            if (viewTypeCount < TwoWayView.TOUCH_MODE_TAP) {
                throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
            }
            ArrayList<View>[] scrapViews = new ArrayList[viewTypeCount];
            for (int i = TwoWayView.TOUCH_MODE_ON; i < viewTypeCount; i += TwoWayView.TOUCH_MODE_TAP) {
                scrapViews[i] = new ArrayList();
            }
            this.mViewTypeCount = viewTypeCount;
            this.mCurrentScrap = scrapViews[TwoWayView.TOUCH_MODE_ON];
            this.mScrapViews = scrapViews;
        }

        public void markChildrenDirty() {
            int i;
            if (this.mViewTypeCount == TwoWayView.TOUCH_MODE_TAP) {
                ArrayList<View> scrap = this.mCurrentScrap;
                int scrapCount = scrap.size();
                for (i = TwoWayView.TOUCH_MODE_ON; i < scrapCount; i += TwoWayView.TOUCH_MODE_TAP) {
                    ((View) scrap.get(i)).forceLayout();
                }
            } else {
                int typeCount = this.mViewTypeCount;
                for (i = TwoWayView.TOUCH_MODE_ON; i < typeCount; i += TwoWayView.TOUCH_MODE_TAP) {
                    Iterator i$ = this.mScrapViews[i].iterator();
                    while (i$.hasNext()) {
                        ((View) i$.next()).forceLayout();
                    }
                }
            }
            if (this.mTransientStateViews != null) {
                int count = this.mTransientStateViews.size();
                for (i = TwoWayView.TOUCH_MODE_ON; i < count; i += TwoWayView.TOUCH_MODE_TAP) {
                    ((View) this.mTransientStateViews.valueAt(i)).forceLayout();
                }
            }
        }

        public boolean shouldRecycleViewType(int viewType) {
            return viewType >= 0;
        }

        void clear() {
            ArrayList<View> scrap;
            int scrapCount;
            int i;
            if (this.mViewTypeCount == TwoWayView.TOUCH_MODE_TAP) {
                scrap = this.mCurrentScrap;
                scrapCount = scrap.size();
                for (i = TwoWayView.TOUCH_MODE_ON; i < scrapCount; i += TwoWayView.TOUCH_MODE_TAP) {
                    TwoWayView.this.removeDetachedView((View) scrap.remove((scrapCount + TwoWayView.TOUCH_MODE_UNKNOWN) - i), false);
                }
            } else {
                int typeCount = this.mViewTypeCount;
                for (i = TwoWayView.TOUCH_MODE_ON; i < typeCount; i += TwoWayView.TOUCH_MODE_TAP) {
                    scrap = this.mScrapViews[i];
                    scrapCount = scrap.size();
                    for (int j = TwoWayView.TOUCH_MODE_ON; j < scrapCount; j += TwoWayView.TOUCH_MODE_TAP) {
                        TwoWayView.this.removeDetachedView((View) scrap.remove((scrapCount + TwoWayView.TOUCH_MODE_UNKNOWN) - j), false);
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
            for (int i = TwoWayView.TOUCH_MODE_ON; i < childCount; i += TwoWayView.TOUCH_MODE_TAP) {
                activeViews[i] = TwoWayView.this.getChildAt(i);
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
            if (this.mViewTypeCount == TwoWayView.TOUCH_MODE_TAP) {
                return retrieveFromScrap(this.mCurrentScrap, position);
            }
            int whichScrap = TwoWayView.this.mAdapter.getItemViewType(position);
            if (whichScrap < 0 || whichScrap >= this.mScrapViews.length) {
                return null;
            }
            return retrieveFromScrap(this.mScrapViews[whichScrap], position);
        }

        @TargetApi(14)
        void addScrapView(View scrap, int position) {
            LayoutParams lp = (LayoutParams) scrap.getLayoutParams();
            if (lp != null) {
                lp.scrappedFromPosition = position;
                int viewType = lp.viewType;
                boolean scrapHasTransientState = ViewCompat.hasTransientState(scrap);
                if (shouldRecycleViewType(viewType) && !scrapHasTransientState) {
                    if (this.mViewTypeCount == TwoWayView.TOUCH_MODE_TAP) {
                        this.mCurrentScrap.add(scrap);
                    } else {
                        this.mScrapViews[viewType].add(scrap);
                    }
                    if (VERSION.SDK_INT >= 14) {
                        scrap.setAccessibilityDelegate(null);
                    }
                    if (this.mRecyclerListener != null) {
                        this.mRecyclerListener.onMovedToScrapHeap(scrap);
                    }
                } else if (scrapHasTransientState) {
                    if (this.mTransientStateViews == null) {
                        this.mTransientStateViews = new SparseArrayCompat();
                    }
                    this.mTransientStateViews.put(position, scrap);
                }
            }
        }

        @TargetApi(14)
        void scrapActiveViews() {
            boolean multipleScraps = true;
            View[] activeViews = this.mActiveViews;
            if (this.mViewTypeCount <= TwoWayView.TOUCH_MODE_TAP) {
                multipleScraps = false;
            }
            ArrayList<View> scrapViews = this.mCurrentScrap;
            for (int i = activeViews.length + TwoWayView.TOUCH_MODE_UNKNOWN; i >= 0; i += TwoWayView.TOUCH_MODE_UNKNOWN) {
                View victim = activeViews[i];
                if (victim != null) {
                    LayoutParams lp = (LayoutParams) victim.getLayoutParams();
                    int whichScrap = lp.viewType;
                    activeViews[i] = null;
                    boolean scrapHasTransientState = ViewCompat.hasTransientState(victim);
                    if (shouldRecycleViewType(whichScrap) && !scrapHasTransientState) {
                        if (multipleScraps) {
                            scrapViews = this.mScrapViews[whichScrap];
                        }
                        lp.scrappedFromPosition = this.mFirstActivePosition + i;
                        scrapViews.add(victim);
                        if (VERSION.SDK_INT >= 14) {
                            victim.setAccessibilityDelegate(null);
                        }
                        if (this.mRecyclerListener != null) {
                            this.mRecyclerListener.onMovedToScrapHeap(victim);
                        }
                    } else if (scrapHasTransientState) {
                        TwoWayView.this.removeDetachedView(victim, false);
                        if (this.mTransientStateViews == null) {
                            this.mTransientStateViews = new SparseArrayCompat();
                        }
                        this.mTransientStateViews.put(this.mFirstActivePosition + i, victim);
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
            for (i = TwoWayView.TOUCH_MODE_ON; i < viewTypeCount; i += TwoWayView.TOUCH_MODE_TAP) {
                ArrayList<View> scrapPile = scrapViews[i];
                int size = scrapPile.size();
                int extras = size - maxViews;
                size += TwoWayView.TOUCH_MODE_UNKNOWN;
                int j = TwoWayView.TOUCH_MODE_ON;
                int size2 = size;
                while (j < extras) {
                    size = size2 + TwoWayView.TOUCH_MODE_UNKNOWN;
                    TwoWayView.this.removeDetachedView((View) scrapPile.remove(size2), false);
                    j += TwoWayView.TOUCH_MODE_TAP;
                    size2 = size;
                }
            }
            if (this.mTransientStateViews != null) {
                i = TwoWayView.TOUCH_MODE_ON;
                while (i < this.mTransientStateViews.size()) {
                    if (!ViewCompat.hasTransientState((View) this.mTransientStateViews.valueAt(i))) {
                        this.mTransientStateViews.removeAt(i);
                        i += TwoWayView.TOUCH_MODE_UNKNOWN;
                    }
                    i += TwoWayView.TOUCH_MODE_TAP;
                }
            }
        }

        void reclaimScrapViews(List<View> views) {
            if (this.mViewTypeCount == TwoWayView.TOUCH_MODE_TAP) {
                views.addAll(this.mCurrentScrap);
                return;
            }
            int viewTypeCount = this.mViewTypeCount;
            ArrayList<View>[] scrapViews = this.mScrapViews;
            for (int i = TwoWayView.TOUCH_MODE_ON; i < viewTypeCount; i += TwoWayView.TOUCH_MODE_TAP) {
                views.addAll(scrapViews[i]);
            }
        }

        View retrieveFromScrap(ArrayList<View> scrapViews, int position) {
            int size = scrapViews.size();
            if (size <= 0) {
                return null;
            }
            for (int i = TwoWayView.TOUCH_MODE_ON; i < size; i += TwoWayView.TOUCH_MODE_TAP) {
                View scrapView = (View) scrapViews.get(i);
                if (((LayoutParams) scrapView.getLayoutParams()).scrappedFromPosition == position) {
                    scrapViews.remove(i);
                    return scrapView;
                }
            }
            return (View) scrapViews.remove(size + TwoWayView.TOUCH_MODE_UNKNOWN);
        }
    }

    public interface RecyclerListener {
        void onMovedToScrapHeap(View view);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR;
        LongSparseArray<Integer> checkIdState;
        SparseBooleanArray checkState;
        int checkedItemCount;
        long firstId;
        int height;
        int position;
        long selectedId;
        int viewStart;

        /* renamed from: twowayview.TwoWayView.SavedState.1 */
        static class C07121 implements Creator<SavedState> {
            C07121() {
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.selectedId = in.readLong();
            this.firstId = in.readLong();
            this.viewStart = in.readInt();
            this.position = in.readInt();
            this.height = in.readInt();
            this.checkedItemCount = in.readInt();
            this.checkState = in.readSparseBooleanArray();
            int N = in.readInt();
            if (N > 0) {
                this.checkIdState = new LongSparseArray();
                for (int i = TwoWayView.TOUCH_MODE_ON; i < N; i += TwoWayView.TOUCH_MODE_TAP) {
                    this.checkIdState.put(in.readLong(), Integer.valueOf(in.readInt()));
                }
            }
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(this.selectedId);
            out.writeLong(this.firstId);
            out.writeInt(this.viewStart);
            out.writeInt(this.position);
            out.writeInt(this.height);
            out.writeInt(this.checkedItemCount);
            out.writeSparseBooleanArray(this.checkState);
            int N = this.checkIdState != null ? this.checkIdState.size() : TwoWayView.TOUCH_MODE_ON;
            out.writeInt(N);
            for (int i = TwoWayView.TOUCH_MODE_ON; i < N; i += TwoWayView.TOUCH_MODE_TAP) {
                out.writeLong(this.checkIdState.keyAt(i));
                out.writeInt(((Integer) this.checkIdState.valueAt(i)).intValue());
            }
        }

        public String toString() {
            return "TwoWayView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewStart=" + this.viewStart + " height=" + this.height + " position=" + this.position + " checkState=" + this.checkState + "}";
        }

        static {
            CREATOR = new C07121();
        }
    }

    private class SelectionNotifier implements Runnable {
        private SelectionNotifier() {
        }

        public void run() {
            if (!TwoWayView.this.mDataChanged) {
                TwoWayView.this.fireOnSelected();
                TwoWayView.this.performAccessibilityActionsOnSelected();
            } else if (TwoWayView.this.mAdapter != null) {
                TwoWayView.this.post(this);
            }
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
            return TwoWayView.this.hasWindowFocus() && TwoWayView.this.getWindowAttachCount() == this.mOriginalAttachCount;
        }
    }

    private class CheckForKeyLongPress extends WindowRunnnable implements Runnable {
        private CheckForKeyLongPress() {
            super(null);
        }

        public void run() {
            if (TwoWayView.this.isPressed() && TwoWayView.this.mSelectedPosition >= 0) {
                View v = TwoWayView.this.getChildAt(TwoWayView.this.mSelectedPosition - TwoWayView.this.mFirstPosition);
                if (TwoWayView.this.mDataChanged) {
                    TwoWayView.this.setPressed(false);
                    if (v != null) {
                        v.setPressed(false);
                        return;
                    }
                    return;
                }
                boolean handled = false;
                if (sameWindow()) {
                    handled = TwoWayView.this.performLongPress(v, TwoWayView.this.mSelectedPosition, TwoWayView.this.mSelectedRowId);
                }
                if (handled) {
                    TwoWayView.this.setPressed(false);
                    v.setPressed(false);
                }
            }
        }
    }

    private class CheckForLongPress extends WindowRunnnable implements Runnable {
        private CheckForLongPress() {
            super(null);
        }

        public void run() {
            int motionPosition = TwoWayView.this.mMotionPosition;
            View child = TwoWayView.this.getChildAt(motionPosition - TwoWayView.this.mFirstPosition);
            if (child != null) {
                long longPressId = TwoWayView.this.mAdapter.getItemId(TwoWayView.this.mMotionPosition);
                boolean handled = false;
                if (sameWindow() && !TwoWayView.this.mDataChanged) {
                    handled = TwoWayView.this.performLongPress(child, motionPosition, longPressId);
                }
                if (handled) {
                    TwoWayView.this.mTouchMode = TwoWayView.TOUCH_MODE_UNKNOWN;
                    TwoWayView.this.setPressed(false);
                    child.setPressed(false);
                    return;
                }
                TwoWayView.this.mTouchMode = TwoWayView.TOUCH_MODE_DONE_WAITING;
            }
        }
    }

    private class ListItemAccessibilityDelegate extends AccessibilityDelegateCompat {
        private ListItemAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            int position = TwoWayView.this.getPositionForView(host);
            ListAdapter adapter = TwoWayView.this.getAdapter();
            if (position != TwoWayView.TOUCH_MODE_UNKNOWN && adapter != null && TwoWayView.this.isEnabled() && adapter.isEnabled(position)) {
                if (position == TwoWayView.this.getSelectedItemPosition()) {
                    info.setSelected(true);
                    info.addAction(8);
                } else {
                    info.addAction(TwoWayView.TOUCH_MODE_FLINGING);
                }
                if (TwoWayView.this.isClickable()) {
                    info.addAction(16);
                    info.setClickable(true);
                }
                if (TwoWayView.this.isLongClickable()) {
                    info.addAction(32);
                    info.setLongClickable(true);
                }
            }
        }

        public boolean performAccessibilityAction(View host, int action, Bundle arguments) {
            if (super.performAccessibilityAction(host, action, arguments)) {
                return true;
            }
            int position = TwoWayView.this.getPositionForView(host);
            ListAdapter adapter = TwoWayView.this.getAdapter();
            if (position == TwoWayView.TOUCH_MODE_UNKNOWN || adapter == null) {
                return false;
            }
            if (!TwoWayView.this.isEnabled() || !adapter.isEnabled(position)) {
                return false;
            }
            long id = TwoWayView.this.getItemIdAtPosition(position);
            switch (action) {
                case TwoWayView.TOUCH_MODE_FLINGING /*4*/:
                    if (TwoWayView.this.getSelectedItemPosition() == position) {
                        return false;
                    }
                    TwoWayView.this.setSelection(position);
                    return true;
                case Std.STD_LOCALE /*8*/:
                    if (TwoWayView.this.getSelectedItemPosition() != position) {
                        return false;
                    }
                    TwoWayView.this.setSelection(TwoWayView.TOUCH_MODE_UNKNOWN);
                    return true;
                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                    if (TwoWayView.this.isClickable() && TwoWayView.this.performItemClick(host, position, id)) {
                        return true;
                    }
                    return false;
                case TransportMediator.FLAG_KEY_MEDIA_STOP /*32*/:
                    if (TwoWayView.this.isLongClickable() && TwoWayView.this.performLongPress(host, position, id)) {
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        }
    }

    private class PerformClick extends WindowRunnnable implements Runnable {
        int mClickMotionPosition;

        private PerformClick() {
            super(null);
        }

        public void run() {
            if (!TwoWayView.this.mDataChanged) {
                ListAdapter adapter = TwoWayView.this.mAdapter;
                int motionPosition = this.mClickMotionPosition;
                if (adapter != null && TwoWayView.this.mItemCount > 0 && motionPosition != TwoWayView.TOUCH_MODE_UNKNOWN && motionPosition < adapter.getCount() && sameWindow()) {
                    View child = TwoWayView.this.getChildAt(motionPosition - TwoWayView.this.mFirstPosition);
                    if (child != null) {
                        TwoWayView.this.performItemClick(child, motionPosition, adapter.getItemId(motionPosition));
                    }
                }
            }
        }
    }

    static {
        int[] iArr = new int[TOUCH_MODE_TAP];
        iArr[TOUCH_MODE_ON] = TOUCH_MODE_ON;
        STATE_NOTHING = iArr;
    }

    public TwoWayView(Context context) {
        this(context, null);
    }

    public TwoWayView(Context context, AttributeSet attrs) {
        this(context, attrs, TOUCH_MODE_ON);
    }

    public TwoWayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIsScrap = new boolean[TOUCH_MODE_TAP];
        this.mNeedSync = false;
        this.mVelocityTracker = null;
        this.mLayoutMode = TOUCH_MODE_ON;
        this.mTouchMode = TOUCH_MODE_UNKNOWN;
        this.mLastTouchMode = TOUCH_MODE_UNKNOWN;
        this.mIsAttached = false;
        this.mContextMenuInfo = null;
        this.mOnScrollListener = null;
        this.mLastScrollState = TOUCH_MODE_ON;
        ViewConfiguration vc = ViewConfiguration.get(context);
        this.mTouchSlop = vc.getScaledTouchSlop();
        this.mMaximumVelocity = vc.getScaledMaximumFlingVelocity();
        this.mFlingVelocity = vc.getScaledMinimumFlingVelocity();
        this.mOverscrollDistance = getScaledOverscrollDistance(vc);
        this.mOverScroll = TOUCH_MODE_ON;
        this.mScroller = new Scroller(context);
        this.mIsVertical = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.mArrowScrollFocusResult = new ArrowScrollFocusResult();
        this.mSelectorPosition = TOUCH_MODE_UNKNOWN;
        this.mSelectorRect = new Rect();
        this.mSelectedStart = TOUCH_MODE_ON;
        this.mResurrectToPosition = TOUCH_MODE_UNKNOWN;
        this.mSelectedStart = TOUCH_MODE_ON;
        this.mNextSelectedPosition = TOUCH_MODE_UNKNOWN;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mSelectedPosition = TOUCH_MODE_UNKNOWN;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mOldSelectedPosition = TOUCH_MODE_UNKNOWN;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        this.mChoiceMode = ChoiceMode.NONE;
        this.mCheckedItemCount = TOUCH_MODE_ON;
        this.mCheckedIdStates = null;
        this.mCheckStates = null;
        this.mRecycler = new RecycleBin();
        this.mDataSetObserver = null;
        this.mAreAllItemsSelectable = true;
        this.mStartEdge = null;
        this.mEndEdge = null;
        setClickable(true);
        setFocusableInTouchMode(true);
        setWillNotDraw(false);
        setAlwaysDrawnWithCacheEnabled(false);
        setWillNotDraw(false);
        setClipToPadding(false);
        ViewCompat.setOverScrollMode(this, TOUCH_MODE_TAP);
        TypedArray a = context.obtainStyledAttributes(attrs, C0708R.styleable.TwoWayView, defStyle, TOUCH_MODE_ON);
        this.mDrawSelectorOnTop = a.getBoolean(C0708R.styleable.TwoWayView_android_drawSelectorOnTop, false);
        Drawable d = a.getDrawable(C0708R.styleable.TwoWayView_android_listSelector);
        if (d != null) {
            setSelector(d);
        }
        int orientation = a.getInt(C0708R.styleable.TwoWayView_android_orientation, TOUCH_MODE_UNKNOWN);
        if (orientation >= 0) {
            setOrientation(Orientation.values()[orientation]);
        }
        int choiceMode = a.getInt(C0708R.styleable.TwoWayView_android_choiceMode, TOUCH_MODE_UNKNOWN);
        if (choiceMode >= 0) {
            setChoiceMode(ChoiceMode.values()[choiceMode]);
        }
        a.recycle();
    }

    public void setOrientation(Orientation orientation) {
        boolean isVertical = orientation == Orientation.VERTICAL;
        if (this.mIsVertical != isVertical) {
            this.mIsVertical = isVertical;
            resetState();
            this.mRecycler.clear();
            requestLayout();
        }
    }

    public Orientation getOrientation() {
        return this.mIsVertical ? Orientation.VERTICAL : Orientation.HORIZONTAL;
    }

    public void setItemMargin(int itemMargin) {
        if (this.mItemMargin != itemMargin) {
            this.mItemMargin = itemMargin;
            requestLayout();
        }
    }

    public int getItemMargin() {
        return this.mItemMargin;
    }

    public void setItemsCanFocus(boolean itemsCanFocus) {
        this.mItemsCanFocus = itemsCanFocus;
        if (!itemsCanFocus) {
            setDescendantFocusability(393216);
        }
    }

    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mOnScrollListener = l;
        invokeOnItemScrollListener();
    }

    public void setRecyclerListener(RecyclerListener l) {
        this.mRecycler.mRecyclerListener = l;
    }

    public void setDrawSelectorOnTop(boolean drawSelectorOnTop) {
        this.mDrawSelectorOnTop = drawSelectorOnTop;
    }

    public void setSelector(int resID) {
        setSelector(getResources().getDrawable(resID));
    }

    public void setSelector(Drawable selector) {
        if (this.mSelector != null) {
            this.mSelector.setCallback(null);
            unscheduleDrawable(this.mSelector);
        }
        this.mSelector = selector;
        selector.getPadding(new Rect());
        selector.setCallback(this);
        updateSelectorState();
    }

    public Drawable getSelector() {
        return this.mSelector;
    }

    public int getSelectedItemPosition() {
        return this.mNextSelectedPosition;
    }

    public long getSelectedItemId() {
        return this.mNextSelectedRowId;
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    public boolean isItemChecked(int position) {
        if (this.mChoiceMode != ChoiceMode.NONE || this.mCheckStates == null) {
            return false;
        }
        return this.mCheckStates.get(position);
    }

    public int getCheckedItemPosition() {
        if (this.mChoiceMode == ChoiceMode.SINGLE && this.mCheckStates != null && this.mCheckStates.size() == TOUCH_MODE_TAP) {
            return this.mCheckStates.keyAt(TOUCH_MODE_ON);
        }
        return TOUCH_MODE_UNKNOWN;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode != ChoiceMode.NONE) {
            return this.mCheckStates;
        }
        return null;
    }

    public long[] getCheckedItemIds() {
        if (this.mChoiceMode == ChoiceMode.NONE || this.mCheckedIdStates == null || this.mAdapter == null) {
            return new long[TOUCH_MODE_ON];
        }
        LongSparseArray<Integer> idStates = this.mCheckedIdStates;
        int count = idStates.size();
        long[] ids = new long[count];
        for (int i = TOUCH_MODE_ON; i < count; i += TOUCH_MODE_TAP) {
            ids[i] = idStates.keyAt(i);
        }
        return ids;
    }

    public void setItemChecked(int position, boolean value) {
        if (this.mChoiceMode != ChoiceMode.NONE) {
            if (this.mChoiceMode == ChoiceMode.MULTIPLE) {
                boolean oldValue = this.mCheckStates.get(position);
                this.mCheckStates.put(position, value);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (value) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(position));
                    }
                }
                if (oldValue != value) {
                    if (value) {
                        this.mCheckedItemCount += TOUCH_MODE_TAP;
                    } else {
                        this.mCheckedItemCount += TOUCH_MODE_UNKNOWN;
                    }
                }
            } else {
                boolean updateIds;
                if (this.mCheckedIdStates == null || !this.mAdapter.hasStableIds()) {
                    updateIds = false;
                } else {
                    updateIds = true;
                }
                if (value || isItemChecked(position)) {
                    this.mCheckStates.clear();
                    if (updateIds) {
                        this.mCheckedIdStates.clear();
                    }
                }
                if (value) {
                    this.mCheckStates.put(position, true);
                    if (updateIds) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                    }
                    this.mCheckedItemCount = TOUCH_MODE_TAP;
                } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(TOUCH_MODE_ON)) {
                    this.mCheckedItemCount = TOUCH_MODE_ON;
                }
            }
            if (!this.mInLayout && !this.mBlockLayoutRequests) {
                this.mDataChanged = true;
                rememberSyncState();
                requestLayout();
            }
        }
    }

    public void clearChoices() {
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        this.mCheckedItemCount = TOUCH_MODE_ON;
    }

    public ChoiceMode getChoiceMode() {
        return this.mChoiceMode;
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

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ListAdapter adapter) {
        if (!(this.mAdapter == null || this.mDataSetObserver == null)) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetState();
        this.mRecycler.clear();
        this.mAdapter = adapter;
        this.mDataChanged = true;
        this.mOldSelectedPosition = TOUCH_MODE_UNKNOWN;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = adapter.getCount();
            this.mDataSetObserver = new AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mRecycler.setViewTypeCount(adapter.getViewTypeCount());
            this.mHasStableIds = adapter.hasStableIds();
            this.mAreAllItemsSelectable = adapter.areAllItemsEnabled();
            if (this.mChoiceMode != ChoiceMode.NONE && this.mHasStableIds && this.mCheckedIdStates == null) {
                this.mCheckedIdStates = new LongSparseArray();
            }
            int position = lookForSelectablePosition(TOUCH_MODE_ON);
            setSelectedPositionInt(position);
            setNextSelectedPositionInt(position);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            this.mItemCount = TOUCH_MODE_ON;
            this.mHasStableIds = false;
            this.mAreAllItemsSelectable = true;
            checkSelectionChanged();
        }
        checkFocus();
        requestLayout();
    }

    public int getFirstVisiblePosition() {
        return this.mFirstPosition;
    }

    public int getLastVisiblePosition() {
        return (this.mFirstPosition + getChildCount()) + TOUCH_MODE_UNKNOWN;
    }

    public int getCount() {
        return this.mItemCount;
    }

    public int getPositionForView(View view) {
        View child = view;
        while (true) {
            try {
                View v = (View) child.getParent();
                if (v.equals(this)) {
                    break;
                }
                child = v;
            } catch (ClassCastException e) {
                return TOUCH_MODE_UNKNOWN;
            }
        }
        int childCount = getChildCount();
        for (int i = TOUCH_MODE_ON; i < childCount; i += TOUCH_MODE_TAP) {
            if (getChildAt(i).equals(child)) {
                return this.mFirstPosition + i;
            }
        }
        return TOUCH_MODE_UNKNOWN;
    }

    public void getFocusedRect(Rect r) {
        View view = getSelectedView();
        if (view == null || view.getParent() != this) {
            super.getFocusedRect(r);
            return;
        }
        view.getFocusedRect(r);
        offsetDescendantRectToMyCoords(view, r);
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus && this.mSelectedPosition < 0 && !isInTouchMode()) {
            if (!(this.mIsAttached || this.mAdapter == null)) {
                this.mDataChanged = true;
                this.mOldItemCount = this.mItemCount;
                this.mItemCount = this.mAdapter.getCount();
            }
            resurrectSelection();
        }
        ListAdapter adapter = this.mAdapter;
        int closetChildIndex = TOUCH_MODE_UNKNOWN;
        int closestChildStart = TOUCH_MODE_ON;
        if (!(adapter == null || !gainFocus || previouslyFocusedRect == null)) {
            previouslyFocusedRect.offset(getScrollX(), getScrollY());
            if (adapter.getCount() < getChildCount() + this.mFirstPosition) {
                this.mLayoutMode = TOUCH_MODE_ON;
                layoutChildren();
            }
            Rect otherRect = this.mTempRect;
            int minDistance = Integer.MAX_VALUE;
            int childCount = getChildCount();
            int firstPosition = this.mFirstPosition;
            for (int i = TOUCH_MODE_ON; i < childCount; i += TOUCH_MODE_TAP) {
                if (adapter.isEnabled(firstPosition + i)) {
                    View other = getChildAt(i);
                    other.getDrawingRect(otherRect);
                    offsetDescendantRectToMyCoords(other, otherRect);
                    int distance = getDistance(previouslyFocusedRect, otherRect, direction);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closetChildIndex = i;
                        closestChildStart = this.mIsVertical ? other.getTop() : other.getLeft();
                    }
                }
            }
        }
        if (closetChildIndex >= 0) {
            setSelectionFromOffset(this.mFirstPosition + closetChildIndex, closestChildStart);
        } else {
            requestLayout();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnTouchModeChangeListener(this);
        if (this.mAdapter != null && this.mDataSetObserver == null) {
            this.mDataSetObserver = new AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
        this.mIsAttached = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRecycler.clear();
        getViewTreeObserver().removeOnTouchModeChangeListener(this);
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            this.mDataSetObserver = null;
        }
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
        if (this.mTouchModeReset != null) {
            removeCallbacks(this.mTouchModeReset);
            this.mTouchModeReset.run();
        }
        this.mIsAttached = false;
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        int touchMode;
        super.onWindowFocusChanged(hasWindowFocus);
        if (isInTouchMode()) {
            touchMode = TOUCH_MODE_ON;
        } else {
            touchMode = TOUCH_MODE_TAP;
        }
        if (hasWindowFocus) {
            if (!(touchMode == this.mLastTouchMode || this.mLastTouchMode == TOUCH_MODE_UNKNOWN)) {
                if (touchMode == TOUCH_MODE_TAP) {
                    resurrectSelection();
                } else {
                    hideSelector();
                    this.mLayoutMode = TOUCH_MODE_ON;
                    layoutChildren();
                }
            }
        } else if (touchMode == TOUCH_MODE_TAP) {
            this.mResurrectToPosition = this.mSelectedPosition;
        }
        this.mLastTouchMode = touchMode;
    }

    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        boolean needsInvalidate = false;
        if (this.mIsVertical && this.mOverScroll != scrollY) {
            onScrollChanged(getScrollX(), scrollY, getScrollX(), this.mOverScroll);
            this.mOverScroll = scrollY;
            needsInvalidate = true;
        } else if (!(this.mIsVertical || this.mOverScroll == scrollX)) {
            onScrollChanged(scrollX, getScrollY(), this.mOverScroll, getScrollY());
            this.mOverScroll = scrollX;
            needsInvalidate = true;
        }
        if (needsInvalidate) {
            invalidate();
            awakenScrollbarsInternal();
        }
    }

    @TargetApi(9)
    private boolean overScrollByInternal(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (VERSION.SDK_INT < 9) {
            return false;
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @TargetApi(9)
    public void setOverScrollMode(int mode) {
        if (VERSION.SDK_INT >= 9) {
            if (mode == TOUCH_MODE_DONE_WAITING) {
                this.mStartEdge = null;
                this.mEndEdge = null;
            } else if (this.mStartEdge == null) {
                Context context = getContext();
                this.mStartEdge = new EdgeEffectCompat(context);
                this.mEndEdge = new EdgeEffectCompat(context);
            }
            super.setOverScrollMode(mode);
        }
    }

    public int pointToPosition(int x, int y) {
        Rect frame = this.mTouchFrame;
        if (frame == null) {
            this.mTouchFrame = new Rect();
            frame = this.mTouchFrame;
        }
        for (int i = getChildCount() + TOUCH_MODE_UNKNOWN; i >= 0; i += TOUCH_MODE_UNKNOWN) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return this.mFirstPosition + i;
                }
            }
        }
        return TOUCH_MODE_UNKNOWN;
    }

    protected int computeVerticalScrollExtent() {
        int count = getChildCount();
        if (count == 0) {
            return TOUCH_MODE_ON;
        }
        int extent = count * SYNC_MAX_DURATION_MILLIS;
        View child = getChildAt(TOUCH_MODE_ON);
        int childTop = child.getTop();
        int childHeight = child.getHeight();
        if (childHeight > 0) {
            extent += (childTop * SYNC_MAX_DURATION_MILLIS) / childHeight;
        }
        child = getChildAt(count + TOUCH_MODE_UNKNOWN);
        int childBottom = child.getBottom();
        childHeight = child.getHeight();
        if (childHeight > 0) {
            return extent - (((childBottom - getHeight()) * SYNC_MAX_DURATION_MILLIS) / childHeight);
        }
        return extent;
    }

    protected int computeHorizontalScrollExtent() {
        int count = getChildCount();
        if (count == 0) {
            return TOUCH_MODE_ON;
        }
        int extent = count * SYNC_MAX_DURATION_MILLIS;
        View child = getChildAt(TOUCH_MODE_ON);
        int childLeft = child.getLeft();
        int childWidth = child.getWidth();
        if (childWidth > 0) {
            extent += (childLeft * SYNC_MAX_DURATION_MILLIS) / childWidth;
        }
        child = getChildAt(count + TOUCH_MODE_UNKNOWN);
        int childRight = child.getRight();
        childWidth = child.getWidth();
        if (childWidth > 0) {
            return extent - (((childRight - getWidth()) * SYNC_MAX_DURATION_MILLIS) / childWidth);
        }
        return extent;
    }

    protected int computeVerticalScrollOffset() {
        int firstPosition = this.mFirstPosition;
        int childCount = getChildCount();
        if (firstPosition < 0 || childCount == 0) {
            return TOUCH_MODE_ON;
        }
        View child = getChildAt(TOUCH_MODE_ON);
        int childTop = child.getTop();
        int childHeight = child.getHeight();
        if (childHeight > 0) {
            return Math.max((firstPosition * SYNC_MAX_DURATION_MILLIS) - ((childTop * SYNC_MAX_DURATION_MILLIS) / childHeight), TOUCH_MODE_ON);
        }
        return TOUCH_MODE_ON;
    }

    protected int computeHorizontalScrollOffset() {
        int firstPosition = this.mFirstPosition;
        int childCount = getChildCount();
        if (firstPosition < 0 || childCount == 0) {
            return TOUCH_MODE_ON;
        }
        View child = getChildAt(TOUCH_MODE_ON);
        int childLeft = child.getLeft();
        int childWidth = child.getWidth();
        if (childWidth > 0) {
            return Math.max((firstPosition * SYNC_MAX_DURATION_MILLIS) - ((childLeft * SYNC_MAX_DURATION_MILLIS) / childWidth), TOUCH_MODE_ON);
        }
        return TOUCH_MODE_ON;
    }

    protected int computeVerticalScrollRange() {
        int result = Math.max(this.mItemCount * SYNC_MAX_DURATION_MILLIS, TOUCH_MODE_ON);
        if (!this.mIsVertical || this.mOverScroll == 0) {
            return result;
        }
        return result + Math.abs((int) (((((float) this.mOverScroll) / ((float) getHeight())) * ((float) this.mItemCount)) * 100.0f));
    }

    protected int computeHorizontalScrollRange() {
        int result = Math.max(this.mItemCount * SYNC_MAX_DURATION_MILLIS, TOUCH_MODE_ON);
        if (this.mIsVertical || this.mOverScroll == 0) {
            return result;
        }
        return result + Math.abs((int) (((((float) this.mOverScroll) / ((float) getWidth())) * ((float) this.mItemCount)) * 100.0f));
    }

    public boolean showContextMenuForChild(View originalView) {
        int longPressPosition = getPositionForView(originalView);
        if (longPressPosition < 0) {
            return false;
        }
        long longPressId = this.mAdapter.getItemId(longPressPosition);
        boolean handled = false;
        OnItemLongClickListener listener = getOnItemLongClickListener();
        if (listener != null) {
            handled = listener.onItemLongClick(this, originalView, longPressPosition, longPressId);
        }
        if (handled) {
            return handled;
        }
        this.mContextMenuInfo = createContextMenuInfo(getChildAt(longPressPosition - this.mFirstPosition), longPressPosition, longPressId);
        return super.showContextMenuForChild(originalView);
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.mIsAttached || this.mAdapter == null) {
            return false;
        }
        switch (ev.getAction() & MotionEventCompat.ACTION_MASK) {
            case TOUCH_MODE_ON /*0*/:
                initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(ev);
                this.mScroller.abortAnimation();
                float x = ev.getX();
                float y = ev.getY();
                if (!this.mIsVertical) {
                    y = x;
                }
                this.mLastTouchPos = y;
                int motionPosition = findMotionRowOrColumn((int) this.mLastTouchPos);
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, TOUCH_MODE_ON);
                this.mTouchRemainderPos = 0.0f;
                if (this.mTouchMode != TOUCH_MODE_FLINGING) {
                    if (motionPosition >= 0) {
                        this.mMotionPosition = motionPosition;
                        this.mTouchMode = TOUCH_MODE_ON;
                        break;
                    }
                }
                return true;
                break;
            case TOUCH_MODE_TAP /*1*/:
            case TOUCH_MODE_DRAGGING /*3*/:
                this.mActivePointerId = TOUCH_MODE_UNKNOWN;
                this.mTouchMode = TOUCH_MODE_UNKNOWN;
                recycleVelocityTracker();
                reportScrollStateChange(TOUCH_MODE_ON);
                break;
            case TOUCH_MODE_DONE_WAITING /*2*/:
                if (this.mTouchMode == 0) {
                    initVelocityTrackerIfNotExists();
                    this.mVelocityTracker.addMovement(ev);
                    int index = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
                    if (index < 0) {
                        Log.e(LOGTAG, "onInterceptTouchEvent could not find pointer with id " + this.mActivePointerId + " - did TwoWayView receive an inconsistent " + "event stream?");
                        return false;
                    }
                    float pos;
                    if (this.mIsVertical) {
                        pos = MotionEventCompat.getY(ev, index);
                    } else {
                        pos = MotionEventCompat.getX(ev, index);
                    }
                    float diff = (pos - this.mLastTouchPos) + this.mTouchRemainderPos;
                    int delta = (int) diff;
                    this.mTouchRemainderPos = diff - ((float) delta);
                    if (maybeStartScrolling(delta)) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (isEnabled()) {
            if (!this.mIsAttached || this.mAdapter == null) {
                return false;
            }
            boolean needsInvalidate = false;
            initVelocityTrackerIfNotExists();
            this.mVelocityTracker.addMovement(ev);
            float x;
            float y;
            int motionPosition;
            switch (ev.getAction() & MotionEventCompat.ACTION_MASK) {
                case TOUCH_MODE_ON /*0*/:
                    if (!this.mDataChanged) {
                        float f;
                        this.mVelocityTracker.clear();
                        this.mScroller.abortAnimation();
                        x = ev.getX();
                        y = ev.getY();
                        if (this.mIsVertical) {
                            f = y;
                        } else {
                            f = x;
                        }
                        this.mLastTouchPos = f;
                        motionPosition = pointToPosition((int) x, (int) y);
                        this.mActivePointerId = MotionEventCompat.getPointerId(ev, TOUCH_MODE_ON);
                        this.mTouchRemainderPos = 0.0f;
                        if (!this.mDataChanged) {
                            if (this.mTouchMode != TOUCH_MODE_FLINGING) {
                                if (this.mMotionPosition >= 0 && this.mAdapter.isEnabled(this.mMotionPosition)) {
                                    this.mTouchMode = TOUCH_MODE_ON;
                                    triggerCheckForTap();
                                }
                                this.mMotionPosition = motionPosition;
                                break;
                            }
                            this.mTouchMode = TOUCH_MODE_DRAGGING;
                            reportScrollStateChange(TOUCH_MODE_TAP);
                            motionPosition = findMotionRowOrColumn((int) this.mLastTouchPos);
                            return true;
                        }
                    }
                    break;
                case TOUCH_MODE_TAP /*1*/:
                    switch (this.mTouchMode) {
                        case TOUCH_MODE_ON /*0*/:
                        case TOUCH_MODE_TAP /*1*/:
                        case TOUCH_MODE_DONE_WAITING /*2*/:
                            motionPosition = this.mMotionPosition;
                            View child = getChildAt(motionPosition - this.mFirstPosition);
                            x = ev.getX();
                            y = ev.getY();
                            boolean inList = this.mIsVertical ? x > ((float) getPaddingLeft()) && x < ((float) (getWidth() - getPaddingRight())) : y > ((float) getPaddingTop()) && y < ((float) (getHeight() - getPaddingBottom()));
                            if (!(child == null || child.hasFocusable() || !inList)) {
                                if (this.mTouchMode != 0) {
                                    child.setPressed(false);
                                }
                                if (this.mPerformClick == null) {
                                    TwoWayView twoWayView = this;
                                    this.mPerformClick = new PerformClick();
                                }
                                PerformClick performClick = this.mPerformClick;
                                performClick.mClickMotionPosition = motionPosition;
                                performClick.rememberWindowAttachCount();
                                this.mResurrectToPosition = motionPosition;
                                if (this.mTouchMode == 0 || this.mTouchMode == TOUCH_MODE_TAP) {
                                    if (this.mTouchMode == 0) {
                                        cancelCheckForTap();
                                    } else {
                                        cancelCheckForLongPress();
                                    }
                                    this.mLayoutMode = TOUCH_MODE_ON;
                                    if (this.mDataChanged || !this.mAdapter.isEnabled(motionPosition)) {
                                        this.mTouchMode = TOUCH_MODE_UNKNOWN;
                                        updateSelectorState();
                                    } else {
                                        this.mTouchMode = TOUCH_MODE_TAP;
                                        setPressed(true);
                                        positionSelector(this.mMotionPosition, child);
                                        child.setPressed(true);
                                        if (this.mSelector != null) {
                                            Drawable d = this.mSelector.getCurrent();
                                            if (d != null && (d instanceof TransitionDrawable)) {
                                                ((TransitionDrawable) d).resetTransition();
                                            }
                                        }
                                        if (this.mTouchModeReset != null) {
                                            removeCallbacks(this.mTouchModeReset);
                                        }
                                        this.mTouchModeReset = new C07111(child, performClick);
                                        postDelayed(this.mTouchModeReset, (long) ViewConfiguration.getPressedStateDuration());
                                    }
                                } else if (!this.mDataChanged && this.mAdapter.isEnabled(motionPosition)) {
                                    performClick.run();
                                }
                            }
                            this.mTouchMode = TOUCH_MODE_UNKNOWN;
                            updateSelectorState();
                            break;
                        case TOUCH_MODE_DRAGGING /*3*/:
                            if (!contentFits()) {
                                float velocity;
                                this.mVelocityTracker.computeCurrentVelocity(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, (float) this.mMaximumVelocity);
                                if (this.mIsVertical) {
                                    velocity = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                                } else {
                                    velocity = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
                                }
                                if (Math.abs(velocity) < ((float) this.mFlingVelocity)) {
                                    this.mTouchMode = TOUCH_MODE_UNKNOWN;
                                    reportScrollStateChange(TOUCH_MODE_ON);
                                    break;
                                }
                                float f2;
                                int i;
                                int i2;
                                int i3;
                                this.mTouchMode = TOUCH_MODE_FLINGING;
                                reportScrollStateChange(TOUCH_MODE_DONE_WAITING);
                                Scroller scroller = this.mScroller;
                                if (this.mIsVertical) {
                                    f2 = 0.0f;
                                } else {
                                    f2 = velocity;
                                }
                                int i4 = (int) f2;
                                if (!this.mIsVertical) {
                                    velocity = 0.0f;
                                }
                                int i5 = (int) velocity;
                                int i6 = this.mIsVertical ? TOUCH_MODE_ON : ExploreByTouchHelper.INVALID_ID;
                                if (this.mIsVertical) {
                                    i = TOUCH_MODE_ON;
                                } else {
                                    i = Integer.MAX_VALUE;
                                }
                                if (this.mIsVertical) {
                                    i2 = ExploreByTouchHelper.INVALID_ID;
                                } else {
                                    i2 = TOUCH_MODE_ON;
                                }
                                if (this.mIsVertical) {
                                    i3 = Integer.MAX_VALUE;
                                } else {
                                    i3 = TOUCH_MODE_ON;
                                }
                                scroller.fling(TOUCH_MODE_ON, TOUCH_MODE_ON, i4, i5, i6, i, i2, i3);
                                this.mLastTouchPos = 0.0f;
                                needsInvalidate = true;
                                break;
                            }
                            this.mTouchMode = TOUCH_MODE_UNKNOWN;
                            reportScrollStateChange(TOUCH_MODE_ON);
                            break;
                        case TOUCH_MODE_OVERSCROLL /*5*/:
                            this.mTouchMode = TOUCH_MODE_UNKNOWN;
                            reportScrollStateChange(TOUCH_MODE_ON);
                            break;
                    }
                    cancelCheckForTap();
                    cancelCheckForLongPress();
                    setPressed(false);
                    if (!(this.mStartEdge == null || this.mEndEdge == null)) {
                        needsInvalidate |= this.mStartEdge.onRelease() | this.mEndEdge.onRelease();
                    }
                    recycleVelocityTracker();
                    break;
                case TOUCH_MODE_DONE_WAITING /*2*/:
                    int index = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
                    if (index >= 0) {
                        float pos;
                        if (this.mIsVertical) {
                            pos = MotionEventCompat.getY(ev, index);
                        } else {
                            pos = MotionEventCompat.getX(ev, index);
                        }
                        if (this.mDataChanged) {
                            layoutChildren();
                        }
                        float diff = (pos - this.mLastTouchPos) + this.mTouchRemainderPos;
                        int delta = (int) diff;
                        this.mTouchRemainderPos = diff - ((float) delta);
                        switch (this.mTouchMode) {
                            case TOUCH_MODE_ON /*0*/:
                            case TOUCH_MODE_TAP /*1*/:
                            case TOUCH_MODE_DONE_WAITING /*2*/:
                                maybeStartScrolling(delta);
                                break;
                            case TOUCH_MODE_DRAGGING /*3*/:
                            case TOUCH_MODE_OVERSCROLL /*5*/:
                                this.mLastTouchPos = pos;
                                maybeScroll(delta);
                                break;
                            default:
                                break;
                        }
                    }
                    Log.e(LOGTAG, "onInterceptTouchEvent could not find pointer with id " + this.mActivePointerId + " - did TwoWayView receive an inconsistent " + "event stream?");
                    return false;
                case TOUCH_MODE_DRAGGING /*3*/:
                    cancelCheckForTap();
                    this.mTouchMode = TOUCH_MODE_UNKNOWN;
                    reportScrollStateChange(TOUCH_MODE_ON);
                    setPressed(false);
                    View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
                    if (motionView != null) {
                        motionView.setPressed(false);
                    }
                    if (!(this.mStartEdge == null || this.mEndEdge == null)) {
                        needsInvalidate = this.mStartEdge.onRelease() | this.mEndEdge.onRelease();
                    }
                    recycleVelocityTracker();
                    break;
            }
            if (needsInvalidate) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
            return true;
        } else if (isClickable() || isLongClickable()) {
            return true;
        } else {
            return false;
        }
    }

    public void onTouchModeChanged(boolean isInTouchMode) {
        if (isInTouchMode) {
            hideSelector();
            if (getWidth() > 0 && getHeight() > 0 && getChildCount() > 0) {
                layoutChildren();
            }
            updateSelectorState();
        } else if (this.mTouchMode == TOUCH_MODE_OVERSCROLL && this.mOverScroll != 0) {
            this.mOverScroll = TOUCH_MODE_ON;
            finishEdgeGlows();
            invalidate();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return handleKeyEvent(keyCode, TOUCH_MODE_TAP, event);
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return handleKeyEvent(keyCode, repeatCount, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return handleKeyEvent(keyCode, TOUCH_MODE_TAP, event);
    }

    public void sendAccessibilityEvent(int eventType) {
        if (eventType == AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int lastVisiblePosition = getLastVisiblePosition();
            if (this.mLastAccessibilityScrollEventFromIndex != firstVisiblePosition || this.mLastAccessibilityScrollEventToIndex != lastVisiblePosition) {
                this.mLastAccessibilityScrollEventFromIndex = firstVisiblePosition;
                this.mLastAccessibilityScrollEventToIndex = lastVisiblePosition;
            } else {
                return;
            }
        }
        super.sendAccessibilityEvent(eventType);
    }

    @TargetApi(14)
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(TwoWayView.class.getName());
    }

    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(TwoWayView.class.getName());
        AccessibilityNodeInfoCompat infoCompat = new AccessibilityNodeInfoCompat(info);
        if (isEnabled()) {
            if (getFirstVisiblePosition() > 0) {
                infoCompat.addAction(AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD);
            }
            if (getLastVisiblePosition() < getCount() + TOUCH_MODE_UNKNOWN) {
                infoCompat.addAction(AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD);
            }
        }
    }

    @TargetApi(16)
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (super.performAccessibilityAction(action, arguments)) {
            return true;
        }
        int viewportSize;
        switch (action) {
            case AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD /*4096*/:
                if (!isEnabled() || getLastVisiblePosition() >= getCount() + TOUCH_MODE_UNKNOWN) {
                    return false;
                }
                if (this.mIsVertical) {
                    viewportSize = (getHeight() - getPaddingTop()) - getPaddingBottom();
                } else {
                    viewportSize = (getWidth() - getPaddingLeft()) - getPaddingRight();
                }
                scrollListItemsBy(viewportSize);
                return true;
            case AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD /*8192*/:
                if (!isEnabled() || this.mFirstPosition <= 0) {
                    return false;
                }
                if (this.mIsVertical) {
                    viewportSize = (getHeight() - getPaddingTop()) - getPaddingBottom();
                } else {
                    viewportSize = (getWidth() - getPaddingLeft()) - getPaddingRight();
                }
                scrollListItemsBy(-viewportSize);
                return true;
            default:
                return false;
        }
    }

    private boolean isViewAncestorOf(View child, View parent) {
        if (child == parent) {
            return true;
        }
        ViewParent theParent = child.getParent();
        if ((theParent instanceof ViewGroup) && isViewAncestorOf((View) theParent, parent)) {
            return true;
        }
        return false;
    }

    private void forceValidFocusDirection(int direction) {
        if (this.mIsVertical && direction != 33 && direction != TransportMediator.KEYCODE_MEDIA_RECORD) {
            throw new IllegalArgumentException("Focus direction must be one of {View.FOCUS_UP, View.FOCUS_DOWN} for vertical orientation");
        } else if (!this.mIsVertical && direction != 17 && direction != 66) {
            throw new IllegalArgumentException("Focus direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT} for vertical orientation");
        }
    }

    private void forceValidInnerFocusDirection(int direction) {
        if (this.mIsVertical && direction != 17 && direction != 66) {
            throw new IllegalArgumentException("Direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT} for vertical orientation");
        } else if (!this.mIsVertical && direction != 33 && direction != TransportMediator.KEYCODE_MEDIA_RECORD) {
            throw new IllegalArgumentException("direction must be one of {View.FOCUS_UP, View.FOCUS_DOWN} for horizontal orientation");
        }
    }

    boolean pageScroll(int direction) {
        forceValidFocusDirection(direction);
        boolean forward = false;
        int nextPage = TOUCH_MODE_UNKNOWN;
        if (direction == 33 || direction == 17) {
            nextPage = Math.max(TOUCH_MODE_ON, (this.mSelectedPosition - getChildCount()) + TOUCH_MODE_UNKNOWN);
        } else if (direction == TransportMediator.KEYCODE_MEDIA_RECORD || direction == 66) {
            nextPage = Math.min(this.mItemCount + TOUCH_MODE_UNKNOWN, (this.mSelectedPosition + getChildCount()) + TOUCH_MODE_UNKNOWN);
            forward = true;
        }
        if (nextPage < 0) {
            return false;
        }
        int position = lookForSelectablePosition(nextPage, forward);
        if (position < 0) {
            return false;
        }
        this.mLayoutMode = TOUCH_MODE_FLINGING;
        this.mSpecificStart = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
        if (forward && position > this.mItemCount - getChildCount()) {
            this.mLayoutMode = TOUCH_MODE_DRAGGING;
        }
        if (!forward && position < getChildCount()) {
            this.mLayoutMode = TOUCH_MODE_TAP;
        }
        setSelectionInt(position);
        invokeOnItemScrollListener();
        if (!awakenScrollbarsInternal()) {
            invalidate();
        }
        return true;
    }

    boolean fullScroll(int direction) {
        forceValidFocusDirection(direction);
        boolean moved = false;
        int position;
        if (direction == 33 || direction == 17) {
            if (this.mSelectedPosition != 0) {
                position = lookForSelectablePosition(TOUCH_MODE_ON, true);
                if (position >= 0) {
                    this.mLayoutMode = TOUCH_MODE_TAP;
                    setSelectionInt(position);
                    invokeOnItemScrollListener();
                }
                moved = true;
            }
        } else if ((direction == TransportMediator.KEYCODE_MEDIA_RECORD || direction == 66) && this.mSelectedPosition < this.mItemCount + TOUCH_MODE_UNKNOWN) {
            position = lookForSelectablePosition(this.mItemCount + TOUCH_MODE_UNKNOWN, true);
            if (position >= 0) {
                this.mLayoutMode = TOUCH_MODE_DRAGGING;
                setSelectionInt(position);
                invokeOnItemScrollListener();
            }
            moved = true;
        }
        if (moved && !awakenScrollbarsInternal()) {
            awakenScrollbarsInternal();
            invalidate();
        }
        return moved;
    }

    private boolean handleFocusWithinItem(int direction) {
        forceValidInnerFocusDirection(direction);
        int numChildren = getChildCount();
        if (this.mItemsCanFocus && numChildren > 0 && this.mSelectedPosition != TOUCH_MODE_UNKNOWN) {
            View selectedView = getSelectedView();
            if (selectedView != null && selectedView.hasFocus() && (selectedView instanceof ViewGroup)) {
                View currentFocus = selectedView.findFocus();
                View nextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) selectedView, currentFocus, direction);
                if (nextFocus != null) {
                    currentFocus.getFocusedRect(this.mTempRect);
                    offsetDescendantRectToMyCoords(currentFocus, this.mTempRect);
                    offsetRectIntoDescendantCoords(nextFocus, this.mTempRect);
                    if (nextFocus.requestFocus(direction, this.mTempRect)) {
                        return true;
                    }
                }
                View globalNextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) getRootView(), currentFocus, direction);
                if (globalNextFocus != null) {
                    return isViewAncestorOf(globalNextFocus, this);
                }
            }
        }
        return false;
    }

    private boolean arrowScroll(int direction) {
        forceValidFocusDirection(direction);
        try {
            this.mInLayout = true;
            boolean handled = arrowScrollImpl(direction);
            if (handled) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            }
            this.mInLayout = false;
            return handled;
        } catch (Throwable th) {
            this.mInLayout = false;
        }
    }

    private void handleNewSelectionChange(View selectedView, int direction, int newSelectedPosition, boolean newFocusAssigned) {
        forceValidFocusDirection(direction);
        if (newSelectedPosition == TOUCH_MODE_UNKNOWN) {
            throw new IllegalArgumentException("newSelectedPosition needs to be valid");
        }
        int startViewIndex;
        int endViewIndex;
        View startView;
        View endView;
        int selectedIndex = this.mSelectedPosition - this.mFirstPosition;
        int nextSelectedIndex = newSelectedPosition - this.mFirstPosition;
        boolean topSelected = false;
        if (direction == 33 || direction == 17) {
            startViewIndex = nextSelectedIndex;
            endViewIndex = selectedIndex;
            startView = getChildAt(startViewIndex);
            endView = selectedView;
            topSelected = true;
        } else {
            startViewIndex = selectedIndex;
            endViewIndex = nextSelectedIndex;
            startView = selectedView;
            endView = getChildAt(endViewIndex);
        }
        int numChildren = getChildCount();
        if (startView != null) {
            boolean z = !newFocusAssigned && topSelected;
            startView.setSelected(z);
            measureAndAdjustDown(startView, startViewIndex, numChildren);
        }
        if (endView != null) {
            z = (newFocusAssigned || topSelected) ? false : true;
            endView.setSelected(z);
            measureAndAdjustDown(endView, endViewIndex, numChildren);
        }
    }

    private void measureAndAdjustDown(View child, int childIndex, int numChildren) {
        int oldHeight = child.getHeight();
        measureChild(child);
        if (child.getMeasuredHeight() != oldHeight) {
            relayoutMeasuredChild(child);
            int heightDelta = child.getMeasuredHeight() - oldHeight;
            for (int i = childIndex + TOUCH_MODE_TAP; i < numChildren; i += TOUCH_MODE_TAP) {
                getChildAt(i).offsetTopAndBottom(heightDelta);
            }
        }
    }

    private ArrowScrollFocusResult arrowScrollFocused(int direction) {
        View newFocus;
        forceValidFocusDirection(direction);
        View selectedView = getSelectedView();
        if (selectedView == null || !selectedView.hasFocus()) {
            int searchPoint;
            int x;
            if (direction == 130 || direction == 66) {
                int start = getStartEdge();
                int selectedStart = selectedView != null ? this.mIsVertical ? selectedView.getTop() : selectedView.getLeft() : start;
                searchPoint = Math.max(selectedStart, start);
            } else {
                int selectedEnd;
                int end = getEndEdge();
                if (selectedView != null) {
                    selectedEnd = getChildEndEdge(selectedView);
                } else {
                    selectedEnd = end;
                }
                searchPoint = Math.min(selectedEnd, end);
            }
            if (this.mIsVertical) {
                x = TOUCH_MODE_ON;
            } else {
                x = searchPoint;
            }
            int y = this.mIsVertical ? searchPoint : TOUCH_MODE_ON;
            this.mTempRect.set(x, y, x, y);
            newFocus = FocusFinder.getInstance().findNextFocusFromRect(this, this.mTempRect, direction);
        } else {
            newFocus = FocusFinder.getInstance().findNextFocus(this, selectedView.findFocus(), direction);
        }
        if (newFocus != null) {
            int positionOfNewFocus = positionOfNewFocus(newFocus);
            int i = this.mSelectedPosition;
            if (r0 != TOUCH_MODE_UNKNOWN) {
                i = this.mSelectedPosition;
                if (positionOfNewFocus != r0) {
                    int selectablePosition = lookForSelectablePositionOnScreen(direction);
                    boolean movingForward = direction == 130 || direction == 66;
                    boolean movingBackward = direction == 33 || direction == 17;
                    if (selectablePosition != TOUCH_MODE_UNKNOWN && ((movingForward && selectablePosition < positionOfNewFocus) || (movingBackward && selectablePosition > positionOfNewFocus))) {
                        return null;
                    }
                }
            }
            int focusScroll = amountToScrollToNewFocus(direction, newFocus, positionOfNewFocus);
            int maxScrollAmount = getMaxScrollAmount();
            if (focusScroll < maxScrollAmount) {
                newFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, focusScroll);
                return this.mArrowScrollFocusResult;
            } else if (distanceToView(newFocus) < maxScrollAmount) {
                newFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, maxScrollAmount);
                return this.mArrowScrollFocusResult;
            }
        }
        return null;
    }

    public int getMaxScrollAmount() {
        return (int) (MAX_SCROLL_FACTOR * ((float) getHeight()));
    }

    private int getArrowScrollPreviewLength() {
        return this.mItemMargin + Math.max(MIN_SCROLL_PREVIEW_PIXELS, this.mIsVertical ? getVerticalFadingEdgeLength() : getHorizontalFadingEdgeLength());
    }

    private int positionOfNewFocus(View newFocus) {
        int numChildren = getChildCount();
        for (int i = TOUCH_MODE_ON; i < numChildren; i += TOUCH_MODE_TAP) {
            if (isViewAncestorOf(newFocus, getChildAt(i))) {
                return this.mFirstPosition + i;
            }
        }
        throw new IllegalArgumentException("newFocus is not a child of any of the children of the list!");
    }

    private boolean arrowScrollImpl(int direction) {
        forceValidFocusDirection(direction);
        if (getChildCount() <= 0) {
            return false;
        }
        boolean needToRedraw;
        View focused;
        View selectedView = getSelectedView();
        int selectedPos = this.mSelectedPosition;
        int nextSelectedPosition = lookForSelectablePositionOnScreen(direction);
        int amountToScroll = amountToScroll(direction, nextSelectedPosition);
        ArrowScrollFocusResult focusResult = this.mItemsCanFocus ? arrowScrollFocused(direction) : null;
        if (focusResult != null) {
            nextSelectedPosition = focusResult.getSelectedPosition();
            amountToScroll = focusResult.getAmountToScroll();
        }
        if (focusResult != null) {
            needToRedraw = true;
        } else {
            needToRedraw = false;
        }
        if (nextSelectedPosition != TOUCH_MODE_UNKNOWN) {
            boolean z;
            if (focusResult != null) {
                z = true;
            } else {
                z = false;
            }
            handleNewSelectionChange(selectedView, direction, nextSelectedPosition, z);
            setSelectedPositionInt(nextSelectedPosition);
            setNextSelectedPositionInt(nextSelectedPosition);
            selectedView = getSelectedView();
            selectedPos = nextSelectedPosition;
            if (this.mItemsCanFocus && focusResult == null) {
                focused = getFocusedChild();
                if (focused != null) {
                    focused.clearFocus();
                }
            }
            needToRedraw = true;
            checkSelectionChanged();
        }
        if (amountToScroll > 0) {
            if (!(direction == 33 || direction == 17)) {
                amountToScroll = -amountToScroll;
            }
            scrollListItemsBy(amountToScroll);
            needToRedraw = true;
        }
        if (this.mItemsCanFocus && focusResult == null && selectedView != null && selectedView.hasFocus()) {
            focused = selectedView.findFocus();
            if (!isViewAncestorOf(focused, this) || distanceToView(focused) > 0) {
                focused.clearFocus();
            }
        }
        if (!(nextSelectedPosition != TOUCH_MODE_UNKNOWN || selectedView == null || isViewAncestorOf(selectedView, this))) {
            selectedView = null;
            hideSelector();
            this.mResurrectToPosition = TOUCH_MODE_UNKNOWN;
        }
        if (!needToRedraw) {
            return false;
        }
        if (selectedView != null) {
            positionSelector(selectedPos, selectedView);
            this.mSelectedStart = selectedView.getTop();
        }
        if (!awakenScrollbarsInternal()) {
            invalidate();
        }
        invokeOnItemScrollListener();
        return true;
    }

    private int amountToScroll(int direction, int nextSelectedPosition) {
        forceValidFocusDirection(direction);
        int numChildren = getChildCount();
        int indexToMakeVisible;
        int positionToMakeVisible;
        int amountToScroll;
        if (direction == 130 || direction == 66) {
            int end = getEndEdge();
            indexToMakeVisible = numChildren + TOUCH_MODE_UNKNOWN;
            if (nextSelectedPosition != TOUCH_MODE_UNKNOWN) {
                indexToMakeVisible = nextSelectedPosition - this.mFirstPosition;
            }
            positionToMakeVisible = this.mFirstPosition + indexToMakeVisible;
            View viewToMakeVisible = getChildAt(indexToMakeVisible);
            int goalEnd = end;
            if (positionToMakeVisible < this.mItemCount + TOUCH_MODE_UNKNOWN) {
                goalEnd -= getArrowScrollPreviewLength();
            }
            int viewToMakeVisibleStart = getChildStartEdge(viewToMakeVisible);
            int viewToMakeVisibleEnd = getChildEndEdge(viewToMakeVisible);
            if (viewToMakeVisibleEnd <= goalEnd) {
                return TOUCH_MODE_ON;
            }
            if (nextSelectedPosition != TOUCH_MODE_UNKNOWN && goalEnd - viewToMakeVisibleStart >= getMaxScrollAmount()) {
                return TOUCH_MODE_ON;
            }
            amountToScroll = viewToMakeVisibleEnd - goalEnd;
            int i = this.mFirstPosition + numChildren;
            int i2 = this.mItemCount;
            if (i == r0) {
                amountToScroll = Math.min(amountToScroll, getChildEndEdge(getChildAt(numChildren + TOUCH_MODE_UNKNOWN)) - end);
            }
            return Math.min(amountToScroll, getMaxScrollAmount());
        }
        int start = getStartEdge();
        indexToMakeVisible = TOUCH_MODE_ON;
        if (nextSelectedPosition != TOUCH_MODE_UNKNOWN) {
            indexToMakeVisible = nextSelectedPosition - this.mFirstPosition;
        }
        positionToMakeVisible = this.mFirstPosition + indexToMakeVisible;
        viewToMakeVisible = getChildAt(indexToMakeVisible);
        int goalStart = start;
        if (positionToMakeVisible > 0) {
            goalStart += getArrowScrollPreviewLength();
        }
        viewToMakeVisibleStart = getChildStartEdge(viewToMakeVisible);
        viewToMakeVisibleEnd = getChildEndEdge(viewToMakeVisible);
        if (viewToMakeVisibleStart >= goalStart) {
            return TOUCH_MODE_ON;
        }
        if (nextSelectedPosition != TOUCH_MODE_UNKNOWN && viewToMakeVisibleEnd - goalStart >= getMaxScrollAmount()) {
            return TOUCH_MODE_ON;
        }
        amountToScroll = goalStart - viewToMakeVisibleStart;
        if (this.mFirstPosition == 0) {
            amountToScroll = Math.min(amountToScroll, start - getChildStartEdge(getChildAt(TOUCH_MODE_ON)));
        }
        return Math.min(amountToScroll, getMaxScrollAmount());
    }

    private int amountToScrollToNewFocus(int direction, View newFocus, int positionOfNewFocus) {
        forceValidFocusDirection(direction);
        newFocus.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(newFocus, this.mTempRect);
        int amountToScroll;
        if (direction == 33 || direction == 17) {
            int start = getStartEdge();
            int newFocusStart = this.mIsVertical ? this.mTempRect.top : this.mTempRect.left;
            if (newFocusStart >= start) {
                return TOUCH_MODE_ON;
            }
            amountToScroll = start - newFocusStart;
            return positionOfNewFocus > 0 ? amountToScroll + getArrowScrollPreviewLength() : amountToScroll;
        } else {
            int end = getEndEdge();
            int newFocusEnd = this.mIsVertical ? this.mTempRect.bottom : this.mTempRect.right;
            if (newFocusEnd <= end) {
                return TOUCH_MODE_ON;
            }
            amountToScroll = newFocusEnd - end;
            if (positionOfNewFocus < this.mItemCount + TOUCH_MODE_UNKNOWN) {
                return amountToScroll + getArrowScrollPreviewLength();
            }
            return amountToScroll;
        }
    }

    private int distanceToView(View descendant) {
        descendant.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(descendant, this.mTempRect);
        int start = getStartEdge();
        int end = getEndEdge();
        int viewStart = this.mIsVertical ? this.mTempRect.top : this.mTempRect.left;
        int viewEnd = this.mIsVertical ? this.mTempRect.bottom : this.mTempRect.right;
        if (viewEnd < start) {
            return start - viewEnd;
        }
        if (viewStart > end) {
            return viewStart - end;
        }
        return TOUCH_MODE_ON;
    }

    private boolean handleKeyScroll(KeyEvent event, int count, int direction) {
        if (KeyEventCompat.hasNoModifiers(event)) {
            boolean handled = resurrectSelectionIfNeeded();
            if (handled) {
                return handled;
            }
            int count2 = count;
            while (true) {
                count = count2 + TOUCH_MODE_UNKNOWN;
                if (count2 <= 0 || !arrowScroll(direction)) {
                    return handled;
                }
                handled = true;
                count2 = count;
            }
        } else if (KeyEventCompat.hasModifiers(event, TOUCH_MODE_DONE_WAITING)) {
            return resurrectSelectionIfNeeded() || fullScroll(direction);
        } else {
            return false;
        }
    }

    private boolean handleKeyEvent(int keyCode, int count, KeyEvent event) {
        int i = 66;
        int i2 = 33;
        if (this.mAdapter == null || !this.mIsAttached) {
            return false;
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        boolean handled = false;
        int action = event.getAction();
        if (action != TOUCH_MODE_TAP) {
            switch (keyCode) {
                case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                    if (!this.mIsVertical) {
                        if (KeyEventCompat.hasNoModifiers(event)) {
                            handled = handleFocusWithinItem(33);
                            break;
                        }
                    }
                    handled = handleKeyScroll(event, count, 33);
                    break;
                    break;
                case CHECK_POSITION_SEARCH_DISTANCE /*20*/:
                    if (!this.mIsVertical) {
                        if (KeyEventCompat.hasNoModifiers(event)) {
                            handled = handleFocusWithinItem(TransportMediator.KEYCODE_MEDIA_RECORD);
                            break;
                        }
                    }
                    handled = handleKeyScroll(event, count, TransportMediator.KEYCODE_MEDIA_RECORD);
                    break;
                    break;
                case C0065R.styleable.TwoWayView_android_visibility /*21*/:
                    if (this.mIsVertical) {
                        if (KeyEventCompat.hasNoModifiers(event)) {
                            handled = handleFocusWithinItem(17);
                            break;
                        }
                    }
                    handled = handleKeyScroll(event, count, 17);
                    break;
                    break;
                case C0065R.styleable.TwoWayView_android_fitsSystemWindows /*22*/:
                    if (this.mIsVertical) {
                        if (KeyEventCompat.hasNoModifiers(event)) {
                            handled = handleFocusWithinItem(66);
                            break;
                        }
                    }
                    handled = handleKeyScroll(event, count, 66);
                    break;
                    break;
                case C0065R.styleable.TwoWayView_android_scrollbars /*23*/:
                case C0065R.styleable.TwoWayView_android_layoutDirection /*66*/:
                    if (KeyEventCompat.hasNoModifiers(event)) {
                        handled = resurrectSelectionIfNeeded();
                        if (!handled && event.getRepeatCount() == 0 && getChildCount() > 0) {
                            keyPressed();
                            handled = true;
                            break;
                        }
                    }
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusForward /*62*/:
                    if (KeyEventCompat.hasNoModifiers(event)) {
                        if (!resurrectSelectionIfNeeded()) {
                            if (this.mIsVertical) {
                                i = TransportMediator.KEYCODE_MEDIA_RECORD;
                            }
                            if (!pageScroll(i)) {
                                handled = false;
                            }
                        }
                        handled = true;
                    } else if (KeyEventCompat.hasModifiers(event, TOUCH_MODE_TAP)) {
                        if (!resurrectSelectionIfNeeded()) {
                            if (!fullScroll(this.mIsVertical ? 33 : 17)) {
                                handled = false;
                            }
                        }
                        handled = true;
                    }
                    handled = true;
                    break;
                case 92:
                    if (KeyEventCompat.hasNoModifiers(event)) {
                        if (!resurrectSelectionIfNeeded()) {
                            if (!this.mIsVertical) {
                                i2 = 17;
                            }
                            if (!pageScroll(i2)) {
                                handled = false;
                                break;
                            }
                        }
                        handled = true;
                    } else if (KeyEventCompat.hasModifiers(event, TOUCH_MODE_DONE_WAITING)) {
                        if (!resurrectSelectionIfNeeded()) {
                            if (!this.mIsVertical) {
                                i2 = 17;
                            }
                            if (!fullScroll(i2)) {
                                handled = false;
                                break;
                            }
                        }
                        handled = true;
                    }
                    break;
                case 93:
                    if (KeyEventCompat.hasNoModifiers(event)) {
                        if (!resurrectSelectionIfNeeded()) {
                            if (this.mIsVertical) {
                                i = TransportMediator.KEYCODE_MEDIA_RECORD;
                            }
                            if (!pageScroll(i)) {
                                handled = false;
                                break;
                            }
                        }
                        handled = true;
                    } else if (KeyEventCompat.hasModifiers(event, TOUCH_MODE_DONE_WAITING)) {
                        if (!resurrectSelectionIfNeeded()) {
                            if (this.mIsVertical) {
                                i = TransportMediator.KEYCODE_MEDIA_RECORD;
                            }
                            if (!fullScroll(i)) {
                                handled = false;
                                break;
                            }
                        }
                        handled = true;
                    }
                    break;
                case 122:
                    if (KeyEventCompat.hasNoModifiers(event)) {
                        if (!resurrectSelectionIfNeeded()) {
                            if (!this.mIsVertical) {
                                i2 = 17;
                            }
                            if (!fullScroll(i2)) {
                                handled = false;
                                break;
                            }
                        }
                        handled = true;
                    }
                    break;
                case 123:
                    if (KeyEventCompat.hasNoModifiers(event)) {
                        if (!resurrectSelectionIfNeeded()) {
                            if (this.mIsVertical) {
                                i = TransportMediator.KEYCODE_MEDIA_RECORD;
                            }
                            if (!fullScroll(i)) {
                                handled = false;
                                break;
                            }
                        }
                        handled = true;
                    }
                    break;
            }
        }
        if (handled) {
            return true;
        }
        switch (action) {
            case TOUCH_MODE_ON /*0*/:
                return super.onKeyDown(keyCode, event);
            case TOUCH_MODE_TAP /*1*/:
                if (!isEnabled()) {
                    return true;
                }
                if (!isClickable() || !isPressed() || this.mSelectedPosition < 0 || this.mAdapter == null || this.mSelectedPosition >= this.mAdapter.getCount()) {
                    return false;
                }
                View child = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (child != null) {
                    performItemClick(child, this.mSelectedPosition, this.mSelectedRowId);
                    child.setPressed(false);
                }
                setPressed(false);
                return true;
            case TOUCH_MODE_DONE_WAITING /*2*/:
                return super.onKeyMultiple(keyCode, count, event);
            default:
                return false;
        }
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

    private void invokeOnItemScrollListener() {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        onScrollChanged(TOUCH_MODE_ON, TOUCH_MODE_ON, TOUCH_MODE_ON, TOUCH_MODE_ON);
    }

    private void reportScrollStateChange(int newState) {
        if (newState != this.mLastScrollState && this.mOnScrollListener != null) {
            this.mLastScrollState = newState;
            this.mOnScrollListener.onScrollStateChanged(this, newState);
        }
    }

    private boolean maybeStartScrolling(int delta) {
        boolean isOverScroll;
        if (this.mOverScroll != 0) {
            isOverScroll = true;
        } else {
            isOverScroll = false;
        }
        if (Math.abs(delta) <= this.mTouchSlop && !isOverScroll) {
            return false;
        }
        if (isOverScroll) {
            this.mTouchMode = TOUCH_MODE_OVERSCROLL;
        } else {
            this.mTouchMode = TOUCH_MODE_DRAGGING;
        }
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        cancelCheckForLongPress();
        setPressed(false);
        View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (motionView != null) {
            motionView.setPressed(false);
        }
        reportScrollStateChange(TOUCH_MODE_TAP);
        return true;
    }

    private void maybeScroll(int delta) {
        if (this.mTouchMode == TOUCH_MODE_DRAGGING) {
            handleDragChange(delta);
        } else if (this.mTouchMode == TOUCH_MODE_OVERSCROLL) {
            handleOverScrollChange(delta);
        }
    }

    private void handleDragChange(int delta) {
        int motionIndex;
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        if (this.mMotionPosition >= 0) {
            motionIndex = this.mMotionPosition - this.mFirstPosition;
        } else {
            motionIndex = getChildCount() / TOUCH_MODE_DONE_WAITING;
        }
        int motionViewPrevStart = TOUCH_MODE_ON;
        View motionView = getChildAt(motionIndex);
        if (motionView != null) {
            motionViewPrevStart = this.mIsVertical ? motionView.getTop() : motionView.getLeft();
        }
        boolean atEdge = scrollListItemsBy(delta);
        motionView = getChildAt(motionIndex);
        if (motionView != null) {
            int motionViewRealStart = this.mIsVertical ? motionView.getTop() : motionView.getLeft();
            if (atEdge) {
                updateOverScrollState(delta, (-delta) - (motionViewRealStart - motionViewPrevStart));
            }
        }
    }

    private void updateOverScrollState(int delta, int overscroll) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (this.mIsVertical) {
            i = TOUCH_MODE_ON;
        } else {
            i = overscroll;
        }
        if (this.mIsVertical) {
            i2 = overscroll;
        } else {
            i2 = TOUCH_MODE_ON;
        }
        if (this.mIsVertical) {
            i3 = TOUCH_MODE_ON;
        } else {
            i3 = this.mOverScroll;
        }
        if (this.mIsVertical) {
            i4 = this.mOverScroll;
        } else {
            i4 = TOUCH_MODE_ON;
        }
        if (this.mIsVertical) {
            i5 = TOUCH_MODE_ON;
        } else {
            i5 = this.mOverscrollDistance;
        }
        if (this.mIsVertical) {
            i6 = this.mOverscrollDistance;
        } else {
            i6 = TOUCH_MODE_ON;
        }
        overScrollByInternal(i, i2, i3, i4, TOUCH_MODE_ON, TOUCH_MODE_ON, i5, i6, true);
        if (Math.abs(this.mOverscrollDistance) == Math.abs(this.mOverScroll) && this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        int overscrollMode = ViewCompat.getOverScrollMode(this);
        if (overscrollMode == 0 || (overscrollMode == TOUCH_MODE_TAP && !contentFits())) {
            this.mTouchMode = TOUCH_MODE_OVERSCROLL;
            float pull = ((float) overscroll) / ((float) (this.mIsVertical ? getHeight() : getWidth()));
            if (delta > 0) {
                this.mStartEdge.onPull(pull);
                if (!this.mEndEdge.isFinished()) {
                    this.mEndEdge.onRelease();
                }
            } else if (delta < 0) {
                this.mEndEdge.onPull(pull);
                if (!this.mStartEdge.isFinished()) {
                    this.mStartEdge.onRelease();
                }
            }
            if (delta != 0) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
    }

    private void handleOverScrollChange(int delta) {
        int oldOverScroll = this.mOverScroll;
        int newOverScroll = oldOverScroll - delta;
        int overScrollDistance = -delta;
        if ((newOverScroll >= 0 || oldOverScroll < 0) && (newOverScroll <= 0 || oldOverScroll > 0)) {
            delta = TOUCH_MODE_ON;
        } else {
            overScrollDistance = -oldOverScroll;
            delta += overScrollDistance;
        }
        if (overScrollDistance != 0) {
            updateOverScrollState(delta, overScrollDistance);
        }
        if (delta != 0) {
            if (this.mOverScroll != 0) {
                this.mOverScroll = TOUCH_MODE_ON;
                ViewCompat.postInvalidateOnAnimation(this);
            }
            scrollListItemsBy(delta);
            this.mTouchMode = TOUCH_MODE_DRAGGING;
            this.mMotionPosition = findClosestMotionRowOrColumn((int) this.mLastTouchPos);
            this.mTouchRemainderPos = 0.0f;
        }
    }

    private static int getDistance(Rect source, Rect dest, int direction) {
        int sX;
        int sY;
        int dX;
        int dY;
        switch (direction) {
            case TOUCH_MODE_TAP /*1*/:
            case TOUCH_MODE_DONE_WAITING /*2*/:
                sX = source.right + (source.width() / TOUCH_MODE_DONE_WAITING);
                sY = source.top + (source.height() / TOUCH_MODE_DONE_WAITING);
                dX = dest.left + (dest.width() / TOUCH_MODE_DONE_WAITING);
                dY = dest.top + (dest.height() / TOUCH_MODE_DONE_WAITING);
                break;
            case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                sX = source.left;
                sY = source.top + (source.height() / TOUCH_MODE_DONE_WAITING);
                dX = dest.right;
                dY = dest.top + (dest.height() / TOUCH_MODE_DONE_WAITING);
                break;
            case CharsToNameCanonicalizer.HASH_MULT /*33*/:
                sX = source.left + (source.width() / TOUCH_MODE_DONE_WAITING);
                sY = source.top;
                dX = dest.left + (dest.width() / TOUCH_MODE_DONE_WAITING);
                dY = dest.bottom;
                break;
            case C0065R.styleable.TwoWayView_android_layoutDirection /*66*/:
                sX = source.right;
                sY = source.top + (source.height() / TOUCH_MODE_DONE_WAITING);
                dX = dest.left;
                dY = dest.top + (dest.height() / TOUCH_MODE_DONE_WAITING);
                break;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                sX = source.left + (source.width() / TOUCH_MODE_DONE_WAITING);
                sY = source.bottom;
                dX = dest.left + (dest.width() / TOUCH_MODE_DONE_WAITING);
                dY = dest.top;
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
        int deltaX = dX - sX;
        int deltaY = dY - sY;
        return (deltaY * deltaY) + (deltaX * deltaX);
    }

    private int findMotionRowOrColumn(int motionPos) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return TOUCH_MODE_UNKNOWN;
        }
        for (int i = TOUCH_MODE_ON; i < childCount; i += TOUCH_MODE_TAP) {
            if (motionPos <= getChildEndEdge(getChildAt(i))) {
                return this.mFirstPosition + i;
            }
        }
        return TOUCH_MODE_UNKNOWN;
    }

    private int findClosestMotionRowOrColumn(int motionPos) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return TOUCH_MODE_UNKNOWN;
        }
        int motionRow = findMotionRowOrColumn(motionPos);
        return motionRow == TOUCH_MODE_UNKNOWN ? (this.mFirstPosition + childCount) + TOUCH_MODE_UNKNOWN : motionRow;
    }

    @TargetApi(9)
    private int getScaledOverscrollDistance(ViewConfiguration vc) {
        if (VERSION.SDK_INT < 9) {
            return TOUCH_MODE_ON;
        }
        return vc.getScaledOverscrollDistance();
    }

    private int getStartEdge() {
        return this.mIsVertical ? getPaddingTop() : getPaddingLeft();
    }

    private int getEndEdge() {
        if (this.mIsVertical) {
            return getHeight() - getPaddingBottom();
        }
        return getWidth() - getPaddingRight();
    }

    private int getChildStartEdge(View child) {
        return this.mIsVertical ? child.getTop() : child.getLeft();
    }

    private int getChildEndEdge(View child) {
        return this.mIsVertical ? child.getBottom() : child.getRight();
    }

    private boolean contentFits() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        if (childCount != this.mItemCount) {
            return false;
        }
        View first = getChildAt(TOUCH_MODE_ON);
        View last = getChildAt(childCount + TOUCH_MODE_UNKNOWN);
        if (getChildStartEdge(first) < getStartEdge() || getChildEndEdge(last) > getEndEdge()) {
            return false;
        }
        return true;
    }

    private void triggerCheckForTap() {
        if (this.mPendingCheckForTap == null) {
            this.mPendingCheckForTap = new CheckForTap();
        }
        postDelayed(this.mPendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
    }

    private void cancelCheckForTap() {
        if (this.mPendingCheckForTap != null) {
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    private void triggerCheckForLongPress() {
        if (this.mPendingCheckForLongPress == null) {
            this.mPendingCheckForLongPress = new CheckForLongPress();
        }
        this.mPendingCheckForLongPress.rememberWindowAttachCount();
        postDelayed(this.mPendingCheckForLongPress, (long) ViewConfiguration.getLongPressTimeout());
    }

    private void cancelCheckForLongPress() {
        if (this.mPendingCheckForLongPress != null) {
            removeCallbacks(this.mPendingCheckForLongPress);
        }
    }

    private boolean scrollListItemsBy(int incrementalDelta) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        int paddingStart;
        int size;
        int firstStart = getChildStartEdge(getChildAt(TOUCH_MODE_ON));
        int lastEnd = getChildEndEdge(getChildAt(childCount + TOUCH_MODE_UNKNOWN));
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        if (this.mIsVertical) {
            paddingStart = paddingTop;
        } else {
            paddingStart = paddingLeft;
        }
        int spaceBefore = paddingStart - firstStart;
        int end = getEndEdge();
        int spaceAfter = lastEnd - end;
        if (this.mIsVertical) {
            size = (getHeight() - paddingBottom) - paddingTop;
        } else {
            size = (getWidth() - paddingRight) - paddingLeft;
        }
        if (incrementalDelta < 0) {
            incrementalDelta = Math.max(-(size + TOUCH_MODE_UNKNOWN), incrementalDelta);
        } else {
            incrementalDelta = Math.min(size + TOUCH_MODE_UNKNOWN, incrementalDelta);
        }
        int firstPosition = this.mFirstPosition;
        boolean cannotScrollDown = firstPosition == 0 && firstStart >= paddingStart && incrementalDelta >= 0;
        boolean cannotScrollUp = firstPosition + childCount == this.mItemCount && lastEnd <= end && incrementalDelta <= 0;
        if (cannotScrollDown || cannotScrollUp) {
            return incrementalDelta != 0;
        } else {
            int i;
            int childIndex;
            boolean inTouchMode = isInTouchMode();
            if (inTouchMode) {
                hideSelector();
            }
            int start = TOUCH_MODE_ON;
            int count = TOUCH_MODE_ON;
            boolean down = incrementalDelta < 0;
            int i2;
            View child;
            if (!down) {
                int childrenEnd = end - incrementalDelta;
                for (i2 = childCount + TOUCH_MODE_UNKNOWN; i2 >= 0; i2 += TOUCH_MODE_UNKNOWN) {
                    child = getChildAt(i2);
                    if (getChildStartEdge(child) <= childrenEnd) {
                        break;
                    }
                    start = i2;
                    count += TOUCH_MODE_TAP;
                    this.mRecycler.addScrapView(child, firstPosition + i2);
                }
            } else {
                int childrenStart = (-incrementalDelta) + paddingStart;
                for (i2 = TOUCH_MODE_ON; i2 < childCount; i2 += TOUCH_MODE_TAP) {
                    child = getChildAt(i2);
                    if (getChildEndEdge(child) >= childrenStart) {
                        break;
                    }
                    count += TOUCH_MODE_TAP;
                    this.mRecycler.addScrapView(child, firstPosition + i2);
                }
            }
            this.mBlockLayoutRequests = true;
            if (count > 0) {
                detachViewsFromParent(start, count);
            }
            if (!awakenScrollbarsInternal()) {
                invalidate();
            }
            offsetChildren(incrementalDelta);
            if (down) {
                this.mFirstPosition += count;
            }
            int absIncrementalDelta = Math.abs(incrementalDelta);
            if (spaceBefore < absIncrementalDelta || spaceAfter < absIncrementalDelta) {
                fillGap(down);
            }
            if (!inTouchMode) {
                i = this.mSelectedPosition;
                if (r0 != TOUCH_MODE_UNKNOWN) {
                    childIndex = this.mSelectedPosition - this.mFirstPosition;
                    if (childIndex >= 0 && childIndex < getChildCount()) {
                        positionSelector(this.mSelectedPosition, getChildAt(childIndex));
                    }
                    this.mBlockLayoutRequests = false;
                    invokeOnItemScrollListener();
                    return false;
                }
            }
            i = this.mSelectorPosition;
            if (r0 != TOUCH_MODE_UNKNOWN) {
                childIndex = this.mSelectorPosition - this.mFirstPosition;
                if (childIndex >= 0 && childIndex < getChildCount()) {
                    positionSelector(TOUCH_MODE_UNKNOWN, getChildAt(childIndex));
                }
            } else {
                this.mSelectorRect.setEmpty();
            }
            this.mBlockLayoutRequests = false;
            invokeOnItemScrollListener();
            return false;
        }
    }

    @TargetApi(14)
    private final float getCurrVelocity() {
        if (VERSION.SDK_INT >= 14) {
            return this.mScroller.getCurrVelocity();
        }
        return 0.0f;
    }

    @TargetApi(5)
    private boolean awakenScrollbarsInternal() {
        return VERSION.SDK_INT >= TOUCH_MODE_OVERSCROLL && super.awakenScrollBars();
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int pos;
            if (this.mIsVertical) {
                pos = this.mScroller.getCurrY();
            } else {
                pos = this.mScroller.getCurrX();
            }
            int diff = (int) (((float) pos) - this.mLastTouchPos);
            this.mLastTouchPos = (float) pos;
            boolean stopped = scrollListItemsBy(diff);
            if (stopped || this.mScroller.isFinished()) {
                if (stopped) {
                    if (ViewCompat.getOverScrollMode(this) != TOUCH_MODE_DONE_WAITING) {
                        if ((diff > 0 ? this.mStartEdge : this.mEndEdge).onAbsorb(Math.abs((int) getCurrVelocity()))) {
                            ViewCompat.postInvalidateOnAnimation(this);
                        }
                    }
                    this.mScroller.abortAnimation();
                }
                this.mTouchMode = TOUCH_MODE_UNKNOWN;
                reportScrollStateChange(TOUCH_MODE_ON);
                return;
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void finishEdgeGlows() {
        if (this.mStartEdge != null) {
            this.mStartEdge.finish();
        }
        if (this.mEndEdge != null) {
            this.mEndEdge.finish();
        }
    }

    private boolean drawStartEdge(Canvas canvas) {
        if (this.mStartEdge.isFinished()) {
            return false;
        }
        if (this.mIsVertical) {
            return this.mStartEdge.draw(canvas);
        }
        int restoreCount = canvas.save();
        canvas.translate(0.0f, (float) getHeight());
        canvas.rotate(BitmapDescriptorFactory.HUE_VIOLET);
        boolean needsInvalidate = this.mStartEdge.draw(canvas);
        canvas.restoreToCount(restoreCount);
        return needsInvalidate;
    }

    private boolean drawEndEdge(Canvas canvas) {
        if (this.mEndEdge.isFinished()) {
            return false;
        }
        int restoreCount = canvas.save();
        int width = getWidth();
        int height = getHeight();
        if (this.mIsVertical) {
            canvas.translate((float) (-width), (float) height);
            canvas.rotate(BitmapDescriptorFactory.HUE_CYAN, (float) width, 0.0f);
        } else {
            canvas.translate((float) width, 0.0f);
            canvas.rotate(90.0f);
        }
        boolean needsInvalidate = this.mEndEdge.draw(canvas);
        canvas.restoreToCount(restoreCount);
        return needsInvalidate;
    }

    private void drawSelector(Canvas canvas) {
        if (!this.mSelectorRect.isEmpty()) {
            Drawable selector = this.mSelector;
            selector.setBounds(this.mSelectorRect);
            selector.draw(canvas);
        }
    }

    private void useDefaultSelector() {
        setSelector(getResources().getDrawable(17301602));
    }

    private boolean shouldShowSelector() {
        return (hasFocus() && !isInTouchMode()) || touchModeDrawsInPressedState();
    }

    private void positionSelector(int position, View selected) {
        if (position != TOUCH_MODE_UNKNOWN) {
            this.mSelectorPosition = position;
        }
        this.mSelectorRect.set(selected.getLeft(), selected.getTop(), selected.getRight(), selected.getBottom());
        boolean isChildViewEnabled = this.mIsChildViewEnabled;
        if (selected.isEnabled() != isChildViewEnabled) {
            this.mIsChildViewEnabled = !isChildViewEnabled;
            if (getSelectedItemPosition() != TOUCH_MODE_UNKNOWN) {
                refreshDrawableState();
            }
        }
    }

    private void hideSelector() {
        if (this.mSelectedPosition != TOUCH_MODE_UNKNOWN) {
            if (this.mLayoutMode != TOUCH_MODE_FLINGING) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
            if (this.mNextSelectedPosition >= 0 && this.mNextSelectedPosition != this.mSelectedPosition) {
                this.mResurrectToPosition = this.mNextSelectedPosition;
            }
            setSelectedPositionInt(TOUCH_MODE_UNKNOWN);
            setNextSelectedPositionInt(TOUCH_MODE_UNKNOWN);
            this.mSelectedStart = TOUCH_MODE_ON;
        }
    }

    private void setSelectedPositionInt(int position) {
        this.mSelectedPosition = position;
        this.mSelectedRowId = getItemIdAtPosition(position);
    }

    private void setSelectionInt(int position) {
        setNextSelectedPositionInt(position);
        boolean awakeScrollbars = false;
        int selectedPosition = this.mSelectedPosition;
        if (selectedPosition >= 0) {
            if (position == selectedPosition + TOUCH_MODE_UNKNOWN) {
                awakeScrollbars = true;
            } else if (position == selectedPosition + TOUCH_MODE_TAP) {
                awakeScrollbars = true;
            }
        }
        layoutChildren();
        if (awakeScrollbars) {
            awakenScrollbarsInternal();
        }
    }

    private void setNextSelectedPositionInt(int position) {
        this.mNextSelectedPosition = position;
        this.mNextSelectedRowId = getItemIdAtPosition(position);
        if (this.mNeedSync && this.mSyncMode == 0 && position >= 0) {
            this.mSyncPosition = position;
            this.mSyncRowId = this.mNextSelectedRowId;
        }
    }

    private boolean touchModeDrawsInPressedState() {
        switch (this.mTouchMode) {
            case TOUCH_MODE_TAP /*1*/:
            case TOUCH_MODE_DONE_WAITING /*2*/:
                return true;
            default:
                return false;
        }
    }

    private void keyPressed() {
        if (isEnabled() && isClickable()) {
            Drawable selector = this.mSelector;
            Rect selectorRect = this.mSelectorRect;
            if (selector == null) {
                return;
            }
            if ((isFocused() || touchModeDrawsInPressedState()) && !selectorRect.isEmpty()) {
                View child = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (child != null) {
                    if (!child.hasFocusable()) {
                        child.setPressed(true);
                    } else {
                        return;
                    }
                }
                setPressed(true);
                boolean longClickable = isLongClickable();
                Drawable d = selector.getCurrent();
                if (d != null && (d instanceof TransitionDrawable)) {
                    if (longClickable) {
                        ((TransitionDrawable) d).startTransition(ViewConfiguration.getLongPressTimeout());
                    } else {
                        ((TransitionDrawable) d).resetTransition();
                    }
                }
                if (longClickable && !this.mDataChanged) {
                    if (this.mPendingCheckForKeyLongPress == null) {
                        this.mPendingCheckForKeyLongPress = new CheckForKeyLongPress();
                    }
                    this.mPendingCheckForKeyLongPress.rememberWindowAttachCount();
                    postDelayed(this.mPendingCheckForKeyLongPress, (long) ViewConfiguration.getLongPressTimeout());
                }
            }
        }
    }

    private void updateSelectorState() {
        if (this.mSelector == null) {
            return;
        }
        if (shouldShowSelector()) {
            this.mSelector.setState(getDrawableState());
        } else {
            this.mSelector.setState(STATE_NOTHING);
        }
    }

    private void checkSelectionChanged() {
        if (this.mSelectedPosition != this.mOldSelectedPosition || this.mSelectedRowId != this.mOldSelectedRowId) {
            selectionChanged();
            this.mOldSelectedPosition = this.mSelectedPosition;
            this.mOldSelectedRowId = this.mSelectedRowId;
        }
    }

    private void selectionChanged() {
        if (getOnItemSelectedListener() != null) {
            if (this.mInLayout || this.mBlockLayoutRequests) {
                if (this.mSelectionNotifier == null) {
                    this.mSelectionNotifier = new SelectionNotifier();
                }
                post(this.mSelectionNotifier);
                return;
            }
            fireOnSelected();
            performAccessibilityActionsOnSelected();
        }
    }

    private void fireOnSelected() {
        OnItemSelectedListener listener = getOnItemSelectedListener();
        if (listener != null) {
            int selection = getSelectedItemPosition();
            if (selection >= 0) {
                listener.onItemSelected(this, getSelectedView(), selection, this.mAdapter.getItemId(selection));
                return;
            }
            listener.onNothingSelected(this);
        }
    }

    private void performAccessibilityActionsOnSelected() {
        if (getSelectedItemPosition() >= 0) {
            sendAccessibilityEvent(TOUCH_MODE_FLINGING);
        }
    }

    private int lookForSelectablePosition(int position) {
        return lookForSelectablePosition(position, true);
    }

    private int lookForSelectablePosition(int position, boolean lookDown) {
        ListAdapter adapter = this.mAdapter;
        if (adapter == null || isInTouchMode()) {
            return TOUCH_MODE_UNKNOWN;
        }
        int itemCount = this.mItemCount;
        if (!this.mAreAllItemsSelectable) {
            if (lookDown) {
                position = Math.max(TOUCH_MODE_ON, position);
                while (position < itemCount && !adapter.isEnabled(position)) {
                    position += TOUCH_MODE_TAP;
                }
            } else {
                position = Math.min(position, itemCount + TOUCH_MODE_UNKNOWN);
                while (position >= 0 && !adapter.isEnabled(position)) {
                    position += TOUCH_MODE_UNKNOWN;
                }
            }
            if (position < 0 || position >= itemCount) {
                return TOUCH_MODE_UNKNOWN;
            }
            return position;
        } else if (position < 0 || position >= itemCount) {
            return TOUCH_MODE_UNKNOWN;
        } else {
            return position;
        }
    }

    private int lookForSelectablePositionOnScreen(int direction) {
        forceValidFocusDirection(direction);
        int firstPosition = this.mFirstPosition;
        ListAdapter adapter = getAdapter();
        int startPos;
        int pos;
        if (direction == TransportMediator.KEYCODE_MEDIA_RECORD || direction == 66) {
            if (this.mSelectedPosition != TOUCH_MODE_UNKNOWN) {
                startPos = this.mSelectedPosition + TOUCH_MODE_TAP;
            } else {
                startPos = firstPosition;
            }
            if (startPos >= adapter.getCount()) {
                return TOUCH_MODE_UNKNOWN;
            }
            if (startPos < firstPosition) {
                startPos = firstPosition;
            }
            int lastVisiblePos = getLastVisiblePosition();
            pos = startPos;
            while (pos <= lastVisiblePos) {
                if (adapter.isEnabled(pos) && getChildAt(pos - firstPosition).getVisibility() == 0) {
                    return pos;
                }
                pos += TOUCH_MODE_TAP;
            }
        } else {
            int last = (getChildCount() + firstPosition) + TOUCH_MODE_UNKNOWN;
            startPos = this.mSelectedPosition != TOUCH_MODE_UNKNOWN ? this.mSelectedPosition + TOUCH_MODE_UNKNOWN : (getChildCount() + firstPosition) + TOUCH_MODE_UNKNOWN;
            if (startPos < 0 || startPos >= adapter.getCount()) {
                return TOUCH_MODE_UNKNOWN;
            }
            if (startPos > last) {
                startPos = last;
            }
            pos = startPos;
            while (pos >= firstPosition) {
                if (adapter.isEnabled(pos) && getChildAt(pos - firstPosition).getVisibility() == 0) {
                    return pos;
                }
                pos += TOUCH_MODE_UNKNOWN;
            }
        }
        return TOUCH_MODE_UNKNOWN;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateSelectorState();
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.mIsChildViewEnabled) {
            return super.onCreateDrawableState(extraSpace);
        }
        int enabledState = ENABLED_STATE_SET[TOUCH_MODE_ON];
        int[] state = super.onCreateDrawableState(extraSpace + TOUCH_MODE_TAP);
        int enabledPos = TOUCH_MODE_UNKNOWN;
        for (int i = state.length + TOUCH_MODE_UNKNOWN; i >= 0; i += TOUCH_MODE_UNKNOWN) {
            if (state[i] == enabledState) {
                enabledPos = i;
                break;
            }
        }
        if (enabledPos < 0) {
            return state;
        }
        System.arraycopy(state, enabledPos + TOUCH_MODE_TAP, state, enabledPos, (state.length - enabledPos) + TOUCH_MODE_UNKNOWN);
        return state;
    }

    protected boolean canAnimate() {
        return super.canAnimate() && this.mItemCount > 0;
    }

    protected void dispatchDraw(Canvas canvas) {
        boolean drawSelectorOnTop = this.mDrawSelectorOnTop;
        if (!drawSelectorOnTop) {
            drawSelector(canvas);
        }
        super.dispatchDraw(canvas);
        if (drawSelectorOnTop) {
            drawSelector(canvas);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        boolean needsInvalidate = false;
        if (this.mStartEdge != null) {
            needsInvalidate = false | drawStartEdge(canvas);
        }
        if (this.mEndEdge != null) {
            needsInvalidate |= drawEndEdge(canvas);
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void requestLayout() {
        if (!this.mInLayout && !this.mBlockLayoutRequests) {
            super.requestLayout();
        }
    }

    public View getSelectedView() {
        if (this.mItemCount <= 0 || this.mSelectedPosition < 0) {
            return null;
        }
        return getChildAt(this.mSelectedPosition - this.mFirstPosition);
    }

    public void setSelection(int position) {
        setSelectionFromOffset(position, TOUCH_MODE_ON);
    }

    public void setSelectionFromOffset(int position, int offset) {
        if (this.mAdapter != null) {
            if (isInTouchMode()) {
                this.mResurrectToPosition = position;
            } else {
                position = lookForSelectablePosition(position);
                if (position >= 0) {
                    setNextSelectedPositionInt(position);
                }
            }
            if (position >= 0) {
                this.mLayoutMode = TOUCH_MODE_FLINGING;
                if (this.mIsVertical) {
                    this.mSpecificStart = getPaddingTop() + offset;
                } else {
                    this.mSpecificStart = getPaddingLeft() + offset;
                }
                if (this.mNeedSync) {
                    this.mSyncPosition = position;
                    this.mSyncRowId = this.mAdapter.getItemId(position);
                }
                requestLayout();
            }
        }
    }

    public void scrollBy(int offset) {
        scrollListItemsBy(offset);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = super.dispatchKeyEvent(event);
        if (handled || getFocusedChild() == null || event.getAction() != 0) {
            return handled;
        }
        return onKeyDown(event.getKeyCode(), event);
    }

    protected void dispatchSetPressed(boolean pressed) {
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mSelector == null) {
            useDefaultSelector();
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = TOUCH_MODE_ON;
        int childHeight = TOUCH_MODE_ON;
        this.mItemCount = this.mAdapter == null ? TOUCH_MODE_ON : this.mAdapter.getCount();
        if (this.mItemCount > 0 && (widthMode == 0 || heightMode == 0)) {
            int secondaryMeasureSpec;
            View child = obtainView(TOUCH_MODE_ON, this.mIsScrap);
            if (this.mIsVertical) {
                secondaryMeasureSpec = widthMeasureSpec;
            } else {
                secondaryMeasureSpec = heightMeasureSpec;
            }
            measureScrapChild(child, TOUCH_MODE_ON, secondaryMeasureSpec);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            if (recycleOnMeasure()) {
                this.mRecycler.addScrapView(child, TOUCH_MODE_UNKNOWN);
            }
        }
        if (widthMode == 0) {
            widthSize = (getPaddingLeft() + getPaddingRight()) + childWidth;
            if (this.mIsVertical) {
                widthSize += getVerticalScrollbarWidth();
            }
        }
        if (heightMode == 0) {
            heightSize = (getPaddingTop() + getPaddingBottom()) + childHeight;
            if (!this.mIsVertical) {
                heightSize += getHorizontalScrollbarHeight();
            }
        }
        if (this.mIsVertical && heightMode == Integer.MIN_VALUE) {
            heightSize = measureHeightOfChildren(widthMeasureSpec, TOUCH_MODE_ON, TOUCH_MODE_UNKNOWN, heightSize, TOUCH_MODE_UNKNOWN);
        }
        if (!this.mIsVertical && widthMode == Integer.MIN_VALUE) {
            widthSize = measureWidthOfChildren(heightMeasureSpec, TOUCH_MODE_ON, TOUCH_MODE_UNKNOWN, widthSize, TOUCH_MODE_UNKNOWN);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mInLayout = true;
        if (changed) {
            int childCount = getChildCount();
            for (int i = TOUCH_MODE_ON; i < childCount; i += TOUCH_MODE_TAP) {
                getChildAt(i).forceLayout();
            }
            this.mRecycler.markChildrenDirty();
        }
        layoutChildren();
        this.mInLayout = false;
        int width = ((r - l) - getPaddingLeft()) - getPaddingRight();
        int height = ((b - t) - getPaddingTop()) - getPaddingBottom();
        if (this.mStartEdge != null && this.mEndEdge != null) {
            if (this.mIsVertical) {
                this.mStartEdge.setSize(width, height);
                this.mEndEdge.setSize(width, height);
                return;
            }
            this.mStartEdge.setSize(height, width);
            this.mEndEdge.setSize(height, width);
        }
    }

    private void layoutChildren() {
        if (getWidth() != 0 && getHeight() != 0) {
            boolean blockLayoutRequests = this.mBlockLayoutRequests;
            if (!blockLayoutRequests) {
                this.mBlockLayoutRequests = true;
                try {
                    invalidate();
                    if (this.mAdapter == null) {
                        resetState();
                        if (!blockLayoutRequests) {
                            this.mBlockLayoutRequests = false;
                            this.mDataChanged = false;
                            return;
                        }
                        return;
                    }
                    boolean dataChanged;
                    View focusLayoutRestoreDirectChild;
                    int firstPosition;
                    RecycleBin recycleBin;
                    int i;
                    View focusedChild;
                    View selected;
                    int i2;
                    int i3;
                    int offset;
                    int start = getStartEdge();
                    int end = getEndEdge();
                    int childCount = getChildCount();
                    int delta = TOUCH_MODE_ON;
                    View focusLayoutRestoreView = null;
                    View oldSelected = null;
                    View newSelected = null;
                    View oldFirstChild = null;
                    int index;
                    switch (this.mLayoutMode) {
                        case TOUCH_MODE_DONE_WAITING /*2*/:
                            index = this.mNextSelectedPosition - this.mFirstPosition;
                            if (index >= 0 && index < childCount) {
                                newSelected = getChildAt(index);
                            }
                        case TOUCH_MODE_TAP /*1*/:
                        case TOUCH_MODE_DRAGGING /*3*/:
                        case TOUCH_MODE_FLINGING /*4*/:
                        case TOUCH_MODE_OVERSCROLL /*5*/:
                            dataChanged = this.mDataChanged;
                            if (dataChanged) {
                                handleDataChanged();
                            }
                            if (this.mItemCount != 0) {
                                resetState();
                                if (!blockLayoutRequests) {
                                    this.mBlockLayoutRequests = false;
                                    this.mDataChanged = false;
                                }
                            }
                            if (this.mItemCount == this.mAdapter.getCount()) {
                                throw new IllegalStateException("The content of the adapter has changed but TwoWayView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. [in TwoWayView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
                            }
                            setSelectedPositionInt(this.mNextSelectedPosition);
                            focusLayoutRestoreDirectChild = null;
                            firstPosition = this.mFirstPosition;
                            recycleBin = this.mRecycler;
                            if (dataChanged) {
                                recycleBin.fillActiveViews(childCount, firstPosition);
                            } else {
                                for (i = TOUCH_MODE_ON; i < childCount; i += TOUCH_MODE_TAP) {
                                    recycleBin.addScrapView(getChildAt(i), firstPosition + i);
                                }
                            }
                            focusedChild = getFocusedChild();
                            if (focusedChild != null) {
                                if (!dataChanged) {
                                    focusLayoutRestoreDirectChild = focusedChild;
                                    focusLayoutRestoreView = findFocus();
                                    if (focusLayoutRestoreView != null) {
                                        focusLayoutRestoreView.onStartTemporaryDetach();
                                    }
                                }
                                requestFocus();
                            }
                            detachAllViewsFromParent();
                            switch (this.mLayoutMode) {
                                case TOUCH_MODE_TAP /*1*/:
                                    this.mFirstPosition = TOUCH_MODE_ON;
                                    selected = fillFromOffset(start);
                                    adjustViewsStartOrEnd();
                                    break;
                                case TOUCH_MODE_DONE_WAITING /*2*/:
                                    if (newSelected == null) {
                                        selected = fillFromMiddle(start, end);
                                        break;
                                    } else {
                                        selected = fillFromSelection(this.mIsVertical ? newSelected.getTop() : newSelected.getLeft(), start, end);
                                        break;
                                    }
                                case TOUCH_MODE_DRAGGING /*3*/:
                                    selected = fillBefore(this.mItemCount + TOUCH_MODE_UNKNOWN, end);
                                    adjustViewsStartOrEnd();
                                    break;
                                case TOUCH_MODE_FLINGING /*4*/:
                                    selected = fillSpecific(reconcileSelectedPosition(), this.mSpecificStart);
                                    break;
                                case TOUCH_MODE_OVERSCROLL /*5*/:
                                    selected = fillSpecific(this.mSyncPosition, this.mSpecificStart);
                                    break;
                                case LAYOUT_MOVE_SELECTION /*6*/:
                                    selected = moveSelection(oldSelected, newSelected, delta, start, end);
                                    break;
                                default:
                                    if (childCount != 0) {
                                        if (this.mSelectedPosition >= 0) {
                                            i2 = this.mSelectedPosition;
                                            i3 = this.mItemCount;
                                            if (i2 < r0) {
                                                offset = start;
                                                if (oldSelected != null) {
                                                    offset = this.mIsVertical ? oldSelected.getTop() : oldSelected.getLeft();
                                                }
                                                selected = fillSpecific(this.mSelectedPosition, offset);
                                                break;
                                            }
                                        }
                                        i2 = this.mFirstPosition;
                                        i3 = this.mItemCount;
                                        if (i2 >= r0) {
                                            selected = fillSpecific(TOUCH_MODE_ON, start);
                                            break;
                                        }
                                        offset = start;
                                        if (oldFirstChild != null) {
                                            offset = this.mIsVertical ? oldFirstChild.getTop() : oldFirstChild.getLeft();
                                        }
                                        selected = fillSpecific(this.mFirstPosition, offset);
                                        break;
                                    }
                                    setSelectedPositionInt(lookForSelectablePosition(TOUCH_MODE_ON));
                                    selected = fillFromOffset(start);
                                    break;
                            }
                            recycleBin.scrapActiveViews();
                            if (selected == null) {
                                if (this.mItemsCanFocus || !hasFocus() || selected.hasFocus()) {
                                    positionSelector(TOUCH_MODE_UNKNOWN, selected);
                                } else {
                                    boolean focusWasTaken = (selected == focusLayoutRestoreDirectChild && focusLayoutRestoreView != null && focusLayoutRestoreView.requestFocus()) || selected.requestFocus();
                                    if (focusWasTaken) {
                                        selected.setSelected(false);
                                        this.mSelectorRect.setEmpty();
                                    } else {
                                        View focused = getFocusedChild();
                                        if (focused != null) {
                                            focused.clearFocus();
                                        }
                                        positionSelector(TOUCH_MODE_UNKNOWN, selected);
                                    }
                                }
                                if (this.mIsVertical) {
                                    i2 = selected.getLeft();
                                } else {
                                    i2 = selected.getTop();
                                }
                                this.mSelectedStart = i2;
                            } else {
                                if (this.mTouchMode > 0 || this.mTouchMode >= TOUCH_MODE_DRAGGING) {
                                    this.mSelectedStart = TOUCH_MODE_ON;
                                    this.mSelectorRect.setEmpty();
                                } else {
                                    View child = getChildAt(this.mMotionPosition - this.mFirstPosition);
                                    if (child != null) {
                                        positionSelector(this.mMotionPosition, child);
                                    }
                                }
                                if (hasFocus() && focusLayoutRestoreView != null) {
                                    focusLayoutRestoreView.requestFocus();
                                }
                            }
                            if (!(focusLayoutRestoreView == null || focusLayoutRestoreView.getWindowToken() == null)) {
                                focusLayoutRestoreView.onFinishTemporaryDetach();
                            }
                            this.mLayoutMode = TOUCH_MODE_ON;
                            this.mDataChanged = false;
                            this.mNeedSync = false;
                            setNextSelectedPositionInt(this.mSelectedPosition);
                            if (this.mItemCount > 0) {
                                checkSelectionChanged();
                            }
                            invokeOnItemScrollListener();
                            if (!blockLayoutRequests) {
                                this.mBlockLayoutRequests = false;
                                this.mDataChanged = false;
                                return;
                            }
                            return;
                        default:
                            index = this.mSelectedPosition - this.mFirstPosition;
                            if (index >= 0 && index < childCount) {
                                oldSelected = getChildAt(index);
                            }
                            oldFirstChild = getChildAt(TOUCH_MODE_ON);
                            if (this.mNextSelectedPosition >= 0) {
                                delta = this.mNextSelectedPosition - this.mSelectedPosition;
                            }
                            newSelected = getChildAt(index + delta);
                    }
                    dataChanged = this.mDataChanged;
                    if (dataChanged) {
                        handleDataChanged();
                    }
                    if (this.mItemCount != 0) {
                        if (this.mItemCount == this.mAdapter.getCount()) {
                            setSelectedPositionInt(this.mNextSelectedPosition);
                            focusLayoutRestoreDirectChild = null;
                            firstPosition = this.mFirstPosition;
                            recycleBin = this.mRecycler;
                            if (dataChanged) {
                                recycleBin.fillActiveViews(childCount, firstPosition);
                            } else {
                                for (i = TOUCH_MODE_ON; i < childCount; i += TOUCH_MODE_TAP) {
                                    recycleBin.addScrapView(getChildAt(i), firstPosition + i);
                                }
                            }
                            focusedChild = getFocusedChild();
                            if (focusedChild != null) {
                                if (dataChanged) {
                                    focusLayoutRestoreDirectChild = focusedChild;
                                    focusLayoutRestoreView = findFocus();
                                    if (focusLayoutRestoreView != null) {
                                        focusLayoutRestoreView.onStartTemporaryDetach();
                                    }
                                }
                                requestFocus();
                            }
                            detachAllViewsFromParent();
                            switch (this.mLayoutMode) {
                                case TOUCH_MODE_TAP /*1*/:
                                    this.mFirstPosition = TOUCH_MODE_ON;
                                    selected = fillFromOffset(start);
                                    adjustViewsStartOrEnd();
                                    break;
                                case TOUCH_MODE_DONE_WAITING /*2*/:
                                    if (newSelected == null) {
                                        if (this.mIsVertical) {
                                        }
                                        selected = fillFromSelection(this.mIsVertical ? newSelected.getTop() : newSelected.getLeft(), start, end);
                                        break;
                                    } else {
                                        selected = fillFromMiddle(start, end);
                                        break;
                                    }
                                case TOUCH_MODE_DRAGGING /*3*/:
                                    selected = fillBefore(this.mItemCount + TOUCH_MODE_UNKNOWN, end);
                                    adjustViewsStartOrEnd();
                                    break;
                                case TOUCH_MODE_FLINGING /*4*/:
                                    selected = fillSpecific(reconcileSelectedPosition(), this.mSpecificStart);
                                    break;
                                case TOUCH_MODE_OVERSCROLL /*5*/:
                                    selected = fillSpecific(this.mSyncPosition, this.mSpecificStart);
                                    break;
                                case LAYOUT_MOVE_SELECTION /*6*/:
                                    selected = moveSelection(oldSelected, newSelected, delta, start, end);
                                    break;
                                default:
                                    if (childCount != 0) {
                                        setSelectedPositionInt(lookForSelectablePosition(TOUCH_MODE_ON));
                                        selected = fillFromOffset(start);
                                        break;
                                    }
                                    if (this.mSelectedPosition >= 0) {
                                        i2 = this.mSelectedPosition;
                                        i3 = this.mItemCount;
                                        if (i2 < r0) {
                                            offset = start;
                                            if (oldSelected != null) {
                                                if (this.mIsVertical) {
                                                }
                                            }
                                            selected = fillSpecific(this.mSelectedPosition, offset);
                                            break;
                                        }
                                    }
                                    i2 = this.mFirstPosition;
                                    i3 = this.mItemCount;
                                    if (i2 >= r0) {
                                        offset = start;
                                        if (oldFirstChild != null) {
                                            if (this.mIsVertical) {
                                            }
                                        }
                                        selected = fillSpecific(this.mFirstPosition, offset);
                                        break;
                                    }
                                    selected = fillSpecific(TOUCH_MODE_ON, start);
                                    break;
                            }
                            recycleBin.scrapActiveViews();
                            if (selected == null) {
                                if (this.mTouchMode > 0) {
                                }
                                this.mSelectedStart = TOUCH_MODE_ON;
                                this.mSelectorRect.setEmpty();
                                focusLayoutRestoreView.requestFocus();
                            } else {
                                if (this.mItemsCanFocus) {
                                }
                                positionSelector(TOUCH_MODE_UNKNOWN, selected);
                                if (this.mIsVertical) {
                                    i2 = selected.getLeft();
                                } else {
                                    i2 = selected.getTop();
                                }
                                this.mSelectedStart = i2;
                            }
                            focusLayoutRestoreView.onFinishTemporaryDetach();
                            this.mLayoutMode = TOUCH_MODE_ON;
                            this.mDataChanged = false;
                            this.mNeedSync = false;
                            setNextSelectedPositionInt(this.mSelectedPosition);
                            if (this.mItemCount > 0) {
                                checkSelectionChanged();
                            }
                            invokeOnItemScrollListener();
                            if (!blockLayoutRequests) {
                                this.mBlockLayoutRequests = false;
                                this.mDataChanged = false;
                                return;
                            }
                            return;
                        }
                        throw new IllegalStateException("The content of the adapter has changed but TwoWayView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. [in TwoWayView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
                    }
                    resetState();
                    if (!blockLayoutRequests) {
                        this.mBlockLayoutRequests = false;
                        this.mDataChanged = false;
                    }
                } catch (Throwable th) {
                    if (!blockLayoutRequests) {
                        this.mBlockLayoutRequests = false;
                        this.mDataChanged = false;
                    }
                }
            }
        }
    }

    protected boolean recycleOnMeasure() {
        return true;
    }

    private void offsetChildren(int offset) {
        int childCount = getChildCount();
        for (int i = TOUCH_MODE_ON; i < childCount; i += TOUCH_MODE_TAP) {
            View child = getChildAt(i);
            if (this.mIsVertical) {
                child.offsetTopAndBottom(offset);
            } else {
                child.offsetLeftAndRight(offset);
            }
        }
    }

    private View moveSelection(View oldSelected, View newSelected, int delta, int start, int end) {
        View selected;
        int selectedPosition = this.mSelectedPosition;
        int oldSelectedStart = getChildStartEdge(oldSelected);
        int oldSelectedEnd = getChildEndEdge(oldSelected);
        int selectedStart;
        int selectedEnd;
        int offset;
        if (delta > 0) {
            oldSelected = makeAndAddView(selectedPosition + TOUCH_MODE_UNKNOWN, oldSelectedStart, true, false);
            int itemMargin = this.mItemMargin;
            selected = makeAndAddView(selectedPosition, oldSelectedEnd + itemMargin, true, true);
            selectedStart = getChildStartEdge(selected);
            selectedEnd = getChildEndEdge(selected);
            if (selectedEnd > end) {
                offset = Math.min(Math.min(selectedStart - start, selectedEnd - end), (end - start) / TOUCH_MODE_DONE_WAITING);
                if (this.mIsVertical) {
                    oldSelected.offsetTopAndBottom(-offset);
                    selected.offsetTopAndBottom(-offset);
                } else {
                    oldSelected.offsetLeftAndRight(-offset);
                    selected.offsetLeftAndRight(-offset);
                }
            }
            fillBefore(this.mSelectedPosition - 2, selectedStart - itemMargin);
            adjustViewsStartOrEnd();
            fillAfter(this.mSelectedPosition + TOUCH_MODE_TAP, selectedEnd + itemMargin);
        } else if (delta < 0) {
            if (newSelected != null) {
                selected = makeAndAddView(selectedPosition, this.mIsVertical ? newSelected.getTop() : newSelected.getLeft(), true, true);
            } else {
                selected = makeAndAddView(selectedPosition, oldSelectedStart, false, true);
            }
            selectedStart = getChildStartEdge(selected);
            selectedEnd = getChildEndEdge(selected);
            if (selectedStart < start) {
                offset = Math.min(Math.min(start - selectedStart, end - selectedEnd), (end - start) / TOUCH_MODE_DONE_WAITING);
                if (this.mIsVertical) {
                    selected.offsetTopAndBottom(offset);
                } else {
                    selected.offsetLeftAndRight(offset);
                }
            }
            fillBeforeAndAfter(selected, selectedPosition);
        } else {
            selected = makeAndAddView(selectedPosition, oldSelectedStart, true, true);
            selectedStart = getChildStartEdge(selected);
            selectedEnd = getChildEndEdge(selected);
            if (oldSelectedStart < start && selectedEnd < start + CHECK_POSITION_SEARCH_DISTANCE) {
                if (this.mIsVertical) {
                    selected.offsetTopAndBottom(start - selectedStart);
                } else {
                    selected.offsetLeftAndRight(start - selectedStart);
                }
            }
            fillBeforeAndAfter(selected, selectedPosition);
        }
        return selected;
    }

    void confirmCheckedPositionsById() {
        this.mCheckStates.clear();
        int checkedIndex = TOUCH_MODE_ON;
        while (checkedIndex < this.mCheckedIdStates.size()) {
            long id = this.mCheckedIdStates.keyAt(checkedIndex);
            int lastPos = ((Integer) this.mCheckedIdStates.valueAt(checkedIndex)).intValue();
            if (id != this.mAdapter.getItemId(lastPos)) {
                int start = Math.max(TOUCH_MODE_ON, lastPos - 20);
                int end = Math.min(lastPos + CHECK_POSITION_SEARCH_DISTANCE, this.mItemCount);
                boolean found = false;
                for (int searchPos = start; searchPos < end; searchPos += TOUCH_MODE_TAP) {
                    if (id == this.mAdapter.getItemId(searchPos)) {
                        found = true;
                        this.mCheckStates.put(searchPos, true);
                        this.mCheckedIdStates.setValueAt(checkedIndex, Integer.valueOf(searchPos));
                        break;
                    }
                }
                if (!found) {
                    this.mCheckedIdStates.delete(id);
                    checkedIndex += TOUCH_MODE_UNKNOWN;
                    this.mCheckedItemCount += TOUCH_MODE_UNKNOWN;
                }
            } else {
                this.mCheckStates.put(lastPos, true);
            }
            checkedIndex += TOUCH_MODE_TAP;
        }
    }

    private void handleDataChanged() {
        if (!(this.mChoiceMode == ChoiceMode.NONE || this.mAdapter == null || !this.mAdapter.hasStableIds())) {
            confirmCheckedPositionsById();
        }
        this.mRecycler.clearTransientStateViews();
        int itemCount = this.mItemCount;
        if (itemCount > 0) {
            int newPos;
            if (this.mNeedSync) {
                this.mNeedSync = false;
                this.mPendingSync = null;
                switch (this.mSyncMode) {
                    case TOUCH_MODE_ON /*0*/:
                        if (isInTouchMode()) {
                            this.mLayoutMode = TOUCH_MODE_OVERSCROLL;
                            this.mSyncPosition = Math.min(Math.max(TOUCH_MODE_ON, this.mSyncPosition), itemCount + TOUCH_MODE_UNKNOWN);
                            return;
                        }
                        newPos = findSyncPosition();
                        if (newPos >= 0 && lookForSelectablePosition(newPos, true) == newPos) {
                            this.mSyncPosition = newPos;
                            if (this.mSyncHeight == ((long) getHeight())) {
                                this.mLayoutMode = TOUCH_MODE_OVERSCROLL;
                            } else {
                                this.mLayoutMode = TOUCH_MODE_DONE_WAITING;
                            }
                            setNextSelectedPositionInt(newPos);
                            return;
                        }
                    case TOUCH_MODE_TAP /*1*/:
                        this.mLayoutMode = TOUCH_MODE_OVERSCROLL;
                        this.mSyncPosition = Math.min(Math.max(TOUCH_MODE_ON, this.mSyncPosition), itemCount + TOUCH_MODE_UNKNOWN);
                        return;
                }
            }
            if (!isInTouchMode()) {
                newPos = getSelectedItemPosition();
                if (newPos >= itemCount) {
                    newPos = itemCount + TOUCH_MODE_UNKNOWN;
                }
                if (newPos < 0) {
                    newPos = TOUCH_MODE_ON;
                }
                int selectablePos = lookForSelectablePosition(newPos, true);
                if (selectablePos >= 0) {
                    setNextSelectedPositionInt(selectablePos);
                    return;
                }
                selectablePos = lookForSelectablePosition(newPos, false);
                if (selectablePos >= 0) {
                    setNextSelectedPositionInt(selectablePos);
                    return;
                }
            } else if (this.mResurrectToPosition >= 0) {
                return;
            }
        }
        this.mLayoutMode = TOUCH_MODE_TAP;
        this.mSelectedPosition = TOUCH_MODE_UNKNOWN;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mNextSelectedPosition = TOUCH_MODE_UNKNOWN;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mSelectorPosition = TOUCH_MODE_UNKNOWN;
        checkSelectionChanged();
    }

    private int reconcileSelectedPosition() {
        int position = this.mSelectedPosition;
        if (position < 0) {
            position = this.mResurrectToPosition;
        }
        return Math.min(Math.max(TOUCH_MODE_ON, position), this.mItemCount + TOUCH_MODE_UNKNOWN);
    }

    boolean resurrectSelection() {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return false;
        }
        int selectedPosition;
        int selectedStart = TOUCH_MODE_ON;
        int start = getStartEdge();
        int end = getEndEdge();
        int firstPosition = this.mFirstPosition;
        int toPosition = this.mResurrectToPosition;
        boolean down = true;
        if (toPosition >= firstPosition && toPosition < firstPosition + childCount) {
            selectedPosition = toPosition;
            View selected = getChildAt(selectedPosition - this.mFirstPosition);
            if (this.mIsVertical) {
                selectedStart = selected.getTop();
            } else {
                selectedStart = selected.getLeft();
            }
        } else if (toPosition < firstPosition) {
            selectedPosition = firstPosition;
            for (i = TOUCH_MODE_ON; i < childCount; i += TOUCH_MODE_TAP) {
                child = getChildAt(i);
                childStart = this.mIsVertical ? child.getTop() : child.getLeft();
                if (i == 0) {
                    selectedStart = childStart;
                }
                if (childStart >= start) {
                    selectedPosition = firstPosition + i;
                    selectedStart = childStart;
                    break;
                }
            }
        } else {
            selectedPosition = (firstPosition + childCount) + TOUCH_MODE_UNKNOWN;
            down = false;
            for (i = childCount + TOUCH_MODE_UNKNOWN; i >= 0; i += TOUCH_MODE_UNKNOWN) {
                child = getChildAt(i);
                childStart = getChildStartEdge(child);
                int childEnd = getChildEndEdge(child);
                if (i == childCount + TOUCH_MODE_UNKNOWN) {
                    selectedStart = childStart;
                }
                if (childEnd <= end) {
                    selectedPosition = firstPosition + i;
                    selectedStart = childStart;
                    break;
                }
            }
        }
        this.mResurrectToPosition = TOUCH_MODE_UNKNOWN;
        this.mTouchMode = TOUCH_MODE_UNKNOWN;
        reportScrollStateChange(TOUCH_MODE_ON);
        this.mSpecificStart = selectedStart;
        selectedPosition = lookForSelectablePosition(selectedPosition, down);
        if (selectedPosition < firstPosition || selectedPosition > getLastVisiblePosition()) {
            selectedPosition = TOUCH_MODE_UNKNOWN;
        } else {
            this.mLayoutMode = TOUCH_MODE_FLINGING;
            updateSelectorState();
            setSelectionInt(selectedPosition);
            invokeOnItemScrollListener();
        }
        if (selectedPosition >= 0) {
            return true;
        }
        return false;
    }

    boolean resurrectSelectionIfNeeded() {
        if (this.mSelectedPosition >= 0 || !resurrectSelection()) {
            return false;
        }
        updateSelectorState();
        return true;
    }

    private int getChildWidthMeasureSpec(LayoutParams lp) {
        if (!this.mIsVertical && lp.width == -2) {
            return MeasureSpec.makeMeasureSpec(TOUCH_MODE_ON, TOUCH_MODE_ON);
        }
        if (this.mIsVertical) {
            return MeasureSpec.makeMeasureSpec((getWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824);
        }
        return MeasureSpec.makeMeasureSpec(lp.width, 1073741824);
    }

    private int getChildHeightMeasureSpec(LayoutParams lp) {
        if (this.mIsVertical && lp.height == -2) {
            return MeasureSpec.makeMeasureSpec(TOUCH_MODE_ON, TOUCH_MODE_ON);
        }
        if (this.mIsVertical) {
            return MeasureSpec.makeMeasureSpec(lp.height, 1073741824);
        }
        return MeasureSpec.makeMeasureSpec((getHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824);
    }

    private void measureChild(View child) {
        measureChild(child, (LayoutParams) child.getLayoutParams());
    }

    private void measureChild(View child, LayoutParams lp) {
        child.measure(getChildWidthMeasureSpec(lp), getChildHeightMeasureSpec(lp));
    }

    private void relayoutMeasuredChild(View child) {
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int childLeft = getPaddingLeft();
        int childRight = childLeft + w;
        int childTop = child.getTop();
        child.layout(childLeft, childTop, childRight, childTop + h);
    }

    private void measureScrapChild(View scrapChild, int position, int secondaryMeasureSpec) {
        int widthMeasureSpec;
        int heightMeasureSpec;
        LayoutParams lp = (LayoutParams) scrapChild.getLayoutParams();
        if (lp == null) {
            lp = generateDefaultLayoutParams();
            scrapChild.setLayoutParams(lp);
        }
        lp.viewType = this.mAdapter.getItemViewType(position);
        lp.forceAdd = true;
        if (this.mIsVertical) {
            widthMeasureSpec = secondaryMeasureSpec;
            heightMeasureSpec = getChildHeightMeasureSpec(lp);
        } else {
            widthMeasureSpec = getChildWidthMeasureSpec(lp);
            heightMeasureSpec = secondaryMeasureSpec;
        }
        scrapChild.measure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureHeightOfChildren(int widthMeasureSpec, int startPosition, int endPosition, int maxHeight, int disallowPartialChildPosition) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return paddingTop + paddingBottom;
        }
        int returnedHeight = paddingTop + paddingBottom;
        int itemMargin = this.mItemMargin;
        int prevHeightWithoutPartialChild = TOUCH_MODE_ON;
        if (endPosition == TOUCH_MODE_UNKNOWN) {
            endPosition = adapter.getCount() + TOUCH_MODE_UNKNOWN;
        }
        RecycleBin recycleBin = this.mRecycler;
        boolean shouldRecycle = recycleOnMeasure();
        boolean[] isScrap = this.mIsScrap;
        int i = startPosition;
        while (i <= endPosition) {
            View child = obtainView(i, isScrap);
            measureScrapChild(child, i, widthMeasureSpec);
            if (i > 0) {
                returnedHeight += itemMargin;
            }
            if (shouldRecycle) {
                recycleBin.addScrapView(child, TOUCH_MODE_UNKNOWN);
            }
            returnedHeight += child.getMeasuredHeight();
            if (returnedHeight < maxHeight) {
                if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
                    prevHeightWithoutPartialChild = returnedHeight;
                }
                i += TOUCH_MODE_TAP;
            } else if (disallowPartialChildPosition < 0 || i <= disallowPartialChildPosition || prevHeightWithoutPartialChild <= 0 || returnedHeight == maxHeight) {
                return maxHeight;
            } else {
                return prevHeightWithoutPartialChild;
            }
        }
        return returnedHeight;
    }

    private int measureWidthOfChildren(int heightMeasureSpec, int startPosition, int endPosition, int maxWidth, int disallowPartialChildPosition) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return paddingLeft + paddingRight;
        }
        int returnedWidth = paddingLeft + paddingRight;
        int itemMargin = this.mItemMargin;
        int prevWidthWithoutPartialChild = TOUCH_MODE_ON;
        if (endPosition == TOUCH_MODE_UNKNOWN) {
            endPosition = adapter.getCount() + TOUCH_MODE_UNKNOWN;
        }
        RecycleBin recycleBin = this.mRecycler;
        boolean shouldRecycle = recycleOnMeasure();
        boolean[] isScrap = this.mIsScrap;
        int i = startPosition;
        while (i <= endPosition) {
            View child = obtainView(i, isScrap);
            measureScrapChild(child, i, heightMeasureSpec);
            if (i > 0) {
                returnedWidth += itemMargin;
            }
            if (shouldRecycle) {
                recycleBin.addScrapView(child, TOUCH_MODE_UNKNOWN);
            }
            returnedWidth += child.getMeasuredWidth();
            if (returnedWidth < maxWidth) {
                if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
                    prevWidthWithoutPartialChild = returnedWidth;
                }
                i += TOUCH_MODE_TAP;
            } else if (disallowPartialChildPosition < 0 || i <= disallowPartialChildPosition || prevWidthWithoutPartialChild <= 0 || returnedWidth == maxWidth) {
                return maxWidth;
            } else {
                return prevWidthWithoutPartialChild;
            }
        }
        return returnedWidth;
    }

    private View makeAndAddView(int position, int offset, boolean flow, boolean selected) {
        int top;
        int left;
        if (this.mIsVertical) {
            top = offset;
            left = getPaddingLeft();
        } else {
            top = getPaddingTop();
            left = offset;
        }
        if (!this.mDataChanged) {
            View activeChild = this.mRecycler.getActiveView(position);
            if (activeChild != null) {
                setupChild(activeChild, position, top, left, flow, selected, true);
                return activeChild;
            }
        }
        View child = obtainView(position, this.mIsScrap);
        setupChild(child, position, top, left, flow, selected, this.mIsScrap[TOUCH_MODE_ON]);
        return child;
    }

    @TargetApi(11)
    private void setupChild(View child, int position, int top, int left, boolean flow, boolean selected, boolean recycled) {
        boolean isPressed;
        boolean updateChildPressed;
        boolean needToMeasure;
        LayoutParams lp;
        int w;
        int h;
        int childTop;
        int childLeft;
        boolean isSelected = selected && shouldShowSelector();
        boolean updateChildSelected = isSelected != child.isSelected();
        int touchMode = this.mTouchMode;
        if (touchMode > 0 && touchMode < TOUCH_MODE_DRAGGING) {
            int i = this.mMotionPosition;
            if (r0 == position) {
                isPressed = true;
                updateChildPressed = isPressed == child.isPressed();
                needToMeasure = recycled || updateChildSelected || child.isLayoutRequested();
                lp = (LayoutParams) child.getLayoutParams();
                if (lp == null) {
                    lp = generateDefaultLayoutParams();
                }
                lp.viewType = this.mAdapter.getItemViewType(position);
                if (recycled || lp.forceAdd) {
                    lp.forceAdd = false;
                    addViewInLayout(child, flow ? TOUCH_MODE_UNKNOWN : TOUCH_MODE_ON, lp, true);
                } else {
                    attachViewToParent(child, flow ? TOUCH_MODE_UNKNOWN : TOUCH_MODE_ON, lp);
                }
                if (updateChildSelected) {
                    child.setSelected(isSelected);
                }
                if (updateChildPressed) {
                    child.setPressed(isPressed);
                }
                if (!(this.mChoiceMode == ChoiceMode.NONE || this.mCheckStates == null)) {
                    if (child instanceof Checkable) {
                        ((Checkable) child).setChecked(this.mCheckStates.get(position));
                    } else if (VERSION.SDK_INT >= 11) {
                        child.setActivated(this.mCheckStates.get(position));
                    }
                }
                if (needToMeasure) {
                    cleanupLayoutState(child);
                } else {
                    measureChild(child, lp);
                }
                w = child.getMeasuredWidth();
                h = child.getMeasuredHeight();
                if (this.mIsVertical || flow) {
                    childTop = top;
                } else {
                    childTop = top - h;
                }
                if (!this.mIsVertical || flow) {
                    childLeft = left;
                } else {
                    childLeft = left - w;
                }
                if (needToMeasure) {
                    child.offsetLeftAndRight(childLeft - child.getLeft());
                    child.offsetTopAndBottom(childTop - child.getTop());
                    return;
                }
                child.layout(childLeft, childTop, childLeft + w, childTop + h);
            }
        }
        isPressed = false;
        if (isPressed == child.isPressed()) {
        }
        if (recycled) {
        }
        lp = (LayoutParams) child.getLayoutParams();
        if (lp == null) {
            lp = generateDefaultLayoutParams();
        }
        lp.viewType = this.mAdapter.getItemViewType(position);
        if (recycled) {
        }
        lp.forceAdd = false;
        if (flow) {
        }
        addViewInLayout(child, flow ? TOUCH_MODE_UNKNOWN : TOUCH_MODE_ON, lp, true);
        if (updateChildSelected) {
            child.setSelected(isSelected);
        }
        if (updateChildPressed) {
            child.setPressed(isPressed);
        }
        if (child instanceof Checkable) {
            ((Checkable) child).setChecked(this.mCheckStates.get(position));
        } else if (VERSION.SDK_INT >= 11) {
            child.setActivated(this.mCheckStates.get(position));
        }
        if (needToMeasure) {
            cleanupLayoutState(child);
        } else {
            measureChild(child, lp);
        }
        w = child.getMeasuredWidth();
        h = child.getMeasuredHeight();
        if (this.mIsVertical) {
        }
        childTop = top;
        if (this.mIsVertical) {
        }
        childLeft = left;
        if (needToMeasure) {
            child.offsetLeftAndRight(childLeft - child.getLeft());
            child.offsetTopAndBottom(childTop - child.getTop());
            return;
        }
        child.layout(childLeft, childTop, childLeft + w, childTop + h);
    }

    void fillGap(boolean down) {
        int childCount = getChildCount();
        int offset;
        if (down) {
            int paddingStart = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
            int lastEnd = getChildEndEdge(getChildAt(childCount + TOUCH_MODE_UNKNOWN));
            if (childCount > 0) {
                offset = lastEnd + this.mItemMargin;
            } else {
                offset = paddingStart;
            }
            fillAfter(this.mFirstPosition + childCount, offset);
            correctTooHigh(getChildCount());
            return;
        }
        int firstStart;
        int end = getEndEdge();
        if (this.mIsVertical) {
            firstStart = getChildAt(TOUCH_MODE_ON).getTop();
        } else {
            firstStart = getChildAt(TOUCH_MODE_ON).getLeft();
        }
        if (childCount > 0) {
            offset = firstStart - this.mItemMargin;
        } else {
            offset = end;
        }
        fillBefore(this.mFirstPosition + TOUCH_MODE_UNKNOWN, offset);
        correctTooLow(getChildCount());
    }

    private View fillBefore(int pos, int nextOffset) {
        View selectedView = null;
        int start = getStartEdge();
        while (nextOffset > start && pos >= 0) {
            boolean isSelected;
            if (pos == this.mSelectedPosition) {
                isSelected = true;
            } else {
                isSelected = false;
            }
            View child = makeAndAddView(pos, nextOffset, false, isSelected);
            if (this.mIsVertical) {
                nextOffset = child.getTop() - this.mItemMargin;
            } else {
                nextOffset = child.getLeft() - this.mItemMargin;
            }
            if (isSelected) {
                selectedView = child;
            }
            pos += TOUCH_MODE_UNKNOWN;
        }
        this.mFirstPosition = pos + TOUCH_MODE_TAP;
        return selectedView;
    }

    private View fillAfter(int pos, int nextOffset) {
        View selectedView = null;
        int end = getEndEdge();
        while (nextOffset < end && pos < this.mItemCount) {
            boolean selected = pos == this.mSelectedPosition;
            View child = makeAndAddView(pos, nextOffset, true, selected);
            nextOffset = getChildEndEdge(child) + this.mItemMargin;
            if (selected) {
                selectedView = child;
            }
            pos += TOUCH_MODE_TAP;
        }
        return selectedView;
    }

    private View fillSpecific(int position, int offset) {
        boolean tempIsSelected = position == this.mSelectedPosition;
        View temp = makeAndAddView(position, offset, true, tempIsSelected);
        this.mFirstPosition = position;
        View before = fillBefore(position + TOUCH_MODE_UNKNOWN, getChildStartEdge(temp) + this.mItemMargin);
        adjustViewsStartOrEnd();
        View after = fillAfter(position + TOUCH_MODE_TAP, getChildEndEdge(temp) + this.mItemMargin);
        int childCount = getChildCount();
        if (childCount > 0) {
            correctTooHigh(childCount);
        }
        if (tempIsSelected) {
            return temp;
        }
        if (before != null) {
            return before;
        }
        return after;
    }

    private View fillFromOffset(int nextOffset) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount + TOUCH_MODE_UNKNOWN);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = TOUCH_MODE_ON;
        }
        return fillAfter(this.mFirstPosition, nextOffset);
    }

    private View fillFromMiddle(int start, int end) {
        int size = end - start;
        int position = reconcileSelectedPosition();
        View selected = makeAndAddView(position, start, true, true);
        this.mFirstPosition = position;
        if (this.mIsVertical) {
            int selectedHeight = selected.getMeasuredHeight();
            if (selectedHeight <= size) {
                selected.offsetTopAndBottom((size - selectedHeight) / TOUCH_MODE_DONE_WAITING);
            }
        } else {
            int selectedWidth = selected.getMeasuredWidth();
            if (selectedWidth <= size) {
                selected.offsetLeftAndRight((size - selectedWidth) / TOUCH_MODE_DONE_WAITING);
            }
        }
        fillBeforeAndAfter(selected, position);
        correctTooHigh(getChildCount());
        return selected;
    }

    private void fillBeforeAndAfter(View selected, int position) {
        fillBefore(position + TOUCH_MODE_UNKNOWN, getChildStartEdge(selected) + this.mItemMargin);
        adjustViewsStartOrEnd();
        fillAfter(position + TOUCH_MODE_TAP, getChildEndEdge(selected) + this.mItemMargin);
    }

    private View fillFromSelection(int selectedTop, int start, int end) {
        int selectedPosition = this.mSelectedPosition;
        View selected = makeAndAddView(selectedPosition, selectedTop, true, true);
        int selectedStart = getChildStartEdge(selected);
        int selectedEnd = getChildEndEdge(selected);
        if (selectedEnd > end) {
            selected.offsetTopAndBottom(-Math.min(selectedStart - start, selectedEnd - end));
        } else if (selectedStart < start) {
            selected.offsetTopAndBottom(Math.min(start - selectedStart, end - selectedEnd));
        }
        fillBeforeAndAfter(selected, selectedPosition);
        correctTooHigh(getChildCount());
        return selected;
    }

    private void correctTooHigh(int childCount) {
        if ((this.mFirstPosition + childCount) + TOUCH_MODE_UNKNOWN == this.mItemCount + TOUCH_MODE_UNKNOWN && childCount != 0) {
            int lastEnd = getChildEndEdge(getChildAt(childCount + TOUCH_MODE_UNKNOWN));
            int start = getStartEdge();
            int endOffset = getEndEdge() - lastEnd;
            View firstChild = getChildAt(TOUCH_MODE_ON);
            int firstStart = getChildStartEdge(firstChild);
            if (endOffset <= 0) {
                return;
            }
            if (this.mFirstPosition > 0 || firstStart < start) {
                if (this.mFirstPosition == 0) {
                    endOffset = Math.min(endOffset, start - firstStart);
                }
                offsetChildren(endOffset);
                if (this.mFirstPosition > 0) {
                    fillBefore(this.mFirstPosition + TOUCH_MODE_UNKNOWN, getChildStartEdge(firstChild) - this.mItemMargin);
                    adjustViewsStartOrEnd();
                }
            }
        }
    }

    private void correctTooLow(int childCount) {
        if (this.mFirstPosition == 0 && childCount != 0) {
            View first = getChildAt(TOUCH_MODE_ON);
            int firstStart = this.mIsVertical ? first.getTop() : first.getLeft();
            int start = getStartEdge();
            int end = getEndEdge();
            int startOffset = firstStart - start;
            View last = getChildAt(childCount + TOUCH_MODE_UNKNOWN);
            int lastEnd = getChildEndEdge(last);
            int lastPosition = (this.mFirstPosition + childCount) + TOUCH_MODE_UNKNOWN;
            if (startOffset <= 0) {
                return;
            }
            if (lastPosition < this.mItemCount + TOUCH_MODE_UNKNOWN || lastEnd > end) {
                if (lastPosition == this.mItemCount + TOUCH_MODE_UNKNOWN) {
                    startOffset = Math.min(startOffset, lastEnd - end);
                }
                offsetChildren(-startOffset);
                if (lastPosition < this.mItemCount + TOUCH_MODE_UNKNOWN) {
                    fillAfter(lastPosition + TOUCH_MODE_TAP, this.mItemMargin + getChildEndEdge(last));
                    adjustViewsStartOrEnd();
                }
            } else if (lastPosition == this.mItemCount + TOUCH_MODE_UNKNOWN) {
                adjustViewsStartOrEnd();
            }
        }
    }

    private void adjustViewsStartOrEnd() {
        if (getChildCount() != 0) {
            int delta;
            View firstChild = getChildAt(TOUCH_MODE_ON);
            if (this.mIsVertical) {
                delta = (firstChild.getTop() - getPaddingTop()) - this.mItemMargin;
            } else {
                delta = (firstChild.getLeft() - getPaddingLeft()) - this.mItemMargin;
            }
            if (delta < 0) {
                delta = TOUCH_MODE_ON;
            }
            if (delta != 0) {
                offsetChildren(-delta);
            }
        }
    }

    @TargetApi(14)
    private SparseBooleanArray cloneCheckStates() {
        if (this.mCheckStates == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 14) {
            return this.mCheckStates.clone();
        }
        SparseBooleanArray checkedStates = new SparseBooleanArray();
        for (int i = TOUCH_MODE_ON; i < this.mCheckStates.size(); i += TOUCH_MODE_TAP) {
            checkedStates.put(this.mCheckStates.keyAt(i), this.mCheckStates.valueAt(i));
        }
        return checkedStates;
    }

    private int findSyncPosition() {
        int itemCount = this.mItemCount;
        if (itemCount == 0) {
            return TOUCH_MODE_UNKNOWN;
        }
        long idToMatch = this.mSyncRowId;
        if (idToMatch == Long.MIN_VALUE) {
            return TOUCH_MODE_UNKNOWN;
        }
        int seed = Math.min(itemCount + TOUCH_MODE_UNKNOWN, Math.max(TOUCH_MODE_ON, this.mSyncPosition));
        long endTime = SystemClock.uptimeMillis() + 100;
        int first = seed;
        int last = seed;
        boolean next = false;
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return TOUCH_MODE_UNKNOWN;
        }
        while (SystemClock.uptimeMillis() <= endTime) {
            if (adapter.getItemId(seed) != idToMatch) {
                boolean hitLast = last == itemCount + TOUCH_MODE_UNKNOWN;
                boolean hitFirst = first == 0;
                if (hitLast && hitFirst) {
                    break;
                } else if (hitFirst || (next && !hitLast)) {
                    last += TOUCH_MODE_TAP;
                    seed = last;
                    next = false;
                } else if (hitLast || !(next || hitFirst)) {
                    first += TOUCH_MODE_UNKNOWN;
                    seed = first;
                    next = true;
                }
            } else {
                return seed;
            }
        }
        return TOUCH_MODE_UNKNOWN;
    }

    @TargetApi(16)
    private View obtainView(int position, boolean[] isScrap) {
        isScrap[TOUCH_MODE_ON] = false;
        View scrapView = this.mRecycler.getTransientStateView(position);
        if (scrapView != null) {
            return scrapView;
        }
        View child;
        scrapView = this.mRecycler.getScrapView(position);
        if (scrapView != null) {
            child = this.mAdapter.getView(position, scrapView, this);
            if (child != scrapView) {
                this.mRecycler.addScrapView(scrapView, position);
            } else {
                isScrap[TOUCH_MODE_ON] = true;
            }
        } else {
            child = this.mAdapter.getView(position, null, this);
        }
        if (ViewCompat.getImportantForAccessibility(child) == 0) {
            ViewCompat.setImportantForAccessibility(child, TOUCH_MODE_TAP);
        }
        if (this.mHasStableIds) {
            LayoutParams lp;
            android.view.ViewGroup.LayoutParams lp2 = (LayoutParams) child.getLayoutParams();
            if (lp2 == null) {
                lp = generateDefaultLayoutParams();
            } else if (!checkLayoutParams(lp2)) {
                lp = generateLayoutParams(lp2);
            }
            lp.id = this.mAdapter.getItemId(position);
            child.setLayoutParams(lp);
        }
        if (this.mAccessibilityDelegate == null) {
            this.mAccessibilityDelegate = new ListItemAccessibilityDelegate();
        }
        ViewCompat.setAccessibilityDelegate(child, this.mAccessibilityDelegate);
        return child;
    }

    void resetState() {
        this.mScroller.forceFinished(true);
        removeAllViewsInLayout();
        this.mSelectedStart = TOUCH_MODE_ON;
        this.mFirstPosition = TOUCH_MODE_ON;
        this.mDataChanged = false;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mOldSelectedPosition = TOUCH_MODE_UNKNOWN;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        this.mOverScroll = TOUCH_MODE_ON;
        setSelectedPositionInt(TOUCH_MODE_UNKNOWN);
        setNextSelectedPositionInt(TOUCH_MODE_UNKNOWN);
        this.mSelectorPosition = TOUCH_MODE_UNKNOWN;
        this.mSelectorRect.setEmpty();
        invalidate();
    }

    private void rememberSyncState() {
        if (getChildCount() != 0) {
            this.mNeedSync = true;
            View child;
            if (this.mSelectedPosition >= 0) {
                child = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                this.mSyncRowId = this.mNextSelectedRowId;
                this.mSyncPosition = this.mNextSelectedPosition;
                if (child != null) {
                    this.mSpecificStart = this.mIsVertical ? child.getTop() : child.getLeft();
                }
                this.mSyncMode = TOUCH_MODE_ON;
                return;
            }
            child = getChildAt(TOUCH_MODE_ON);
            ListAdapter adapter = getAdapter();
            if (this.mFirstPosition < 0 || this.mFirstPosition >= adapter.getCount()) {
                this.mSyncRowId = -1;
            } else {
                this.mSyncRowId = adapter.getItemId(this.mFirstPosition);
            }
            this.mSyncPosition = this.mFirstPosition;
            if (child != null) {
                this.mSpecificStart = this.mIsVertical ? child.getTop() : child.getLeft();
            }
            this.mSyncMode = TOUCH_MODE_TAP;
        }
    }

    private ContextMenuInfo createContextMenuInfo(View view, int position, long id) {
        return new AdapterContextMenuInfo(view, position, id);
    }

    @TargetApi(11)
    private void updateOnScreenCheckedViews() {
        int firstPos = this.mFirstPosition;
        int count = getChildCount();
        for (int i = TOUCH_MODE_ON; i < count; i += TOUCH_MODE_TAP) {
            View child = getChildAt(i);
            int position = firstPos + i;
            if (child instanceof Checkable) {
                ((Checkable) child).setChecked(this.mCheckStates.get(position));
            } else if (VERSION.SDK_INT >= 11) {
                child.setActivated(this.mCheckStates.get(position));
            }
        }
    }

    public boolean performItemClick(View view, int position, long id) {
        boolean checkedStateChanged = false;
        boolean checked;
        if (this.mChoiceMode == ChoiceMode.MULTIPLE) {
            checked = !this.mCheckStates.get(position, false);
            this.mCheckStates.put(position, checked);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (checked) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(position));
                }
            }
            if (checked) {
                this.mCheckedItemCount += TOUCH_MODE_TAP;
            } else {
                this.mCheckedItemCount += TOUCH_MODE_UNKNOWN;
            }
            checkedStateChanged = true;
        } else if (this.mChoiceMode == ChoiceMode.SINGLE) {
            if (this.mCheckStates.get(position, false)) {
                checked = false;
            } else {
                checked = true;
            }
            if (checked) {
                this.mCheckStates.clear();
                this.mCheckStates.put(position, true);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    this.mCheckedIdStates.clear();
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                }
                this.mCheckedItemCount = TOUCH_MODE_TAP;
            } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(TOUCH_MODE_ON)) {
                this.mCheckedItemCount = TOUCH_MODE_ON;
            }
            checkedStateChanged = true;
        }
        if (checkedStateChanged) {
            updateOnScreenCheckedViews();
        }
        return super.performItemClick(view, position, id);
    }

    private boolean performLongPress(View child, int longPressPosition, long longPressId) {
        boolean handled = false;
        OnItemLongClickListener listener = getOnItemLongClickListener();
        if (listener != null) {
            handled = listener.onItemLongClick(this, child, longPressPosition, longPressId);
        }
        if (!handled) {
            this.mContextMenuInfo = createContextMenuInfo(child, longPressPosition, longPressId);
            handled = super.showContextMenuForChild(this);
        }
        if (handled) {
            performHapticFeedback(TOUCH_MODE_ON);
        }
        return handled;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        if (this.mIsVertical) {
            return new LayoutParams((int) TOUCH_MODE_UNKNOWN, -2);
        }
        return new LayoutParams(-2, (int) TOUCH_MODE_UNKNOWN);
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams lp) {
        return lp instanceof LayoutParams;
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSync != null) {
            ss.selectedId = this.mPendingSync.selectedId;
            ss.firstId = this.mPendingSync.firstId;
            ss.viewStart = this.mPendingSync.viewStart;
            ss.position = this.mPendingSync.position;
            ss.height = this.mPendingSync.height;
        } else {
            boolean haveChildren;
            if (getChildCount() <= 0 || this.mItemCount <= 0) {
                haveChildren = false;
            } else {
                haveChildren = true;
            }
            long selectedId = getSelectedItemId();
            ss.selectedId = selectedId;
            ss.height = getHeight();
            if (selectedId >= 0) {
                ss.viewStart = this.mSelectedStart;
                ss.position = getSelectedItemPosition();
                ss.firstId = -1;
            } else if (!haveChildren || this.mFirstPosition <= 0) {
                ss.viewStart = TOUCH_MODE_ON;
                ss.firstId = -1;
                ss.position = TOUCH_MODE_ON;
            } else {
                View child = getChildAt(TOUCH_MODE_ON);
                ss.viewStart = this.mIsVertical ? child.getTop() : child.getLeft();
                int firstPos = this.mFirstPosition;
                if (firstPos >= this.mItemCount) {
                    firstPos = this.mItemCount + TOUCH_MODE_UNKNOWN;
                }
                ss.position = firstPos;
                ss.firstId = this.mAdapter.getItemId(firstPos);
            }
            if (this.mCheckStates != null) {
                ss.checkState = cloneCheckStates();
            }
            if (this.mCheckedIdStates != null) {
                LongSparseArray<Integer> idState = new LongSparseArray();
                int count = this.mCheckedIdStates.size();
                for (int i = TOUCH_MODE_ON; i < count; i += TOUCH_MODE_TAP) {
                    idState.put(this.mCheckedIdStates.keyAt(i), this.mCheckedIdStates.valueAt(i));
                }
                ss.checkIdState = idState;
            }
            ss.checkedItemCount = this.mCheckedItemCount;
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = (long) ss.height;
        if (ss.selectedId >= 0) {
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncRowId = ss.selectedId;
            this.mSyncPosition = ss.position;
            this.mSpecificStart = ss.viewStart;
            this.mSyncMode = TOUCH_MODE_ON;
        } else if (ss.firstId >= 0) {
            setSelectedPositionInt(TOUCH_MODE_UNKNOWN);
            setNextSelectedPositionInt(TOUCH_MODE_UNKNOWN);
            this.mSelectorPosition = TOUCH_MODE_UNKNOWN;
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncRowId = ss.firstId;
            this.mSyncPosition = ss.position;
            this.mSpecificStart = ss.viewStart;
            this.mSyncMode = TOUCH_MODE_TAP;
        }
        if (ss.checkState != null) {
            this.mCheckStates = ss.checkState;
        }
        if (ss.checkIdState != null) {
            this.mCheckedIdStates = ss.checkIdState;
        }
        this.mCheckedItemCount = ss.checkedItemCount;
        requestLayout();
    }

    public void setEmptyView(View emptyView) {
        super.setEmptyView(emptyView);
        this.mEmptyView = emptyView;
        updateEmptyStatus();
    }

    public void setFocusable(boolean focusable) {
        boolean z = true;
        ListAdapter adapter = getAdapter();
        boolean empty;
        if (adapter == null || adapter.getCount() == 0) {
            empty = true;
        } else {
            empty = false;
        }
        this.mDesiredFocusableState = focusable;
        if (!focusable) {
            this.mDesiredFocusableInTouchModeState = false;
        }
        if (!focusable || empty) {
            z = false;
        }
        super.setFocusable(z);
    }

    public void setFocusableInTouchMode(boolean focusable) {
        boolean z = true;
        ListAdapter adapter = getAdapter();
        boolean empty;
        if (adapter == null || adapter.getCount() == 0) {
            empty = true;
        } else {
            empty = false;
        }
        this.mDesiredFocusableInTouchModeState = focusable;
        if (focusable) {
            this.mDesiredFocusableState = true;
        }
        if (!focusable || empty) {
            z = false;
        }
        super.setFocusableInTouchMode(z);
    }

    private void checkFocus() {
        boolean focusable;
        boolean z;
        boolean z2 = true;
        ListAdapter adapter = getAdapter();
        if (adapter == null || adapter.getCount() <= 0) {
            focusable = false;
        } else {
            focusable = true;
        }
        if (focusable && this.mDesiredFocusableInTouchModeState) {
            z = true;
        } else {
            z = false;
        }
        super.setFocusableInTouchMode(z);
        if (!(focusable && this.mDesiredFocusableState)) {
            z2 = false;
        }
        super.setFocusable(z2);
        if (this.mEmptyView != null) {
            updateEmptyStatus();
        }
    }

    private void updateEmptyStatus() {
        boolean isEmpty;
        if (this.mAdapter == null || this.mAdapter.isEmpty()) {
            isEmpty = true;
        } else {
            isEmpty = false;
        }
        if (isEmpty) {
            if (this.mEmptyView != null) {
                this.mEmptyView.setVisibility(TOUCH_MODE_ON);
                setVisibility(8);
            } else {
                setVisibility(TOUCH_MODE_ON);
            }
            if (this.mDataChanged) {
                layout(getLeft(), getTop(), getRight(), getBottom());
                return;
            }
            return;
        }
        if (this.mEmptyView != null) {
            this.mEmptyView.setVisibility(8);
        }
        setVisibility(TOUCH_MODE_ON);
    }
}
