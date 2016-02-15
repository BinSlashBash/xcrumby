/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.DataSetObservable
 *  android.database.DataSetObserver
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.widget.Adapter
 *  android.widget.Filter
 *  android.widget.Filterable
 *  android.widget.FrameLayout
 *  android.widget.GridView
 *  android.widget.ListAdapter
 *  android.widget.WrapperListAdapter
 */
package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import java.util.ArrayList;
import java.util.Iterator;

public class HeaderGridView
extends GridView {
    private static final String TAG = "HeaderGridView";
    private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList();

    public HeaderGridView(Context context) {
        super(context);
        this.initHeaderGridView();
    }

    public HeaderGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initHeaderGridView();
    }

    public HeaderGridView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.initHeaderGridView();
    }

    private void initHeaderGridView() {
        super.setClipChildren(false);
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

    public void addHeaderView(View view) {
        this.addHeaderView(view, null, true);
    }

    public void addHeaderView(View view, Object object, boolean bl2) {
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter != null && !(listAdapter instanceof HeaderViewGridAdapter)) {
            throw new IllegalStateException("Cannot add header view to grid -- setAdapter has already been called.");
        }
        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        FullWidthFixedViewLayout fullWidthFixedViewLayout = new FullWidthFixedViewLayout(this.getContext());
        fullWidthFixedViewLayout.addView(view);
        fixedViewInfo.view = view;
        fixedViewInfo.viewContainer = fullWidthFixedViewLayout;
        fixedViewInfo.data = object;
        fixedViewInfo.isSelectable = bl2;
        this.mHeaderViewInfos.add(fixedViewInfo);
        if (listAdapter != null) {
            ((HeaderViewGridAdapter)listAdapter).notifyDataSetChanged();
        }
    }

    public int getHeaderViewCount() {
        return this.mHeaderViewInfos.size();
    }

    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter != null && listAdapter instanceof HeaderViewGridAdapter) {
            ((HeaderViewGridAdapter)listAdapter).setNumColumns(this.getNumColumns());
        }
    }

    public boolean removeHeaderView(View view) {
        if (this.mHeaderViewInfos.size() > 0) {
            boolean bl2 = false;
            ListAdapter listAdapter = this.getAdapter();
            boolean bl3 = bl2;
            if (listAdapter != null) {
                bl3 = bl2;
                if (((HeaderViewGridAdapter)listAdapter).removeHeader(view)) {
                    bl3 = true;
                }
            }
            this.removeFixedViewInfo(view, this.mHeaderViewInfos);
            return bl3;
        }
        return false;
    }

    public void setAdapter(ListAdapter object) {
        if (this.mHeaderViewInfos.size() > 0) {
            object = new HeaderViewGridAdapter(this.mHeaderViewInfos, (ListAdapter)object);
            int n2 = this.getNumColumns();
            if (n2 > 1) {
                object.setNumColumns(n2);
            }
            super.setAdapter((ListAdapter)object);
            return;
        }
        super.setAdapter((ListAdapter)object);
    }

    public void setClipChildren(boolean bl2) {
    }

    private static class FixedViewInfo {
        public Object data;
        public boolean isSelectable;
        public View view;
        public ViewGroup viewContainer;

        private FixedViewInfo() {
        }
    }

    private class FullWidthFixedViewLayout
    extends FrameLayout {
        public FullWidthFixedViewLayout(Context context) {
            super(context);
        }

        protected void onMeasure(int n2, int n3) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)(HeaderGridView.this.getMeasuredWidth() - HeaderGridView.this.getPaddingLeft() - HeaderGridView.this.getPaddingRight()), (int)View.MeasureSpec.getMode((int)n2)), n3);
        }
    }

    public static class HeaderViewGridAdapter
    implements WrapperListAdapter,
    Filterable {
        private final ListAdapter mAdapter;
        boolean mAreAllFixedViewsSelectable;
        private final DataSetObservable mDataSetObservable = new DataSetObservable();
        ArrayList<FixedViewInfo> mHeaderViewInfos;
        private final boolean mIsFilterable;
        private int mNumColumns = 1;

        public HeaderViewGridAdapter(ArrayList<FixedViewInfo> arrayList, ListAdapter listAdapter) {
            this.mAdapter = listAdapter;
            this.mIsFilterable = listAdapter instanceof Filterable;
            if (arrayList == null) {
                throw new IllegalArgumentException("headerViewInfos cannot be null");
            }
            this.mHeaderViewInfos = arrayList;
            this.mAreAllFixedViewsSelectable = this.areAllListInfosSelectable(this.mHeaderViewInfos);
        }

        private boolean areAllListInfosSelectable(ArrayList<FixedViewInfo> object) {
            if (object != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    if (((FixedViewInfo)object.next()).isSelectable) continue;
                    return false;
                }
            }
            return true;
        }

        public boolean areAllItemsEnabled() {
            if (this.mAdapter == null || this.mAreAllFixedViewsSelectable && this.mAdapter.areAllItemsEnabled()) {
                return true;
            }
            return false;
        }

        public int getCount() {
            if (this.mAdapter != null) {
                return this.getHeadersCount() * this.mNumColumns + this.mAdapter.getCount();
            }
            return this.getHeadersCount() * this.mNumColumns;
        }

        public Filter getFilter() {
            if (this.mIsFilterable) {
                return ((Filterable)this.mAdapter).getFilter();
            }
            return null;
        }

        public int getHeadersCount() {
            return this.mHeaderViewInfos.size();
        }

        public Object getItem(int n2) {
            int n3 = this.getHeadersCount() * this.mNumColumns;
            if (n2 < n3) {
                if (n2 % this.mNumColumns == 0) {
                    return this.mHeaderViewInfos.get((int)(n2 / this.mNumColumns)).data;
                }
                return null;
            }
            n3 = n2 - n3;
            if (this.mAdapter != null && n3 < this.mAdapter.getCount()) {
                return this.mAdapter.getItem(n3);
            }
            throw new ArrayIndexOutOfBoundsException(n2);
        }

        public long getItemId(int n2) {
            int n3 = this.getHeadersCount() * this.mNumColumns;
            if (this.mAdapter != null && n2 >= n3 && (n2 -= n3) < this.mAdapter.getCount()) {
                return this.mAdapter.getItemId(n2);
            }
            return -1;
        }

        public int getItemViewType(int n2) {
            int n3 = this.getHeadersCount() * this.mNumColumns;
            if (n2 < n3 && n2 % this.mNumColumns != 0) {
                if (this.mAdapter != null) {
                    return this.mAdapter.getViewTypeCount();
                }
                return 1;
            }
            if (this.mAdapter != null && n2 >= n3 && (n2 -= n3) < this.mAdapter.getCount()) {
                return this.mAdapter.getItemViewType(n2);
            }
            return -2;
        }

        public View getView(int n2, View view, ViewGroup viewGroup) {
            int n3 = this.getHeadersCount() * this.mNumColumns;
            if (n2 < n3) {
                ViewGroup viewGroup2 = this.mHeaderViewInfos.get((int)(n2 / this.mNumColumns)).viewContainer;
                if (n2 % this.mNumColumns == 0) {
                    return viewGroup2;
                }
                View view2 = view;
                if (view == null) {
                    view2 = new View(viewGroup.getContext());
                }
                view2.setVisibility(4);
                view2.setMinimumHeight(viewGroup2.getHeight());
                return view2;
            }
            n3 = n2 - n3;
            if (this.mAdapter != null && n3 < this.mAdapter.getCount()) {
                return this.mAdapter.getView(n3, view, viewGroup);
            }
            throw new ArrayIndexOutOfBoundsException(n2);
        }

        public int getViewTypeCount() {
            if (this.mAdapter != null) {
                return this.mAdapter.getViewTypeCount() + 1;
            }
            return 2;
        }

        public ListAdapter getWrappedAdapter() {
            return this.mAdapter;
        }

        public boolean hasStableIds() {
            if (this.mAdapter != null) {
                return this.mAdapter.hasStableIds();
            }
            return false;
        }

        public boolean isEmpty() {
            if ((this.mAdapter == null || this.mAdapter.isEmpty()) && this.getHeadersCount() == 0) {
                return true;
            }
            return false;
        }

        public boolean isEnabled(int n2) {
            int n3 = this.getHeadersCount() * this.mNumColumns;
            if (n2 < n3) {
                if (n2 % this.mNumColumns == 0 && this.mHeaderViewInfos.get((int)(n2 / this.mNumColumns)).isSelectable) {
                    return true;
                }
                return false;
            }
            n3 = n2 - n3;
            if (this.mAdapter != null && n3 < this.mAdapter.getCount()) {
                return this.mAdapter.isEnabled(n3);
            }
            throw new ArrayIndexOutOfBoundsException(n2);
        }

        public void notifyDataSetChanged() {
            this.mDataSetObservable.notifyChanged();
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            this.mDataSetObservable.registerObserver((Object)dataSetObserver);
            if (this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        public boolean removeHeader(View view) {
            for (int i2 = 0; i2 < this.mHeaderViewInfos.size(); ++i2) {
                if (this.mHeaderViewInfos.get((int)i2).view != view) continue;
                this.mHeaderViewInfos.remove(i2);
                this.mAreAllFixedViewsSelectable = this.areAllListInfosSelectable(this.mHeaderViewInfos);
                this.mDataSetObservable.notifyChanged();
                return true;
            }
            return false;
        }

        public void setNumColumns(int n2) {
            if (n2 < 1) {
                throw new IllegalArgumentException("Number of columns must be 1 or more");
            }
            if (this.mNumColumns != n2) {
                this.mNumColumns = n2;
                this.notifyDataSetChanged();
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            this.mDataSetObservable.unregisterObserver((Object)dataSetObserver);
            if (this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

}

