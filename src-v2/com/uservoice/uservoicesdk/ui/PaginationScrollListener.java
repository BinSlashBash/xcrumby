/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 */
package com.uservoice.uservoicesdk.ui;

import android.widget.AbsListView;
import com.uservoice.uservoicesdk.ui.PaginatedAdapter;

public class PaginationScrollListener
implements AbsListView.OnScrollListener {
    private final PaginatedAdapter<?> adapter;

    public PaginationScrollListener(PaginatedAdapter<?> paginatedAdapter) {
        this.adapter = paginatedAdapter;
    }

    public void onScroll(AbsListView absListView, int n2, int n3, int n4) {
        if (n2 + n3 >= n4) {
            this.adapter.loadMore();
        }
    }

    public void onScrollStateChanged(AbsListView absListView, int n2) {
    }
}

