/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.view.View;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.UrlCrumb;
import java.util.List;

public interface BreadcrumbListModifier {
    public /* varargs */ void addNew(UrlCrumb ... var1);

    public int getBreadcrumbCount();

    public List<Breadcrumb> getChildren();

    public void scrollIntoView(View var1);
}

