package com.uservoice.uservoicesdk.ui;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class PaginationScrollListener implements OnScrollListener {
    private final PaginatedAdapter<?> adapter;

    public PaginationScrollListener(PaginatedAdapter<?> adapter) {
        this.adapter = adapter;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
            this.adapter.loadMore();
        }
    }
}
