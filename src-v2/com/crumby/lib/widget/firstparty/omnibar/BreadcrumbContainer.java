/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.accounts.AccountManagerCallback
 *  android.accounts.AccountManagerFuture
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.HorizontalScrollView
 *  android.widget.RelativeLayout
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.events.LoginEvent;
import com.crumby.lib.events.OmniformEvent;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbList;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.crumby.lib.widget.firstparty.omnibar.FragmentStatusButton;
import com.crumby.lib.widget.firstparty.omnibar.OmnibarView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.Iterator;
import java.util.List;

public class BreadcrumbContainer
extends RelativeLayout {
    private BreadcrumbList breadcrumbList;
    private FragmentStatusButton fragmentStatusButton;
    private String loggedInAccount;
    private View loggedInIndicator;

    public BreadcrumbContainer(Context context) {
        super(context);
    }

    public BreadcrumbContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BreadcrumbContainer(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void displayLoginIndicator(String string2) {
        Object object = (Breadcrumb)this.breadcrumbList.getChildAt(0);
        if (object == null) return;
        if ((object = object.getFragmentIndex().getAccountType()) == null) {
            this.loggedInIndicator.setVisibility(8);
            return;
        }
        if (string2 != null) {
            if (!object.equals(string2)) return;
            this.loggedInIndicator.setVisibility(0);
            return;
        }
        if (this.loggedInAccount != null && this.loggedInAccount.equals(object)) {
            CrDb.d("breadcrumb container", "same account type as last one, no need to check!");
            this.loggedInIndicator.setVisibility(0);
            return;
        }
        CrDb.d("breadcrumb container", "different account type! time to check!");
        this.loggedInIndicator.setVisibility(8);
        ((GalleryViewer)this.getContext()).checkLogin((String)object, new AccountManagerCallback<Bundle>((String)object){
            final /* synthetic */ String val$accountType;

            public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                try {
                    accountManagerFuture.getResult();
                    BreadcrumbContainer.this.loggedInAccount = this.val$accountType;
                    BreadcrumbContainer.this.loggedInIndicator.setVisibility(0);
                    return;
                }
                catch (Exception var1_2) {
                    BreadcrumbContainer.this.loggedInIndicator.setVisibility(8);
                    return;
                }
            }
        });
    }

    public void focus() {
        this.breadcrumbList.setFocusToLast();
    }

    public List<Breadcrumb> getCurrentBreadcrumbs() {
        return this.breadcrumbList.getChildren();
    }

    public int getCurrentSearchForm() {
        return this.breadcrumbList.getCurrentSearchForm();
    }

    public void hide() {
        this.setVisibility(8);
        this.breadcrumbList.setFocusToLast();
    }

    public void initialize(final OmnibarView omnibarView) {
        View view = this.findViewById(2131492909);
        this.breadcrumbList = (BreadcrumbList)this.findViewById(2131492910);
        this.breadcrumbList.initialize(omnibarView, (HorizontalScrollView)view);
        this.fragmentStatusButton = (FragmentStatusButton)this.findViewById(2131492911);
        this.loggedInIndicator = this.findViewById(2131492908);
        this.loggedInIndicator.setClickable(true);
        this.loggedInIndicator.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                BreadcrumbContainer.this.callOnClick();
                omnibarView.showAccount();
            }
        });
        BusProvider.BUS.get().register((Object)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void newPage(GalleryViewerFragment galleryViewerFragment, boolean bl2) {
        Analytics.INSTANCE.newPage(galleryViewerFragment);
        String string2 = galleryViewerFragment.getUrl();
        if (galleryViewerFragment.getBreadcrumbs() != null) {
            this.breadcrumbList.replaceBreadcrumbs(galleryViewerFragment.getBreadcrumbs());
        } else {
            if (!bl2) {
                this.breadcrumbList.updateBreadcrumbs(string2, FragmentRouter.INSTANCE.buildBreadCrumbPath(string2));
            } else {
                this.breadcrumbList.updateBreadcrumbsWithTruncate(string2, FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(string2));
            }
            galleryViewerFragment.setBreadcrumbs(this.breadcrumbList);
        }
        this.displayLoginIndicator(null);
        this.breadcrumbList.delayScrollRight();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BusProvider.BUS.get().unregister((Object)this);
    }

    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent) {
        this.displayLoginIndicator(loginEvent.accountType);
    }

    @Subscribe
    public void onOmniformEvent(OmniformEvent omniformEvent) {
        if (omniformEvent.eventName.equals("Login")) {
            this.loggedInIndicator.callOnClick();
            return;
        }
        this.callOnClick();
    }

    public void override(List<Breadcrumb> object) {
        this.breadcrumbList.removeAllViews();
        object = object.iterator();
        while (object.hasNext()) {
            Breadcrumb breadcrumb = (Breadcrumb)((Object)object.next());
            this.breadcrumbList.addView((View)breadcrumb);
        }
        this.breadcrumbList.setFocusToLast();
    }

    public void refreshMode() {
        this.fragmentStatusButton.refreshMode();
    }

    public void removeBreadcrumbs() {
        this.breadcrumbList.removeAllViews();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.breadcrumbList.setOnClickListener(onClickListener);
    }

    public void show() {
        this.setVisibility(0);
    }

    public void stopLoadingMode() {
        this.fragmentStatusButton.stopLoadingMode();
    }

}

