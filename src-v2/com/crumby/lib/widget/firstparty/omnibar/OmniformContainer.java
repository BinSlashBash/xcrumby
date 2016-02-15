/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.accounts.AccountManager
 *  android.accounts.AccountManagerCallback
 *  android.accounts.AccountManagerFuture
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.FormSearchHandler;
import com.crumby.lib.SearchForm;
import com.crumby.lib.authentication.UserData;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.FormTabButton;
import com.crumby.lib.widget.firstparty.omnibar.TabManager;
import com.crumby.lib.widget.firstparty.omnibar.TabSwitchListener;
import com.squareup.otto.Bus;
import java.util.Iterator;
import java.util.List;

public class OmniformContainer
extends LinearLayout
implements TabSwitchListener {
    private AnalyticsCategories category;
    private TextView formTitle;
    private FragmentIndex index;
    private String lastQuery;
    private SearchForm searchForm;
    private FormTabButton searchFormTab;
    private ViewGroup searchFormView;
    private FormTabButton settingsTab;
    TextView.OnEditorActionListener submitOnDone;
    private Button submitSearch;
    private TabManager tabManager;
    private ViewGroup userData;
    private FormTabButton userTab;

    public OmniformContainer(Context context) {
        super(context);
    }

    public OmniformContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OmniformContainer(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void addNewAccount(String string2, String string3) {
        AccountManager.get((Context)this.getContext()).addAccount(string2, string3, null, null, (Activity)this.getContext(), (AccountManagerCallback)new AccountManagerCallback<Bundle>(){

            public void run(AccountManagerFuture<Bundle> bundle) {
                try {
                    bundle = (Bundle)bundle.getResult();
                    Log.d((String)"udinic", (String)("AddNewAccount Bundle is " + (Object)bundle));
                    return;
                }
                catch (Exception var1_2) {
                    var1_2.printStackTrace();
                    return;
                }
            }
        }, null);
    }

    private void alter(String string2, FragmentIndex fragmentIndex) {
        this.lastQuery = string2;
        if (this.index == fragmentIndex) {
            if (this.searchForm != null) {
                this.searchForm.setFormData(string2);
            }
            return;
        }
        this.index = fragmentIndex;
        this.searchFormView.removeAllViews();
        if (fragmentIndex != null) {
            this.prepareSearchForm(string2);
            this.prepareUserData();
            return;
        }
        this.setVisibility(8);
    }

    private void getAuthToken() {
        ((GalleryViewer)this.getContext()).authLoginPromptIfNeeded(this.index.getAccountType(), new AccountManagerCallback<Bundle>(){

            public void run(AccountManagerFuture<Bundle> object) {
                try {
                    object = ((Bundle)object.getResult()).getString("authAccount");
                    OmniformContainer.this.userData.setVisibility(0);
                    ((UserData)OmniformContainer.this.userData.getChildAt(0)).fillWith(object);
                    OmniformContainer.this.findViewById(2131493089).setVisibility(8);
                    return;
                }
                catch (Exception var1_2) {
                    OmniformContainer.this.userData.setVisibility(8);
                    OmniformContainer.this.findViewById(2131493089).setVisibility(0);
                    ((TextView)OmniformContainer.this.findViewById(2131493090)).setText((CharSequence)var1_2.getMessage());
                    return;
                }
            }
        });
    }

    private FragmentIndex getNearestSearchIndex(String object) {
        for (object = com.crumby.lib.router.FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl((String)object); object != null && object.getSearchFormId() == -1; object = object.getParent()) {
        }
        return object;
    }

    private void prepareSearchForm(String string2) {
        int n2;
        if (this.submitOnDone == null) {
            this.submitOnDone = new TextView.OnEditorActionListener(){

                public boolean onEditorAction(TextView textView, int n2, KeyEvent keyEvent) {
                    if (keyEvent != null && keyEvent.getKeyCode() == 66 || n2 == 6) {
                        OmniformContainer.this.submitSearch.callOnClick();
                    }
                    return false;
                }
            };
        }
        OmniformContainer.inflate((Context)this.getContext(), (int)this.index.getSearchFormId(), (ViewGroup)this.searchFormView);
        FormSearchHandler formSearchHandler = this.index.getFormSearchHandler();
        String string3 = this.index.getBreadcrumbName().toUpperCase();
        int n3 = n2 = this.index.getRootBreadcrumbIcon();
        String string4 = string3;
        if (formSearchHandler != null) {
            string4 = formSearchHandler.getTitle(this.index, string2);
            int n4 = formSearchHandler.getIcon(this.index, string2);
            if (string4 != null) {
                string3 = string4;
            }
            n3 = n2;
            string4 = string3;
            if (n4 != 0) {
                n3 = n4;
                string4 = string3;
            }
        }
        this.formTitle.setText((CharSequence)string4);
        this.submitSearch.setCompoundDrawablesWithIntrinsicBounds(n3, 0, 0, 0);
        this.submitSearch.setText((CharSequence)("Search " + this.index.getBreadcrumbName()));
        this.searchForm = new SearchForm(this.searchFormView);
        this.searchForm.setFormData(string2);
        this.searchForm.setEditorActionListener(this.submitOnDone);
        this.setVisibility(0);
    }

    private void prepareUserData() {
        this.userData.removeAllViews();
        if (this.index.getAccountType() != null) {
            this.userTab.setVisibility(0);
            OmniformContainer.inflate((Context)this.getContext(), (int)this.index.getAccountLayout(), (ViewGroup)this.userData);
            return;
        }
        this.userTab.setVisibility(8);
    }

    public void alter(String string2) {
        if (string2.equals(this.lastQuery)) {
            return;
        }
        this.alter(string2, this.getNearestSearchIndex(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void alter(List<Breadcrumb> object) {
        CrDb.logTime("search form container", "alter", true);
        Breadcrumb breadcrumb = null;
        String string2 = null;
        Iterator iterator = object.iterator();
        object = breadcrumb;
        while (iterator.hasNext()) {
            breadcrumb = (Breadcrumb)((Object)iterator.next());
            if (breadcrumb.getFragmentIndex().getSearchFormId() == -1) continue;
            object = breadcrumb.getFragmentIndex();
            string2 = breadcrumb.getUrl();
        }
        if (object != null) {
            this.alter(string2, (FragmentIndex)object);
        } else {
            this.userTab.hide();
        }
        CrDb.logTime("search form container", "alter", false);
    }

    public void backToSearchForm() {
        this.tabManager.onClick((View)this.searchFormTab);
    }

    public boolean isActive() {
        if (this.index != null) {
            return true;
        }
        return false;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.category = AnalyticsCategories.OMNIBAR;
        this.searchFormView = (ViewGroup)this.findViewById(2131493086);
        this.formTitle = (TextView)this.findViewById(2131493081);
        this.submitSearch = (Button)this.findViewById(2131493093);
        this.submitSearch.setOnClickListener(new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View object) {
                object = OmniformContainer.this.index.getFormSearchHandler() != null ? OmniformContainer.this.index.getFormSearchHandler().getUrlForSearch(OmniformContainer.this.lastQuery, OmniformContainer.this.searchForm) : OmniformContainer.this.index.getBaseUrl() + "?" + OmniformContainer.this.searchForm.encodeFormData();
                Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, OmniformContainer.this.category.getName() + " form search", OmniformContainer.this.index.getBreadcrumbName() + " " + (String)object);
                BusProvider.BUS.get().post(new UrlChangeEvent((String)object));
            }
        });
        this.searchFormTab = (FormTabButton)this.findViewById(2131493082);
        this.searchFormTab.setView((View)this.searchFormView);
        this.userTab = (FormTabButton)this.findViewById(2131493083);
        this.userTab.setView(this.findViewById(2131493087));
        this.settingsTab = (FormTabButton)this.findViewById(2131493084);
        this.settingsTab.setView(this.findViewById(2131493092));
        this.tabManager = new TabManager(this, this.searchFormTab, this.userTab, this.settingsTab);
        this.searchFormTab.callOnClick();
        this.userData = (ViewGroup)this.findViewById(2131493088);
        this.findViewById(2131493091).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                OmniformContainer.this.getAuthToken();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onTabSwitch(FormTabButton formTabButton) {
        this.submitSearch.setVisibility(8);
        if (formTabButton == this.searchFormTab) {
            Analytics.INSTANCE.newEvent(this.category, "tab", "search");
            this.submitSearch.setVisibility(0);
            return;
        } else {
            if (formTabButton != this.userTab || this.index.getAccountType() == null) return;
            {
                Analytics.INSTANCE.newEvent(this.category, "tab", "user");
                this.getAuthToken();
                return;
            }
        }
    }

    public void showAccount() {
        if (this.userTab.isShown()) {
            this.tabManager.onClick((View)this.userTab);
        }
    }

    public void showAsInGrid(String string2) {
        this.category = AnalyticsCategories.GALLERY_GRID;
        this.setVisibility(0);
        this.formTitle.setVisibility(8);
        this.alter(string2);
    }

}

