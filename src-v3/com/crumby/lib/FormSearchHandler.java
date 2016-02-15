package com.crumby.lib;

import com.crumby.lib.router.FragmentIndex;

public interface FormSearchHandler {
    int getIcon(FragmentIndex fragmentIndex, String str);

    String getTitle(FragmentIndex fragmentIndex, String str);

    String getUrlForSearch(String str, SearchForm searchForm);
}
