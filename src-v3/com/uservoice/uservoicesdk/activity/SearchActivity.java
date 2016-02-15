package com.uservoice.uservoicesdk.activity;

import com.uservoice.uservoicesdk.ui.SearchAdapter;

public interface SearchActivity {
    SearchAdapter<?> getSearchAdapter();

    void hideSearch();

    void showSearch();

    void updateScopedSearch(int i, int i2, int i3);
}
