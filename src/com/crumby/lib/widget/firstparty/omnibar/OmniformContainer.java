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
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.FormSearchHandler;
import com.crumby.lib.SearchForm;
import com.crumby.lib.authentication.UserData;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.squareup.otto.Bus;
import java.util.Iterator;
import java.util.List;

public class OmniformContainer
  extends LinearLayout
  implements TabSwitchListener
{
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
  
  public OmniformContainer(Context paramContext)
  {
    super(paramContext);
  }
  
  public OmniformContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public OmniformContainer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void addNewAccount(String paramString1, String paramString2)
  {
    AccountManager.get(getContext()).addAccount(paramString1, paramString2, null, null, (Activity)getContext(), new AccountManagerCallback()
    {
      public void run(AccountManagerFuture<Bundle> paramAnonymousAccountManagerFuture)
      {
        try
        {
          paramAnonymousAccountManagerFuture = (Bundle)paramAnonymousAccountManagerFuture.getResult();
          Log.d("udinic", "AddNewAccount Bundle is " + paramAnonymousAccountManagerFuture);
          return;
        }
        catch (Exception paramAnonymousAccountManagerFuture)
        {
          paramAnonymousAccountManagerFuture.printStackTrace();
        }
      }
    }, null);
  }
  
  private void alter(String paramString, FragmentIndex paramFragmentIndex)
  {
    this.lastQuery = paramString;
    if (this.index == paramFragmentIndex)
    {
      if (this.searchForm != null) {
        this.searchForm.setFormData(paramString);
      }
      return;
    }
    this.index = paramFragmentIndex;
    this.searchFormView.removeAllViews();
    if (paramFragmentIndex != null)
    {
      prepareSearchForm(paramString);
      prepareUserData();
      return;
    }
    setVisibility(8);
  }
  
  private void getAuthToken()
  {
    ((GalleryViewer)getContext()).authLoginPromptIfNeeded(this.index.getAccountType(), new AccountManagerCallback()
    {
      public void run(AccountManagerFuture<Bundle> paramAnonymousAccountManagerFuture)
      {
        try
        {
          paramAnonymousAccountManagerFuture = ((Bundle)paramAnonymousAccountManagerFuture.getResult()).getString("authAccount");
          OmniformContainer.this.userData.setVisibility(0);
          ((UserData)OmniformContainer.this.userData.getChildAt(0)).fillWith(paramAnonymousAccountManagerFuture);
          OmniformContainer.this.findViewById(2131493089).setVisibility(8);
          return;
        }
        catch (Exception paramAnonymousAccountManagerFuture)
        {
          OmniformContainer.this.userData.setVisibility(8);
          OmniformContainer.this.findViewById(2131493089).setVisibility(0);
          ((TextView)OmniformContainer.this.findViewById(2131493090)).setText(paramAnonymousAccountManagerFuture.getMessage());
        }
      }
    });
  }
  
  private FragmentIndex getNearestSearchIndex(String paramString)
  {
    for (paramString = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(paramString); (paramString != null) && (paramString.getSearchFormId() == -1); paramString = paramString.getParent()) {}
    return paramString;
  }
  
  private void prepareSearchForm(String paramString)
  {
    if (this.submitOnDone == null) {
      this.submitOnDone = new TextView.OnEditorActionListener()
      {
        public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if (((paramAnonymousKeyEvent != null) && (paramAnonymousKeyEvent.getKeyCode() == 66)) || (paramAnonymousInt == 6)) {
            OmniformContainer.this.submitSearch.callOnClick();
          }
          return false;
        }
      };
    }
    inflate(getContext(), this.index.getSearchFormId(), this.searchFormView);
    FormSearchHandler localFormSearchHandler = this.index.getFormSearchHandler();
    Object localObject1 = this.index.getBreadcrumbName().toUpperCase();
    int j = this.index.getRootBreadcrumbIcon();
    int i = j;
    Object localObject2 = localObject1;
    if (localFormSearchHandler != null)
    {
      localObject2 = localFormSearchHandler.getTitle(this.index, paramString);
      int k = localFormSearchHandler.getIcon(this.index, paramString);
      if (localObject2 != null) {
        localObject1 = localObject2;
      }
      i = j;
      localObject2 = localObject1;
      if (k != 0)
      {
        i = k;
        localObject2 = localObject1;
      }
    }
    this.formTitle.setText((CharSequence)localObject2);
    this.submitSearch.setCompoundDrawablesWithIntrinsicBounds(i, 0, 0, 0);
    this.submitSearch.setText("Search " + this.index.getBreadcrumbName());
    this.searchForm = new SearchForm(this.searchFormView);
    this.searchForm.setFormData(paramString);
    this.searchForm.setEditorActionListener(this.submitOnDone);
    setVisibility(0);
  }
  
  private void prepareUserData()
  {
    this.userData.removeAllViews();
    if (this.index.getAccountType() != null)
    {
      this.userTab.setVisibility(0);
      inflate(getContext(), this.index.getAccountLayout(), this.userData);
      return;
    }
    this.userTab.setVisibility(8);
  }
  
  public void alter(String paramString)
  {
    if (paramString.equals(this.lastQuery)) {
      return;
    }
    alter(paramString, getNearestSearchIndex(paramString));
  }
  
  public void alter(List<Breadcrumb> paramList)
  {
    CrDb.logTime("search form container", "alter", true);
    Breadcrumb localBreadcrumb = null;
    String str = null;
    Iterator localIterator = paramList.iterator();
    paramList = localBreadcrumb;
    while (localIterator.hasNext())
    {
      localBreadcrumb = (Breadcrumb)localIterator.next();
      if (localBreadcrumb.getFragmentIndex().getSearchFormId() != -1)
      {
        paramList = localBreadcrumb.getFragmentIndex();
        str = localBreadcrumb.getUrl();
      }
    }
    if (paramList != null) {
      alter(str, paramList);
    }
    for (;;)
    {
      CrDb.logTime("search form container", "alter", false);
      return;
      this.userTab.hide();
    }
  }
  
  public void backToSearchForm()
  {
    this.tabManager.onClick(this.searchFormTab);
  }
  
  public boolean isActive()
  {
    return this.index != null;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.category = AnalyticsCategories.OMNIBAR;
    this.searchFormView = ((ViewGroup)findViewById(2131493086));
    this.formTitle = ((TextView)findViewById(2131493081));
    this.submitSearch = ((Button)findViewById(2131493093));
    this.submitSearch.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (OmniformContainer.this.index.getFormSearchHandler() != null) {}
        for (paramAnonymousView = OmniformContainer.this.index.getFormSearchHandler().getUrlForSearch(OmniformContainer.this.lastQuery, OmniformContainer.this.searchForm);; paramAnonymousView = OmniformContainer.this.index.getBaseUrl() + "?" + OmniformContainer.this.searchForm.encodeFormData())
        {
          Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, OmniformContainer.this.category.getName() + " form search", OmniformContainer.this.index.getBreadcrumbName() + " " + paramAnonymousView);
          BusProvider.BUS.get().post(new UrlChangeEvent(paramAnonymousView));
          return;
        }
      }
    });
    this.searchFormTab = ((FormTabButton)findViewById(2131493082));
    this.searchFormTab.setView(this.searchFormView);
    this.userTab = ((FormTabButton)findViewById(2131493083));
    this.userTab.setView(findViewById(2131493087));
    this.settingsTab = ((FormTabButton)findViewById(2131493084));
    this.settingsTab.setView(findViewById(2131493092));
    this.tabManager = new TabManager(this, new FormTabButton[] { this.searchFormTab, this.userTab, this.settingsTab });
    this.searchFormTab.callOnClick();
    this.userData = ((ViewGroup)findViewById(2131493088));
    findViewById(2131493091).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        OmniformContainer.this.getAuthToken();
      }
    });
  }
  
  public void onTabSwitch(FormTabButton paramFormTabButton)
  {
    this.submitSearch.setVisibility(8);
    if (paramFormTabButton == this.searchFormTab)
    {
      Analytics.INSTANCE.newEvent(this.category, "tab", "search");
      this.submitSearch.setVisibility(0);
    }
    while ((paramFormTabButton != this.userTab) || (this.index.getAccountType() == null)) {
      return;
    }
    Analytics.INSTANCE.newEvent(this.category, "tab", "user");
    getAuthToken();
  }
  
  public void showAccount()
  {
    if (this.userTab.isShown()) {
      this.tabManager.onClick(this.userTab);
    }
  }
  
  public void showAsInGrid(String paramString)
  {
    this.category = AnalyticsCategories.GALLERY_GRID;
    setVisibility(0);
    this.formTitle.setVisibility(8);
    alter(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/OmniformContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */