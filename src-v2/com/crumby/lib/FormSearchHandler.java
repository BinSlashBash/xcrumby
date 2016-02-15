/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib;

import com.crumby.lib.SearchForm;
import com.crumby.lib.router.FragmentIndex;

public interface FormSearchHandler {
    public int getIcon(FragmentIndex var1, String var2);

    public String getTitle(FragmentIndex var1, String var2);

    public String getUrlForSearch(String var1, SearchForm var2);
}

