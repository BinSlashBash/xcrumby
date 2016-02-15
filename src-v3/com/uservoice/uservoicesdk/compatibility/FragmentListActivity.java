package com.uservoice.uservoicesdk.compatibility;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.activity.BaseActivity;

public abstract class FragmentListActivity extends BaseActivity {
    private ListAdapter mAdapter;
    private boolean mFinishedStart;
    private Handler mHandler;
    private ListView mList;
    private OnItemClickListener mOnClickListener;
    private Runnable mRequestFocus;

    /* renamed from: com.uservoice.uservoicesdk.compatibility.FragmentListActivity.1 */
    class C06331 implements Runnable {
        C06331() {
        }

        public void run() {
            FragmentListActivity.this.mList.focusableViewAvailable(FragmentListActivity.this.mList);
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.compatibility.FragmentListActivity.2 */
    class C06342 implements OnItemClickListener {
        C06342() {
        }

        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            FragmentListActivity.this.onListItemClick((ListView) parent, v, position, id);
        }
    }

    public FragmentListActivity() {
        this.mHandler = new Handler();
        this.mFinishedStart = false;
        this.mRequestFocus = new C06331();
        this.mOnClickListener = new C06342();
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
    }

    protected void onRestoreInstanceState(Bundle state) {
        ensureList();
        super.onRestoreInstanceState(state);
    }

    public void onContentChanged() {
        super.onContentChanged();
        View emptyView = findViewById(16908292);
        this.mList = (ListView) findViewById(16908298);
        if (this.mList == null) {
            throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
        }
        if (emptyView != null) {
            this.mList.setEmptyView(emptyView);
        }
        this.mList.setOnItemClickListener(this.mOnClickListener);
        if (this.mFinishedStart) {
            setListAdapter(this.mAdapter);
        }
        this.mHandler.post(this.mRequestFocus);
        this.mFinishedStart = true;
    }

    public void setListAdapter(ListAdapter adapter) {
        synchronized (this) {
            ensureList();
            this.mAdapter = adapter;
            this.mList.setAdapter(adapter);
        }
    }

    public void setSelection(int position) {
        this.mList.setSelection(position);
    }

    public int getSelectedItemPosition() {
        return this.mList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        return this.mList.getSelectedItemId();
    }

    public ListView getListView() {
        ensureList();
        return this.mList;
    }

    public ListAdapter getListAdapter() {
        return this.mAdapter;
    }

    private void ensureList() {
        if (this.mList == null) {
            setContentView(C0621R.layout.uv_list_content);
        }
    }
}
