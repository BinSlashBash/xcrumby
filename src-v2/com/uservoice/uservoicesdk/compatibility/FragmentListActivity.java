/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.uservoice.uservoicesdk.compatibility;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.BaseActivity;

public abstract class FragmentListActivity
extends BaseActivity {
    private ListAdapter mAdapter;
    private boolean mFinishedStart = false;
    private Handler mHandler = new Handler();
    private ListView mList;
    private AdapterView.OnItemClickListener mOnClickListener;
    private Runnable mRequestFocus;

    public FragmentListActivity() {
        this.mRequestFocus = new Runnable(){

            @Override
            public void run() {
                FragmentListActivity.this.mList.focusableViewAvailable((View)FragmentListActivity.this.mList);
            }
        };
        this.mOnClickListener = new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
                FragmentListActivity.this.onListItemClick((ListView)adapterView, view, n2, l2);
            }
        };
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        this.setContentView(R.layout.uv_list_content);
    }

    public ListAdapter getListAdapter() {
        return this.mAdapter;
    }

    public ListView getListView() {
        this.ensureList();
        return this.mList;
    }

    public long getSelectedItemId() {
        return this.mList.getSelectedItemId();
    }

    public int getSelectedItemPosition() {
        return this.mList.getSelectedItemPosition();
    }

    public void onContentChanged() {
        super.onContentChanged();
        View view = this.findViewById(16908292);
        this.mList = (ListView)this.findViewById(16908298);
        if (this.mList == null) {
            throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
        }
        if (view != null) {
            this.mList.setEmptyView(view);
        }
        this.mList.setOnItemClickListener(this.mOnClickListener);
        if (this.mFinishedStart) {
            this.setListAdapter(this.mAdapter);
        }
        this.mHandler.post(this.mRequestFocus);
        this.mFinishedStart = true;
    }

    protected void onListItemClick(ListView listView, View view, int n2, long l2) {
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        this.ensureList();
        super.onRestoreInstanceState(bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setListAdapter(ListAdapter listAdapter) {
        synchronized (this) {
            this.ensureList();
            this.mAdapter = listAdapter;
            this.mList.setAdapter(listAdapter);
            return;
        }
    }

    public void setSelection(int n2) {
        this.mList.setSelection(n2);
    }

}

