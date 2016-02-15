/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.BaseAdapter
 */
package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import android.widget.BaseAdapter;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public abstract class SearchAdapter<T>
extends BaseAdapter {
    protected Context context;
    protected String currentQuery;
    protected SearchAdapter<T> currentSearch;
    protected boolean loading;
    protected String pendingQuery;
    protected int scope;
    protected boolean searchActive = false;
    protected List<T> searchResults = new ArrayList<T>();

    public void performSearch(String string2) {
        this.pendingQuery = string2;
        if (string2.length() == 0) {
            this.searchResults = new ArrayList<T>();
            this.loading = false;
            this.notifyDataSetChanged();
            return;
        }
        this.loading = true;
        this.notifyDataSetChanged();
        if (this.currentSearch != null) {
            this.currentSearch.cancel();
        }
        this.currentSearch = new SearchTask(string2);
        this.currentSearch.run();
    }

    protected RestTask search(String string2, Callback<List<T>> callback) {
        return null;
    }

    protected void searchResultsUpdated() {
    }

    public void setScope(int n2) {
        this.scope = n2;
        this.notifyDataSetChanged();
    }

    public void setSearchActive(boolean bl2) {
        this.searchActive = bl2;
        this.loading = false;
        this.notifyDataSetChanged();
    }

    protected boolean shouldShowSearchResults() {
        if (this.searchActive && this.pendingQuery != null && this.pendingQuery.length() > 0) {
            return true;
        }
        return false;
    }

    private class SearchTask
    extends TimerTask {
        private final String query;
        private boolean stop;
        private RestTask task;

        public SearchTask(String string2) {
            this.query = string2;
        }

        @Override
        public boolean cancel() {
            this.stop = true;
            if (this.task != null) {
                this.task.cancel(true);
            }
            return true;
        }

        @Override
        public void run() {
            SearchAdapter.this.currentQuery = this.query;
            this.task = SearchAdapter.this.search(this.query, new DefaultCallback<List<T>>(SearchAdapter.this.context){

                @Override
                public void onModel(List<T> list) {
                    if (!SearchTask.this.stop) {
                        SearchAdapter.this.searchResults = list;
                        SearchAdapter.this.loading = false;
                        SearchAdapter.this.notifyDataSetChanged();
                        SearchAdapter.this.searchResultsUpdated();
                    }
                }
            });
            if (this.task == null) {
                SearchAdapter.this.loading = false;
            }
        }

    }

}

