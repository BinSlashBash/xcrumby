package com.crumby.lib.widget.firstparty.omnibar;

import android.view.View;
import java.util.List;

public interface BreadcrumbListModifier {
    void addNew(UrlCrumb... urlCrumbArr);

    int getBreadcrumbCount();

    List<Breadcrumb> getChildren();

    void scrollIntoView(View view);
}
