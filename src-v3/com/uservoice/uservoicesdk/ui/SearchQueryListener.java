package com.uservoice.uservoicesdk.ui;

import android.widget.SearchView.OnQueryTextListener;
import com.uservoice.uservoicesdk.activity.SearchActivity;

public class SearchQueryListener implements OnQueryTextListener {
    private final SearchActivity searchActivity;

    public SearchQueryListener(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    public boolean onQueryTextSubmit(String query) {
        this.searchActivity.getSearchAdapter().performSearch(query);
        return true;
    }

    public boolean onQueryTextChange(String query) {
        this.searchActivity.getSearchAdapter().performSearch(query);
        if (query.length() > 0) {
            this.searchActivity.showSearch();
        } else {
            this.searchActivity.hideSearch();
        }
        return true;
    }
}
