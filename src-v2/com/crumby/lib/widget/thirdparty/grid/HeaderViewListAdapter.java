/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.Filter
 *  android.widget.Filterable
 *  android.widget.ListAdapter
 *  android.widget.WrapperListAdapter
 */
package com.crumby.lib.widget.thirdparty.grid;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import com.crumby.lib.widget.thirdparty.grid.ExtendableListView;
import java.util.ArrayList;
import java.util.Iterator;

public class HeaderViewListAdapter
implements WrapperListAdapter,
Filterable {
    static final ArrayList<ExtendableListView.FixedViewInfo> EMPTY_INFO_LIST = new ArrayList();
    private final ListAdapter mAdapter;
    boolean mAreAllFixedViewsSelectable;
    ArrayList<ExtendableListView.FixedViewInfo> mFooterViewInfos;
    ArrayList<ExtendableListView.FixedViewInfo> mHeaderViewInfos;
    private final boolean mIsFilterable;

    /*
     * Enabled aggressive block sorting
     */
    public HeaderViewListAdapter(ArrayList<ExtendableListView.FixedViewInfo> arrayList, ArrayList<ExtendableListView.FixedViewInfo> arrayList2, ListAdapter listAdapter) {
        this.mAdapter = listAdapter;
        this.mIsFilterable = listAdapter instanceof Filterable;
        this.mHeaderViewInfos = arrayList == null ? EMPTY_INFO_LIST : arrayList;
        this.mFooterViewInfos = arrayList2 == null ? EMPTY_INFO_LIST : arrayList2;
        boolean bl2 = this.areAllListInfosSelectable(this.mHeaderViewInfos) && this.areAllListInfosSelectable(this.mFooterViewInfos);
        this.mAreAllFixedViewsSelectable = bl2;
    }

    private boolean areAllListInfosSelectable(ArrayList<ExtendableListView.FixedViewInfo> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                if (((ExtendableListView.FixedViewInfo)object.next()).isSelectable) continue;
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
            return this.getFootersCount() + this.getHeadersCount() + this.mAdapter.getCount();
        }
        return this.getFootersCount() + this.getHeadersCount();
    }

    public Filter getFilter() {
        if (this.mIsFilterable) {
            return ((Filterable)this.mAdapter).getFilter();
        }
        return null;
    }

    public int getFootersCount() {
        return this.mFooterViewInfos.size();
    }

    public int getHeadersCount() {
        return this.mHeaderViewInfos.size();
    }

    public Object getItem(int n2) {
        int n3 = this.getHeadersCount();
        if (n2 < n3) {
            return this.mHeaderViewInfos.get((int)n2).data;
        }
        int n4 = n2 - n3;
        n2 = 0;
        if (this.mAdapter != null) {
            n2 = n3 = this.mAdapter.getCount();
            if (n4 < n3) {
                return this.mAdapter.getItem(n4);
            }
        }
        return this.mFooterViewInfos.get((int)(n4 - n2)).data;
    }

    public long getItemId(int n2) {
        int n3 = this.getHeadersCount();
        if (this.mAdapter != null && n2 >= n3 && (n2 -= n3) < this.mAdapter.getCount()) {
            return this.mAdapter.getItemId(n2);
        }
        return -1;
    }

    public int getItemViewType(int n2) {
        int n3 = this.getHeadersCount();
        if (this.mAdapter != null && n2 >= n3 && (n2 -= n3) < this.mAdapter.getCount()) {
            return this.mAdapter.getItemViewType(n2);
        }
        return -2;
    }

    public View getView(int n2, View view, ViewGroup viewGroup) {
        int n3 = this.getHeadersCount();
        if (n2 < n3) {
            return this.mHeaderViewInfos.get((int)n2).view;
        }
        int n4 = n2 - n3;
        n2 = 0;
        if (this.mAdapter != null) {
            n2 = n3 = this.mAdapter.getCount();
            if (n4 < n3) {
                return this.mAdapter.getView(n4, view, viewGroup);
            }
        }
        return this.mFooterViewInfos.get((int)(n4 - n2)).view;
    }

    public int getViewTypeCount() {
        if (this.mAdapter != null) {
            return this.mAdapter.getViewTypeCount();
        }
        return 1;
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
        if (this.mAdapter == null || this.mAdapter.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isEnabled(int n2) {
        int n3 = this.getHeadersCount();
        if (n2 < n3) {
            return this.mHeaderViewInfos.get((int)n2).isSelectable;
        }
        int n4 = n2 - n3;
        n2 = 0;
        if (this.mAdapter != null) {
            n2 = n3 = this.mAdapter.getCount();
            if (n4 < n3) {
                return this.mAdapter.isEnabled(n4);
            }
        }
        return this.mFooterViewInfos.get((int)(n4 - n2)).isSelectable;
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        if (this.mAdapter != null) {
            this.mAdapter.registerDataSetObserver(dataSetObserver);
        }
    }

    public boolean removeFooter(View view) {
        boolean bl2 = false;
        for (int i2 = 0; i2 < this.mFooterViewInfos.size(); ++i2) {
            if (this.mFooterViewInfos.get((int)i2).view != view) continue;
            this.mFooterViewInfos.remove(i2);
            boolean bl3 = bl2;
            if (this.areAllListInfosSelectable(this.mHeaderViewInfos)) {
                bl3 = bl2;
                if (this.areAllListInfosSelectable(this.mFooterViewInfos)) {
                    bl3 = true;
                }
            }
            this.mAreAllFixedViewsSelectable = bl3;
            return true;
        }
        return false;
    }

    public boolean removeHeader(View view) {
        boolean bl2 = false;
        for (int i2 = 0; i2 < this.mHeaderViewInfos.size(); ++i2) {
            if (this.mHeaderViewInfos.get((int)i2).view != view) continue;
            this.mHeaderViewInfos.remove(i2);
            boolean bl3 = bl2;
            if (this.areAllListInfosSelectable(this.mHeaderViewInfos)) {
                bl3 = bl2;
                if (this.areAllListInfosSelectable(this.mFooterViewInfos)) {
                    bl3 = true;
                }
            }
            this.mAreAllFixedViewsSelectable = bl3;
            return true;
        }
        return false;
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(dataSetObserver);
        }
    }
}

