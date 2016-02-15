/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.HorizontalScrollView
 *  android.widget.LinearLayout
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.crumby.lib.widget.firstparty.omnibar.OmnibarView;
import com.crumby.lib.widget.firstparty.omnibar.UrlCrumb;
import com.squareup.otto.Bus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BreadcrumbList
extends LinearLayout
implements View.OnClickListener,
BreadcrumbListModifier {
    OmnibarView omnibarView;
    private View.OnClickListener onClickListener;
    HorizontalScrollView scrollView;

    public BreadcrumbList(Context context) {
        super(context);
    }

    public BreadcrumbList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BreadcrumbList(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void addNewBreadcrumbs(List<FragmentIndex> list, int n2, String string2) {
        while (n2 < list.size()) {
            String string3 = null;
            if (n2 == list.size() - 1) {
                string3 = string2;
            }
            this.appendBreadcrumb(n2, string3, list.get(n2));
            ++n2;
        }
    }

    private void appendBreadcrumb(int n2, String object, FragmentIndex fragmentIndex) {
        object = new Breadcrumb(this.getContext(), fragmentIndex, n2, (String)object);
        object.setOnClickListener((View.OnClickListener)this);
        this.addView((View)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int findTruncatePoint(List<FragmentIndex> list) {
        int n2 = 0;
        while (n2 < Math.min(list.size(), this.getBreadcrumbCount())) {
            Breadcrumb breadcrumb = (Breadcrumb)this.getChildAt(n2);
            boolean bl2 = list.get(n2).matches(breadcrumb.getFragmentIndex());
            if (breadcrumb == null || !bl2) {
                return n2;
            }
            ++n2;
        }
        return n2;
    }

    private Breadcrumb getLastBreadcrumb() {
        return (Breadcrumb)this.getChildAt(this.getChildCount() - 1);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void setFocus(Breadcrumb breadcrumb, boolean bl2) {
        if (breadcrumb == null) {
            return;
        }
        Iterator<Breadcrumb> iterator = this.getChildren().iterator();
        do {
            if (!iterator.hasNext()) {
                if (!bl2) return;
                this.scrollIntoView((View)breadcrumb);
                return;
            }
            Breadcrumb breadcrumb2 = iterator.next();
            if (breadcrumb != breadcrumb2) {
                breadcrumb2.defocus();
                continue;
            }
            breadcrumb2.focus();
        } while (true);
    }

    private void setFocusToLast(boolean bl2) {
        this.setFocus((Breadcrumb)this.getChildAt(this.getChildCount() - 1), bl2);
    }

    private void truncateBreadcrumbs(int n2) {
        while (this.getBreadcrumbCount() != n2) {
            this.removeViewAt(n2);
        }
    }

    @Override
    public /* varargs */ void addNew(UrlCrumb ... arrurlCrumb) {
        for (UrlCrumb urlCrumb : arrurlCrumb) {
            Breadcrumb breadcrumb = new Breadcrumb(this.getContext(), FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(urlCrumb.url), urlCrumb.position, urlCrumb.url);
            breadcrumb.setOnClickListener((View.OnClickListener)this);
            this.addView((View)breadcrumb, urlCrumb.position);
        }
    }

    public void delayScrollRight() {
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                BreadcrumbList.this.scrollView.fullScroll(66);
            }
        }, 200);
    }

    public void delayScrollToLast() {
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                BreadcrumbList.this.scrollIntoView((View)BreadcrumbList.this.getLastBreadcrumb());
            }
        }, 200);
    }

    @Override
    public int getBreadcrumbCount() {
        return this.getChildCount();
    }

    @Override
    public List<Breadcrumb> getChildren() {
        int n2 = this.getBreadcrumbCount();
        ArrayList<Breadcrumb> arrayList = new ArrayList<Breadcrumb>();
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add((Breadcrumb)this.getChildAt(i2));
        }
        return arrayList;
    }

    public int getCurrentSearchForm() {
        return ((Breadcrumb)this.getChildAt(this.getBreadcrumbCount() - 1)).getFragmentIndex().getSearchFormId();
    }

    public void initialize(OmnibarView omnibarView, HorizontalScrollView horizontalScrollView) {
        this.omnibarView = omnibarView;
        this.scrollView = horizontalScrollView;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onClick(View view) {
        Breadcrumb breadcrumb = (Breadcrumb)view;
        this.omnibarView.dismissGalleryPanel();
        if (breadcrumb.hasUrl() && breadcrumb != this.getLastBreadcrumb()) {
            Analytics.INSTANCE.newNavigationEvent("breadcrumb click", breadcrumb.getUrl());
            BusProvider.BUS.get().post(new UrlChangeEvent(breadcrumb.getUrl()));
        } else {
            this.onClickListener.onClick(view);
        }
        this.setFocusToLast();
    }

    public void replaceBreadcrumbs(List<Breadcrumb> object) {
        this.removeAllViews();
        object = object.iterator();
        while (object.hasNext()) {
            this.addView((View)((Breadcrumb)((Object)object.next())));
        }
        this.setFocusToLast(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void scrollIntoView(View view) {
        if (this.scrollView == null) {
            return;
        }
        int n2 = this.scrollView.getScrollX() < view.getLeft() ? view.getRight() + 10 : view.getLeft() - 10;
        this.scrollView.smoothScrollTo(n2, 0);
    }

    public void setFocusToLast() {
        this.setFocusToLast(true);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.onClickListener = onClickListener;
    }

    public void updateBreadcrumbs(String string2, List<FragmentIndex> object) {
        int n2 = this.findTruncatePoint((List<FragmentIndex>)object);
        this.truncateBreadcrumbs(n2);
        this.addNewBreadcrumbs((List<FragmentIndex>)object, n2, string2);
        object = this.getLastBreadcrumb();
        if (object != null) {
            this.removeView((View)object);
            this.appendBreadcrumb(this.getChildCount(), string2, object.getFragmentIndex());
        }
        this.setFocusToLast(true);
    }

    public void updateBreadcrumbsWithTruncate(String string2, FragmentIndex fragmentIndex) {
        int n2 = this.getChildCount() - 1;
        do {
            if (n2 <= 0 || ((Breadcrumb)this.getChildAt(n2)).getFragmentIndex() == fragmentIndex) {
                this.truncateBreadcrumbs(n2);
                this.updateBreadcrumbs(string2, FragmentRouter.INSTANCE.buildBreadCrumbPath(string2));
                return;
            }
            --n2;
        } while (true);
    }

}

