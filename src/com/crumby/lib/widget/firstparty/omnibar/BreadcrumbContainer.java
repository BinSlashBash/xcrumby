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
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.events.LoginEvent;
import com.crumby.lib.events.OmniformEvent;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.Iterator;
import java.util.List;

public class BreadcrumbContainer
  extends RelativeLayout
{
  private BreadcrumbList breadcrumbList;
  private FragmentStatusButton fragmentStatusButton;
  private String loggedInAccount;
  private View loggedInIndicator;
  
  public BreadcrumbContainer(Context paramContext)
  {
    super(paramContext);
  }
  
  public BreadcrumbContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public BreadcrumbContainer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void displayLoginIndicator(String paramString)
  {
    final Object localObject = (Breadcrumb)this.breadcrumbList.getChildAt(0);
    if (localObject != null)
    {
      localObject = ((Breadcrumb)localObject).getFragmentIndex().getAccountType();
      if (localObject == null) {
        break label120;
      }
      if (paramString != null) {
        break label103;
      }
      if ((this.loggedInAccount == null) || (!this.loggedInAccount.equals(localObject))) {
        break label66;
      }
      CrDb.d("breadcrumb container", "same account type as last one, no need to check!");
      this.loggedInIndicator.setVisibility(0);
    }
    label66:
    label103:
    while (!((String)localObject).equals(paramString))
    {
      return;
      CrDb.d("breadcrumb container", "different account type! time to check!");
      this.loggedInIndicator.setVisibility(8);
      ((GalleryViewer)getContext()).checkLogin((String)localObject, new AccountManagerCallback()
      {
        public void run(AccountManagerFuture<Bundle> paramAnonymousAccountManagerFuture)
        {
          try
          {
            paramAnonymousAccountManagerFuture.getResult();
            BreadcrumbContainer.access$002(BreadcrumbContainer.this, localObject);
            BreadcrumbContainer.this.loggedInIndicator.setVisibility(0);
            return;
          }
          catch (Exception paramAnonymousAccountManagerFuture)
          {
            BreadcrumbContainer.this.loggedInIndicator.setVisibility(8);
          }
        }
      });
      return;
    }
    this.loggedInIndicator.setVisibility(0);
    return;
    label120:
    this.loggedInIndicator.setVisibility(8);
  }
  
  public void focus()
  {
    this.breadcrumbList.setFocusToLast();
  }
  
  public List<Breadcrumb> getCurrentBreadcrumbs()
  {
    return this.breadcrumbList.getChildren();
  }
  
  public int getCurrentSearchForm()
  {
    return this.breadcrumbList.getCurrentSearchForm();
  }
  
  public void hide()
  {
    setVisibility(8);
    this.breadcrumbList.setFocusToLast();
  }
  
  public void initialize(final OmnibarView paramOmnibarView)
  {
    View localView = findViewById(2131492909);
    this.breadcrumbList = ((BreadcrumbList)findViewById(2131492910));
    this.breadcrumbList.initialize(paramOmnibarView, (HorizontalScrollView)localView);
    this.fragmentStatusButton = ((FragmentStatusButton)findViewById(2131492911));
    this.loggedInIndicator = findViewById(2131492908);
    this.loggedInIndicator.setClickable(true);
    this.loggedInIndicator.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        BreadcrumbContainer.this.callOnClick();
        paramOmnibarView.showAccount();
      }
    });
    BusProvider.BUS.get().register(this);
  }
  
  public void newPage(GalleryViewerFragment paramGalleryViewerFragment, boolean paramBoolean)
  {
    Analytics.INSTANCE.newPage(paramGalleryViewerFragment);
    String str = paramGalleryViewerFragment.getUrl();
    if (paramGalleryViewerFragment.getBreadcrumbs() != null)
    {
      this.breadcrumbList.replaceBreadcrumbs(paramGalleryViewerFragment.getBreadcrumbs());
      displayLoginIndicator(null);
      this.breadcrumbList.delayScrollRight();
      return;
    }
    if (!paramBoolean) {
      this.breadcrumbList.updateBreadcrumbs(str, FragmentRouter.INSTANCE.buildBreadCrumbPath(str));
    }
    for (;;)
    {
      paramGalleryViewerFragment.setBreadcrumbs(this.breadcrumbList);
      break;
      this.breadcrumbList.updateBreadcrumbsWithTruncate(str, FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(str));
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    BusProvider.BUS.get().unregister(this);
  }
  
  @Subscribe
  public void onLoginEvent(LoginEvent paramLoginEvent)
  {
    displayLoginIndicator(paramLoginEvent.accountType);
  }
  
  @Subscribe
  public void onOmniformEvent(OmniformEvent paramOmniformEvent)
  {
    if (paramOmniformEvent.eventName.equals("Login"))
    {
      this.loggedInIndicator.callOnClick();
      return;
    }
    callOnClick();
  }
  
  public void override(List<Breadcrumb> paramList)
  {
    this.breadcrumbList.removeAllViews();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Breadcrumb localBreadcrumb = (Breadcrumb)paramList.next();
      this.breadcrumbList.addView(localBreadcrumb);
    }
    this.breadcrumbList.setFocusToLast();
  }
  
  public void refreshMode()
  {
    this.fragmentStatusButton.refreshMode();
  }
  
  public void removeBreadcrumbs()
  {
    this.breadcrumbList.removeAllViews();
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    super.setOnClickListener(paramOnClickListener);
    this.breadcrumbList.setOnClickListener(paramOnClickListener);
  }
  
  public void show()
  {
    setVisibility(0);
  }
  
  public void stopLoadingMode()
  {
    this.fragmentStatusButton.stopLoadingMode();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/BreadcrumbContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */