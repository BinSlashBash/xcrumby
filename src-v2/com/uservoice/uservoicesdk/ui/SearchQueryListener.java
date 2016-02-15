/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.widget.SearchView
 *  android.widget.SearchView$OnQueryTextListener
 */
package com.uservoice.uservoicesdk.ui;

import android.widget.SearchView;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.ui.SearchAdapter;

public class SearchQueryListener
implements SearchView.OnQueryTextListener {
    private final SearchActivity searchActivity;

    public SearchQueryListener(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onQueryTextChange(String string2) {
        this.searchActivity.getSearchAdapter().performSearch(string2);
        if (string2.length() > 0) {
            this.searchActivity.showSearch();
            do {
                return true;
                break;
            } while (true);
        }
        this.searchActivity.hideSearch();
        return true;
    }

    public boolean onQueryTextSubmit(String string2) {
        this.searchActivity.getSearchAdapter().performSearch(string2);
        return true;
    }
}

