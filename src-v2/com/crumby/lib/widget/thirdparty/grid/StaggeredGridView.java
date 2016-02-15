/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ListAdapter
 */
package com.crumby.lib.widget.thirdparty.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import com.crumby.R;
import com.crumby.lib.widget.thirdparty.grid.ExtendableListView;
import java.util.Arrays;

public class StaggeredGridView
extends ExtendableListView {
    private static final boolean DBG = false;
    private static final int DEFAULT_COLUMNS_LANDSCAPE = 3;
    private static final int DEFAUlT_COLUMNS_PORTRAIT = 2;
    private static final String TAG = "StaggeredGridView";
    private int[] mColumnBottoms;
    private int mColumnCount;
    private int mColumnCountLandscape = 3;
    private int mColumnCountPortrait = 2;
    private int[] mColumnLefts;
    private int[] mColumnTops;
    private int mColumnWidth;
    private int mDistanceToTop;
    private int mGridPaddingBottom;
    private int mGridPaddingLeft;
    private int mGridPaddingRight;
    private int mGridPaddingTop;
    private int mItemMargin;
    private boolean mNeedSync;
    private SparseArray<GridItemRecord> mPositionData;

    public StaggeredGridView(Context context) {
        this(context, null);
    }

    public StaggeredGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StaggeredGridView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        if (attributeSet != null) {
            context = context.obtainStyledAttributes(attributeSet, R.styleable.StaggeredGridView, n2, 0);
            this.mColumnCountPortrait = context.getInteger(0, 2);
            this.mColumnCountLandscape = context.getInteger(1, 3);
            this.mItemMargin = context.getDimensionPixelSize(2, 0);
            this.mGridPaddingLeft = context.getDimensionPixelSize(3, 0);
            this.mGridPaddingRight = context.getDimensionPixelSize(4, 0);
            this.mGridPaddingTop = context.getDimensionPixelSize(5, 0);
            this.mGridPaddingBottom = context.getDimensionPixelSize(6, 0);
            context.recycle();
        }
        this.mColumnCount = 0;
        this.mColumnTops = new int[0];
        this.mColumnBottoms = new int[0];
        this.mColumnLefts = new int[0];
        this.mPositionData = new SparseArray();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void alignTops() {
        if (this.mFirstPosition != this.getHeaderViewsCount()) return;
        int[] arrn = this.getHighestNonHeaderTops();
        int n2 = 1;
        int n3 = -1;
        int n4 = Integer.MAX_VALUE;
        int n5 = 0;
        while (n5 < arrn.length) {
            int n6 = n2;
            if (n2 != 0) {
                n6 = n2;
                if (n5 > 0) {
                    n6 = n2;
                    if (arrn[n5] != n4) {
                        n6 = 0;
                    }
                }
            }
            n2 = n4;
            if (arrn[n5] < n4) {
                n2 = arrn[n5];
                n3 = n5;
            }
            ++n5;
            n4 = n2;
            n2 = n6;
        }
    }

    private int calculateColumnLeft(int n2) {
        return this.getRowPaddingLeft() + this.mItemMargin + (this.mItemMargin + this.mColumnWidth) * n2;
    }

    private int calculateColumnWidth(int n2) {
        return (n2 - (this.getRowPaddingLeft() + this.getRowPaddingRight()) - this.mItemMargin * (this.mColumnCount + 1)) / this.mColumnCount;
    }

    private int getChildBottomMargin() {
        return this.mItemMargin;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getChildColumn(int n2, boolean bl2) {
        int n3 = this.getPositionColumn(n2);
        int n4 = this.mColumnCount;
        if (n3 >= 0) {
            n2 = n3;
            if (n3 < n4) return n2;
        }
        if (!bl2) return this.getLowestPositionedTopColumn();
        return this.getHighestPositionedBottomColumn();
    }

    private int getChildHeight(View view) {
        return view.getMeasuredHeight();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getChildTopMargin(int n2) {
        int n3 = 0;
        if (n2 >= this.getHeaderViewsCount() + this.mColumnCount) return n3;
        n2 = 1;
        if (n2 == 0) return n3;
        return this.mItemMargin;
    }

    private int[] getHighestNonHeaderTops() {
        int[] arrn = new int[this.mColumnCount];
        int n2 = this.getChildCount();
        if (n2 > 0) {
            for (int i2 = 0; i2 < n2; ++i2) {
                View view = this.getChildAt(i2);
                if (view == null || view.getLayoutParams() == null || !(view.getLayoutParams() instanceof GridLayoutParams)) continue;
                GridLayoutParams gridLayoutParams = (GridLayoutParams)view.getLayoutParams();
                if (gridLayoutParams.viewType == -2 || view.getTop() >= arrn[gridLayoutParams.column]) continue;
                arrn[gridLayoutParams.column] = view.getTop();
            }
        }
        return arrn;
    }

    private int getHighestPositionedBottom() {
        int n2 = this.getHighestPositionedBottomColumn();
        return this.mColumnBottoms[n2];
    }

    private int getHighestPositionedBottomColumn() {
        int n2 = 0;
        int n3 = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < this.mColumnCount; ++i2) {
            int n4 = this.mColumnBottoms[i2];
            int n5 = n3;
            if (n4 < n3) {
                n5 = n4;
                n2 = i2;
            }
            n3 = n5;
        }
        return n2;
    }

    private int getHighestPositionedTop() {
        int n2 = this.getHighestPositionedTopColumn();
        return this.mColumnTops[n2];
    }

    private int getHighestPositionedTopColumn() {
        int n2 = 0;
        int n3 = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < this.mColumnCount; ++i2) {
            int n4 = this.mColumnTops[i2];
            int n5 = n3;
            if (n4 < n3) {
                n5 = n4;
                n2 = i2;
            }
            n3 = n5;
        }
        return n2;
    }

    private int getLowestPositionedBottom() {
        int n2 = this.getLowestPositionedBottomColumn();
        return this.mColumnBottoms[n2];
    }

    private int getLowestPositionedBottomColumn() {
        int n2 = 0;
        int n3 = Integer.MIN_VALUE;
        for (int i2 = 0; i2 < this.mColumnCount; ++i2) {
            int n4 = this.mColumnBottoms[i2];
            int n5 = n3;
            if (n4 > n3) {
                n5 = n4;
                n2 = i2;
            }
            n3 = n5;
        }
        return n2;
    }

    private int getLowestPositionedTop() {
        int n2 = this.getLowestPositionedTopColumn();
        return this.mColumnTops[n2];
    }

    private int getLowestPositionedTopColumn() {
        int n2 = 0;
        int n3 = Integer.MIN_VALUE;
        for (int i2 = 0; i2 < this.mColumnCount; ++i2) {
            int n4 = this.mColumnTops[i2];
            int n5 = n3;
            if (n4 > n3) {
                n5 = n4;
                n2 = i2;
            }
            n3 = n5;
        }
        return n2;
    }

    private GridItemRecord getOrCreateRecord(int n2) {
        GridItemRecord gridItemRecord;
        GridItemRecord gridItemRecord2 = gridItemRecord = (GridItemRecord)this.mPositionData.get(n2, (Object)null);
        if (gridItemRecord == null) {
            gridItemRecord2 = new GridItemRecord();
            this.mPositionData.append(n2, (Object)gridItemRecord2);
        }
        return gridItemRecord2;
    }

    private int getPositionColumn(int n2) {
        GridItemRecord gridItemRecord = (GridItemRecord)this.mPositionData.get(n2, (Object)null);
        if (gridItemRecord != null) {
            return gridItemRecord.column;
        }
        return -1;
    }

    private boolean isHeaderOrFooter(int n2) {
        if (this.mAdapter.getItemViewType(n2) == -2) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void layoutGridChild(View view, int n2, boolean bl2, int n3, int n4) {
        int n5;
        int n6 = this.getPositionColumn(n2);
        int n7 = this.getChildTopMargin(n2);
        int n8 = this.getChildBottomMargin();
        int n9 = n7 + n8;
        if (bl2) {
            n5 = this.mColumnBottoms[n6];
            n2 = n5 + (this.getChildHeight(view) + n9);
        } else {
            n2 = this.mColumnTops[n6];
            n5 = n2 - (this.getChildHeight(view) + n9);
        }
        ((GridLayoutParams)view.getLayoutParams()).column = n6;
        this.updateColumnBottomIfNeeded(n6, n2);
        this.updateColumnTopIfNeeded(n6, n5);
        view.layout(n3, n5 + n7, n4, n2 - n8);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void layoutGridHeaderFooter(View view, int n2, boolean bl2, int n3, int n4, int n5, int n6) {
        if (bl2) {
            n4 = this.getLowestPositionedBottom();
            n6 = n4 + this.getChildHeight(view);
        } else {
            n6 = this.getHighestPositionedTop();
            n4 = n6 - this.getChildHeight(view);
        }
        int n7 = 0;
        do {
            if (n7 >= this.mColumnCount) {
                super.onLayoutChild(view, n2, bl2, n3, n4, n5, n6);
                return;
            }
            this.updateColumnTopIfNeeded(n7, n4);
            this.updateColumnBottomIfNeeded(n7, n6);
            ++n7;
        } while (true);
    }

    private void offsetAllColumnsTopAndBottom(int n2) {
        if (n2 != 0) {
            for (int i2 = 0; i2 < this.mColumnCount; ++i2) {
                this.offsetColumnTopAndBottom(n2, i2);
            }
        }
    }

    private void offsetColumnTopAndBottom(int n2, int n3) {
        if (n2 != 0) {
            int[] arrn = this.mColumnTops;
            arrn[n3] = arrn[n3] + n2;
            arrn = this.mColumnBottoms;
            arrn[n3] = arrn[n3] + n2;
        }
    }

    private void offsetDistanceToTop(int n2) {
        this.mDistanceToTop += n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void offsetGridChild(View view, int n2, boolean bl2, int n3, int n4) {
        int n5;
        int n6 = this.getPositionColumn(n2);
        int n7 = this.getChildTopMargin(n2);
        int n8 = n7 + this.getChildBottomMargin();
        if (bl2) {
            n5 = this.mColumnBottoms[n6];
            n4 = n5 + (this.getChildHeight(view) + n8);
        } else {
            n4 = this.mColumnTops[n6];
            n5 = n4 - (this.getChildHeight(view) + n8);
        }
        ((GridLayoutParams)view.getLayoutParams()).column = n6;
        this.updateColumnBottomIfNeeded(n6, n4);
        this.updateColumnTopIfNeeded(n6, n5);
        super.onOffsetChild(view, n2, bl2, n3, n5 + n7);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void offsetGridHeaderFooter(View view, int n2, boolean bl2, int n3, int n4) {
        int n5;
        if (bl2) {
            n4 = this.getLowestPositionedBottom();
            n5 = n4 + this.getChildHeight(view);
        } else {
            n5 = this.getHighestPositionedTop();
            n4 = n5 - this.getChildHeight(view);
        }
        int n6 = 0;
        do {
            if (n6 >= this.mColumnCount) {
                super.onOffsetChild(view, n2, bl2, n3, n4);
                return;
            }
            this.updateColumnTopIfNeeded(n6, n4);
            this.updateColumnBottomIfNeeded(n6, n5);
            ++n6;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onColumnSync() {
        GridItemRecord gridItemRecord;
        int n2 = Math.min(this.mSyncPosition, this.getCount() - 1);
        SparseArray sparseArray = new SparseArray(n2);
        int n3 = 0;
        do {
            if (n3 >= n2 || (gridItemRecord = (GridItemRecord)this.mPositionData.get(n3)) == null) break;
            Log.d((String)"StaggeredGridView", (String)("onColumnSync:" + n3 + " ratio:" + gridItemRecord.heightRatio));
            sparseArray.append(n3, (Object)gridItemRecord.heightRatio);
            ++n3;
        } while (true);
        this.mPositionData.clear();
        n3 = 0;
        do {
            int n4;
            int n5;
            if (n3 >= n2) {
                n3 = this.getHighestPositionedBottomColumn();
                this.setPositionColumn(n2, n3);
                n3 = this.mColumnBottoms[n3];
                this.offsetAllColumnsTopAndBottom(- n3 + this.mSpecificTop);
                this.mDistanceToTop = - n3;
                System.arraycopy(this.mColumnBottoms, 0, this.mColumnTops, 0, this.mColumnCount);
                return;
            }
            gridItemRecord = this.getOrCreateRecord(n3);
            double d2 = (Double)sparseArray.get(n3);
            int n6 = (int)((double)this.mColumnWidth * d2);
            gridItemRecord.heightRatio = d2;
            if (this.isHeaderOrFooter(n3)) {
                n4 = this.getLowestPositionedBottom();
                for (n5 = 0; n5 < this.mColumnCount; ++n5) {
                    this.mColumnTops[n5] = n4;
                    this.mColumnBottoms[n5] = n4 + n6;
                }
            } else {
                n5 = this.getHighestPositionedBottomColumn();
                n4 = this.mColumnBottoms[n5];
                int n7 = this.getChildTopMargin(n3);
                int n8 = this.getChildBottomMargin();
                this.mColumnTops[n5] = n4;
                this.mColumnBottoms[n5] = n4 + n6 + n7 + n8;
                gridItemRecord.column = n5;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void preLayoutChildren() {
        if (!this.mNeedSync) {
            Arrays.fill(this.mColumnBottoms, 0);
        } else {
            this.mNeedSync = false;
        }
        System.arraycopy(this.mColumnTops, 0, this.mColumnBottoms, 0, this.mColumnCount);
    }

    private void setPositionColumn(int n2, int n3) {
        this.getOrCreateRecord((int)n2).column = n3;
    }

    private void setPositionHeightRatio(int n2, int n3) {
        this.getOrCreateRecord((int)n2).heightRatio = (double)n3 / (double)this.mColumnWidth;
    }

    private void setPositionIsHeaderFooter(int n2) {
        this.getOrCreateRecord((int)n2).isHeaderFooter = true;
    }

    private void updateColumnBottomIfNeeded(int n2, int n3) {
        if (n3 > this.mColumnBottoms[n2]) {
            this.mColumnBottoms[n2] = n3;
        }
    }

    private void updateColumnTopIfNeeded(int n2, int n3) {
        if (n3 < this.mColumnTops[n2]) {
            this.mColumnTops[n2] = n3;
        }
    }

    @Override
    protected void adjustViewsAfterFillGap(boolean bl2) {
        super.adjustViewsAfterFillGap(bl2);
        if (!bl2) {
            this.alignTops();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected ExtendableListView.LayoutParams generateChildLayoutParams(View object) {
        Object object2 = null;
        ViewGroup.LayoutParams layoutParams = object.getLayoutParams();
        object = object2;
        if (layoutParams != null) {
            object = layoutParams instanceof GridLayoutParams ? (GridLayoutParams)layoutParams : new GridLayoutParams(layoutParams);
        }
        object2 = object;
        if (object != null) return object2;
        return new GridLayoutParams(this.mColumnWidth, -2);
    }

    @Override
    protected int getChildBottom(int n2) {
        if (this.isHeaderOrFooter(n2)) {
            return super.getChildBottom(n2);
        }
        if ((n2 = this.getPositionColumn(n2)) == -1) {
            return this.getLowestPositionedTop();
        }
        return this.mColumnTops[n2];
    }

    @Override
    protected int getChildLeft(int n2) {
        if (this.isHeaderOrFooter(n2)) {
            return super.getChildLeft(n2);
        }
        n2 = this.getPositionColumn(n2);
        return this.mColumnLefts[n2];
    }

    @Override
    protected int getChildTop(int n2) {
        if (this.isHeaderOrFooter(n2)) {
            return super.getChildTop(n2);
        }
        if ((n2 = this.getPositionColumn(n2)) == -1) {
            return this.getHighestPositionedBottom();
        }
        return this.mColumnBottoms[n2];
    }

    public int getColumnWidth() {
        return this.mColumnWidth;
    }

    public int getDistanceToTop() {
        return this.mDistanceToTop;
    }

    @Override
    protected int getFirstChildTop() {
        if (this.isHeaderOrFooter(this.mFirstPosition)) {
            return super.getFirstChildTop();
        }
        return this.getLowestPositionedTop();
    }

    @Override
    protected int getHighestChildTop() {
        if (this.isHeaderOrFooter(this.mFirstPosition)) {
            return super.getHighestChildTop();
        }
        return this.getHighestPositionedTop();
    }

    @Override
    protected int getLastChildBottom() {
        if (this.isHeaderOrFooter(this.mFirstPosition + (this.getChildCount() - 1))) {
            return super.getLastChildBottom();
        }
        return this.getHighestPositionedBottom();
    }

    @Override
    protected int getLowestChildBottom() {
        if (this.isHeaderOrFooter(this.mFirstPosition + (this.getChildCount() - 1))) {
            return super.getLowestChildBottom();
        }
        return this.getLowestPositionedBottom();
    }

    @Override
    protected int getNextChildDownsTop(int n2) {
        if (this.isHeaderOrFooter(n2)) {
            return super.getNextChildDownsTop(n2);
        }
        return this.getHighestPositionedBottom();
    }

    @Override
    protected int getNextChildUpsBottom(int n2) {
        if (this.isHeaderOrFooter(n2)) {
            return super.getNextChildUpsBottom(n2);
        }
        return this.getLowestPositionedTop();
    }

    public int getRowPaddingBottom() {
        return this.getListPaddingBottom() + this.mGridPaddingBottom;
    }

    public int getRowPaddingLeft() {
        return this.getListPaddingLeft() + this.mGridPaddingLeft;
    }

    public int getRowPaddingRight() {
        return this.getListPaddingRight() + this.mGridPaddingRight;
    }

    public int getRowPaddingTop() {
        return this.getListPaddingTop() + this.mGridPaddingTop;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected boolean hasSpaceUp() {
        boolean bl2 = false;
        int n2 = this.mClipToPadding ? this.getRowPaddingTop() : 0;
        if (this.getLowestPositionedTop() <= n2) return bl2;
        return true;
    }

    @Override
    protected void layoutChildren() {
        this.preLayoutChildren();
        super.layoutChildren();
    }

    @Override
    protected void offsetChildrenTopAndBottom(int n2) {
        super.offsetChildrenTopAndBottom(n2);
        this.offsetAllColumnsTopAndBottom(n2);
        this.offsetDistanceToTop(n2);
    }

    protected void offsetChildrenTopAndBottom(int n2, int n3) {
        int n4 = this.getChildCount();
        for (int i2 = 0; i2 < n4; ++i2) {
            View view = this.getChildAt(i2);
            if (view == null || view.getLayoutParams() == null || !(view.getLayoutParams() instanceof GridLayoutParams) || ((GridLayoutParams)view.getLayoutParams()).column != n3) continue;
            view.offsetTopAndBottom(n2);
        }
        this.offsetColumnTopAndBottom(n2, n3);
    }

    @Override
    protected void onChildCreated(int n2, boolean bl2) {
        super.onChildCreated(n2, bl2);
        if (!this.isHeaderOrFooter(n2)) {
            this.setPositionColumn(n2, this.getChildColumn(n2, bl2));
            return;
        }
        this.setPositionIsHeaderFooter(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onChildrenDetached(int n2, int n3) {
        super.onChildrenDetached(n2, n3);
        Arrays.fill(this.mColumnTops, Integer.MAX_VALUE);
        Arrays.fill(this.mColumnBottoms, 0);
        n2 = 0;
        while (n2 < this.getChildCount()) {
            View view = this.getChildAt(n2);
            if (view != null) {
                int n4;
                int n5;
                ExtendableListView.LayoutParams layoutParams = (ExtendableListView.LayoutParams)view.getLayoutParams();
                if (layoutParams.viewType != -2 && layoutParams instanceof GridLayoutParams) {
                    layoutParams = (GridLayoutParams)layoutParams;
                    n3 = layoutParams.column;
                    n4 = layoutParams.position;
                    n5 = view.getTop();
                    if (n5 < this.mColumnTops[n3]) {
                        this.mColumnTops[n3] = n5 - this.getChildTopMargin(n4);
                    }
                    if ((n4 = view.getBottom()) > this.mColumnBottoms[n3]) {
                        this.mColumnBottoms[n3] = this.getChildBottomMargin() + n4;
                    }
                } else {
                    n4 = view.getTop();
                    n5 = view.getBottom();
                    for (n3 = 0; n3 < this.mColumnCount; ++n3) {
                        if (n4 < this.mColumnTops[n3]) {
                            this.mColumnTops[n3] = n4;
                        }
                        if (n5 <= this.mColumnBottoms[n3]) continue;
                        this.mColumnBottoms[n3] = n5;
                    }
                }
            }
            ++n2;
        }
    }

    @Override
    protected void onLayoutChild(View view, int n2, boolean bl2, int n3, int n4, int n5, int n6) {
        if (this.isHeaderOrFooter(n2)) {
            this.layoutGridHeaderFooter(view, n2, bl2, n3, n4, n5, n6);
            return;
        }
        this.layoutGridChild(view, n2, bl2, n3, n5);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        if (this.mColumnCount <= 0) {
            n2 = this.getMeasuredWidth() > this.getMeasuredHeight() ? 1 : 0;
            n2 = n2 != 0 ? this.mColumnCountLandscape : this.mColumnCountPortrait;
            this.mColumnCount = n2;
        }
        this.mColumnWidth = this.calculateColumnWidth(this.getMeasuredWidth());
        if (this.mColumnTops == null || this.mColumnTops.length != this.mColumnCount) {
            this.mColumnTops = new int[this.mColumnCount];
        }
        if (this.mColumnBottoms == null || this.mColumnBottoms.length != this.mColumnCount) {
            this.mColumnBottoms = new int[this.mColumnCount];
        }
        if (this.mColumnLefts == null || this.mColumnLefts.length != this.mColumnCount) {
            this.mColumnLefts = new int[this.mColumnCount];
        }
        n2 = 0;
        while (n2 < this.mColumnCount) {
            this.mColumnLefts[n2] = this.calculateColumnLeft(n2);
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onMeasureChild(View view, ExtendableListView.LayoutParams layoutParams) {
        int n2 = layoutParams.viewType;
        int n3 = layoutParams.position;
        if (n2 == -2 || n2 == -1) {
            super.onMeasureChild(view, layoutParams);
        } else {
            int n4 = View.MeasureSpec.makeMeasureSpec((int)this.mColumnWidth, (int)1073741824);
            n2 = layoutParams.height > 0 ? View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)-2, (int)0);
            view.measure(n4, n2);
        }
        this.setPositionHeightRatio(n3, this.getChildHeight(view));
    }

    @Override
    protected void onOffsetChild(View view, int n2, boolean bl2, int n3, int n4) {
        if (this.isHeaderOrFooter(n2)) {
            this.offsetGridHeaderFooter(view, n2, bl2, n3, n4);
            return;
        }
        this.offsetGridChild(view, n2, bl2, n3, n4);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (GridListSavedState)parcelable;
        this.mColumnCount = parcelable.columnCount;
        this.mColumnTops = parcelable.columnTops;
        this.mPositionData = parcelable.positionData;
        this.mNeedSync = true;
        super.onRestoreInstanceState(parcelable);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Parcelable onSaveInstanceState() {
        int n2 = 0;
        ExtendableListView.ListSavedState listSavedState = (ExtendableListView.ListSavedState)super.onSaveInstanceState();
        GridListSavedState gridListSavedState = new GridListSavedState(listSavedState.getSuperState());
        gridListSavedState.selectedId = listSavedState.selectedId;
        gridListSavedState.firstId = listSavedState.firstId;
        gridListSavedState.viewTop = listSavedState.viewTop;
        gridListSavedState.position = listSavedState.position;
        gridListSavedState.height = listSavedState.height;
        int n3 = this.getChildCount() > 0 && this.getCount() > 0 ? 1 : 0;
        if (n3 != 0 && this.mFirstPosition > 0) {
            gridListSavedState.columnCount = this.mColumnCount;
            gridListSavedState.columnTops = this.mColumnTops;
            gridListSavedState.positionData = this.mPositionData;
            return gridListSavedState;
        }
        n3 = n2;
        if (this.mColumnCount >= 0) {
            n3 = this.mColumnCount;
        }
        gridListSavedState.columnCount = n3;
        gridListSavedState.columnTops = new int[gridListSavedState.columnCount];
        gridListSavedState.positionData = new SparseArray();
        return gridListSavedState;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        n3 = n2 > n3 ? 1 : 0;
        n3 = n3 != 0 ? this.mColumnCountLandscape : this.mColumnCountPortrait;
        if (this.mColumnCount != n3) {
            this.mColumnCount = n3;
            this.mColumnWidth = this.calculateColumnWidth(n2);
            this.mColumnTops = new int[this.mColumnCount];
            this.mColumnBottoms = new int[this.mColumnCount];
            this.mColumnLefts = new int[this.mColumnCount];
            this.mDistanceToTop = 0;
            for (n2 = 0; n2 < this.mColumnCount; ++n2) {
                this.mColumnLefts[n2] = this.calculateColumnLeft(n2);
            }
            if (this.getCount() > 0 && this.mPositionData.size() > 0) {
                this.onColumnSync();
            }
            this.requestLayout();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void resetToTop() {
        if (this.mColumnCount > 0) {
            if (this.mColumnTops == null) {
                this.mColumnTops = new int[this.mColumnCount];
            } else {
                Arrays.fill(this.mColumnTops, 0);
            }
            if (this.mColumnBottoms == null) {
                this.mColumnBottoms = new int[this.mColumnCount];
            } else {
                Arrays.fill(this.mColumnBottoms, 0);
            }
            this.mPositionData.clear();
            this.mNeedSync = false;
            this.mDistanceToTop = 0;
            this.setSelection(0);
        }
    }

    public void setGridPadding(int n2, int n3, int n4, int n5) {
        this.mGridPaddingLeft = n2;
        this.mGridPaddingTop = n3;
        this.mGridPaddingRight = n4;
        this.mGridPaddingBottom = n5;
    }

    static class GridItemRecord
    implements Parcelable {
        public static final Parcelable.Creator<GridItemRecord> CREATOR = new Parcelable.Creator<GridItemRecord>(){

            public GridItemRecord createFromParcel(Parcel parcel) {
                return new GridItemRecord(parcel);
            }

            public GridItemRecord[] newArray(int n2) {
                return new GridItemRecord[n2];
            }
        };
        int column;
        double heightRatio;
        boolean isHeaderFooter;

        GridItemRecord() {
        }

        /*
         * Enabled aggressive block sorting
         */
        private GridItemRecord(Parcel parcel) {
            boolean bl2 = true;
            this.column = parcel.readInt();
            this.heightRatio = parcel.readDouble();
            if (parcel.readByte() != 1) {
                bl2 = false;
            }
            this.isHeaderFooter = bl2;
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "GridItemRecord.ListSavedState{" + Integer.toHexString(System.identityHashCode(this)) + " column:" + this.column + " heightRatio:" + this.heightRatio + " isHeaderFooter:" + this.isHeaderFooter + "}";
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n2) {
            parcel.writeInt(this.column);
            parcel.writeDouble(this.heightRatio);
            n2 = this.isHeaderFooter ? 1 : 0;
            parcel.writeByte((byte)n2);
        }

    }

    public static class GridLayoutParams
    extends ExtendableListView.LayoutParams {
        int column;

        public GridLayoutParams(int n2, int n3) {
            super(n2, n3);
            this.enforceStaggeredLayout();
        }

        public GridLayoutParams(int n2, int n3, int n4) {
            super(n2, n3);
            this.enforceStaggeredLayout();
        }

        public GridLayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.enforceStaggeredLayout();
        }

        public GridLayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.enforceStaggeredLayout();
        }

        private void enforceStaggeredLayout() {
            if (this.width != -1) {
                this.width = -1;
            }
            if (this.height == -1) {
                this.height = -2;
            }
        }
    }

    public static class GridListSavedState
    extends ExtendableListView.ListSavedState {
        public static final Parcelable.Creator<GridListSavedState> CREATOR = new Parcelable.Creator<GridListSavedState>(){

            public GridListSavedState createFromParcel(Parcel parcel) {
                return new GridListSavedState(parcel);
            }

            public GridListSavedState[] newArray(int n2) {
                return new GridListSavedState[n2];
            }
        };
        int columnCount;
        int[] columnTops;
        SparseArray positionData;

        /*
         * Enabled aggressive block sorting
         */
        public GridListSavedState(Parcel parcel) {
            super(parcel);
            this.columnCount = parcel.readInt();
            int n2 = this.columnCount >= 0 ? this.columnCount : 0;
            this.columnTops = new int[n2];
            parcel.readIntArray(this.columnTops);
            this.positionData = parcel.readSparseArray(GridItemRecord.class.getClassLoader());
        }

        public GridListSavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public String toString() {
            return "StaggeredGridView.GridListSavedState{" + Integer.toHexString(System.identityHashCode(this)) + "}";
        }

        @Override
        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.columnCount);
            parcel.writeIntArray(this.columnTops);
            parcel.writeSparseArray(this.positionData);
        }

    }

}

