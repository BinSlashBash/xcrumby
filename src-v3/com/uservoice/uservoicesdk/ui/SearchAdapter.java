package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import android.widget.BaseAdapter;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTask;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public abstract class SearchAdapter<T> extends BaseAdapter {
    protected Context context;
    protected String currentQuery;
    protected SearchTask currentSearch;
    protected boolean loading;
    protected String pendingQuery;
    protected int scope;
    protected boolean searchActive;
    protected List<T> searchResults;

    private class SearchTask extends TimerTask {
        private final String query;
        private boolean stop;
        private RestTask task;

        /* renamed from: com.uservoice.uservoicesdk.ui.SearchAdapter.SearchTask.1 */
        class C14571 extends DefaultCallback<List<T>> {
            C14571(Context x0) {
                super(x0);
            }

            public void onModel(List<T> model) {
                if (!SearchTask.this.stop) {
                    SearchAdapter.this.searchResults = model;
                    SearchAdapter.this.loading = false;
                    SearchAdapter.this.notifyDataSetChanged();
                    SearchAdapter.this.searchResultsUpdated();
                }
            }
        }

        public SearchTask(String query) {
            this.query = query;
        }

        public boolean cancel() {
            this.stop = true;
            if (this.task != null) {
                this.task.cancel(true);
            }
            return true;
        }

        public void run() {
            SearchAdapter.this.currentQuery = this.query;
            this.task = SearchAdapter.this.search(this.query, new C14571(SearchAdapter.this.context));
            if (this.task == null) {
                SearchAdapter.this.loading = false;
            }
        }
    }

    public SearchAdapter() {
        this.searchResults = new ArrayList();
        this.searchActive = false;
    }

    public void performSearch(String query) {
        this.pendingQuery = query;
        if (query.length() == 0) {
            this.searchResults = new ArrayList();
            this.loading = false;
            notifyDataSetChanged();
            return;
        }
        this.loading = true;
        notifyDataSetChanged();
        if (this.currentSearch != null) {
            this.currentSearch.cancel();
        }
        this.currentSearch = new SearchTask(query);
        this.currentSearch.run();
    }

    public void setSearchActive(boolean searchActive) {
        this.searchActive = searchActive;
        this.loading = false;
        notifyDataSetChanged();
    }

    protected void searchResultsUpdated() {
    }

    protected boolean shouldShowSearchResults() {
        return this.searchActive && this.pendingQuery != null && this.pendingQuery.length() > 0;
    }

    protected RestTask search(String query, Callback<List<T>> callback) {
        return null;
    }

    public void setScope(int scope) {
        this.scope = scope;
        notifyDataSetChanged();
    }
}
