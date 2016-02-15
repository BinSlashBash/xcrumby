package com.uservoice.uservoicesdk.ui;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import com.uservoice.uservoicesdk.activity.SearchActivity;

@SuppressLint({"NewApi"})
public class SearchExpandListener implements OnActionExpandListener {
    private final SearchActivity searchActivity;

    public SearchExpandListener(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    public boolean onMenuItemActionExpand(MenuItem item) {
        this.searchActivity.getSearchAdapter().setSearchActive(true);
        return true;
    }

    public boolean onMenuItemActionCollapse(MenuItem item) {
        this.searchActivity.getSearchAdapter().setSearchActive(false);
        this.searchActivity.hideSearch();
        return true;
    }
}
