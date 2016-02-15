package com.crumby.lib.widget.thirdparty.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.lib.widget.thirdparty.grid.ExtendableListView.LayoutParams;
import com.crumby.lib.widget.thirdparty.grid.ExtendableListView.ListSavedState;
import java.util.Arrays;

public class StaggeredGridView extends ExtendableListView {
    private static final boolean DBG = false;
    private static final int DEFAULT_COLUMNS_LANDSCAPE = 3;
    private static final int DEFAUlT_COLUMNS_PORTRAIT = 2;
    private static final String TAG = "StaggeredGridView";
    private int[] mColumnBottoms;
    private int mColumnCount;
    private int mColumnCountLandscape;
    private int mColumnCountPortrait;
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

    static class GridItemRecord implements Parcelable {
        public static final Creator<GridItemRecord> CREATOR;
        int column;
        double heightRatio;
        boolean isHeaderFooter;

        /* renamed from: com.crumby.lib.widget.thirdparty.grid.StaggeredGridView.GridItemRecord.1 */
        static class C01691 implements Creator<GridItemRecord> {
            C01691() {
            }

            public GridItemRecord createFromParcel(Parcel in) {
                return new GridItemRecord(null);
            }

            public GridItemRecord[] newArray(int size) {
                return new GridItemRecord[size];
            }
        }

        GridItemRecord() {
        }

        private GridItemRecord(Parcel in) {
            boolean z = true;
            this.column = in.readInt();
            this.heightRatio = in.readDouble();
            if (in.readByte() != (byte) 1) {
                z = StaggeredGridView.DBG;
            }
            this.isHeaderFooter = z;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.column);
            out.writeDouble(this.heightRatio);
            out.writeByte((byte) (this.isHeaderFooter ? 1 : 0));
        }

        public String toString() {
            return "GridItemRecord.ListSavedState{" + Integer.toHexString(System.identityHashCode(this)) + " column:" + this.column + " heightRatio:" + this.heightRatio + " isHeaderFooter:" + this.isHeaderFooter + "}";
        }

        static {
            CREATOR = new C01691();
        }
    }

    public static class GridLayoutParams extends LayoutParams {
        int column;

        public GridLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            enforceStaggeredLayout();
        }

        public GridLayoutParams(int w, int h) {
            super(w, h);
            enforceStaggeredLayout();
        }

        public GridLayoutParams(int w, int h, int viewType) {
            super(w, h);
            enforceStaggeredLayout();
        }

        public GridLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            enforceStaggeredLayout();
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

    public static class GridListSavedState extends ListSavedState {
        public static final Creator<GridListSavedState> CREATOR;
        int columnCount;
        int[] columnTops;
        SparseArray positionData;

        /* renamed from: com.crumby.lib.widget.thirdparty.grid.StaggeredGridView.GridListSavedState.1 */
        static class C01701 implements Creator<GridListSavedState> {
            C01701() {
            }

            public GridListSavedState createFromParcel(Parcel in) {
                return new GridListSavedState(in);
            }

            public GridListSavedState[] newArray(int size) {
                return new GridListSavedState[size];
            }
        }

        public GridListSavedState(Parcelable superState) {
            super(superState);
        }

        public GridListSavedState(Parcel in) {
            super(in);
            this.columnCount = in.readInt();
            this.columnTops = new int[(this.columnCount >= 0 ? this.columnCount : 0)];
            in.readIntArray(this.columnTops);
            this.positionData = in.readSparseArray(GridItemRecord.class.getClassLoader());
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.columnCount);
            out.writeIntArray(this.columnTops);
            out.writeSparseArray(this.positionData);
        }

        public String toString() {
            return "StaggeredGridView.GridListSavedState{" + Integer.toHexString(System.identityHashCode(this)) + "}";
        }

        static {
            CREATOR = new C01701();
        }
    }

    public StaggeredGridView(Context context) {
        this(context, null);
    }

    public StaggeredGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StaggeredGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mColumnCountPortrait = DEFAUlT_COLUMNS_PORTRAIT;
        this.mColumnCountLandscape = DEFAULT_COLUMNS_LANDSCAPE;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, C0065R.styleable.StaggeredGridView, defStyle, 0);
            this.mColumnCountPortrait = typedArray.getInteger(0, DEFAUlT_COLUMNS_PORTRAIT);
            this.mColumnCountLandscape = typedArray.getInteger(1, DEFAULT_COLUMNS_LANDSCAPE);
            this.mItemMargin = typedArray.getDimensionPixelSize(DEFAUlT_COLUMNS_PORTRAIT, 0);
            this.mGridPaddingLeft = typedArray.getDimensionPixelSize(DEFAULT_COLUMNS_LANDSCAPE, 0);
            this.mGridPaddingRight = typedArray.getDimensionPixelSize(4, 0);
            this.mGridPaddingTop = typedArray.getDimensionPixelSize(5, 0);
            this.mGridPaddingBottom = typedArray.getDimensionPixelSize(6, 0);
            typedArray.recycle();
        }
        this.mColumnCount = 0;
        this.mColumnTops = new int[0];
        this.mColumnBottoms = new int[0];
        this.mColumnLefts = new int[0];
        this.mPositionData = new SparseArray();
    }

    public int getRowPaddingLeft() {
        return getListPaddingLeft() + this.mGridPaddingLeft;
    }

    public int getRowPaddingRight() {
        return getListPaddingRight() + this.mGridPaddingRight;
    }

    public int getRowPaddingTop() {
        return getListPaddingTop() + this.mGridPaddingTop;
    }

    public int getRowPaddingBottom() {
        return getListPaddingBottom() + this.mGridPaddingBottom;
    }

    public void setGridPadding(int left, int top, int right, int bottom) {
        this.mGridPaddingLeft = left;
        this.mGridPaddingTop = top;
        this.mGridPaddingRight = right;
        this.mGridPaddingBottom = bottom;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mColumnCount <= 0) {
            this.mColumnCount = getMeasuredWidth() > getMeasuredHeight() ? true : DBG ? this.mColumnCountLandscape : this.mColumnCountPortrait;
        }
        this.mColumnWidth = calculateColumnWidth(getMeasuredWidth());
        if (this.mColumnTops == null || this.mColumnTops.length != this.mColumnCount) {
            this.mColumnTops = new int[this.mColumnCount];
        }
        if (this.mColumnBottoms == null || this.mColumnBottoms.length != this.mColumnCount) {
            this.mColumnBottoms = new int[this.mColumnCount];
        }
        if (this.mColumnLefts == null || this.mColumnLefts.length != this.mColumnCount) {
            this.mColumnLefts = new int[this.mColumnCount];
        }
        for (int i = 0; i < this.mColumnCount; i++) {
            this.mColumnLefts[i] = calculateColumnLeft(i);
        }
    }

    protected void onMeasureChild(View child, LayoutParams layoutParams) {
        int viewType = layoutParams.viewType;
        int position = layoutParams.position;
        if (viewType == -2 || viewType == -1) {
            super.onMeasureChild(child, layoutParams);
        } else {
            int childHeightSpec;
            int childWidthSpec = MeasureSpec.makeMeasureSpec(this.mColumnWidth, 1073741824);
            if (layoutParams.height > 0) {
                childHeightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824);
            } else {
                childHeightSpec = MeasureSpec.makeMeasureSpec(-2, 0);
            }
            child.measure(childWidthSpec, childHeightSpec);
        }
        setPositionHeightRatio(position, getChildHeight(child));
    }

    public int getColumnWidth() {
        return this.mColumnWidth;
    }

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
            this.mNeedSync = DBG;
            this.mDistanceToTop = 0;
            setSelection(0);
        }
    }

    protected void onChildCreated(int position, boolean flowDown) {
        super.onChildCreated(position, flowDown);
        if (isHeaderOrFooter(position)) {
            setPositionIsHeaderFooter(position);
        } else {
            setPositionColumn(position, getChildColumn(position, flowDown));
        }
    }

    protected void layoutChildren() {
        preLayoutChildren();
        super.layoutChildren();
    }

    private void preLayoutChildren() {
        if (this.mNeedSync) {
            this.mNeedSync = DBG;
        } else {
            Arrays.fill(this.mColumnBottoms, 0);
        }
        System.arraycopy(this.mColumnTops, 0, this.mColumnBottoms, 0, this.mColumnCount);
    }

    protected void onLayoutChild(View child, int position, boolean flowDown, int childrenLeft, int childTop, int childRight, int childBottom) {
        if (isHeaderOrFooter(position)) {
            layoutGridHeaderFooter(child, position, flowDown, childrenLeft, childTop, childRight, childBottom);
        } else {
            layoutGridChild(child, position, flowDown, childrenLeft, childRight);
        }
    }

    private void layoutGridHeaderFooter(View child, int position, boolean flowDown, int childrenLeft, int childTop, int childRight, int childBottom) {
        int gridChildTop;
        int gridChildBottom;
        if (flowDown) {
            gridChildTop = getLowestPositionedBottom();
            gridChildBottom = gridChildTop + getChildHeight(child);
        } else {
            gridChildBottom = getHighestPositionedTop();
            gridChildTop = gridChildBottom - getChildHeight(child);
        }
        for (int i = 0; i < this.mColumnCount; i++) {
            updateColumnTopIfNeeded(i, gridChildTop);
            updateColumnBottomIfNeeded(i, gridChildBottom);
        }
        super.onLayoutChild(child, position, flowDown, childrenLeft, gridChildTop, childRight, gridChildBottom);
    }

    private void layoutGridChild(View child, int position, boolean flowDown, int childrenLeft, int childRight) {
        int gridChildTop;
        int gridChildBottom;
        int column = getPositionColumn(position);
        int childTopMargin = getChildTopMargin(position);
        int childBottomMargin = getChildBottomMargin();
        int verticalMargins = childTopMargin + childBottomMargin;
        if (flowDown) {
            gridChildTop = this.mColumnBottoms[column];
            gridChildBottom = gridChildTop + (getChildHeight(child) + verticalMargins);
        } else {
            gridChildBottom = this.mColumnTops[column];
            gridChildTop = gridChildBottom - (getChildHeight(child) + verticalMargins);
        }
        ((GridLayoutParams) child.getLayoutParams()).column = column;
        updateColumnBottomIfNeeded(column, gridChildBottom);
        updateColumnTopIfNeeded(column, gridChildTop);
        child.layout(childrenLeft, gridChildTop + childTopMargin, childRight, gridChildBottom - childBottomMargin);
    }

    protected void onOffsetChild(View child, int position, boolean flowDown, int childrenLeft, int childTop) {
        if (isHeaderOrFooter(position)) {
            offsetGridHeaderFooter(child, position, flowDown, childrenLeft, childTop);
        } else {
            offsetGridChild(child, position, flowDown, childrenLeft, childTop);
        }
    }

    private void offsetGridHeaderFooter(View child, int position, boolean flowDown, int childrenLeft, int childTop) {
        int gridChildTop;
        int gridChildBottom;
        if (flowDown) {
            gridChildTop = getLowestPositionedBottom();
            gridChildBottom = gridChildTop + getChildHeight(child);
        } else {
            gridChildBottom = getHighestPositionedTop();
            gridChildTop = gridChildBottom - getChildHeight(child);
        }
        for (int i = 0; i < this.mColumnCount; i++) {
            updateColumnTopIfNeeded(i, gridChildTop);
            updateColumnBottomIfNeeded(i, gridChildBottom);
        }
        super.onOffsetChild(child, position, flowDown, childrenLeft, gridChildTop);
    }

    private void offsetGridChild(View child, int position, boolean flowDown, int childrenLeft, int childTop) {
        int gridChildTop;
        int gridChildBottom;
        int column = getPositionColumn(position);
        int childTopMargin = getChildTopMargin(position);
        int verticalMargins = childTopMargin + getChildBottomMargin();
        if (flowDown) {
            gridChildTop = this.mColumnBottoms[column];
            gridChildBottom = gridChildTop + (getChildHeight(child) + verticalMargins);
        } else {
            gridChildBottom = this.mColumnTops[column];
            gridChildTop = gridChildBottom - (getChildHeight(child) + verticalMargins);
        }
        ((GridLayoutParams) child.getLayoutParams()).column = column;
        updateColumnBottomIfNeeded(column, gridChildBottom);
        updateColumnTopIfNeeded(column, gridChildTop);
        super.onOffsetChild(child, position, flowDown, childrenLeft, gridChildTop + childTopMargin);
    }

    private int getChildHeight(View child) {
        return child.getMeasuredHeight();
    }

    private int getChildTopMargin(int position) {
        boolean isFirstRow;
        if (position < getHeaderViewsCount() + this.mColumnCount) {
            isFirstRow = true;
        } else {
            isFirstRow = DBG;
        }
        if (isFirstRow) {
            return this.mItemMargin;
        }
        return 0;
    }

    private int getChildBottomMargin() {
        return this.mItemMargin;
    }

    protected LayoutParams generateChildLayoutParams(View child) {
        GridLayoutParams layoutParams = null;
        ViewGroup.LayoutParams childParams = child.getLayoutParams();
        if (childParams != null) {
            if (childParams instanceof GridLayoutParams) {
                layoutParams = (GridLayoutParams) childParams;
            } else {
                layoutParams = new GridLayoutParams(childParams);
            }
        }
        if (layoutParams == null) {
            return new GridLayoutParams(this.mColumnWidth, -2);
        }
        return layoutParams;
    }

    private void updateColumnTopIfNeeded(int column, int childTop) {
        if (childTop < this.mColumnTops[column]) {
            this.mColumnTops[column] = childTop;
        }
    }

    private void updateColumnBottomIfNeeded(int column, int childBottom) {
        if (childBottom > this.mColumnBottoms[column]) {
            this.mColumnBottoms[column] = childBottom;
        }
    }

    protected int getChildLeft(int position) {
        if (isHeaderOrFooter(position)) {
            return super.getChildLeft(position);
        }
        return this.mColumnLefts[getPositionColumn(position)];
    }

    protected int getChildTop(int position) {
        if (isHeaderOrFooter(position)) {
            return super.getChildTop(position);
        }
        int column = getPositionColumn(position);
        if (column == -1) {
            return getHighestPositionedBottom();
        }
        return this.mColumnBottoms[column];
    }

    protected int getNextChildDownsTop(int position) {
        if (isHeaderOrFooter(position)) {
            return super.getNextChildDownsTop(position);
        }
        return getHighestPositionedBottom();
    }

    protected int getChildBottom(int position) {
        if (isHeaderOrFooter(position)) {
            return super.getChildBottom(position);
        }
        int column = getPositionColumn(position);
        if (column == -1) {
            return getLowestPositionedTop();
        }
        return this.mColumnTops[column];
    }

    protected int getNextChildUpsBottom(int position) {
        if (isHeaderOrFooter(position)) {
            return super.getNextChildUpsBottom(position);
        }
        return getLowestPositionedTop();
    }

    protected int getLastChildBottom() {
        if (isHeaderOrFooter(this.mFirstPosition + (getChildCount() - 1))) {
            return super.getLastChildBottom();
        }
        return getHighestPositionedBottom();
    }

    protected int getFirstChildTop() {
        if (isHeaderOrFooter(this.mFirstPosition)) {
            return super.getFirstChildTop();
        }
        return getLowestPositionedTop();
    }

    protected int getHighestChildTop() {
        if (isHeaderOrFooter(this.mFirstPosition)) {
            return super.getHighestChildTop();
        }
        return getHighestPositionedTop();
    }

    protected int getLowestChildBottom() {
        if (isHeaderOrFooter(this.mFirstPosition + (getChildCount() - 1))) {
            return super.getLowestChildBottom();
        }
        return getLowestPositionedBottom();
    }

    protected void offsetChildrenTopAndBottom(int offset) {
        super.offsetChildrenTopAndBottom(offset);
        offsetAllColumnsTopAndBottom(offset);
        offsetDistanceToTop(offset);
    }

    protected void offsetChildrenTopAndBottom(int offset, int column) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            if (v != null && v.getLayoutParams() != null && (v.getLayoutParams() instanceof GridLayoutParams) && ((GridLayoutParams) v.getLayoutParams()).column == column) {
                v.offsetTopAndBottom(offset);
            }
        }
        offsetColumnTopAndBottom(offset, column);
    }

    private void offsetDistanceToTop(int offset) {
        this.mDistanceToTop += offset;
    }

    public int getDistanceToTop() {
        return this.mDistanceToTop;
    }

    private void offsetAllColumnsTopAndBottom(int offset) {
        if (offset != 0) {
            for (int i = 0; i < this.mColumnCount; i++) {
                offsetColumnTopAndBottom(offset, i);
            }
        }
    }

    private void offsetColumnTopAndBottom(int offset, int column) {
        if (offset != 0) {
            int[] iArr = this.mColumnTops;
            iArr[column] = iArr[column] + offset;
            iArr = this.mColumnBottoms;
            iArr[column] = iArr[column] + offset;
        }
    }

    protected void adjustViewsAfterFillGap(boolean down) {
        super.adjustViewsAfterFillGap(down);
        if (!down) {
            alignTops();
        }
    }

    private void alignTops() {
        if (this.mFirstPosition == getHeaderViewsCount()) {
            int[] nonHeaderTops = getHighestNonHeaderTops();
            boolean isAligned = true;
            int highestColumn = -1;
            int highestTop = Integer.MAX_VALUE;
            int i = 0;
            while (i < nonHeaderTops.length) {
                if (isAligned && i > 0 && nonHeaderTops[i] != highestTop) {
                    isAligned = DBG;
                }
                if (nonHeaderTops[i] < highestTop) {
                    highestTop = nonHeaderTops[i];
                    highestColumn = i;
                }
                i++;
            }
            if (!isAligned) {
                for (i = 0; i < nonHeaderTops.length; i++) {
                    if (i != highestColumn) {
                        offsetChildrenTopAndBottom(highestTop - nonHeaderTops[i], i);
                    }
                }
                invalidate();
            }
        }
    }

    private int[] getHighestNonHeaderTops() {
        int[] nonHeaderTops = new int[this.mColumnCount];
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (!(child == null || child.getLayoutParams() == null || !(child.getLayoutParams() instanceof GridLayoutParams))) {
                    GridLayoutParams lp = (GridLayoutParams) child.getLayoutParams();
                    if (lp.viewType != -2 && child.getTop() < nonHeaderTops[lp.column]) {
                        nonHeaderTops[lp.column] = child.getTop();
                    }
                }
            }
        }
        return nonHeaderTops;
    }

    protected void onChildrenDetached(int start, int count) {
        super.onChildrenDetached(start, count);
        Arrays.fill(this.mColumnTops, Integer.MAX_VALUE);
        Arrays.fill(this.mColumnBottoms, 0);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != null) {
                LayoutParams childParams = (LayoutParams) child.getLayoutParams();
                int childTop;
                int childBottom;
                if (childParams.viewType == -2 || !(childParams instanceof GridLayoutParams)) {
                    childTop = child.getTop();
                    childBottom = child.getBottom();
                    for (int col = 0; col < this.mColumnCount; col++) {
                        if (childTop < this.mColumnTops[col]) {
                            this.mColumnTops[col] = childTop;
                        }
                        if (childBottom > this.mColumnBottoms[col]) {
                            this.mColumnBottoms[col] = childBottom;
                        }
                    }
                } else {
                    GridLayoutParams layoutParams = (GridLayoutParams) childParams;
                    int column = layoutParams.column;
                    int position = layoutParams.position;
                    childTop = child.getTop();
                    if (childTop < this.mColumnTops[column]) {
                        this.mColumnTops[column] = childTop - getChildTopMargin(position);
                    }
                    childBottom = child.getBottom();
                    if (childBottom > this.mColumnBottoms[column]) {
                        this.mColumnBottoms[column] = getChildBottomMargin() + childBottom;
                    }
                }
            }
        }
    }

    protected boolean hasSpaceUp() {
        int end;
        if (this.mClipToPadding) {
            end = getRowPaddingTop();
        } else {
            end = 0;
        }
        if (getLowestPositionedTop() > end) {
            return true;
        }
        return DBG;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        boolean isLandscape;
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > h) {
            isLandscape = true;
        } else {
            isLandscape = DBG;
        }
        int newColumnCount = isLandscape ? this.mColumnCountLandscape : this.mColumnCountPortrait;
        if (this.mColumnCount != newColumnCount) {
            this.mColumnCount = newColumnCount;
            this.mColumnWidth = calculateColumnWidth(w);
            this.mColumnTops = new int[this.mColumnCount];
            this.mColumnBottoms = new int[this.mColumnCount];
            this.mColumnLefts = new int[this.mColumnCount];
            this.mDistanceToTop = 0;
            for (int i = 0; i < this.mColumnCount; i++) {
                this.mColumnLefts[i] = calculateColumnLeft(i);
            }
            if (getCount() > 0 && this.mPositionData.size() > 0) {
                onColumnSync();
            }
            requestLayout();
        }
    }

    private int calculateColumnWidth(int gridWidth) {
        return ((gridWidth - (getRowPaddingLeft() + getRowPaddingRight())) - (this.mItemMargin * (this.mColumnCount + 1))) / this.mColumnCount;
    }

    private int calculateColumnLeft(int colIndex) {
        return (getRowPaddingLeft() + this.mItemMargin) + ((this.mItemMargin + this.mColumnWidth) * colIndex);
    }

    private void onColumnSync() {
        int pos;
        int syncPosition = Math.min(this.mSyncPosition, getCount() - 1);
        SparseArray<Double> positionHeightRatios = new SparseArray(syncPosition);
        for (pos = 0; pos < syncPosition; pos++) {
            GridItemRecord rec = (GridItemRecord) this.mPositionData.get(pos);
            if (rec == null) {
                break;
            }
            StringBuilder append = new StringBuilder().append("onColumnSync:");
            Log.d(TAG, r17.append(pos).append(" ratio:").append(rec.heightRatio).toString());
            positionHeightRatios.append(pos, Double.valueOf(rec.heightRatio));
        }
        this.mPositionData.clear();
        for (pos = 0; pos < syncPosition; pos++) {
            rec = getOrCreateRecord(pos);
            double heightRatio = ((Double) positionHeightRatios.get(pos)).doubleValue();
            int height = (int) (((double) this.mColumnWidth) * heightRatio);
            rec.heightRatio = heightRatio;
            int top;
            int bottom;
            if (isHeaderOrFooter(pos)) {
                top = getLowestPositionedBottom();
                bottom = top + height;
                int i = 0;
                while (true) {
                    int i2 = this.mColumnCount;
                    if (i >= r0) {
                        break;
                    }
                    this.mColumnTops[i] = top;
                    this.mColumnBottoms[i] = bottom;
                    i++;
                }
            } else {
                int column = getHighestPositionedBottomColumn();
                top = this.mColumnBottoms[column];
                bottom = ((top + height) + getChildTopMargin(pos)) + getChildBottomMargin();
                this.mColumnTops[column] = top;
                this.mColumnBottoms[column] = bottom;
                rec.column = column;
            }
        }
        int syncColumn = getHighestPositionedBottomColumn();
        setPositionColumn(syncPosition, syncColumn);
        int syncToBottom = this.mColumnBottoms[syncColumn];
        offsetAllColumnsTopAndBottom((-syncToBottom) + this.mSpecificTop);
        this.mDistanceToTop = -syncToBottom;
        System.arraycopy(this.mColumnBottoms, 0, this.mColumnTops, 0, this.mColumnCount);
    }

    private void setPositionColumn(int position, int column) {
        getOrCreateRecord(position).column = column;
    }

    private void setPositionHeightRatio(int position, int height) {
        getOrCreateRecord(position).heightRatio = ((double) height) / ((double) this.mColumnWidth);
    }

    private void setPositionIsHeaderFooter(int position) {
        getOrCreateRecord(position).isHeaderFooter = true;
    }

    private GridItemRecord getOrCreateRecord(int position) {
        GridItemRecord rec = (GridItemRecord) this.mPositionData.get(position, null);
        if (rec != null) {
            return rec;
        }
        rec = new GridItemRecord();
        this.mPositionData.append(position, rec);
        return rec;
    }

    private int getPositionColumn(int position) {
        GridItemRecord rec = (GridItemRecord) this.mPositionData.get(position, null);
        return rec != null ? rec.column : -1;
    }

    private boolean isHeaderOrFooter(int position) {
        return this.mAdapter.getItemViewType(position) == -2 ? true : DBG;
    }

    private int getChildColumn(int position, boolean flowDown) {
        int column = getPositionColumn(position);
        int columnCount = this.mColumnCount;
        if (column >= 0 && column < columnCount) {
            return column;
        }
        if (flowDown) {
            return getHighestPositionedBottomColumn();
        }
        return getLowestPositionedTopColumn();
    }

    private int getHighestPositionedBottom() {
        return this.mColumnBottoms[getHighestPositionedBottomColumn()];
    }

    private int getHighestPositionedBottomColumn() {
        int columnFound = 0;
        int highestPositionedBottom = Integer.MAX_VALUE;
        for (int i = 0; i < this.mColumnCount; i++) {
            int bottom = this.mColumnBottoms[i];
            if (bottom < highestPositionedBottom) {
                highestPositionedBottom = bottom;
                columnFound = i;
            }
        }
        return columnFound;
    }

    private int getLowestPositionedBottom() {
        return this.mColumnBottoms[getLowestPositionedBottomColumn()];
    }

    private int getLowestPositionedBottomColumn() {
        int columnFound = 0;
        int lowestPositionedBottom = ExploreByTouchHelper.INVALID_ID;
        for (int i = 0; i < this.mColumnCount; i++) {
            int bottom = this.mColumnBottoms[i];
            if (bottom > lowestPositionedBottom) {
                lowestPositionedBottom = bottom;
                columnFound = i;
            }
        }
        return columnFound;
    }

    private int getLowestPositionedTop() {
        return this.mColumnTops[getLowestPositionedTopColumn()];
    }

    private int getLowestPositionedTopColumn() {
        int columnFound = 0;
        int lowestPositionedTop = ExploreByTouchHelper.INVALID_ID;
        for (int i = 0; i < this.mColumnCount; i++) {
            int top = this.mColumnTops[i];
            if (top > lowestPositionedTop) {
                lowestPositionedTop = top;
                columnFound = i;
            }
        }
        return columnFound;
    }

    private int getHighestPositionedTop() {
        return this.mColumnTops[getHighestPositionedTopColumn()];
    }

    private int getHighestPositionedTopColumn() {
        int columnFound = 0;
        int highestPositionedTop = Integer.MAX_VALUE;
        for (int i = 0; i < this.mColumnCount; i++) {
            int top = this.mColumnTops[i];
            if (top < highestPositionedTop) {
                highestPositionedTop = top;
                columnFound = i;
            }
        }
        return columnFound;
    }

    public Parcelable onSaveInstanceState() {
        boolean haveChildren;
        int i = 0;
        ListSavedState listState = (ListSavedState) super.onSaveInstanceState();
        GridListSavedState ss = new GridListSavedState(listState.getSuperState());
        ss.selectedId = listState.selectedId;
        ss.firstId = listState.firstId;
        ss.viewTop = listState.viewTop;
        ss.position = listState.position;
        ss.height = listState.height;
        if (getChildCount() <= 0 || getCount() <= 0) {
            haveChildren = DBG;
        } else {
            haveChildren = true;
        }
        if (!haveChildren || this.mFirstPosition <= 0) {
            if (this.mColumnCount >= 0) {
                i = this.mColumnCount;
            }
            ss.columnCount = i;
            ss.columnTops = new int[ss.columnCount];
            ss.positionData = new SparseArray();
        } else {
            ss.columnCount = this.mColumnCount;
            ss.columnTops = this.mColumnTops;
            ss.positionData = this.mPositionData;
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        GridListSavedState ss = (GridListSavedState) state;
        this.mColumnCount = ss.columnCount;
        this.mColumnTops = ss.columnTops;
        this.mPositionData = ss.positionData;
        this.mNeedSync = true;
        super.onRestoreInstanceState(ss);
    }
}
