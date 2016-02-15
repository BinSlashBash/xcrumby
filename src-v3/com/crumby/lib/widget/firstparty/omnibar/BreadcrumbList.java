package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import java.util.ArrayList;
import java.util.List;

public class BreadcrumbList extends LinearLayout implements OnClickListener, BreadcrumbListModifier {
    OmnibarView omnibarView;
    private OnClickListener onClickListener;
    HorizontalScrollView scrollView;

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.BreadcrumbList.1 */
    class C01511 implements Runnable {
        C01511() {
        }

        public void run() {
            BreadcrumbList.this.scrollView.fullScroll(66);
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.BreadcrumbList.2 */
    class C01522 implements Runnable {
        C01522() {
        }

        public void run() {
            BreadcrumbList.this.scrollIntoView(BreadcrumbList.this.getLastBreadcrumb());
        }
    }

    public BreadcrumbList(Context context) {
        super(context);
    }

    public BreadcrumbList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BreadcrumbList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(OmnibarView omnibarView, HorizontalScrollView scrollView) {
        this.omnibarView = omnibarView;
        this.scrollView = scrollView;
    }

    public int getBreadcrumbCount() {
        return getChildCount();
    }

    public int getCurrentSearchForm() {
        return ((Breadcrumb) getChildAt(getBreadcrumbCount() - 1)).getFragmentIndex().getSearchFormId();
    }

    public List<Breadcrumb> getChildren() {
        int size = getBreadcrumbCount();
        List<Breadcrumb> breadcrumbs = new ArrayList();
        for (int i = 0; i < size; i++) {
            breadcrumbs.add((Breadcrumb) getChildAt(i));
        }
        return breadcrumbs;
    }

    public void addNew(UrlCrumb... crumbs) {
        for (UrlCrumb crumb : crumbs) {
            Breadcrumb breadcrumb = new Breadcrumb(getContext(), FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(crumb.url), crumb.position, crumb.url);
            breadcrumb.setOnClickListener(this);
            addView(breadcrumb, crumb.position);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int findTruncatePoint(java.util.List<com.crumby.lib.router.FragmentIndex> r7) {
        /*
        r6 = this;
        r2 = 0;
    L_0x0001:
        r4 = r7.size();
        r5 = r6.getBreadcrumbCount();
        r4 = java.lang.Math.min(r4, r5);
        if (r2 >= r4) goto L_0x0027;
    L_0x000f:
        r0 = r6.getChildAt(r2);
        r0 = (com.crumby.lib.widget.firstparty.omnibar.Breadcrumb) r0;
        r1 = r7.get(r2);
        r1 = (com.crumby.lib.router.FragmentIndex) r1;
        r4 = r0.getFragmentIndex();
        r3 = r1.matches(r4);
        if (r0 == 0) goto L_0x0027;
    L_0x0025:
        if (r3 != 0) goto L_0x0028;
    L_0x0027:
        return r2;
    L_0x0028:
        r2 = r2 + 1;
        goto L_0x0001;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crumby.lib.widget.firstparty.omnibar.BreadcrumbList.findTruncatePoint(java.util.List):int");
    }

    private void truncateBreadcrumbs(int start) {
        while (getBreadcrumbCount() != start) {
            removeViewAt(start);
        }
    }

    private void appendBreadcrumb(int position, String url, FragmentIndex index) {
        Breadcrumb breadcrumb = new Breadcrumb(getContext(), index, position, url);
        breadcrumb.setOnClickListener(this);
        addView(breadcrumb);
    }

    private void addNewBreadcrumbs(List<FragmentIndex> fragmentIndexes, int truncatePosition, String url) {
        for (int n = truncatePosition; n < fragmentIndexes.size(); n++) {
            String urlToInsert = null;
            if (n == fragmentIndexes.size() - 1) {
                urlToInsert = url;
            }
            appendBreadcrumb(n, urlToInsert, (FragmentIndex) fragmentIndexes.get(n));
        }
    }

    private Breadcrumb getLastBreadcrumb() {
        return (Breadcrumb) getChildAt(getChildCount() - 1);
    }

    public void updateBreadcrumbsWithTruncate(String url, FragmentIndex searchIndex) {
        int i = getChildCount() - 1;
        while (i > 0 && ((Breadcrumb) getChildAt(i)).getFragmentIndex() != searchIndex) {
            i--;
        }
        truncateBreadcrumbs(i);
        updateBreadcrumbs(url, FragmentRouter.INSTANCE.buildBreadCrumbPath(url));
    }

    public void updateBreadcrumbs(String url, List<FragmentIndex> fragmentIndexes) {
        int truncatePosition = findTruncatePoint(fragmentIndexes);
        truncateBreadcrumbs(truncatePosition);
        addNewBreadcrumbs(fragmentIndexes, truncatePosition, url);
        Breadcrumb lastBreadcrumb = getLastBreadcrumb();
        if (lastBreadcrumb != null) {
            removeView(lastBreadcrumb);
            appendBreadcrumb(getChildCount(), url, lastBreadcrumb.getFragmentIndex());
        }
        setFocusToLast(true);
    }

    public void replaceBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        removeAllViews();
        for (Breadcrumb breadcrumb : breadcrumbs) {
            addView(breadcrumb);
        }
        setFocusToLast(false);
    }

    public void setFocusToLast() {
        setFocusToLast(true);
    }

    private void setFocusToLast(boolean scroll) {
        setFocus((Breadcrumb) getChildAt(getChildCount() - 1), scroll);
    }

    public void scrollIntoView(View v) {
        if (this.scrollView != null) {
            int position;
            if (this.scrollView.getScrollX() < v.getLeft()) {
                position = v.getRight() + 10;
            } else {
                position = v.getLeft() - 10;
            }
            this.scrollView.smoothScrollTo(position, 0);
        }
    }

    private void setFocus(Breadcrumb focusedBreadcrumb, boolean scroll) {
        if (focusedBreadcrumb != null) {
            for (Breadcrumb breadcrumb : getChildren()) {
                if (focusedBreadcrumb != breadcrumb) {
                    breadcrumb.defocus();
                } else {
                    breadcrumb.focus();
                }
            }
            if (scroll) {
                scrollIntoView(focusedBreadcrumb);
            }
        }
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.onClickListener = l;
    }

    public void onClick(View v) {
        Breadcrumb breadcrumb = (Breadcrumb) v;
        this.omnibarView.dismissGalleryPanel();
        if (!breadcrumb.hasUrl() || breadcrumb == getLastBreadcrumb()) {
            this.onClickListener.onClick(v);
        } else {
            Analytics.INSTANCE.newNavigationEvent("breadcrumb click", breadcrumb.getUrl());
            BusProvider.BUS.get().post(new UrlChangeEvent(breadcrumb.getUrl()));
        }
        setFocusToLast();
    }

    public void delayScrollRight() {
        new Handler().postDelayed(new C01511(), 200);
    }

    public void delayScrollToLast() {
        new Handler().postDelayed(new C01522(), 200);
    }
}
