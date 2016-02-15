package com.crumby.lib.widget.firstparty.omnibar;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.events.LoginEvent;
import com.crumby.lib.events.OmniformEvent;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.router.FragmentRouter;
import com.squareup.otto.Subscribe;
import java.util.List;

public class BreadcrumbContainer extends RelativeLayout {
    private BreadcrumbList breadcrumbList;
    private FragmentStatusButton fragmentStatusButton;
    private String loggedInAccount;
    private View loggedInIndicator;

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.BreadcrumbContainer.1 */
    class C01491 implements OnClickListener {
        final /* synthetic */ OmnibarView val$omnibarView;

        C01491(OmnibarView omnibarView) {
            this.val$omnibarView = omnibarView;
        }

        public void onClick(View v) {
            BreadcrumbContainer.this.callOnClick();
            this.val$omnibarView.showAccount();
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.BreadcrumbContainer.2 */
    class C01502 implements AccountManagerCallback<Bundle> {
        final /* synthetic */ String val$accountType;

        C01502(String str) {
            this.val$accountType = str;
        }

        public void run(AccountManagerFuture<Bundle> future) {
            try {
                future.getResult();
                BreadcrumbContainer.this.loggedInAccount = this.val$accountType;
                BreadcrumbContainer.this.loggedInIndicator.setVisibility(0);
            } catch (Exception e) {
                BreadcrumbContainer.this.loggedInIndicator.setVisibility(8);
            }
        }
    }

    public BreadcrumbContainer(Context context) {
        super(context);
    }

    public BreadcrumbContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BreadcrumbContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(OmnibarView omnibarView) {
        View breadcrumbScrollView = findViewById(C0065R.id.breadcrumb_scroll);
        this.breadcrumbList = (BreadcrumbList) findViewById(C0065R.id.breadcrumb_list);
        this.breadcrumbList.initialize(omnibarView, (HorizontalScrollView) breadcrumbScrollView);
        this.fragmentStatusButton = (FragmentStatusButton) findViewById(C0065R.id.refresh_fragment);
        this.loggedInIndicator = findViewById(C0065R.id.logged_in_indicator);
        this.loggedInIndicator.setClickable(true);
        this.loggedInIndicator.setOnClickListener(new C01491(omnibarView));
        BusProvider.BUS.get().register(this);
    }

    @Subscribe
    public void onOmniformEvent(OmniformEvent event) {
        if (event.eventName.equals(OmniformEvent.LOGIN)) {
            this.loggedInIndicator.callOnClick();
        } else {
            callOnClick();
        }
    }

    public void hide() {
        setVisibility(8);
        this.breadcrumbList.setFocusToLast();
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.breadcrumbList.setOnClickListener(l);
    }

    public int getCurrentSearchForm() {
        return this.breadcrumbList.getCurrentSearchForm();
    }

    public void focus() {
        this.breadcrumbList.setFocusToLast();
    }

    public void stopLoadingMode() {
        this.fragmentStatusButton.stopLoadingMode();
    }

    public void refreshMode() {
        this.fragmentStatusButton.refreshMode();
    }

    public void newPage(GalleryViewerFragment fragment, boolean truncate) {
        Analytics.INSTANCE.newPage(fragment);
        String url = fragment.getUrl();
        if (fragment.getBreadcrumbs() != null) {
            this.breadcrumbList.replaceBreadcrumbs(fragment.getBreadcrumbs());
        } else {
            if (truncate) {
                this.breadcrumbList.updateBreadcrumbsWithTruncate(url, FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(url));
            } else {
                this.breadcrumbList.updateBreadcrumbs(url, FragmentRouter.INSTANCE.buildBreadCrumbPath(url));
            }
            fragment.setBreadcrumbs(this.breadcrumbList);
        }
        displayLoginIndicator(null);
        this.breadcrumbList.delayScrollRight();
    }

    private void displayLoginIndicator(String checkAccountType) {
        Breadcrumb breadcrumb = (Breadcrumb) this.breadcrumbList.getChildAt(0);
        if (breadcrumb != null) {
            String accountType = breadcrumb.getFragmentIndex().getAccountType();
            if (accountType == null) {
                this.loggedInIndicator.setVisibility(8);
            } else if (checkAccountType == null) {
                if (this.loggedInAccount == null || !this.loggedInAccount.equals(accountType)) {
                    CrDb.m0d("breadcrumb container", "different account type! time to check!");
                    this.loggedInIndicator.setVisibility(8);
                    ((GalleryViewer) getContext()).checkLogin(accountType, new C01502(accountType));
                    return;
                }
                CrDb.m0d("breadcrumb container", "same account type as last one, no need to check!");
                this.loggedInIndicator.setVisibility(0);
            } else if (accountType.equals(checkAccountType)) {
                this.loggedInIndicator.setVisibility(0);
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BusProvider.BUS.get().unregister(this);
    }

    public void override(List<Breadcrumb> breadcrumbs) {
        this.breadcrumbList.removeAllViews();
        for (Breadcrumb breadcrumb : breadcrumbs) {
            this.breadcrumbList.addView(breadcrumb);
        }
        this.breadcrumbList.setFocusToLast();
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        displayLoginIndicator(event.accountType);
    }

    public void show() {
        setVisibility(0);
    }

    public void removeBreadcrumbs() {
        this.breadcrumbList.removeAllViews();
    }

    public List<Breadcrumb> getCurrentBreadcrumbs() {
        return this.breadcrumbList.getChildren();
    }
}
