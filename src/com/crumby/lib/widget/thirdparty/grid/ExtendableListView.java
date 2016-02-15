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
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.Scroller;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ExtendableListView
  extends AbsListView
{
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
  
  public ExtendableListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setWillNotDraw(false);
    setClipToPadding(false);
    setFocusableInTouchMode(false);
    paramContext = ViewConfiguration.get(paramContext);
    this.mTouchSlop = paramContext.getScaledTouchSlop();
    this.mMaximumVelocity = paramContext.getScaledMaximumFlingVelocity();
    this.mFlingVelocity = paramContext.getScaledMinimumFlingVelocity();
    this.mRecycleBin = new RecycleBin();
    this.mObserver = new AdapterDataSetObserver();
    this.mHeaderViewInfos = new ArrayList();
    this.mFooterViewInfos = new ArrayList();
    this.mLayoutMode = 0;
  }
  
  private void adjustViewsUpOrDown()
  {
    if (getChildCount() > 0)
    {
      int j = getHighestChildTop() - getListPaddingTop();
      int i = j;
      if (j < 0) {
        i = 0;
      }
      if (i != 0) {
        offsetChildrenTopAndBottom(-i);
      }
    }
  }
  
  private void clearRecycledState(ArrayList<FixedViewInfo> paramArrayList)
  {
    if (paramArrayList == null) {}
    for (;;)
    {
      return;
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext())
      {
        LayoutParams localLayoutParams = (LayoutParams)((FixedViewInfo)paramArrayList.next()).view.getLayoutParams();
        if (localLayoutParams != null) {
          localLayoutParams.recycledHeaderFooter = false;
        }
      }
    }
  }
  
  private void clearState()
  {
    clearRecycledState(this.mHeaderViewInfos);
    clearRecycledState(this.mFooterViewInfos);
    removeAllViewsInLayout();
    this.mFirstPosition = 0;
    this.mDataChanged = false;
    this.mRecycleBin.clear();
    this.mNeedSync = false;
    this.mSyncState = null;
    this.mLayoutMode = 0;
    invalidate();
  }
  
  private void correctTooHigh(int paramInt)
  {
    if ((this.mFirstPosition + paramInt - 1 == this.mItemCount - 1) && (paramInt > 0))
    {
      paramInt = getLowestChildBottom();
      int i = getBottom() - getTop() - getListPaddingBottom() - paramInt;
      int j = getHighestChildTop();
      if ((i > 0) && ((this.mFirstPosition > 0) || (j < getListPaddingTop())))
      {
        paramInt = i;
        if (this.mFirstPosition == 0) {
          paramInt = Math.min(i, getListPaddingTop() - j);
        }
        offsetChildrenTopAndBottom(paramInt);
        if (this.mFirstPosition > 0)
        {
          paramInt = this.mFirstPosition - 1;
          fillUp(paramInt, getNextChildUpsBottom(paramInt));
          adjustViewsUpOrDown();
        }
      }
    }
  }
  
  private void correctTooLow(int paramInt)
  {
    int m;
    if ((this.mFirstPosition == 0) && (paramInt > 0))
    {
      int i = getHighestChildTop();
      int k = getListPaddingTop();
      int j = getTop() - getBottom() - getListPaddingBottom();
      i -= k;
      k = getLowestChildBottom();
      m = this.mFirstPosition + paramInt - 1;
      if (i > 0)
      {
        if ((m >= this.mItemCount - 1) && (k <= j)) {
          break label139;
        }
        paramInt = i;
        if (m == this.mItemCount - 1) {
          paramInt = Math.min(i, k - j);
        }
        offsetChildrenTopAndBottom(-paramInt);
        if (m < this.mItemCount - 1)
        {
          paramInt = m + 1;
          fillDown(paramInt, getNextChildDownsTop(paramInt));
          adjustViewsUpOrDown();
        }
      }
    }
    label139:
    while (m != this.mItemCount - 1) {
      return;
    }
    adjustViewsUpOrDown();
  }
  
  private View fillDown(int paramInt1, int paramInt2)
  {
    int m = getHeight();
    int i = m;
    int j = paramInt1;
    int k = paramInt2;
    if (this.mClipToPadding)
    {
      i = m - getListPaddingBottom();
      k = paramInt2;
      j = paramInt1;
    }
    while (((k < i) || (hasSpaceDown())) && (j < this.mItemCount))
    {
      makeAndAddView(j, k, true, false);
      j += 1;
      k = getNextChildDownsTop(j);
    }
    return null;
  }
  
  private View fillFromTop(int paramInt)
  {
    this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
    if (this.mFirstPosition < 0) {
      this.mFirstPosition = 0;
    }
    return fillDown(this.mFirstPosition, paramInt);
  }
  
  private View fillSpecific(int paramInt1, int paramInt2)
  {
    View localView1 = makeAndAddView(paramInt1, paramInt2, true, false);
    this.mFirstPosition = paramInt1;
    paramInt2 = getNextChildUpsBottom(paramInt1 - 1);
    int i = getNextChildDownsTop(paramInt1 + 1);
    View localView2 = fillUp(paramInt1 - 1, paramInt2);
    adjustViewsUpOrDown();
    View localView3 = fillDown(paramInt1 + 1, i);
    paramInt1 = getChildCount();
    if (paramInt1 > 0) {
      correctTooHigh(paramInt1);
    }
    if (0 != 0) {
      return localView1;
    }
    if (localView2 != null) {
      return localView2;
    }
    return localView3;
  }
  
  private View fillUp(int paramInt1, int paramInt2)
  {
    int i;
    if (this.mClipToPadding) {
      i = getListPaddingTop();
    }
    while (((paramInt2 > i) || (hasSpaceUp())) && (paramInt1 >= 0))
    {
      makeAndAddView(paramInt1, paramInt2, false, false);
      paramInt1 -= 1;
      paramInt2 = getNextChildUpsBottom(paramInt1);
      continue;
      i = 0;
    }
    this.mFirstPosition = (paramInt1 + 1);
    return null;
  }
  
  private int findMotionRow(int paramInt)
  {
    int j = getChildCount();
    if (j > 0)
    {
      int i = 0;
      while (i < j)
      {
        if (paramInt <= getChildAt(i).getBottom()) {
          return this.mFirstPosition + i;
        }
        i += 1;
      }
    }
    return -1;
  }
  
  private void initOrResetVelocityTracker()
  {
    if (this.mVelocityTracker == null)
    {
      this.mVelocityTracker = VelocityTracker.obtain();
      return;
    }
    this.mVelocityTracker.clear();
  }
  
  private void initVelocityTrackerIfNotExists()
  {
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
  }
  
  private View makeAndAddView(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    onChildCreated(paramInt1, paramBoolean1);
    if (!this.mDataChanged)
    {
      localView = this.mRecycleBin.getActiveView(paramInt1);
      if (localView != null)
      {
        setupChild(localView, paramInt1, paramInt2, paramBoolean1, paramBoolean2, true);
        return localView;
      }
    }
    View localView = obtainView(paramInt1, this.mIsScrap);
    setupChild(localView, paramInt1, paramInt2, paramBoolean1, paramBoolean2, this.mIsScrap[0]);
    return localView;
  }
  
  private boolean moveTheChildren(int paramInt1, int paramInt2)
  {
    if (!hasChildren()) {
      return true;
    }
    paramInt1 = getHighestChildTop();
    int i = getLowestChildBottom();
    int k = 0;
    int j = 0;
    if (this.mClipToPadding)
    {
      k = getListPaddingTop();
      j = getListPaddingBottom();
    }
    int i3 = getHeight();
    int i4 = getFirstChildTop();
    int i5 = getLastChildBottom();
    int m = i3 - getListPaddingBottom() - getListPaddingTop();
    int i6;
    int i7;
    if (paramInt2 < 0)
    {
      m = Math.max(-(m - 1), paramInt2);
      i6 = this.mFirstPosition;
      n = getListPaddingTop();
      paramInt2 = getListPaddingBottom();
      i7 = getChildCount();
      if ((i6 != 0) || (paramInt1 < n) || (m < 0)) {
        break label183;
      }
      paramInt1 = 1;
      label132:
      if ((i6 + i7 != this.mItemCount) || (i > i3 - paramInt2) || (m > 0)) {
        break label188;
      }
      paramInt2 = 1;
    }
    for (;;)
    {
      if (paramInt1 != 0)
      {
        if (m != 0)
        {
          return true;
          m = Math.min(m - 1, paramInt2);
          break;
          label183:
          paramInt1 = 0;
          break label132;
          label188:
          paramInt2 = 0;
          continue;
        }
        return false;
      }
    }
    if (paramInt2 != 0) {
      return m != 0;
    }
    boolean bool;
    int i8;
    int i9;
    int i1;
    int i2;
    View localView;
    if (m < 0)
    {
      bool = true;
      i8 = getHeaderViewsCount();
      i9 = this.mItemCount - getFooterViewsCount();
      i = 0;
      n = 0;
      paramInt2 = 0;
      paramInt1 = 0;
      if (bool)
      {
        i = -m;
        paramInt2 = i;
        if (this.mClipToPadding) {
          paramInt2 = i + getListPaddingTop();
        }
        i = 0;
      }
    }
    else
    {
      for (;;)
      {
        i1 = paramInt1;
        i2 = n;
        if (i < i7)
        {
          localView = getChildAt(i);
          if (localView.getBottom() >= paramInt2)
          {
            i2 = n;
            i1 = paramInt1;
          }
        }
        else
        {
          this.mBlockLayoutRequests = true;
          if (i1 > 0)
          {
            detachViewsFromParent(i2, i1);
            this.mRecycleBin.removeSkippedScrap();
            onChildrenDetached(i2, i1);
          }
          if (!awakenScrollBars()) {
            invalidate();
          }
          offsetChildrenTopAndBottom(m);
          if (bool) {
            this.mFirstPosition += i1;
          }
          paramInt1 = Math.abs(m);
          if ((k - i4 < paramInt1) || (i5 - (i3 - j) < paramInt1)) {
            fillGap(bool);
          }
          this.mBlockLayoutRequests = false;
          invokeOnItemScrollListener();
          return false;
          bool = false;
          break;
        }
        paramInt1 += 1;
        i1 = i6 + i;
        if ((i1 >= i8) && (i1 < i9)) {
          this.mRecycleBin.addScrapView(localView, i1);
        }
        i += 1;
      }
    }
    paramInt1 = i3 - m;
    int n = paramInt1;
    if (this.mClipToPadding) {
      n = paramInt1 - getListPaddingBottom();
    }
    paramInt1 = i7 - 1;
    for (;;)
    {
      i1 = paramInt2;
      i2 = i;
      if (paramInt1 < 0) {
        break;
      }
      localView = getChildAt(paramInt1);
      i1 = paramInt2;
      i2 = i;
      if (localView.getTop() <= n) {
        break;
      }
      i = paramInt1;
      paramInt2 += 1;
      i1 = i6 + paramInt1;
      if ((i1 >= i8) && (i1 < i9)) {
        this.mRecycleBin.addScrapView(localView, i1);
      }
      paramInt1 -= 1;
    }
  }
  
  private View obtainView(int paramInt, boolean[] paramArrayOfBoolean)
  {
    paramArrayOfBoolean[0] = false;
    View localView1 = this.mRecycleBin.getScrapView(paramInt);
    if (localView1 != null)
    {
      View localView2 = this.mAdapter.getView(paramInt, localView1, this);
      if (localView2 != localView1)
      {
        this.mRecycleBin.addScrapView(localView1, paramInt);
        return localView2;
      }
      paramArrayOfBoolean[0] = true;
      return localView2;
    }
    return this.mAdapter.getView(paramInt, null, this);
  }
  
  private void onSecondaryPointerUp(MotionEvent paramMotionEvent)
  {
    int i = (paramMotionEvent.getAction() & 0xFF00) >> 8;
    if (paramMotionEvent.getPointerId(i) == this.mActivePointerId) {
      if (i != 0) {
        break label64;
      }
    }
    label64:
    for (i = 1;; i = 0)
    {
      this.mMotionX = ((int)paramMotionEvent.getX(i));
      this.mMotionY = ((int)paramMotionEvent.getY(i));
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      recycleVelocityTracker();
      return;
    }
  }
  
  private boolean onTouchCancel(MotionEvent paramMotionEvent)
  {
    this.mTouchMode = 0;
    setPressed(false);
    invalidate();
    recycleVelocityTracker();
    this.mActivePointerId = -1;
    return true;
  }
  
  private boolean onTouchDown(MotionEvent paramMotionEvent)
  {
    int k = (int)paramMotionEvent.getX();
    int m = (int)paramMotionEvent.getY();
    int j = pointToPosition(k, m);
    this.mVelocityTracker.clear();
    this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
    int i;
    if ((this.mTouchMode != 2) && (!this.mDataChanged) && (j >= 0) && (getAdapter().isEnabled(j)))
    {
      this.mTouchMode = 3;
      i = j;
      if (paramMotionEvent.getEdgeFlags() != 0)
      {
        i = j;
        if (j < 0) {
          return false;
        }
      }
    }
    else
    {
      i = j;
      if (this.mTouchMode == 2)
      {
        this.mTouchMode = 1;
        this.mMotionCorrection = 0;
        i = findMotionRow(m);
      }
    }
    this.mMotionX = k;
    this.mMotionY = m;
    this.mMotionPosition = i;
    this.mLastY = Integer.MIN_VALUE;
    return true;
  }
  
  private boolean onTouchMove(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId);
    if (i < 0)
    {
      Log.e("ExtendableListView", "onTouchMove could not find pointer with id " + this.mActivePointerId + " - did ExtendableListView receive an inconsistent " + "event stream?");
      return false;
    }
    i = (int)MotionEventCompat.getY(paramMotionEvent, i);
    if (this.mDataChanged) {
      layoutChildren();
    }
    switch (this.mTouchMode)
    {
    }
    for (;;)
    {
      return true;
      startScrollIfNeeded(i);
      continue;
      scrollIfNeeded(i);
    }
  }
  
  private boolean onTouchPointerUp(MotionEvent paramMotionEvent)
  {
    onSecondaryPointerUp(paramMotionEvent);
    int j = this.mMotionX;
    int i = this.mMotionY;
    j = pointToPosition(j, i);
    if (j >= 0) {
      this.mMotionPosition = j;
    }
    this.mLastY = i;
    return true;
  }
  
  private boolean onTouchUp(MotionEvent paramMotionEvent)
  {
    switch (this.mTouchMode)
    {
    case 2: 
    default: 
      setPressed(false);
      invalidate();
      recycleVelocityTracker();
      this.mActivePointerId = -1;
      return true;
    case 3: 
    case 4: 
    case 5: 
      return onTouchUpTap(paramMotionEvent);
    }
    return onTouchUpScrolling(paramMotionEvent);
  }
  
  private boolean onTouchUpScrolling(MotionEvent paramMotionEvent)
  {
    if (hasChildren())
    {
      int i = getFirstChildTop();
      int j = getLastChildBottom();
      if ((this.mFirstPosition == 0) && (i >= getListPaddingTop()) && (this.mFirstPosition + getChildCount() < this.mItemCount) && (j <= getHeight() - getListPaddingBottom())) {}
      for (i = 1; i == 0; i = 0)
      {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        float f = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        if (Math.abs(f) <= this.mFlingVelocity) {
          break;
        }
        startFlingRunnable(f);
        this.mTouchMode = 2;
        this.mMotionY = 0;
        invalidate();
        return true;
      }
    }
    stopFlingRunnable();
    recycleVelocityTracker();
    this.mTouchMode = 0;
    return true;
  }
  
  private boolean onTouchUpTap(MotionEvent paramMotionEvent)
  {
    if (this.mPerformClick == null)
    {
      invalidate();
      this.mPerformClick = new PerformClick(null);
    }
    int i = this.mMotionPosition;
    if ((!this.mDataChanged) && (i >= 0) && (this.mAdapter.isEnabled(i)))
    {
      paramMotionEvent = this.mPerformClick;
      paramMotionEvent.mClickMotionPosition = i;
      paramMotionEvent.rememberWindowAttachCount();
      paramMotionEvent.run();
    }
    return true;
  }
  
  private void postOnAnimate(Runnable paramRunnable)
  {
    ViewCompat.postOnAnimation(this, paramRunnable);
  }
  
  private void recycleVelocityTracker()
  {
    if (this.mVelocityTracker != null)
    {
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }
  
  private void removeFixedViewInfo(View paramView, ArrayList<FixedViewInfo> paramArrayList)
  {
    int j = paramArrayList.size();
    int i = 0;
    for (;;)
    {
      if (i < j)
      {
        if (((FixedViewInfo)paramArrayList.get(i)).view == paramView) {
          paramArrayList.remove(i);
        }
      }
      else {
        return;
      }
      i += 1;
    }
  }
  
  static View retrieveFromScrap(ArrayList<View> paramArrayList, int paramInt)
  {
    int j = paramArrayList.size();
    if (j > 0)
    {
      int i = 0;
      while (i < j)
      {
        View localView = (View)paramArrayList.get(i);
        if (((LayoutParams)localView.getLayoutParams()).position == paramInt)
        {
          paramArrayList.remove(i);
          return localView;
        }
        i += 1;
      }
      return (View)paramArrayList.remove(j - 1);
    }
    return null;
  }
  
  private void scrollIfNeeded(int paramInt)
  {
    int j = paramInt - this.mMotionY;
    int k = j - this.mMotionCorrection;
    int i;
    if (this.mLastY != Integer.MIN_VALUE)
    {
      i = paramInt - this.mLastY;
      if ((this.mTouchMode == 1) && (paramInt != this.mLastY))
      {
        if (Math.abs(j) > this.mTouchSlop)
        {
          ViewParent localViewParent = getParent();
          if (localViewParent != null) {
            localViewParent.requestDisallowInterceptTouchEvent(true);
          }
        }
        if (this.mMotionPosition < 0) {
          break label146;
        }
      }
    }
    label146:
    for (j = this.mMotionPosition - this.mFirstPosition;; j = getChildCount() / 2)
    {
      boolean bool = false;
      if (i != 0) {
        bool = moveTheChildren(k, i);
      }
      if (getChildAt(j) != null)
      {
        if (bool) {
          this.mTouchMode = 5;
        }
        this.mMotionY = paramInt;
      }
      this.mLastY = paramInt;
      return;
      i = k;
      break;
    }
  }
  
  private void setupChild(View paramView, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    int j;
    int i;
    label39:
    int k;
    label51:
    label71:
    int m;
    LayoutParams localLayoutParams;
    if (paramView.isSelected())
    {
      j = 1;
      i = this.mTouchMode;
      if ((i <= 3) || (i >= 1) || (this.mMotionPosition != paramInt1)) {
        break label239;
      }
      paramBoolean2 = true;
      if (paramBoolean2 == paramView.isPressed()) {
        break label245;
      }
      k = 1;
      if ((paramBoolean3) && (j == 0) && (!paramView.isLayoutRequested())) {
        break label251;
      }
      i = 1;
      m = this.mAdapter.getItemViewType(paramInt1);
      if (m != -2) {
        break label257;
      }
      localLayoutParams = generateWrapperLayoutParams(paramView);
      label97:
      localLayoutParams.viewType = m;
      localLayoutParams.position = paramInt1;
      if ((!paramBoolean3) && ((!localLayoutParams.recycledHeaderFooter) || (localLayoutParams.viewType != -2))) {
        break label273;
      }
      if (!paramBoolean1) {
        break label267;
      }
      m = -1;
      label141:
      attachViewToParent(paramView, m, localLayoutParams);
      if (j != 0) {
        paramView.setSelected(false);
      }
      if (k != 0) {
        paramView.setPressed(paramBoolean2);
      }
      if (i == 0) {
        break label317;
      }
      onMeasureChild(paramView, localLayoutParams);
      label183:
      j = paramView.getMeasuredWidth();
      k = paramView.getMeasuredHeight();
      if (!paramBoolean1) {
        break label325;
      }
    }
    for (;;)
    {
      m = getChildLeft(paramInt1);
      if (i == 0) {
        break label333;
      }
      onLayoutChild(paramView, paramInt1, paramBoolean1, m, paramInt2, m + j, paramInt2 + k);
      return;
      j = 0;
      break;
      label239:
      paramBoolean2 = false;
      break label39;
      label245:
      k = 0;
      break label51;
      label251:
      i = 0;
      break label71;
      label257:
      localLayoutParams = generateChildLayoutParams(paramView);
      break label97;
      label267:
      m = 0;
      break label141;
      label273:
      if (localLayoutParams.viewType == -2) {
        localLayoutParams.recycledHeaderFooter = true;
      }
      if (paramBoolean1) {}
      for (m = -1;; m = 0)
      {
        addViewInLayout(paramView, m, localLayoutParams, true);
        break;
      }
      label317:
      cleanupLayoutState(paramView);
      break label183;
      label325:
      paramInt2 -= k;
    }
    label333:
    onOffsetChild(paramView, paramInt1, paramBoolean1, m, paramInt2);
  }
  
  private void startFlingRunnable(float paramFloat)
  {
    if (this.mFlingRunnable == null) {
      this.mFlingRunnable = new FlingRunnable();
    }
    this.mFlingRunnable.start((int)-paramFloat);
  }
  
  private boolean startScrollIfNeeded(int paramInt)
  {
    int i = paramInt - this.mMotionY;
    if (Math.abs(i) > this.mTouchSlop)
    {
      this.mTouchMode = 1;
      if (i > 0) {}
      for (i = this.mTouchSlop;; i = -this.mTouchSlop)
      {
        this.mMotionCorrection = i;
        setPressed(false);
        Object localObject = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (localObject != null) {
          ((View)localObject).setPressed(false);
        }
        localObject = getParent();
        if (localObject != null) {
          ((ViewParent)localObject).requestDisallowInterceptTouchEvent(true);
        }
        scrollIfNeeded(paramInt);
        return true;
      }
    }
    return false;
  }
  
  private void stopFlingRunnable()
  {
    if (this.mFlingRunnable != null) {
      this.mFlingRunnable.endFling();
    }
  }
  
  public void addFooterView(View paramView)
  {
    addFooterView(paramView, null, true);
  }
  
  public void addFooterView(View paramView, Object paramObject, boolean paramBoolean)
  {
    FixedViewInfo localFixedViewInfo = new FixedViewInfo();
    localFixedViewInfo.view = paramView;
    localFixedViewInfo.data = paramObject;
    localFixedViewInfo.isSelectable = paramBoolean;
    this.mFooterViewInfos.add(localFixedViewInfo);
    if ((this.mAdapter != null) && (this.mObserver != null)) {
      this.mObserver.onChanged();
    }
  }
  
  public void addHeaderView(View paramView)
  {
    addHeaderView(paramView, null, true);
  }
  
  public void addHeaderView(View paramView, Object paramObject, boolean paramBoolean)
  {
    if ((this.mAdapter != null) && (!(this.mAdapter instanceof HeaderViewListAdapter))) {
      throw new IllegalStateException("Cannot add header view to list -- setAdapter has already been called.");
    }
    FixedViewInfo localFixedViewInfo = new FixedViewInfo();
    localFixedViewInfo.view = paramView;
    localFixedViewInfo.data = paramObject;
    localFixedViewInfo.isSelectable = paramBoolean;
    this.mHeaderViewInfos.add(localFixedViewInfo);
    if ((this.mAdapter != null) && (this.mObserver != null)) {
      this.mObserver.onChanged();
    }
  }
  
  protected void adjustViewsAfterFillGap(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      correctTooHigh(getChildCount());
      return;
    }
    correctTooLow(getChildCount());
  }
  
  protected void fillGap(boolean paramBoolean)
  {
    int i = getChildCount();
    if (paramBoolean)
    {
      i = this.mFirstPosition + i;
      fillDown(i, getChildTop(i));
    }
    for (;;)
    {
      adjustViewsAfterFillGap(paramBoolean);
      return;
      i = this.mFirstPosition - 1;
      fillUp(i, getChildBottom(i));
    }
  }
  
  protected LayoutParams generateChildLayoutParams(View paramView)
  {
    return generateWrapperLayoutParams(paramView);
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-1, -2, 0);
  }
  
  protected LayoutParams generateHeaderFooterLayoutParams(View paramView)
  {
    return new LayoutParams(-1, -2, 0);
  }
  
  protected LayoutParams generateWrapperLayoutParams(View paramView)
  {
    Object localObject = null;
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    paramView = (View)localObject;
    if (localLayoutParams != null) {
      if (!(localLayoutParams instanceof LayoutParams)) {
        break label38;
      }
    }
    label38:
    for (paramView = (LayoutParams)localLayoutParams;; paramView = new LayoutParams(localLayoutParams))
    {
      localObject = paramView;
      if (paramView == null) {
        localObject = generateDefaultLayoutParams();
      }
      return (LayoutParams)localObject;
    }
  }
  
  public ListAdapter getAdapter()
  {
    return this.mAdapter;
  }
  
  protected int getChildBottom(int paramInt)
  {
    int i = getChildCount();
    paramInt = 0;
    if (this.mClipToPadding) {
      paramInt = getListPaddingBottom();
    }
    if (i > 0) {
      return getChildAt(0).getTop();
    }
    return getHeight() - paramInt;
  }
  
  protected int getChildLeft(int paramInt)
  {
    return getListPaddingLeft();
  }
  
  protected int getChildTop(int paramInt)
  {
    int i = getChildCount();
    paramInt = 0;
    if (this.mClipToPadding) {
      paramInt = getListPaddingTop();
    }
    if (i > 0) {
      paramInt = getChildAt(i - 1).getBottom();
    }
    return paramInt;
  }
  
  public int getCount()
  {
    return this.mItemCount;
  }
  
  protected int getFirstChildTop()
  {
    int i = 0;
    if (hasChildren()) {
      i = getChildAt(0).getTop();
    }
    return i;
  }
  
  public int getFirstVisiblePosition()
  {
    return Math.max(0, this.mFirstPosition - getHeaderViewsCount());
  }
  
  public int getFooterViewsCount()
  {
    return this.mFooterViewInfos.size();
  }
  
  public int getHeaderViewsCount()
  {
    return this.mHeaderViewInfos.size();
  }
  
  protected int getHighestChildTop()
  {
    int i = 0;
    if (hasChildren()) {
      i = getChildAt(0).getTop();
    }
    return i;
  }
  
  protected int getLastChildBottom()
  {
    if (hasChildren()) {
      return getChildAt(getChildCount() - 1).getBottom();
    }
    return 0;
  }
  
  public int getLastVisiblePosition()
  {
    return Math.min(this.mFirstPosition + getChildCount() - 1, this.mAdapter.getCount() - 1);
  }
  
  protected int getLowestChildBottom()
  {
    if (hasChildren()) {
      return getChildAt(getChildCount() - 1).getBottom();
    }
    return 0;
  }
  
  protected int getNextChildDownsTop(int paramInt)
  {
    paramInt = getChildCount();
    if (paramInt > 0) {
      return getChildAt(paramInt - 1).getBottom();
    }
    return 0;
  }
  
  protected int getNextChildUpsBottom(int paramInt)
  {
    paramInt = getChildCount();
    if (paramInt == 0) {}
    while (paramInt <= 0) {
      return 0;
    }
    return getChildAt(0).getTop();
  }
  
  public View getSelectedView()
  {
    return null;
  }
  
  protected void handleDataChanged()
  {
    super.handleDataChanged();
    int i = this.mItemCount;
    if ((i > 0) && (this.mNeedSync))
    {
      this.mNeedSync = false;
      this.mSyncState = null;
      this.mLayoutMode = 2;
      this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), i - 1);
      if (this.mSyncPosition == 0) {
        this.mLayoutMode = 1;
      }
      return;
    }
    this.mLayoutMode = 1;
    this.mNeedSync = false;
    this.mSyncState = null;
  }
  
  protected boolean hasChildren()
  {
    return getChildCount() > 0;
  }
  
  protected boolean hasSpaceDown()
  {
    return false;
  }
  
  protected boolean hasSpaceUp()
  {
    return false;
  }
  
  void invokeOnItemScrollListener()
  {
    if (this.mOnScrollListener != null) {
      this.mOnScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
    }
  }
  
  protected void layoutChildren()
  {
    if (this.mBlockLayoutRequests) {
      return;
    }
    this.mBlockLayoutRequests = true;
    int j;
    int k;
    boolean bool;
    try
    {
      super.layoutChildren();
      invalidate();
      if (this.mAdapter == null)
      {
        clearState();
        return;
      }
      j = getListPaddingTop();
      k = getChildCount();
      View localView = null;
      if (this.mLayoutMode == 0) {
        localView = getChildAt(0);
      }
      bool = this.mDataChanged;
      if (bool) {
        handleDataChanged();
      }
      if (this.mItemCount == 0)
      {
        clearState();
        return;
      }
      if (this.mItemCount != this.mAdapter.getCount()) {
        throw new IllegalStateException("The content of the adapter has changed but ExtendableListView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. [in ExtendableListView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
      }
    }
    finally
    {
      this.mBlockLayoutRequests = false;
    }
    int m = this.mFirstPosition;
    RecycleBin localRecycleBin = this.mRecycleBin;
    int i;
    if (bool)
    {
      i = 0;
      while (i < k)
      {
        localRecycleBin.addScrapView(getChildAt(i), m + i);
        i += 1;
      }
    }
    localRecycleBin.fillActiveViews(k, m);
    detachAllViewsFromParent();
    localRecycleBin.removeSkippedScrap();
    switch (this.mLayoutMode)
    {
    }
    for (;;)
    {
      if (k == 0) {
        fillFromTop(j);
      }
      for (;;)
      {
        localRecycleBin.scrapActiveViews();
        this.mDataChanged = false;
        this.mNeedSync = false;
        this.mLayoutMode = 0;
        this.mBlockLayoutRequests = false;
        return;
        this.mFirstPosition = 0;
        resetToTop();
        adjustViewsUpOrDown();
        fillFromTop(j);
        adjustViewsUpOrDown();
        continue;
        fillSpecific(this.mSyncPosition, this.mSpecificTop);
        continue;
        if (this.mFirstPosition < this.mItemCount)
        {
          k = this.mFirstPosition;
          if (localObject == null) {}
          for (i = j;; i = ((View)localObject).getTop())
          {
            fillSpecific(k, i);
            break;
          }
        }
        fillSpecific(0, j);
      }
    }
  }
  
  public void notifyTouchMode()
  {
    switch (this.mTouchMode)
    {
    default: 
      return;
    case 1: 
      reportScrollStateChange(1);
      return;
    case 2: 
      reportScrollStateChange(2);
      return;
    }
    reportScrollStateChange(0);
  }
  
  protected void offsetChildrenTopAndBottom(int paramInt)
  {
    int j = getChildCount();
    int i = 0;
    while (i < j)
    {
      getChildAt(i).offsetTopAndBottom(paramInt);
      i += 1;
    }
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mAdapter != null)
    {
      this.mDataChanged = true;
      this.mOldItemCount = this.mItemCount;
      this.mItemCount = this.mAdapter.getCount();
    }
    this.mIsAttached = true;
  }
  
  protected void onChildCreated(int paramInt, boolean paramBoolean) {}
  
  protected void onChildrenDetached(int paramInt1, int paramInt2) {}
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mRecycleBin.clear();
    if (this.mFlingRunnable != null) {
      removeCallbacks(this.mFlingRunnable);
    }
    this.mIsAttached = false;
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect) {}
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    if (!this.mIsAttached) {}
    do
    {
      do
      {
        return false;
        switch (i & 0xFF)
        {
        case 4: 
        case 5: 
        default: 
          return false;
        case 0: 
          i = this.mTouchMode;
          j = (int)paramMotionEvent.getX();
          int k = (int)paramMotionEvent.getY();
          this.mActivePointerId = paramMotionEvent.getPointerId(0);
          int m = findMotionRow(k);
          if ((i != 2) && (m >= 0))
          {
            this.mMotionX = j;
            this.mMotionY = k;
            this.mMotionPosition = m;
            this.mTouchMode = 3;
          }
          this.mLastY = Integer.MIN_VALUE;
          initOrResetVelocityTracker();
          this.mVelocityTracker.addMovement(paramMotionEvent);
        }
      } while (i != 2);
      return true;
      switch (this.mTouchMode)
      {
      default: 
        return false;
      }
      int j = paramMotionEvent.findPointerIndex(this.mActivePointerId);
      i = j;
      if (j == -1)
      {
        i = 0;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
      }
      i = (int)paramMotionEvent.getY(i);
      initVelocityTrackerIfNotExists();
      this.mVelocityTracker.addMovement(paramMotionEvent);
    } while (!startScrollIfNeeded(i));
    return true;
    this.mTouchMode = 0;
    this.mActivePointerId = -1;
    recycleVelocityTracker();
    reportScrollStateChange(0);
    return false;
    onSecondaryPointerUp(paramMotionEvent);
    return false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mAdapter == null) {
      return;
    }
    if (paramBoolean)
    {
      paramInt2 = getChildCount();
      paramInt1 = 0;
      while (paramInt1 < paramInt2)
      {
        getChildAt(paramInt1).forceLayout();
        paramInt1 += 1;
      }
      this.mRecycleBin.markChildrenDirty();
    }
    this.mInLayout = true;
    layoutChildren();
    this.mInLayout = false;
  }
  
  protected void onLayoutChild(View paramView, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    paramView.layout(paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.mWidthMeasureSpec = paramInt1;
  }
  
  protected void onMeasureChild(View paramView, LayoutParams paramLayoutParams)
  {
    int j = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, getListPaddingLeft() + getListPaddingRight(), paramLayoutParams.width);
    int i = paramLayoutParams.height;
    if (i > 0) {}
    for (i = View.MeasureSpec.makeMeasureSpec(i, 1073741824);; i = View.MeasureSpec.makeMeasureSpec(0, 0))
    {
      paramView.measure(j, i);
      return;
    }
  }
  
  protected void onOffsetChild(View paramView, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3)
  {
    paramView.offsetLeftAndRight(paramInt2 - paramView.getLeft());
    paramView.offsetTopAndBottom(paramInt3 - paramView.getTop());
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    paramParcelable = (ListSavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.mDataChanged = true;
    this.mSyncHeight = paramParcelable.height;
    if (paramParcelable.firstId >= 0L)
    {
      this.mNeedSync = true;
      this.mSyncState = paramParcelable;
      this.mSyncRowId = paramParcelable.firstId;
      this.mSyncPosition = paramParcelable.position;
      this.mSpecificTop = paramParcelable.viewTop;
    }
    requestLayout();
  }
  
  public Parcelable onSaveInstanceState()
  {
    ListSavedState localListSavedState = new ListSavedState(super.onSaveInstanceState());
    if (this.mSyncState != null)
    {
      localListSavedState.selectedId = this.mSyncState.selectedId;
      localListSavedState.firstId = this.mSyncState.firstId;
      localListSavedState.viewTop = this.mSyncState.viewTop;
      localListSavedState.position = this.mSyncState.position;
      localListSavedState.height = this.mSyncState.height;
      return localListSavedState;
    }
    if ((getChildCount() > 0) && (this.mItemCount > 0)) {}
    for (int i = 1;; i = 0)
    {
      localListSavedState.selectedId = getSelectedItemId();
      localListSavedState.height = getHeight();
      if ((i == 0) || (this.mFirstPosition <= 0)) {
        break;
      }
      localListSavedState.viewTop = getChildAt(0).getTop();
      int j = this.mFirstPosition;
      i = j;
      if (j >= this.mItemCount) {
        i = this.mItemCount - 1;
      }
      localListSavedState.position = i;
      localListSavedState.firstId = this.mAdapter.getItemId(i);
      return localListSavedState;
    }
    localListSavedState.viewTop = 0;
    localListSavedState.firstId = -1L;
    localListSavedState.position = 0;
    return localListSavedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (getChildCount() > 0)
    {
      this.mDataChanged = true;
      rememberSyncState();
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = false;
    if (!isEnabled()) {
      if ((isClickable()) || (isLongClickable())) {
        bool = true;
      }
    }
    do
    {
      return bool;
      initVelocityTrackerIfNotExists();
      this.mVelocityTracker.addMovement(paramMotionEvent);
    } while (!hasChildren());
    switch (paramMotionEvent.getAction() & 0xFF)
    {
    case 1: 
    case 4: 
    case 5: 
    default: 
      bool = onTouchUp(paramMotionEvent);
    }
    for (;;)
    {
      notifyTouchMode();
      return bool;
      bool = onTouchDown(paramMotionEvent);
      continue;
      bool = onTouchMove(paramMotionEvent);
      continue;
      bool = onTouchCancel(paramMotionEvent);
      continue;
      bool = onTouchPointerUp(paramMotionEvent);
    }
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {}
  
  void rememberSyncState()
  {
    View localView;
    ListAdapter localListAdapter;
    if (getChildCount() > 0)
    {
      this.mNeedSync = true;
      this.mSyncHeight = getHeight();
      localView = getChildAt(0);
      localListAdapter = getAdapter();
      if ((this.mFirstPosition < 0) || (this.mFirstPosition >= localListAdapter.getCount())) {
        break label87;
      }
    }
    label87:
    for (this.mSyncRowId = localListAdapter.getItemId(this.mFirstPosition);; this.mSyncRowId = -1L)
    {
      if (localView != null) {
        this.mSpecificTop = localView.getTop();
      }
      this.mSyncPosition = this.mFirstPosition;
      return;
    }
  }
  
  public boolean removeFooterView(View paramView)
  {
    if (this.mFooterViewInfos.size() > 0)
    {
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (this.mAdapter != null)
      {
        bool1 = bool2;
        if (((HeaderViewListAdapter)this.mAdapter).removeFooter(paramView))
        {
          if (this.mObserver != null) {
            this.mObserver.onChanged();
          }
          bool1 = true;
        }
      }
      removeFixedViewInfo(paramView, this.mFooterViewInfos);
      return bool1;
    }
    return false;
  }
  
  public boolean removeHeaderView(View paramView)
  {
    if (this.mHeaderViewInfos.size() > 0)
    {
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (this.mAdapter != null)
      {
        bool1 = bool2;
        if (((HeaderViewListAdapter)this.mAdapter).removeHeader(paramView))
        {
          if (this.mObserver != null) {
            this.mObserver.onChanged();
          }
          bool1 = true;
        }
      }
      removeFixedViewInfo(paramView, this.mHeaderViewInfos);
      return bool1;
    }
    return false;
  }
  
  void reportScrollStateChange(int paramInt)
  {
    if (paramInt != this.mTouchMode)
    {
      this.mTouchMode = paramInt;
      if (this.mOnScrollListener != null) {
        this.mOnScrollListener.onScrollStateChanged(this, paramInt);
      }
    }
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    if (paramBoolean) {
      recycleVelocityTracker();
    }
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
  
  public void requestLayout()
  {
    if ((!this.mBlockLayoutRequests) && (!this.mInLayout)) {
      super.requestLayout();
    }
  }
  
  public void resetToTop() {}
  
  public void setAdapter(ListAdapter paramListAdapter)
  {
    if (this.mAdapter != null) {
      this.mAdapter.unregisterDataSetObserver(this.mObserver);
    }
    if ((this.mHeaderViewInfos.size() > 0) || (this.mFooterViewInfos.size() > 0))
    {
      this.mAdapter = new HeaderViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, paramListAdapter);
      this.mDataChanged = true;
      if (paramListAdapter == null) {
        break label121;
      }
    }
    label121:
    for (int i = paramListAdapter.getCount();; i = 0)
    {
      this.mItemCount = i;
      if (paramListAdapter != null)
      {
        paramListAdapter.registerDataSetObserver(this.mObserver);
        this.mRecycleBin.setViewTypeCount(paramListAdapter.getViewTypeCount());
      }
      requestLayout();
      return;
      this.mAdapter = paramListAdapter;
      break;
    }
  }
  
  public void setClipToPadding(boolean paramBoolean)
  {
    super.setClipToPadding(paramBoolean);
    this.mClipToPadding = paramBoolean;
  }
  
  public void setOnScrollListener(AbsListView.OnScrollListener paramOnScrollListener)
  {
    super.setOnScrollListener(paramOnScrollListener);
    this.mOnScrollListener = paramOnScrollListener;
  }
  
  public void setSelection(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.mLayoutMode = 2;
      this.mSpecificTop = getListPaddingTop();
      this.mFirstPosition = 0;
      if (this.mNeedSync)
      {
        this.mSyncPosition = paramInt;
        this.mSyncRowId = this.mAdapter.getItemId(paramInt);
      }
      requestLayout();
    }
  }
  
  class AdapterDataSetObserver
    extends DataSetObserver
  {
    private Parcelable mInstanceState = null;
    
    AdapterDataSetObserver() {}
    
    public void clearSavedState()
    {
      this.mInstanceState = null;
    }
    
    public void onChanged()
    {
      ExtendableListView.access$702(ExtendableListView.this, true);
      ExtendableListView.access$802(ExtendableListView.this, ExtendableListView.this.mItemCount);
      ExtendableListView.access$402(ExtendableListView.this, ExtendableListView.this.getAdapter().getCount());
      ExtendableListView.this.mRecycleBin.clearTransientStateViews();
      if ((ExtendableListView.this.getAdapter().hasStableIds()) && (this.mInstanceState != null) && (ExtendableListView.this.mOldItemCount == 0) && (ExtendableListView.this.mItemCount > 0))
      {
        ExtendableListView.this.onRestoreInstanceState(this.mInstanceState);
        this.mInstanceState = null;
      }
      for (;;)
      {
        ExtendableListView.this.requestLayout();
        return;
        ExtendableListView.this.rememberSyncState();
      }
    }
    
    public void onInvalidated()
    {
      ExtendableListView.access$702(ExtendableListView.this, true);
      if (ExtendableListView.this.getAdapter().hasStableIds()) {
        this.mInstanceState = ExtendableListView.this.onSaveInstanceState();
      }
      ExtendableListView.access$802(ExtendableListView.this, ExtendableListView.this.mItemCount);
      ExtendableListView.access$402(ExtendableListView.this, 0);
      ExtendableListView.this.mNeedSync = false;
      ExtendableListView.this.requestLayout();
    }
  }
  
  public class FixedViewInfo
  {
    public Object data;
    public boolean isSelectable;
    public View view;
    
    public FixedViewInfo() {}
  }
  
  private class FlingRunnable
    implements Runnable
  {
    private int mLastFlingY;
    private final Scroller mScroller = new Scroller(ExtendableListView.this.getContext());
    
    FlingRunnable() {}
    
    private void endFling()
    {
      this.mLastFlingY = 0;
      ExtendableListView.access$202(ExtendableListView.this, 0);
      ExtendableListView.this.reportScrollStateChange(0);
      ExtendableListView.this.removeCallbacks(this);
      this.mScroller.forceFinished(true);
    }
    
    public void run()
    {
      switch (ExtendableListView.this.mTouchMode)
      {
      default: 
        return;
      }
      if ((ExtendableListView.this.mItemCount == 0) || (ExtendableListView.this.getChildCount() == 0))
      {
        endFling();
        return;
      }
      Scroller localScroller = this.mScroller;
      boolean bool1 = localScroller.computeScrollOffset();
      int j = localScroller.getCurrY();
      int i = this.mLastFlingY - j;
      if (i > 0) {
        ExtendableListView.access$502(ExtendableListView.this, ExtendableListView.this.mFirstPosition);
      }
      for (i = Math.min(ExtendableListView.this.getHeight() - ExtendableListView.this.getPaddingBottom() - ExtendableListView.this.getPaddingTop() - 1, i);; i = Math.max(-(ExtendableListView.this.getHeight() - ExtendableListView.this.getPaddingBottom() - ExtendableListView.this.getPaddingTop() - 1), i))
      {
        boolean bool2 = ExtendableListView.this.moveTheChildren(i, i);
        if ((!bool1) || (bool2)) {
          break;
        }
        ExtendableListView.this.invalidate();
        this.mLastFlingY = j;
        ExtendableListView.this.postOnAnimate(this);
        return;
        int k = ExtendableListView.this.getChildCount();
        ExtendableListView.access$502(ExtendableListView.this, ExtendableListView.this.mFirstPosition + (k - 1));
      }
      endFling();
    }
    
    void start(int paramInt)
    {
      if (paramInt < 0) {}
      for (int i = Integer.MAX_VALUE;; i = 0)
      {
        this.mLastFlingY = i;
        this.mScroller.forceFinished(true);
        this.mScroller.fling(0, i, 0, paramInt, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        ExtendableListView.access$202(ExtendableListView.this, 2);
        ExtendableListView.this.postOnAnimate(this);
        return;
      }
    }
    
    void startScroll(int paramInt1, int paramInt2)
    {
      if (paramInt1 < 0) {}
      for (int i = Integer.MAX_VALUE;; i = 0)
      {
        this.mLastFlingY = i;
        this.mScroller.startScroll(0, i, 0, paramInt1, paramInt2);
        ExtendableListView.access$202(ExtendableListView.this, 2);
        ExtendableListView.this.postOnAnimate(this);
        return;
      }
    }
  }
  
  public static class LayoutParams
    extends AbsListView.LayoutParams
  {
    long itemId = -1L;
    int position;
    boolean recycledHeaderFooter;
    int viewType;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(int paramInt1, int paramInt2, int paramInt3)
    {
      super(paramInt2);
      this.viewType = paramInt3;
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
  }
  
  public static class ListSavedState
    extends ClassLoaderSavedState
  {
    public static final Parcelable.Creator<ListSavedState> CREATOR = new Parcelable.Creator()
    {
      public ExtendableListView.ListSavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new ExtendableListView.ListSavedState(paramAnonymousParcel);
      }
      
      public ExtendableListView.ListSavedState[] newArray(int paramAnonymousInt)
      {
        return new ExtendableListView.ListSavedState[paramAnonymousInt];
      }
    };
    protected long firstId;
    protected int height;
    protected int position;
    protected long selectedId;
    protected int viewTop;
    
    public ListSavedState(Parcel paramParcel)
    {
      super();
      this.selectedId = paramParcel.readLong();
      this.firstId = paramParcel.readLong();
      this.viewTop = paramParcel.readInt();
      this.position = paramParcel.readInt();
      this.height = paramParcel.readInt();
    }
    
    public ListSavedState(Parcelable paramParcelable)
    {
      super(AbsListView.class.getClassLoader());
    }
    
    public String toString()
    {
      return "ExtendableListView.ListSavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewTop=" + this.viewTop + " position=" + this.position + " height=" + this.height + "}";
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeLong(this.selectedId);
      paramParcel.writeLong(this.firstId);
      paramParcel.writeInt(this.viewTop);
      paramParcel.writeInt(this.position);
      paramParcel.writeInt(this.height);
    }
  }
  
  private class PerformClick
    extends ExtendableListView.WindowRunnnable
    implements Runnable
  {
    int mClickMotionPosition;
    
    private PerformClick()
    {
      super(null);
    }
    
    public void run()
    {
      if (ExtendableListView.this.mDataChanged) {}
      ListAdapter localListAdapter;
      int i;
      View localView;
      do
      {
        do
        {
          return;
          localListAdapter = ExtendableListView.this.mAdapter;
          i = this.mClickMotionPosition;
        } while ((localListAdapter == null) || (ExtendableListView.this.mItemCount <= 0) || (i == -1) || (i >= localListAdapter.getCount()) || (!sameWindow()));
        localView = ExtendableListView.this.getChildAt(i);
      } while (localView == null);
      ExtendableListView.this.performItemClick(localView, ExtendableListView.this.mFirstPosition + i, localListAdapter.getItemId(i));
    }
  }
  
  class RecycleBin
  {
    private View[] mActiveViews = new View[0];
    private ArrayList<View> mCurrentScrap;
    private int mFirstActivePosition;
    private ArrayList<View>[] mScrapViews;
    private ArrayList<View> mSkippedScrap;
    private SparseArrayCompat<View> mTransientStateViews;
    private int mViewTypeCount;
    
    RecycleBin() {}
    
    private void pruneScrapViews()
    {
      int m = this.mActiveViews.length;
      int n = this.mViewTypeCount;
      ArrayList[] arrayOfArrayList = this.mScrapViews;
      int i = 0;
      int j;
      while (i < n)
      {
        ArrayList localArrayList = arrayOfArrayList[i];
        int i1 = localArrayList.size();
        int k = 0;
        j = i1 - 1;
        while (k < i1 - m)
        {
          ExtendableListView.this.removeDetachedView((View)localArrayList.remove(j), false);
          k += 1;
          j -= 1;
        }
        i += 1;
      }
      if (this.mTransientStateViews != null) {
        for (i = 0; i < this.mTransientStateViews.size(); i = j + 1)
        {
          j = i;
          if (!ViewCompat.hasTransientState((View)this.mTransientStateViews.valueAt(i)))
          {
            this.mTransientStateViews.removeAt(i);
            j = i - 1;
          }
        }
      }
    }
    
    void addScrapView(View paramView, int paramInt)
    {
      ExtendableListView.LayoutParams localLayoutParams = (ExtendableListView.LayoutParams)paramView.getLayoutParams();
      if (localLayoutParams == null) {}
      int i;
      boolean bool;
      do
      {
        return;
        localLayoutParams.position = paramInt;
        i = localLayoutParams.viewType;
        bool = ViewCompat.hasTransientState(paramView);
        if ((shouldRecycleViewType(i)) && (!bool)) {
          break;
        }
        if ((i != -2) || (bool))
        {
          if (this.mSkippedScrap == null) {
            this.mSkippedScrap = new ArrayList();
          }
          this.mSkippedScrap.add(paramView);
        }
      } while (!bool);
      if (this.mTransientStateViews == null) {
        this.mTransientStateViews = new SparseArrayCompat();
      }
      this.mTransientStateViews.put(paramInt, paramView);
      return;
      if (this.mViewTypeCount == 1)
      {
        this.mCurrentScrap.add(paramView);
        return;
      }
      this.mScrapViews[i].add(paramView);
    }
    
    void clear()
    {
      ArrayList localArrayList;
      int j;
      if (this.mViewTypeCount == 1)
      {
        localArrayList = this.mCurrentScrap;
        j = localArrayList.size();
        i = 0;
        while (i < j)
        {
          ExtendableListView.this.removeDetachedView((View)localArrayList.remove(j - 1 - i), false);
          i += 1;
        }
      }
      int k = this.mViewTypeCount;
      int i = 0;
      while (i < k)
      {
        localArrayList = this.mScrapViews[i];
        int m = localArrayList.size();
        j = 0;
        while (j < m)
        {
          ExtendableListView.this.removeDetachedView((View)localArrayList.remove(m - 1 - j), false);
          j += 1;
        }
        i += 1;
      }
      if (this.mTransientStateViews != null) {
        this.mTransientStateViews.clear();
      }
    }
    
    void clearTransientStateViews()
    {
      if (this.mTransientStateViews != null) {
        this.mTransientStateViews.clear();
      }
    }
    
    void fillActiveViews(int paramInt1, int paramInt2)
    {
      if (this.mActiveViews.length < paramInt1) {
        this.mActiveViews = new View[paramInt1];
      }
      this.mFirstActivePosition = paramInt2;
      View[] arrayOfView = this.mActiveViews;
      paramInt2 = 0;
      while (paramInt2 < paramInt1)
      {
        View localView = ExtendableListView.this.getChildAt(paramInt2);
        ExtendableListView.LayoutParams localLayoutParams = (ExtendableListView.LayoutParams)localView.getLayoutParams();
        if ((localLayoutParams != null) && (localLayoutParams.viewType != -2)) {
          arrayOfView[paramInt2] = localView;
        }
        paramInt2 += 1;
      }
    }
    
    View getActiveView(int paramInt)
    {
      paramInt -= this.mFirstActivePosition;
      View[] arrayOfView = this.mActiveViews;
      if ((paramInt >= 0) && (paramInt < arrayOfView.length))
      {
        View localView = arrayOfView[paramInt];
        arrayOfView[paramInt] = null;
        return localView;
      }
      return null;
    }
    
    View getScrapView(int paramInt)
    {
      if (this.mViewTypeCount == 1) {
        return ExtendableListView.retrieveFromScrap(this.mCurrentScrap, paramInt);
      }
      int i = ExtendableListView.this.mAdapter.getItemViewType(paramInt);
      if ((i >= 0) && (i < this.mScrapViews.length)) {
        return ExtendableListView.retrieveFromScrap(this.mScrapViews[i], paramInt);
      }
      return null;
    }
    
    View getTransientStateView(int paramInt)
    {
      if (this.mTransientStateViews == null) {}
      do
      {
        return null;
        paramInt = this.mTransientStateViews.indexOfKey(paramInt);
      } while (paramInt < 0);
      View localView = (View)this.mTransientStateViews.valueAt(paramInt);
      this.mTransientStateViews.removeAt(paramInt);
      return localView;
    }
    
    public void markChildrenDirty()
    {
      ArrayList localArrayList;
      int j;
      if (this.mViewTypeCount == 1)
      {
        localArrayList = this.mCurrentScrap;
        j = localArrayList.size();
        i = 0;
        while (i < j)
        {
          ((View)localArrayList.get(i)).forceLayout();
          i += 1;
        }
      }
      int k = this.mViewTypeCount;
      int i = 0;
      while (i < k)
      {
        localArrayList = this.mScrapViews[i];
        int m = localArrayList.size();
        j = 0;
        while (j < m)
        {
          ((View)localArrayList.get(j)).forceLayout();
          j += 1;
        }
        i += 1;
      }
      if (this.mTransientStateViews != null)
      {
        j = this.mTransientStateViews.size();
        i = 0;
        while (i < j)
        {
          ((View)this.mTransientStateViews.valueAt(i)).forceLayout();
          i += 1;
        }
      }
    }
    
    void removeSkippedScrap()
    {
      if (this.mSkippedScrap == null) {
        return;
      }
      int j = this.mSkippedScrap.size();
      int i = 0;
      while (i < j)
      {
        ExtendableListView.this.removeDetachedView((View)this.mSkippedScrap.get(i), false);
        i += 1;
      }
      this.mSkippedScrap.clear();
    }
    
    void scrapActiveViews()
    {
      int i = 1;
      View[] arrayOfView = this.mActiveViews;
      Object localObject1;
      int j;
      label28:
      View localView;
      int k;
      if (this.mViewTypeCount > 1)
      {
        localObject1 = this.mCurrentScrap;
        j = arrayOfView.length - 1;
        if (j < 0) {
          break label209;
        }
        localView = arrayOfView[j];
        localObject2 = localObject1;
        if (localView != null)
        {
          localObject2 = (ExtendableListView.LayoutParams)localView.getLayoutParams();
          arrayOfView[j] = null;
          boolean bool = ViewCompat.hasTransientState(localView);
          k = ((ExtendableListView.LayoutParams)localObject2).viewType;
          if ((shouldRecycleViewType(k)) && (!bool)) {
            break label171;
          }
          if ((k != -2) || (bool)) {
            ExtendableListView.this.removeDetachedView(localView, false);
          }
          localObject2 = localObject1;
          if (bool)
          {
            if (this.mTransientStateViews == null) {
              this.mTransientStateViews = new SparseArrayCompat();
            }
            this.mTransientStateViews.put(this.mFirstActivePosition + j, localView);
          }
        }
      }
      for (Object localObject2 = localObject1;; localObject2 = localObject1)
      {
        j -= 1;
        localObject1 = localObject2;
        break label28;
        i = 0;
        break;
        label171:
        if (i != 0) {
          localObject1 = this.mScrapViews[k];
        }
        ((ExtendableListView.LayoutParams)localObject2).position = (this.mFirstActivePosition + j);
        ((ArrayList)localObject1).add(localView);
      }
      label209:
      pruneScrapViews();
    }
    
    void setCacheColorHint(int paramInt)
    {
      if (this.mViewTypeCount == 1)
      {
        localObject1 = this.mCurrentScrap;
        j = ((ArrayList)localObject1).size();
        i = 0;
        while (i < j)
        {
          ((View)((ArrayList)localObject1).get(i)).setDrawingCacheBackgroundColor(paramInt);
          i += 1;
        }
      }
      int k = this.mViewTypeCount;
      int i = 0;
      while (i < k)
      {
        localObject1 = this.mScrapViews[i];
        int m = ((ArrayList)localObject1).size();
        j = 0;
        while (j < m)
        {
          ((View)((ArrayList)localObject1).get(j)).setDrawingCacheBackgroundColor(paramInt);
          j += 1;
        }
        i += 1;
      }
      Object localObject1 = this.mActiveViews;
      int j = localObject1.length;
      i = 0;
      while (i < j)
      {
        Object localObject2 = localObject1[i];
        if (localObject2 != null) {
          ((View)localObject2).setDrawingCacheBackgroundColor(paramInt);
        }
        i += 1;
      }
    }
    
    public void setViewTypeCount(int paramInt)
    {
      if (paramInt < 1) {
        throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
      }
      ArrayList[] arrayOfArrayList = new ArrayList[paramInt];
      int i = 0;
      while (i < paramInt)
      {
        arrayOfArrayList[i] = new ArrayList();
        i += 1;
      }
      this.mViewTypeCount = paramInt;
      this.mCurrentScrap = arrayOfArrayList[0];
      this.mScrapViews = arrayOfArrayList;
    }
    
    public boolean shouldRecycleViewType(int paramInt)
    {
      return paramInt >= 0;
    }
  }
  
  private class WindowRunnnable
  {
    private int mOriginalAttachCount;
    
    private WindowRunnnable() {}
    
    public void rememberWindowAttachCount()
    {
      this.mOriginalAttachCount = ExtendableListView.this.getWindowAttachCount();
    }
    
    public boolean sameWindow()
    {
      return (ExtendableListView.this.hasWindowFocus()) && (ExtendableListView.this.getWindowAttachCount() == this.mOriginalAttachCount);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/grid/ExtendableListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */