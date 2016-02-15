/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.activity;

import com.uservoice.uservoicesdk.ui.SearchAdapter;

public interface SearchActivity {
    public SearchAdapter<?> getSearchAdapter();

    public void hideSearch();

    public void showSearch();

    public void updateScopedSearch(int var1, int var2, int var3);
}

