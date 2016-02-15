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
import com.squareup.otto.Bus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BreadcrumbList
  extends LinearLayout
  implements View.OnClickListener, BreadcrumbListModifier
{
  OmnibarView omnibarView;
  private View.OnClickListener onClickListener;
  HorizontalScrollView scrollView;
  
  public BreadcrumbList(Context paramContext)
  {
    super(paramContext);
  }
  
  public BreadcrumbList(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public BreadcrumbList(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void addNewBreadcrumbs(List<FragmentIndex> paramList, int paramInt, String paramString)
  {
    while (paramInt < paramList.size())
    {
      String str = null;
      if (paramInt == paramList.size() - 1) {
        str = paramString;
      }
      appendBreadcrumb(paramInt, str, (FragmentIndex)paramList.get(paramInt));
      paramInt += 1;
    }
  }
  
  private void appendBreadcrumb(int paramInt, String paramString, FragmentIndex paramFragmentIndex)
  {
    paramString = new Breadcrumb(getContext(), paramFragmentIndex, paramInt, paramString);
    paramString.setOnClickListener(this);
    addView(paramString);
  }
  
  private int findTruncatePoint(List<FragmentIndex> paramList)
  {
    int i = 0;
    for (;;)
    {
      if (i < Math.min(paramList.size(), getBreadcrumbCount()))
      {
        Breadcrumb localBreadcrumb = (Breadcrumb)getChildAt(i);
        boolean bool = ((FragmentIndex)paramList.get(i)).matches(localBreadcrumb.getFragmentIndex());
        if ((localBreadcrumb != null) && (bool)) {}
      }
      else
      {
        return i;
      }
      i += 1;
    }
  }
  
  private Breadcrumb getLastBreadcrumb()
  {
    return (Breadcrumb)getChildAt(getChildCount() - 1);
  }
  
  private void setFocus(Breadcrumb paramBreadcrumb, boolean paramBoolean)
  {
    if (paramBreadcrumb == null) {}
    do
    {
      return;
      Iterator localIterator = getChildren().iterator();
      while (localIterator.hasNext())
      {
        Breadcrumb localBreadcrumb = (Breadcrumb)localIterator.next();
        if (paramBreadcrumb != localBreadcrumb) {
          localBreadcrumb.defocus();
        } else {
          localBreadcrumb.focus();
        }
      }
    } while (!paramBoolean);
    scrollIntoView(paramBreadcrumb);
  }
  
  private void setFocusToLast(boolean paramBoolean)
  {
    setFocus((Breadcrumb)getChildAt(getChildCount() - 1), paramBoolean);
  }
  
  private void truncateBreadcrumbs(int paramInt)
  {
    while (getBreadcrumbCount() != paramInt) {
      removeViewAt(paramInt);
    }
  }
  
  public void addNew(UrlCrumb... paramVarArgs)
  {
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      UrlCrumb localUrlCrumb = paramVarArgs[i];
      Breadcrumb localBreadcrumb = new Breadcrumb(getContext(), FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(localUrlCrumb.url), localUrlCrumb.position, localUrlCrumb.url);
      localBreadcrumb.setOnClickListener(this);
      addView(localBreadcrumb, localUrlCrumb.position);
      i += 1;
    }
  }
  
  public void delayScrollRight()
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        BreadcrumbList.this.scrollView.fullScroll(66);
      }
    }, 200L);
  }
  
  public void delayScrollToLast()
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        BreadcrumbList.this.scrollIntoView(BreadcrumbList.this.getLastBreadcrumb());
      }
    }, 200L);
  }
  
  public int getBreadcrumbCount()
  {
    return getChildCount();
  }
  
  public List<Breadcrumb> getChildren()
  {
    int j = getBreadcrumbCount();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < j)
    {
      localArrayList.add((Breadcrumb)getChildAt(i));
      i += 1;
    }
    return localArrayList;
  }
  
  public int getCurrentSearchForm()
  {
    return ((Breadcrumb)getChildAt(getBreadcrumbCount() - 1)).getFragmentIndex().getSearchFormId();
  }
  
  public void initialize(OmnibarView paramOmnibarView, HorizontalScrollView paramHorizontalScrollView)
  {
    this.omnibarView = paramOmnibarView;
    this.scrollView = paramHorizontalScrollView;
  }
  
  public void onClick(View paramView)
  {
    Breadcrumb localBreadcrumb = (Breadcrumb)paramView;
    this.omnibarView.dismissGalleryPanel();
    if ((localBreadcrumb.hasUrl()) && (localBreadcrumb != getLastBreadcrumb()))
    {
      Analytics.INSTANCE.newNavigationEvent("breadcrumb click", localBreadcrumb.getUrl());
      BusProvider.BUS.get().post(new UrlChangeEvent(localBreadcrumb.getUrl()));
    }
    for (;;)
    {
      setFocusToLast();
      return;
      this.onClickListener.onClick(paramView);
    }
  }
  
  public void replaceBreadcrumbs(List<Breadcrumb> paramList)
  {
    removeAllViews();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      addView((Breadcrumb)paramList.next());
    }
    setFocusToLast(false);
  }
  
  public void scrollIntoView(View paramView)
  {
    if (this.scrollView == null) {
      return;
    }
    if (this.scrollView.getScrollX() < paramView.getLeft()) {}
    for (int i = paramView.getRight() + 10;; i = paramView.getLeft() - 10)
    {
      this.scrollView.smoothScrollTo(i, 0);
      return;
    }
  }
  
  public void setFocusToLast()
  {
    setFocusToLast(true);
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    super.setOnClickListener(paramOnClickListener);
    this.onClickListener = paramOnClickListener;
  }
  
  public void updateBreadcrumbs(String paramString, List<FragmentIndex> paramList)
  {
    int i = findTruncatePoint(paramList);
    truncateBreadcrumbs(i);
    addNewBreadcrumbs(paramList, i, paramString);
    paramList = getLastBreadcrumb();
    if (paramList != null)
    {
      removeView(paramList);
      appendBreadcrumb(getChildCount(), paramString, paramList.getFragmentIndex());
    }
    setFocusToLast(true);
  }
  
  public void updateBreadcrumbsWithTruncate(String paramString, FragmentIndex paramFragmentIndex)
  {
    int i = getChildCount() - 1;
    for (;;)
    {
      if ((i <= 0) || (((Breadcrumb)getChildAt(i)).getFragmentIndex() == paramFragmentIndex))
      {
        truncateBreadcrumbs(i);
        updateBreadcrumbs(paramString, FragmentRouter.INSTANCE.buildBreadCrumbPath(paramString));
        return;
      }
      i -= 1;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/BreadcrumbList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */