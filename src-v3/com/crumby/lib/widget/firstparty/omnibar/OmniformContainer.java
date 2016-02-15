package com.crumby.lib.widget.firstparty.omnibar;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.FormSearchHandler;
import com.crumby.lib.SearchForm;
import com.crumby.lib.authentication.UserData;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.List;

public class OmniformContainer extends LinearLayout implements TabSwitchListener {
    private AnalyticsCategories category;
    private TextView formTitle;
    private FragmentIndex index;
    private String lastQuery;
    private SearchForm searchForm;
    private FormTabButton searchFormTab;
    private ViewGroup searchFormView;
    private FormTabButton settingsTab;
    OnEditorActionListener submitOnDone;
    private Button submitSearch;
    private TabManager tabManager;
    private ViewGroup userData;
    private FormTabButton userTab;

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.OmniformContainer.1 */
    class C01561 implements OnClickListener {
        C01561() {
        }

        public void onClick(View v) {
            String url = UnsupportedUrlFragment.DISPLAY_NAME;
            if (OmniformContainer.this.index.getFormSearchHandler() != null) {
                url = OmniformContainer.this.index.getFormSearchHandler().getUrlForSearch(OmniformContainer.this.lastQuery, OmniformContainer.this.searchForm);
            } else {
                url = OmniformContainer.this.index.getBaseUrl() + "?" + OmniformContainer.this.searchForm.encodeFormData();
            }
            Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, OmniformContainer.this.category.getName() + " form search", OmniformContainer.this.index.getBreadcrumbName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + url);
            BusProvider.BUS.get().post(new UrlChangeEvent(url));
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.OmniformContainer.2 */
    class C01572 implements OnClickListener {
        C01572() {
        }

        public void onClick(View v) {
            OmniformContainer.this.getAuthToken();
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.OmniformContainer.3 */
    class C01583 implements OnEditorActionListener {
        C01583() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if ((event != null && event.getKeyCode() == 66) || actionId == 6) {
                OmniformContainer.this.submitSearch.callOnClick();
            }
            return false;
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.OmniformContainer.4 */
    class C01594 implements AccountManagerCallback<Bundle> {
        C01594() {
        }

        public void run(AccountManagerFuture<Bundle> future) {
            try {
                Log.d("udinic", "AddNewAccount Bundle is " + ((Bundle) future.getResult()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.OmniformContainer.5 */
    class C01605 implements AccountManagerCallback<Bundle> {
        C01605() {
        }

        public void run(AccountManagerFuture<Bundle> future) {
            try {
                String account = ((Bundle) future.getResult()).getString("authAccount");
                OmniformContainer.this.userData.setVisibility(0);
                ((UserData) OmniformContainer.this.userData.getChildAt(0)).fillWith(account);
                OmniformContainer.this.findViewById(C0065R.id.login_encountered_an_error).setVisibility(8);
            } catch (Exception e) {
                OmniformContainer.this.userData.setVisibility(8);
                OmniformContainer.this.findViewById(C0065R.id.login_encountered_an_error).setVisibility(0);
                ((TextView) OmniformContainer.this.findViewById(C0065R.id.login_encountered_an_error_message)).setText(e.getMessage());
            }
        }
    }

    public OmniformContainer(Context context) {
        super(context);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.category = AnalyticsCategories.OMNIBAR;
        this.searchFormView = (ViewGroup) findViewById(C0065R.id.search_form);
        this.formTitle = (TextView) findViewById(C0065R.id.gallery_form_title);
        this.submitSearch = (Button) findViewById(C0065R.id.search_form_submit);
        this.submitSearch.setOnClickListener(new C01561());
        this.searchFormTab = (FormTabButton) findViewById(C0065R.id.search_form_button);
        this.searchFormTab.setView(this.searchFormView);
        this.userTab = (FormTabButton) findViewById(C0065R.id.user_button);
        this.userTab.setView(findViewById(C0065R.id.user_login_form));
        this.settingsTab = (FormTabButton) findViewById(C0065R.id.settings_button);
        this.settingsTab.setView(findViewById(C0065R.id.setting_form));
        this.tabManager = new TabManager(this, this.searchFormTab, this.userTab, this.settingsTab);
        this.searchFormTab.callOnClick();
        this.userData = (ViewGroup) findViewById(C0065R.id.user_login_data);
        findViewById(C0065R.id.try_logging_in_again).setOnClickListener(new C01572());
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public OmniformContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OmniformContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private FragmentIndex getNearestSearchIndex(String query) {
        FragmentIndex index = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(query);
        while (index != null && index.getSearchFormId() == -1) {
            index = index.getParent();
        }
        return index;
    }

    public void alter(String query) {
        if (!query.equals(this.lastQuery)) {
            alter(query, getNearestSearchIndex(query));
        }
    }

    private void alter(String query, FragmentIndex index) {
        this.lastQuery = query;
        if (this.index != index) {
            this.index = index;
            this.searchFormView.removeAllViews();
            if (index != null) {
                prepareSearchForm(query);
                prepareUserData();
                return;
            }
            setVisibility(8);
        } else if (this.searchForm != null) {
            this.searchForm.setFormData(query);
        }
    }

    private void prepareSearchForm(String query) {
        if (this.submitOnDone == null) {
            this.submitOnDone = new C01583();
        }
        inflate(getContext(), this.index.getSearchFormId(), this.searchFormView);
        FormSearchHandler searchHandler = this.index.getFormSearchHandler();
        String title = this.index.getBreadcrumbName().toUpperCase();
        int icon = this.index.getRootBreadcrumbIcon();
        if (searchHandler != null) {
            String titleAlt = searchHandler.getTitle(this.index, query);
            int iconAlt = searchHandler.getIcon(this.index, query);
            if (titleAlt != null) {
                title = titleAlt;
            }
            if (iconAlt != 0) {
                icon = iconAlt;
            }
        }
        this.formTitle.setText(title);
        this.submitSearch.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
        this.submitSearch.setText("Search " + this.index.getBreadcrumbName());
        this.searchForm = new SearchForm(this.searchFormView);
        this.searchForm.setFormData(query);
        this.searchForm.setEditorActionListener(this.submitOnDone);
        setVisibility(0);
    }

    private void prepareUserData() {
        this.userData.removeAllViews();
        if (this.index.getAccountType() != null) {
            this.userTab.setVisibility(0);
            inflate(getContext(), this.index.getAccountLayout(), this.userData);
            return;
        }
        this.userTab.setVisibility(8);
    }

    public void alter(List<Breadcrumb> breadcrumbs) {
        CrDb.logTime("search form container", "alter", true);
        FragmentIndex index = null;
        String url = null;
        for (Breadcrumb breadcrumb : breadcrumbs) {
            if (breadcrumb.getFragmentIndex().getSearchFormId() != -1) {
                index = breadcrumb.getFragmentIndex();
                url = breadcrumb.getUrl();
            }
        }
        if (index != null) {
            alter(url, index);
        } else {
            this.userTab.hide();
        }
        CrDb.logTime("search form container", "alter", false);
    }

    public boolean isActive() {
        return this.index != null;
    }

    private void addNewAccount(String accountType, String authTokenType) {
        AccountManagerFuture<Bundle> future = AccountManager.get(getContext()).addAccount(accountType, authTokenType, null, null, (Activity) getContext(), new C01594(), null);
    }

    private void getAuthToken() {
        ((GalleryViewer) getContext()).authLoginPromptIfNeeded(this.index.getAccountType(), new C01605());
    }

    public void showAccount() {
        if (this.userTab.isShown()) {
            this.tabManager.onClick(this.userTab);
        }
    }

    public void backToSearchForm() {
        this.tabManager.onClick(this.searchFormTab);
    }

    public void showAsInGrid(String linkUrl) {
        this.category = AnalyticsCategories.GALLERY_GRID;
        setVisibility(0);
        this.formTitle.setVisibility(8);
        alter(linkUrl);
    }

    public void onTabSwitch(FormTabButton current) {
        this.submitSearch.setVisibility(8);
        if (current == this.searchFormTab) {
            Analytics.INSTANCE.newEvent(this.category, "tab", "search");
            this.submitSearch.setVisibility(0);
        } else if (current == this.userTab && this.index.getAccountType() != null) {
            Analytics.INSTANCE.newEvent(this.category, "tab", "user");
            getAuthToken();
        }
    }
}
