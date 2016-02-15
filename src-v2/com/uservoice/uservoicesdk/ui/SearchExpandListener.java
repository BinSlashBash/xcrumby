/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 */
package com.uservoice.uservoicesdk.ui;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.ui.SearchAdapter;

@SuppressLint(value={"NewApi"})
public class SearchExpandListener
implements MenuItem.OnActionExpandListener {
    private final SearchActivity searchActivity;

    public SearchExpandListener(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        this.searchActivity.getSearchAdapter().setSearchActive(false);
        this.searchActivity.hideSearch();
        return true;
    }

    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        this.searchActivity.getSearchAdapter().setSearchActive(true);
        return true;
    }
}

